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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanma
 */
@Entity
@Table(name = "mala_clasificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MalaClasificacion.findAll", query = "SELECT m FROM MalaClasificacion m"),
    @NamedQuery(name = "MalaClasificacion.findByIdmalaClasificacion", query = "SELECT m FROM MalaClasificacion m WHERE m.idmalaClasificacion = :idmalaClasificacion"),
    @NamedQuery(name = "MalaClasificacion.findByFechaCreacion", query = "SELECT m FROM MalaClasificacion m WHERE m.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "MalaClasificacion.findByFechaAtencion", query = "SELECT m FROM MalaClasificacion m WHERE m.fechaAtencion = :fechaAtencion")})
public class MalaClasificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmala_clasificacion")
    private Integer idmalaClasificacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;
    @JoinColumn(name = "usuario_atiende", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAtiende;
    @JoinColumn(name = "usuario_avisa", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAvisa;
    @JoinColumn(name = "producto_idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Producto productoIdproducto;

    public MalaClasificacion() {
    }

    public MalaClasificacion(Integer idmalaClasificacion) {
        this.idmalaClasificacion = idmalaClasificacion;
    }

    public Integer getIdmalaClasificacion() {
        return idmalaClasificacion;
    }

    public void setIdmalaClasificacion(Integer idmalaClasificacion) {
        this.idmalaClasificacion = idmalaClasificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public Usuario getUsuarioAtiende() {
        return usuarioAtiende;
    }

    public void setUsuarioAtiende(Usuario usuarioAtiende) {
        this.usuarioAtiende = usuarioAtiende;
    }

    public Usuario getUsuarioAvisa() {
        return usuarioAvisa;
    }

    public void setUsuarioAvisa(Usuario usuarioAvisa) {
        this.usuarioAvisa = usuarioAvisa;
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
        hash += (idmalaClasificacion != null ? idmalaClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MalaClasificacion)) {
            return false;
        }
        MalaClasificacion other = (MalaClasificacion) object;
        if ((this.idmalaClasificacion == null && other.idmalaClasificacion != null) || (this.idmalaClasificacion != null && !this.idmalaClasificacion.equals(other.idmalaClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.MalaClasificacion[ idmalaClasificacion=" + idmalaClasificacion + " ]";
    }
    
}
