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
public class EstadoCarnetJpaController implements Serializable {

    public EstadoCarnetJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoCarnet estadoCarnet) throws PreexistingEntityException, Exception {
        if (estadoCarnet.getFuncionariosList() == null) {
            estadoCarnet.setFuncionariosList(new ArrayList<Funcionarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Funcionarios> attachedFuncionariosList = new ArrayList<Funcionarios>();
            for (Funcionarios funcionariosListFuncionariosToAttach : estadoCarnet.getFuncionariosList()) {
                funcionariosListFuncionariosToAttach = em.getReference(funcionariosListFuncionariosToAttach.getClass(), funcionariosListFuncionariosToAttach.getCedula());
                attachedFuncionariosList.add(funcionariosListFuncionariosToAttach);
            }
            estadoCarnet.setFuncionariosList(attachedFuncionariosList);
            em.persist(estadoCarnet);
            for (Funcionarios funcionariosListFuncionarios : estadoCarnet.getFuncionariosList()) {
                EstadoCarnet oldEstadocarnetIDESTADOCARNETOfFuncionariosListFuncionarios = funcionariosListFuncionarios.getEstadocarnetIDESTADOCARNET();
                funcionariosListFuncionarios.setEstadocarnetIDESTADOCARNET(estadoCarnet);
                funcionariosListFuncionarios = em.merge(funcionariosListFuncionarios);
                if (oldEstadocarnetIDESTADOCARNETOfFuncionariosListFuncionarios != null) {
                    oldEstadocarnetIDESTADOCARNETOfFuncionariosListFuncionarios.getFuncionariosList().remove(funcionariosListFuncionarios);
                    oldEstadocarnetIDESTADOCARNETOfFuncionariosListFuncionarios = em.merge(oldEstadocarnetIDESTADOCARNETOfFuncionariosListFuncionarios);
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
            List<Funcionarios> funcionariosListOld = persistentEstadoCarnet.getFuncionariosList();
            List<Funcionarios> funcionariosListNew = estadoCarnet.getFuncionariosList();
            List<String> illegalOrphanMessages = null;
            for (Funcionarios funcionariosListOldFuncionarios : funcionariosListOld) {
                if (!funcionariosListNew.contains(funcionariosListOldFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Funcionarios " + funcionariosListOldFuncionarios + " since its estadocarnetIDESTADOCARNET field is not nullable.");
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
            estadoCarnet.setFuncionariosList(funcionariosListNew);
            estadoCarnet = em.merge(estadoCarnet);
            for (Funcionarios funcionariosListNewFuncionarios : funcionariosListNew) {
                if (!funcionariosListOld.contains(funcionariosListNewFuncionarios)) {
                    EstadoCarnet oldEstadocarnetIDESTADOCARNETOfFuncionariosListNewFuncionarios = funcionariosListNewFuncionarios.getEstadocarnetIDESTADOCARNET();
                    funcionariosListNewFuncionarios.setEstadocarnetIDESTADOCARNET(estadoCarnet);
                    funcionariosListNewFuncionarios = em.merge(funcionariosListNewFuncionarios);
                    if (oldEstadocarnetIDESTADOCARNETOfFuncionariosListNewFuncionarios != null && !oldEstadocarnetIDESTADOCARNETOfFuncionariosListNewFuncionarios.equals(estadoCarnet)) {
                        oldEstadocarnetIDESTADOCARNETOfFuncionariosListNewFuncionarios.getFuncionariosList().remove(funcionariosListNewFuncionarios);
                        oldEstadocarnetIDESTADOCARNETOfFuncionariosListNewFuncionarios = em.merge(oldEstadocarnetIDESTADOCARNETOfFuncionariosListNewFuncionarios);
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
            List<Funcionarios> funcionariosListOrphanCheck = estadoCarnet.getFuncionariosList();
            for (Funcionarios funcionariosListOrphanCheckFuncionarios : funcionariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoCarnet (" + estadoCarnet + ") cannot be destroyed since the Funcionarios " + funcionariosListOrphanCheckFuncionarios + " in its funcionariosList field has a non-nullable estadocarnetIDESTADOCARNET field.");
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
