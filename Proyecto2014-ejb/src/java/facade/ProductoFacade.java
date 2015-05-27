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
    public Producto productosXIdProducto(Integer idproducto){
        
        Query query=em.createNamedQuery("Producto.findByIdproducto");
        query.setParameter(1, idproducto);
        Producto lP =(Producto)query.getSingleResult();
        return lP;
    }
    
    public List<Producto> productosNoExpirados(){
        Query query5=getEntityManager().createQuery(
                             "SELECT producto5 FROM Producto producto5 WHERE producto5.expirado=false");
        List<Producto> lP =query5.getResultList();
        return lP;
    }
    public Producto salva(Producto producto){
        return em.merge(producto);
      
    }
    
    public List<Producto> productosXNombreParcial(String nombreP){
        
        
        List<Producto> lP=null;
//        System.out.println("@@@psumado parametro antes del try  : "+"%"+nombreP+"%");
        try{
            Query query2=
                    //em.createQuery(
                    getEntityManager().createQuery(
                     "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");
            
//            System.out.println("@@@psumado parametro depues del try  despues de crear query: "+"%"+nombreP+"%");
            

            query2.setParameter("nombreP" , "%"+nombreP+"%");
//            System.out.println("@@@psumado parametro despues del try despues de añadir parametros  : "+"%"+nombreP+"%");
//            System.out.println("@@@psumado parametro  : "+"%"+nombreP+"%");
            lP =query2.getResultList();
//            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
//            System.out.println("@@@error en contrando : "+nombreP);
//            System.out.println(e.toString());
        }
//    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
    public List<Producto> productosXNombreParcialYFiltro(String nombreP,String filtro){
        
       
               
        List<Producto> lP=null;
//        System.out.println("@@@psumado parametro antes del try  : "+"%"+nombreP+"%");
        try{
            
            
            
            Query query2
                    
                         =getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");
                    //em.createQuery(

            
            switch (filtro) {
                    case "todos":
                            query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");

                        break;
                        
                     case "ventaDirecta":


                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=false");
                        break;

                     case "subasta":
                           query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP AND producto2.enSubasta=true");
                        break;
                     default:
                            query2=getEntityManager().createQuery(
                             "SELECT producto2 FROM Producto producto2 WHERE producto2.nombre like :nombreP");
                    }
            
          

            query2.setParameter("nombreP" , "%"+nombreP+"%");
            lP =query2.getResultList();

        }catch (Exception e){

        }

        return lP;
    }
    
    
    
    public List<Producto> productosXNombreParcial(String nombreP,String filtro, String vendidos){
        
       
               
        List<Producto> lP=null;
//        System.out.println("@@@psumado parametro antes del try  : "+"%"+nombreP+"%");
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
            
            
            
            
            
//            System.out.println("@@@psumado parametro depues del try  despues de crear query: "+"%"+nombreP+"%");
            

            query2.setParameter("nombreP" , "%"+nombreP+"%");
//            System.out.println("@@@psumado parametro despues del try despues de añadir parametros  : "+"%"+nombreP+"%");
//            System.out.println("@@@psumado parametro  : "+"%"+nombreP+"%");
            lP =query2.getResultList();
//            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
//            System.out.println("@@@error en contrando : "+nombreP);
        }
//    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
    
    
    public List<Producto> productosXCategoria(Integer categoriaP){
        
        
        List<Producto> lP=null;
//        System.out.println("@@@idcategoria  de la que buscamos pruductos  : "+"%"+categoriaP+"%");
        try{
            Query query2=
                    //em.createQuery(
                    getEntityManager().createQuery(
                    "SELECT producto3 FROM Producto producto3 WHERE producto3.categoriaIdcategoria.idcategoria = :categoriaP");
           query2.setParameter("categoriaP" , categoriaP);        
           lP =query2.getResultList();
            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
//            System.out.println("@@@error en contrando productos de la categoria : "+categoriaP);
//            System.out.println(e.toString());
        }
//    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
    
    
    public List<Producto> productosXCategoria(Integer categoriaP,String filtro, String vendidos){
        
        
        List<Producto> lP=null;
//        System.out.println("@@@idcategoria  de la que buscamos pruductos  : "+"%"+categoriaP+"%");
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
//            System.out.println("@@@encontrados  : "+lP.size());
        }catch (Exception e){
//            System.out.println("@@@error en contrando productos de la categoria filtrada: "+categoriaP);
//             System.out.println(e.toString());
        }
//    System.out.println("@@@ amtes de devolver lP");
        return lP;
    }
    public List<Producto> todosProductosXFiltro(String filtro){
        System.out.println("encontrando todos los producotos con filtro = "+filtro);
                List<Producto> lP=null;
        try{
            Query query3=
                    //em.createQuery(
                    getEntityManager().createQuery(
                    "SELECT producto4 FROM Producto producto4");
            /////////////////////////////////////
            
            switch (filtro) {
                    case "todos":
                           System.out.println("encontrando todos los producotos con filtro = todos ");
                            query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4");
                            System.out.println("encontrando todos los producotos con filtro todos");
                        break;
                    
                    case "ventaDirecta":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=false");
                            break;
                    case "subasta":
                            query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=true");
                             break;
                    case "noVendidos":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=true  AND producto4.vendido=false");

                    default:
                            query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4");
                        System.out.println("encontrando todos los producotos con filtro default");
                    }
        
             
            lP =query3.getResultList();

        }catch (Exception e){
              System.out.println("@@@error en contrando todos los producotos con filtro : "+e.toString());
                        }
    
        return lP;
    }
        
    public List<Producto> todosProductosFiltrados(String filtro, String vendidos){
                List<Producto> lP=null;
//        System.out.println("@@@todos filtrados ");
//        System.out.println("vendidos : "+ vendidos);
//        System.out.println("filtro : "+ filtro);
        try{
            Query query3=
                    //em.createQuery(
                    getEntityManager().createQuery(
                    "SELECT producto4 FROM Producto producto4");
            /////////////////////////////////////
            
            switch (filtro) {
                    case "todos":
                        switch (vendidos) {
                        case "todos":

                            query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4");

                        break;
                        case "noVendidos":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.vendido=false");



                        break;
                        case "yaVendidos":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.vendido=true");


                        break;
                        }





                    break;
                    case "ventaDirecta":

                        switch (vendidos) {
                        case "todos":

                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=false");



                        break;
                        case "noVendidos":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=false   AND producto4.vendido=false");



                        break;
                        case "yaVendidos":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=false   AND producto4.vendido=true");


                        break;
                        }



                        break;
                    case "subasta":
                        switch (vendidos) {
                        case "todos":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=true");




                        break;
                        case "noVendidos":
                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=true  AND producto4.vendido=false");



                        break;
                        case "yaVendidos":

                           query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4 WHERE  producto4.enSubasta=true  AND producto4.vendido=true");

                        break;
                        }



                    break;
                    default:
                            query3=getEntityManager().createQuery(
                             "SELECT producto4 FROM Producto producto4");
                    }
        
             
            lP =query3.getResultList();

            
//            lP =(List<Producto>)query3.getSingleResult();
        }catch (Exception e){
              System.out.println("@@@error en contrando todos los producotos : "+e.toString());
                        }
    
        return lP;
    }
    
    
    
}