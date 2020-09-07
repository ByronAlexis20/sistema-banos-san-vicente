package controlador;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.Categoria;
import modelo.CategoriaDAO;
import modelo.DetalleFactura;
import modelo.Precio;
import modelo.PrecioDAO;
import modelo.Servicio;
import modelo.ServicioDAO;
import util.Context;
import util.ControllerHelper;

public class RecServiciosC {
	Tooltip toolTip;
	@FXML private Button btnSalir;
	@FXML private TextField txtCantidad;
	@FXML private ComboBox<Servicio> cboServicio;
	@FXML private Button btnAgregar;
	@FXML private TextField txtPrecio;
	@FXML private ComboBox<Categoria> cboCategoria;
	
	CategoriaDAO categoriaDAO = new CategoriaDAO();
	ServicioDAO servicioDAO = new ServicioDAO();
	PrecioDAO precioDAO = new PrecioDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			txtPrecio.setText("0.00");
			
			toolTip = new Tooltip("Salir");
			btnSalir.setStyle("-fx-graphic: url('/salir.png');-fx-cursor: hand;");
			btnSalir.setTooltip(toolTip);
			
			cboServicio.setStyle("-fx-cursor: hand;");
			
			toolTip = new Tooltip("Agregar servicio");
			btnAgregar.setStyle("-fx-graphic: url('/agregar.png');-fx-cursor: hand;");
			btnAgregar.setTooltip(toolTip);
			
			cboCategoria.setStyle("-fx-cursor: hand;");
			txtPrecio.setEditable(false);
			
			txtCantidad.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtCantidad.setText(oldValue);
					}
				}
			});
			llenarCombos();
			txtCantidad.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						Agregar();
					} 
				} 
			}); 
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
	public void seleccionarPrecio() {
		try {
			txtPrecio.setText("0.00");
			List<Precio> listado = precioDAO.getPrecioActivo(cboCategoria.getSelectionModel().getSelectedItem().getIdCategoria());
			for(Precio precio : listado) {
				if(precio.getEstado().equals("A"))
					txtPrecio.setText(String.valueOf(precio.getPrecio()));
			}
			txtCantidad.requestFocus();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void Agregar() {
		try {
			if(cboServicio.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un servicio", Context.getInstance().getStage());
				return;
			}
			if(cboCategoria.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar una categoría", Context.getInstance().getStage());
				return;
			}
			if(txtCantidad.getText().equals("") || txtCantidad.getText().equals("0")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar cantidad", Context.getInstance().getStage());
				return;
			}
			DetalleFactura detalle = new DetalleFactura();
			detalle.setIdDetalleFactura(null);
			detalle.setEstado("A");
			detalle.setCategoriaId(cboCategoria.getSelectionModel().getSelectedItem().getIdCategoria());
			detalle.setCantidad(Integer.parseInt(txtCantidad.getText()));
			detalle.setPrecioServicio(Double.parseDouble(txtPrecio.getText()));
			detalle.setServicio(cboServicio.getSelectionModel().getSelectedItem());
			Double total = Double.parseDouble(txtPrecio.getText()) * Integer.parseInt(txtCantidad.getText());
			detalle.setTotal(total);
			Context.getInstance().setDetalleFactura(detalle);
			Context.getInstance().getStageModal().close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public void salir() {
		try {
			Context.getInstance().getStageModal().close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
