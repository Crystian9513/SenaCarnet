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
import entidades.Tipodocumento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController( ) {
         this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) throws PreexistingEntityException, Exception {
        if (tipodocumento.getFuncionariosList() == null) {
            tipodocumento.setFuncionariosList(new ArrayList<Funcionarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Funcionarios> attachedFuncionariosList = new ArrayList<Funcionarios>();
            for (Funcionarios funcionariosListFuncionariosToAttach : tipodocumento.getFuncionariosList()) {
                funcionariosListFuncionariosToAttach = em.getReference(funcionariosListFuncionariosToAttach.getClass(), funcionariosListFuncionariosToAttach.getCedula());
                attachedFuncionariosList.add(funcionariosListFuncionariosToAttach);
            }
            tipodocumento.setFuncionariosList(attachedFuncionariosList);
            em.persist(tipodocumento);
            for (Funcionarios funcionariosListFuncionarios : tipodocumento.getFuncionariosList()) {
                Tipodocumento oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListFuncionarios = funcionariosListFuncionarios.getTipodocumentoIDTIPODOCUMENTO();
                funcionariosListFuncionarios.setTipodocumentoIDTIPODOCUMENTO(tipodocumento);
                funcionariosListFuncionarios = em.merge(funcionariosListFuncionarios);
                if (oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListFuncionarios != null) {
                    oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListFuncionarios.getFuncionariosList().remove(funcionariosListFuncionarios);
                    oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListFuncionarios = em.merge(oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListFuncionarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipodocumento(tipodocumento.getIdTipoDocumento()) != null) {
                throw new PreexistingEntityException("Tipodocumento " + tipodocumento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumento tipodocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getIdTipoDocumento());
            List<Funcionarios> funcionariosListOld = persistentTipodocumento.getFuncionariosList();
            List<Funcionarios> funcionariosListNew = tipodocumento.getFuncionariosList();
            List<String> illegalOrphanMessages = null;
            for (Funcionarios funcionariosListOldFuncionarios : funcionariosListOld) {
                if (!funcionariosListNew.contains(funcionariosListOldFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Funcionarios " + funcionariosListOldFuncionarios + " since its tipodocumentoIDTIPODOCUMENTO field is not nullable.");
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
            tipodocumento.setFuncionariosList(funcionariosListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Funcionarios funcionariosListNewFuncionarios : funcionariosListNew) {
                if (!funcionariosListOld.contains(funcionariosListNewFuncionarios)) {
                    Tipodocumento oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListNewFuncionarios = funcionariosListNewFuncionarios.getTipodocumentoIDTIPODOCUMENTO();
                    funcionariosListNewFuncionarios.setTipodocumentoIDTIPODOCUMENTO(tipodocumento);
                    funcionariosListNewFuncionarios = em.merge(funcionariosListNewFuncionarios);
                    if (oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListNewFuncionarios != null && !oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListNewFuncionarios.equals(tipodocumento)) {
                        oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListNewFuncionarios.getFuncionariosList().remove(funcionariosListNewFuncionarios);
                        oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListNewFuncionarios = em.merge(oldTipodocumentoIDTIPODOCUMENTOOfFuncionariosListNewFuncionarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getIdTipoDocumento();
                if (findTipodocumento(id) == null) {
                    throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.");
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
            Tipodocumento tipodocumento;
            try {
                tipodocumento = em.getReference(Tipodocumento.class, id);
                tipodocumento.getIdTipoDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Funcionarios> funcionariosListOrphanCheck = tipodocumento.getFuncionariosList();
            for (Funcionarios funcionariosListOrphanCheckFuncionarios : funcionariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Funcionarios " + funcionariosListOrphanCheckFuncionarios + " in its funcionariosList field has a non-nullable tipodocumentoIDTIPODOCUMENTO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipodocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumento> findTipodocumentoEntities() {
        return findTipodocumentoEntities(true, -1, -1);
    }

    public List<Tipodocumento> findTipodocumentoEntities(int maxResults, int firstResult) {
        return findTipodocumentoEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumento> findTipodocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumento.class));
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

    public Tipodocumento findTipodocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipodocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumento> rt = cq.from(Tipodocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
