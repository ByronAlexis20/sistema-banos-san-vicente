package controlador;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import modelo.Empleado;
import modelo.EmpleadoDAO;
import util.Context;
import util.ControllerHelper;

public class AdmListaEmpleadoC {
	Tooltip toolTip;
	@FXML private TableView<Empleado> tvDatos;
	@FXML private Button btnEliminar;
	@FXML private Button btnNuevo;
	@FXML private TextField txtBuscar;
	@FXML private Button btnEditar;

	EmpleadoDAO empleadoDAO = new EmpleadoDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			toolTip = new Tooltip("Editar empleado");
			btnEditar.setStyle("-fx-graphic: url('/editar.png');-fx-cursor: hand;");
			btnEditar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Eliminar empleado");
			btnEliminar.setStyle("-fx-graphic: url('/eliminar.png');-fx-cursor: hand;");
			btnEliminar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Nuevo empleado");
			btnNuevo.setStyle("-fx-graphic: url('/nuevo.png');-fx-cursor: hand;");
			btnNuevo.setTooltip(toolTip);
			
			llenarDatos("");
			txtBuscar.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtBuscar.getText().toUpperCase();
					txtBuscar.setText(cadena);
				}
			});
			txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarCliente();
					} 
				} 
			}); 
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void llenarDatos(String busqueda) {
		try {
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Empleado> listaEmpleados;
			ObservableList<Empleado> datos = FXCollections.observableArrayList();
			listaEmpleados = empleadoDAO.getListaEmpleados(busqueda);
			datos.setAll(listaEmpleados);
			
			//llenar los datos en la tabla
			TableColumn<Empleado, String> cedulaColum = new TableColumn<>("Cédula");
			cedulaColum.setMinWidth(10);
			cedulaColum.setPrefWidth(90);
			cedulaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Empleado,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Empleado, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getCedulaRuc());
				}
			});
			
			TableColumn<Empleado, String> nombresColum = new TableColumn<>("Nombres y apellidos");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(200);
			nombresColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Empleado,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Empleado, String> param) {
					String nombres = param.getValue().getNombres() + " " + param.getValue().getApellidos();
					return new SimpleObjectProperty<String>(nombres);
				}
			});
			
			TableColumn<Empleado, String> cargoColum = new TableColumn<>("Cargo");
			cargoColum.setMinWidth(10);
			cargoColum.setPrefWidth(100);
			cargoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Empleado,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Empleado, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getCargo().getCargo());
				}
			});
			tvDatos.getColumns().addAll(cedulaColum, nombresColum,cargoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void buscarCliente() {
		try {
			List<Empleado> listaEmpleados;
			ObservableList<Empleado> datos = FXCollections.observableArrayList();
			listaEmpleados = empleadoDAO.getListaEmpleados(txtBuscar.getText().toString());
			datos.setAll(listaEmpleados);
			tvDatos.setItems(datos);
			tvDatos.refresh();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void nuevoEmpleado() {
		try {
			helper.abrirPantallaModal("/administracion/FrmEmpleados.fxml","Registro de empleados", Context.getInstance().getStage());
			llenarDatos("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public void editarEmpleado() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Context.getInstance().setEmpleado(tvDatos.getSelectionModel().getSelectedItem());
				helper.abrirPantallaModal("/administracion/FrmEmpleados.fxml","Registro de empleados", Context.getInstance().getStage());
				llenarDatos("");
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un empleado a Editar", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void eliminarEmpleado() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() != null) {
				Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se procederá a dar de baja al empleado.. Desea Continuar?",Context.getInstance().getStage());
				if(result.get() == ButtonType.OK){
					Empleado empleadoSeleccionado = new Empleado();
					empleadoSeleccionado = tvDatos.getSelectionModel().getSelectedItem();
					empleadoSeleccionado.setEstado("I");
					empleadoDAO.getEntityManager().getTransaction().begin();
					empleadoDAO.getEntityManager().merge(empleadoSeleccionado);
					empleadoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Empleado Dado de Baja", Context.getInstance().getStage());
					llenarDatos("");
				}
			}else {
				helper.mostrarAlertaError("Debe Seleccionar un Cliente a Dar de Baja", Context.getInstance().getStage());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
