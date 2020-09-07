package controlador;

import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import modelo.Cargo;
import modelo.CargoDAO;
import util.Context;
import util.ControllerHelper;

public class AdmCargosC {
	Tooltip toolTip;
	@FXML private TableView<Cargo> tvDatos;
	@FXML private Button btnNuevo;
	@FXML private CheckBox chkEstado;
	@FXML private TextField txtCodigo;
	@FXML private Button btnGrabar;
	@FXML private TextArea txtDescripcion;

	CargoDAO cargoDAO = new CargoDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			txtCodigo.setText("0");
			txtCodigo.setEditable(false);
			chkEstado.setSelected(true);
			
			toolTip = new Tooltip("Nuevo cargo");
			btnNuevo.setStyle("-fx-graphic: url('/nuevo.png');-fx-cursor: hand;");
			btnNuevo.setTooltip(toolTip);
			
			toolTip = new Tooltip("Grabar cargo");
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
			btnGrabar.setTooltip(toolTip);
			
			llenarDatos();
			txtDescripcion.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					String cadena = txtDescripcion.getText().toUpperCase();
					txtDescripcion.setText(cadena);
				}
			});
			tvDatos.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					recuperarDatos(tvDatos.getSelectionModel().getSelectedItem());

				}
			});
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void recuperarDatos(Cargo serv){
		try {
			txtCodigo.setText(String.valueOf(serv.getIdCargo()));
			txtDescripcion.setText(serv.getCargo());
			if(serv.getEstado().equals("A"))
				chkEstado.setSelected(true);
			else
				chkEstado.setSelected(false);	
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private void llenarDatos() {
		try {
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Cargo> listaServicios;
			ObservableList<Cargo> datos = FXCollections.observableArrayList();
			listaServicios = cargoDAO.getListaCargos();
			datos.setAll(listaServicios);
			
			//llenar los datos en la tabla
			TableColumn<Cargo, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(60);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cargo,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Cargo, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdCargo()));
				}
			});
			
			TableColumn<Cargo, String> descripcionColum = new TableColumn<>("Descripción");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(150);
			descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cargo,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Cargo, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getCargo());
				}
			});
			
			TableColumn<Cargo, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(50);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cargo,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Cargo, String> param) {
					String cadena = "";
					if(param.getValue().getEstado().equals("A"))
						cadena = "ACTIVO";
					else
						cadena = "INACTIVO";
					return new SimpleObjectProperty<String>(cadena);
				}
			});
			tvDatos.getColumns().addAll(idColum, descripcionColum,estadoColum);
			tvDatos.setItems(datos);
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
				Cargo cargoGrabar = new Cargo();
				if(txtCodigo.getText().equals("0")) {
					cargoGrabar.setIdCargo(null);
				}else
					cargoGrabar.setIdCargo(Integer.parseInt(txtCodigo.getText()));
				String estado = "";
				if(chkEstado.isSelected())
					estado = "A";
				else
					estado = "I";
				cargoGrabar.setEstado(estado);
				cargoGrabar.setCargo(txtDescripcion.getText());
				cargoDAO.getEntityManager().getTransaction().begin();
				if(txtCodigo.getText().equals("0")) {
					cargoDAO.getEntityManager().persist(cargoGrabar);
				}else {
					cargoDAO.getEntityManager().merge(cargoGrabar);
				}
				cargoDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
				llenarDatos();
				txtCodigo.setText("0");
				txtDescripcion.setText("");
				chkEstado.setSelected(true);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
		}
	}
	private boolean validarDatos() {
		try {
			boolean bandera = false;
			if(txtDescripcion.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar descripción del servicio", Context.getInstance().getStage());
				return false;
			}
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
	public void nuevo() {
		try {
			txtCodigo.setText("0");
			chkEstado.setSelected(true);
			txtDescripcion.setText("");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
