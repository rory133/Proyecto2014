/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;


import ejbs.GestionEventos;
import entidades.Denuncia;
import entidades.Login;
import entidades.Usuario;
import facade.DenunciaFacade;
import facade.LoginFacade;
import facade.UsuarioFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author juanma
 */
@ManagedBean(name = "gestionUsuariosAdministradorBean")
//@ViewScoped
@SessionScoped
public class GestionUsuariosAdministrador implements Serializable {


    public GestionUsuariosAdministrador() {
    }
    
@EJB
private DenunciaFacade denunciaFacade;
    
@EJB
private UsuarioFacade usuarioFacade;

@EJB
private LoginFacade loginFacade;

@EJB
GestionEventos gestionEventos;

private List<Usuario> listaUsuarios;

private List<Login> listaLogins;

private Login loginSeleccionado;

private Usuario usurarioSeleccionado;

private String filtro;// todos soloSocios soloAdministradores soloBloqueados

private HttpSession session;  

private FacesMessage facesMessage;
private FacesContext facesContext;

///////////////////////////////////////////
    public Login getLoginSeleccionado() {
        return loginSeleccionado;
    }

    public void setLoginSeleccionado(Login loginSeleccionado) {
        this.loginSeleccionado = loginSeleccionado;
    }



    public List<Login> getListaLogins() {
        return listaLogins;
    }

