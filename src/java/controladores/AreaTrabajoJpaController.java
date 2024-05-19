/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.AreaTrabajo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Funcionarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class AreaTrabajoJpaController implements Serializable {

    public AreaTrabajoJpaController( ) {
       this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AreaTrabajo areaTrabajo) {
        if (areaTrabajo.getFuncionariosList() == null) {
            areaTrabajo.setFuncionariosList(new ArrayList<Funcionarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Funcionarios> attachedFuncionariosList = new ArrayList<Funcionarios>();
            for (Funcionarios funcionariosListFuncionariosToAttach : areaTrabajo.getFuncionariosList()) {
                funcionariosListFuncionariosToAttach = em.getReference(funcionariosListFuncionariosToAttach.getClass(), funcionariosListFuncionariosToAttach.getCedula());
                attachedFuncionariosList.add(funcionariosListFuncionariosToAttach);
            }
            areaTrabajo.setFuncionariosList(attachedFuncionariosList);
            em.persist(areaTrabajo);
            for (Funcionarios funcionariosListFuncionarios : areaTrabajo.getFuncionariosList()) {
                AreaTrabajo oldAreatrabajoCODIGOOfFuncionariosListFuncionarios = funcionariosListFuncionarios.getAreatrabajoCODIGO();
                funcionariosListFuncionarios.setAreatrabajoCODIGO(areaTrabajo);
                funcionariosListFuncionarios = em.merge(funcionariosListFuncionarios);
                if (oldAreatrabajoCODIGOOfFuncionariosListFuncionarios != null) {
                    oldAreatrabajoCODIGOOfFuncionariosListFuncionarios.getFuncionariosList().remove(funcionariosListFuncionarios);
                    oldAreatrabajoCODIGOOfFuncionariosListFuncionarios = em.merge(oldAreatrabajoCODIGOOfFuncionariosListFuncionarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AreaTrabajo areaTrabajo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AreaTrabajo persistentAreaTrabajo = em.find(AreaTrabajo.class, areaTrabajo.getCodigo());
            List<Funcionarios> funcionariosListOld = persistentAreaTrabajo.getFuncionariosList();
            List<Funcionarios> funcionariosListNew = areaTrabajo.getFuncionariosList();
            List<String> illegalOrphanMessages = null;
            for (Funcionarios funcionariosListOldFuncionarios : funcionariosListOld) {
                if (!funcionariosListNew.contains(funcionariosListOldFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Funcionarios " + funcionariosListOldFuncionarios + " since its areatrabajoCODIGO field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Funcionarios> attachedFuncionariosListNew = new ArrayList<Funcionarios>();
            for (Funcionarios funcionariosListNewFuncionariosToAttach : funcionariosListNew) {
                funcionariosListNewFuncionariosToAttach = em.getReference(funcionariosListNewFuncionariosToAttach.getClass(), funcionariosListNewFuncionariosToAttach.getCedula());
                attachedFuncionariosListNew.add(funcionariosListNewFuncionariosToAttach);
            }
            funcionariosListNew = attachedFuncionariosListNew;
            areaTrabajo.setFuncionariosList(funcionariosListNew);
            areaTrabajo = em.merge(areaTrabajo);
            for (Funcionarios funcionariosListNewFuncionarios : funcionariosListNew) {
                if (!funcionariosListOld.contains(funcionariosListNewFuncionarios)) {
                    AreaTrabajo oldAreatrabajoCODIGOOfFuncionariosListNewFuncionarios = funcionariosListNewFuncionarios.getAreatrabajoCODIGO();
                    funcionariosListNewFuncionarios.setAreatrabajoCODIGO(areaTrabajo);
                    funcionariosListNewFuncionarios = em.merge(funcionariosListNewFuncionarios);
                    if (oldAreatrabajoCODIGOOfFuncionariosListNewFuncionarios != null && !oldAreatrabajoCODIGOOfFuncionariosListNewFuncionarios.equals(areaTrabajo)) {
                        oldAreatrabajoCODIGOOfFuncionariosListNewFuncionarios.getFuncionariosList().remove(funcionariosListNewFuncionarios);
                        oldAreatrabajoCODIGOOfFuncionariosListNewFuncionarios = em.merge(oldAreatrabajoCODIGOOfFuncionariosListNewFuncionarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = areaTrabajo.getCodigo();
                if (findAreaTrabajo(id) == null) {
                    throw new NonexistentEntityException("The areaTrabajo with id " + id + " no longer exists.");
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
            AreaTrabajo areaTrabajo;
            try {
                areaTrabajo = em.getReference(AreaTrabajo.class, id);
                areaTrabajo.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The areaTrabajo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Funcionarios> funcionariosListOrphanCheck = areaTrabajo.getFuncionariosList();
            for (Funcionarios funcionariosListOrphanCheckFuncionarios : funcionariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AreaTrabajo (" + areaTrabajo + ") cannot be destroyed since the Funcionarios " + funcionariosListOrphanCheckFuncionarios + " in its funcionariosList field has a non-nullable areatrabajoCODIGO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(areaTrabajo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AreaTrabajo> findAreaTrabajoEntities() {
        return findAreaTrabajoEntities(true, -1, -1);
    }

    public List<AreaTrabajo> findAreaTrabajoEntities(int maxResults, int firstResult) {
        return findAreaTrabajoEntities(false, maxResults, firstResult);
    }

    private List<AreaTrabajo> findAreaTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AreaTrabajo.class));
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

    public AreaTrabajo findAreaTrabajo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AreaTrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AreaTrabajo> rt = cq.from(AreaTrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
