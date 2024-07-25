/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "regional")
@NamedQueries({
    @NamedQuery(name = "Regional.findAll", query = "SELECT r FROM Regional r"),
    @NamedQuery(name = "Regional.findByIdregional", query = "SELECT r FROM Regional r WHERE r.idregional = :idregional"),
    @NamedQuery(name = "Regional.findByNombre", query = "SELECT r FROM Regional r WHERE r.nombre = :nombre")})
public class Regional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDREGIONAL")
    private Integer idregional;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionalIdregional")
    private List<Centro> centroList;

    public Regional() {
    }

    public Regional(Integer idregional) {
        this.idregional = idregional;
    }

    public Regional(Integer idregional, String nombre) {
        this.idregional = idregional;
        this.nombre = nombre;
    }

    public Integer getIdregional() {
        return idregional;
    }

    public void setIdregional(Integer idregional) {
        this.idregional = idregional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Centro> getCentroList() {
        return centroList;
    }

    public void setCentroList(List<Centro> centroList) {
        this.centroList = centroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregional != null ? idregional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regional)) {
            return false;
        }
        Regional other = (Regional) object;
        if ((this.idregional == null && other.idregional != null) || (this.idregional != null && !this.idregional.equals(other.idregional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
