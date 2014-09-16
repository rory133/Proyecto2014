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
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author juanma
 */

@ManagedBean(name = "productoView")
@ViewScoped
public class ListadoProductosBean {

@EJB
private ProductoFacade productoFacade;

@EJB
private ImagenFacade imagenFacade;

private Imagen imagen;

private List<Imagen> imagenesProducto;

private List<Producto> listaProductos;




    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
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

    

public List<Producto> todosProductos(){
   setListaProductos(productoFacade.findAll());
   
   //añadimos a la lista de imagenes la primera imagen de cada producto.
   for(Producto productoEncontrado :listaProductos){
       List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
       imagenesProducto.add(imagenesXProcducto.get(0));
   }
   
   
   return getListaProductos();
}

public List<Producto> todosProductosVenta(){
   return productoFacade.findAll();
}
    
    
}
