/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */
package managedBeans.beans;

import entidades.Imagen;
import entidades.Producto;
import facade.ImagenFacade;
import facade.ProductoFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBeans.utilidades.ResourcesUtil;

/**
 *
 * @author juanma
 */
@ManagedBean
@SessionScoped
public class ListadoProductos {

@EJB
private ProductoFacade productoFacade;

@EJB
private ImagenFacade imagenFacade;

private Imagen imagen;

private Producto producto;
private List<Imagen> imagenesProducto;
private List<Producto> listaProductos;
private FacesContext faceContext;
private String filtro;
private String nombreBuscado;
private boolean buscandoPorNombre;

    public ListadoProductos() {
        }
    @PostConstruct
    public void init() {
//    public void iniciar() {
       faceContext=FacesContext.getCurrentInstance();
       setBuscandoPorNombre(false);
       setFiltro("todos");
    }

    public void seleccionaProductos(){
        setListaProductos(productoFacade.findAll());
    }
    public void todosLosProductos(){
             System.out.println(" en todos los productos con filtro"+ filtro);
        setListaProductos(productoFacade.todosProductosXFiltro(filtro));

    }
    public void buscar(){
        if(isBuscandoPorNombre()){
            System.out.println(" en buscar-por nombre ");
            buscaXNombre();
        }
        else{
             System.out.println(" en buscar-todos ");
            setNombreBuscado("");
            setBuscandoPorNombre(false);
            todosLosProductos();
        }
        
    }
    
    public void buscaXNombre(){
        setBuscandoPorNombre(true);
        //comprobamos que se ha puesto un nombre a buscar y que este es de mas de 2 caracteres
         if((nombreBuscado==null)||(nombreBuscado.isEmpty())||(nombreBuscado.length()<3)){

            setNombreBuscado("");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeDebesItroducirUnaPalabra"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
         
            FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeAlMenosTresLetras"),"");
            FacesContext.getCurrentInstance().addMessage(null, message1);
//            System.out.println(" nombreBuscado vacio");
            setBuscandoPorNombre(false);
            
        }else{
             //buscamos los productos que coinciden con el nombre
             List<Producto> listaProductosTempo =productoFacade.productosXNombreParcialYFiltro(nombreBuscado, filtro);
             //si no se ha encontrado ninguno se indica con un mensaje
             if ((listaProductosTempo.isEmpty())){
                seleccionaProductos();
                FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajesNoEncontradoPorNombre")+nombreBuscado,nombreBuscado);
                FacesContext.getCurrentInstance().addMessage(null, message1);
    //            System.out.println(" No se han encontrado productos");
                setBuscandoPorNombre(false);
               }else{
                 //añadimos los productos encontrados
                 System.out.println(" encontrados numero de productos por nombre: "+listaProductosTempo.size());
//                    setFiltro("todos");
                    setListaProductos(listaProductosTempo);
                    setBuscandoPorNombre(true);

                    }
        }
    }
    public void limpiarFiltros(){
         setNombreBuscado("");
         setBuscandoPorNombre(false);
    }
    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Imagen> getImagenesProducto() {
        return imagenesProducto;
    }

    public void setImagenesProducto(List<Imagen> imagenesProducto) {
        this.imagenesProducto = imagenesProducto;
    }

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
        System.out.println("cambiamos filtro "+filtro);
        this.filtro = filtro;
    }

    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
    }

    public boolean isBuscandoPorNombre() {
        return buscandoPorNombre;
    }

    public void setBuscandoPorNombre(boolean buscandoPorNombre) {
        this.buscandoPorNombre = buscandoPorNombre;
    }
        
       
        
        
}


