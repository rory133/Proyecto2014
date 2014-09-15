/*
 * Proyecto fin de carrera gen?rico del Dpto.  * 
 * Lenguajes y Sistemas Inform?ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;

import entidades.Categoria;
import entidades.Imagen;
import entidades.Login;
import entidades.Producto;
import entidades.Puja;
import entidades.Usuario;
import entidades.Venta;
import facade.CategoriaFacade;
import facade.ImagenFacade;
import facade.LoginFacade;
import facade.ProductoFacade;
import facade.PujaFacade;
import facade.UsuarioFacade;
import facade.VentaFacade;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Scope;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedBeans.utilidades.SelectionView;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import utilidades.Loggable;

/**
 *
 * @author juanma
 */
//@Loggable
//@RequestScoped
@SessionScoped
@ManagedBean(name="crearProductoBean")
public class CrearProductoBean {

@EJB
private ProductoFacade productoFacade;

@EJB
private ImagenFacade imagenFacade;

@EJB
private LoginFacade loginFacade;

@EJB
private UsuarioFacade usuarioFacade;


@EJB
private VentaFacade ventaFacade;

@EJB
private PujaFacade pujaFacade;

@EJB 
CategoriaFacade categoriaFacade;



 private List<byte[]> imagenesSubidasByte = new ArrayList<byte[]>(); ;

////////////////////
  private List<UploadedFile> imagenesSubidas ;
  private  Imagen imagen;
  private  UploadedFile file;
  
  private Usuario usuario;
  private Producto producto;
  private Venta venta;
  private Categoria categoria;
  private Puja puja;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    

///////////////////

private FacesMessage facesMessage;
private final FacesContext faceContext;

// private List<UploadedFile> ImagenesSubidas ;

 private String nombre;
 
 
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
         
