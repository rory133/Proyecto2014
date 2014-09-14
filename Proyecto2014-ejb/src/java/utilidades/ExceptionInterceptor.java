/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package utilidades;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author juanma
 */
@Interceptor
@Priority(150)
@Loggable
public class ExceptionInterceptor implements Serializable {

    @Inject
    private Logger log;

    @AroundInvoke
    public Object catchException(InvocationContext ic) throws Exception {
        try {
            return ic.proceed();
        } catch (Exception e) {
            System.out.println("*catchException");
            addErrorMessage("prubaaaaaaaaaaaaaa "+e.getMessage());
         log.severe("/!\\ " + ic.getTarget().getClass().getName() + " - " + ic.getMethod().getName() + " - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // TODO to refactor with Controller methods
    protected void addErrorMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR , message +"   holita", null));
    }
}
