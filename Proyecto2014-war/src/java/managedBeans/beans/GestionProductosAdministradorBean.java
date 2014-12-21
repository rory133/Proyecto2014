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
import entidades.Categoria;
import entidades.Denuncia;
import entidades.Producto;
import entidades.Puja;
import entidades.Venta;
import facade.CategoriaFacade;
import facade.DenunciaFacade;
import facade.ImagenFacade;
import facade.ProductoFacade;
import facade.PujaFacade;
import facade.VentaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import utilidades.DatosProductoCompleto;

/**
 *
 * @author juanma
 */
@ManagedBean(name = "gestionProductosAdministradorView")

@SessionScoped
public class GestionProductosAdministradorBean implements Serializable {

    @EJB
private ProductoFacade productoFacade;

@EJB
private ImagenFacade imagenFacade;


@EJB
private CategoriaFacade categoriaFacade;

@EJB
private VentaFacade ventaFacade;

@EJB
private DenunciaFacade denunciaFacade;

@EJB
GestionEventos gestionEventos;
@EJB
private PujaFacade pujaFacade;

private HttpSession session;  

private FacesMessage facesMessage;
private FacesContext facesContext;

private List<Producto> listaProductos;

private Venta ventaDelProducto;

private String filtro;//todos, ventaDirecta, subasta

private String vendidos;//todos, yaVendidos, noVendidos

private String denunciados;//todos, malClasificado, FaltaPago, faltaEnvio


private DatosProductoCompleto datosProductoCompleto;

private List<DatosProductoCompleto>listaProductosCompletos;

private DatosProductoCompleto datosProductoCompletoSeleccionado;

private List<Denuncia>denunciasSeleccionadas;

private List<Puja>pujasSeleccionadas;

private boolean viendoCategorias;


///////////////////////////////////////////////
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
    
        this.filtro = filtro;
    }

    public String getVendidos() {
         
        return vendidos;
    }

    public void setVendidos(String vendidos) {
 
        this.vendidos = vendidos;
    }

    public Venta getVentaDelProducto() {
        return ventaDelProducto;
    }

    public void setVentaDelProducto(Venta ventaDelProducto) {
        this.ventaDelProducto = ventaDelProducto;
    }

    public DatosProductoCompleto getDatosProductoCompleto() {
        return datosProductoCompleto;
    }

    public void setDatosProductoCompleto(DatosProductoCompleto datosProductoCompleto) {
        this.datosProductoCompleto = datosProductoCompleto;
    }

    public List<DatosProductoCompleto> getListaProductosCompletos() {
        return listaProductosCompletos;
    }

    public void setListaProductosCompletos(List<DatosProductoCompleto> listaProductosCompletos) {
        this.listaProductosCompletos = listaProductosCompletos;
    }

    public DatosProductoCompleto getDatosProductoCompletoSeleccionado() {
        return datosProductoCompletoSeleccionado;
    }

    public void setDatosProductoCompletoSeleccionado(DatosProductoCompleto datosProductoCompletoSeleccionado) {
         this.datosProductoCompletoSeleccionado = datosProductoCompletoSeleccionado;
    }

    public List<Denuncia> getDenunciasSeleccionadas() {
        return denunciasSeleccionadas;
    }

    public void setDenunciasSeleccionadas(List<Denuncia> denunciasSeleccionadas) {
        this.denunciasSeleccionadas = denunciasSeleccionadas;
    }

    public List<Puja> getPujasSeleccionadas() {
        return pujasSeleccionadas;
    }

    public void setPujasSeleccionadas(List<Puja> pujasSeleccionadas) {
        this.pujasSeleccionadas = pujasSeleccionadas;
    }

    public String getDenunciados() {
        return denunciados;
    }

    public void setDenunciados(String denunciados) {
        this.denunciados = denunciados;
    }

    public boolean isViendoCategorias() {
        return viendoCategorias;
    }

    public void setViendoCategorias(boolean viendoCategorias) {
        System.out.println(" cambio viendoCategorias: "+viendoCategorias);
        this.viendoCategorias = viendoCategorias;
    }


    
    
    
    
