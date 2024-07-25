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
import entidades.Centro;
import entidades.Regional;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class RegionalJpaController implements Serializable {

    public RegionalJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regional regional) throws PreexistingEntityException, Exception {
        if (regional.getCentroList() == null) {
            regional.setCentroList(new ArrayList<Centro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Centro> attachedCentroList = new ArrayList<Centro>();
            for (Centro centroListCentroToAttach : regional.getCentroList()) {
                centroListCentroToAttach = em.getReference(centroListCentroToAttach.getClass(), centroListCentroToAttach.getIdcentro());
                attachedCentroList.add(centroListCentroToAttach);
            }
            regional.setCentroList(attachedCentroList);
            em.persist(regional);
            for (Centro centroListCentro : regional.getCentroList()) {
                Regional oldRegionalIdregionalOfCentroListCentro = centroListCentro.getRegionalIdregional();
                centroListCentro.setRegionalIdregional(regional);
                centroListCentro = em.merge(centroListCentro);
                if (oldRegionalIdregionalOfCentroListCentro != null) {
                    oldRegionalIdregionalOfCentroListCentro.getCentroList().remove(centroListCentro);
                    oldRegionalIdregionalOfCentroListCentro = em.merge(oldRegionalIdregionalOfCentroListCentro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegional(regional.getIdregional()) != null) {
                throw new PreexistingEntityException("Regional " + regional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regional regional) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regional persistentRegional = em.find(Regional.class, regional.getIdregional());
            List<Centro> centroListOld = persistentRegional.getCentroList();
            List<Centro> centroListNew = regional.getCentroList();
            List<String> illegalOrphanMessages = null;
            for (Centro centroListOldCentro : centroListOld) {
                if (!centroListNew.contains(centroListOldCentro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Centro " + centroListOldCentro + " since its regionalIdregional field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Centro> attachedCentroListNew = new ArrayList<Centro>();
            for (Centro centroListNewCentroToAttach : centroListNew) {
                centroListNewCentroToAttach = em.getReference(centroListNewCentroToAttach.getClass(), centroListNewCentroToAttach.getIdcentro());
                attachedCentroListNew.add(centroListNewCentroToAttach);
            }
            centroListNew = attachedCentroListNew;
            regional.setCentroList(centroListNew);
            regional = em.merge(regional);
            for (Centro centroListNewCentro : centroListNew) {
                if (!centroListOld.contains(centroListNewCentro)) {
                    Regional oldRegionalIdregionalOfCentroListNewCentro = centroListNewCentro.getRegionalIdregional();
                    centroListNewCentro.setRegionalIdregional(regional);
                    centroListNewCentro = em.merge(centroListNewCentro);
                    if (oldRegionalIdregionalOfCentroListNewCentro != null && !oldRegionalIdregionalOfCentroListNewCentro.equals(regional)) {
                        oldRegionalIdregionalOfCentroListNewCentro.getCentroList().remove(centroListNewCentro);
                        oldRegionalIdregionalOfCentroListNewCentro = em.merge(oldRegionalIdregionalOfCentroListNewCentro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regional.getIdregional();
                if (findRegional(id) == null) {
                    throw new NonexistentEntityException("The regional with id " + id + " no longer exists.");
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
            Regional regional;
            try {
                regional = em.getReference(Regional.class, id);
                regional.getIdregional();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Centro> centroListOrphanCheck = regional.getCentroList();
            for (Centro centroListOrphanCheckCentro : centroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Regional (" + regional + ") cannot be destroyed since the Centro " + centroListOrphanCheckCentro + " in its centroList field has a non-nullable regionalIdregional field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(regional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regional> findRegionalEntities() {
        return findRegionalEntities(true, -1, -1);
    }

    public List<Regional> findRegionalEntities(int maxResults, int firstResult) {
        return findRegionalEntities(false, maxResults, firstResult);
    }

    private List<Regional> findRegionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regional.class));
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

    public Regional findRegional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regional.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regional> rt = cq.from(Regional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
