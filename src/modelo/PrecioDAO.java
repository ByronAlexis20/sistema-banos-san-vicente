package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class PrecioDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<Precio> getPrecioActivo(Integer idCategoria){
		List<Precio> resultado = new ArrayList<Precio>();
		Query query = getEntityManager().createNamedQuery("Precio.recuperarActivo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idCategoria", idCategoria);
		resultado = (List<Precio>) query.getResultList();
		return resultado;
	}
}
