package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import util.Context;

public class FacturaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Factura> getListaFacturas(){
		List<Factura> resultado = new ArrayList<Factura>();
		Query query = getEntityManager().createNamedQuery("Factura.numeroControl");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Factura>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Factura> getListaFacturaUsuario(){
		List<Factura> resultado = new ArrayList<Factura>();
		Query query = getEntityManager().createNamedQuery("Factura.facturaUsuario");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idUsuario", Context.getInstance().getUsuario().getIdUsuario());
		query.setParameter("fecha", new Date());
		resultado = (List<Factura>) query.getResultList();
		return resultado;
	}
}
