/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.utilidades;

import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author juanma
 */
public class ResourcesUtil {

 
    public static String getString(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        
//       generamos texto segun locale
        ResourceBundle text = ResourceBundle.getBundle("resources.mensajes.mensajes", context.getViewRoot().getLocale());
        return text.getString(key);
        

    }

    
}
