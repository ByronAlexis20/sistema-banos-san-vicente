package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class CargoDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Cargo> getListaCargoActivo(){
		List<Cargo> resultado = new ArrayList<Cargo>();
		Query query = getEntityManager().createNamedQuery("Cargo.buscarActivo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Cargo>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Cargo> getListaCargos(){
		List<Cargo> resultado = new ArrayList<Cargo>();
		Query query = getEntityManager().createNamedQuery("Cargo.buscarCargos");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Cargo>) query.getResultList();
		return resultado;
	}
}
