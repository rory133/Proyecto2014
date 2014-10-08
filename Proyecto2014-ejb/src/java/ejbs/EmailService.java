/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package ejbs;


import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author juanma
 */
@Stateless
@LocalBean
//realiza el envio de los emails correspondientes
public class EmailService {
    
    @Asynchronous
  public void envioGrupo(String[] to, String subject, String contenido){
      try 
    {
    String host = "smtp.gmail.com";
    final String from = "buyup.mail.aps@gmail.com";
    final String pass = "proyecto2014";

    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true"); 
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.user", from);
    props.put("mail.smtp.password", pass);
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");

    

  
    Session session = Session.getInstance(props);
    
    
    
    
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));

    InternetAddress[] toAddress = new InternetAddress[to.length];

    // To get the array of addresses
    for( int i=0; i < to.length; i++ ) { 
        toAddress[i] = new InternetAddress(to[i]);
    }
    System.out.println(Message.RecipientType.TO);

    for( int i=0; i < toAddress.length; i++) { 
        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
    }
    message.setSubject(subject);
    message.setText(contenido);
    
    
   
    Transport t = session.getTransport("smtps");
    try {
	t.connect(host, from, pass);
	t.sendMessage(message, message.getAllRecipients());
    } finally {
	t.close();
    }
    
    
    
    
    
    
    }catch (Exception e) {
        System.out.println(e);
    }
}
  
  
  
  
  @Asynchronous//para que el envio no ralentice la aplicación
  public void envioIndividual(String to, String subject, String contenido){
      try 
    {
    String host = "smtp.gmail.com";
    final String from = "buyup.mail.aps@gmail.com";
    final String pass = "proyecto2014";
    
    

    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true"); 
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.user", from);
    props.put("mail.smtp.password", pass);
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");

   // String to = "rory133@gmail.com"; 


    
    Session session = Session.getInstance(props);
    
    
    
    
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));

    InternetAddress toAddress = new InternetAddress(to);


 
    System.out.println(Message.RecipientType.TO);

     
        message.addRecipient(Message.RecipientType.TO, toAddress);
    
    message.setSubject(subject);
    message.setText(contenido);
    
    

   
       Transport t = session.getTransport("smtps");
    try {
	t.connect(host, from, pass);
	t.sendMessage(message, message.getAllRecipients());
    } finally {
	t.close();
    }
    
    
    
    
    
    
    }catch (Exception e) {
        System.out.println(e);
    }
}  
}
