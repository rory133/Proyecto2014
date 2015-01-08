/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;

import static com.sun.faces.context.flash.ELFlash.getFlash;
import ejbs.EmailService;
import ejbs.GestionEventos;
import entidades.Login;
import entidades.Usuario;
import facade.LoginFacade;
import facade.UsuarioFacade;
//import java.awt.Event;
import java.io.IOException;
import java.util.Observable;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import utilidades.Loggable;


/**
 *
 * @author juanma
 */
@Loggable
@RequestScoped
@ManagedBean(name="userBean")
public class UserBean {
    


@EJB
private LoginFacade loginFacade;

@EJB
private UsuarioFacade usuarioFacade;

@EJB 
EmailService emailService;

@EJB
GestionEventos gestionEventos;

private FacesMessage facesMessage;
private final FacesContext faceContext;

private Login login;
private Usuario usuario;
private boolean crearComoAdministrador;

//@Inject
//@SumadoSocio   
//Event <Login> usuarioSumadoEvent;
  


@Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")
@Size(min=4,max=45)
private String nombre;

 @Pattern(regexp = "(^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$)*")
private String apellidos;

@Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" + 
        "@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
private String email;


@Pattern(regexp = "(^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])*")
private String web;


@Size(min=4,max=100)
private String localizacion;

@Pattern(regexp = "^[a-zA-Z0-9]+(([',.-][a-zA-Z0-9])?[a-zA-Z0-9]*)*$")
@Size(min=4,max=45)
private String loginUsuario;


@Pattern(regexp = "^[a-zA-Z0-9]+(([',.-][a-zA-Z0-9])?[a-zA-Z0-9]*)*$")
@Size(min=4,max=20)
private String password;

private String role;


    public UserBean() {
        faceContext=FacesContext.getCurrentInstance();
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isCrearComoAdministrador() {
        return crearComoAdministrador;
    }

    public void setCrearComoAdministrador(boolean crearComoAdministrador) {
        this.crearComoAdministrador = crearComoAdministrador;
    }



  public String crearUsuario(){
   
      System.out.println("@@@crear Usuario: login "+ getLogin()+" password: "+ getPassword());
//      if (!(getLoginUsuario()==null) && !(getPassword()==null)){
       login = loginFacade.ValidarLogin(getLoginUsuario(),  getPassword());
//      }
      if (login==null){
          
           System.out.println("@@@crear Usuario no existe");
          usuario=new Usuario();
          usuario.setApellidos(getApellidos());
          usuario.setEmail(getEmail());
          usuario.setLocalizacion(getLocalizacion());
          usuario.setNombre(getNombre());
          usuario.setWeb(getWeb());
          usuario.setVotosNegativos(0);           
          usuarioFacade.create(usuario);
          
          login=new Login();
          login.setLogin(getLoginUsuario());
          login.setUsuarioIdusuario(usuario);
          login.setPassword(getPassword());
          login.setRole("ROLE_SOCIO");
          
          loginFacade.create(login);
          //se llama a gestionEventos para genere el evento correspondiente a crear un usuario
          gestionEventos.fireUsuarioSumadoEvent(login);
         
          
          

          
          facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "usuario creado correctamente", null);
          
          faceContext.addMessage(null, facesMessage);
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Bienvendio "  ));
               
               
             
               
               //mantenemos los menasajes en la redireccion
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
             return "index.xhtml?faces-redirect=true";
          
          
//        FacesContext.getCurrentInstance().addMessage(null,
//        new FacesMessage("Usuario creado correctamente "  ));
          
          
//        try {
//        System.out.println("contexto---->:"+FacesContext.getCurrentInstance().getExternalContext().toString());
//        facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario creado correctamente", null);
//        FacesContext.getCurrentInstance().getExternalContext()
//        .redirect("index.xhtml");
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
          
          
           
          
      }else{
          System.out.println("@@@crear Usuario SI QUE existe");
//          FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage("Usuario existente "  ));
          
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario ya existe", null);    
            faceContext.addMessage(null, facesMessage);
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Vuelva a probar "  ));
            return "crearUsuario";
          
//           facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "usuario existente", null);
//            try {
//        System.out.println("contexto---->:"+FacesContext.getCurrentInstance().getExternalContext().toString());
//        FacesContext.getCurrentInstance().getExternalContext()
//        .redirect("crearUsuario.xhtml");
//        } catch (IOException e) {
//        e.printStackTrace();
//        }
       }
  }
  
 public String crearUsuarioPorAdministrador(){
   
      

       login = loginFacade.ValidarLogin(getLoginUsuario(),  getPassword());

      if (login==null){
          
           
          usuario=new Usuario();
          usuario.setApellidos(getApellidos());
          usuario.setEmail(getEmail());
          usuario.setLocalizacion(getLocalizacion());
          usuario.setNombre(getNombre());
          usuario.setWeb(getWeb());
          usuario.setVotosNegativos(0);           
          usuarioFacade.create(usuario);
          
          login=new Login();
          login.setLogin(getLoginUsuario());
          login.setUsuarioIdusuario(usuario);
          login.setPassword(getPassword());
          if(crearComoAdministrador){
               System.out.println("@@@SE CREA COMO ADMINISTRADOR");
              login.setRole("ROLE_ADMIN");
          }
          else{
              System.out.println("@@@SE CREA COMO SOCIO");
              login.setRole("ROLE_SOCIO");
          }
         loginFacade.create(login);
         
         
          
          facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "usuario creado correctamente", null);
          
//          faceContext.addMessage(null, facesMessage);
//               FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage(" "  ));
               
              
               gestionEventos.fireUsuarioSumadoEvent(login);
               
               
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
             return "index.xhtml?faces-redirect=true";
          

          
           
          
      }else{
 
          
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario ya existe", null);    
            faceContext.addMessage(null, facesMessage);
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Vuelva a probar "  ));
            return "crearUsuarioPorAdministrador";
          

       }
  }
   public void addMessage() {
        String summary = "Se creará un nuevo Administrador";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
}
