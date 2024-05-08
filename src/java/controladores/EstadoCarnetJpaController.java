/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.EstadoCarnet;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Estudiantes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class EstadoCarnetJpaController implements Serializable {

    public EstadoCarnetJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoCarnet estadoCarnet) throws PreexistingEntityException, Exception {
        if (estadoCarnet.getEstudiantesList() == null) {
            estadoCarnet.setEstudiantesList(new ArrayList<Estudiantes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estudiantes> attachedEstudiantesList = new ArrayList<Estudiantes>();
            for (Estudiantes estudiantesListEstudiantesToAttach : estadoCarnet.getEstudiantesList()) {
                estudiantesListEstudiantesToAttach = em.getReference(estudiantesListEstudiantesToAttach.getClass(), estudiantesListEstudiantesToAttach.getCedula());
                attachedEstudiantesList.add(estudiantesListEstudiantesToAttach);
            }
            estadoCarnet.setEstudiantesList(attachedEstudiantesList);
            em.persist(estadoCarnet);
            for (Estudiantes estudiantesListEstudiantes : estadoCarnet.getEstudiantesList()) {
                EstadoCarnet oldEstadoCarnetIdestadoCarnetOfEstudiantesListEstudiantes = estudiantesListEstudiantes.getEstadoCarnetIdestadoCarnet();
                estudiantesListEstudiantes.setEstadoCarnetIdestadoCarnet(estadoCarnet);
                estudiantesListEstudiantes = em.merge(estudiantesListEstudiantes);
                if (oldEstadoCarnetIdestadoCarnetOfEstudiantesListEstudiantes != null) {
                    oldEstadoCarnetIdestadoCarnetOfEstudiantesListEstudiantes.getEstudiantesList().remove(estudiantesListEstudiantes);
                    oldEstadoCarnetIdestadoCarnetOfEstudiantesListEstudiantes = em.merge(oldEstadoCarnetIdestadoCarnetOfEstudiantesListEstudiantes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoCarnet(estadoCarnet.getIdestadoCarnet()) != null) {
                throw new PreexistingEntityException("EstadoCarnet " + estadoCarnet + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoCarnet estadoCarnet) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoCarnet persistentEstadoCarnet = em.find(EstadoCarnet.class, estadoCarnet.getIdestadoCarnet());
            List<Estudiantes> estudiantesListOld = persistentEstadoCarnet.getEstudiantesList();
            List<Estudiantes> estudiantesListNew = estadoCarnet.getEstudiantesList();
            List<String> illegalOrphanMessages = null;
            for (Estudiantes estudiantesListOldEstudiantes : estudiantesListOld) {
                if (!estudiantesListNew.contains(estudiantesListOldEstudiantes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiantes " + estudiantesListOldEstudiantes + " since its estadoCarnetIdestadoCarnet field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estudiantes> attachedEstudiantesListNew = new ArrayList<Estudiantes>();
            for (Estudiantes estudiantesListNewEstudiantesToAttach : estudiantesListNew) {
                estudiantesListNewEstudiantesToAttach = em.getReference(estudiantesListNewEstudiantesToAttach.getClass(), estudiantesListNewEstudiantesToAttach.getCedula());
                attachedEstudiantesListNew.add(estudiantesListNewEstudiantesToAttach);
            }
            estudiantesListNew = attachedEstudiantesListNew;
            estadoCarnet.setEstudiantesList(estudiantesListNew);
            estadoCarnet = em.merge(estadoCarnet);
            for (Estudiantes estudiantesListNewEstudiantes : estudiantesListNew) {
                if (!estudiantesListOld.contains(estudiantesListNewEstudiantes)) {
                    EstadoCarnet oldEstadoCarnetIdestadoCarnetOfEstudiantesListNewEstudiantes = estudiantesListNewEstudiantes.getEstadoCarnetIdestadoCarnet();
                    estudiantesListNewEstudiantes.setEstadoCarnetIdestadoCarnet(estadoCarnet);
                    estudiantesListNewEstudiantes = em.merge(estudiantesListNewEstudiantes);
                    if (oldEstadoCarnetIdestadoCarnetOfEstudiantesListNewEstudiantes != null && !oldEstadoCarnetIdestadoCarnetOfEstudiantesListNewEstudiantes.equals(estadoCarnet)) {
                        oldEstadoCarnetIdestadoCarnetOfEstudiantesListNewEstudiantes.getEstudiantesList().remove(estudiantesListNewEstudiantes);
                        oldEstadoCarnetIdestadoCarnetOfEstudiantesListNewEstudiantes = em.merge(oldEstadoCarnetIdestadoCarnetOfEstudiantesListNewEstudiantes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoCarnet.getIdestadoCarnet();
                if (findEstadoCarnet(id) == null) {
                    throw new NonexistentEntityException("The estadoCarnet with id " + id + " no longer exists.");
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
            EstadoCarnet estadoCarnet;
            try {
                estadoCarnet = em.getReference(EstadoCarnet.class, id);
                estadoCarnet.getIdestadoCarnet();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoCarnet with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estudiantes> estudiantesListOrphanCheck = estadoCarnet.getEstudiantesList();
            for (Estudiantes estudiantesListOrphanCheckEstudiantes : estudiantesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoCarnet (" + estadoCarnet + ") cannot be destroyed since the Estudiantes " + estudiantesListOrphanCheckEstudiantes + " in its estudiantesList field has a non-nullable estadoCarnetIdestadoCarnet field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoCarnet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoCarnet> findEstadoCarnetEntities() {
        return findEstadoCarnetEntities(true, -1, -1);
    }

    public List<EstadoCarnet> findEstadoCarnetEntities(int maxResults, int firstResult) {
        return findEstadoCarnetEntities(false, maxResults, firstResult);
    }

    private List<EstadoCarnet> findEstadoCarnetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoCarnet.class));
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

    public EstadoCarnet findEstadoCarnet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoCarnet.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCarnetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoCarnet> rt = cq.from(EstadoCarnet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
