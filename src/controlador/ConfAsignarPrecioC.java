package controlador;

import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import modelo.Categoria;
import modelo.CategoriaDAO;
import modelo.Precio;
import modelo.PrecioDAO;
import modelo.Servicio;
import modelo.ServicioDAO;
import util.Context;
import util.ControllerHelper;

public class ConfAsignarPrecioC {

	Tooltip toolTip;
	@FXML private ComboBox<Servicio> cboServicio;
	@FXML private TextField txtPrecio;
	@FXML private ComboBox<Categoria> cboCategoria;
	@FXML private Button btnGrabar;
	
	CategoriaDAO categoriaDAO = new CategoriaDAO();
	ServicioDAO servicioDAO = new ServicioDAO();
	PrecioDAO precioDAO = new PrecioDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			txtPrecio.setText("0.00");
			
			toolTip = new Tooltip("Grabar precio");
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
			btnGrabar.setTooltip(toolTip);
			
			cboServicio.setStyle("-fx-cursor: hand;");
			cboCategoria.setStyle("-fx-cursor: hand;");
			
			txtPrecio.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*(\\.\\d*)?")) {
						txtPrecio.setText(oldValue);
					}
				}
			});
			llenarCombos();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void llenarCombos() {
		try {
			ObservableList<Servicio> datos = FXCollections.observableArrayList();
			List<Servicio> listaServicio = servicioDAO.getServiciosActivos();
			datos.addAll(listaServicio);
			cboServicio.setItems(datos);
			cboServicio.setPromptText("Seleccione servicio");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionarCategoria() {
		try {
			cboCategoria.getItems().clear();
			List<Categoria> listado = categoriaDAO.getListaCategoriasServicio(cboServicio.getSelectionModel().getSelectedItem().getIdServicio());
			
			for(Categoria categoria : listado) {
				if(categoria.getEstado().equals("A"))
					cboCategoria.getItems().add(categoria);
			}
			cboCategoria.setPromptText("Seleccione categoria");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void grabar() {
		try {
			if(validarDatos() == false) {
				return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				//primero se da de baja al registro del precio anterios
				List<Precio> precioUltimo = precioDAO.getPrecioActivo(cboCategoria.getSelectionModel().getSelectedItem().getIdCategoria());
				System.out.println(precioUltimo.size());
				if(precioUltimo.size() > 0) {//trajo datos
					Precio precio = precioUltimo.get(0);
					precio.setEstado("I");
					precioDAO.getEntityManager().getTransaction().begin();
					precioDAO.getEntityManager().merge(precio);
					precioDAO.getEntityManager().getTransaction().commit();;
				}
				
				//graba el nuevo dato
				
				Precio precioGrabar = new Precio();
				precioGrabar.setCategoria(cboCategoria.getSelectionModel().getSelectedItem());
				precioGrabar.setEstado("A");
				precioGrabar.setIdPrecio(null);
				precioGrabar.setPrecio(Double.parseDouble(txtPrecio.getText()));
				
				precioDAO.getEntityManager().getTransaction().begin();
				precioDAO.getEntityManager().persist(precioGrabar);
				precioDAO.getEntityManager().getTransaction().commit();;
				
				helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
				cboServicio.getSelectionModel().select(null);
				cboCategoria.getItems().clear();
				txtPrecio.setText("0");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
		}
	}
	private boolean validarDatos() {
		try {
			boolean bandera = false;
			if(cboServicio.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un servicio", Context.getInstance().getStage());
				return false;
			}
			if(cboCategoria.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar una categoria", Context.getInstance().getStage());
				return false;
			}
			if(txtPrecio.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar precio", Context.getInstance().getStage());
				return false;
			}
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
}
