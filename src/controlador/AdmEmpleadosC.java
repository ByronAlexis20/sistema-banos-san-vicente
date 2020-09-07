package controlador;

import java.util.Date;
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
import modelo.Cargo;
import modelo.CargoDAO;
import modelo.Empleado;
import modelo.EmpleadoDAO;
import util.Context;
import util.ControllerHelper;

public class AdmEmpleadosC {
	Tooltip toolTip;
	
	@FXML private Button btnSalir;
	@FXML private Button btnAgregarCargo;
	@FXML private Button btnNuevo;
	@FXML private TextField txtApellidos;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtTelefono;
	@FXML private ComboBox<Cargo> cboCargos;
	@FXML private TextField txtNombres;
	@FXML private TextField txtCedula;
	@FXML private Button btnGrabar;

	private int longitudCedula = 13;
	private int longitudNombres = 50;
	private int longitudApellidos = 50;
	private int longitudTelefono = 15;
	private int longitudDireccion = 250;
	
	CargoDAO cargoDAO = new CargoDAO();
	EmpleadoDAO empleadoDAO = new EmpleadoDAO();
	Empleado empleadoSeleccionado;
	ControllerHelper helper = new ControllerHelper();
	
	public void initialize() {
		try {
			cboCargos.setStyle("-fx-cursor: hand;");
			
			toolTip = new Tooltip("Grabar empleado");
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
			btnGrabar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Salir");
			btnSalir.setStyle("-fx-graphic: url('/salir.png');-fx-cursor: hand;");
			btnSalir.setTooltip(toolTip);
			
			toolTip = new Tooltip("Agregar cargos");
			btnAgregarCargo.setStyle("-fx-graphic: url('/agregar.png');-fx-cursor: hand;");
			btnAgregarCargo.setTooltip(toolTip);
			
			limpiar();
			restricciones();
			llenarCombos();
			if(Context.getInstance().getEmpleado() != null) {
				empleadoSeleccionado = Context.getInstance().getEmpleado();
				Context.getInstance().setEmpleado(null);
				cargarDatosEmpleado();
			}else {
				empleadoSeleccionado = new Empleado();
				empleadoSeleccionado.setIdEmpleado(null);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void restricciones() {
		//longitud de las cadenas de textos ingresadas
		txtCedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtCedula.getText().length() > longitudCedula) {
					String s = txtCedula.getText().substring(0, longitudCedula);
					txtCedula.setText(s);
				}
			}
		});
		txtTelefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtTelefono.getText().length() > longitudTelefono) {
					String s = txtTelefono.getText().substring(0, longitudTelefono);
					txtTelefono.setText(s);
				}
			}
		});
		txtNombres.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtNombres.getText().length() > longitudNombres) {
					String s = txtNombres.getText().substring(0, longitudNombres);
					txtNombres.setText(s);
				}
			}
		});
		txtApellidos.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtApellidos.getText().length() > longitudApellidos) {
					String s = txtApellidos.getText().substring(0, longitudApellidos);
					txtApellidos.setText(s);
				}
			}
		});
		txtDireccion.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtDireccion.getText().length() > longitudDireccion) {
					String s = txtDireccion.getText().substring(0, longitudDireccion);
					txtDireccion.setText(s);
				}
			}
		});
		//validar solo numeros
		txtCedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtCedula.setText(oldValue);
				}
			}
		});
		txtTelefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtTelefono.setText(oldValue);
				}
			}
		});
		//solo letras mayusculas
		txtApellidos.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String cadena = txtApellidos.getText().toUpperCase();
				txtApellidos.setText(cadena);
			}
		});
		txtDireccion.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String cadena = txtDireccion.getText().toUpperCase();
				txtDireccion.setText(cadena);
			}
		});
		txtNombres.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String cadena = txtNombres.getText().toUpperCase();
				txtNombres.setText(cadena);
			}
		});
	}
	
	private void cargarDatosEmpleado() {
		try {
			txtCedula.setText(empleadoSeleccionado.getCedulaRuc());
			txtApellidos.setText(empleadoSeleccionado.getApellidos());
			txtDireccion.setText(empleadoSeleccionado.getDireccion());
			txtNombres.setText(empleadoSeleccionado.getNombres());
			txtTelefono.setText(empleadoSeleccionado.getTelefono());
			cboCargos.getSelectionModel().select(empleadoSeleccionado.getCargo());
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void llenarCombos() {
		try {
			cboCargos.getItems().clear();
			ObservableList<Cargo> datos = FXCollections.observableArrayList();
			List<Cargo> listaCargo = cargoDAO.getListaCargoActivo();
			datos.addAll(listaCargo);
			cboCargos.setItems(datos);
			cboCargos.setPromptText("Seleccione cargo");
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
				empleadoSeleccionado.setApellidos(txtApellidos.getText());
				empleadoSeleccionado.setCargo(cboCargos.getSelectionModel().getSelectedItem());
				empleadoSeleccionado.setCedulaRuc(txtCedula.getText());
				empleadoSeleccionado.setDireccion(txtDireccion.getText());
				empleadoSeleccionado.setEstado("A");
				empleadoSeleccionado.setNombres(txtNombres.getText());
				empleadoSeleccionado.setTelefono(txtTelefono.getText());
				
				if(empleadoSeleccionado.getIdEmpleado() != null) {//modifica
					empleadoSeleccionado.setFechaModificacion(new Date());
					cargoDAO.getEntityManager().getTransaction().begin();
					cargoDAO.getEntityManager().merge(empleadoSeleccionado);
					cargoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
				}else {//inserta
					empleadoSeleccionado.setFechaIngreso(new Date());
					empleadoDAO.getEntityManager().getTransaction().begin();
					empleadoDAO.getEntityManager().persist(empleadoSeleccionado);
					empleadoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
		}
	}
	
	private void limpiar() {
		txtCedula.setText("");
		txtApellidos.setText("");
		txtDireccion.setText("");
		txtNombres.setText("");
		txtTelefono.setText("");
	}
	
	private boolean validarDatos() {
		try {
			boolean bandera = false;
			if(txtCedula.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar cédula de identidad", Context.getInstance().getStage());
				return false;
			}
			if(empleadoSeleccionado.getIdEmpleado() == null) {
				if(verificarCedula() == false) {
					helper.mostrarAlertaAdvertencia("Cédula ya existe en el registro", Context.getInstance().getStage());
					return false;
				}
			}
			if(txtNombres.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar nombres", Context.getInstance().getStage());
				return false;
			}
			if(txtApellidos.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar apellidos", Context.getInstance().getStage());
				return false;
			}			
			if(cboCargos.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un cargo", Context.getInstance().getStage());
				return false;
			}
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
	
	private boolean verificarCedula() {
		try {
			boolean bandera = false;
			List<Empleado> listaEmpleado = empleadoDAO.getEmpleadoCedula(txtCedula.getText());
			if(listaEmpleado.size() > 0) {
				return false;
			}
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
	public void salir() {
		try {
			Context.getInstance().getStageModal().close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void agregarCargo() {
		try {
			helper.abrirPantallaModalSecundario("/registros/FrmCargos.fxml","Registro de cargos", Context.getInstance().getStage());
			llenarCombos();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
