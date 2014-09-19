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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author juanma
 */

@ManagedBean(name = "productoView")
@ViewScoped
//@SessionScoped
public class ListadoProductosBean implements Serializable {

@EJB
private ProductoFacade productoFacade;

@EJB
private ImagenFacade imagenFacade;

private Imagen imagen;

private List<Imagen> imagenesProducto;

private List<Producto> listaProductos;


private String nombreBuscado;

private FacesMessage facesMessage;
private FacesContext faceContext;

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

    public String getNombreBuscado() {
        return nombreBuscado;
    }

    public void setNombreBuscado(String nombreBuscado) {
        this.nombreBuscado = nombreBuscado;
    }

    

    
    
    @PostConstruct
    public void init() {
        faceContext=FacesContext.getCurrentInstance();
       imagenesProducto= new  ArrayList<>();
       listaProductos=new  ArrayList<>(); 
       todosProductos();
       
    }
    
    public List<Producto> todosProductos(){
      // setListaProductos(productoFacade.findAll());
       List<Producto> listaProductos2 =productoFacade.findAll();
       System.out.println(" cantidad de productos encontrados: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
       setListaProductos(listaProductos2);
       for(Producto productoEncontrado :listaProductos2){
           System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
       }


       return getListaProductos();
    }
    
    public String buscaXNombre(){
        System.out.println(" entro en buscaXNombre con texto introducido: "+ getNombreBuscado().toString()+"--");
      // setListaProductos(productoFacade.findAll());
        //if(((getNombreBuscado()==null)||(getNombreBuscado().isEmpty()))){
        //if((getNombreBuscado()==null)||(getNombreBuscado().isEmpty())||(getNombreBuscado()=="")){
        String buscar=getNombreBuscado();
         if((buscar==null)||(buscar.isEmpty())||(buscar.length()<3)){
             System.out.println(" true");
            imagenesProducto= new  ArrayList<>();
            listaProductos=new  ArrayList<>(); 
      //      todosProductos();
            
//            FacesContext.getCurrentInstance().addMessage(null,
//            new FacesMessage("Debe introducir texto a "  ));
//               
//               
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES INTRODUCIR UNA PALABRA A BUSCAR","AL MENOS DE TRES LETRAS");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
             FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,"INTRODUCE AL MENOS TRES CARACTERES A BUSCAR","");
            FacesContext.getCurrentInstance().addMessage(null, message1);
            System.out.println(" nombreBuscado vacio");
         //   getListaProductos().clear();
            
            
//                FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage("Debe seleccionar una imagen"  ));
//               
//             
//            
//           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Agregado correctamente producto: ", "sñdf");
//           FacesContext.getCurrentInstance().addMessage(null, message);
            return    "index";
            
        }else{
       List<Producto> listaProductos2 =productoFacade.productosXNombreParcial(buscar);
       
       if (listaProductos2==null){
            FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,"no se han encontrado productos que cumplan la condicion: "+buscar,buscar);
            FacesContext.getCurrentInstance().addMessage(null, message1);
            System.out.println(" No se han encontrado productos");
            return    "index";
       }else{
       System.out.println(" cantidad de productos encontrados -----: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
       setListaProductos(listaProductos2);
       for(Producto productoEncontrado :listaProductos2){
           System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           
        }
        return    "index";
       }
      
     }  

      // return getListaProductos();
        //return    "index";
    }
    
    

    public List<Producto> todosProductosVenta(){
       return productoFacade.findAll();
    }
    
    
}
