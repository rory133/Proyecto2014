/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.utilidades;

import com.google.common.collect.Table;
import entidades.Categoria;
import facade.CategoriaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import utilidades.Loggable;

/**
 *
 * @author juanma
 */
//@Named(value = "documentService")
//@RequestScoped
@ViewScoped
@ManagedBean(name = "documentService")
//@ApplicationScoped
//@Loggable
public class DocumentService  implements Serializable {

       @EJB 
       CategoriaFacade categoriaFacade;
       List<Categoria> categorias;
       List<Categoria> categoriasRaiz;
       TreeNode root;
       Map<String, TreeNode> rootNodes;
    public List<Categoria> seleccionaRaiz (List<Categoria> categorias){
            System.out.println( "entro en seleccionaRaiz");
            List<Categoria> listaCategoriasTemporal = new ArrayList<Categoria>();
           
            for(Categoria cate:categorias){
                if (cate.getIdPadre()==null){
                    listaCategoriasTemporal.add(cate);

                }
            }
            return listaCategoriasTemporal;
        }
        public List<Categoria> seleccionaHijos (Integer IdPadre){
             List<Categoria> listaCategoriasTemporal = new ArrayList<Categoria>();
       //      System.out.println( "entro en seleccionaHijos");
         
            for(Categoria cate:categorias){
                if (cate.getIdPadre()!=null&&cate.getIdPadre().getIdcategoria()==IdPadre){
                    listaCategoriasTemporal.add(cate);

                }
            } 
        //     System.out.println( "salgo de seleccionaHijos");
           // if (listaCategoriasTemporal.isEmpty()) contador=0; else contador++;
            return listaCategoriasTemporal;
        }
        public void recorreArbol (List<Categoria> categorias){
            // List<Categoria> listaCategoriasTemporal = new ArrayList<Categoria>();
        //     System.out.println( "entro en recorreArbol");
         
            //contador++;
           
           // Map<String, TreeNode> rootNodes = new HashMap<String, TreeNode>();
            if (!categorias.isEmpty()){
            for(Categoria cate:categorias){
               String nombre=cate.getNombre();
               if (cate.getIdPadre()==null){
//                    rootNodes.put(cate.getNombre(), new DefaultTreeNode(cate.getNombre(), cate, root));
                   TreeNode documents = new DefaultTreeNode(cate, root);
                   rootNodes.put(cate.getIdcategoria().toString(), documents);
                   recorreArbol(seleccionaHijos(cate.getIdcategoria()));
                }else{
                   TreeNode documents = new DefaultTreeNode(cate, rootNodes.get(cate.getIdPadre().getIdcategoria().toString()));
                   rootNodes.put(cate.getIdcategoria().toString(), documents);
                   recorreArbol(seleccionaHijos(cate.getIdcategoria()));
               }
             }
            }
            //TreeNode documents = new DefaultTreeNode(new Categoria(1,"primera"), root);
            
//            TreeNode work = new DefaultTreeNode(new Categoria(2,"pakita"), documents);
//            TreeNode otra = new DefaultTreeNode(new Categoria(2,"otra"), documents);
//            TreeNode otra2 = new DefaultTreeNode(new Categoria(2,"otra2"), otra);
//            TreeNode otra3 = new DefaultTreeNode(new Categoria(2,"otra3"), otra2);
//             TreeNode otra4 = new DefaultTreeNode(new Categoria(2,"otra3"), otra3);
//              TreeNode otra5 = new DefaultTreeNode(new Categoria(2,"otra4"), otra4);
//               TreeNode otra6 = new DefaultTreeNode(new Categoria(2,"otra5"), otra5);
        }
    
    public TreeNode createDocuments() {
        /*
        TreeNode root = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);
         
        TreeNode documents = new DefaultTreeNode(new Document("Documents", "-", "Folder"), root);
        TreeNode pictures = new DefaultTreeNode(new Document("Pictures", "-", "Folder"), root);
        TreeNode movies = new DefaultTreeNode(new Document("Movies", "-", "Folder"), root);
         
        TreeNode work = new DefaultTreeNode(new Document("Work", "-", "Folder"), documents);
        TreeNode primefaces = new DefaultTreeNode(new Document("PrimeFaces", "-", "Folder"), documents);
         
        //Documents
        TreeNode expenses = new DefaultTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
        TreeNode resume = new DefaultTreeNode("document", new Document("Resume.doc", "10 KB", "Word Document"), work);
        TreeNode refdoc = new DefaultTreeNode("document", new Document("RefDoc.pages", "40 KB", "Pages Document"), primefaces);
         
        //Pictures
        TreeNode barca = new DefaultTreeNode("picture", new Document("barcelona.jpg", "30 KB", "JPEG Image"), pictures);
        TreeNode primelogo = new DefaultTreeNode("picture", new Document("logo.jpg", "45 KB", "JPEG Image"), pictures);
        TreeNode optimus = new DefaultTreeNode("picture", new Document("optimusprime.png", "96 KB", "PNG Image"), pictures);
         
        //Movies
        TreeNode pacino = new DefaultTreeNode(new Document("Al Pacino", "-", "Folder"), movies);
        TreeNode deniro = new DefaultTreeNode(new Document("Robert De Niro", "-", "Folder"), movies);
         
        TreeNode scarface = new DefaultTreeNode("mp3", new Document("Scarface", "15 GB", "Movie File"), pacino);
        TreeNode carlitosWay = new DefaultTreeNode("mp3", new Document("Carlitos' Way", "24 GB", "Movie File"), pacino);
         
        TreeNode goodfellas = new DefaultTreeNode("mp3", new Document("Goodfellas", "23 GB", "Movie File"), deniro);
        TreeNode untouchables = new DefaultTreeNode("mp3", new Document("Untouchables", "17 GB", "Movie File"), deniro);
         
        */
     
       //categoriaFacade.findAll();
        rootNodes = new HashMap<String, TreeNode>();
       categorias =categoriaFacade.findAll();
       categoriasRaiz=seleccionaRaiz(categorias);
       //TreeNode root = new DefaultTreeNode(null, null);
      root = new DefaultTreeNode("raiz",new Categoria(), null);
      
       recorreArbol(categoriasRaiz);
        //documents = new DefaultTreeNode(new Categoria(), root);
       
       
       
        return root;
    }
    
}
