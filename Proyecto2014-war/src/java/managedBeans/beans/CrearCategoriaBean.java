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
import facade.CategoriaFacade;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import managedBeans.utilidades.CategoriasBean;
import managedBeans.utilidades.SelectionView;


/**
 *
 * @author juanma
 */
@ManagedBean(name = "crearCategoriaBean")
//@SessionScoped
@ViewScoped
//@RequestScoped
public class CrearCategoriaBean {

    @EJB 
    CategoriaFacade categoriaFacade;

    @ManagedProperty("#{administradorBean}")
    private AdministradorBean administrador;
    
    @ManagedProperty("#{gestorCategorias}")
    private CategoriasBean  seleccionarCategorias;
        
    private HttpSession session;     
        
    public CrearCategoriaBean() {
    }
    private FacesContext facesContext;
    private String nombre;
    private Categoria categoriaSeleccionada;
    private Categoria categoriaSeleccionadaParaEditar;
    private Categoria categoriaSeleccionadaParaPadre;
    
    private String descripcion;
    private String NombrePadre;
    
    private Categoria padre;
    
    private boolean comoRaiz;
    private boolean cambiarPadre;
    
    private boolean editandoCategoria;

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

    public Categoria getPadre() {
        return padre;
    }

    public boolean isComoRaiz() {
        return comoRaiz;
    }

    public void setComoRaiz(boolean comoRaiz) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
      
