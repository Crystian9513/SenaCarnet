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
import entidades.Funcionarios;
import entidades.RolFuncionario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class RolFuncionarioJpaController implements Serializable {

    public RolFuncionarioJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RolFuncionario rolFuncionario) throws PreexistingEntityException, Exception {
        if (rolFuncionario.getFuncionariosList() == null) {
            rolFuncionario.setFuncionariosList(new ArrayList<Funcionarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Funcionarios> attachedFuncionariosList = new ArrayList<Funcionarios>();
            for (Funcionarios funcionariosListFuncionariosToAttach : rolFuncionario.getFuncionariosList()) {
                funcionariosListFuncionariosToAttach = em.getReference(funcionariosListFuncionariosToAttach.getClass(), funcionariosListFuncionariosToAttach.getCedula());
                attachedFuncionariosList.add(funcionariosListFuncionariosToAttach);
            }
            rolFuncionario.setFuncionariosList(attachedFuncionariosList);
            em.persist(rolFuncionario);
            for (Funcionarios funcionariosListFuncionarios : rolFuncionario.getFuncionariosList()) {
                RolFuncionario oldRolfuncionarioCODIGOOfFuncionariosListFuncionarios = funcionariosListFuncionarios.getRolfuncionarioCODIGO();
                funcionariosListFuncionarios.setRolfuncionarioCODIGO(rolFuncionario);
                funcionariosListFuncionarios = em.merge(funcionariosListFuncionarios);
                if (oldRolfuncionarioCODIGOOfFuncionariosListFuncionarios != null) {
                    oldRolfuncionarioCODIGOOfFuncionariosListFuncionarios.getFuncionariosList().remove(funcionariosListFuncionarios);
                    oldRolfuncionarioCODIGOOfFuncionariosListFuncionarios = em.merge(oldRolfuncionarioCODIGOOfFuncionariosListFuncionarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRolFuncionario(rolFuncionario.getCodigo()) != null) {
                throw new PreexistingEntityException("RolFuncionario " + rolFuncionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RolFuncionario rolFuncionario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RolFuncionario persistentRolFuncionario = em.find(RolFuncionario.class, rolFuncionario.getCodigo());
            List<Funcionarios> funcionariosListOld = persistentRolFuncionario.getFuncionariosList();
            List<Funcionarios> funcionariosListNew = rolFuncionario.getFuncionariosList();
            List<String> illegalOrphanMessages = null;
            for (Funcionarios funcionariosListOldFuncionarios : funcionariosListOld) {
                if (!funcionariosListNew.contains(funcionariosListOldFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Funcionarios " + funcionariosListOldFuncionarios + " since its rolfuncionarioCODIGO field is not nullable.");
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
            rolFuncionario.setFuncionariosList(funcionariosListNew);
            rolFuncionario = em.merge(rolFuncionario);
            for (Funcionarios funcionariosListNewFuncionarios : funcionariosListNew) {
                if (!funcionariosListOld.contains(funcionariosListNewFuncionarios)) {
                    RolFuncionario oldRolfuncionarioCODIGOOfFuncionariosListNewFuncionarios = funcionariosListNewFuncionarios.getRolfuncionarioCODIGO();
                    funcionariosListNewFuncionarios.setRolfuncionarioCODIGO(rolFuncionario);
                    funcionariosListNewFuncionarios = em.merge(funcionariosListNewFuncionarios);
                    if (oldRolfuncionarioCODIGOOfFuncionariosListNewFuncionarios != null && !oldRolfuncionarioCODIGOOfFuncionariosListNewFuncionarios.equals(rolFuncionario)) {
                        oldRolfuncionarioCODIGOOfFuncionariosListNewFuncionarios.getFuncionariosList().remove(funcionariosListNewFuncionarios);
                        oldRolfuncionarioCODIGOOfFuncionariosListNewFuncionarios = em.merge(oldRolfuncionarioCODIGOOfFuncionariosListNewFuncionarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolFuncionario.getCodigo();
                if (findRolFuncionario(id) == null) {
                    throw new NonexistentEntityException("The rolFuncionario with id " + id + " no longer exists.");
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
            RolFuncionario rolFuncionario;
            try {
                rolFuncionario = em.getReference(RolFuncionario.class, id);
                rolFuncionario.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolFuncionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Funcionarios> funcionariosListOrphanCheck = rolFuncionario.getFuncionariosList();
            for (Funcionarios funcionariosListOrphanCheckFuncionarios : funcionariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RolFuncionario (" + rolFuncionario + ") cannot be destroyed since the Funcionarios " + funcionariosListOrphanCheckFuncionarios + " in its funcionariosList field has a non-nullable rolfuncionarioCODIGO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rolFuncionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RolFuncionario> findRolFuncionarioEntities() {
        return findRolFuncionarioEntities(true, -1, -1);
    }

    public List<RolFuncionario> findRolFuncionarioEntities(int maxResults, int firstResult) {
        return findRolFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<RolFuncionario> findRolFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RolFuncionario.class));
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

    public RolFuncionario findRolFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RolFuncionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RolFuncionario> rt = cq.from(RolFuncionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
