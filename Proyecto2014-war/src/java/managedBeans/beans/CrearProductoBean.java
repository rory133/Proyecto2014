/*
 * Proyecto fin de carrera gen?rico del Dpto.  * 
 * Lenguajes y Sistemas Inform?ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;

import ejbs.SubastaTimer;
import entidades.Categoria;
import entidades.Imagen;
import entidades.Login;
import entidades.Producto;
import entidades.Puja;
import entidades.Usuario;
import facade.CategoriaFacade;
import facade.ImagenFacade;
import facade.ProductoFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import utilidades.Loggable;

/**
 *
 * @author juanma
 */


@Loggable
@SessionScoped
//@RequestScoped
@ManagedBean(name="crearProductoBean")
public class CrearProductoBean {

@EJB
private ProductoFacade productoFacade;

@EJB
private ImagenFacade imagenFacade;

@EJB 
CategoriaFacade categoriaFacade;

@EJB
SubastaTimer subastaTimer;



// private List<byte[]> imagenesSubidasByte = new ArrayList<byte[]>(); ;


  private List<UploadedFile> imagenesSubidas ;
  private  Imagen imagen;
  private  UploadedFile file;
  
  private Usuario usuario;
  private Producto producto;
  private Categoria categoria;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    



private FacesMessage facesMessage;
private final FacesContext faceContext;



 private String nombre;
 
 
 @Size(min=4,max=400)
 private String descripcion;
 
 
 private float precio;
 
 
 private boolean vendido;
 
 
 private boolean enSubasta;
 
 private Login login;

private String nombreCategoria;

private String idCategoria;



   public List<UploadedFile> getImagenesSubidas() {
        return imagenesSubidas;
    }

