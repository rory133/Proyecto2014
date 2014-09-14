/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package facade;

import entidades.Login;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author juanma
 */
@Stateless
public class LoginFacade extends AbstractFacade<Login> {
    @PersistenceContext(unitName = "Proyecto2014-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginFacade() {
        super(Login.class);
    }
    
    public Login ValidarLogin (String loginB, String passwordB){
        Login encontrado=null;
        System.out.println("@@@entro en validarLogin con login:  " +loginB+" passwordB: "+passwordB);
        try{
            Query query=
                    getEntityManager().createQuery(
                    "SELECT log FROM Login log WHERE log.login = :loginB and log.password = :passwordB");
            query.setParameter("loginB" , loginB);
            query.setParameter ("passwordB" , passwordB);
            
            encontrado=(Login) query.getSingleResult();
            System.out.println("@@@en validarLogin encontrado: "+encontrado.getLogin().toString());
        }catch (Exception e){
            System.out.println("@@@error en ValidarLogin: "+e.toString());
        }
         
        return encontrado;
            
    } 
        
    
    
}
