/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Peralta
 */
@Entity
@Table(name = "logo")
@NamedQueries({
    @NamedQuery(name = "Logo.findAll", query = "SELECT l FROM Logo l"),
    @NamedQuery(name = "Logo.findByIdlogo", query = "SELECT l FROM Logo l WHERE l.idlogo = :idlogo")})
public class Logo implements Serializable {

    @Lob
    @Column(name = "IMAGEN")
    private byte[] imagen;
    @Lob
    @Column(name = "FIRMA")
    private byte[] firma;
    @Basic(optional = false)
    @Column(name = "DIRECTOR")
    private String director;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDLOGO")
    private Integer idlogo;

    public Logo() {
    }

    public Logo(Integer idlogo) {
        this.idlogo = idlogo;
    }

    public Integer getIdlogo() {
        return idlogo;
    }

    public void setIdlogo(Integer idlogo) {
        this.idlogo = idlogo;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlogo != null ? idlogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logo)) {
            return false;
        }
        Logo other = (Logo) object;
        if ((this.idlogo == null && other.idlogo != null) || (this.idlogo != null && !this.idlogo.equals(other.idlogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Logo[ idlogo=" + idlogo + " ]";
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }
    
}
