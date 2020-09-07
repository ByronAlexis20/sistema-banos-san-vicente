package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class CategoriaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Categoria> getListaCategorias(){
		List<Categoria> resultado = new ArrayList<Categoria>();
		Query query = getEntityManager().createNamedQuery("Categoria.buscarActivo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Categoria>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Categoria> getListaCategoriasServicio(Integer idServicio){
		List<Categoria> resultado = new ArrayList<Categoria>();
		Query query = getEntityManager().createNamedQuery("Categoria.buscarPorServicio");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idServicio", idServicio );
		resultado = (List<Categoria>) query.getResultList();
		return resultado;
	}
}
