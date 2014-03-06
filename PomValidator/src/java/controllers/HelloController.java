package controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JK
 */
 
@Controller//anotace kontroleru
public class HelloController {//nazev tridy musi byt stejny jako url!!!
    
    /*
	Priklad fungovani frameworku
    podle tutorialu http://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm
    */
    // url (value=...)se nemusi psat pokazde, lze jej napsat i primo pro celou tridu, viz. tutorial 
   @RequestMapping(value = "/hello", method = RequestMethod.GET) 
   public String printHello(ModelMap model) {//promenne do viewu
      model.addAttribute("message", "Ahoj");//promenna message predana do jspcka
      return "hello/hello";// jmeno jspcka, v tomhle pripade se bude hledat hello.jsp (sestavuje se ve view Resolveru - viz dispatcher-servlet.xml)
   }
   
   @RequestMapping(value = "/hello/horse", method = RequestMethod.GET) 
   public String printHorse(ModelMap model) {//promenne do viewu
      model.addAttribute("message", "Žluťoučký kůň");//promenna message predana do jspcka
      return "hello/hello";// jmeno jspcka, v tomhle pripade se bude hledat hello.jsp (sestavuje se ve view Resolveru - viz dispatcher-servlet.xml)
   }
    
}
//@Controller
//@RequestMapping("/hello")
//public class HelloController{
// 
//   @RequestMapping(method = RequestMethod.GET)
//   public String printHello(ModelMap model) {
//      model.addAttribute("message", "Hello Spring MVC Framework!");
//      return "hello";
//   }
//
//}

