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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "centro")
@NamedQueries({
    @NamedQuery(name = "Centro.findAll", query = "SELECT c FROM Centro c"),
    @NamedQuery(name = "Centro.findByIdcentro", query = "SELECT c FROM Centro c WHERE c.idcentro = :idcentro"),
    @NamedQuery(name = "Centro.findByNombre", query = "SELECT c FROM Centro c WHERE c.nombre = :nombre")})
public class Centro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCENTRO")
    private Integer idcentro;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @JoinColumn(name = "Regional_Idregional", referencedColumnName = "IDREGIONAL")
    @ManyToOne(optional = false)
    private Regional regionalIdregional;

    public Centro() {
    }

    public Centro(Integer idcentro) {
        this.idcentro = idcentro;
    }

    public Centro(Integer idcentro, String nombre) {
        this.idcentro = idcentro;
        this.nombre = nombre;
    }

    public Integer getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(Integer idcentro) {
        this.idcentro = idcentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Regional getRegionalIdregional() {
        return regionalIdregional;
    }

    public void setRegionalIdregional(Regional regionalIdregional) {
        this.regionalIdregional = regionalIdregional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcentro != null ? idcentro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centro)) {
            return false;
        }
        Centro other = (Centro) object;
        if ((this.idcentro == null && other.idcentro != null) || (this.idcentro != null && !this.idcentro.equals(other.idcentro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