////////////////////////////////////////////////
    public GestionProductosAdministradorBean() {

    }
    
    @PostConstruct
    public void init() {
         facesContext=FacesContext.getCurrentInstance();
       setVendidos("todos");
       setFiltro("todos");
       setDenunciados("todos");
      // setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
       actualizaListaProductosCompletos();
       setViendoCategorias(false);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("idCategoria", -1);       
       
    }
    
    public void actualizaListadoProductos(){

        setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
    }
    
    public String actualizaListaProductosCompletos(){
        //datosProductoCompleto=new  ArrayList<>();
        
        
        
        List<DatosProductoCompleto> listaProductosCompletos2  = new ArrayList<>();
        setListaProductosCompletos(null);
        List<Producto> listaProductos2=new ArrayList<>();
        listaProductos2=productoFacade.todosProductosFiltrados(filtro, vendidos);
      //  setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
        
         System.out.println("@@tamaño listaProductos2: "+listaProductos2.size());
        for(Producto producto: listaProductos2){
            DatosProductoCompleto datosProductoCompleto2= new  DatosProductoCompleto();
            System.out.println("WWWWAñadimos producto : "+producto.getNombre());
            System.out.println("WWWWW tiene marca de mal clasificado : "+producto.isMarcadoMalClasificado());
            System.out.println("WWWWW expirado : "+producto.isExpirado());
            datosProductoCompleto2.setProducto(producto);
            datosProductoCompleto2.setImagenes(imagenFacade.imagenesXProcducto(producto));
           
            
            
            if(producto.getVendido()){
                System.out.println("@@AbuscoVentas del producto "+ producto.getNombre()+" vendido "+producto.getVendido());
                datosProductoCompleto2.setVenta(ventaFacade.ventaXProducto(producto));
                 System.out.println("@@datos ventaSUMADA "+datosProductoCompleto2.getVenta().getIdventa());
            }else{
                datosProductoCompleto2.setVenta(null);
            }
            if(producto.getEnSubasta()){
                datosProductoCompleto2.setPujas(pujaFacade.pujaXProducto(producto));
            }else{
                datosProductoCompleto2.setPujas(null);
            }
            if (datosProductoCompleto2.getVenta()!=null){
                datosProductoCompleto2.setDenuncias(denunciaFacade.denunciaXVenta(datosProductoCompleto2.getVenta()));
            }else{
                datosProductoCompleto2.setDenuncias(null);
            }
            
            
            if(denunciados.equals("todos")) {
                 System.out.println("@@añadimos producto:"+ datosProductoCompleto2.getProducto().getNombre()+ "denuncia mal clasificado? "+datosProductoCompleto2.getProducto().isMarcadoMalClasificado());
                listaProductosCompletos2.add(datosProductoCompleto2);
            }else if(denunciados.equals("malClasificado")) { 
                if(datosProductoCompleto2.getProducto().isMarcadoMalClasificado()){
                    listaProductosCompletos2.add(datosProductoCompleto2);
                }
                
            }else if(denunciados.equals("faltaPago")) {
                if(datosProductoCompleto2.getVenta()!=null && compruebaDenuciaActiva(datosProductoCompleto2.getVenta().getDenunciaList(),"NO_PAGADO"))
                    
                    listaProductosCompletos2.add(datosProductoCompleto2);
                
            }else if(denunciados.equals("faltaEnvio")) {
                if(datosProductoCompleto2.getVenta()!=null && compruebaDenuciaActiva(datosProductoCompleto2.getVenta().getDenunciaList(),"NO_ENVIADO")){
                    
                    listaProductosCompletos2.add(datosProductoCompleto2);
                
            }
            
            
            System.out.println("@@tamaño listaProductosCompletos2: "+listaProductosCompletos2.size());
        }
          System.out.println("@@tamaño FINAL listaProductosCompletos2: "+listaProductosCompletos2.size());
          

         
          
          
          setListaProductosCompletos(listaProductosCompletos2);
        
       }
        return "index.xhtml?faces-redirect=true";
    }
    public boolean compruebaDenuciaActiva(List<Denuncia> denuncias, String tipo){
        for(Denuncia denuncia:denuncias){
            if (!denuncia.getAtendida()&& denuncia.getTipoDenuncia().equals(tipo)){
                return true;
            }
        }
        return false;
    }
    
    public void encuentraVentaProducto(Producto producto){
        
       setVentaDelProducto(ventaFacade.ventaXProducto(producto));
        
    }
    public String borrarProducto(){
                            //recogemos los parametros necesarios
         System.out.println("#######borrarProducto");
        facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("productoABorrar");
             System.out.println("#######id producto a borrar: "+idProducto);
          //producto a salvar
           Producto productoABorrar=productoFacade.find((Integer)Integer.parseInt(idProducto));
            
                  
                  productoFacade.remove(productoABorrar);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"ACABAS DE BORRAR EL PRODUCTO "+productoABorrar.getNombre(),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);
                   FacesContext context = FacesContext.getCurrentInstance();
                   context.getExternalContext().getFlash().setKeepMessages(true);   
        return "index.xhtml?faces-redirect=true";
    }
    public String borrarProductoPorImprocedente(){
                            //recogemos los parametros necesarios
         System.out.println("#######borrarProducto");
        facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("productoABorrar");
             System.out.println("#######id producto a borrar: "+idProducto);
          //producto a salvar
           Producto productoABorrar=productoFacade.find((Integer)Integer.parseInt(idProducto));
            
                  gestionEventos.fireBorradoProductoPorImprocedenteEvent(productoABorrar);
                  productoFacade.remove(productoABorrar);
                  
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"ACABAS DE BORRAR EL PRODUCTO "+productoABorrar.getNombre(),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);
                   FacesContext context = FacesContext.getCurrentInstance();
                   context.getExternalContext().getFlash().setKeepMessages(true);   
        return "index.xhtml?faces-redirect=true";
    }
    public String atenderDenunciaMalClasificado(){
        
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("productoAEliminarMarca");
          System.out.println("######atenderDenunciaMalClasificado"+idProducto);
         Producto productoMalClasificado=productoFacade.find((Integer)Integer.parseInt(idProducto));
          
       
         
         productoMalClasificado.setMarcadoMalClasificado(false);
         productoFacade.salva(productoMalClasificado);
         

//        gestionEventos.fireRealizadaDenuncia(denuncia);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha eliminado la marca de mal clasificado del producto "+productoMalClasificado.getNombre() ,"");
        FacesContext.getCurrentInstance().addMessage(null, message);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);   
        return "index.xhtml?faces-redirect=true";
        
    }
    public String cambiarCategoriaAProducto(){
        
        facesContext = FacesContext.getCurrentInstance();
        session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Integer categoriaNueva=(Integer)session.getAttribute("idCategoria");
        System.out.println(" entro en cambiar Categoria: "+ categoriaNueva);
//        System.out.println(" categoria padre seleccionada "+categoriaSeleccionadaPadreNuevaCategoria);
        if (categoriaNueva < 0) {
           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES SELECCIONAR UNA CATEGORIA ","SELECCIONA UNA EN EL PANEL SUPERIOR");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);            
            System.out.println(" sin categoria Seleccionada ");
            return "index.xhtml?faces-redirect=true";
        }else{
            System.out.println(" categoriaSeleccinoada "+categoriaNueva);
            Categoria categoria=categoriaFacade.find(categoriaNueva);
           String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("productoACambiarCategoria");
            
          //producto a salvar
           Producto productoACambiarCategoria=productoFacade.find((Integer)Integer.parseInt(idProducto));
           
           productoACambiarCategoria.setCategoriaIdcategoria(categoria);
           productoFacade.salva(productoACambiarCategoria);
            

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("idCategoria", -1);
            setViendoCategorias(false);
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"salvado producto "," exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
                return "index.xhtml?faces-redirect=true";
            
            
        }
        
    }   
    
    
//    public void cerrarDialogoDetallesProducto(){   
//      RequestContext.getCurrentInstance().closeDialog("detallesProducto");
//   }
//    public void abreDialogoCategorias() {
//        cerrarDialogoDetallesProducto();
//      Map<String, Object> options = new HashMap<>();
//      options.put("contentHeight", "'100%'");
//      options.put("contentWidth", "'100%'");
//      options.put("height", "170");
//      options.put("width", "500");
//      options.put("modal", true);
//      
//      RequestContext.getCurrentInstance().openDialog("categoriasParaCambiar", options,
//            null);
//
//   }
}
