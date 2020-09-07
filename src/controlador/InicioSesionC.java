package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Usuario;
import modelo.UsuarioDAO;
import util.Context;
import util.ControllerHelper;
import util.Encriptado;

public class InicioSesionC {
	Tooltip toolTip;
	@FXML private ImageView pbFoto;
	@FXML private Button btnAceptar;
	@FXML private Button btnCancelar;
	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtContrasenia;
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String rutaArchivo = "";
	String fecha;
	String expiro;
	String mensaje;
	
	ControllerHelper helper = new ControllerHelper();
	public void initialize() {
		try {
			
			String applicationPath = System.getProperty("user.dir");
			rutaArchivo = applicationPath + "/configuracion.txt";
			
			leerArchivo();
			System.out.println(fecha);
			if(mensaje.equals("SI"))
				validarBotones();
			
			toolTip = new Tooltip("Aceptar");
			btnAceptar.setStyle("-fx-graphic: url('/aceptar.png');-fx-cursor: hand;");
			btnAceptar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Cancelar");
			btnCancelar.setStyle("-fx-graphic: url('/cancelar.png');-fx-cursor: hand;");
			btnCancelar.setTooltip(toolTip);
			
			Image image = new Image("logo_banos.jpg");
			pbFoto.setImage(image);
			
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void aceptar() {
		try {
			List<Usuario> usuario;
    		usuario = usuarioDAO.getUsuario(Encriptado.Encriptar(txtUsuario.getText()),Encriptado.Encriptar(txtContrasenia.getText()));
    		if(usuario.size() == 1){
    			System.out.println(usuario.size());
    			Context.getInstance().setUsuario(usuario.get(0));
    			helper.abrirPantallaPrincipal();
    		}else {
    			helper.mostrarAlertaError("Usuario o clave incorrecto", Context.getInstance().getStage());
    		}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void cancelar() {
		try {
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Salir?",Context.getInstance().getStagePrincipal());
			if(result.get() == ButtonType.OK)
				System.exit(0);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void validarBotones() {
		try {
			if(expiro.equals("SI")) {
				btnAceptar.setDisable(true);
			}else {
				SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
				Date fechaVencimiento = formateador.parse(fecha);
				Date fechaActual = new Date();
				
				if(fechaVencimiento.after(fechaActual)) {
					System.out.println("La fecha actual es menor a la de vencimiento");
				}else {
					if(fechaActual.after(fechaVencimiento)) {
						System.out.println("La fecha actual es mayor a la de vencimiento");
						escribirArchivo();
						btnAceptar.setDisable(true);
					}else {
						System.out.println("Las fechas son iguales");
					}
				}
			}
		}catch(Exception ex) {
			
		}
	}
	private void leerArchivo() {
		File fichero = new File(rutaArchivo);
		Scanner s = null;
		try {
			s = new Scanner(fichero);
			while (s.hasNextLine()) {
				String linea = s.nextLine(); 	// Guardamos la linea en un String
				String[] parts = linea.split(":");
				if(parts[0].equals("Fecha"))
					fecha = parts[1];
				if(parts[0].equals("Expiro"))
					expiro = parts[1];
				if(parts[0].equals("Presente mensaje"))
					mensaje = parts[1];
			}

		} catch (Exception ex) {
			System.out.println("Mensaje: " + ex.getMessage());
		} finally {
			// Cerramos el fichero tanto si la lectura ha sido correcta o no
			try {
				if (s != null)
					s.close();
			} catch (Exception ex2) {
				System.out.println("Mensaje 2: " + ex2.getMessage());
			}
		}
	}
	private void escribirArchivo() throws IOException {
		File archivo = new File(rutaArchivo);
		BufferedWriter bw;
		if(archivo.exists()) {
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write("Fecha:" + fecha);
			bw.newLine();
			bw.write("Expiro:SI");
			bw.newLine();
			bw.write("Presente mensaje:SI");
			bw.close();
		}
	}
}
