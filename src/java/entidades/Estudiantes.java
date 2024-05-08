/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "estudiantes")
@NamedQueries({
    @NamedQuery(name = "Estudiantes.findAll", query = "SELECT e FROM Estudiantes e"),
    @NamedQuery(name = "Estudiantes.findByCedula", query = "SELECT e FROM Estudiantes e WHERE e.cedula = :cedula"),
    @NamedQuery(name = "Estudiantes.findByNombres", query = "SELECT e FROM Estudiantes e WHERE e.nombres = :nombres"),
    @NamedQuery(name = "Estudiantes.findByApellidos", query = "SELECT e FROM Estudiantes e WHERE e.apellidos = :apellidos"),
    @NamedQuery(name = "Estudiantes.findByCorreo", query = "SELECT e FROM Estudiantes e WHERE e.correo = :correo"),
    @NamedQuery(name = "Estudiantes.findByVenceCarnet", query = "SELECT e FROM Estudiantes e WHERE e.venceCarnet = :venceCarnet"),
    @NamedQuery(name = "Estudiantes.findByIdentificadorUnico", query = "SELECT e FROM Estudiantes e WHERE e.identificadorUnico = :identificadorUnico")})
public class Estudiantes implements Serializable {

    @Lob
    @Column(name = "FOTOGRAFIA")
    private byte[] fotografia;
    @Basic(optional = false)
    @Column(name = "RH")
    private String rh;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CEDULA")
    private Integer cedula;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "CORREO")
    private String correo;
    @Basic(optional = false)
    @Column(name = "VENCE_CARNET")
    @Temporal(TemporalType.DATE)
    private Date venceCarnet;
    @Column(name = "IDENTIFICADOR_UNICO")
    private String identificadorUnico;
    @JoinColumn(name = "ESTADO_CARNET_IDESTADO_CARNET", referencedColumnName = "IDESTADO_CARNET")
    @ManyToOne(optional = false)
    private EstadoCarnet estadoCarnetIdestadoCarnet;
    @JoinColumn(name = "FORMACION_FK", referencedColumnName = "ID_FORMACION")
    @ManyToOne(optional = false)
    private Formacion formacionFk;
    @JoinColumn(name = "SEDE_FK", referencedColumnName = "ID_SEDE")
    @ManyToOne(optional = false)
    private Sede sedeFk;
    @JoinColumn(name = "TIPO_DOCUMENTO_FK", referencedColumnName = "ID_TIPO_DOCUMENTO")
    @ManyToOne(optional = false)
    private Tipodocumento tipoDocumentoFk;

    public Estudiantes() {
    }

    public Estudiantes(Integer cedula) {
        this.cedula = cedula;
    }

    public Estudiantes(Integer cedula, String nombres, String apellidos, String correo, Date venceCarnet) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.venceCarnet = venceCarnet;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getVenceCarnet() {
        return venceCarnet;
    }

    public void setVenceCarnet(Date venceCarnet) {
        this.venceCarnet = venceCarnet;
    }

    public String getIdentificadorUnico() {
        return identificadorUnico;
    }

    public void setIdentificadorUnico(String identificadorUnico) {
        this.identificadorUnico = identificadorUnico;
    }

    public EstadoCarnet getEstadoCarnetIdestadoCarnet() {
        return estadoCarnetIdestadoCarnet;
    }

    public void setEstadoCarnetIdestadoCarnet(EstadoCarnet estadoCarnetIdestadoCarnet) {
        this.estadoCarnetIdestadoCarnet = estadoCarnetIdestadoCarnet;
    }

    public Formacion getFormacionFk() {
        return formacionFk;
    }

    public void setFormacionFk(Formacion formacionFk) {
        this.formacionFk = formacionFk;
    }

    public Sede getSedeFk() {
        return sedeFk;
    }

    public void setSedeFk(Sede sedeFk) {
        this.sedeFk = sedeFk;
    }

    public Tipodocumento getTipoDocumentoFk() {
        return tipoDocumentoFk;
    }

    public void setTipoDocumentoFk(Tipodocumento tipoDocumentoFk) {
        this.tipoDocumentoFk = tipoDocumentoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiantes)) {
            return false;
        }
        Estudiantes other = (Estudiantes) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Estudiantes[ cedula=" + cedula + " ]";
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }
    
}
