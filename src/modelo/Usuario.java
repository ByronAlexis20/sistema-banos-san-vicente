package modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.buscarUsuario", query="SELECT u FROM Usuario u WHERE "
			+ "u.usuario = (:usu) and u.clave = (:cla) and u.estado = 'A'"),
	@NamedQuery(name="Usuario.buscarPorIdEmpleado", query="SELECT u FROM Usuario u where u.estado = 'A' and u.empleado.idEmpleado = :idEmpleado"),
	@NamedQuery(name="Usuario.validarUsuario", query="SELECT u FROM Usuario u "
			+ "WHERE u.usuario = (:usuario) AND u.idUsuario <> (:idUsuario) and u.estado = 'A'"),
	@NamedQuery(name="Usuario.buscarAdministrador", query="SELECT u FROM Usuario u WHERE u.rol.idRol = :idRol and u.estado = 'A'")
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Integer idUsuario;

	@Column(name="cambio_clave")
	private String cambioClave;

	private String clave;

	private String estado;

	private String usuario;

	//bi-directional many-to-one association to Empleado
	@ManyToOne
	@JoinColumn(name="empleado_id")
	private Empleado empleado;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol rol;

	public Usuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCambioClave() {
		return this.cambioClave;
	}

	public void setCambioClave(String cambioClave) {
		this.cambioClave = cambioClave;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}