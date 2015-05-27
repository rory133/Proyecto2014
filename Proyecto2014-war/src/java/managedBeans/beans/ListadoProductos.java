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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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

    public ListadoProductos() {
        }
    @PostConstruct
    public void init() {
//    public void iniciar() {
       faceContext=FacesContext.getCurrentInstance();
       setFiltro("todos");
    }

    public void seleccionaProductos(){
        setListaProductos(productoFacade.findAll());
    }
    public void todosLosProductos(){
//          setListaProductos(productoFacade.findAll());
//        seleccionaProductos();
//          String filtro2=getFiltro();
        
        
        
        setListaProductos(productoFacade.todosProductosXFiltro(filtro));
//        switch (filtro) {
//            
//                case "todos":
//                    setListaProductos(productoFacade.findAll());
//                break;
//                default:
//                    setListaProductos(productoFacade.findAll());
//        }
    
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
        
        
        
        
}


