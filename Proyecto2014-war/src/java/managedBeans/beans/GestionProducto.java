/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;

import ejbs.GestionEventos;
import entidades.Denuncia;
import entidades.Producto;
import entidades.Puja;
import entidades.Usuario;
import entidades.Venta;
import facade.ProductoFacade;
import facade.PujaFacade;
import facade.UsuarioFacade;
import facade.VentaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import utilidades.Loggable;

/**
 *
 * @author juanma
 */
@Loggable
@ViewScoped
//@RequestScoped
//@SessionScoped
@ManagedBean(name="gestionProductoBean")
public class GestionProducto implements Serializable{

    /**
     * Creates a new instance of GestionProducto
     */
    @EJB
    private ProductoFacade productoFacade;

    @EJB
    private VentaFacade ventaFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    GestionEventos gestionEventos;
    
    @EJB
    private PujaFacade pujaFacade;
    
    private List<Puja> pujasProducto ;
    
    private FacesMessage facesMessage;
    private final FacesContext faceContext;
    
    private Producto producto;
    private Venta venta;
    private Puja puja;
    private Usuario usuario;
    private float pujaPropuesta;

    public VentaFacade getVentaFacade() {
        return ventaFacade;
    }

    public void setVentaFacade(VentaFacade ventaFacade) {
        this.ventaFacade = ventaFacade;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public float getPujaPropuesta() {
        return pujaPropuesta;
    }

    public void setPujaPropuesta(float pujaPropuesta) {
        this.pujaPropuesta = pujaPropuesta;
    }
    
    
    
    
    
     
    
    
    
    public GestionProducto() {   
         faceContext=FacesContext.getCurrentInstance();
    }
    
    public String compraProducto(){
        //recogemos los parametros necesarios
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         session.getServletContext().getAttribute("idUsuarioComprador");
        
                 //id del usuario que compra el producto
                 String idUsuario = (String) facesContext.getExternalContext().getRequestParameterMap().get("idUsuarioComprador");
                 System.out.println("usuario comprador getCurrentInstance con Map: "+idUsuario);
                //id del producto a comprar
                 String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("idProductoAComprar");
                  System.out.println("producto a comprar antes de buscarlo con Map: "+idProducto);
                 
 
         
         //usuario que compra el producto
           Usuario usuarioComprador = usuarioFacade.find((Integer)Integer.parseInt(idUsuario));
         //producto a comprar
           Producto productoAComprar=productoFacade.find((Integer)Integer.parseInt(idProducto));
         
        System.out.println("usuario comprador: "+productoAComprar.getNombre());
        System.out.println("producto a comprar antes de buscarlo: "+usuarioComprador.getNombre());
        
       //usuario que oferta el producto
        Usuario usuarioProducto=(Usuario)productoAComprar.getUsuarioIdusuario();
        //un usuario no podrá compar sus propios pructos, se comprueba si esto ocurre
        if(usuarioComprador.equals(usuarioProducto)){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"NO PUEDES COMPRAR UNO DE TUS PRODUCTOS","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("producto a comprar: "+productoAComprar.getNombre());
            System.out.println("mismo usuario comprador y ofertador");
            //faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return "index";
        }
            venta=new Venta();
            venta.setCompradorIdusuario(usuarioComprador);
            venta.setDenunciaList(new ArrayList<Denuncia>());
            venta.setEnviado(false);
            venta.setFecha(new java.util.Date(System.currentTimeMillis()));
            venta.setPrecioFinal(productoAComprar.getPrecio());
            venta.setProductoIdproducto(productoAComprar);
            venta.setRecibido(false);
            ventaFacade.create(venta);
            productoAComprar.setVendido(true);
            productoFacade.salva(productoAComprar);
                    
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"has comprado el producto "+productoAComprar.getNombre()+".","");
            FacesContext.getCurrentInstance().addMessage(null, message);
             gestionEventos.fireProductoAdquiridoVentaDirecta(venta);
            return "index.xhtml?faces-redirect=true";
    }
    
    public String pujaXProducto(){
        //recogemos los parametros necesarios
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         session.getServletContext().getAttribute("idUsuarioComprador");
        
                 //id del usuario que compra el producto
                 String idUsuario = (String) facesContext.getExternalContext().getRequestParameterMap().get("idUsuarioComprador");
                 System.out.println("usuario pujador getCurrentInstance con Map: "+idUsuario);
                //id del producto a comprar
                 String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("idProductoAComprar");
                  System.out.println("producto a pujar antes de buscarlo con Map: "+idProducto);
                 
 
         
         //usuario que compra el producto
           Usuario usuarioPujador = usuarioFacade.find((Integer)Integer.parseInt(idUsuario));
         //producto a comprar
           Producto productoAPujar=productoFacade.find((Integer)Integer.parseInt(idProducto));
         
        System.out.println("producto a Pujar: "+productoAPujar.getNombre());
        System.out.println("producto a comprar antes de buscarlo: "+usuarioPujador.getNombre());
        
       //usuario que oferta el producto
        Usuario usuarioProducto=(Usuario)productoAPujar.getUsuarioIdusuario();
        //un usuario no podrá compar sus propios pructos, se comprueba si esto ocurre
        if(usuarioPujador.equals(usuarioProducto)){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"NO PUEDES PUJAR POR UNO DE TUS PRODUCTOS","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("producto a comprar: "+productoAPujar.getNombre());
            System.out.println("mismo usuario comprador y ofertador");
            //faceContext.getExternalContext().getFlash().setKeepMessages(true);
            return "index";
        }
          pujasProducto=pujaFacade.pujaXProducto(productoAPujar);
          
          if((pujasProducto==null)||(pujasProducto.isEmpty())){//NO HABIA PUJAS
              if(pujaPropuesta>=productoAPujar.getPrecio()){
                  puja=new Puja();
                  puja.setFecha(new java.util.Date(System.currentTimeMillis()));
                  puja.setOferta(pujaPropuesta);
                  puja.setProductoIdproducto(productoAPujar);
                  puja.setUsuarioIdusuario(usuarioPujador);
                  pujaFacade.create(puja);
                  System.out.println("Creada primara puja del producto: "+productoAPujar.getNombre());
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"ACABAS DE PUJAR POR EL PRODUCTO "+productoAPujar.getNombre(),"");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        gestionEventos.firePujaRealizada(puja);
                        return "index";  

              }else{
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"EN LA PRIMERA PUJA AL MENOS TIENE QUE IGUARLAR EL PRECIO DE SALIDA","");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return "index";                 
              }
              
          }else{
               if(pujaPropuesta>productoAPujar.getUltimaPuja()){
                  puja=new Puja();
                  puja.setFecha(new java.util.Date(System.currentTimeMillis()));
                  puja.setOferta(pujaPropuesta);
                  puja.setProductoIdproducto(productoAPujar);
                  puja.setUsuarioIdusuario(usuarioPujador);
                  pujaFacade.create(puja);
                  System.out.println("Creada puja por producto : "+productoAPujar.getNombre());
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"ACABAS DE PUJAR POR EL PRODUCTO "+productoAPujar.getNombre(),"");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        gestionEventos.firePujaRealizada(puja);
                        return "index";  

              }else{
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"TIENES QUE PUJAR POR UN VALOR SUPERIOR AL ACTUAL: "+productoAPujar.getUltimaPuja(),"");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        return "index.xhtml?faces-redirect=true";                 
                            
                    }

            
          }
                    
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"has pujado por el producto "+productoAPujar.getNombre()+".","");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//            // gestionEventos.fireProductoAdquiridoVentaDirecta(venta);
//            return "index.xhtml?faces-redirect=true";
    }
          
    
}
