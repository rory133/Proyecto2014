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
import entidades.Denuncia;
import entidades.Usuario;
import entidades.Venta;
import facade.DenunciaFacade;
import facade.VentaFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import managedBeans.utilidades.ResourcesUtil;

/**
 *
 * @author juanma
 */
@ManagedBean
@SessionScoped
public class GestionVentas {
    
    @EJB
    private VentaFacade ventaFacade;
    @EJB
    private DenunciaFacade denunciaFacade;
    @EJB
    GestionEventos gestionEventos;
    
    private List<Venta> listaVentas;
    private String ventasAMostrar;
    private Denuncia denuncia;
    private Venta ventaSeleccionada;
    private Venta ventaSeleccionada2;
    private String motivoDenuncia;
    private String tipoDenuncia;
    private String filtroMisProductos;
    private FacesMessage facesMessage;
    private FacesContext facesContext;


    /**
     * Creates a new instance of GestionVentas
     */
    public GestionVentas() {
    }
    
       @PostConstruct
    public void init() {

       facesContext=FacesContext.getCurrentInstance();
       setFiltroMisProductos("ofertados");
       setVentasAMostrar("NoEnviados");
      

//       todosProductosSinFiltro();
       
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
    
    public Usuario usuarioLogado(){
       
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         return(Usuario)session.getAttribute("usuario");
    }
    public void salvaVenta(){

        ventaFacade.salva(ventaSeleccionada2);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.MensajeAcutalizadaVentaProducto")+ ventaSeleccionada.getProductoIdproducto().getNombre(),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
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
    public void actualizaVistaVentas(){
        System.out.println("entro actualizaVistaVentas //////////////// ");
        switch (filtroMisProductos) {
            case "comprados":
                switch (ventasAMostrar) {
                    case "Recibidos":
                    misProductosCompradosRecibidos();
                    
                    break;
                    case "NoRecibidos":
                    misProductosCompradosNORecibidos();
                    break;
                    
                }
            
            break;
            case "vendidos":
                    switch (ventasAMostrar) {
                    case "Enviados":
                    misProductosVendidosEnviados();
                    
                    break;
                    case "NoEnviados":
                    misProductosVendidosNoEnviados();
                    break;
                    
                }
            
            break;
            
        }
    }
    

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public String getVentasAMostrar() {
        return ventasAMostrar;
    }

    public void setVentasAMostrar(String ventasAMostrar) {
        this.ventasAMostrar = ventasAMostrar;
    }

    public Venta getVentaSeleccionada2() {
        return ventaSeleccionada2;
    }

    public void setVentaSeleccionada2(Venta ventaSeleccionada2) {
        this.ventaSeleccionada2 = ventaSeleccionada2;
    }

    public Venta getVentaSeleccionada() {
        return ventaSeleccionada;
    }

    public void setVentaSeleccionada(Venta ventaSeleccionada) {
        this.ventaSeleccionada = ventaSeleccionada;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
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

    public String getFiltroMisProductos() {
        return filtroMisProductos;
    }

    public void setFiltroMisProductos(String filtroMisProductos) {
        System.out.println(" Cambio filtro mis productos a: "+ filtroMisProductos );
        this.filtroMisProductos = filtroMisProductos;
    }
    
    
    
    
}
