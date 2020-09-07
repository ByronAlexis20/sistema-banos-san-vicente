package controlador;

import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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

public class AdmListadoEmpleadosC {
	Tooltip toolTip;
	@FXML private TableView<Empleado> tvDatos;
	@FXML private TextField txtBuscar;
	EmpleadoDAO empleadoDAO = new EmpleadoDAO();
	
	public void initialize() {
		try {
			llenarDatos("");
			txtBuscar.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarEmpleado();
					} 
				} 
			}); 
			tvDatos.setRowFactory(tv -> {
	            TableRow<Empleado> row = new TableRow<>();
	            row.setOnMouseClicked(event -> {
	                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
	                	if(tvDatos.getSelectionModel().getSelectedItem() != null){
	    					Context.getInstance().setEmpleado(tvDatos.getSelectionModel().getSelectedItem());
	    					Context.getInstance().getStageModal().close();
	    				}
	                }
	            });
	            return row ;
	        });
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void buscarEmpleado() {
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
			
			TableColumn<Empleado, String> nombresColum = new TableColumn<>("Apellidos y nombres");
			nombresColum.setMinWidth(10);
			nombresColum.setPrefWidth(200);
			nombresColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Empleado,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Empleado, String> param) {
					String nombres = param.getValue().getApellidos() + " " + param.getValue().getNombres();
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
}
