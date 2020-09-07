package controlador;

import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import modelo.Empresa;
import modelo.EmpresaDAO;
import util.Context;
import util.ControllerHelper;

public class AdmEmpresaC {
	Tooltip toolTip;
	@FXML private TextField txtRepresentante;
	@FXML private TextField txtRuc;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtIva;
	@FXML private TextField txtEmail;
	@FXML private Button btnGrabar;
	@FXML private TextField txtRazonSocial;
	@FXML private TextField txtControl;
	Empresa empresa = new Empresa();
	EmpresaDAO empresaDAO = new EmpresaDAO();
	ControllerHelper helper = new ControllerHelper();

	private int longitudRuc = 13;
	private int longitudTelefono = 15;

	public void initialize() {
		try {
			
			toolTip = new Tooltip("Grabar empresa");
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
			btnGrabar.setTooltip(toolTip);
			
			restricciones();
		
			List<Empresa> listaEmpresa = empresaDAO.getEmpresa();
			if(listaEmpresa.size() > 0) {//tiene datos, hay una empresa activa
				empresa = listaEmpresa.get(0);
				cargarDatos();
			}else {
				empresa = new Empresa();
				empresa.setIdEmpresa(null);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void cargarDatos() {
		try {
			txtDireccion.setText(empresa.getDireccion());
			txtEmail.setText(empresa.getEmail());
			txtIva.setText(String.valueOf(empresa.getIva()));
			txtRazonSocial.setText(empresa.getRazonSocial());
			txtRepresentante.setText(empresa.getRepresentante());
			txtRuc.setText(empresa.getRuc());
			txtTelefono.setText(empresa.getTelefono());
			txtControl.setText(String.valueOf(empresa.getInicioControl()));
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void restricciones() {
		//longitud de las cadenas de textos ingresadas
		txtRuc.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtRuc.getText().length() > longitudRuc) {
					String s = txtRuc.getText().substring(0, longitudRuc);
					txtRuc.setText(s);
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
		//validar solo numeros
		txtRuc.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtRuc.setText(oldValue);
				}
			}
		});
		txtControl.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtControl.setText(oldValue);
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
		txtIva.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtIva.setText(oldValue);
				}
			}
		});
		//solo letras mayusculas
		txtDireccion.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String cadena = txtDireccion.getText().toUpperCase();
				txtDireccion.setText(cadena);
			}
		});
		txtRazonSocial.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String cadena = txtRazonSocial.getText().toUpperCase();
				txtRazonSocial.setText(cadena);
			}
		});
		txtRepresentante.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String cadena = txtRepresentante.getText().toUpperCase();
				txtRepresentante.setText(cadena);
			}
		});
	}
	public void grabar() {
		try {
			if(validarDatos() == false) {
				return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				empresa.setDireccion(txtDireccion.getText());
				empresa.setEmail(txtEmail.getText());
				empresa.setEstado("A");
				empresa.setIva(Integer.parseInt(txtIva.getText()));
				empresa.setRazonSocial(txtRazonSocial.getText());
				empresa.setRepresentante(txtRepresentante.getText());
				empresa.setRuc(txtRuc.getText());
				empresa.setTelefono(txtTelefono.getText());
				empresa.setInicioControl(Integer.parseInt(txtControl.getText()));
				
				if(empresa.getIdEmpresa() != null) {//modifica
					empresaDAO.getEntityManager().getTransaction().begin();
					empresaDAO.getEntityManager().merge(empresa);
					empresaDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
					cargarDatos();
				}else {//inserta
					empresaDAO.getEntityManager().getTransaction().begin();
					empresaDAO.getEntityManager().persist(empresa);
					empresaDAO.getEntityManager().getTransaction().commit();
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
					cargarDatos();
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
			if(txtRuc.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar el ruc de la institución", Context.getInstance().getStage());
				return false;
			}

			if(txtRazonSocial.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar razón social de la empresa", Context.getInstance().getStage());
				return false;
			}
			if(txtIva.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar el iva", Context.getInstance().getStage());
				return false;
			}			
			
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
}
