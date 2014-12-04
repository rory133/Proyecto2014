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
import entidades.Producto;
import entidades.Venta;
import facade.CategoriaFacade;
import facade.DenunciaFacade;
import facade.ImagenFacade;
import facade.ProductoFacade;
import facade.PujaFacade;
import facade.VentaFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author juanma
 */
@ManagedBean(name = "gestionProductosAdministradorView")

@SessionScoped
public class GestionProductosAdministradorBean {

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

private List<Producto> listaProductos;

private Venta ventaDelProducto;

private String filtro;//todos, ventaDirecta, subasta

private String vendidos;//todos, yaVendidos, noVendidos

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
    
    

    
////////////////////////////////////////////////
    public GestionProductosAdministradorBean() {

    }
    
    @PostConstruct
    public void init() {
       setVendidos("todos");
       setFiltro("todos");
       setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
    }
    
    public void actualizaListadoProductos(){

        setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
    }
    
    public void encuentraVentaProducto(Producto producto){
        
       setVentaDelProducto(ventaFacade.ventaXProducto(producto));
        
    }
}