    public void setImagenesSubidas(List<UploadedFile> imagenesSubidas) {
        this.imagenesSubidas = imagenesSubidas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public boolean isEnSubasta() {
        return enSubasta;
    }

    public void setEnSubasta(boolean enSubasta) {
        this.enSubasta = enSubasta;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }


    
    
    

   
    public CrearProductoBean() {
        System.out.println("ENTRO EN CREARPRODUCTOBEAN()");
        faceContext=FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().getAttributes().put((String)"idCategoria",(Integer)0);
        imagenesSubidas = new  ArrayList<>();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("idCategoria", 0);
        
    }
    
    
    
    
    
  //antes de salir de la pagina borramos los campos
    public String salir() {
    imagenesSubidas = new  ArrayList<>();
     
      setCategoria(null);
      setDescripcion("");
      setEnSubasta(false);
      setNombre("");
      setPrecio((float) 0.0);
      imagenesSubidas = new  ArrayList<>();
      ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
      externalContext.getSessionMap().put("idCategoria", 0);
      return "index.xhtml?faces-redirect=true";
    }

    
   public void fileUploadListener(FileUploadEvent event){
        
           imagenesSubidas.add(event.getFile());

    }
   public void imprimirImagenes(){
       
          
           System.out.println("contenido imagenesSubidas: "+imagenesSubidas.size());
                for(UploadedFile uploadedFile:imagenesSubidas){
                    System.out.println(" nombre: "+uploadedFile.getFileName());
                    System.out.println(" tamaño: "+uploadedFile.getSize());
                    System.out.println(" contenido: "+uploadedFile.toString());

                }
     }

 

    
    
    //guardamos un nuevo producto en la base de datos
    public String guardarProducto() {
    //public void guardarProducto() {
         System.out.println("guardarProducto()");
         
    FacesContext facesContext = FacesContext.getCurrentInstance();
      HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         
       
         usuario=new Usuario();    
      
         usuario=(Usuario) session.getAttribute("usuario");
         
         producto= new Producto();
         Integer categoriaSeleccionada=(Integer)session.getAttribute("idCategoria");
         System.out.println("categoria pasada desde SelectionView : "+categoriaSeleccionada);
         
        
     
          if ((categoriaSeleccionada != null) && (categoriaSeleccionada>0)) {
             

             categoria=categoriaFacade.find(categoriaSeleccionada);
             
             System.out.println("nombre CATEGORIA PASADA en if::::::::::"+ categoria.getNombre()); 
//             producto.setCategoriaIdcategoria(categoria);
//             producto.setUsuarioIdusuario(usuario);
//             producto.setNombre(getNombre());
//             producto.setPrecio(getPrecio());
//             producto.setDescripcion(getDescripcion());
//             producto.setEnSubasta(isEnSubasta());
//             producto.setVendido(false);
//             producto.setFechaProducto(new java.util.Date(System.currentTimeMillis()));
//             //productoFacade.create(producto);
//             
//             if (!isEnSubasta()){
//                 System.out.println("creado producto "+ producto.getNombre()+" en modo Venta directa");
//                 
//             }else{
//                 System.out.println("creado producto "+ producto.getNombre()+" en modo subasta");
//             }
            if (!imagenesSubidas.isEmpty()){
                
                
             producto.setCategoriaIdcategoria(categoria);
             producto.setUsuarioIdusuario(usuario);
             producto.setNombre(getNombre());
             producto.setPrecio(getPrecio());
             producto.setDescripcion(getDescripcion());
             producto.setEnSubasta(false);
             producto.setVendido(false);
             producto.setFechaProducto(new java.util.Date(System.currentTimeMillis()));
             
             
             if (!isEnSubasta()){
                 System.out.println("creado producto "+ producto.getNombre()+" en modo Venta directa");
                 //al no ser en modo subasta lo ponemos ya a verdadero
                 producto.setExpirado(true);
                 
             }else{
                 System.out.println("creado producto "+ producto.getNombre()+" en modo subasta");
                 producto.setExpirado(false);
                 
                
                 
             }
                System.out.println("CREANDO PRODUCTOOOOOO: "+producto.getNombre());
               // productoFacade.create(producto);
                producto=productoFacade.salva(producto);
                
                
                //le creamos un temporizador para que expire a los 7 dias
                if (isEnSubasta()){
                    
                    producto.setEnSubasta(true);
                    producto=productoFacade.salva(producto);
                    subastaTimer.creaTemporizador(producto);
                }
            for(UploadedFile uploadedFile:imagenesSubidas){
                    imagen=new Imagen();
                    //imagen.setImagen(uploadedFile.getContents());
                    imagen.setProductoIdproducto(producto);
                    
//                    System.out.println(" por insertar uploadedFile : "+uploadedFile.getFileName());
//                    System.out.println(" por insertar imagen para producto: "+imagen.getProductoIdproducto().getNombre());
                    try { 
                        
                        InputStream input = uploadedFile.getInputstream();
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                    
                        byte[] buffer = new byte[16777215];
                        for (int length = 0; (length = input.read(buffer)) > 0;) output.write(buffer, 0, length);
                            imagen.setImagen(output.toByteArray());
                            
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    imagenFacade.create(imagen);
                    System.out.println(" insertada imagen : "+uploadedFile.getFileName());


                }
            //informamos que el producto se añadio correctamente
                      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Agregado correctamente producto: "+producto.getNombre(), producto.getNombre());
                      FacesContext.getCurrentInstance().addMessage(null, message);
                              
                      imagenesSubidas = new  ArrayList<>();
                      setCategoria(null);
                      setDescripcion("");
                      setEnSubasta(false);
                      setNombre("");
                      setPrecio((float) 0.0);
                      
                      ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                      externalContext.getSessionMap().put("idCategoria", 0);
                    //return "index";
                     //return "/usuario/index?faces-redirect=true";
                      //return "/usuario/index";
                     // return "/usuario/index";
//                              try {
//                                    System.out.println("contexto---->:"+FacesContext.getCurrentInstance().getExternalContext().toString());
//                                    FacesContext.getCurrentInstance().getExternalContext()
//                                    .redirect("../usuario/index.xhtml");
//                                    } catch (IOException e) {
//                                    e.printStackTrace();
//                                    }
                      return "index.xhtml?faces-redirect=true";
             
            }else{
        // se informa al usuario cuando no ha añadido ninguna imagen
                
                System.out.println("---- no se ha pasado ninguna imagen ----");
             
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado ninguna imagen",
                                    "Selecciona una imagen"));

               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Debe seleccionar una imagen"  ));
               
               
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES SELECCIONAR AL MENOS UNA IMAGEN","SELECCIONA UNA");
            FacesContext.getCurrentInstance().addMessage(null, message);
               
               
            return "crearProducto";
            
//                                  try {
//                                    System.out.println("contexto---->:"+FacesContext.getCurrentInstance().getExternalContext().toString());
//                                    FacesContext.getCurrentInstance().getExternalContext()
//                                    .redirect("../usuario/crearProducto.xhtml");
//                                    } catch (IOException e) {
//                                    e.printStackTrace();
//                                    }
                
                
            } 
         
          }else {
              
           // se informa al usuario cuando no ha añadido ninguna categoria
           //  System.out.println("CATEGORIA PASADA en else::::::::::"+ idCategoria);
             
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado ninguna categoría",
                                    "Selecciona una categoria"));
            
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Debe seleccionar una Categoria"  ));
               
               
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES SELECCIONAR UNA CATEGORIA","SELECCIONA UNA");
            FacesContext.getCurrentInstance().addMessage(null, message);
               
               
            return "crearProducto";
//                                try {
//                                    System.out.println("contexto---->:"+FacesContext.getCurrentInstance().getExternalContext().toString());
//                                    FacesContext.getCurrentInstance().getExternalContext()
//                                    .redirect("../usuario/crearProducto.xhtml");
//                                    } catch (IOException e) {
//                                    e.printStackTrace();
//                                    }
            
            
          }
              
    
    }
   

}
