package cz.jet.services;


import cz.jet.controllers.ResultController;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class DeferredResultService implements Runnable {

  

  private final BlockingQueue<DeferredResult<String>> resultQueue = new LinkedBlockingQueue();

  private Thread thread;

  private volatile boolean start = true;

  
  private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();



  public void subscribe() {
    System.out.println("Long polling thread started.");
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

        DeferredResult<String> result = resultQueue.take();
        String string = queue.take();

        result.setResult(string);

      } catch (InterruptedException e) {
        System.err.println("Cannot get latest update. " + e.getMessage());
      }
    }
  }

  public void getUpdate(DeferredResult<String> result) {
    resultQueue.add(result);
  }

}