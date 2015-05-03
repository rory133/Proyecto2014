/*

 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package ejbs;

import entidades.Denuncia;
import entidades.Login;
import entidades.Producto;
import entidades.Puja;
import entidades.Usuario;
import entidades.Venta;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import utilidades.BorradoProductoConPujas;
import utilidades.BorradoProductoPorImprocedente;
import utilidades.CompraVentaDirecta;
import utilidades.RealizadaDenuncia;
import utilidades.RealizadaPuja;
import utilidades.SubastaExpirada;
import utilidades.SumadoSocio;
import utilidades.UsuarioBloqueado;
import utilidades.UsuarioBorrado;
import utilidades.UsuarioRedimido;


/**
 *
 * @author juanma
 */

@Stateless
@LocalBean
public class GestionEventos {
   
@Inject
@UsuarioBloqueado 
Event <Usuario> usuarioBloqueadoEvent;

@Inject
@UsuarioRedimido 
Event <Usuario> usuarioRedimidoEvent;

@Inject
@UsuarioBorrado 
Event <Usuario> usuarioBorradoEvent;

    
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

@Inject
@RealizadaDenuncia
Event <Denuncia> realizadaDenunciaEvent;

@Inject
@BorradoProductoConPujas
Event <Puja> borradoProductoConPujasEvent;

@Inject
@BorradoProductoPorImprocedente
Event <Producto> borradoProductoPorImprocedenteEvent;
  
   // generamos un evento al sumar un socio
    public void fireUsuarioSumadoEvent( Login login ) {
        usuarioSumadoEvent.fire(login);
    } 
    // generamos eventos para caja puja cuando se borra un producto que tenía
    public void fireBorradoProductoConPujasEvent( Puja puja ) {
        borradoProductoConPujasEvent.fire(puja);
    } 
    
    public void fireBorradoProductoPorImprocedenteEvent( Producto producto ) {
        borradoProductoPorImprocedenteEvent.fire(producto);
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
    
    public void fireRealizadaDenuncia(Denuncia denuncia){
        realizadaDenunciaEvent.fire(denuncia);
    }
       // generamos un evento al bloquear un socio
    public void fireUsuarioBloqueadoEvent( Usuario usuario ) {
        usuarioBloqueadoEvent.fire(usuario);
    } 
           // generamos un evento al redimir un socio
    public void fireUsuarioRedimidoEvent( Usuario usuario ) {
        usuarioRedimidoEvent.fire(usuario);
    }
    
    // generamos un evento al borrar un socio
    public void fireUsuarioBorradoEvent( Usuario usuario ) {
        usuarioBorradoEvent.fire(usuario);
    }
}
