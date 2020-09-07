package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categorias database table.
 * 
 */
@Entity
@Table(name="categorias")
@NamedQueries({
	@NamedQuery(name="Categoria.buscarActivo", query="SELECT c FROM Categoria c"),
	@NamedQuery(name="Categoria.buscarPorServicio", query="SELECT c FROM Categoria c where c.servicio.idServicio = :idServicio")
})
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_categoria")
	private Integer idCategoria;

	private String categoria;

	private String estado;

	//bi-directional many-to-one association to Servicio
	@ManyToOne
	@JoinColumn(name="servicio_id")
	private Servicio servicio;

	//bi-directional many-to-one association to Precio
	@OneToMany(mappedBy="categoria")
	private List<Precio> precios;

	public Categoria() {
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Servicio getServicio() {
		return this.servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public List<Precio> getPrecios() {
		return this.precios;
	}

	public void setPrecios(List<Precio> precios) {
		this.precios = precios;
	}

	public Precio addPrecio(Precio precio) {
		getPrecios().add(precio);
		precio.setCategoria(this);

		return precio;
	}

	public Precio removePrecio(Precio precio) {
		getPrecios().remove(precio);
		precio.setCategoria(null);

		return precio;
	}

	@Override
	public String toString() {
		return categoria ;
	}

}