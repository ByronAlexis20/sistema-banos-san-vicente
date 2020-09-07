package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class ServicioDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Servicio> getServicios(){
		List<Servicio> resultado = new ArrayList<Servicio>();
		Query query = getEntityManager().createNamedQuery("Servicio.recuperaServicio");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Servicio>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Servicio> getServiciosActivos(){
		List<Servicio> resultado = new ArrayList<Servicio>();
		Query query = getEntityManager().createNamedQuery("Servicio.recuperaServicioActivo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Servicio>) query.getResultList();
		return resultado;
	}
}
