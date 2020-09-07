package controlador;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.Cliente;
import modelo.ClienteDAO;
import util.Context;
import util.ControllerHelper;

public class RecClientesC {
	Tooltip toolTip;
	@FXML private Button btnSalir;
	@FXML private TextField txtApellidos;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtNombres;
	@FXML private TextField txtCedula;
	@FXML private Button btnGrabar;
	@FXML private TextField txtCorreo;

	ClienteDAO clienteDAO = new ClienteDAO();
	ControllerHelper helper = new ControllerHelper();
	Cliente cliente;

	public void initialize() {
		try {
			restricciones();
			txtCedula.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarClienteCedula(txtCedula.getText());
					} 
				} 
			}); 
			toolTip = new Tooltip("Salir");
			btnSalir.setTooltip(toolTip);
			btnSalir.setStyle("-fx-graphic: url('/salir.png');-fx-cursor: hand;");
			
			toolTip = new Tooltip("Grabar cliente");
			btnGrabar.setTooltip(toolTip);
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void restricciones() {
		//longitud de las cadenas de textos ingresadas
		txtCedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtCedula.getText().length() > 13) {
					String s = txtCedula.getText().substring(0, 13);
					txtCedula.setText(s);
				}
			}
		});
		txtNombres.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtNombres.getText().length() > 50) {
					String s = txtNombres.getText().substring(0, 50);
					txtNombres.setText(s);
				}
			}
		});
		txtApellidos.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtApellidos.getText().length() > 50) {
					String s = txtApellidos.getText().substring(0, 50);
					txtApellidos.setText(s);
				}
			}
		});
		txtTelefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtTelefono.getText().length() > 15) {
					String s = txtTelefono.getText().substring(0, 15);
					txtTelefono.setText(s);
				}
			}
		});
		txtCorreo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtCorreo.getText().length() > 100) {
					String s = txtCorreo.getText().substring(0, 100);
					txtCorreo.setText(s);
				}
			}
		});
		txtDireccion.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtDireccion.getText().length() > 255) {
					String s = txtDireccion.getText().substring(0, 255);
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
		txtNombres.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String cadena = txtNombres.getText().toUpperCase();
				txtNombres.setText(cadena);
			}
		});
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
	}
	private void buscarClienteCedula(String cedula) {
		try {
			List<Cliente> listaCliente = clienteDAO.getClienteCedula(cedula);
			if(listaCliente.size() > 0) {
				cliente = listaCliente.get(0);
				txtCedula.setEditable(false);
				txtApellidos.setText(listaCliente.get(0).getApellidos());
				txtDireccion.setText(listaCliente.get(0).getDireccion());
				txtTelefono.setText(listaCliente.get(0).getTelefono());
				txtNombres.setText(listaCliente.get(0).getNombres());
				txtCedula.setText(listaCliente.get(0).getCedulaRuc());
				txtCorreo.setText(listaCliente.get(0).getEmail());
			}else {
				helper.mostrarAlertaAdvertencia("Cliente no existe en la base de datos, registe los datos para grabar", Context.getInstance().getStage());
				cliente = null;
				txtCedula.setEditable(true);
				txtApellidos.setText("");
				txtDireccion.setText("");
				txtTelefono.setText("");
				txtNombres.setText("");
				txtCorreo.setText("");
			}
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
				if(cliente != null) {
					cliente.setApellidos(txtApellidos.getText());
					cliente.setCedulaRuc(txtCedula.getText());
					cliente.setDireccion(txtDireccion.getText());
					cliente.setEmail(txtCorreo.getText());
					cliente.setEstado("A");
					cliente.setFechaModificacion(new Date());
					cliente.setNombres(txtNombres.getText());
					cliente.setTelefono(txtTelefono.getText());
					cliente.setUsuarioId(Context.getInstance().getUsuario().getIdUsuario());
					
					clienteDAO.getEntityManager().getTransaction().begin();
					clienteDAO.getEntityManager().merge(cliente);
					clienteDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
					limpiar();
				}else {
					cliente = new Cliente();
					cliente.setIdCliente(null);
					cliente.setApellidos(txtApellidos.getText());
					cliente.setCedulaRuc(txtCedula.getText());
					cliente.setDireccion(txtDireccion.getText());
					cliente.setEmail(txtCorreo.getText());
					cliente.setEstado("A");
					cliente.setFechaIngreso(new Date());
					cliente.setNombres(txtNombres.getText());
					cliente.setTelefono(txtTelefono.getText());
					cliente.setUsuarioId(Context.getInstance().getUsuario().getIdUsuario());
					
					clienteDAO.getEntityManager().getTransaction().begin();
					clienteDAO.getEntityManager().persist(cliente);
					clienteDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
					limpiar();
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void limpiar() {
		txtCedula.setEditable(true);
		txtApellidos.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtNombres.setText("");
		txtCedula.setText("");
		txtCorreo.setText("");
	}
	private boolean validarDatos() {
		try {
			boolean bandera = false;
			if(txtCedula.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar cedula del cliente", Context.getInstance().getStage());
				return false;
			}
			if(cliente == null)
				if(verificarCedula() == false){
					helper.mostrarAlertaAdvertencia("Cliente con el número de cédula ya existe", Context.getInstance().getStage());
					return false;
				}
			if(txtNombres.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar el nombre", Context.getInstance().getStage());
				return false;
			}			
			if(txtApellidos.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar el apellido", Context.getInstance().getStage());
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
			List<Cliente> listaCliente = clienteDAO.getClienteCedula(txtCedula.getText());
			if(listaCliente.size() > 0)
				return false;
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
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
}