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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
