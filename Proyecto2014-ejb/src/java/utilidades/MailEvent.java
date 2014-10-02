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
import entidades.Usuario;
import facade.LoginFacade;
import facade.UsuarioFacade;
import java.io.Serializable;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import utilidades.SumadoSocio;
import utilidades.Loggable;

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


  //captura el evento que se ha producido al crear un nuevo socio
    public void sumadoUsuario(@Observes @SumadoSocio Login login ) {
        
         Usuario usuario= usuarioFacade.find(login.getUsuarioIdusuario().getIdusuario());
        
          String bienVenida="Bienvenido al portal BuyUp \n"+usuario.getNombre()+" "+usuario.getApellidos()+"\n";
          bienVenida=bienVenida+"has sido dado de alta como usuario \n con el login:"+ login.getLogin()+"\n";
          bienVenida=bienVenida+"y el password: "+login.getPassword();
          
          
          emailService.envioIndividual(usuario.getEmail(),"BuyUp", bienVenida);

              
            }
    
}
