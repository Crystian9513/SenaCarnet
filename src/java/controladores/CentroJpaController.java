/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.Centro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Regional;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class CentroJpaController implements Serializable {

    public CentroJpaController( ) {
       this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Centro centro) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regional regionalIdregional = centro.getRegionalIdregional();
            if (regionalIdregional != null) {
                regionalIdregional = em.getReference(regionalIdregional.getClass(), regionalIdregional.getIdregional());
                centro.setRegionalIdregional(regionalIdregional);
            }
            em.persist(centro);
            if (regionalIdregional != null) {
                regionalIdregional.getCentroList().add(centro);
                regionalIdregional = em.merge(regionalIdregional);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCentro(centro.getIdcentro()) != null) {
                throw new PreexistingEntityException("Centro " + centro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Centro centro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Centro persistentCentro = em.find(Centro.class, centro.getIdcentro());
            Regional regionalIdregionalOld = persistentCentro.getRegionalIdregional();
            Regional regionalIdregionalNew = centro.getRegionalIdregional();
            if (regionalIdregionalNew != null) {
                regionalIdregionalNew = em.getReference(regionalIdregionalNew.getClass(), regionalIdregionalNew.getIdregional());
                centro.setRegionalIdregional(regionalIdregionalNew);
            }
            centro = em.merge(centro);
            if (regionalIdregionalOld != null && !regionalIdregionalOld.equals(regionalIdregionalNew)) {
                regionalIdregionalOld.getCentroList().remove(centro);
                regionalIdregionalOld = em.merge(regionalIdregionalOld);
            }
            if (regionalIdregionalNew != null && !regionalIdregionalNew.equals(regionalIdregionalOld)) {
                regionalIdregionalNew.getCentroList().add(centro);
                regionalIdregionalNew = em.merge(regionalIdregionalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = centro.getIdcentro();
                if (findCentro(id) == null) {
                    throw new NonexistentEntityException("The centro with id " + id + " no longer exists.");
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
            Centro centro;
            try {
                centro = em.getReference(Centro.class, id);
                centro.getIdcentro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The centro with id " + id + " no longer exists.", enfe);
            }
            Regional regionalIdregional = centro.getRegionalIdregional();
            if (regionalIdregional != null) {
                regionalIdregional.getCentroList().remove(centro);
                regionalIdregional = em.merge(regionalIdregional);
            }
            em.remove(centro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Centro> findCentroEntities() {
        return findCentroEntities(true, -1, -1);
    }

    public List<Centro> findCentroEntities(int maxResults, int firstResult) {
        return findCentroEntities(false, maxResults, firstResult);
    }

    private List<Centro> findCentroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Centro.class));
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

    public Centro findCentro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Centro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCentroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Centro> rt = cq.from(Centro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
