/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package managedBeans.beans;

import entidades.Login;
import entidades.Usuario;
import facade.LoginFacade;
import facade.UsuarioFacade;
import java.io.IOException;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedBeans.utilidades.ResourcesUtil;
import utilidades.Loggable;

/**
 *
 * @author juanma
 */
//@Named(value = "loginBean")
//@Dependent
@Loggable
@RequestScoped
//@ViewScoped
@ManagedBean(name="loginBean")
public class LoginBean {
@EJB
private LoginFacade loginFacade;

@EJB
private UsuarioFacade usuarioFacade;

private Login usuarioLogado;
private Usuario datosUsuarioLogado;

private FacesMessage facesMessage;
private final FacesContext faceContext;
private String login;

private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String loginb) {
        login = loginb;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordb) {
        password = passwordb;
    }






    public Login getUsuarioLogado() {
        
        if (usuarioLogado == null){
            usuarioLogado = new Login();
            usuarioLogado.setUsuarioIdusuario(new Usuario());
        }
        
        
        
        
        return usuarioLogado;
    }

    public void setUsuarioLogado(Login usuarioLogadoB) {
        this.usuarioLogado = usuarioLogadoB;
    }



    public LoginBean() {
         faceContext=FacesContext.getCurrentInstance();
    }
public String validarUsuario(){
   // String outcome="login";
      //  System.out.println("@@@ValidarUsuario: login "+ getLogin()+" password: "+ getPassword());
      if (!(getLogin()==null) && !(getPassword()==null)){
        usuarioLogado= loginFacade.ValidarLogin(login,  password);
       // logger.info("@@@@@@@despues de encontrar usuario : "+usuarioLogado.getLogin());
        //System.out.println("usuario encontrado: "+usuarioLogado.getLogin());
        if(usuarioLogado!=null){
            HttpSession session = 
           
            (HttpSession) faceContext.getExternalContext().getSession(false);
            session.setAttribute("login", usuarioLogado);
            datosUsuarioLogado=(Usuario)usuarioFacade.find(usuarioLogado.getUsuarioIdusuario().getIdusuario());
            session.setAttribute("usuario", datosUsuarioLogado);
            
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO,ResourcesUtil.getString("app.AccesoCorrecto"), null);
            session.setMaxInactiveInterval(2000*60*60);
            /*
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Welcome " + usuarioLogado.getLogin() ));
            faceContext.addMessage(null, facesMessage);
                    */
            if (usuarioLogado.getRole().toString().matches("ROLE_ADMIN")){
                session.setAttribute("ROLE_ADMIN", true);
                session.setAttribute("ROLE_SOCIO", false);
//                System.out.println("es adminstrador" );
                return "/admin/index?faces-redirect=true";
            }else if (usuarioLogado.getRole().toString().matches("ROLE_SOCIO")){
                if (usuarioLogado.getUsuarioIdusuario().getVotosNegativos()>2){
//                      System.out.println("<<<<<<<Bloqueadoooo>>>>>>>>");
                    return "usuarioBloqueado?faces-redirect=true";
                }
                session.setAttribute("ROLE_ADMIN", false);
                session.setAttribute("ROLE_SOCIO", true);
//                  System.out.println("es socio");
                 return "/usuario/index?faces-redirect=true";
            }else
                System.out.println("na de na: "+usuarioLogado.getRole());
            
           // return "acceso";
        } else {

            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourcesUtil.getString("app.MensajeUsuarioContraErroneo"), null);
            faceContext.addMessage(null, facesMessage);
               FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(ResourcesUtil.getString("app.MensajeVuelvaAProbar") ));
            return "login";
     
        
        }
    
    
      }
      return "login";
   }
public void logout(){  
//    System.out.println("entro en logout()");
    String retorno;
    retorno = "../index?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();

        ExternalContext externalContext = context.getExternalContext();

        Object session = externalContext.getSession(false);

        HttpSession httpSession = (HttpSession) session;

        httpSession.invalidate();
    
        try {
//        System.out.println("contexto---->:"+FacesContext.getCurrentInstance().getExternalContext().toString());
        FacesContext.getCurrentInstance().getExternalContext()
        .redirect("../index.xhtml");
        } catch (IOException e) {
        e.printStackTrace();
        }
     }


        


}
