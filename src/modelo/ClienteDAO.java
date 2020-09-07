package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class ClienteDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Cliente> getClienteCedula(String cedula){
		List<Cliente> resultado = new ArrayList<Cliente>();
		Query query = getEntityManager().createNamedQuery("Cliente.buscarPorCedula");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("cedula", cedula );
		resultado = (List<Cliente>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Cliente> getClientes(){
		List<Cliente> resultado = new ArrayList<Cliente>();
		Query query = getEntityManager().createNamedQuery("Cliente.buscarTodos");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Cliente>) query.getResultList();
		return resultado;
	}
}
