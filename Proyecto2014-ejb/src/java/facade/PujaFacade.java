/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package facade;

import entidades.Producto;
import entidades.Puja;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author juanma
 */
@Stateless
public class PujaFacade extends AbstractFacade<Puja>  {
    @PersistenceContext(unitName = "Proyecto2014-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PujaFacade() {
        super(Puja.class);
    }
    public List<Puja> pujaXProducto(Producto productoP){
        List<Puja> lP=null;
        
//           System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
//           System.out.println("entro en pujaXIdProduto con "+productoP.getNombre());
//           System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        
        try{
        Query queryx=getEntityManager().createQuery(
//                             "SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto = :productoP ORDER BY puja2.fecha DESC");
                            "SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto.idproducto = :idProducto ORDER BY puja2.fecha DESC");
                //"SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto = :productoP");
        queryx.setParameter("idProducto" , productoP.getIdproducto());
         lP=queryx.getResultList();
        // System.out.println("Creating Timer...");
        }catch (Exception e){
            System.out.println("@@@error en contrando pujas del producto : "+productoP);
            System.out.println(e.toString());
        }
        return lP;
        
    }
    public List<Puja> pujaXIdProducto(Integer productoP){
        List<Puja> lP=null;
        
//           System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
//           System.out.println("entro en pujaXIdProduto con "+productoP);
//           System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        
        try{
        Query query=getEntityManager().createQuery(
                             "SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto.idproducto = :productoP ORDER BY puja2.oferta DESC");
                //"SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto = :productoP");
        query.setParameter("productoP" , productoP);
         lP=query.getResultList();
        // System.out.println("Creating Timer...");
        }catch (Exception e){
            System.out.println("@@@error en contrando pujas del producto : "+productoP);
            System.out.println(e.toString());
        }
        return lP;
        
    }
    public List<Puja> pujaXIdProducto2(Integer idproductoP){
        List<Puja> lP=null;
        
        try{
        Query query;
            query = getEntityManager().createQuery(
                    "SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto.idproducto = :productoP");
                //"SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto = :productoP");
        query.setParameter("productoP" , idproductoP);
         lP=query.getResultList();
        // System.out.println("Creating Timer...");
        }catch (Exception e){
            System.out.println("@@@error en contrando pujas del producto : "+idproductoP);
//            System.out.println(e.toString());
        }
        return lP;
        
    }
    
    public List<Puja> pujaXIdProducto3(Integer productoP){
        List<Puja> lP=null;
        
        try{
        Query query=getEntityManager().createQuery(
                             "SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto.idproducto = :productoP ORDER BY puja2.oferta DESC");
                //"SELECT puja2 FROM Puja puja2 WHERE puja2.productoIdproducto = :productoP");
        query.setParameter("productoP" , productoP);
         lP=query.getResultList();
        // System.out.println("Creating Timer...");
        }catch (Exception e){
            System.out.println("@@@error en contrando pujas del producto : "+productoP);
            System.out.println(e.toString());
        }
        return lP;
        
    }

}
