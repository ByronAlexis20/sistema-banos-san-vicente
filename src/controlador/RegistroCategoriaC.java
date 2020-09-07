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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import modelo.Categoria;
import modelo.CategoriaDAO;
import modelo.Servicio;
import modelo.ServicioDAO;
import util.Context;
import util.ControllerHelper;

public class RegistroCategoriaC {
	Tooltip toolTip;
	@FXML private TableView<Categoria> tvDatos;
	@FXML private TextField txtCategoria;
	@FXML private Button btnNuevo;
	@FXML private CheckBox chkEstado;
	@FXML private TextField txtCodigo;
	@FXML private TextField txtPrecio;
	@FXML private ComboBox<Servicio> cboServicio;
	@FXML private Button btnGrabar;

	CategoriaDAO categoriaDAO = new CategoriaDAO();
	ServicioDAO servicioDAO = new ServicioDAO();
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			txtCodigo.setText("0");
			txtCodigo.setEditable(false);
			chkEstado.setSelected(true);
			txtCategoria.setText("");
			cboServicio.getSelectionModel().select(null);
			
			toolTip = new Tooltip("Nueva categoría");
			btnNuevo.setStyle("-fx-graphic: url('/nuevo.png');-fx-cursor: hand;");
			btnNuevo.setTooltip(toolTip);
			
			toolTip = new Tooltip("Grabar categoría");
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
			btnGrabar.setTooltip(toolTip);
			
			llenarCombos();
			llenarDatos();
			txtCategoria.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					String cadena = txtCategoria.getText().toUpperCase();
					txtCategoria.setText(cadena);
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
	private void recuperarDatos(Categoria categoria){
		try {
			txtCodigo.setText(String.valueOf(categoria.getIdCategoria()));
			txtCategoria.setText(categoria.getCategoria());
			cboServicio.getSelectionModel().select(categoria.getServicio());
			if(categoria.getEstado().equals("A"))
				chkEstado.setSelected(true);
			else
				chkEstado.setSelected(false);	
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
	@SuppressWarnings("unchecked")
	private void llenarDatos() {
		try {
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			List<Categoria> listaServicios;
			ObservableList<Categoria> datos = FXCollections.observableArrayList();
			listaServicios = categoriaDAO.getListaCategorias();
			datos.setAll(listaServicios);
			
			//llenar los datos en la tabla
			TableColumn<Categoria, String> idColum = new TableColumn<>("Código");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(60);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdCategoria()));
				}
			});
			
			TableColumn<Categoria, String> descripcionColum = new TableColumn<>("Categoria");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(150);
			descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getCategoria());
				}
			});
			
			TableColumn<Categoria, String> servicioColum = new TableColumn<>("Servicio");
			servicioColum.setMinWidth(10);
			servicioColum.setPrefWidth(150);
			servicioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getServicio().getServicio());
				}
			});
			
			TableColumn<Categoria, String> estadoColum = new TableColumn<>("Estado");
			estadoColum.setMinWidth(10);
			estadoColum.setPrefWidth(50);
			estadoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Categoria,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Categoria, String> param) {
					String cadena = "";
					if(param.getValue().getEstado().equals("A"))
						cadena = "ACTIVO";
					else
						cadena = "INACTIVO";
					return new SimpleObjectProperty<String>(cadena);
				}
			});
			tvDatos.getColumns().addAll(idColum, servicioColum,descripcionColum ,estadoColum);
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
				Categoria categoriaGrabar = new Categoria();
				if(txtCodigo.getText().equals("0")) {
					categoriaGrabar.setIdCategoria(null);
				}else
					categoriaGrabar.setIdCategoria(Integer.parseInt(txtCodigo.getText()));
				String estado = "";
				if(chkEstado.isSelected())
					estado = "A";
				else
					estado = "I";
				categoriaGrabar.setEstado(estado);
				categoriaGrabar.setCategoria(txtCategoria.getText());
				categoriaGrabar.setServicio(cboServicio.getSelectionModel().getSelectedItem());
				categoriaDAO.getEntityManager().getTransaction().begin();
				if(txtCodigo.getText().equals("0")) {
					categoriaDAO.getEntityManager().persist(categoriaGrabar);
				}else {
					categoriaDAO.getEntityManager().merge(categoriaGrabar);
				}
				categoriaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
				llenarDatos();
				txtCodigo.setText("0");
				txtCategoria.setText("");
				cboServicio.getSelectionModel().select(null);
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
			if(txtCategoria.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar descripción del servicio", Context.getInstance().getStage());
				return false;
			}
			if(cboServicio.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un servicio", Context.getInstance().getStage());
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
			txtCategoria.setText("");
			cboServicio.getSelectionModel().select(null);
			chkEstado.setSelected(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
