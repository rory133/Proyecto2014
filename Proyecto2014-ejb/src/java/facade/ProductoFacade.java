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
public class ProductoFacade extends AbstractFacade<Producto> {
    @PersistenceContext(unitName = "Proyecto2014-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    public List<Producto> productosXNombreExacto(String nombre){
        
        Query query=em.createNamedQuery("Producto.findByNombre");
        query.setParameter(1, nombre);
        List<Producto> lP =query.getResultList();
        return lP;
    }
    
    public List<Producto> productosXNombreParcial(String nombreP){
        
        
        List<Producto> lP=null;
        System.out.println("@@@psumado parametro antes del try  : "+"%"+nombreP+"%");
        try{
            Query query2=
                    //em.createQuery(
                    getEntityManager().createQuery(
                     "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");
            
            System.out.println("@@@psumado parametro depues del try  despues de crear query: "+"%"+nombreP+"%");
            
//           "SELECT producto FROM Producto producto WHERE producto.nombre like '%' + replace(:nombreP, '%', '[%]') + '%'");
//             "SELECT producto FROM Producto producto WHERE producto.nombre like :escape:nombreP:escape");
           // query.setParameter("escape" , '%');
            query2.setParameter("nombreP" , "%"+nombreP+"%");
            System.out.println("@@@psumado parametro despues del try despues de añadir parametros  : "+"%"+nombreP+"%");
            System.out.println("@@@psumado parametro  : "+"%"+nombreP+"%");
            lP =query2.getResultList();
            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
            System.out.println("@@@error en contrando : "+nombreP);
        }
    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
    
    
    
    public List<Producto> productosXNombreParcial(String nombreP,String filtro, String vendidos){
        
        
        List<Producto> lP=null;
        System.out.println("@@@psumado parametro antes del try  : "+"%"+nombreP+"%");
        try{
            
            
            
            Query query2
                    
                         =getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");
                    //em.createQuery(

            
            switch (filtro) {
                    case "todos":
                        switch (vendidos) {
                        case "todos":
                            query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");

                        break;
                        case "noVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.vendido=false");



                        break;
                        case "yaVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.vendido=true");


                        break;
                        }





                    break;
                    case "ventaDirecta":

                        switch (vendidos) {
                        case "todos":

                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=false");



                        break;
                        case "noVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=false   AND producto2.vendido=false");



                        break;
                        case "yaVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=false   AND producto2.vendido=true");


                        break;
                        }



                        break;
                    case "subasta":
                        switch (vendidos) {
                        case "todos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=true");




                        break;
                        case "noVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=true  AND producto2.vendido=false");



                        break;
                        case "yaVendidos":

                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=true  AND producto2.vendido=true");

                        break;
                        }



                    break;
                    default:
                            query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");
                    }
            
            
            
            
            
            System.out.println("@@@psumado parametro depues del try  despues de crear query: "+"%"+nombreP+"%");
            
//           "SELECT producto FROM Producto producto WHERE producto.nombre like '%' + replace(:nombreP, '%', '[%]') + '%'");
//             "SELECT producto FROM Producto producto WHERE producto.nombre like :escape:nombreP:escape");
           // query.setParameter("escape" , '%');
            query2.setParameter("nombreP" , "%"+nombreP+"%");
            System.out.println("@@@psumado parametro despues del try despues de añadir parametros  : "+"%"+nombreP+"%");
            System.out.println("@@@psumado parametro  : "+"%"+nombreP+"%");
            lP =query2.getResultList();
            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
            System.out.println("@@@error en contrando : "+nombreP);
        }
    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
    
    
    public List<Producto> productosXCategoria(Integer categoriaP){
        
        
        List<Producto> lP=null;
        System.out.println("@@@idcategoria  de la que buscamos pruductos  : "+"%"+categoriaP+"%");
        try{
            Query query2=
                    //em.createQuery(
                    getEntityManager().createQuery(
                    "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP");
           query2.setParameter("categoriaP" , categoriaP);        
           lP =query2.getResultList();
            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
            System.out.println("@@@error en contrando productos de la categoria : "+categoriaP);
        }
    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
        public List<Producto> productosXCategoria(Integer categoriaP,String filtro, String vendidos){
        
        
        List<Producto> lP=null;
        System.out.println("@@@idcategoria  de la que buscamos pruductos  : "+"%"+categoriaP+"%");
        try{
            Query query2=
                    //em.createQuery(
                    getEntityManager().createQuery(
                    "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP");
            /////////////////////////////////////
            
            switch (filtro) {
                    case "todos":
                        switch (vendidos) {
                        case "todos":
                            query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP");

                        break;
                        case "noVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.vendido=false");



                        break;
                        case "yaVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.vendido=true");


                        break;
                        }





                    break;
                    case "ventaDirecta":

                        switch (vendidos) {
                        case "todos":

                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.enSubasta=false");



                        break;
                        case "noVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.enSubasta=false   AND producto3.vendido=false");



                        break;
                        case "yaVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.enSubasta=false   AND producto3.vendido=true");


                        break;
                        }



                        break;
                    case "subasta":
                        switch (vendidos) {
                        case "todos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.enSubasta=true");




                        break;
                        case "noVendidos":
                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.enSubasta=true  AND producto3.vendido=false");



                        break;
                        case "yaVendidos":

                           query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP AND producto3.enSubasta=true  AND producto3.vendido=true");

                        break;
                        }



                    break;
                    default:
                            query2=getEntityManager().createQuery(
                             "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP");
                    }
            
            
            
            
            
            
            
            ///////////////////////////
           query2.setParameter("categoriaP" , categoriaP);        
           lP =query2.getResultList();
            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
            System.out.println("@@@error en contrando productos de la categoria : "+categoriaP);
        }
    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
    
    
    
    
}