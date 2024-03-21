/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.InformacionCarnet;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Peralta
 */
public class InformacionCarnetJpaController implements Serializable {

    public InformacionCarnetJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InformacionCarnet informacionCarnet) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(informacionCarnet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InformacionCarnet informacionCarnet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            informacionCarnet = em.merge(informacionCarnet);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = informacionCarnet.getId();
                if (findInformacionCarnet(id) == null) {
                    throw new NonexistentEntityException("The informacionCarnet with id " + id + " no longer exists.");
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
            InformacionCarnet informacionCarnet;
            try {
                informacionCarnet = em.getReference(InformacionCarnet.class, id);
                informacionCarnet.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The informacionCarnet with id " + id + " no longer exists.", enfe);
            }
            em.remove(informacionCarnet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InformacionCarnet> findInformacionCarnetEntities() {
        return findInformacionCarnetEntities(true, -1, -1);
    }

    public List<InformacionCarnet> findInformacionCarnetEntities(int maxResults, int firstResult) {
        return findInformacionCarnetEntities(false, maxResults, firstResult);
    }

    private List<InformacionCarnet> findInformacionCarnetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InformacionCarnet.class));
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

    public InformacionCarnet findInformacionCarnet(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InformacionCarnet.class, id);
        } finally {
            em.close();
        }
    }

    public int getInformacionCarnetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InformacionCarnet> rt = cq.from(InformacionCarnet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
