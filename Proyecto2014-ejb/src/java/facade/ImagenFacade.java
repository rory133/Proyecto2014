/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package facade;

import entidades.Imagen;
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
public class ImagenFacade extends AbstractFacade<Imagen> {
    @PersistenceContext(unitName = "Proyecto2014-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImagenFacade() {
        super(Imagen.class);
    }
    
    
    public List<Imagen> imagenesXProcducto(Producto productoIdproductoP){
            
        
        
        try{
            Query query=
                    getEntityManager().createQuery(
                    "SELECT imagen FROM Imagen imagen WHERE Imagen.productoIdproducto = :productoIdproductoP order by Imagen.idimagen");
            query.setParameter("productoIdproductoP" , productoIdproductoP);
            
            
            return (List<Imagen>) query.getResultList();
            
        }catch (Exception e){
            System.out.println("@@@no encontradas imagenes para el producto: "+e.toString());
            return null;
        }
        
    }
        public Imagen imageneUnicaXIdProducto(Integer productoIdproductoP){
            
        
        
        try{
            Query query=
                    getEntityManager().createQuery(
                    "SELECT imagen FROM Imagen imagen WHERE Imagen.productoIdproducto.idproducto = :productoIdproductoP order by Imagen.idimagen");
            query.setParameter("productoIdproductoP" , productoIdproductoP);
            
            List<Imagen> lista=(List<Imagen>) query.getResultList();
            return lista.get(0);
            
            
        }catch (Exception e){
            System.out.println("@@@no encontradas imagenes para el producto: "+e.toString());
            return null;
        }
        
    }
    
}
