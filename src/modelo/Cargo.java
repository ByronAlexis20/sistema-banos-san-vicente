package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cargos database table.
 * 
 */
@Entity
@Table(name="cargos")
@NamedQueries({
	@NamedQuery(name="Cargo.buscarActivo", query="SELECT c FROM Cargo c where c.estado = 'A'"),
	@NamedQuery(name="Cargo.buscarCargos", query="SELECT c FROM Cargo c")
})
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cargo")
	private Integer idCargo;

	private String cargo;

	private String estado;

	//bi-directional many-to-one association to Empleado
	@OneToMany(mappedBy="cargo")
	private List<Empleado> empleados;

	public Cargo() {
	}

	public Integer getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Empleado> getEmpleados() {
		return this.empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public Empleado addEmpleado(Empleado empleado) {
		getEmpleados().add(empleado);
		empleado.setCargo(this);

		return empleado;
	}

	public Empleado removeEmpleado(Empleado empleado) {
		getEmpleados().remove(empleado);
		empleado.setCargo(null);

		return empleado;
	}

	@Override
	public String toString() {
		return  cargo ;
	}

	
}