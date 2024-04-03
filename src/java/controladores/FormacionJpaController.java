/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Sede;
import entidades.Estudiantes;
import entidades.Formacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class FormacionJpaController implements Serializable {

    public FormacionJpaController( ) {
         this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Formacion formacion) throws PreexistingEntityException, Exception {
        if (formacion.getEstudiantesList() == null) {
            formacion.setEstudiantesList(new ArrayList<Estudiantes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede sedeId = formacion.getSedeId();
            if (sedeId != null) {
                sedeId = em.getReference(sedeId.getClass(), sedeId.getIdSede());
                formacion.setSedeId(sedeId);
            }
            List<Estudiantes> attachedEstudiantesList = new ArrayList<Estudiantes>();
            for (Estudiantes estudiantesListEstudiantesToAttach : formacion.getEstudiantesList()) {
                estudiantesListEstudiantesToAttach = em.getReference(estudiantesListEstudiantesToAttach.getClass(), estudiantesListEstudiantesToAttach.getCedula());
                attachedEstudiantesList.add(estudiantesListEstudiantesToAttach);
            }
            formacion.setEstudiantesList(attachedEstudiantesList);
            em.persist(formacion);
            if (sedeId != null) {
                sedeId.getFormacionList().add(formacion);
                sedeId = em.merge(sedeId);
            }
            for (Estudiantes estudiantesListEstudiantes : formacion.getEstudiantesList()) {
                Formacion oldFormacionFkOfEstudiantesListEstudiantes = estudiantesListEstudiantes.getFormacionFk();
                estudiantesListEstudiantes.setFormacionFk(formacion);
                estudiantesListEstudiantes = em.merge(estudiantesListEstudiantes);
                if (oldFormacionFkOfEstudiantesListEstudiantes != null) {
                    oldFormacionFkOfEstudiantesListEstudiantes.getEstudiantesList().remove(estudiantesListEstudiantes);
                    oldFormacionFkOfEstudiantesListEstudiantes = em.merge(oldFormacionFkOfEstudiantesListEstudiantes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormacion(formacion.getIdFormacion()) != null) {
                throw new PreexistingEntityException("Formacion " + formacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Formacion formacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formacion persistentFormacion = em.find(Formacion.class, formacion.getIdFormacion());
            Sede sedeIdOld = persistentFormacion.getSedeId();
            Sede sedeIdNew = formacion.getSedeId();
            List<Estudiantes> estudiantesListOld = persistentFormacion.getEstudiantesList();
            List<Estudiantes> estudiantesListNew = formacion.getEstudiantesList();
            List<String> illegalOrphanMessages = null;
            for (Estudiantes estudiantesListOldEstudiantes : estudiantesListOld) {
                if (!estudiantesListNew.contains(estudiantesListOldEstudiantes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiantes " + estudiantesListOldEstudiantes + " since its formacionFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sedeIdNew != null) {
                sedeIdNew = em.getReference(sedeIdNew.getClass(), sedeIdNew.getIdSede());
                formacion.setSedeId(sedeIdNew);
            }
            List<Estudiantes> attachedEstudiantesListNew = new ArrayList<Estudiantes>();
            for (Estudiantes estudiantesListNewEstudiantesToAttach : estudiantesListNew) {
                estudiantesListNewEstudiantesToAttach = em.getReference(estudiantesListNewEstudiantesToAttach.getClass(), estudiantesListNewEstudiantesToAttach.getCedula());
                attachedEstudiantesListNew.add(estudiantesListNewEstudiantesToAttach);
            }
            estudiantesListNew = attachedEstudiantesListNew;
            formacion.setEstudiantesList(estudiantesListNew);
            formacion = em.merge(formacion);
            if (sedeIdOld != null && !sedeIdOld.equals(sedeIdNew)) {
                sedeIdOld.getFormacionList().remove(formacion);
                sedeIdOld = em.merge(sedeIdOld);
            }
            if (sedeIdNew != null && !sedeIdNew.equals(sedeIdOld)) {
                sedeIdNew.getFormacionList().add(formacion);
                sedeIdNew = em.merge(sedeIdNew);
            }
            for (Estudiantes estudiantesListNewEstudiantes : estudiantesListNew) {
                if (!estudiantesListOld.contains(estudiantesListNewEstudiantes)) {
                    Formacion oldFormacionFkOfEstudiantesListNewEstudiantes = estudiantesListNewEstudiantes.getFormacionFk();
                    estudiantesListNewEstudiantes.setFormacionFk(formacion);
                    estudiantesListNewEstudiantes = em.merge(estudiantesListNewEstudiantes);
                    if (oldFormacionFkOfEstudiantesListNewEstudiantes != null && !oldFormacionFkOfEstudiantesListNewEstudiantes.equals(formacion)) {
                        oldFormacionFkOfEstudiantesListNewEstudiantes.getEstudiantesList().remove(estudiantesListNewEstudiantes);
                        oldFormacionFkOfEstudiantesListNewEstudiantes = em.merge(oldFormacionFkOfEstudiantesListNewEstudiantes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formacion.getIdFormacion();
                if (findFormacion(id) == null) {
                    throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formacion formacion;
            try {
                formacion = em.getReference(Formacion.class, id);
                formacion.getIdFormacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estudiantes> estudiantesListOrphanCheck = formacion.getEstudiantesList();
            for (Estudiantes estudiantesListOrphanCheckEstudiantes : estudiantesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Formacion (" + formacion + ") cannot be destroyed since the Estudiantes " + estudiantesListOrphanCheckEstudiantes + " in its estudiantesList field has a non-nullable formacionFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sede sedeId = formacion.getSedeId();
            if (sedeId != null) {
                sedeId.getFormacionList().remove(formacion);
                sedeId = em.merge(sedeId);
            }
            em.remove(formacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Formacion> findFormacionEntities() {
        return findFormacionEntities(true, -1, -1);
    }

    public List<Formacion> findFormacionEntities(int maxResults, int firstResult) {
        return findFormacionEntities(false, maxResults, firstResult);
    }

    private List<Formacion> findFormacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Formacion findFormacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formacion> rt = cq.from(Formacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
