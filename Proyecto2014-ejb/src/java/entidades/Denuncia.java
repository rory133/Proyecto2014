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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanma
 */
@Entity
@Table(name = "denuncia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Denuncia.findAll", query = "SELECT d FROM Denuncia d"),
    @NamedQuery(name = "Denuncia.findByIddenuncia", query = "SELECT d FROM Denuncia d WHERE d.iddenuncia = :iddenuncia"),
    @NamedQuery(name = "Denuncia.findByTipoDenuncia", query = "SELECT d FROM Denuncia d WHERE d.tipoDenuncia = :tipoDenuncia"),
    @NamedQuery(name = "Denuncia.findByMotivo", query = "SELECT d FROM Denuncia d WHERE d.motivo = :motivo"),
    @NamedQuery(name = "Denuncia.findByAtendida", query = "SELECT d FROM Denuncia d WHERE d.atendida = :atendida"),
    @NamedQuery(name = "Denuncia.findByFechaDenuncia", query = "SELECT d FROM Denuncia d WHERE d.fechaDenuncia = :fechaDenuncia"),
    @NamedQuery(name = "Denuncia.findByFechaAtencion", query = "SELECT d FROM Denuncia d WHERE d.fechaAtencion = :fechaAtencion")})
public class Denuncia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddenuncia")
    private Integer iddenuncia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_denuncia")
    private String tipoDenuncia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "motivo")
    private String motivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "atendida")
    private boolean atendida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_denuncia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDenuncia;
    @Column(name = "fecha_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;
    @JoinColumn(name = "venta_idventa", referencedColumnName = "idventa")
    @ManyToOne(optional = false)
    private Venta ventaIdventa;
    @JoinColumn(name = "atiende_idusuario1", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario atiendeIdusuario1;
    @JoinColumn(name = "denuncia_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario denunciaIdusuario;

    public Denuncia() {
    }

    public Denuncia(Integer iddenuncia) {
        this.iddenuncia = iddenuncia;
    }

    public Denuncia(Integer iddenuncia, String tipoDenuncia, String motivo, boolean atendida, Date fechaDenuncia) {
        this.iddenuncia = iddenuncia;
        this.tipoDenuncia = tipoDenuncia;
        this.motivo = motivo;
        this.atendida = atendida;
        this.fechaDenuncia = fechaDenuncia;
    }

    public Integer getIddenuncia() {
        return iddenuncia;
    }

    public void setIddenuncia(Integer iddenuncia) {
        this.iddenuncia = iddenuncia;
    }

    public String getTipoDenuncia() {
        return tipoDenuncia;
    }

    public void setTipoDenuncia(String tipoDenuncia) {
        this.tipoDenuncia = tipoDenuncia;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean getAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public Date getFechaDenuncia() {
        return fechaDenuncia;
    }

    public void setFechaDenuncia(Date fechaDenuncia) {
        this.fechaDenuncia = fechaDenuncia;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public Venta getVentaIdventa() {
        return ventaIdventa;
    }

    public void setVentaIdventa(Venta ventaIdventa) {
        this.ventaIdventa = ventaIdventa;
    }

    public Usuario getAtiendeIdusuario1() {
        return atiendeIdusuario1;
    }

    public void setAtiendeIdusuario1(Usuario atiendeIdusuario1) {
        this.atiendeIdusuario1 = atiendeIdusuario1;
    }

    public Usuario getDenunciaIdusuario() {
        return denunciaIdusuario;
    }

    public void setDenunciaIdusuario(Usuario denunciaIdusuario) {
        this.denunciaIdusuario = denunciaIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddenuncia != null ? iddenuncia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Denuncia)) {
            return false;
        }
        Denuncia other = (Denuncia) object;
        if ((this.iddenuncia == null && other.iddenuncia != null) || (this.iddenuncia != null && !this.iddenuncia.equals(other.iddenuncia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Denuncia[ iddenuncia=" + iddenuncia + " ]";
    }
    
}
