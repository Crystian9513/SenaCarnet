/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.EstadoCarnet;
import entidades.Estudiantes;
import entidades.Formacion;
import entidades.Sede;
import entidades.Tipodocumento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author Peralta
 */
public class EstudiantesJpaController implements Serializable {

    public EstudiantesJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiantes estudiantes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoCarnet estadoCarnetIdestadoCarnet = estudiantes.getEstadoCarnetIdestadoCarnet();
            if (estadoCarnetIdestadoCarnet != null) {
                estadoCarnetIdestadoCarnet = em.getReference(estadoCarnetIdestadoCarnet.getClass(), estadoCarnetIdestadoCarnet.getIdestadoCarnet());
                estudiantes.setEstadoCarnetIdestadoCarnet(estadoCarnetIdestadoCarnet);
            }
            Formacion formacionFk = estudiantes.getFormacionFk();
            if (formacionFk != null) {
                formacionFk = em.getReference(formacionFk.getClass(), formacionFk.getIdFormacion());
                estudiantes.setFormacionFk(formacionFk);
            }
            Sede sedeFk = estudiantes.getSedeFk();
            if (sedeFk != null) {
                sedeFk = em.getReference(sedeFk.getClass(), sedeFk.getIdSede());
                estudiantes.setSedeFk(sedeFk);
            }
            Tipodocumento tipoDocumentoFk = estudiantes.getTipoDocumentoFk();
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk = em.getReference(tipoDocumentoFk.getClass(), tipoDocumentoFk.getIdTipoDocumento());
                estudiantes.setTipoDocumentoFk(tipoDocumentoFk);
            }
            em.persist(estudiantes);
            if (estadoCarnetIdestadoCarnet != null) {
                estadoCarnetIdestadoCarnet.getEstudiantesList().add(estudiantes);
                estadoCarnetIdestadoCarnet = em.merge(estadoCarnetIdestadoCarnet);
            }
            if (formacionFk != null) {
                formacionFk.getEstudiantesList().add(estudiantes);
                formacionFk = em.merge(formacionFk);
            }
            if (sedeFk != null) {
                sedeFk.getEstudiantesList().add(estudiantes);
                sedeFk = em.merge(sedeFk);
            }
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk.getEstudiantesList().add(estudiantes);
                tipoDocumentoFk = em.merge(tipoDocumentoFk);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiantes(estudiantes.getCedula()) != null) {
                throw new PreexistingEntityException("Estudiantes " + estudiantes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiantes estudiantes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiantes persistentEstudiantes = em.find(Estudiantes.class, estudiantes.getCedula());
            EstadoCarnet estadoCarnetIdestadoCarnetOld = persistentEstudiantes.getEstadoCarnetIdestadoCarnet();
            EstadoCarnet estadoCarnetIdestadoCarnetNew = estudiantes.getEstadoCarnetIdestadoCarnet();
            Formacion formacionFkOld = persistentEstudiantes.getFormacionFk();
            Formacion formacionFkNew = estudiantes.getFormacionFk();
            Sede sedeFkOld = persistentEstudiantes.getSedeFk();
            Sede sedeFkNew = estudiantes.getSedeFk();
            Tipodocumento tipoDocumentoFkOld = persistentEstudiantes.getTipoDocumentoFk();
            Tipodocumento tipoDocumentoFkNew = estudiantes.getTipoDocumentoFk();
            if (estadoCarnetIdestadoCarnetNew != null) {
                estadoCarnetIdestadoCarnetNew = em.getReference(estadoCarnetIdestadoCarnetNew.getClass(), estadoCarnetIdestadoCarnetNew.getIdestadoCarnet());
                estudiantes.setEstadoCarnetIdestadoCarnet(estadoCarnetIdestadoCarnetNew);
            }
            if (formacionFkNew != null) {
                formacionFkNew = em.getReference(formacionFkNew.getClass(), formacionFkNew.getIdFormacion());
                estudiantes.setFormacionFk(formacionFkNew);
            }
            if (sedeFkNew != null) {
                sedeFkNew = em.getReference(sedeFkNew.getClass(), sedeFkNew.getIdSede());
                estudiantes.setSedeFk(sedeFkNew);
            }
            if (tipoDocumentoFkNew != null) {
                tipoDocumentoFkNew = em.getReference(tipoDocumentoFkNew.getClass(), tipoDocumentoFkNew.getIdTipoDocumento());
                estudiantes.setTipoDocumentoFk(tipoDocumentoFkNew);
            }
            estudiantes = em.merge(estudiantes);
            if (estadoCarnetIdestadoCarnetOld != null && !estadoCarnetIdestadoCarnetOld.equals(estadoCarnetIdestadoCarnetNew)) {
                estadoCarnetIdestadoCarnetOld.getEstudiantesList().remove(estudiantes);
                estadoCarnetIdestadoCarnetOld = em.merge(estadoCarnetIdestadoCarnetOld);
            }
            if (estadoCarnetIdestadoCarnetNew != null && !estadoCarnetIdestadoCarnetNew.equals(estadoCarnetIdestadoCarnetOld)) {
                estadoCarnetIdestadoCarnetNew.getEstudiantesList().add(estudiantes);
                estadoCarnetIdestadoCarnetNew = em.merge(estadoCarnetIdestadoCarnetNew);
            }
            if (formacionFkOld != null && !formacionFkOld.equals(formacionFkNew)) {
                formacionFkOld.getEstudiantesList().remove(estudiantes);
                formacionFkOld = em.merge(formacionFkOld);
            }
            if (formacionFkNew != null && !formacionFkNew.equals(formacionFkOld)) {
                formacionFkNew.getEstudiantesList().add(estudiantes);
                formacionFkNew = em.merge(formacionFkNew);
            }
            if (sedeFkOld != null && !sedeFkOld.equals(sedeFkNew)) {
                sedeFkOld.getEstudiantesList().remove(estudiantes);
                sedeFkOld = em.merge(sedeFkOld);
            }
            if (sedeFkNew != null && !sedeFkNew.equals(sedeFkOld)) {
                sedeFkNew.getEstudiantesList().add(estudiantes);
                sedeFkNew = em.merge(sedeFkNew);
            }
            if (tipoDocumentoFkOld != null && !tipoDocumentoFkOld.equals(tipoDocumentoFkNew)) {
                tipoDocumentoFkOld.getEstudiantesList().remove(estudiantes);
                tipoDocumentoFkOld = em.merge(tipoDocumentoFkOld);
            }
            if (tipoDocumentoFkNew != null && !tipoDocumentoFkNew.equals(tipoDocumentoFkOld)) {
                tipoDocumentoFkNew.getEstudiantesList().add(estudiantes);
                tipoDocumentoFkNew = em.merge(tipoDocumentoFkNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estudiantes.getCedula();
                if (findEstudiantes(id) == null) {
                    throw new NonexistentEntityException("The estudiantes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiantes estudiantes;
            try {
                estudiantes = em.getReference(Estudiantes.class, id);
                estudiantes.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiantes with id " + id + " no longer exists.", enfe);
            }
            EstadoCarnet estadoCarnetIdestadoCarnet = estudiantes.getEstadoCarnetIdestadoCarnet();
            if (estadoCarnetIdestadoCarnet != null) {
                estadoCarnetIdestadoCarnet.getEstudiantesList().remove(estudiantes);
                estadoCarnetIdestadoCarnet = em.merge(estadoCarnetIdestadoCarnet);
            }
            Formacion formacionFk = estudiantes.getFormacionFk();
            if (formacionFk != null) {
                formacionFk.getEstudiantesList().remove(estudiantes);
                formacionFk = em.merge(formacionFk);
            }
            Sede sedeFk = estudiantes.getSedeFk();
            if (sedeFk != null) {
                sedeFk.getEstudiantesList().remove(estudiantes);
                sedeFk = em.merge(sedeFk);
            }
            Tipodocumento tipoDocumentoFk = estudiantes.getTipoDocumentoFk();
            if (tipoDocumentoFk != null) {
                tipoDocumentoFk.getEstudiantesList().remove(estudiantes);
                tipoDocumentoFk = em.merge(tipoDocumentoFk);
            }
            em.remove(estudiantes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudiantes> findEstudiantesEntities() {
        return findEstudiantesEntities(true, -1, -1);
    }

    public List<Estudiantes> findEstudiantesEntities(int maxResults, int firstResult) {
        return findEstudiantesEntities(false, maxResults, firstResult);
    }

     private List<Estudiantes> findEstudiantesEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Estudiantes> root = cq.from(Estudiantes.class);
        
        // Selección de los campos necesarios y exclusión de la columna de la fotografía
        cq.select(cb.array(
            root.get("cedula"),
            root.get("tipoDocumentoFk"),
            root.get("nombres"),
            root.get("apellidos"),
            root.get("formacionFk"),
            root.get("sedeFk"),
            root.get("correo"),
            root.get("venceCarnet"),
            root.get("estadoCarnetIdestadoCarnet"),
            root.get("rh")
        ));

        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        
        List<Object[]> resultList = q.getResultList();
        List<Estudiantes> estudiantesList = new ArrayList<>();
        
        // Construir objetos Estudiantes a partir de los resultados de la consulta
        for (Object[] result : resultList) {
            Estudiantes estudiante = new Estudiantes();
            estudiante.setCedula((Integer) result[0]);
            estudiante.setTipoDocumentoFk((Tipodocumento) result[1]);
            estudiante.setNombres((String) result[2]);
            estudiante.setApellidos((String) result[3]);
            estudiante.setFormacionFk((Formacion) result[4]);
            estudiante.setSedeFk((Sede) result[5]);
            estudiante.setCorreo((String) result[6]);
            estudiante.setVenceCarnet((Date) result[7]);
            estudiante.setEstadoCarnetIdestadoCarnet((EstadoCarnet) result[8]);
            estudiante.setRh((String) result[9]);
            
            estudiantesList.add(estudiante);
        }
        
        return estudiantesList;
    } finally {
        em.close();
    }
}

    public Estudiantes findEstudiantes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiantes.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudiantesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiantes> rt = cq.from(Estudiantes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
