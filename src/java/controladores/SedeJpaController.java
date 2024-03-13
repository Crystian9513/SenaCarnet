/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Formacion;
import java.util.ArrayList;
import java.util.List;
import entidades.Estudiantes;
import entidades.Sede;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class SedeJpaController implements Serializable {

    public SedeJpaController( ) {
         this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sede sede) {
        if (sede.getFormacionList() == null) {
            sede.setFormacionList(new ArrayList<Formacion>());
        }
        if (sede.getEstudiantesList() == null) {
            sede.setEstudiantesList(new ArrayList<Estudiantes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Formacion> attachedFormacionList = new ArrayList<Formacion>();
            for (Formacion formacionListFormacionToAttach : sede.getFormacionList()) {
                formacionListFormacionToAttach = em.getReference(formacionListFormacionToAttach.getClass(), formacionListFormacionToAttach.getIdFormacion());
                attachedFormacionList.add(formacionListFormacionToAttach);
            }
            sede.setFormacionList(attachedFormacionList);
            List<Estudiantes> attachedEstudiantesList = new ArrayList<Estudiantes>();
            for (Estudiantes estudiantesListEstudiantesToAttach : sede.getEstudiantesList()) {
                estudiantesListEstudiantesToAttach = em.getReference(estudiantesListEstudiantesToAttach.getClass(), estudiantesListEstudiantesToAttach.getCedula());
                attachedEstudiantesList.add(estudiantesListEstudiantesToAttach);
            }
            sede.setEstudiantesList(attachedEstudiantesList);
            em.persist(sede);
            for (Formacion formacionListFormacion : sede.getFormacionList()) {
                Sede oldSedeIdOfFormacionListFormacion = formacionListFormacion.getSedeId();
                formacionListFormacion.setSedeId(sede);
                formacionListFormacion = em.merge(formacionListFormacion);
                if (oldSedeIdOfFormacionListFormacion != null) {
                    oldSedeIdOfFormacionListFormacion.getFormacionList().remove(formacionListFormacion);
                    oldSedeIdOfFormacionListFormacion = em.merge(oldSedeIdOfFormacionListFormacion);
                }
            }
            for (Estudiantes estudiantesListEstudiantes : sede.getEstudiantesList()) {
                Sede oldSedeFkOfEstudiantesListEstudiantes = estudiantesListEstudiantes.getSedeFk();
                estudiantesListEstudiantes.setSedeFk(sede);
                estudiantesListEstudiantes = em.merge(estudiantesListEstudiantes);
                if (oldSedeFkOfEstudiantesListEstudiantes != null) {
                    oldSedeFkOfEstudiantesListEstudiantes.getEstudiantesList().remove(estudiantesListEstudiantes);
                    oldSedeFkOfEstudiantesListEstudiantes = em.merge(oldSedeFkOfEstudiantesListEstudiantes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sede sede) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sede persistentSede = em.find(Sede.class, sede.getIdSede());
            List<Formacion> formacionListOld = persistentSede.getFormacionList();
            List<Formacion> formacionListNew = sede.getFormacionList();
            List<Estudiantes> estudiantesListOld = persistentSede.getEstudiantesList();
            List<Estudiantes> estudiantesListNew = sede.getEstudiantesList();
            List<String> illegalOrphanMessages = null;
            for (Formacion formacionListOldFormacion : formacionListOld) {
                if (!formacionListNew.contains(formacionListOldFormacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Formacion " + formacionListOldFormacion + " since its sedeId field is not nullable.");
                }
            }
            for (Estudiantes estudiantesListOldEstudiantes : estudiantesListOld) {
                if (!estudiantesListNew.contains(estudiantesListOldEstudiantes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiantes " + estudiantesListOldEstudiantes + " since its sedeFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Formacion> attachedFormacionListNew = new ArrayList<Formacion>();
            for (Formacion formacionListNewFormacionToAttach : formacionListNew) {
                formacionListNewFormacionToAttach = em.getReference(formacionListNewFormacionToAttach.getClass(), formacionListNewFormacionToAttach.getIdFormacion());
                attachedFormacionListNew.add(formacionListNewFormacionToAttach);
            }
            formacionListNew = attachedFormacionListNew;
            sede.setFormacionList(formacionListNew);
            List<Estudiantes> attachedEstudiantesListNew = new ArrayList<Estudiantes>();
            for (Estudiantes estudiantesListNewEstudiantesToAttach : estudiantesListNew) {
                estudiantesListNewEstudiantesToAttach = em.getReference(estudiantesListNewEstudiantesToAttach.getClass(), estudiantesListNewEstudiantesToAttach.getCedula());
                attachedEstudiantesListNew.add(estudiantesListNewEstudiantesToAttach);
            }
            estudiantesListNew = attachedEstudiantesListNew;
            sede.setEstudiantesList(estudiantesListNew);
            sede = em.merge(sede);
            for (Formacion formacionListNewFormacion : formacionListNew) {
                if (!formacionListOld.contains(formacionListNewFormacion)) {
                    Sede oldSedeIdOfFormacionListNewFormacion = formacionListNewFormacion.getSedeId();
                    formacionListNewFormacion.setSedeId(sede);
                    formacionListNewFormacion = em.merge(formacionListNewFormacion);
                    if (oldSedeIdOfFormacionListNewFormacion != null && !oldSedeIdOfFormacionListNewFormacion.equals(sede)) {
                        oldSedeIdOfFormacionListNewFormacion.getFormacionList().remove(formacionListNewFormacion);
                        oldSedeIdOfFormacionListNewFormacion = em.merge(oldSedeIdOfFormacionListNewFormacion);
                    }
                }
            }
            for (Estudiantes estudiantesListNewEstudiantes : estudiantesListNew) {
                if (!estudiantesListOld.contains(estudiantesListNewEstudiantes)) {
                    Sede oldSedeFkOfEstudiantesListNewEstudiantes = estudiantesListNewEstudiantes.getSedeFk();
                    estudiantesListNewEstudiantes.setSedeFk(sede);
                    estudiantesListNewEstudiantes = em.merge(estudiantesListNewEstudiantes);
                    if (oldSedeFkOfEstudiantesListNewEstudiantes != null && !oldSedeFkOfEstudiantesListNewEstudiantes.equals(sede)) {
                        oldSedeFkOfEstudiantesListNewEstudiantes.getEstudiantesList().remove(estudiantesListNewEstudiantes);
                        oldSedeFkOfEstudiantesListNewEstudiantes = em.merge(oldSedeFkOfEstudiantesListNewEstudiantes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sede.getIdSede();
                if (findSede(id) == null) {
                    throw new NonexistentEntityException("The sede with id " + id + " no longer exists.");
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
            Sede sede;
            try {
                sede = em.getReference(Sede.class, id);
                sede.getIdSede();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sede with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Formacion> formacionListOrphanCheck = sede.getFormacionList();
            for (Formacion formacionListOrphanCheckFormacion : formacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Formacion " + formacionListOrphanCheckFormacion + " in its formacionList field has a non-nullable sedeId field.");
            }
            List<Estudiantes> estudiantesListOrphanCheck = sede.getEstudiantesList();
            for (Estudiantes estudiantesListOrphanCheckEstudiantes : estudiantesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sede (" + sede + ") cannot be destroyed since the Estudiantes " + estudiantesListOrphanCheckEstudiantes + " in its estudiantesList field has a non-nullable sedeFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sede);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sede> findSedeEntities() {
        return findSedeEntities(true, -1, -1);
    }

    public List<Sede> findSedeEntities(int maxResults, int firstResult) {
        return findSedeEntities(false, maxResults, firstResult);
    }

    private List<Sede> findSedeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sede.class));
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

    public Sede findSede(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sede.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sede> rt = cq.from(Sede.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
