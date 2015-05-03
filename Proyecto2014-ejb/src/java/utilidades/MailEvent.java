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
import entidades.Denuncia;
import entidades.Login;
import entidades.Producto;
import entidades.Puja;
import entidades.Usuario;
import entidades.Venta;
import facade.PujaFacade;
import facade.UsuarioFacade;
import facade.VentaFacade;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import utilidades.Loggable;
import utilidades.SumadoSocio;

/**
 *
 * @author juanma
 */




//@Loggable
@RequestScoped
//captura los eventos en los que se envian emails 
public class MailEvent implements Serializable{
@EJB 
private static EmailService emailService;
@EJB
private static UsuarioFacade usuarioFacade;
@EJB
private PujaFacade pujaFacade;

@EJB
private VentaFacade ventaFacade;

private List<Puja> listaPujas;

   

         

  //captura el evento que se ha producido al crear un nuevo socio
    public void sumadoUsuario(@Observes @SumadoSocio Login login ) {
        //internacionalizar los mensajes de correo enviados por el portal
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());     
        
         Usuario usuario= usuarioFacade.find(login.getUsuarioIdusuario().getIdusuario());
        
//          String bienVenida="Bienvenido al portal BuyUp \n"+usuario.getNombre()+" "+usuario.getApellidos()+"\n";
          String bienVenida=text.getString("app.BienvendioAlPortal")+usuario.getNombre()+" "+usuario.getApellidos()+"\n";
          