        faceContext=FacesContext.getCurrentInstance();
        imagenesSubidas = new  ArrayList<UploadedFile>();
//        imagenesSubiendo=new ImagenesSubiendo();
//        imagenesSubidasByte= new ArrayList<byte[]>();
        
    }
    
  
    public String salir() {
        imagenesSubidas = new  ArrayList<UploadedFile>();
//        imagenesSubiendo=new ImagenesSubiendo();
//        imagenesSubidasByte= new ArrayList<byte[]>();
      return "index";
    }

   public void fileUploadListener(FileUploadEvent event){
        // Get uploaded file from the FileUploadEvent
        //file = event.getFile();
        // Print out the information of the file
     //  System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
      //  UploadedFile uploadedFile = event.getFile();
       // String fileName = uploadedFile.getFileName();
      //  System.out.println("nombre uploadedFile: "+ fileName);
        
      //  System.out.println("byte[] contents = uploadedFile.getContents();");
       // byte[] contents = uploadedFile.getContents();
        
        //System.out.println("imagen.setImagen(contents);");
       // imagen=new Imagen();
        //imagen.setImagen(contents);
       
  
            
       
       
        
           imagenesSubidas.add(event.getFile());
           System.out.println("contenido imagenes: "+imagenesSubidas.size());
                for(UploadedFile uploadedFile:imagenesSubidas){
                    System.out.println(" nombre: "+uploadedFile.getFileName());
                    System.out.println(" tamaño: "+uploadedFile.getSize());
                    System.out.println(" contenido: "+uploadedFile.toString());
                    
                   // imagenesSubidasByte.add(uploadedFile.getContents());
                    //imagenesSubiendo.getImagenesSubidas().add(uploadedFile.getContents());
                    
                }
//            imagenesSubiendo.setImagenesSubidas(imagenesSubidasByte);
//            imagenesSubiendo.imprimir();
              

       
    }
   public void imprimirImagenes(){
        // Get uploaded file from the FileUploadEvent
        //file = event.getFile();
        // Print out the information of the file
     //  System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
      //  UploadedFile uploadedFile = event.getFile();
       // String fileName = uploadedFile.getFileName();
      //  System.out.println("nombre uploadedFile: "+ fileName);
        
      //  System.out.println("byte[] contents = uploadedFile.getContents();");
       // byte[] contents = uploadedFile.getContents();
        
        //System.out.println("imagen.setImagen(contents);");
       // imagen=new Imagen();
        //imagen.setImagen(contents);
        
          
           System.out.println("contenido imagenesSubidas: "+imagenesSubidas.size());
                for(UploadedFile uploadedFile:imagenesSubidas){
                    System.out.println(" nombre: "+uploadedFile.getFileName());
                    System.out.println(" tamaño: "+uploadedFile.getSize());
                    System.out.println(" contenido: "+uploadedFile.toString());

                }
                
//            System.out.println("contenido imagenesSubidasByte: "+imagenesSubidasByte.size());
//                for(byte[] imagen:imagenesSubidasByte){
//                    
//                    System.out.println(" tamaño: "+imagen.length);
//                    System.out.println(" contenido: "+imagen.toString());
//                
//                }
                
                
                
//            System.out.println("IMPRIMIDO DESDE IMAGNESSUBIENDO: ");
//            imagenesSubiendo.imprimir();
       
    }

 

    
    
    
     public String guardarProducto() {
         System.out.println("guardarProducto()");
         
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext context = facesContext.getExternalContext();  
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         
       
         usuario=new Usuario();    
         //HttpSession session = (HttpSession) faceContext.getExternalContext().getSession(false);
       
         usuario=(Usuario) session.getAttribute("usuario");
         
         producto= new Producto();
         Integer categoriaSeleccionada=(Integer)session.getAttribute("idCategoria");
         System.out.println("categoria pasada desde SelectionView : "+categoriaSeleccionada);
         
         
//         Map<String,String> params = 
//                faceContext.getExternalContext().getRequestParameterMap();
//	  String idCategoria = params.get("idCategoriaJSF");
//          String idCategoria=(String)session.getAttribute("idCategoriaJSF");
//         String idCategoria = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCategoriaJSF");
         
         
//            String idCategoria2="";
//            System.out.println("antes de todo CATEGORIA PASADAfasdfa::::::::::"+ facesContext.getExternalContext().getRequestParameterMap().get("idCategoriaJSF"));
//            idCategoria2 = facesContext.getExternalContext().getRequestParameterMap().get("idCategoriaJSF");
//            System.out.println("CATEGORIA PASADA antes if::::::::::"+ idCategoria2);
            
            
        //  if (!(idCategoria2.isEmpty())&&(idCategoria2!=null)){
        //  if ((!idCategoria2.equals("null"))&& (idCategoria2 != null) && (!idCategoria2.equals(""))&&!( idCategoria2 == "null" ) ) {
          
          if ((categoriaSeleccionada != null) && (categoriaSeleccionada>0)) {
//             System.out.println("CATEGORIA PASADA en if::::::::::"+ idCategoria2); 
//             System.out.println("logitud CATEGORIA PASADA en if::::::::::"+ idCategoria2.length()); 
//             Integer idCategoria=Integer.parseInt(idCategoria2); 

             

             Categoria categoria=categoriaFacade.find(categoriaSeleccionada);
             
             System.out.println("nombre CATEGORIA PASADA en if::::::::::"+ categoria.getNombre()); 
             producto.setCategoriaIdcategoria(categoria);
             producto.setUsuarioIdusuario(usuario);
             producto.setNombre(getNombre());
             producto.setPrecio(getPrecio());
             producto.setDescripcion(getDescripcion());
             producto.setEnSubasta(isEnSubasta());
             producto.setVendido(false);
             producto.setFechaProducto(new java.util.Date(System.currentTimeMillis()));
             productoFacade.create(producto);
             
             if (!isEnSubasta()){
                 System.out.println("creado procuto "+ producto.getNombre()+" en modo Venta directa");
                 
             }else{
                 System.out.println("crado procuto "+ producto.getNombre()+" en modo subasta");
             }
             
            for(UploadedFile uploadedFile:imagenesSubidas){
                    imagen=new Imagen();
                    //imagen.setImagen(uploadedFile.getContents());
                    imagen.setProductoIdproducto(producto);
                    System.out.println(" por insertar uploadedFile : "+uploadedFile.getFileName());
                    System.out.println(" por insertar imagen para producto: "+imagen.getProductoIdproducto().getNombre());
                    try { 
                        
                        byte[] bytearray = new byte[16777215];
                        int size = 0;
                        InputStream input = uploadedFile.getInputstream();
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        byte[] buffer = new byte[10240];
                        for (int length = 0; (length = input.read(buffer)) > 0;) output.write(buffer, 0, length);
                            imagen.setImagen(output.toByteArray());
                            
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    imagenFacade.create(imagen);
                  
                    System.out.println(" insertada imagen : "+uploadedFile.getFileName());


                }
             
             
         
          }else {
             System.out.println("CATEGORIA PASADA en else::::::::::"+ idCategoria);
             
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha seleccionado ninguna categoría",
                                    "Selecciona una categoria"));
            
//            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "No ha elegido una categoria", null);    
//            faceContext.addMessage(null, facesMessage);
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Debe seleccionar una Categoria"  ));
               
               
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES SELECCIONAR UNA CATEGORIA","SELECCIONA UNA");
            FacesContext.getCurrentInstance().addMessage(null, message);
               
               
            return "crearProducto";
          }
              
        return null;
    }
   

}
