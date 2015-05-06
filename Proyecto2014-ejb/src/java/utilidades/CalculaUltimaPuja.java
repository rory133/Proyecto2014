/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package utilidades;

import entidades.Producto;
import entidades.Puja;
import facade.PujaFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 *
 * @author juanma
 */
public class CalculaUltimaPuja {
    @EJB
    private PujaFacade pujaFacade;
    
    private List<Puja> pujasProducto ;
    
//    @PostLoad
//    @PostPersist
//    @PostUpdate
    //calculamos el valor de la última puja para cada producto en modo subasta
    public void calculaUltimaPuja(Producto producto) {
        
     if ((producto==null)||(producto.getIdproducto()==null)){
          
      }else{
        if (!producto.getEnSubasta()){
            producto.setUltimaPuja(0f);
//            System.out.println("@Listener@@no esta en subasta "+producto.getNombre());
//            System.out.println("@Listener@@no esta en subasta "+producto.getEnSubasta());
        }else{
//             System.out.println("@Listener@@ si esta en subasta "+producto.getNombre());
//             System.out.println("@Listener@@ si esta en subasta "+producto.getEnSubasta());
//             System.out.println("@Listener@@idpructuo en subasta "+producto.getIdproducto().toString());
//             producto.setUltimaPuja(77);
           pujasProducto=null;
           pujasProducto=pujaFacade.pujaXIdProducto(producto.getIdproducto());
       System.out.println("********************************************");
       System.out.println("*pujasProducto*********"+ pujasProducto.size());
       System.out.println("********************************************");
       
              
           if((pujasProducto==null)||(pujasProducto.isEmpty())){
               producto.setUltimaPuja(producto.getPrecio());
           }else producto.setUltimaPuja(pujasProducto.get(0).getOferta());
              
        }
        
    }
   }
}
