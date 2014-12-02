/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author juanma
 */

@ManagedBean(name = "administradorBean")
@SessionScoped
public class AdministradorBean {

    private String filtroAdministrador;
    private boolean creandoCategoria;
    
    
    /**
     * Creates a new instance of AdministradorBean
     */
    public AdministradorBean() {
    }
    
    @PostConstruct
    public void init() {
     setFiltroAdministrador("Taxonomias");
     setCreandoCategoria(false);
    }

    public String getFiltroAdministrador() {
        return filtroAdministrador;
    }

    public void setFiltroAdministrador(String filtroAdministrador) {
        this.creandoCategoria =false;
        this.filtroAdministrador = filtroAdministrador;
    }

    public boolean isCreandoCategoria() {
        return creandoCategoria;
    }

    public void setCreandoCategoria(boolean creandoCategoria) {
        this.creandoCategoria = creandoCategoria;
    }
    
    
    
}