          bienVenida=bienVenida+text.getString("app.HasSidoDadoDeAlta")+ login.getLogin()+"\n";
          bienVenida=bienVenida+text.getString("app.YElPassword")+login.getPassword();
          
          
          emailService.envioIndividual(usuario.getEmail(),text.getString("app.AsuntoBienvenida"), bienVenida);

              
    }
    public void borradoProductoConPujaUsuario(@Observes @BorradoProductoConPujas Puja puja){
        //internacionalizar los mensajes de correo enviados por el portal
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());     
        Usuario usuarioPujador=puja.getUsuarioIdusuario();
        Producto productoPujado=puja.getProductoIdproducto();
          String mensajeAVendedor=text.getString("app.AsuntoMensaje");
          mensajeAVendedor=mensajeAVendedor+text.getString("app.ElProducto")+productoPujado.getNombre()  +text.getString("app.PorElQueHasPujado");
          mensajeAVendedor=mensajeAVendedor+text.getString("app.ProductoPujaBorrado")+"\n";
          

          emailService.envioIndividual(usuarioPujador.getEmail(),text.getString("app.AsuntoProductoPujaBorrado"), mensajeAVendedor);
        
    }
    public void compradoPruductoVentaDirecta(@Observes @CompraVentaDirecta Venta venta ) {

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());            
         
         Usuario usuarioComprador=venta.getCompradorIdusuario();
         Usuario usuarioVendedor= venta.getProductoIdproducto().getUsuarioIdusuario();
         Producto productoComprado=venta.getProductoIdproducto();
        
         //correo al vendedor
          String mensajeAVendedor=text.getString("app.AsuntoMensaje");
          mensajeAVendedor=mensajeAVendedor+text.getString("app.ElProducto")+productoComprado.getNombre()  +text.getString("app.QueTeniasEnVentaDirecta");
          mensajeAVendedor=mensajeAVendedor+text.getString("app.HasidoAdquirido")+usuarioComprador.getNombre()+"\n";
          mensajeAVendedor=mensajeAVendedor+text.getString("app.PonteEnContacto")+usuarioComprador.getEmail()+"\n";
          emailService.envioIndividual(usuarioVendedor.getEmail(),text.getString("app.AsuntoCompraProductoVentaDirecta"), mensajeAVendedor);
          
          //correo al comprador
          String mensajeAComprador=text.getString("app.AsuntoMensaje");
          mensajeAComprador=mensajeAComprador+text.getString("app.AcabasDeAdquirirProducto")+productoComprado.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+text.getString("app.QueTeniaUsuario")+usuarioVendedor.getNombre()+" "+ usuarioVendedor.getApellidos() +text.getString("app.EnNuestroPortal");
          mensajeAComprador=mensajeAComprador+text.getString("app.PonteEnContacto")+usuarioVendedor.getEmail()+"\n";
          emailService.envioIndividual(usuarioComprador.getEmail(),text.getString("app.AsuntoHasCompradoProducto"), mensajeAComprador);

              
    }
    public void realizadaPuja(@Observes @RealizadaPuja Puja puja ) {

        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale()); 
        
       
        
          Usuario usuarioPujador=puja.getUsuarioIdusuario();
          Usuario usuarioVendedor= puja.getProductoIdproducto().getUsuarioIdusuario();
          Producto productoComprado=puja.getProductoIdproducto();
                 //correo al ofertador
          String mensajeAVendedor=text.getString("app.AsuntoMensaje");
          mensajeAVendedor=mensajeAVendedor+text.getString("app.ElProducto")+productoComprado.getNombre()  +text.getString("app.QueTeniasEnSubasta");
          mensajeAVendedor=mensajeAVendedor+text.getString("app.ConValorInicial")+productoComprado.getPrecio()+ "\n";
          mensajeAVendedor=mensajeAVendedor+text.getString("app.AcabaDeRecibirPuja")+usuarioPujador.getNombre()+"\n";
          mensajeAVendedor=mensajeAVendedor+text.getString("app.PorUnValor")+puja.getOferta()+"\n";

          emailService.envioIndividual(usuarioVendedor.getEmail(),text.getString("app.AsuntoPujaRecibida"), mensajeAVendedor);
          
          //correo al pujador
          String mensajeAComprador=text.getString("app.AsuntoMensaje");
          mensajeAComprador=mensajeAComprador+text.getString("app.AcabasDePujar")+productoComprado.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+text.getString("app.PorLaCantidadDe")+puja.getOferta()  +"\n";
          mensajeAComprador=mensajeAComprador+text.getString("app.QueTieneUsuario")+usuarioVendedor.getNombre()+" "+ usuarioVendedor.getApellidos() +" en nuestro portal \n";
          emailService.envioIndividual(usuarioPujador.getEmail(),text.getString("app.AsuntoHasPujado"), mensajeAComprador);
        
    }
    public void expiradoTiempoPuja(@Observes @SubastaExpirada Producto producto ) {
          System.out.println(" en MAILEVENT -expirado tiempo");
          
          
        Locale localeTemp= new Locale("es", "ES");
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", localeTemp);   
        
        
                listaPujas=pujaFacade.pujaXProducto(producto);
        if((listaPujas==null)||(listaPujas.isEmpty())){
         //no ha tenido ninguna puja, se informa a vendedor
         String mensajeAVendedor=text.getString("app.AsuntoMensaje");
         mensajeAVendedor=mensajeAVendedor+text.getString("app.ExpiradoTiempoSubasta")+producto.getNombre()+"\n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.SinPujas");
         
         String asunto;
         asunto= text.getString("app.AsuntoExpiradoTiempoPuja");
         
         localeTemp = new Locale("en", "US");
         text = ResourceBundle.getBundle("utilidades/Bundle", localeTemp); 
         
         mensajeAVendedor=mensajeAVendedor+text.getString("app.AsuntoMensaje");
         mensajeAVendedor=mensajeAVendedor+text.getString("app.ExpiradoTiempoSubasta")+producto.getNombre()+"\n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.SinPujas");
         
         asunto= asunto+text.getString("app.AsuntoExpiradoTiempoPuja");
         emailService.envioIndividual(producto.getUsuarioIdusuario().getEmail(),asunto, mensajeAVendedor);    
        }else{
            //han habido pujas se informa a vendedor y a último pujador
            
            //correo al vendedor
         String mensajeAVendedor=text.getString("app.AsuntoMensaje");
         mensajeAVendedor=mensajeAVendedor+text.getString("app.ExpiradoTiempoSubasta")+producto.getNombre()+"\n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.HaRecibidoEstasPujas");
         for (Puja puja:listaPujas){
              mensajeAVendedor=mensajeAVendedor+text.getString("app.OfertaDia")+puja.getFecha()+text.getString("app.DelUsuario")+puja.getUsuarioIdusuario().getNombre()+" "
                      +puja.getUsuarioIdusuario().getApellidos()+text.getString("app.PorCantidad")+ puja.getOferta()+"\n";
         }
         mensajeAVendedor=mensajeAVendedor+text.getString("app.ComoMaximoPujadorUsuario")+listaPujas.get(0).getUsuarioIdusuario().getNombre()+" \n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.PorLaCantidadDe")+listaPujas.get(0).getOferta()+" \n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.PonteEnContacto")+ listaPujas.get(0).getUsuarioIdusuario().getEmail()+"\n";
         
         String asunto= text.getString("app.AsuntoExpiradoTiempoPuja");
         
         //Cambiamos el locale
          localeTemp = new Locale("en", "US");
          text = ResourceBundle.getBundle("utilidades/Bundle", localeTemp); 
         mensajeAVendedor=mensajeAVendedor+text.getString("app.AsuntoMensaje");
         mensajeAVendedor=mensajeAVendedor+text.getString("app.ExpiradoTiempoSubasta")+producto.getNombre()+"\n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.HaRecibidoEstasPujas");
         for (Puja puja:listaPujas){
              mensajeAVendedor=mensajeAVendedor+text.getString("app.OfertaDia")+puja.getFecha()+text.getString("app.DelUsuario")+puja.getUsuarioIdusuario().getNombre()+" "
                      +puja.getUsuarioIdusuario().getApellidos()+text.getString("app.PorCantidad")+ puja.getOferta()+"\n";
         }
         mensajeAVendedor=mensajeAVendedor+text.getString("app.ComoMaximoPujadorUsuario")+listaPujas.get(0).getUsuarioIdusuario().getNombre()+" \n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.PorLaCantidadDe")+listaPujas.get(0).getOferta()+" \n";
         mensajeAVendedor=mensajeAVendedor+text.getString("app.PonteEnContacto")+ listaPujas.get(0).getUsuarioIdusuario().getEmail()+"\n";
         
         asunto= asunto+text.getString("app.AsuntoExpiradoTiempoPuja");
         
         emailService.envioIndividual(producto.getUsuarioIdusuario().getEmail(),asunto, mensajeAVendedor); 
            //correo el comprador
         
         localeTemp= new Locale("es", "ES");
         text = ResourceBundle.getBundle("utilidades/Bundle", localeTemp); 
          String mensajeAComprador=text.getString("app.AsuntoMensaje");
          mensajeAComprador=mensajeAComprador+text.getString("app.AcabasDeAdquirirProducto")+producto.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+text.getString("app.QueTenidaModoSubasta")+producto.getUsuarioIdusuario().getNombre()+" "+producto.getUsuarioIdusuario().getApellidos()+text.getString("app.EnNuestroPortal");
          mensajeAComprador=mensajeAComprador+text.getString("app.PorLaCantidadDe") +  listaPujas.get(0).getOferta()+" \n";
          mensajeAComprador=mensajeAComprador+text.getString("app.PonteEnContacto")+producto.getUsuarioIdusuario().getEmail()+"\n";
          
          asunto =text.getString("app.AsuntoHasCompradoProducto");
          localeTemp = new Locale("en", "US");
          text = ResourceBundle.getBundle("utilidades/Bundle", localeTemp); 
          mensajeAComprador=mensajeAComprador+text.getString("app.AsuntoMensaje");
          mensajeAComprador=mensajeAComprador+text.getString("app.AcabasDeAdquirirProducto")+producto.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+text.getString("app.QueTenidaModoSubasta")+producto.getUsuarioIdusuario().getNombre()+" "+producto.getUsuarioIdusuario().getApellidos()+text.getString("app.EnNuestroPortal");
          mensajeAComprador=mensajeAComprador+text.getString("app.PorLaCantidadDe") +  listaPujas.get(0).getOferta()+" \n";
          mensajeAComprador=mensajeAComprador+text.getString("app.PonteEnContacto")+producto.getUsuarioIdusuario().getEmail()+"\n";
          
          asunto =asunto+text.getString("app.AsuntoHasCompradoProducto");
          emailService.envioIndividual(listaPujas.get(0).getUsuarioIdusuario().getEmail(),asunto, mensajeAComprador);
         
            
        }
    
        
        
        
        
        
        
        
        
        
        /*
           listaPujas=pujaFacade.pujaXProducto(producto);
        if((listaPujas==null)||(listaPujas.isEmpty())){
         //no ha tenido ninguna puja, se informa a vendedor
         String mensajeAVendedor="información desde BuyUp \n";
         mensajeAVendedor=mensajeAVendedor+"Ha expirado el tiempo de permanencia en modo subasta del producto "+producto.getNombre()+"\n";
         mensajeAVendedor=mensajeAVendedor+"y no ha recibido ninguna puja.";
         emailService.envioIndividual(producto.getUsuarioIdusuario().getEmail(),"expirado tiempo en subasta de uno de tus productos en BuyUp", mensajeAVendedor);    
        }else{
            //han habido pujas se informa a vendedor y a último pujador
            
            //correo al vendedor
         String mensajeAVendedor="información desde BuyUp \n";
         mensajeAVendedor=mensajeAVendedor+"Ha expirado el tiempo de permanencia en modo subasta del producto "+producto.getNombre()+"\n";
         mensajeAVendedor=mensajeAVendedor+"y ha recibido las siguientes pujas:\n";
         for (Puja puja:listaPujas){
              mensajeAVendedor=mensajeAVendedor+"oferta el dia "+puja.getFecha()+" del usuario "+puja.getUsuarioIdusuario().getNombre()+" "
                      +puja.getUsuarioIdusuario().getApellidos()+" por un valor de "+ puja.getOferta()+"\n";
         }
         mensajeAVendedor=mensajeAVendedor+"como máximo pujador se ha concedido al usuario "+listaPujas.get(0).getUsuarioIdusuario().getNombre()+" \n";
         mensajeAVendedor=mensajeAVendedor+"por la cantidad de "+listaPujas.get(0).getOferta()+" \n";
         mensajeAVendedor=mensajeAVendedor+"Ponte en contacto con él para gestionar pago y envio en el correo "+ listaPujas.get(0).getUsuarioIdusuario().getEmail()+"\n";
         
         
         emailService.envioIndividual(producto.getUsuarioIdusuario().getEmail(),"expirado tiempo en subasta de uno de tus productos en BuyUp", mensajeAVendedor); 
            //correo el comprador
          String mensajeAComprador="información desde BuyUp \n";
          mensajeAComprador=mensajeAComprador+"Acabas de adquirir el producto "+producto.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+"Que tenía en modo subasta el usuario  "+producto.getUsuarioIdusuario().getNombre()+" "+producto.getUsuarioIdusuario().getApellidos()+" en nuestro portal \n";
          mensajeAComprador=mensajeAComprador+"por la cantidad de: " +  listaPujas.get(0).getOferta()+" \n";
          mensajeAComprador=mensajeAComprador+"Ponte en contacto con él para gestionar pago y envio en el correo  "+producto.getUsuarioIdusuario().getEmail()+"\n";
          emailService.envioIndividual(listaPujas.get(0).getUsuarioIdusuario().getEmail(),"Has adquirido un producto en BuyUp", mensajeAComprador);
         
            
        }
    
          */
        

        
    }
    public void realiadaDenuncia(@Observes @RealizadaDenuncia Denuncia denuncia ) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());             
         Usuario usuarioDenunciado=denuncia.getVentaIdventa().getProductoIdproducto().getUsuarioIdusuario();
         Usuario usuarioDenunciante= denuncia.getDenunciaIdusuario();
         Producto productoDenunciado=denuncia.getVentaIdventa().getProductoIdproducto();
        
         //correo al denunciado
          String mensajeADenunciado=text.getString("app.AsuntoMensaje");
          mensajeADenunciado=mensajeADenunciado+text.getString("app.DenunciaRecibida")+productoDenunciado.getNombre()  +text.getString("app.RealizadaDia")+denuncia.getVentaIdventa().getFecha()+"\n";
          mensajeADenunciado=mensajeADenunciado+text.getString("app.TipoDenuncia")+denuncia.getTipoDenuncia()+"\n";
          mensajeADenunciado=mensajeADenunciado+text.getString("app.MotivoDenuncia")+denuncia.getMotivo()+"\n";
          mensajeADenunciado=mensajeADenunciado+text.getString("app.PeticionContactoDenunciante")+usuarioDenunciante.getEmail()+"\n";
          emailService.envioIndividual(usuarioDenunciado.getEmail(),text.getString("app.AsuntoDenunciaDenunciado"), mensajeADenunciado);
          
          //correo al denunciante
          String mensajeADenunciante=text.getString("app.AsuntoMensaje");
          mensajeADenunciante=mensajeADenunciante+text.getString("app.AcabasDeDenunciar")+productoDenunciado.getNombre()  +"\n";
          mensajeADenunciante=mensajeADenunciante+text.getString("app.TipoDenuncia")+denuncia.getTipoDenuncia()+"\n";
          mensajeADenunciante=mensajeADenunciante+text.getString("app.MotivoDenuncia")+denuncia.getMotivo()+"\n";
          mensajeADenunciante=mensajeADenunciante+text.getString("app.PeticionContactoDenunciado")+usuarioDenunciado.getEmail()+"\n";
          emailService.envioIndividual(usuarioDenunciante.getEmail(),text.getString("app.AsuntoDenunciaDenunciante"), mensajeADenunciante);
    }    
    public void borradoPruductoPorImprocedente(@Observes @BorradoProductoPorImprocedente Producto producto ) {
        
         
         Usuario usuario=producto.getUsuarioIdusuario();
         
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());            
        
         //correo al vendedor
          String mensajeAUsuario=text.getString("app.AsuntoMensaje");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.ElProducto")+producto.getNombre()  +text.getString("app.ALaVenta");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.BorradoPorInanpropiado");
         emailService.envioIndividual(usuario.getEmail(),text.getString("app.AsuntoBorradoPorInapropiado"), mensajeAUsuario);
          
  
              
    }    
    public void usuarioBloqueado(@Observes @UsuarioBloqueado Usuario usuario ) {
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());            
         //correo al usuario
          String mensajeAUsuario=text.getString("app.AsuntoMensaje");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.HasSidoBloqueado");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.ParaVolverAEntrar");
         emailService.envioIndividual(usuario.getEmail(),text.getString("app.AsuntoBloqueado"), mensajeAUsuario);
    }   
    
    public void usuarioRedimido(@Observes @UsuarioRedimido Usuario usuario ) {
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());            
         //correo al usuario
          String mensajeAUsuario=text.getString("app.AsuntoMensaje");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.Redimido");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.YaPuedesEntrar");
         emailService.envioIndividual(usuario.getEmail(),text.getString("app.AsuntoRedimido"), mensajeAUsuario);
    }    
    public void usuarioBorrado(@Observes @UsuarioBorrado Usuario usuario ) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("utilidades/Bundle", context.getViewRoot().getLocale());            
         //correo al usuario
          String mensajeAUsuario=text.getString("app.AsuntoMensaje");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.BorradoUsuarioPorAdmin");
          mensajeAUsuario=mensajeAUsuario+text.getString("app.DejasDeSerUsuario");
         emailService.envioIndividual(usuario.getEmail(),text.getString("app.AsuntoBorradoComoUsuario"), mensajeAUsuario);
    }     
}
