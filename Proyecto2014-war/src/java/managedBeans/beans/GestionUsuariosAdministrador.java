/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;


import entidades.Usuario;
import facade.DenunciaFacade;
import facade.UsuarioFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author juanma
 */
@ManagedBean(name = "gestionUsuariosAdministradorBean")
@ViewScoped
public class GestionUsuariosAdministrador {


    public GestionUsuariosAdministrador() {
    }
    
@EJB
private DenunciaFacade denunciaFacade;
    
@EJB
private UsuarioFacade usuarioFacade;


private List<Usuario> listaUsuarios;

private Usuario usurarioSeleccionado;

private String filtro;// todos soloSocios soloAdministradores soloBloquados



///////////////////////////////////////////


    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsurarioSeleccionado() {
        return usurarioSeleccionado;
    }

    public void setUsurarioSeleccionado(Usuario usurarioSeleccionado) {
        this.usurarioSeleccionado = usurarioSeleccionado;
    }
    
///////////////////////////////////////////
    
    
    
    
    
}
