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
import entidades.Estudiantes;
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
        if (tipodocumento.getEstudiantesList() == null) {
            tipodocumento.setEstudiantesList(new ArrayList<Estudiantes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estudiantes> attachedEstudiantesList = new ArrayList<Estudiantes>();
            for (Estudiantes estudiantesListEstudiantesToAttach : tipodocumento.getEstudiantesList()) {
                estudiantesListEstudiantesToAttach = em.getReference(estudiantesListEstudiantesToAttach.getClass(), estudiantesListEstudiantesToAttach.getCedula());
                attachedEstudiantesList.add(estudiantesListEstudiantesToAttach);
            }
            tipodocumento.setEstudiantesList(attachedEstudiantesList);
            em.persist(tipodocumento);
            for (Estudiantes estudiantesListEstudiantes : tipodocumento.getEstudiantesList()) {
                Tipodocumento oldTipoDocumentoFkOfEstudiantesListEstudiantes = estudiantesListEstudiantes.getTipoDocumentoFk();
                estudiantesListEstudiantes.setTipoDocumentoFk(tipodocumento);
                estudiantesListEstudiantes = em.merge(estudiantesListEstudiantes);
                if (oldTipoDocumentoFkOfEstudiantesListEstudiantes != null) {
                    oldTipoDocumentoFkOfEstudiantesListEstudiantes.getEstudiantesList().remove(estudiantesListEstudiantes);
                    oldTipoDocumentoFkOfEstudiantesListEstudiantes = em.merge(oldTipoDocumentoFkOfEstudiantesListEstudiantes);
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
            List<Estudiantes> estudiantesListOld = persistentTipodocumento.getEstudiantesList();
            List<Estudiantes> estudiantesListNew = tipodocumento.getEstudiantesList();
            List<String> illegalOrphanMessages = null;
            for (Estudiantes estudiantesListOldEstudiantes : estudiantesListOld) {
                if (!estudiantesListNew.contains(estudiantesListOldEstudiantes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudiantes " + estudiantesListOldEstudiantes + " since its tipoDocumentoFk field is not nullable.");
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
            tipodocumento.setEstudiantesList(estudiantesListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Estudiantes estudiantesListNewEstudiantes : estudiantesListNew) {
                if (!estudiantesListOld.contains(estudiantesListNewEstudiantes)) {
                    Tipodocumento oldTipoDocumentoFkOfEstudiantesListNewEstudiantes = estudiantesListNewEstudiantes.getTipoDocumentoFk();
                    estudiantesListNewEstudiantes.setTipoDocumentoFk(tipodocumento);
                    estudiantesListNewEstudiantes = em.merge(estudiantesListNewEstudiantes);
                    if (oldTipoDocumentoFkOfEstudiantesListNewEstudiantes != null && !oldTipoDocumentoFkOfEstudiantesListNewEstudiantes.equals(tipodocumento)) {
                        oldTipoDocumentoFkOfEstudiantesListNewEstudiantes.getEstudiantesList().remove(estudiantesListNewEstudiantes);
                        oldTipoDocumentoFkOfEstudiantesListNewEstudiantes = em.merge(oldTipoDocumentoFkOfEstudiantesListNewEstudiantes);
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
            List<Estudiantes> estudiantesListOrphanCheck = tipodocumento.getEstudiantesList();
            for (Estudiantes estudiantesListOrphanCheckEstudiantes : estudiantesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Estudiantes " + estudiantesListOrphanCheckEstudiantes + " in its estudiantesList field has a non-nullable tipoDocumentoFk field.");
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
