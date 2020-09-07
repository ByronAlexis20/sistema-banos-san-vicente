package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;


public class EmpleadoDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Empleado> getListaEmpleados(String patron){
		List<Empleado> resultado = new ArrayList<Empleado>();
		Query query = getEntityManager().createNamedQuery("Empleado.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Empleado>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Empleado> getEmpleadoCedula(String cedula){
		List<Empleado> resultado = new ArrayList<Empleado>();
		Query query = getEntityManager().createNamedQuery("Empleado.buscarPorCedula");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("cedula", cedula );
		resultado = (List<Empleado>) query.getResultList();
		return resultado;
	}
}
