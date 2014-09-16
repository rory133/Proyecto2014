/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author juanma
 */


public class VisualizaImagen extends HttpServlet {
    private static final long serialVersionUID = 4593558495041379082L;
    
    //ServletContext servletContext;
    
    //CrearProductoBean crearProducto = (CrearProductoBean) servletContext.getAttribute("crearProductoBean");
    
    
    //CrearProductoBean crearProducto;
        @Override
        public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
            InputStream sImage;
            CrearProductoBean crearProducto = (CrearProductoBean) request.getSession().getAttribute("crearProductoBean");
    try { 
            String tipo = request.getParameter("tipo");  
              System.out.println("pasdo como tipo: " + tipo);
     switch (tipo){
         
      case "crearProducto":
        try { 
            String id = request.getParameter("Image_id");
            System.out.println("inside servlet–>" + id);
            Integer indice=Integer.parseInt(id);
            System.out.println("inside servlet indice –>" + indice);
           // byte[] bytearray = new byte[1048576];
            byte[] bytearray = new byte[16777215];
            int size = 0;
            sImage = crearProducto.getImagenesSubidas().get(indice).getInputstream();
            System.out.println("inside servlet oontents –>" + crearProducto.getImagenesSubidas().size());
            //sImage = new ByteArrayInputStream(crearProducto.getImagenesSubidas().get(indice).getContents());
            response.reset();
            response.setContentType("image/jpeg");
                while ((size = sImage.read(bytearray)) != -1) {
                    response.getOutputStream().
                            write(bytearray, 0, size);
                }    
            } catch (Exception e) {
            e.printStackTrace();
        }
            
        break;    
        }
     
     
    }catch (Exception e) {
            e.printStackTrace();
        }
    
  }  
    
}
