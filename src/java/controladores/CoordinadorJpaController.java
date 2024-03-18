/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import com.password4j.Hash;
import com.password4j.Password;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.Coordinador;
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
public class CoordinadorJpaController implements Serializable {

    public CoordinadorJpaController( ) {
           this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Coordinador coordinador) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(coordinador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCoordinador(coordinador.getCedula()) != null) {
                throw new PreexistingEntityException("Coordinador " + coordinador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Coordinador coordinador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            coordinador = em.merge(coordinador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = coordinador.getCedula();
                if (findCoordinador(id) == null) {
                    throw new NonexistentEntityException("The coordinador with id " + id + " no longer exists.");
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
            Coordinador coordinador;
            try {
                coordinador = em.getReference(Coordinador.class, id);
                coordinador.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The coordinador with id " + id + " no longer exists.", enfe);
            }
            em.remove(coordinador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Coordinador> findCoordinadorEntities() {
        return findCoordinadorEntities(true, -1, -1);
    }

    public List<Coordinador> findCoordinadorEntities(int maxResults, int firstResult) {
        return findCoordinadorEntities(false, maxResults, firstResult);
    }

    private List<Coordinador> findCoordinadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Coordinador.class));
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

    public Coordinador findCoordinador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Coordinador.class, id);
        } finally {
            em.close();
        }
    }

    public int getCoordinadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Coordinador> rt = cq.from(Coordinador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public boolean DencryptarClave(String claveHash, String claveLogin) {
    // Verifica si la contraseña proporcionada coincide con la contraseña almacenada
    return Password.check(claveLogin, claveHash).addPepper().withScrypt();
}
   
     public String EncryptarClave(String clave) {
        Hash hash = Password.hash(clave).addPepper().withScrypt();
        return hash.getResult();
    }
    
}
