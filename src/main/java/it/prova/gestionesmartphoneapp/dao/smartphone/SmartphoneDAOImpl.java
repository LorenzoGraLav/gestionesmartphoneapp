package it.prova.gestionesmartphoneapp.dao.smartphone;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionesmartphoneapp.model.Smartphone;

public class SmartphoneDAOImpl implements SmartphoneDAO {
	private EntityManager entityManager;

	@Override
	public List<Smartphone> list() throws Exception {
		return entityManager.createQuery("from Smartphone", Smartphone.class).getResultList();

	}

	@Override
	public Smartphone get(Long id) throws Exception {
		return entityManager.find(Smartphone.class, id);
	}

	@Override
	public void update(Smartphone input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);

	}

	@Override
	public void insert(Smartphone input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);

	}

	@Override
	public void delete(Smartphone input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public void unlinkAppBySmartphone(Long idSmartphoneInput) throws Exception {
		entityManager.createNativeQuery("delete from smartphone_app t where t.smartphone_id = ?1")
				.setParameter(1, idSmartphoneInput).executeUpdate();

	}

	@Override
	public void uninstallApps(Smartphone smartphoneInstance) throws Exception {
		if (smartphoneInstance == null || smartphoneInstance.getId() == null || smartphoneInstance.getId() < 1)
			throw new Exception("errore! input invalido...");
		entityManager.createNativeQuery("delete from smartphone_app where smartphone_id=?1").setParameter(1,
				smartphoneInstance.getId()).executeUpdate();

	}

}
