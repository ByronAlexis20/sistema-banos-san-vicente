package modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the precios database table.
 * 
 */
@Entity
@Table(name="precios")
@NamedQueries({
	@NamedQuery(name="Precio.recuperarActivo", query="SELECT p FROM Precio p where p.categoria.idCategoria = :idCategoria and p.estado = 'A'")
})
public class Precio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_precio")
	private Integer idPrecio;

	private String estado;

	private double precio;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;

	public Precio() {
	}

	public Integer getIdPrecio() {
		return this.idPrecio;
	}

	public void setIdPrecio(Integer idPrecio) {
		this.idPrecio = idPrecio;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}