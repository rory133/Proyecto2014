/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package ejbs;


import entidades.Denuncia;
import entidades.Producto;
import entidades.Puja;
import entidades.Venta;
import facade.ProductoFacade;
import facade.PujaFacade;
import facade.VentaFacade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

/**
 *
 * @author juanma
 */
//@Singleton
//@Startup
@Stateless
public class SubastaTimer {
    
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private PujaFacade pujaFacade;
    @EJB
    private GestionEventos gestionEventos;
    @EJB
    private VentaFacade ventaFacade;
    private List<Producto> listaProductos ;
    private List<Puja> listaPujas;
    private Venta venta;
        
    @Resource
    TimerService timerService;
    
    
//    @PostConstruct
//    public void createTimer(){
//        System.out.println("Creating Timer...");
//        //cada segundo comprobaremos si ha espirado algun producto en subasta.
//        Timer timer =timerService.createIntervalTimer(10000, 10000, null);
//        //Timer timer = timerService.createTimer(1000, "Timer has been created...");
//        //.createSingleActionTimer(null, null)
//    }
    @Asynchronous
    public void creaTemporizador(Producto producto){
         System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO@@@@@@@@@@@@@creado temporizador para producto ..."+ producto.getNombre());
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 7);
        cal.add(Calendar.MINUTE, 5);
        Timer timer = timerService.createSingleActionTimer(cal.getTime(), new TimerConfig(producto,false));
       // timerService.createCalendarTimer(cal.getTime(), new TimerConfig(producto, false));
       
    }
    
    
    @Timeout
    public void timeout(Timer timer){
        
       // System.out.println("ejecutado el timercillo...");
//        listaProductos=productoFacade.productosNoExpirados();
//         for(Producto producto:listaProductos){
//                         
//           // System.out.println("ejecutado el timercillo..."+producto.getNombre());
//         }
        Producto producto=(Producto)timer.getInfo();
        System.out.println("tiempo expirado para producto ..."+ producto.getNombre());
        producto.setExpirado(true);
        productoFacade.salva(producto);
        listaPujas=pujaFacade.pujaXProducto(producto);
        if((listaPujas==null)||(listaPujas.isEmpty())){
            //aunque no tenga pujas se informa a vendedor
            gestionEventos.fireExpiradoTiempoSubasta(producto);
            
        }else{
            // si ha tenido pujas generamos un venta y avisamos a comprador y vendedor
            venta=new Venta();
            venta.setCompradorIdusuario(listaPujas.get(0).getUsuarioIdusuario());
            venta.setDenunciaList(new ArrayList<Denuncia>());
            venta.setEnviado(false);
            venta.setPrecioFinal(listaPujas.get(0).getOferta());
            venta.setProductoIdproducto(listaPujas.get(0).getProductoIdproducto());
            venta.setRecibido(false);
            venta.setFecha(new java.util.Date(System.currentTimeMillis()));
            ventaFacade.create(venta);
            producto.setVendido(true);
            productoFacade.salva(producto);
            gestionEventos.fireExpiradoTiempoSubasta(producto);
        }
        
    }
}

