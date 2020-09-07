package controlador;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.Empleado;
import modelo.EmpleadoDAO;
import modelo.Rol;
import modelo.RolDAO;
import modelo.Usuario;
import modelo.UsuarioDAO;
import util.Context;
import util.ControllerHelper;
import util.Encriptado;

public class AdmUsuarioC {

	Tooltip toolTip;
	@FXML private TextField txtApellidos;
	@FXML private TextField txtTelefono;
	@FXML private Button btnNuevo;
	@FXML private ComboBox<Rol> cboPerfil;
	@FXML private TextField txtNombres;
	@FXML private TextField txtCedula;
	@FXML private Button btnGrabar;
	@FXML private Button btnBuscar;
	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtClave;
	
	EmpleadoDAO empleadoDAO = new EmpleadoDAO();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	RolDAO rolDAO = new RolDAO();
	Empleado empleado;
	Usuario usuario;
	
	ControllerHelper helper = new ControllerHelper();
	public void initialize() {
		try {
			cboPerfil.setStyle("-fx-cursor: hand;");
			
			toolTip = new Tooltip("Nuevo usuario");
			//btnNuevo.setStyle("-fx-graphic: url('/nuevo.png');-fx-cursor: hand;");
			btnNuevo.setTooltip(toolTip);
			
			toolTip = new Tooltip("Grabar usuario");
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
			btnGrabar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Buscar empleado");
			btnBuscar.setStyle("-fx-graphic: url('/buscar.png');-fx-cursor: hand;");
			btnBuscar.setTooltip(toolTip);
			
			txtNombres.setEditable(false);
			txtApellidos.setEditable(false);
			txtTelefono.setEditable(false);
			
			llenarCombos();
			txtCedula.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarEmpleadoCedula(txtCedula.getText());
					} 
				} 
			}); 
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void llenarCombos() {
		try {
			ObservableList<Rol> datos = FXCollections.observableArrayList();
			List<Rol> listaServicio = rolDAO.getListaRolActivo();
			datos.addAll(listaServicio);
			cboPerfil.setItems(datos);
			cboPerfil.setPromptText("Seleccione rol");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void buscarEmpleadoCedula(String cedula) {
		try {
			List<Empleado> lista = empleadoDAO.getEmpleadoCedula(cedula);
			if(lista.size() > 0) {//tiene un dato
				empleado = lista.get(0);
				txtApellidos.setText(empleado.getApellidos());
				txtCedula.setText(empleado.getCedulaRuc());
				txtNombres.setText(empleado.getNombres());
				txtTelefono.setText(empleado.getTelefono());
				//preguntar si es un usuario
				List<Usuario> listaUsuario = usuarioDAO.getUsuariosPorEmpleado(empleado.getIdEmpleado());
				if(listaUsuario.size() > 0) {
					usuario = listaUsuario.get(0);
					txtUsuario.setText(Encriptado.Desencriptar(listaUsuario.get(0).getUsuario()));
					txtClave.setText(Encriptado.Desencriptar(listaUsuario.get(0).getClave()));
					cboPerfil.getSelectionModel().select(listaUsuario.get(0).getRol());
				}else {
					usuario = null;
					txtUsuario.setText("");
					txtClave.setText("");
					cboPerfil.getSelectionModel().select(null);
				}
			}else {//no tiene datos
				helper.mostrarAlertaAdvertencia("Empleado no existe!", Context.getInstance().getStage());
				limpiar();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void limpiar() {
		txtApellidos.setText("");
		txtCedula.setText("");
		txtNombres.setText("");
		txtTelefono.setText("");
		cboPerfil.getSelectionModel().select(null);
		txtClave.setText("");
		txtUsuario.setText("");
		cboPerfil.getSelectionModel().select(null);
		empleado = null;
	}
	public void buscarEmpleado() {
		try {
			Context.getInstance().setEmpleado(null);
			helper.abrirPantallaModal("/administracion/FrmListaEmpleados.fxml","Listado de empleados", Context.getInstance().getStage());
			if(Context.getInstance().getEmpleado() != null) {
				buscarEmpleadoCedula(Context.getInstance().getEmpleado().getCedulaRuc());
			}
			Context.getInstance().setEmpleado(null);
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
				if(usuario != null) {//existe el usuario, se estan modificando los datos
					usuario.setRol(cboPerfil.getSelectionModel().getSelectedItem());
					usuario.setClave(Encriptado.Encriptar(txtClave.getText()));
					usuario.setUsuario(Encriptado.Encriptar(txtUsuario.getText()));
					empleadoDAO.getEntityManager().getTransaction().begin();
					empleadoDAO.getEntityManager().merge(usuario);
					empleadoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
					limpiar();
				}else {//usuario nuevo
					usuario = new Usuario();
					usuario.setIdUsuario(null);
					usuario.setEstado("A");
					usuario.setRol(cboPerfil.getSelectionModel().getSelectedItem());
					usuario.setClave(Encriptado.Encriptar(txtClave.getText()));
					usuario.setUsuario(Encriptado.Encriptar(txtUsuario.getText()));
					usuario.setEmpleado(empleado);
					empleadoDAO.getEntityManager().getTransaction().begin();
					empleadoDAO.getEntityManager().persist(usuario);
					empleadoDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
					limpiar();
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
		}
	}
	private boolean validarDatos() {
		try {
			boolean bandera = false;
			if(empleado == null) {
				helper.mostrarAlertaAdvertencia("Debe registrar un empleado", Context.getInstance().getStage());
				return false;
			}
			if(cboPerfil.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar perfil de usuario", Context.getInstance().getStage());
				return false;
			}
			if(txtUsuario.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar nombre de usuario", Context.getInstance().getStage());
				return false;
			}
			if(validarUsuario() == true) {
				helper.mostrarAlertaAdvertencia("El usuario ya existe!!", Context.getInstance().getStage());
				txtUsuario.requestFocus();
				return false;	
			}
			if(txtClave.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar contraseña para el usuario", Context.getInstance().getStage());
				return false;
			}
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
	boolean validarUsuario() {
		try {
			List<Usuario> listaUsuario;
			listaUsuario = usuarioDAO.getValidarUsuario(Encriptado.Encriptar(txtUsuario.getText()), empleado.getIdEmpleado());
			if(listaUsuario.size() != 0)
				return true;
			else
				return false;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public void nuevo() {
		try {
			limpiar();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
