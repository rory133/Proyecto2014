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
import facade.LoginFacade;
import facade.PujaFacade;
import facade.UsuarioFacade;
import facade.VentaFacade;
import java.io.Serializable;
import java.util.List;
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
private PujaFacade pujaFacade;

@EJB
private VentaFacade ventaFacade;

private List<Puja> listaPujas;

  //captura el evento que se ha producido al crear un nuevo socio
    public void sumadoUsuario(@Observes @SumadoSocio Login login ) {
        
         Usuario usuario= usuarioFacade.find(login.getUsuarioIdusuario().getIdusuario());
        
          String bienVenida="Bienvenido al portal BuyUp \n"+usuario.getNombre()+" "+usuario.getApellidos()+"\n";
          bienVenida=bienVenida+"has sido dado de alta como usuario \n con el login:"+ login.getLogin()+"\n";
          bienVenida=bienVenida+"y el password: "+login.getPassword();
          
          
          emailService.envioIndividual(usuario.getEmail(),"Bienvenid@ a BuyUp", bienVenida);

              
    }
    public void borradoProductoConPujaUsuario(@Observes @BorradoProductoConPujas Puja puja){
        Usuario usuarioPujador=puja.getUsuarioIdusuario();
        Producto productoPujado=puja.getProductoIdproducto();
          String mensajeAVendedor="información desde BuyUp \n";
          mensajeAVendedor=mensajeAVendedor+"El producto "+productoPujado.getNombre()  +" por el que habias pujado en nuestro portal \n";
          mensajeAVendedor=mensajeAVendedor+"acaba de ser borrado por el Usuario que lo había puesto a la venta "+"\n";
          

          emailService.envioIndividual(usuarioPujador.getEmail(),"Borrado producto por el que habias pujado en BuyUp", mensajeAVendedor);
        
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
          emailService.envioIndividual(usuarioVendedor.getEmail(),"Compra de un producto tuyo en BuyUp", mensajeAVendedor);
          
          //correo al comprador
          String mensajeAComprador="información desde BuyUp \n";
          mensajeAComprador=mensajeAComprador+"Acabas de adquirir el producto "+productoComprado.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+"Que tenía a la venta el usuario  "+usuarioVendedor.getNombre()+" "+ usuarioVendedor.getApellidos() +" en nuestro portal \n";
          mensajeAComprador=mensajeAComprador+"Ponte en contacto con él para gestionar pago y envio en el correo  "+usuarioVendedor.getEmail()+"\n";
          emailService.envioIndividual(usuarioComprador.getEmail(),"Has comprado un producto en BuyUp", mensajeAComprador);

              
    }
    public void realizadaPuja(@Observes @RealizadaPuja Puja puja ) {
        
          Usuario usuarioPujador=puja.getUsuarioIdusuario();
          Usuario usuarioVendedor= puja.getProductoIdproducto().getUsuarioIdusuario();
          Producto productoComprado=puja.getProductoIdproducto();
                 //correo al ofertador
          String mensajeAVendedor="información desde BuyUp \n";
          mensajeAVendedor=mensajeAVendedor+"El producto "+productoComprado.getNombre()  +" que tenias en la modalidad de subasta en nuestro portal \n";
          mensajeAVendedor=mensajeAVendedor+"con un valor inicial de: "+productoComprado.getPrecio()+ "\n";
          mensajeAVendedor=mensajeAVendedor+"acaba de recibir una puja del usuario "+usuarioPujador.getNombre()+"\n";
          mensajeAVendedor=mensajeAVendedor+"por un valor de "+puja.getOferta()+"\n";

          emailService.envioIndividual(usuarioVendedor.getEmail(),"Puja recibida por uno producto tuyo en BuyUp", mensajeAVendedor);
          
          //correo al pujador
          String mensajeAComprador="información desde BuyUp \n";
          mensajeAComprador=mensajeAComprador+"Acabas de pujar por el producto "+productoComprado.getNombre()  +"\n";
          mensajeAComprador=mensajeAComprador+"Por la cantidad de "+puja.getOferta()  +"\n";
          mensajeAComprador=mensajeAComprador+"Que tiene a la venta el usuario  "+usuarioVendedor.getNombre()+" "+ usuarioVendedor.getApellidos() +" en nuestro portal \n";
          emailService.envioIndividual(usuarioPujador.getEmail(),"Has pujado por un producto en BuyUp", mensajeAComprador);
        
    }
    public void expiradoTiempoPuja(@Observes @SubastaExpirada Producto producto ) {
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
    
        
        
    }
    public void realiadaDenuncia(@Observes @RealizadaDenuncia Denuncia denuncia ) {
        
         
         Usuario usuarioDenunciado=denuncia.getVentaIdventa().getProductoIdproducto().getUsuarioIdusuario();
         Usuario usuarioDenunciante= denuncia.getDenunciaIdusuario();
         Producto productoDenunciado=denuncia.getVentaIdventa().getProductoIdproducto();
        
         //correo al denunciado
          String mensajeADenunciado="información desde BuyUp \n";
          mensajeADenunciado=mensajeADenunciado+"has recibido una denuncia por la venta del producto "+productoDenunciado.getNombre()  +" realizada el dia "+denuncia.getVentaIdventa().getFecha()+"\n";
          mensajeADenunciado=mensajeADenunciado+"por la causa "+denuncia.getTipoDenuncia()+"\n";
          mensajeADenunciado=mensajeADenunciado+"y el motivo siguiente \n"+denuncia.getMotivo()+"\n";
          mensajeADenunciado=mensajeADenunciado+"Ponte en contacto con el usuario denunciante para aclarar el problema en el correo  "+usuarioDenunciante.getEmail()+"\n";
          emailService.envioIndividual(usuarioDenunciado.getEmail(),"recibida denuncia en la venta de uno de tus productos en BuyUp", mensajeADenunciado);
          
          //correo al denunciante
          String mensajeADenunciante="información desde BuyUp \n";
          mensajeADenunciante=mensajeADenunciante+"Acabas de realizar una denuncia por la venta del producto "+productoDenunciado.getNombre()  +"\n";
          mensajeADenunciante=mensajeADenunciante+"por la causa "+denuncia.getTipoDenuncia()+"\n";
          mensajeADenunciante=mensajeADenunciante+"y el motivo siguiente \n"+denuncia.getMotivo()+"\n";
          mensajeADenunciante=mensajeADenunciante+"Puedes ponerte en contacto con el usuario denunciado para aclarar el problema en el correo  "+usuarioDenunciado.getEmail()+"\n";
          emailService.envioIndividual(usuarioDenunciante.getEmail(),"has realizado una denuncia por problemas en la venta de un producto en BuyUp", mensajeADenunciante);
    }    
    public void borradoPruductoPorImprocedente(@Observes @BorradoProductoPorImprocedente Producto producto ) {
        
         
         Usuario usuario=producto.getUsuarioIdusuario();
         
        
        
         //correo al vendedor
          String mensajeAUsuario="información desde BuyUp \n";
          mensajeAUsuario=mensajeAUsuario+"El producto "+producto.getNombre()  +" que tenias a la venta en nuestro portal \n";
          mensajeAUsuario=mensajeAUsuario+"acaba de ser borrado por resultar inapropiado para el portal \n";
         emailService.envioIndividual(usuario.getEmail(),"borrado por inapropiado uno de tus productos en BuyUp", mensajeAUsuario);
          
  
              
    }    
    
}
