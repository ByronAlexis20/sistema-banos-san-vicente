package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class RolDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Rol> getListaRolActivo(){
		List<Rol> resultado = new ArrayList<Rol>();
		Query query = getEntityManager().createNamedQuery("Rol.buscarActivo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Rol>) query.getResultList();
		return resultado;
	}
}
