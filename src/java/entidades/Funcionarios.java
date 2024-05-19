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
@Table(name = "funcionarios")
@NamedQueries({
    @NamedQuery(name = "Funcionarios.findAll", query = "SELECT f FROM Funcionarios f"),
    @NamedQuery(name = "Funcionarios.findByCedula", query = "SELECT f FROM Funcionarios f WHERE f.cedula = :cedula"),
    @NamedQuery(name = "Funcionarios.findByNombres", query = "SELECT f FROM Funcionarios f WHERE f.nombres = :nombres"),
    @NamedQuery(name = "Funcionarios.findByApellidos", query = "SELECT f FROM Funcionarios f WHERE f.apellidos = :apellidos"),
    @NamedQuery(name = "Funcionarios.findByCorreo", query = "SELECT f FROM Funcionarios f WHERE f.correo = :correo"),
    @NamedQuery(name = "Funcionarios.findByVenceCarnet", query = "SELECT f FROM Funcionarios f WHERE f.venceCarnet = :venceCarnet"),
    @NamedQuery(name = "Funcionarios.findByRh", query = "SELECT f FROM Funcionarios f WHERE f.rh = :rh"),
    @NamedQuery(name = "Funcionarios.findByIdentificadorUnico", query = "SELECT f FROM Funcionarios f WHERE f.identificadorUnico = :identificadorUnico")})
public class Funcionarios implements Serializable {

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
    @Lob
    @Column(name = "FOTOGRAFIA")
    private byte[] fotografia;
    @Basic(optional = false)
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "VENCE_CARNET")
    @Temporal(TemporalType.DATE)
    private Date venceCarnet;
    @Basic(optional = false)
    @Column(name = "RH")
    private String rh;
    @Column(name = "IDENTIFICADOR_UNICO")
    private String identificadorUnico;
    @JoinColumn(name = "area_trabajo_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private AreaTrabajo areatrabajoCODIGO;
    @JoinColumn(name = "estado_carnet_IDESTADO_CARNET", referencedColumnName = "IDESTADO_CARNET")
    @ManyToOne(optional = false)
    private EstadoCarnet estadocarnetIDESTADOCARNET;
    @JoinColumn(name = "rol_funcionario_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private RolFuncionario rolfuncionarioCODIGO;
    @JoinColumn(name = "tipodocumento_ID_TIPO_DOCUMENTO", referencedColumnName = "ID_TIPO_DOCUMENTO")
    @ManyToOne(optional = false)
    private Tipodocumento tipodocumentoIDTIPODOCUMENTO;

    public Funcionarios() {
    }

    public Funcionarios(Integer cedula) {
        this.cedula = cedula;
    }

    public Funcionarios(Integer cedula, String nombres, String apellidos, String correo, String rh) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.rh = rh;
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

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
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

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getIdentificadorUnico() {
        return identificadorUnico;
    }

    public void setIdentificadorUnico(String identificadorUnico) {
        this.identificadorUnico = identificadorUnico;
    }

    public AreaTrabajo getAreatrabajoCODIGO() {
        return areatrabajoCODIGO;
    }

    public void setAreatrabajoCODIGO(AreaTrabajo areatrabajoCODIGO) {
        this.areatrabajoCODIGO = areatrabajoCODIGO;
    }

    public EstadoCarnet getEstadocarnetIDESTADOCARNET() {
        return estadocarnetIDESTADOCARNET;
    }

    public void setEstadocarnetIDESTADOCARNET(EstadoCarnet estadocarnetIDESTADOCARNET) {
        this.estadocarnetIDESTADOCARNET = estadocarnetIDESTADOCARNET;
    }

    public RolFuncionario getRolfuncionarioCODIGO() {
        return rolfuncionarioCODIGO;
    }

    public void setRolfuncionarioCODIGO(RolFuncionario rolfuncionarioCODIGO) {
        this.rolfuncionarioCODIGO = rolfuncionarioCODIGO;
    }

    public Tipodocumento getTipodocumentoIDTIPODOCUMENTO() {
        return tipodocumentoIDTIPODOCUMENTO;
    }

    public void setTipodocumentoIDTIPODOCUMENTO(Tipodocumento tipodocumentoIDTIPODOCUMENTO) {
        this.tipodocumentoIDTIPODOCUMENTO = tipodocumentoIDTIPODOCUMENTO;
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
        if (!(object instanceof Funcionarios)) {
            return false;
        }
        Funcionarios other = (Funcionarios) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Funcionarios[ cedula=" + cedula + " ]";
    }
    
}
