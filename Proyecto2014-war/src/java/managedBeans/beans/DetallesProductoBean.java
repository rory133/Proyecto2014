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
import entidades.Imagen;
import entidades.Producto;
import entidades.Puja;
import facade.ImagenFacade;
import facade.ProductoFacade;
import facade.PujaFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import managedBeans.utilidades.ResourcesUtil;

/**
 *
 * @author juanma
 */
@ManagedBean
@SessionScoped
public class DetallesProductoBean {
    @EJB
    private ImagenFacade imagenFacade;
    @EJB
    private PujaFacade pujaFacade;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    GestionEventos gestionEventos;
    
    public Producto productoSeleccionado;
    private List<Imagen> imagenesProductoSeleccionado;
    long elapsedDays;
    long elapsedHours;
    long elapsedMinutes;
    long elapsedSeconds;
    private boolean soloMios;
    private List<Puja> pujasProducto;

    /**
     * Creates a new instance of DetallesProductoBean
     */
    public DetallesProductoBean() {
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

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
        setImagenesProductoSeleccionado(imagenFacade.imagenesXProcducto(productoSeleccionado));
        setTiempoRestante(this.productoSeleccionado.getFechaProducto());
        if (!productoSeleccionado.getEnSubasta()){
             
            productoSeleccionado.setUltimaPuja(0f);

        }else{
          List<Puja> pujasProducto2=pujaFacade.pujaXIdProducto(productoSeleccionado.getIdproducto());
           if((pujasProducto2==null)||(pujasProducto2.isEmpty())){
               productoSeleccionado.setUltimaPuja(productoSeleccionado.getPrecio());
           }else productoSeleccionado.setUltimaPuja(pujasProducto2.get(0).getOferta());
          
             
         }
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
    public void salvarProducto(){
//        System.out.println("EN SALVAR PRODUCTO ");
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
    public List<Imagen> getImagenesProductoSeleccionado() {
        return imagenesProductoSeleccionado;
    }

    public void setImagenesProductoSeleccionado(List<Imagen> imagenesProductoSeleccionado) {
        this.imagenesProductoSeleccionado = imagenesProductoSeleccionado;
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

    public boolean isSoloMios() {
        return soloMios;
    }

    public void setSoloMios(boolean soloMios) {
        this.soloMios = soloMios;
    }

    public List<Puja> getPujasProducto() {
        return pujasProducto;
    }

    public void setPujasProducto(List<Puja> pujasProducto) {
        this.pujasProducto = pujasProducto;
    }
    
}