       if (comoRaiz){
//         System.out.println(" puesta idCategoriaPadre en contexto a -1 comoRaiz: "+comoRaiz);   
        externalContext.getSessionMap().put("idCategoriaPadre", -1);
        setCambiarPadre(false);
       }
        this.comoRaiz = comoRaiz;
    }

    public boolean isCambiarPadre() {
        return cambiarPadre;
    }

    public void setCambiarPadre(boolean cambiarPadre) {
//        System.out.println(" actualizado cambiarPadre: "+cambiarPadre); 
        if(cambiarPadre){
        setComoRaiz(false);
        }
        this.cambiarPadre = cambiarPadre;
    }
    
    
    public void setPadre(Categoria padre) {
        this.padre = padre;
    }

    public boolean isEditandoCategoria() {
        return editandoCategoria;
    }

    public void setEditandoCategoria(boolean editandoCategoria) {
        this.editandoCategoria = editandoCategoria;
    }

    public String getNombrePadre() {
        return NombrePadre;
    }

    public void setNombrePadre(String NombrePadre) {
        this.NombrePadre = NombrePadre;
    }

    public Categoria getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(Categoria categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public AdministradorBean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(AdministradorBean administrador) {
        this.administrador = administrador;
    }

    public Categoria getCategoriaSeleccionadaParaEditar() {
        return categoriaSeleccionadaParaEditar;
    }

    public void setCategoriaSeleccionadaParaEditar(Categoria categoriaSeleccionadaParaEditar) {
        this.categoriaSeleccionadaParaEditar = categoriaSeleccionadaParaEditar;
    }

    public Categoria getCategoriaSeleccionadaParaPadre() {
        return categoriaSeleccionadaParaPadre;
    }

    public void setCategoriaSeleccionadaParaPadre(Categoria categoriaSeleccionadaParaPadre) {
        this.categoriaSeleccionadaParaPadre = categoriaSeleccionadaParaPadre;
    }

    public CategoriasBean getSeleccionarCategorias() {
        return seleccionarCategorias;
    }

    public void setSeleccionarCategorias(CategoriasBean seleccionarCategorias) {
        this.seleccionarCategorias = seleccionarCategorias;
    }

    
    
    
    
    
    
    @PostConstruct
    public void init() {

        setComoRaiz(false);
        setEditandoCategoria(false);
        setCambiarPadre(false);
        setCategoriaSeleccionada(null);
        facesContext=FacesContext.getCurrentInstance();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("idCategoria", -1);
    }
        
    
    public String guardarCategoria(){
        System.out.println(" entro en guardar Categoria: ");
        facesContext = FacesContext.getCurrentInstance();
        session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Integer categoriaSeleccionadaPadreNuevaCategoria=(Integer)session.getAttribute("idCategoria");
//        System.out.println(" categoria padre seleccionada "+categoriaSeleccionadaPadreNuevaCategoria);
        if (((categoriaSeleccionadaPadreNuevaCategoria == null) || (categoriaSeleccionadaPadreNuevaCategoria<0))&&!comoRaiz) {
           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES SELECCIONAR UNA CATEGORIA o marcarla como categoria raiz","SELECCIONA UNA EN EL PANEL IZQUIERDO");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);            
//            System.out.println(" sin seleccionar padre: ");
            return "index.xhtml?faces-redirect=true";
        }else{
            
            setPadre(categoriaFacade.find(categoriaSeleccionadaPadreNuevaCategoria));
           
            Categoria categoria=new Categoria();
            categoria.setDescripcion(descripcion);
            categoria.setNombre(nombre);
            categoria.setIdPadre(padre);
            categoriaFacade.create(categoria);
            seleccionarCategorias.actualiza();
            seleccionarCategorias.init();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("idCategoria", -1);
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"creada Categoria "+ categoria.getNombre()," exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
                return "index.xhtml?faces-redirect=true";
            
            
        }
        
    }
    public String salvarCategoriaCambiada(){
//        System.out.println(" entro en salvar Categoria: ");
//         System.out.println(" comoRaiz: "+comoRaiz);
//          System.out.println(" cambiarPadre: "+cambiarPadre);
//          System.out.println("getPadre(): "+getPadre());
//          System.out.println("padre: "+padre);
          
        if ((!comoRaiz)&& (!cambiarPadre)) {
           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debes seleccionar o bien crearla como raiz o cambiar Padre","como raiz: "+comoRaiz);
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
            return "index.xhtml?faces-redirect=true";
       
           

        }  

        else if ((!comoRaiz)&& (cambiarPadre)&&(getPadre()==null)) {
           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debes seleccionar una nueva categoria para el padre o marcarla como categoria raiz ","SELECCIONA UNA EN EL PANEL IZQUIERDO");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
            return "index.xhtml?faces-redirect=true";
       
           

        }else if ((!comoRaiz)&& (cambiarPadre)&&(getPadre()!=null)) {
            if (getCategoriaSeleccionadaParaEditar().getIdcategoria()==getCategoriaSeleccionadaParaPadre().getIdcategoria()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"no puedes asignar una categoria como su propio padre","");
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);  
                return "index.xhtml?faces-redirect=true";
                }
            else{
            

            
            
            
            if(comoRaiz)
            {
                
            setPadre(null);
            }
//            else{
//            setPadre(padre);
//            }
            //categoriaSeleccionadaParaEditar
            categoriaSeleccionadaParaEditar.setDescripcion(descripcion);
            categoriaSeleccionadaParaEditar.setNombre(nombre);
            categoriaSeleccionadaParaEditar.setIdPadre(getPadre());
            
//            System.out.println(" salvando categoria : ");
//            System.out.println(" ______________________ ");
//           System.out.println(" id : "+categoriaSeleccionadaParaEditar.getIdcategoria());
//           System.out.println("  nombre : "+categoriaSeleccionadaParaEditar.getNombre());
//           System.out.println(" descripcion : "+categoriaSeleccionadaParaEditar.getDescripcion());
//           System.out.println(" nombre padre : "+categoriaSeleccionadaParaEditar.getIdPadre().getNombre());
//            System.out.println(" ______________________ ");
            categoriaFacade.salva(categoriaSeleccionadaParaEditar);
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("idCategoria",-1);
            externalContext.getSessionMap().put("idCategoriaPadre",-1);
            setNombre("");
            setDescripcion("");
            setPadre(null);
            seleccionarCategorias.actualiza();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"salvada Categoria "+ categoriaSeleccionada.getNombre()," exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
            
               return "index.xhtml?faces-redirect=true";
        }
       }if (comoRaiz){
           
            categoriaSeleccionadaParaEditar.setDescripcion(descripcion);
            categoriaSeleccionadaParaEditar.setNombre(nombre);
            categoriaSeleccionadaParaEditar.setIdPadre(null);
            
//            System.out.println(" salvando categoria : ");
//            System.out.println(" ______________________ ");
//           System.out.println(" id : "+categoriaSeleccionadaParaEditar.getIdcategoria());
//           System.out.println("  nombre : "+categoriaSeleccionadaParaEditar.getNombre());
//           System.out.println(" descripcion : "+categoriaSeleccionadaParaEditar.getDescripcion());
//           
//            System.out.println(" ______________________ ");
            categoriaFacade.salva(categoriaSeleccionadaParaEditar);
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("idCategoria",-1);
            externalContext.getSessionMap().put("idCategoriaPadre",-1);
            setNombre("");
            setDescripcion("");
            setPadre(null);
            seleccionarCategorias.actualiza();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"salvada Categoria "+ categoriaSeleccionada.getNombre()," exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
            
               return "index.xhtml?faces-redirect=true";
           
           
       }
          return "index.xhtml?faces-redirect=true";
      
    }
    public String borrarCategoria(){
                    
            categoriaFacade.remove(categoriaSeleccionadaParaEditar);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("idCategoria",-1);
            externalContext.getSessionMap().put("idCategoriaPadre",-1);
            setNombre("");
            setDescripcion("");
            setPadre(null);
            seleccionarCategorias.actualiza();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Borrada Categoria "+ categoriaSeleccionadaParaEditar.getNombre()," exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
            
               return "index.xhtml?faces-redirect=true";
        
        
    }

    public void asignarPadre(){
        //session = (HttpSession) facesContext.getExternalContext().getSession(false);
        seleccionarCategorias.sumaCategoriaPadre();
        //seleccionarCategorias.sumaCategoria();
        Integer categoriaSeleccionada=(Integer)session.getAttribute("idCategoriaPadre");
        
        setPadre(categoriaFacade.find(categoriaSeleccionada));
        setCategoriaSeleccionadaParaPadre(getPadre());
        
//        System.out.println(" asignando padre: "+getCategoriaSeleccionadaParaPadre());
//        System.out.println(" id asignando padre: "+getCategoriaSeleccionadaParaPadre().getIdcategoria());
//        System.out.println(" categoria Editando: "+getCategoriaSeleccionadaParaEditar());
//        System.out.println(" id categoria Editando: "+getCategoriaSeleccionadaParaEditar().getIdcategoria());
        if (getCategoriaSeleccionadaParaEditar().getIdcategoria()==getCategoriaSeleccionadaParaPadre().getIdcategoria()){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"no puedes asignar una categoria como su propio padre","");
          
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true); 
            
        }else{
            setComoRaiz(false);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"asignado padre "+ getPadre().getNombre()," exitosamente");
           
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true); 
        }
     }   
    
    
    public void editarCategoria(){
        
        facesContext = FacesContext.getCurrentInstance();
        session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Integer idcategoriaSeleccionada=(Integer)session.getAttribute("idCategoria");
//        System.out.println(" entro en editar Categoria: idcategoriaSeleccionada "+idcategoriaSeleccionada);
        setCategoriaSeleccionada(categoriaFacade.find(idcategoriaSeleccionada));
        setCategoriaSeleccionadaParaEditar(getCategoriaSeleccionada());
        administrador.setCreandoCategoria(false);
        if ((categoriaSeleccionadaParaEditar == null)) {
           
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"DEBES SELECCIONAR UNA CATEGORIA para poder editarla","SELECCIONA UNA EN EL PANEL IZQUIERDO");
            FacesContext.getCurrentInstance().addMessage(null, message);
//            return "index.xhtml?faces-redirect=true";
        }else{
            
            //setPadre(categoriaFacade.find(categoriaSeleccionada));
            // setComoRaiz(false);
            setEditandoCategoria(true);
//            Categoria categoria=new Categoria();
//            categoria.setDescripcion(descripcion);
//            categoria.setNombre(nombre);
//            categoria.setIdPadre(categoriaFacade.find(categoriaSeleccionada).getIdPadre());
//          Categoria  categoria=categoriaFacade.find(categoriaSeleccionada);
            setNombre(categoriaSeleccionadaParaEditar.getNombre());
//             System.out.println(" nombre  Categoria: "+getNombre());
            setDescripcion(categoriaSeleccionadaParaEditar.getDescripcion());
//             System.out.println(" descipcion  Categoria: "+getDescripcion());
             
             setPadre(categoriaSeleccionadaParaEditar.getIdPadre());
              if(categoriaSeleccionadaParaEditar.getIdPadre()!=null)
              {
//                  System.out.println(" padre: "+getPadre().getNombre());
                  setNombrePadre(getPadre().getNombre());
                  setComoRaiz(false);
              }else{
                  setNombrePadre("sin padre, categoria raiz");
                   setComoRaiz(true);
              }

         
            
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            externalContext.getSessionMap().put("idCategoria", 0);
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"editando Categoria "+ categoriaSeleccionada.getNombre()," exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
//                return "index.xhtml?faces-redirect=true";
            
            
        }
        
    }    
    
}
