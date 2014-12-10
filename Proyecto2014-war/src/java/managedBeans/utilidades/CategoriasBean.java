/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.utilidades;

import entidades.Categoria;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import managedBeans.beans.CrearProductoBean;
import managedBeans.beans.ListadoProductosBean;
import org.primefaces.model.TreeNode;
import utilidades.Loggable;

/**
 *
 * @author juanma
 */
//@Named(value = "selectionView")
//@Dependent
//@RequestScoped

@ManagedBean(name="gestorCategorias")
//@SessionScoped
@ViewScoped  

//@RequestScoped
//@Loggable
//public class SelectionView implements Serializable {
public class CategoriasBean implements Serializable  {
 private Categoria categoriaSelec;
 private String nombreCategoria;
 private Integer idCategoria;
 private TreeNode root1;
 private  FacesContext faceContext;
// @EJB
 //private ListadoProductosBean listadoProductoBean;
//    private TreeNode root2;
//    private TreeNode root3;
    private TreeNode selectedNode;
//    private TreeNode[] selectedNodes1;
//    private TreeNode[] selectedNodes2;
     
    @ManagedProperty("#{documentServiceGestionCategorias}")
    private DocumentServiceGestionCategorias service;
     
    @PostConstruct
    public void init() {
        root1 = service.createDocuments();
        faceContext=FacesContext.getCurrentInstance();
        
//        root2 = service.createDocuments();
//        root3 = service.createDocuments();
    }
 
    public TreeNode getRoot1() {
        return root1;
    }
 
//    public TreeNode getRoot2() {
//        return root2;
//    }
// 
//    public TreeNode getRoot3() {
//        return root3;
//    }
 
    public TreeNode getSelectedNode() {
        
        return selectedNode;
        
    }
 
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
            categoriaSelec =  (Categoria)selectedNode.getData();
            setNombreCategoria(categoriaSelec.getNombre());
//            setIdCategoria(categoriaSelec.getIdcategoria());
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            externalContext.getSessionMap().put("idCategoria", getIdCategoria());
           System.out.println("***seleccionada categoria:  "+ getNombreCategoria());
//             System.out.println("******en setSeletedNode****entro en suma categoria se actualiza IsCategoria  "+ getIdCategoria());

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Seleccionada: ", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
    }
 

 
    public void setService(DocumentServiceGestionCategorias service) {
        this.service = service;
    }

    public Categoria getCategoriaSelec() {
        return categoriaSelec;
    }

    public void setCategoriaSelec(Categoria categoriaSelec) {
        this.categoriaSelec = categoriaSelec;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    
 
    public void displaySelectedSingle() {
        System.out.println("**********entro en displaySelectedSingle  ");
        
        if(selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "sin categoria seleccionada", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }
     
    public void displaySelectedMultiple(TreeNode[] nodes) {
        if(nodes != null && nodes.length > 0) {
            StringBuilder builder = new StringBuilder();
 
            for(TreeNode node : nodes) {
                builder.append(node.getData().toString());
                builder.append("<br />");
            }
 
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
        public void sumaCategoria() {
        if(selectedNode != null) {
           
            System.out.println("**********entro en suma categoria con  "+ selectedNode.getData().toString());
           
           // System.out.println("entro en suma categoria con "+ categoriaSelec.getNombre());
            categoriaSelec =  (Categoria)selectedNode.getData();
            setNombreCategoria(categoriaSelec.getNombre());
            setIdCategoria(categoriaSelec.getIdcategoria());
            
             System.out.println("**********entro en suma categoria se actualiza nombrecategoria  "+ getNombreCategoria());
             System.out.println("**********entro en suma categoria se actualiza IsCategoria  "+ getIdCategoria());
            //////////////////////////////////
//            HttpSession session =            
//            (HttpSession) faceContext.getExternalContext().getSession(false);
//            session.setAttribute("idCategoria", getIdCategoria());
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("idCategoria", getIdCategoria());
            /////////////////////////////////////
            
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Seleccionada categoria", categoriaSelec.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, message);
            
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected","debes seleccionar una categoria");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }
         public void sumaCategoriaPadre() {
        if(selectedNode != null) {
           
            System.out.println("**********entro en suma categoria padre con  "+ selectedNode.getData().toString());
           
           // System.out.println("entro en suma categoria con "+ categoriaSelec.getNombre());
            categoriaSelec =  (Categoria)selectedNode.getData();
            setNombreCategoria(categoriaSelec.getNombre());
            setIdCategoria(categoriaSelec.getIdcategoria());
            
             System.out.println("**********entro en suma categoria padre se actualiza nombrecategoria  "+ getNombreCategoria());
             System.out.println("**********entro en suma categoria padre se actualiza IsCategoriaPadre  "+ getIdCategoria());
            //////////////////////////////////
//            HttpSession session =            
//            (HttpSession) faceContext.getExternalContext().getSession(false);
//            session.setAttribute("idCategoria", getIdCategoria());
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("idCategoriaPadre", getIdCategoria());
            /////////////////////////////////////
            
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Seleccionada categoriaPadre", categoriaSelec.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
            
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected","debes seleccionar una categoria para el padre");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);  
        }
        
    }
         
         public void actualiza(){
             service.createDocuments();
         }
       
}
