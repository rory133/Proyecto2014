/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package facade;

import entidades.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "Proyecto2014-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public List<Usuario> usuariosSocios(){
        Query query=getEntityManager().createQuery(
                             "SELECT usuario22 FROM Usuario usuario22 WHERE usuario22.login.role='ROLE_SOCIO'");
        List<Usuario> lP =query.getResultList();
        return lP;
    }
    
    public List<Usuario> usuariosAdministradores(){
        Query query=getEntityManager().createQuery(
                             "SELECT usuario22 FROM Usuario usuario22 WHERE usuario22.login.role='ROLE_ADMIN'");
        List<Usuario> lP =query.getResultList();
        return lP;
    }
    
    public List<Usuario> usuariosBloqueados(){
        Query query=getEntityManager().createQuery(
                             "SELECT usuario22 FROM Usuario usuario22 WHERE usuario22.votosNegativos>2");
        List<Usuario> lP =query.getResultList();
        return lP;
    }
    
}
