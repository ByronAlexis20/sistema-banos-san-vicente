package modelo;

import java.util.List;

import javax.persistence.Query;


public class UsuarioDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuario(String usuario,String clave) {
		List<Usuario> resultado; 
		Query query = getEntityManager().createNamedQuery("Usuario.buscarUsuario");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("usu", usuario);
		query.setParameter("cla", clave);
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuarios() {
		List<Usuario> resultado; 
		Query query = getEntityManager().createNamedQuery("Usuario.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosPorEmpleado(Integer idEmpleado) {
		List<Usuario> resultado; 
		Query query = getEntityManager().createNamedQuery("Usuario.buscarPorIdEmpleado");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idEmpleado", idEmpleado);
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Usuario> getValidarUsuario(String usuario,int idUsuario) {
		List<Usuario> resultado; 
		Query query = getEntityManager().createNamedQuery("Usuario.validarUsuario");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("usuario", usuario);
		query.setParameter("idUsuario", idUsuario);
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosPorRol(Integer idRol) {
		List<Usuario> resultado; 
		Query query = getEntityManager().createNamedQuery("Usuario.buscarAdministrador");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idRol", idRol);
		resultado = (List<Usuario>) query.getResultList();
		return resultado;
	}
}
