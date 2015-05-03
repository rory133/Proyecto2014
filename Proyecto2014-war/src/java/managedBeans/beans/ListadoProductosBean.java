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
import entidades.Imagen;
import entidades.Producto;
import entidades.Puja;
import entidades.Usuario;
import entidades.Venta;
import facade.CategoriaFacade;
import facade.DenunciaFacade;
import facade.ImagenFacade;
import facade.ProductoFacade;
import facade.PujaFacade;
import facade.VentaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedBeans.utilidades.ResourcesUtil;


/**
 *
 * @author juanma
 */

@ManagedBean(name = "productoView")
@SessionScoped
public class ListadoProductosBean implements Serializable {


    
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

private Imagen imagen;
private Categoria categoria;

private List<Imagen> imagenesProducto;

private List<Producto> listaProductos;

private List<Venta> listaVentas;



private String nombreBuscado;
private String filtro;
private String vendidos;
private boolean soloMios; 
private Producto productoSeleccionado;
private List<Imagen> imagenesProductoSeleccionado;

//para controlar cuando se esta buscando por nombre
private boolean buscandoPorNombre;
//para controlar cuando se esta buscando por categoria
private boolean buscandoPorCategoria;
private String filtroMisProductos;
private Denuncia denuncia;
private Venta ventaSeleccionada;
private Venta ventaSeleccionada2;
private String motivoDenuncia;
private String tipoDenuncia;
private List<Puja> pujasProducto ;
private String ventasAMostrar;

private FacesMessage facesMessage;
private FacesContext faceContext;
//tiempo que resta en subasta
long elapsedDays;
long elapsedHours;
long elapsedMinutes;
long elapsedSeconds;

private Locale locale;
//el título del menu izquierdo variará segun veamos categorias y gestionemos nuestros pruductos.
    private String titulo;

    public List<Imagen> getImagenesProductoSeleccionado() {
        return imagenesProductoSeleccionado;
    }