    public void setListaLogins(List<Login> listaLogins) {
        this.listaLogins = listaLogins;
    }


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

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
    
    
    
///////////////////////////////////////////
        @PostConstruct
    public void init() {
         facesContext=FacesContext.getCurrentInstance();
         setFiltro("todos");
//         listadoUsuarios2();
         listadoLogins();
    }
    /*
    public void listadoUsuarios(){
    setListaUsuarios(null);
    List<Usuario> listaUsuariosTemp =  usuarioFacade.findAll();
    
    if(filtro.equals("todos")){
        setListaUsuarios(listaUsuariosTemp);
    }else{
       List<Usuario> listaUsuariosTemp2=new ArrayList<>();
       for(Usuario usuario:listaUsuariosTemp){
          switch (filtro) {
            case "soloBloqueados":
                if (usuario.getVotosNegativos()>2){
                     System.out.println("WWWWWWWWWWWWWWWWWsoloBloqueadosWWWWWWWWWWWWWWWWW");
                    listaUsuariosTemp2.add(usuario);
                    
                }
                break;
            case "soloSocios":
                if (usuario.getLogin()!=null && usuario.getLogin().getRole().equals("ROLE_SOCIO")){
                     System.out.println("WWWWWWWWWWWWWWWWWsoloSociosWWWWWWWWWWWWWWWWW_ "+ usuario.getIdusuario());
                    listaUsuariosTemp2.add(usuario);
                    
                }
                break;
            case "soloAdministradores":
                if (usuario.getLogin()!=null && usuario.getLogin().getRole().equals("ROLE_ADMIN")){
                     System.out.println("WWWWWWWWWWWWWWWWWsoloAdminsitradoresWWWWWWWWWWWWWWWWW_ "+ usuario.getIdusuario() );
                    listaUsuariosTemp2.add(usuario);
                    
                }
                break;
            default:
                    System.out.println("WWWWWWWWWWWWWWWWW DEFOULT sWWWWWWWWWWWWWWWWW_ "+ usuario.getIdusuario() );
                    listaUsuariosTemp2.add(usuario);
              break;
          }
       } 
       setListaUsuarios(listaUsuariosTemp2);
     }   

    }
    */
    public void listadoLogins(){
    setListaLogins(null);

    
    if(filtro.equals("todos")){
         
        setListaLogins(loginFacade.findAll());
    }else{
       
       
          switch (filtro) {
            case "soloBloqueados":
                
                   
                    setListaLogins(loginFacade.usuariosBloqueados());
                    
                
                break;
            case "soloSocios":
                
                   
                    setListaLogins(loginFacade.usuariosSocios());
                   
                   
                break;
            case "soloAdministradores":
                
                 
                   setListaLogins(loginFacade.usuariosAdministradores());
                break;
            default:
                     setListaLogins(loginFacade.findAll());
              break;
          }
      
      }   

    }
   
    
    /*
    public void listadoUsuarios2(){
    setListaUsuarios(null);

    
    if(filtro.equals("todos")){
         
        setListaUsuarios(usuarioFacade.findAll());
    }else{
       
       
          switch (filtro) {
            case "soloBloqueados":
                
                   
                    setListaUsuarios(usuarioFacade.usuariosBloqueados());
                    
                
                break;
            case "soloSocios":
                
                   
                    setListaUsuarios(usuarioFacade.usuariosSocios());
                   
                   
                break;
            case "soloAdministradores":
                
                 
                   setListaUsuarios(usuarioFacade.usuariosAdministradores()); 
                break;
            default:
                     setListaUsuarios(usuarioFacade.findAll());
              break;
          }
      
      }   

    }
    */
     public void sumarVotoNegativoUsuario(){
        
        
             facesContext = FacesContext.getCurrentInstance();
             HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

            String denunciaPasada = (String) facesContext.getExternalContext().getRequestParameterMap().get("denunciaAVotar");

          if(denunciaPasada!=null){
                Denuncia denuncia=denunciaFacade.find((Integer)Integer.parseInt(denunciaPasada));
                Usuario usuario;
                //seleccionamos al usuario al que sumar los votos
                if (denuncia.getTipoDenuncia().equals("NO_ENVIADO")){
                usuario = denuncia.getVentaIdventa().getProductoIdproducto().getUsuarioIdusuario();
                }else{
                    usuario=denuncia.getVentaIdventa().getCompradorIdusuario();
                }
                Integer votos=usuario.getVotosNegativos();
                votos+=1;
                //enviamos un mensaje al usuario informandole que ha sido bloquedo
                if (votos==3){
                    gestionEventos.fireUsuarioBloqueadoEvent(usuario);
                }
                usuario.setVotosNegativos(votos);
                usuarioFacade.salva(usuario);


                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"sumado voto negativo al usuario "+usuario.getNombre()," ahora tiene: "+usuario.getVotosNegativos());
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
          }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"no hay usuario","");
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
      }
    }
     
      public void redimirUsuario(){
             facesContext = FacesContext.getCurrentInstance();
             HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
             String usuarioPasado = (String) facesContext.getExternalContext().getRequestParameterMap().get("usuarioARedimir");
             Usuario usuario = usuarioFacade.find((Integer)Integer.parseInt(usuarioPasado));
             usuario.setVotosNegativos(0);
             //enviamos un mensaje al usuario informandole que ha sido redimido
             gestionEventos.fireUsuarioRedimidoEvent(usuario);
             usuarioFacade.salva(usuario);
             
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"ha sido redimido el usuario "+usuario.getNombre()," ahora tiene: "+usuario.getVotosNegativos());
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);
             
          
      }
    
    public void borrarUsuario(){
        facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         Usuario usuario;           
         usuario=(Usuario) session.getAttribute("usuario");
         
         
         String usuarioPasado = (String) facesContext.getExternalContext().getRequestParameterMap().get("usuarioABorrar");
         Usuario usuarioAborrar = usuarioFacade.find((Integer)Integer.parseInt(usuarioPasado));
         if(usuarioAborrar.equals(usuario)){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"no te puedes eliminar a ti mismo ","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
             
         }else{
             //enviamos un mensaje al usuario informandole que ha sido borrado como usuario en el portal
            gestionEventos.fireUsuarioBorradoEvent(usuarioAborrar);
            usuarioFacade.remove(usuarioAborrar);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"se ha borrado el usuario "+usuarioAborrar.getNombre(),"");
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
         }
        
    }
}
