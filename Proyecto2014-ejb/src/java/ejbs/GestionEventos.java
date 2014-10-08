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
import entidades.Producto;
import entidades.Puja;
import entidades.Venta;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import utilidades.CompraVentaDirecta;
import utilidades.RealizadaPuja;
import utilidades.SubastaExpirada;
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

@Inject
@CompraVentaDirecta
Event <Venta> productoAdquiridoVentaDirectaEvent;

@Inject
@RealizadaPuja
Event <Puja> pujaRealizadaEvent;


@Inject
@SubastaExpirada
Event <Producto> expiradoTiempoSubastaEvent;
  
   // generamos un evento al sumar un socio
    public void fireUsuarioSumadoEvent( Login login ) {
        usuarioSumadoEvent.fire(login);
    } 

    public void fireProductoAdquiridoVentaDirecta( Venta venta ) {
        productoAdquiridoVentaDirectaEvent.fire(venta);
    } 
    
    public void firePujaRealizada( Puja puja ) {
        pujaRealizadaEvent.fire(puja);
    } 
    
    public void fireExpiradoTiempoSubasta(Producto producto){
        expiradoTiempoSubastaEvent.fire(producto);
    }
}
