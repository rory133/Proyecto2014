/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package utilidades;

import ejbs.EmailService;
import entidades.Login;
import entidades.Producto;
import entidades.Usuario;
import entidades.Venta;
import facade.LoginFacade;
import facade.UsuarioFacade;
import facade.VentaFacade;
import java.io.Serializable;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import utilidades.Loggable;
import utilidades.SumadoSocio;

/**
 *
 * @author juanma
 */




@Loggable
@RequestScoped
//captura los eventos en los que se envian emails 
public class MailEvent implements Serializable{
@EJB 
EmailService emailService;
@EJB
private UsuarioFacade usuarioFacade;

@EJB
private VentaFacade ventaFacade;


  //captura el evento que se ha producido al crear un nuevo socio
    public void sumadoUsuario(@Observes @SumadoSocio Login login ) {
        
         Usuario usuario= usuarioFacade.find(login.getUsuarioIdusuario().getIdusuario());
        
          String bienVenida="Bienvenido al portal BuyUp \n"+usuario.getNombre()+" "+usuario.getApellidos()+"\n";
          bienVenida=bienVenida+"has sido dado de alta como usuario \n con el login:"+ login.getLogin()+"\n";
          bienVenida=bienVenida+"y el password: "+login.getPassword();
          
          
          emailService.envioIndividual(usuario.getEmail(),"BuyUp", bienVenida);

              
    }
    public void compradoPruductoVentaDirecta(@Observes @CompraVentaDirecta Venta venta ) {
        
         
         Usuario usuarioComprador=venta.getCompradorIdusuario();
         Usuario usuarioVendedor= venta.getProductoIdproducto().getUsuarioIdusuario();
         Producto productoComprado=venta.getProductoIdproducto();
        
         //correo al vendedor
          String mensajeAVendedor="información desde BuyUp \n";
          mensajeAVendedor=mensajeAVendedor+"El producto "+productoComprado.getNombre()  +" que tenias en la modalidad de venta directa en nuestro portal \n";
          mensajeAVendedor=mensajeAVendedor+"acaba de ser adquirido por el usuario "+usuarioComprador.getNombre()+"\n";
          mensajeAVendedor=mensajeAVendedor+"Ponte en contacto con él para gestionar pago y envio en el correo  "+usuarioComprador.getEmail()+"\n";
          emailService.envioIndividual(usuarioVendedor.getEmail(),"Compra de tú producto en BuyUp", mensajeAVendedor);
          
          //correo al comprador
          String mensajeAComprador="información desde BuyUp \n";
          mensajeAComprador=mensajeAComprador+"Acabas de adquirir el producto "+productoComprado.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+"Que tenía a la venta el usuario  "+usuarioVendedor.getNombre()+" "+ usuarioVendedor.getApellidos() +" en nuestro portal \n";
          mensajeAComprador=mensajeAComprador+"Ponte en contacto con él para gestionar pago y envio en el correo  "+usuarioVendedor.getEmail()+"\n";
          emailService.envioIndividual(usuarioComprador.getEmail(),"Has comprado un producto en BuyUp", mensajeAComprador);

              
    }
    
}
