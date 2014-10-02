/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package ejbs;

import entidades.Login;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import utilidades.SumadoSocio;


/**
 *
 * @author juanma
 */

@Stateless
@LocalBean
public class GestionEventos {
   
    
@Inject
@SumadoSocio 
Event <Login> usuarioSumadoEvent;
  
   // generamos un evento al sumar un socio
    public void fireUsuarioSumadoEvent( Login login ) {
        usuarioSumadoEvent.fire(login);
    } 

    
    
    
}
