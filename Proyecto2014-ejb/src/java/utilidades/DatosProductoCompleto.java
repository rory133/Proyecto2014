/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package utilidades;

import entidades.Denuncia;
import entidades.Imagen;
import entidades.Producto;
import entidades.Puja;
import entidades.Venta;
import java.util.List;

/**
 *
 * @author juanma
 */
public class DatosProductoCompleto {
    
    
   Producto producto;
   Venta venta;
   List<Denuncia>denuncias;
   List<Imagen>imagenes;
   List<Puja>pujas;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<Denuncia> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Puja> getPujas() {
        return pujas;
    }

    public void setPujas(List<Puja> pujas) {
        this.pujas = pujas;
    }
   
   
   
    
}
