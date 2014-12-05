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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import utilidades.DatosProductoCompleto;

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

private DatosProductoCompleto datosProductoCompleto;

private List<DatosProductoCompleto>listaProductosCompletos;


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
    
    

    
////////////////////////////////////////////////
    public GestionProductosAdministradorBean() {

    }
    
    @PostConstruct
    public void init() {
       setVendidos("todos");
       setFiltro("todos");
      // setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
       actualizaListaProductosCompletos();
    }
    
    public void actualizaListadoProductos(){

        setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
    }
    
    public void actualizaListaProductosCompletos(){
        //datosProductoCompleto=new  ArrayList<>();
        List<DatosProductoCompleto> listaProductosCompletos2  = new ArrayList<DatosProductoCompleto>();
        setListaProductosCompletos(null);
        List<Producto> listaProductos2=productoFacade.todosProductosFiltrados(filtro, vendidos);
      //  setListaProductos(productoFacade.todosProductosFiltrados(filtro, vendidos));
        
         System.out.println("@@tamaño listaProductos2: "+listaProductos2.size());
        for(Producto producto: listaProductos2){
            DatosProductoCompleto datosProductoCompleto2= new  DatosProductoCompleto();
            System.out.println("@@Añadimos producto : "+producto.getNombre());
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
            
            listaProductosCompletos2.add(datosProductoCompleto2);
            System.out.println("@@tamaño listaProductosCompletos2: "+listaProductosCompletos2.size());
        }
          System.out.println("@@tamaño FINAL listaProductosCompletos2: "+listaProductosCompletos2.size());
          
           for(DatosProductoCompleto datosProductoCompleto3: listaProductosCompletos2){
               System.out.println("##############################################################################################");
               System.out.println("###productoen lista productos completo: "+ datosProductoCompleto3.getProducto().getNombre()+" ###########################");
                System.out.println("##############################################################################################");
           }
         
         setListaProductosCompletos(listaProductosCompletos2);
        
        
        
    }
    
    public void encuentraVentaProducto(Producto producto){
        
       setVentaDelProducto(ventaFacade.ventaXProducto(producto));
        
    }
}
