/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.AreaTrabajo;
import entidades.EstadoCarnet;
import entidades.Funcionarios;
import entidades.RolFuncionario;
import entidades.Tipodocumento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Peralta
 */
public class FuncionariosJpaController implements Serializable {

    public FuncionariosJpaController( ) {
         this.emf = Persistence.createEntityManagerFactory("SenaCarnetPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionarios funcionarios) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AreaTrabajo areatrabajoCODIGO = funcionarios.getAreatrabajoCODIGO();
            if (areatrabajoCODIGO != null) {
                areatrabajoCODIGO = em.getReference(areatrabajoCODIGO.getClass(), areatrabajoCODIGO.getCodigo());
                funcionarios.setAreatrabajoCODIGO(areatrabajoCODIGO);
            }
            EstadoCarnet estadocarnetIDESTADOCARNET = funcionarios.getEstadocarnetIDESTADOCARNET();
            if (estadocarnetIDESTADOCARNET != null) {
                estadocarnetIDESTADOCARNET = em.getReference(estadocarnetIDESTADOCARNET.getClass(), estadocarnetIDESTADOCARNET.getIdestadoCarnet());
                funcionarios.setEstadocarnetIDESTADOCARNET(estadocarnetIDESTADOCARNET);
            }
            RolFuncionario rolfuncionarioCODIGO = funcionarios.getRolfuncionarioCODIGO();
            if (rolfuncionarioCODIGO != null) {
                rolfuncionarioCODIGO = em.getReference(rolfuncionarioCODIGO.getClass(), rolfuncionarioCODIGO.getCodigo());
                funcionarios.setRolfuncionarioCODIGO(rolfuncionarioCODIGO);
            }
            Tipodocumento tipodocumentoIDTIPODOCUMENTO = funcionarios.getTipodocumentoIDTIPODOCUMENTO();
            if (tipodocumentoIDTIPODOCUMENTO != null) {
                tipodocumentoIDTIPODOCUMENTO = em.getReference(tipodocumentoIDTIPODOCUMENTO.getClass(), tipodocumentoIDTIPODOCUMENTO.getIdTipoDocumento());
                funcionarios.setTipodocumentoIDTIPODOCUMENTO(tipodocumentoIDTIPODOCUMENTO);
            }
            em.persist(funcionarios);
            if (areatrabajoCODIGO != null) {
                areatrabajoCODIGO.getFuncionariosList().add(funcionarios);
                areatrabajoCODIGO = em.merge(areatrabajoCODIGO);
            }
            if (estadocarnetIDESTADOCARNET != null) {
                estadocarnetIDESTADOCARNET.getFuncionariosList().add(funcionarios);
                estadocarnetIDESTADOCARNET = em.merge(estadocarnetIDESTADOCARNET);
            }
            if (rolfuncionarioCODIGO != null) {
                rolfuncionarioCODIGO.getFuncionariosList().add(funcionarios);
                rolfuncionarioCODIGO = em.merge(rolfuncionarioCODIGO);
            }
            if (tipodocumentoIDTIPODOCUMENTO != null) {
                tipodocumentoIDTIPODOCUMENTO.getFuncionariosList().add(funcionarios);
                tipodocumentoIDTIPODOCUMENTO = em.merge(tipodocumentoIDTIPODOCUMENTO);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionarios(funcionarios.getCedula()) != null) {
                throw new PreexistingEntityException("Funcionarios " + funcionarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionarios funcionarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionarios persistentFuncionarios = em.find(Funcionarios.class, funcionarios.getCedula());
            AreaTrabajo areatrabajoCODIGOOld = persistentFuncionarios.getAreatrabajoCODIGO();
            AreaTrabajo areatrabajoCODIGONew = funcionarios.getAreatrabajoCODIGO();
            EstadoCarnet estadocarnetIDESTADOCARNETOld = persistentFuncionarios.getEstadocarnetIDESTADOCARNET();
            EstadoCarnet estadocarnetIDESTADOCARNETNew = funcionarios.getEstadocarnetIDESTADOCARNET();
            RolFuncionario rolfuncionarioCODIGOOld = persistentFuncionarios.getRolfuncionarioCODIGO();
            RolFuncionario rolfuncionarioCODIGONew = funcionarios.getRolfuncionarioCODIGO();
            Tipodocumento tipodocumentoIDTIPODOCUMENTOOld = persistentFuncionarios.getTipodocumentoIDTIPODOCUMENTO();
            Tipodocumento tipodocumentoIDTIPODOCUMENTONew = funcionarios.getTipodocumentoIDTIPODOCUMENTO();
            if (areatrabajoCODIGONew != null) {
                areatrabajoCODIGONew = em.getReference(areatrabajoCODIGONew.getClass(), areatrabajoCODIGONew.getCodigo());
                funcionarios.setAreatrabajoCODIGO(areatrabajoCODIGONew);
            }
            if (estadocarnetIDESTADOCARNETNew != null) {
                estadocarnetIDESTADOCARNETNew = em.getReference(estadocarnetIDESTADOCARNETNew.getClass(), estadocarnetIDESTADOCARNETNew.getIdestadoCarnet());
                funcionarios.setEstadocarnetIDESTADOCARNET(estadocarnetIDESTADOCARNETNew);
            }
            if (rolfuncionarioCODIGONew != null) {
                rolfuncionarioCODIGONew = em.getReference(rolfuncionarioCODIGONew.getClass(), rolfuncionarioCODIGONew.getCodigo());
                funcionarios.setRolfuncionarioCODIGO(rolfuncionarioCODIGONew);
            }
            if (tipodocumentoIDTIPODOCUMENTONew != null) {
                tipodocumentoIDTIPODOCUMENTONew = em.getReference(tipodocumentoIDTIPODOCUMENTONew.getClass(), tipodocumentoIDTIPODOCUMENTONew.getIdTipoDocumento());
                funcionarios.setTipodocumentoIDTIPODOCUMENTO(tipodocumentoIDTIPODOCUMENTONew);
            }
            funcionarios = em.merge(funcionarios);
            if (areatrabajoCODIGOOld != null && !areatrabajoCODIGOOld.equals(areatrabajoCODIGONew)) {
                areatrabajoCODIGOOld.getFuncionariosList().remove(funcionarios);
                areatrabajoCODIGOOld = em.merge(areatrabajoCODIGOOld);
            }
            if (areatrabajoCODIGONew != null && !areatrabajoCODIGONew.equals(areatrabajoCODIGOOld)) {
                areatrabajoCODIGONew.getFuncionariosList().add(funcionarios);
                areatrabajoCODIGONew = em.merge(areatrabajoCODIGONew);
            }
            if (estadocarnetIDESTADOCARNETOld != null && !estadocarnetIDESTADOCARNETOld.equals(estadocarnetIDESTADOCARNETNew)) {
                estadocarnetIDESTADOCARNETOld.getFuncionariosList().remove(funcionarios);
                estadocarnetIDESTADOCARNETOld = em.merge(estadocarnetIDESTADOCARNETOld);
            }
            if (estadocarnetIDESTADOCARNETNew != null && !estadocarnetIDESTADOCARNETNew.equals(estadocarnetIDESTADOCARNETOld)) {
                estadocarnetIDESTADOCARNETNew.getFuncionariosList().add(funcionarios);
                estadocarnetIDESTADOCARNETNew = em.merge(estadocarnetIDESTADOCARNETNew);
            }
            if (rolfuncionarioCODIGOOld != null && !rolfuncionarioCODIGOOld.equals(rolfuncionarioCODIGONew)) {
                rolfuncionarioCODIGOOld.getFuncionariosList().remove(funcionarios);
                rolfuncionarioCODIGOOld = em.merge(rolfuncionarioCODIGOOld);
            }
            if (rolfuncionarioCODIGONew != null && !rolfuncionarioCODIGONew.equals(rolfuncionarioCODIGOOld)) {
                rolfuncionarioCODIGONew.getFuncionariosList().add(funcionarios);
                rolfuncionarioCODIGONew = em.merge(rolfuncionarioCODIGONew);
            }
            if (tipodocumentoIDTIPODOCUMENTOOld != null && !tipodocumentoIDTIPODOCUMENTOOld.equals(tipodocumentoIDTIPODOCUMENTONew)) {
                tipodocumentoIDTIPODOCUMENTOOld.getFuncionariosList().remove(funcionarios);
                tipodocumentoIDTIPODOCUMENTOOld = em.merge(tipodocumentoIDTIPODOCUMENTOOld);
            }
            if (tipodocumentoIDTIPODOCUMENTONew != null && !tipodocumentoIDTIPODOCUMENTONew.equals(tipodocumentoIDTIPODOCUMENTOOld)) {
                tipodocumentoIDTIPODOCUMENTONew.getFuncionariosList().add(funcionarios);
                tipodocumentoIDTIPODOCUMENTONew = em.merge(tipodocumentoIDTIPODOCUMENTONew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionarios.getCedula();
                if (findFuncionarios(id) == null) {
                    throw new NonexistentEntityException("The funcionarios with id " + id + " no longer exists.");
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
            Funcionarios funcionarios;
            try {
                funcionarios = em.getReference(Funcionarios.class, id);
                funcionarios.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionarios with id " + id + " no longer exists.", enfe);
            }
            AreaTrabajo areatrabajoCODIGO = funcionarios.getAreatrabajoCODIGO();
            if (areatrabajoCODIGO != null) {
                areatrabajoCODIGO.getFuncionariosList().remove(funcionarios);
                areatrabajoCODIGO = em.merge(areatrabajoCODIGO);
            }
            EstadoCarnet estadocarnetIDESTADOCARNET = funcionarios.getEstadocarnetIDESTADOCARNET();
            if (estadocarnetIDESTADOCARNET != null) {
                estadocarnetIDESTADOCARNET.getFuncionariosList().remove(funcionarios);
                estadocarnetIDESTADOCARNET = em.merge(estadocarnetIDESTADOCARNET);
            }
            RolFuncionario rolfuncionarioCODIGO = funcionarios.getRolfuncionarioCODIGO();
            if (rolfuncionarioCODIGO != null) {
                rolfuncionarioCODIGO.getFuncionariosList().remove(funcionarios);
                rolfuncionarioCODIGO = em.merge(rolfuncionarioCODIGO);
            }
            Tipodocumento tipodocumentoIDTIPODOCUMENTO = funcionarios.getTipodocumentoIDTIPODOCUMENTO();
            if (tipodocumentoIDTIPODOCUMENTO != null) {
                tipodocumentoIDTIPODOCUMENTO.getFuncionariosList().remove(funcionarios);
                tipodocumentoIDTIPODOCUMENTO = em.merge(tipodocumentoIDTIPODOCUMENTO);
            }
            em.remove(funcionarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionarios> findFuncionariosEntities() {
        return findFuncionariosEntities(true, -1, -1);
    }

    public List<Funcionarios> findFuncionariosEntities(int maxResults, int firstResult) {
        return findFuncionariosEntities(false, maxResults, firstResult);
    }

    private List<Funcionarios> findFuncionariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionarios.class));
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

    public Funcionarios findFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionarios> rt = cq.from(Funcionarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Object[] findFuncionarioYEstadoCarnetPorIdentificadorUnico(String identificadorUnico) {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT f, f.estadocarnetIDESTADOCARNET FROM Funcionarios f WHERE f.identificadorUnico = :identificadorUnico");
        query.setParameter("identificadorUnico", identificadorUnico);
        List<Object[]> resultados = query.getResultList();
        if (!resultados.isEmpty()) {
            return resultados.get(0); // Devuelve un array con el funcionario y el estado del carnet
        } else {
            return null; // No se encontraron funcionarios con ese identificador único
        }
    } finally {
        em.close();
    }
}

    
}
