/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;

import entidades.Categoria;
import entidades.Imagen;
import entidades.Producto;
import facade.CategoriaFacade;
import facade.ImagenFacade;
import facade.ProductoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author juanma
 */

@ManagedBean(name = "productoView")
//@ViewScoped
@SessionScoped
//@RequestScoped
public class ListadoProductosBean implements Serializable {

@EJB
private ProductoFacade productoFacade;

@EJB
private ImagenFacade imagenFacade;


@EJB
private CategoriaFacade categoriaFacade;

private Imagen imagen;
private Categoria categoria;

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
        try{
        Thread.currentThread().sleep(200);
        }catch (Exception e)
            {

            }
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
    
    //public List<Producto> todosProductos(){
    public void todosProductos(){
      // setListaProductos(productoFacade.findAll());
       List<Producto> listaProductos2 =productoFacade.findAll();
       System.out.println(" cantidad de productos encontrados: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
        imagenesProducto= new  ArrayList<>();
        listaProductos=new  ArrayList<>();
        setNombreBuscado("");
       //setListaProductos(listaProductos2);
       for(Producto productoEncontrado :listaProductos2){
           System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           listaProductos.add(productoEncontrado);
       }

     
      // return getListaProductos();
    }
    
    public void buscaXNombre(){
        System.out.println(" entro en buscaXNombre con texto introducido: "+ getNombreBuscado().toString()+"--");
      // setListaProductos(productoFacade.findAll());
        //if(((getNombreBuscado()==null)||(getNombreBuscado().isEmpty()))){
        //if((getNombreBuscado()==null)||(getNombreBuscado().isEmpty())||(getNombreBuscado()=="")){
        String buscar=getNombreBuscado();
         if((buscar==null)||(buscar.isEmpty())||(buscar.length()<3)){
             System.out.println(" true");
            imagenesProducto= new  ArrayList<>();
            listaProductos=new  ArrayList<>();
            setNombreBuscado("");
      //      todosProductos();
            
//            FacesContext.getCurrentInstance().addMessage(null,
//            new FacesMessage("Debe introducir texto a "  ));
//               
//               
            todosProductos();
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
            
//            return    "index";
            
        }else{
       List<Producto> listaProductos2 =productoFacade.productosXNombreParcial(buscar);
       
       if ((listaProductos2.isEmpty())){
           setNombreBuscado("");
           todosProductos();
            FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,"no se han encontrado productos que cumplan la condicion: "+buscar,buscar);
            FacesContext.getCurrentInstance().addMessage(null, message1);
            System.out.println(" No se han encontrado productos");
            
//           return    "index";
       }else{
       System.out.println(" cantidad de productos encontrados -----: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
       getListaProductos().clear();
       getImagenesProducto().clear();
       
       //setListaProductos(listaProductos2);
       imagenesProducto= new  ArrayList<>();
       listaProductos=new  ArrayList<>();
       setNombreBuscado("");
        setListaProductos(listaProductos2);
       for(Producto productoEncontrado :listaProductos2){
           //getListaProductos().add(productoEncontrado);
         //  System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
//           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           
        }
       
//        return    "index";
       }
      
     }  

      // return getListaProductos();
        //return    "index";
    }
    
    
    
    
    
    public void buscaXCategoria(){
             FacesContext facesContext = FacesContext.getCurrentInstance();
             HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
             Integer categoriaSeleccionada=(Integer)session.getAttribute("idCategoria");
         System.out.println(" ###categoria seleccionada -----: " +categoriaSeleccionada +"*****"); 
        if ((categoriaSeleccionada != null) && (categoriaSeleccionada>0)) {
  
            //List<Producto> listaProductos2 =productoFacade.productosXCategoria(Integer.toString(categoriaSeleccionada));
            List<Producto> listaProductos2 =productoFacade.productosXCategoria(categoriaSeleccionada);
            categoria = (Categoria)categoriaFacade.find(categoriaSeleccionada);
       
       if ((null==listaProductos2)|| (listaProductos2.isEmpty())){//no hay productos de la categoria seleccionada
           setNombreBuscado("");
           todosProductos();
            FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,"no se han encontrado productos de la categoria seleccionada: "+categoria.getNombre(),categoria.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, message1);
            System.out.println(" No se han encontrado productos de "+categoria.getNombre());
            

       }else{
       System.out.println(" cantidad de productos de la categoria -----: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
       getListaProductos().clear();
       getImagenesProducto().clear();
       
       //setListaProductos(listaProductos2);
       imagenesProducto= new  ArrayList<>();
       listaProductos=new  ArrayList<>();
       setNombreBuscado("");
        setListaProductos(listaProductos2);
       for(Producto productoEncontrado :listaProductos2){
           //getListaProductos().add(productoEncontrado);
         //  System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
//           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           
        }
       
//        return    "index";
       }
      
    

        }else {
              
           // se informa al usuario cuando no ha añadido ninguna categoria
             System.out.println("CATEGORIA PASADA en else::::::::::"+ categoriaSeleccionada);
             
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado ninguna categoría",
                                    "Selecciona una categoria"));
            
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Debe seleccionar una Categoria"  ));
               
               
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES SELECCIONAR UNA CATEGORIA","SELECCIONA UNA");
            FacesContext.getCurrentInstance().addMessage(null, message);
               
               
         
          }
    }

    public List<Producto> todosProductosVenta(){
       return productoFacade.findAll();
    }
    
    
}
