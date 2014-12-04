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
import entidades.Usuario;
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
public class VentaFacade extends AbstractFacade<Venta> {
    @PersistenceContext(unitName = "Proyecto2014-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VentaFacade() {
        super(Venta.class);
    }
    public List<Venta> ventaXUsuarioCompradorRecibidos(Usuario usuarioP){
     try{
      Query  query=getEntityManager().createQuery(
                             "SELECT venta13 FROM Venta venta13 WHERE  venta13.compradorIdusuario = :usuarioP AND venta13.recibido=true ORDER BY venta13.fecha DESC");
      query.setParameter("usuarioP" , usuarioP);
         return query.getResultList();
      }catch (Exception e){
            System.out.println("@@@error en contrando ventas del usuario : "+usuarioP);
            System.out.println(e.toString());
        }
      return null;
    }
    public List<Venta> ventaXUsuarioCompradorNoRecibidos(Usuario usuarioP){
     try{
      Query  query=getEntityManager().createQuery(
                             "SELECT venta14 FROM Venta venta14 WHERE  venta14.compradorIdusuario = :usuarioP AND venta14.recibido=false ORDER BY venta14.fecha DESC");
      query.setParameter("usuarioP" , usuarioP);
         return query.getResultList();
      }catch (Exception e){
            System.out.println("@@@error en contrando ventas del usuario : "+usuarioP);
            System.out.println(e.toString());
        }
      return null;
    }
    
    public List<Venta> ventaXUsuarioVendedor(Usuario usuarioP){
     try{
      Query  query=getEntityManager().createQuery(
                             "SELECT venta12 FROM Venta venta12 WHERE  venta12.productoIdproducto.usuarioIdusuario = :usuarioP ORDER BY venta12.fecha DESC");
      query.setParameter("usuarioP" , usuarioP);
         return query.getResultList();
      }catch (Exception e){
            System.out.println("@@@error en contrando ventas del usuario : "+usuarioP);
            System.out.println(e.toString());
        }
      return null;
    }
    
    
    public List<Venta> ventaXUsuarioVendedorNoEnviados(Usuario usuarioP){
     try{
      Query  query=getEntityManager().createQuery(
                             "SELECT venta14 FROM Venta venta14 WHERE  venta14.productoIdproducto.usuarioIdusuario = :usuarioP AND venta14.enviado=false ORDER BY venta14.fecha DESC");
      query.setParameter("usuarioP" , usuarioP);
         return query.getResultList();
      }catch (Exception e){
            System.out.println("@@@error en contrando ventas del usuario : "+usuarioP);
            System.out.println(e.toString());
        }
      return null;
    }
    public List<Venta> ventaXUsuarioVendedorEnviados(Usuario usuarioP){
     try{
      Query  query=getEntityManager().createQuery(
                             "SELECT venta14 FROM Venta venta14 WHERE  venta14.productoIdproducto.usuarioIdusuario = :usuarioP AND venta14.enviado=true ORDER BY venta14.fecha DESC");
      query.setParameter("usuarioP" , usuarioP);
         return query.getResultList();
      }catch (Exception e){
            System.out.println("@@@error en contrando ventas del usuario : "+usuarioP);
            System.out.println(e.toString());
        }
      return null;
    }    
    public Venta ventaXProducto(Producto productoP){
     try{
      Query  query=getEntityManager().createQuery(
                             "SELECT venta4 FROM Venta venta4 WHERE  venta4.productoIdproducto = :produtoP");
      query.setParameter("productoP" , productoP);
         return (Venta)query.getSingleResult();
      }catch (Exception e){
            System.out.println("@@@error en contrando ventas del usuario : "+productoP);
            System.out.println(e.toString());
        }
      return null;
    }   
    public Venta salva(Venta venta){
      
        return em.merge(venta);
      
    }        
}
