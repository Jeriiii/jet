package cz.jet.services;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.mail.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class DeferredResultService implements Runnable {

  

  private final BlockingQueue<DeferredResult<Message>> resultQueue = new LinkedBlockingQueue();

  private Thread thread;

  private volatile boolean start = true;

  
  private LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue();



  public void subscribe() {
    
    startThread();
  }

  private void startThread() {

    if (start) {
      synchronized (this) {
        if (start) {
          start = false;
          thread = new Thread(this, "Studio Teletype");
          thread.start();
        }
      }
    }
  }

  @Override
  public void run() {

    while (true) {
      try {

        DeferredResult<Message> result = resultQueue.take();
        Message message = queue.take();

        result.setResult(message);

      } catch (InterruptedException e) {
        System.err.println("Cannot get latest update. " + e.getMessage());
      }
    }
  }

  public void getUpdate(DeferredResult<Message> result) {
    resultQueue.add(result);
  }

}