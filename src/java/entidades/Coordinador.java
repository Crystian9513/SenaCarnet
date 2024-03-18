/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "coordinador")
@NamedQueries({
    @NamedQuery(name = "Coordinador.findAll", query = "SELECT c FROM Coordinador c"),
    @NamedQuery(name = "Coordinador.findByCedula", query = "SELECT c FROM Coordinador c WHERE c.cedula = :cedula"),
    @NamedQuery(name = "Coordinador.findByNombre", query = "SELECT c FROM Coordinador c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Coordinador.findByApellido", query = "SELECT c FROM Coordinador c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Coordinador.findByClave", query = "SELECT c FROM Coordinador c WHERE c.clave = :clave"),
    @NamedQuery(name = "Coordinador.findByCorreo", query = "SELECT c FROM Coordinador c WHERE c.correo = :correo")})
public class Coordinador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CEDULA")
    private Integer cedula;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "CLAVE")
    private String clave;
    @Basic(optional = false)
    @Column(name = "CORREO")
    private String correo;

    public Coordinador() {
    }

    public Coordinador(Integer cedula) {
        this.cedula = cedula;
    }

    public Coordinador(Integer cedula, String nombre, String apellido, String clave, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.correo = correo;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
        if (!(object instanceof Coordinador)) {
            return false;
        }
        Coordinador other = (Coordinador) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Coordinador[ cedula=" + cedula + " ]";
    }
    
}