    public void setImagenesProductoSeleccionado(List<Imagen> imagenesProductoSeleccionado) {
        this.imagenesProductoSeleccionado = imagenesProductoSeleccionado;
    }
    
    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
         System.out.println("introducido productoSeleccionado:::::::"+productoSeleccionado.getNombre());
        buscaImagenesProductoSeleccionado(productoSeleccionado);
        this.productoSeleccionado = productoSeleccionado;
        setTiempoRestante(this.productoSeleccionado.getFechaProducto());
        
        
    }
    	public void setTiempoRestante(Date endDate){
 
                Date startDate=new java.util.Date(System.currentTimeMillis());
		//milliseconds
		long different = (endDate.getTime()+604800000) - startDate.getTime();
 
//		System.out.println("startDate : " + startDate);
//		System.out.println("endDate : "+ endDate);
//		System.out.println("different : " + different);
 
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;
                
                setElapsedDays(different / daysInMilli);
		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;
                
                setElapsedHours(different / hoursInMilli);
		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;
 
                setElapsedMinutes(different / minutesInMilli);
		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;
                
                setElapsedSeconds(different / secondsInMilli);
		long elapsedSeconds = different / secondsInMilli;
 
		System.out.printf(
		    "%d days, %d hours, %d minutes, %d seconds%n", 
		    elapsedDays,
		    elapsedHours, elapsedMinutes, elapsedSeconds);
 
	}

    public boolean isSoloMios() {

        return soloMios;
    }

    public void setSoloMios(boolean soloMios) {
        if (soloMios==true){
            

              setTitulo(ResourcesUtil.getString("app.GestionaTusProductos"));
              
        }else {

            setTitulo(ResourcesUtil.getString("app.Categorias"));
            setFiltroMisProductos("ofertados");
        }        
        this.soloMios = soloMios;
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

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }

    public List<Puja> getPujasProducto() {
        return pujasProducto;
    }

    public void setPujasProducto(List<Puja> pujasProducto) {
        this.pujasProducto = pujasProducto;
    }

    public String getVentasAMostrar() {
        return ventasAMostrar;
    }

    public void setVentasAMostrar(String ventasAMostrar) {
        this.ventasAMostrar = ventasAMostrar;
    }
    
    
    



    
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
        Thread.currentThread().sleep(10);
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



    public long getElapsedDays() {
        return elapsedDays;
    }

    public void setElapsedDays(long elapsedDays) {
        this.elapsedDays = elapsedDays;
    }

    public long getElapsedHours() {
        return elapsedHours;
    }

    public void setElapsedHours(long elapsedHours) {
        this.elapsedHours = elapsedHours;
    }

    public long getElapsedMinutes() {
        return elapsedMinutes;
    }

    public void setElapsedMinutes(long elapsedMinutes) {
        this.elapsedMinutes = elapsedMinutes;
    }

    public long getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(long elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public boolean isBuscandoPorNombre() {
        return buscandoPorNombre;
    }

    public void setBuscandoPorNombre(boolean buscandoPorNombre) {
        this.buscandoPorNombre = buscandoPorNombre;
    }

    public boolean isBuscandoPorCategoria() {
        return buscandoPorCategoria;
    }

    public void setBuscandoPorCategoria(boolean buscandoPorCategoria) {
        this.buscandoPorCategoria = buscandoPorCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFiltroMisProductos() {
        return filtroMisProductos;
    }

    public void setFiltroMisProductos(String filtroMisProductos) {
        this.filtroMisProductos = filtroMisProductos;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public Venta getVentaSeleccionada() {
        return ventaSeleccionada;
    }

    public void setVentaSeleccionada(Venta ventaSeleccionada) {
        this.ventaSeleccionada = ventaSeleccionada;
    }

    public Venta getVentaSeleccionada2() {
        return ventaSeleccionada2;
    }

    public void setVentaSeleccionada2(Venta ventaSeleccionada2) {
        this.ventaSeleccionada2 = ventaSeleccionada2;
    }
    
    
    
    public String getMotivoDenuncia() {
        return motivoDenuncia;
    }

    public void setMotivoDenuncia(String motivoDenuncia) {
        this.motivoDenuncia = motivoDenuncia;
    }

    public String getTipoDenuncia() {
        return tipoDenuncia;
    }

    public void setTipoDenuncia(String tipoDenuncia) {
        this.tipoDenuncia = tipoDenuncia;
    }



    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    
    

    
    
    @PostConstruct
    public void init() {
       faceContext=FacesContext.getCurrentInstance();
       
       setSoloMios(false);
       imagenesProducto= new  ArrayList<>();
       listaProductos=new  ArrayList<>(); 
       setVendidos("todos");
       setFiltro("todos");
       setFiltroMisProductos("ofertados");

       todosProductos();
       setTitulo(ResourcesUtil.getString("app.Categorias"));
       setVentasAMostrar("NoEnviados");
//       iniciarLocate();
       
    }
    
    
//    public void iniciarLocate() {
//        //obtenemos el HttpServletRequest  de la peticion para poder saber
//       // el locale del cliente
//        
//        
//         HttpServletRequest requestObj = (HttpServletRequest)      
//         FacesContext.getCurrentInstance().getExternalContext().getRequest();
//         
//       //Asignamos al locale de la aplicacion el locale del cliente
////       FacesContext.getCurrentInstance().getViewRoot().setLocale(requestObj.getLocale());
//       locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
//       
//    }   
    
    
    
    
    

    public void todosProductosSinFiltro(){

        setBuscandoPorNombre(false);
       List<Producto> listaProductos2 =productoFacade.findAll();
//       System.out.println(" cantidad de productos encontrados: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
        imagenesProducto= new  ArrayList<>();
        listaProductos=new  ArrayList<>();
        setNombreBuscado("");

       for(Producto productoEncontrado :listaProductos2){
//           System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
//           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           listaProductos.add(productoEncontrado);
       }
    }  
       
    public void todosProductos(){

        setBuscandoPorCategoria(false);
        setBuscandoPorNombre(false);
        List<Producto> listaProductos2 =productoFacade.todosProductosFiltrados(filtro, vendidos);
        if (soloMios){
         List<Producto> listaProductos3= new  ArrayList<>();;
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         Usuario usuario=(Usuario)session.getAttribute("usuario");
         for(Producto productoEncontrado :listaProductos2){
             if (productoEncontrado.getUsuarioIdusuario().getIdusuario().equals(usuario.getIdusuario())){
                 listaProductos3.add(productoEncontrado);             
                 
             }
         }
         listaProductos2=listaProductos3;
        }
       
//       System.out.println(" cantidad de productos encontrados: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
        imagenesProducto= new  ArrayList<>();
        listaProductos=new  ArrayList<>();
        setNombreBuscado("");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("idCategoria", 0);

       for(Producto productoEncontrado :listaProductos2){
           
           
//           System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
//           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           listaProductos.add(productoEncontrado);
       }

     

    }
    public void actualizaVistaTodosProductos(){
      // si estamos buscando por nombre se redirige
         if (this.isBuscandoPorNombre()){
          
           buscaXNombre();
         }
         else if(isBuscandoPorCategoria()){
           buscaXCategoria();
         }
         else{
        setBuscandoPorNombre(false);
        List<Producto> listaProductos2 =productoFacade.todosProductosFiltrados(filtro, vendidos);
        if (soloMios){
         List<Producto> listaProductos3= new  ArrayList<>();;
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         Usuario usuario=(Usuario)session.getAttribute("usuario");
         for(Producto productoEncontrado :listaProductos2){
             if (productoEncontrado.getUsuarioIdusuario().getIdusuario().equals(usuario.getIdusuario())){
                 listaProductos3.add(productoEncontrado);             
                 
             }
         }
         listaProductos2=listaProductos3;
        }
       
//       System.out.println(" cantidad de productos encontrados: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
        imagenesProducto= new  ArrayList<>();
        listaProductos=new  ArrayList<>();
        setNombreBuscado("");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("idCategoria", 0);

       for(Producto productoEncontrado :listaProductos2){
           
           
//           System.out.println("en el for, producto encontrado: "+productoEncontrado.getNombre());
           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
//           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           listaProductos.add(productoEncontrado);
       }
      }
     
      // return getListaProductos();
    }
        
    public void buscaXNombre(){
        setBuscandoPorNombre(true);
     
//        System.out.println(" entro en buscaXNombre con texto introducido: "+ getNombreBuscado().toString()+"--");

        String buscar=getNombreBuscado();
         if((buscar==null)||(buscar.isEmpty())||(buscar.length()<3)){
             System.out.println(" true");
            imagenesProducto= new  ArrayList<>();
            listaProductos=new  ArrayList<>();
            setNombreBuscado("");
               
            todosProductos();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeDebesItroducirUnaPalabra"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
             FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeAlMenosTresLetras"),"");
            FacesContext.getCurrentInstance().addMessage(null, message1);
            System.out.println(" nombreBuscado vacio");

            
        }else{
       List<Producto> listaProductos2 =productoFacade.productosXNombreParcial(buscar,filtro,vendidos);
       
                    if (soloMios){
                         List<Producto> listaProductos3= new  ArrayList<>();
                         FacesContext facesContext = FacesContext.getCurrentInstance();
                         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                         Usuario usuario=(Usuario)session.getAttribute("usuario");
                         for(Producto productoEncontrado :listaProductos2){
                             if (productoEncontrado.getUsuarioIdusuario().getIdusuario().equals(usuario.getIdusuario())){
                                 listaProductos3.add(productoEncontrado);             

                             }
                         }
                         listaProductos2=listaProductos3;
                     }
       
       
       
       if ((listaProductos2.isEmpty())){
           setNombreBuscado("");
           todosProductos();
            FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajesNoEncontradoPorNombre")+buscar,buscar);
            FacesContext.getCurrentInstance().addMessage(null, message1);
//            System.out.println(" No se han encontrado productos");
            
       }else{
//       System.out.println(" cantidad de productos encontrados -----: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
       getListaProductos().clear();
       getImagenesProducto().clear();
       

       imagenesProducto= new  ArrayList<>();
       listaProductos=new  ArrayList<>();
       setNombreBuscado("");
        setListaProductos(listaProductos2);
       for(Producto productoEncontrado :listaProductos2){

           List<Imagen> imagenesXProcducto=imagenFacade.imagenesXProcducto(productoEncontrado);
//           System.out.println("cantidad de imagenes del producoto : "+ imagenesXProcducto.size());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getIdimagen());
//           System.out.println("IdImagen : "+ imagenesXProcducto.get(0).getImagen().toString());
           imagenesProducto.add(imagenesXProcducto.get(0));
           
        }
       

       }
      setNombreBuscado(buscar);
     }  

    }
    
    
    
    
    
    public void buscaXCategoria(){
             setBuscandoPorCategoria(true);
             setBuscandoPorNombre(false);
             FacesContext facesContext = FacesContext.getCurrentInstance();
             HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
             Integer categoriaSeleccionada=(Integer)session.getAttribute("idCategoria");
//         System.out.println(" ###categoria seleccionada -----: " +categoriaSeleccionada +"*****"); 
        if ((categoriaSeleccionada != null) && (categoriaSeleccionada>0)) {

            List<Producto> listaProductos2 =productoFacade.productosXCategoria(categoriaSeleccionada,filtro,vendidos);
                    if (soloMios){
                         List<Producto> listaProductos3= new  ArrayList<>();

                         Usuario usuario=(Usuario)session.getAttribute("usuario");
                         for(Producto productoEncontrado :listaProductos2){
                             if (productoEncontrado.getUsuarioIdusuario().getIdusuario().equals(usuario.getIdusuario())){
                                 listaProductos3.add(productoEncontrado);             

                             }
                         }
                         listaProductos2=listaProductos3;
                     }
            
            
            
            
            
            categoria = (Categoria)categoriaFacade.find(categoriaSeleccionada);
       
       if ((null==listaProductos2)|| (listaProductos2.isEmpty())){//no hay productos de la categoria seleccionada
           setNombreBuscado("");
           todosProductos();
            FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeNoProductosCategoria")+categoria.getNombre(),categoria.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, message1);
//            System.out.println(" No se han encontrado productos de "+categoria.getNombre());
            

       }else{
//       System.out.println(" cantidad de productos de la categoria -----: "+ listaProductos2.size());
       //añadimos a la lista de imagenes la primera imagen de cada producto.
       getListaProductos().clear();
       getImagenesProducto().clear();
       

       imagenesProducto= new  ArrayList<>();
       listaProductos=new  ArrayList<>();
       setNombreBuscado("");
        setListaProductos(listaProductos2);
       for(Producto productoEncontrado :listaProductos2){

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
//             System.out.println("CATEGORIA PASADA en else::::::::::"+ categoriaSeleccionada);
             
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeNoCategoriaSeleccionada"),
                                    ResourcesUtil.getString("app.MensajeSeleccionaCategoria")));
            
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(ResourcesUtil.getString("app.MensajeSeleccionaCategoria")  ));
               
               
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeSeleccionaCategoria"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
               
               
         
          }
    }

    public List<Producto> todosProductosVenta(){
       return productoFacade.findAll();
    }
    
    //buscamos las imagenes del producto seleccionado para mostrarselas al usuario
    public void buscaImagenesProductoSeleccionado(Producto productoSele){
        setImagenesProductoSeleccionado(imagenFacade.imagenesXProcducto(productoSele));
        
    }
    public void misOfertadosVentaDirecta(){
        System.out.println("misOfertadosVentaDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta()");
        setFiltro("ventaDirecta");
        setVendidos("noVendidos");

        actualizaVistaTodosProductos();
    }

    public void misOfertadosSubasta(){
//        System.out.println("misOfertadosSubasta()misOfertadosSubasta()misOfertadosSubasta()");
        setFiltro("subasta");
        setVendidos("noVendidos");

        actualizaVistaTodosProductos();
        
    }
    public void misOfertadosSubasta(ActionEvent event){
//        System.out.println("misOfertadosSubasta()mmisOfertadosSubasta()misOfertadosSubasta()misOfertadosSubasta()misOfertadosSubasta()misOfertadosSubasta()misOfertadosSubasta()");
        String nickname;
        nickname = (String)event.getComponent().getAttributes().get("username");
        
//        System.out.println("misOfertadosSubasta()misOfertadosSubasta()misOfertadosSubasta():"+nickname );
        setFiltro("subasta");
        setVendidos("noVendidos");
        todosProductos();
        actualizaVistaTodosProductos();
        
    }    
    
    public void misOfertadosVentaDirecta(ActionEvent event){
//        System.out.println("misOfertadosVentaDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta(taDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta(");
        String nickname;
        nickname = (String)event.getComponent().getAttributes().get("username");

//        System.out.println("misOfertadosVentaDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta()misOfertadosVentaDirecta():"+nickname);
        setFiltro("ventaDirecta");
        setVendidos("noVendidos");
        todosProductos();

    }
    
    public void misProductosCompradosNORecibidos(String sin){
//        System.out.println("compradoNORecibidos(String sin)");
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioCompradorNoRecibidos(usuario);
        setVentasAMostrar("NoRecibidos");
        setListaVentas(listaVentasTempo);

        
        

    }
    public void misProductosCompradosRecibidos(String sin){
//        System.out.println("compradoRecibidos(String sin)");
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioCompradorRecibidos(usuario);
        setVentasAMostrar("Recibidos");
        setListaVentas(listaVentasTempo);

        
        

    }
    public void misProductosVendidosEnviados(String sin){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioVendedorEnviados(usuario);
        setVentasAMostrar("Enviados");
        setListaVentas(listaVentasTempo);

    }
    
    public void misProductosVendidosNoEnviados(String sin){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioVendedorNoEnviados(usuario);
        setVentasAMostrar("NoEnviados");
        setListaVentas(listaVentasTempo);

    }
    public void misProductosCompradosNORecibidos(){
//        System.out.println("compradoNORecibidos()");
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioCompradorNoRecibidos(usuario);
        setVentasAMostrar("NoRecibidos");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeCompradoNoRecibido"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMostrandoCompradoNoRecibido"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
        
        

    }
    public void misProductosCompradosRecibidos(){
//        System.out.println("compradoRecibidos()");
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioCompradorRecibidos(usuario);
        setVentasAMostrar("Recibidos");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeCompradoRecibido"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMostrandoCompradoRecibido"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
        
        

    }
    public void misProductosVendidosEnviados(){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioVendedorEnviados(usuario);
        setVentasAMostrar("Enviados");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeVendidoEnviado"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMostrandoVendidoEnviado"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
    }
    
    public void misProductosVendidosNoEnviados(){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioVendedorNoEnviados(usuario);
        setVentasAMostrar("NoEnviados");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeVendidoNoEnviado"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMostrandoVendidoNoEnviado"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
    }
    
   /* 
    public void misProductosCompradosNORecibidos(ActionEvent event){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioCompradorNoRecibidos(usuario);
        setVentasAMostrar("NoRecibidos");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeCompradoNoRecibido"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMostrandoCompradoNoRecibido"),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
        
        

    }
    public void misProductosCompradosRecibidos(ActionEvent event){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioCompradorRecibidos(usuario);
        setVentasAMostrar("Recibidos");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeMostrandoCompradoNoRecibido")+"No existe ningun producto comprado y que hayas recibido","");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMostrandoCompradoNoRecibido")+"Se muestran los productos que has comprados y ya has recibido","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
        
        

    }
    public void misProductosVendidosEnviados(ActionEvent event){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioVendedorEnviados(usuario);
        setVentasAMostrar("Enviados");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,ResourcesUtil.getString("app.MensajeMostrandoCompradoNoRecibido")+"No existe ningun producto vendido y que hayas enviado","");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMostrandoCompradoNoRecibido")+"Se muestran los productos que has vendido y ya has enviado","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
    }
    
    public void misProductosVendidosNoEnviados(ActionEvent event){
        setListaVentas(null);
        Usuario usuario=usuarioLogado();
        List<Venta> listaVentasTempo=ventaFacade.ventaXUsuarioVendedorNoEnviados(usuario);
        setVentasAMostrar("NoEnviados");
         if((listaVentasTempo==null)||(listaVentasTempo.isEmpty())){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"No existe ningun producto vendido y que no hayas enviado","");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
         }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Se muestran los productos que has vendido y aun no has enviado","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            setListaVentas(listaVentasTempo);
         }
    }    
    
    */
    public void actualizaVistaVentas(){
        switch (filtroMisProductos) {
            case "comprados":
                switch (ventasAMostrar) {
                    case "Recibidos":
                    misProductosCompradosRecibidos("sin");
                    
                    break;
                    case "NoRecibidos":
                    misProductosCompradosNORecibidos("sin");
                    break;
                    
                }
            
            break;
            case "vendidos":
                    switch (ventasAMostrar) {
                    case "Enviados":
                    misProductosVendidosEnviados("sin");
                    
                    break;
                    case "NoEnviados":
                    misProductosVendidosNoEnviados("sin");
                    break;
                    
                }
            
            break;
            
        }
    }
    
    public void creaDenuncia(){
        
        
        denuncia=new Denuncia();
        denuncia.setDenunciaIdusuario(usuarioLogado());
        denuncia.setFechaDenuncia(new java.util.Date(System.currentTimeMillis()));
        if (filtroMisProductos.equals("comprados")){
            denuncia.setTipoDenuncia("NO_ENVIADO");
        }else {
            denuncia.setTipoDenuncia("NO_PAGADO");
        }
        
        denuncia.setMotivo(getMotivoDenuncia());
        denuncia.setVentaIdventa(ventaSeleccionada);
        denuncia.setAtendida(false);
        denuncia.setFechaAtencion(null);
        denuncia.setAtiendeIdusuario1(null);
        //denunciaFacade.create(denuncia);
        denunciaFacade.salva(denuncia);
        gestionEventos.fireRealizadaDenuncia(denuncia);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeDenunciaCreada")+denuncia.getTipoDenuncia(),"");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        
    }
    public void creaDenunciaMalClasificado(){
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("idProductoMalClasificado");
        
         Producto productoMalClasificado=productoFacade.find((Integer)Integer.parseInt(idProducto));
//          System.out.println("marcar como mal clasificado PRODUCTO "+productoMalClasificado.getNombre()+" idproducto "+idProducto );
         
         productoMalClasificado.setMarcadoMalClasificado(true);
         productoFacade.salva(productoMalClasificado);
         

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeMarcadoProducto")+productoMalClasificado.getNombre()+ResourcesUtil.getString("app.MensajeComoMalClasificado"),"");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        
    }    
    
    public void salvaVenta(Venta venta){

        ventaFacade.salva(venta);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeActualizadaVenta")+ venta.getFecha(),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public void salvaVenta(){

        ventaFacade.salva(ventaSeleccionada2);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeAcutalizadaVentaProducto")+ ventaSeleccionada.getProductoIdproducto().getNombre(),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public Usuario usuarioLogado(){
       
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         return(Usuario)session.getAttribute("usuario");
    }
    
        public void salvarProducto(){
        System.out.println("EN SALVAR PRODUCTO ");
                //recogemos los parametros necesarios
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("idProductoASalvar");
                  
                 
 
         
          //producto a salvar
           Producto productoASalvar=productoFacade.find((Integer)Integer.parseInt(idProducto));
           if(productoASalvar.getEnSubasta()){
                   pujasProducto=pujaFacade.pujaXProducto(productoASalvar);
          
              if((pujasProducto==null)||(pujasProducto.isEmpty())){//NO HAY PUJAS
                  productoASalvar.setNombre(productoSeleccionado.getNombre());
                  productoASalvar.setDescripcion(productoSeleccionado.getDescripcion());
                  productoASalvar.setPrecio(productoSeleccionado.getPrecio());
                  
                  
                  productoFacade.salva(productoASalvar);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeActualizadoValoresproducto")+productoASalvar.getNombre(),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);
                

              }else{
                  System.out.println("producto a Salvar con pujas "+idProducto);
                  productoASalvar.setNombre(productoSeleccionado.getNombre());
                  productoASalvar.setDescripcion(productoSeleccionado.getDescripcion());
                  
                  
                  
                  productoFacade.salva(productoASalvar);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeActualizadoValoresproducto")+productoASalvar.getNombre()+ResourcesUtil.getString("app.MensajeAcutalizaValoresProductoPuja"),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);
                  
                  
              }
               
           }else{
                  productoASalvar.setNombre(productoSeleccionado.getNombre());
                  productoASalvar.setDescripcion(productoSeleccionado.getDescripcion());
                  productoASalvar.setPrecio(productoSeleccionado.getPrecio());
                  
                  
                  productoFacade.salva(productoASalvar);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeActualizadoValoresproducto")+productoASalvar.getNombre(),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);
               
           }
        
        
    }
        public void borrarProductoNoVendido(){
                            //recogemos los parametros necesarios
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            String idProducto = (String) facesContext.getExternalContext().getRequestParameterMap().get("idProductoABorrar");
          //producto a salvar
           Producto productoABorrar=productoFacade.find((Integer)Integer.parseInt(idProducto));
           if(productoABorrar.getEnSubasta()){
                   pujasProducto=pujaFacade.pujaXProducto(productoABorrar);
          
              if((pujasProducto==null)||(pujasProducto.isEmpty())){//NO HAY PUJAS
                 
                  
                  productoFacade.remove(productoABorrar);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeProductoBorrado")+productoABorrar.getNombre(),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);                
              }else{
                  
                  for(Puja puja :pujasProducto){
                      gestionEventos.fireBorradoProductoConPujasEvent(puja);
                  }
                  
                  
                  productoFacade.remove(productoABorrar);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeProductoBorrado")+productoABorrar.getNombre(),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);    
              }
        }else{
                   productoFacade.remove(productoABorrar);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeProductoBorrado")+productoABorrar.getNombre(),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);
           }

        }
        public String borrarVenta(){
           //recogemos los parametros necesarios
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            String idVenta = (String) facesContext.getExternalContext().getRequestParameterMap().get("idVentaABorrar");
             Venta venta=ventaFacade.find((Integer)Integer.parseInt(idVenta));
             if (!venta.isCobrado()||!venta.isEnviado()||!venta.isPagado()||!venta.isRecibido()){
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeNoBorradaVenta"),"");
                   FacesContext.getCurrentInstance().addMessage(null, message);
                   return "index.xhtml?faces-redirect=true";  
             }else{
                  
                 
                   productoFacade.remove(venta.getProductoIdproducto());
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Borrada Venta,","");
                   FacesContext.getCurrentInstance().addMessage(null, message);
                   return "index.xhtml?faces-redirect=true";  
             }
            
        }
   public void quitarFiltros(){
       setBuscandoPorCategoria(true);
       setBuscandoPorNombre(false);
       setNombreBuscado("");
       setFiltro("todos");       
       todosProductos();
   }     
}
