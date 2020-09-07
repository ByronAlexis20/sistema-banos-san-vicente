package modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the empresa database table.
 * 
 */
@Entity
@Table(name="empresa")
@NamedQueries({
	@NamedQuery(name="Empresa.recuperaEmpresa", query="SELECT e FROM Empresa e WHERE e.estado = 'A'")
})
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_empresa")
	private Integer idEmpresa;

	@Column(name="razon_social")
	private String razonSocial;
	
	@Column(name="inicio_control")
	private Integer inicioControl;
	
	private String ruc;
	
	private String representante;
		
	private String direccion;

	private String telefono;
	
	private String email;

	private Integer iva;
	
	@Column(name="imp_factura")
	private String impFactura;
	
	@Column(name="imp_reportes")
	private String impReportes;
	
	private String estado;
	
	public Empresa() {
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIva() {
		return iva;
	}

	public void setIva(Integer iva) {
		this.iva = iva;
	}

	public String getImpFactura() {
		return impFactura;
	}

	public void setImpFactura(String impFactura) {
		this.impFactura = impFactura;
	}

	public String getImpReportes() {
		return impReportes;
	}

	public void setImpReportes(String impReportes) {
		this.impReportes = impReportes;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getInicioControl() {
		return inicioControl;
	}

	public void setInicioControl(Integer inicioControl) {
		this.inicioControl = inicioControl;
	}

	
}