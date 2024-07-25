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
@Table(name = "estado_carnet")
@NamedQueries({
    @NamedQuery(name = "EstadoCarnet.findAll", query = "SELECT e FROM EstadoCarnet e"),
    @NamedQuery(name = "EstadoCarnet.findByIdestadoCarnet", query = "SELECT e FROM EstadoCarnet e WHERE e.idestadoCarnet = :idestadoCarnet"),
    @NamedQuery(name = "EstadoCarnet.findByNombre", query = "SELECT e FROM EstadoCarnet e WHERE e.nombre = :nombre")})
public class EstadoCarnet implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoCarnetIdestadoCarnet")
    private List<Estudiantes> estudiantesList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDESTADO_CARNET")
    private Integer idestadoCarnet;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadocarnetIDESTADOCARNET")
    private List<Funcionarios> funcionariosList;

    public EstadoCarnet() {
    }

    public EstadoCarnet(Integer idestadoCarnet) {
        this.idestadoCarnet = idestadoCarnet;
    }

    public EstadoCarnet(Integer idestadoCarnet, String nombre) {
        this.idestadoCarnet = idestadoCarnet;
        this.nombre = nombre;
    }

    public Integer getIdestadoCarnet() {
        return idestadoCarnet;
    }

    public void setIdestadoCarnet(Integer idestadoCarnet) {
        this.idestadoCarnet = idestadoCarnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Funcionarios> getFuncionariosList() {
        return funcionariosList;
    }

    public void setFuncionariosList(List<Funcionarios> funcionariosList) {
        this.funcionariosList = funcionariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestadoCarnet != null ? idestadoCarnet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoCarnet)) {
            return false;
        }
        EstadoCarnet other = (EstadoCarnet) object;
        if ((this.idestadoCarnet == null && other.idestadoCarnet != null) || (this.idestadoCarnet != null && !this.idestadoCarnet.equals(other.idestadoCarnet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public List<Estudiantes> getEstudiantesList() {
        return estudiantesList;
    }

    public void setEstudiantesList(List<Estudiantes> estudiantesList) {
        this.estudiantesList = estudiantesList;
    }
    
}
