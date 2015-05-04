/*
 * Proyecto fin de carrera genï¿½rico del Dpto.  * 
 * Lenguajes y Sistemas Informï¿½ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */
package managedBeans.utilidades;

/**
 *
 * @author juanma
 */

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LocaleBean implements Serializable{
    
       private Locale locale;

    @PostConstruct
    public void init() {

        
        

//        if(null==FacesContext.getCurrentInstance())
//             locale = new Locale.Builder().setLanguage("es").setRegion("es").build();
//        else
//             locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
         locale = new Locale.Builder().setLanguage("es").setRegion("es").build();
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
