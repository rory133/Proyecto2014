/*
 * Proyecto fin de carrera gen�rico del Dpto.  * 
 * Lenguajes y Sistemas Inform�ticos (curso 2014-2015)  * 
 * Desarrollo de un portal de anuncios para compra/venta online. * 
 * realizado por: * 
 * juan Manuel Mendez Feijoo  * 
 *  juan-ma@telefonica.net * 
 */

package entidades;

import facade.PujaFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import utilidades.CalculaUltimaPuja;

/**
 *
 * @author juanma
 */
@EntityListeners({CalculaUltimaPuja.class})
@Entity
@Table(name = "producto", catalog = "portalsubastas", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdproducto", query = "SELECT p FROM Producto p WHERE p.idproducto = :idproducto"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByVendido", query = "SELECT p FROM Producto p WHERE p.vendido = :vendido"),
    @NamedQuery(name = "Producto.findByNoExpirado", query = "SELECT p FROM Producto p WHERE p.expirado = :expirado"),
    @NamedQuery(name = "Producto.findByMarcadoMalClasificado", query = "SELECT p FROM Producto p WHERE p.marcadoMalClasificado = :marcadoMalClasificado"),
    @NamedQuery(name = "Producto.findByEnSubasta", query = "SELECT p FROM Producto p WHERE p.enSubasta = :enSubasta")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducto", nullable = false)
    private Integer idproducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion", nullable = false, length = 400)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio", nullable = false)
    private float precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vendido", nullable = false)
    private boolean vendido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enSubasta", nullable = false)
    private boolean enSubasta;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario usuarioIdusuario;
    @JoinColumn(name = "categoria_idcategoria", referencedColumnName = "idcategoria", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Categoria categoriaIdcategoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaProducto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expirado", nullable = false)
    private boolean expirado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "marcadoMalClasificado", nullable = false)
    private boolean marcadoMalClasificado;
    @Transient
    private float ultimaPuja;


    public Producto() {
    }

    public Producto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public Producto(Integer idproducto, String nombre, String descripcion, float precio, boolean vendido, boolean enSubasta , boolean expirado, Date fechaProducto ) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.vendido = vendido;
        this.enSubasta = enSubasta;
        this.expirado = expirado;
        this.fechaProducto = fechaProducto;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean getVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public boolean getEnSubasta() {
        return enSubasta;
    }

    public void setEnSubasta(boolean enSubasta) {
        this.enSubasta = enSubasta;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    public Categoria getCategoriaIdcategoria() {
        return categoriaIdcategoria;
    }

    public void setCategoriaIdcategoria(Categoria categoriaIdcategoria) {
        this.categoriaIdcategoria = categoriaIdcategoria;
    }

    public Date getFechaProducto() {
        return fechaProducto;
    }

    public void setFechaProducto(Date fechaProducto) {
        this.fechaProducto = fechaProducto;
    }

    public boolean isExpirado() {
        return expirado;
    }

    public void setExpirado(boolean expirado) {
        this.expirado = expirado;
    }

    public float getUltimaPuja() {
        return ultimaPuja;
    }

    public void setUltimaPuja(float ultimaPuja) {
        this.ultimaPuja = ultimaPuja;
    }

    public boolean isMarcadoMalClasificado() {
        return marcadoMalClasificado;
    }

    public void setMarcadoMalClasificado(boolean marcadoMalClasificado) {
        this.marcadoMalClasificado = marcadoMalClasificado;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducto != null ? idproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idproducto == null && other.idproducto != null) || (this.idproducto != null && !this.idproducto.equals(other.idproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Producto[ idproducto=" + idproducto + " ]";
    }
    
}
