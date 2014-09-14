/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanma
 */
@Entity
@Table(name = "puja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Puja.findAll", query = "SELECT p FROM Puja p"),
    @NamedQuery(name = "Puja.findByIdpuja", query = "SELECT p FROM Puja p WHERE p.idpuja = :idpuja"),
    @NamedQuery(name = "Puja.findByFecha", query = "SELECT p FROM Puja p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Puja.findByOferta", query = "SELECT p FROM Puja p WHERE p.oferta = :oferta")})
public class Puja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpuja")
    private Integer idpuja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oferta")
    private float oferta;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;
    @JoinColumn(name = "producto_idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Producto productoIdproducto;

    public Puja() {
    }

    public Puja(Integer idpuja) {
        this.idpuja = idpuja;
    }

    public Puja(Integer idpuja, Date fecha, float oferta) {
        this.idpuja = idpuja;
        this.fecha = fecha;
        this.oferta = oferta;
    }

    public Integer getIdpuja() {
        return idpuja;
    }

    public void setIdpuja(Integer idpuja) {
        this.idpuja = idpuja;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getOferta() {
        return oferta;
    }

    public void setOferta(float oferta) {
        this.oferta = oferta;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public Producto getProductoIdproducto() {
        return productoIdproducto;
    }

    public void setProductoIdproducto(Producto productoIdproducto) {
        this.productoIdproducto = productoIdproducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpuja != null ? idpuja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Puja)) {
            return false;
        }
        Puja other = (Puja) object;
        if ((this.idpuja == null && other.idpuja != null) || (this.idpuja != null && !this.idpuja.equals(other.idpuja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Puja[ idpuja=" + idpuja + " ]";
    }
    
}
