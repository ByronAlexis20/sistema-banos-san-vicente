package modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detalle_facturas database table.
 * 
 */
@Entity
@Table(name="detalle_facturas")
@NamedQuery(name="DetalleFactura.findAll", query="SELECT d FROM DetalleFactura d")
public class DetalleFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle_factura")
	private Integer idDetalleFactura;

	private Integer cantidad;

	private String estado;

	@Column(name="precio_servicio")
	private double precioServicio;

	private double total;

	@Column(name="categoria_id")
	private Integer categoriaId;
	
	//bi-directional many-to-one association to Factura
	@ManyToOne
	@JoinColumn(name="factura_id")
	private Factura factura;

	//bi-directional many-to-one association to Servicio
	@ManyToOne
	@JoinColumn(name="servicio_id")
	private Servicio servicio;

	public DetalleFactura() {
	}

	public Integer getIdDetalleFactura() {
		return this.idDetalleFactura;
	}

	public void setIdDetalleFactura(Integer idDetalleFactura) {
		this.idDetalleFactura = idDetalleFactura;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getPrecioServicio() {
		return this.precioServicio;
	}

	public void setPrecioServicio(double precioServicio) {
		this.precioServicio = precioServicio;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Servicio getServicio() {
		return this.servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

}