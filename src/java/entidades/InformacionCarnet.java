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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "informacion_carnet")
@NamedQueries({
    @NamedQuery(name = "InformacionCarnet.findAll", query = "SELECT i FROM InformacionCarnet i"),
    @NamedQuery(name = "InformacionCarnet.findById", query = "SELECT i FROM InformacionCarnet i WHERE i.id = :id"),
    @NamedQuery(name = "InformacionCarnet.findByCedula", query = "SELECT i FROM InformacionCarnet i WHERE i.cedula = :cedula"),
    @NamedQuery(name = "InformacionCarnet.findByFormacion", query = "SELECT i FROM InformacionCarnet i WHERE i.formacion = :formacion"),
    @NamedQuery(name = "InformacionCarnet.findByNombres", query = "SELECT i FROM InformacionCarnet i WHERE i.nombres = :nombres"),
    @NamedQuery(name = "InformacionCarnet.findByApellidos", query = "SELECT i FROM InformacionCarnet i WHERE i.apellidos = :apellidos"),
    @NamedQuery(name = "InformacionCarnet.findByFechaEliminacion", query = "SELECT i FROM InformacionCarnet i WHERE i.fechaEliminacion = :fechaEliminacion")})
public class InformacionCarnet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CEDULA")
    private int cedula;
    @Basic(optional = false)
    @Column(name = "FORMACION")
    private int formacion;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.DATE)
    private Date fechaEliminacion;

    public InformacionCarnet() {
    }

    public InformacionCarnet(Integer id) {
        this.id = id;
    }

    public InformacionCarnet(Integer id, int cedula, int formacion, String nombres, String apellidos, Date fechaEliminacion) {
        this.id = id;
        this.cedula = cedula;
        this.formacion = formacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaEliminacion = fechaEliminacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getFormacion() {
        return formacion;
    }

    public void setFormacion(int formacion) {
        this.formacion = formacion;
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

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformacionCarnet)) {
            return false;
        }
        InformacionCarnet other = (InformacionCarnet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.InformacionCarnet[ id=" + id + " ]";
    }
    
}
