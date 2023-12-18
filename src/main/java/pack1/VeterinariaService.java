package pack1;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class VeterinariaService {

    @PersistenceContext
    private EntityManager entityManager;

    public VeterinariaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
	// Consultas JPQL
    public List<Mascota> findMascotasByTipo(String tipo) {
        Query query = entityManager.createQuery("SELECT m FROM Mascota m WHERE m.tipo = :tipo");
        query.setParameter("tipo", tipo);
        return query.getResultList();
    }

    public List<Dueño> findDueñosWithMultipleMascotas() {
        Query query = entityManager.createQuery("SELECT d FROM Dueño d WHERE SIZE(d.mascotas) > 2");
        return query.getResultList();
    }

    public List<Visita> findVisitasByMascotaNombre(String nombreMascota) {
        Query query = entityManager.createQuery("SELECT v FROM Visita v WHERE v.mascota.nombre = :nombre");
        query.setParameter("nombre", nombreMascota);
        return query.getResultList();
    }

    public List<Visita> findVisitasByMotivo(String motivo) {
        Query query = entityManager.createQuery("SELECT v FROM Visita v WHERE v.motivo = :motivo");
        query.setParameter("motivo", motivo);
        return query.getResultList();
    }

    // Operaciones CRUD
    public void createDueño(Dueño dueño) {
        entityManager.persist(dueño);
    }

    public void updateDueño(Dueño dueño) {
        entityManager.merge(dueño);
    }

    public void deleteDueño(Long id) {
        Dueño dueño = entityManager.find(Dueño.class, id);
        if (dueño != null) {
            entityManager.remove(dueño);
        }
    }

    // Más operaciones CRUD para Mascota y Visita
}