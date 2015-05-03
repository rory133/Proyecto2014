/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package utilidades;

/**
 *
 * @author juanma
 */
import java.io.IOException;
import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;



public class LoggingProducer {
    
   // private FileHandler fileHandler;
@Produces
public Logger produceLogger(InjectionPoint injectionPoint) throws IOException {
    
    
//    FileHandler fileHandler = new FileHandler("C:/Users/juanma/myLogFile");
    
  //  fileHandler.setFormatter(new LogFormatter());
//    Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()).addHandler(fileHandler);
//    
//    Logger logger=Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
//    addFileHandler(logger);
    

//    try {
        //String filePath="./";
      //FileHandler fileHandler = new FileHandler("C:\\Users\\juanma\\Documents\\NetBeansProjects\\EjemploLogin\\logxxx.log", 5242880, 5, true);
        //      FileHandler fileHandler = new FileHandler("C:\\logs\\myapp-log.%u.%g.txt",1024 * 1024, 10, true);
        //      fileHandler.setFormatter(new SimpleFormatter());
      
     // Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()).addHandler(fileHandler);
        
//    } catch (IOException e) {}
    
    //FileHandler fileHandler = new FileHandler();
      
      
      
//    FileHandler fileHandler = new FileHandler("C:\\logs\\myapp-log.%u.%g.txt",1024 * 1024, 10, true);
//    fileHandler.setFormatter(new SimpleFormatter());
//    Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()).addHandler(fileHandler);
//    Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()).setUseParentHandlers(false);
//    System.out.println("********loogginfPruducer");
    
    /*
    FileHandler fh;  
    
    try {  

        // se configura el logger con un fileHandler y el formato  
       fh = new FileHandler("C:/logsProyecto/MyLogFile.%u.%g.log",1024 * 1024, 1, true);  
        //fh = new FileHandler("C:/logsProyecto/MyLogFile.%u.%g.log",1024 * 1024,2, true); 
        //fh = new FileHandler("C:/logsProyecto/MyLogFile.log",1024 * 1024,2, true);
       // fh = new FileHandler("C:/logsProyecto/logfile%g.txt",1024 * 1024,2, true); 
        
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()).addHandler(fh);
        
        
        

        // añadimos un mensaje  
        Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()).info("Comienza el Log");  

        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    
    
    
    */
    
    return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
   }

//    private void addFileHandler(Logger logger) {
//        try {
//            fileHandler = new FileHandler(Logger.class.getName() + ".logxxxx");
//        } catch (IOException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        } catch (SecurityException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//        logger.addHandler(fileHandler);
//    }
}

