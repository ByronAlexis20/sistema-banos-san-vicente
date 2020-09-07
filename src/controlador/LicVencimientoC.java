package controlador;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.Context;
import util.ControllerHelper;

public class LicVencimientoC {
	@FXML private ImageView imFoto;
	@FXML private Button btnContinuar;
	@FXML private Label lblMensaje;
	private String rutaArchivo = "";
	String fecha;
	String expiro;
	public void initialize() {
		try {
			String applicationPath = System.getProperty("user.dir");
			rutaArchivo = applicationPath + "/configuracion.txt";
			
			leerArchivo();
			
			Image image = new Image("icono_advertencia.png");
			imFoto.setImage(image);
			
			if(expiro.equals("SI")) 
				lblMensaje.setText("Tu etapa de prueba de 100 días ya expiró el " + fecha);
			else
				lblMensaje.setText("Tu etapa de prueba de 100 días caducará el " + fecha + ",");
		}catch(Exception ex) {
			
		}
	}
	ControllerHelper helper = new ControllerHelper();
	public void continuar() {
		try {
			Context.getInstance().getStagePrincipal().close();
			try {
				Stage stage = new Stage();
				String fxmlInicio = "/principal/FrmInicioSesion.fxml";
				
				FXMLLoader root = new FXMLLoader();
				root.setLocation(getClass().getResource(fxmlInicio));
				AnchorPane page = (AnchorPane) root.load();
				Scene scene = new Scene(page);
				stage.setScene(scene);
				stage.getIcons().add(new Image("/icono.png"));
				stage.setTitle("");
				Context.getInstance().setStagePrincipal(stage);
				//stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea salir del sistema?",Context.getInstance().getStage());
						if(result.get() == ButtonType.OK)
							System.exit(0);
						else
							event.consume();
					}
				});
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}catch(Exception ex) {
			
		}
    }
	
	private void leerArchivo() {
		File fichero = new File(rutaArchivo);
		Scanner s = null;
		try {
			System.out.println("... Leemos el contenido del fichero ...");
			s = new Scanner(fichero);

			while (s.hasNextLine()) {
				String linea = s.nextLine(); 	// Guardamos la linea en un String
				String[] parts = linea.split(":");
				if(parts[0].equals("Fecha"))
					fecha = parts[1];
				if(parts[0].equals("Expiro"))
					expiro = parts[1];
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
}
