/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package facade;

import entidades.Denuncia;
import entidades.Venta;
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
public class DenunciaFacade extends AbstractFacade<Denuncia> {
    @PersistenceContext(unitName = "Proyecto2014-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DenunciaFacade() {
        super(Denuncia.class);
    }
    public Denuncia salva(Denuncia denuncia){

        return em.merge(denuncia);
      
    }
    
    public List<Denuncia> denunciaXVenta(Venta ventaP){
     try{
      Query  query=getEntityManager().createQuery(
                             "SELECT denuncia2 FROM Denuncia denuncia2 WHERE denuncia2.ventaIdventa = :ventaP");
      query.setParameter("ventaP" , ventaP);
         return query.getResultList();
      }catch (Exception e){
            System.out.println("@@@error en contrando denuncia de la venta : "+ventaP);
            System.out.println(e.toString());
        }
      return null;
    }
    
}
