package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.Context;
import util.ControllerHelper;
import util.Encriptado;

public class LaunchSystemSV extends Application{
	ControllerHelper helper = new ControllerHelper();
	private String rutaArchivo = "";
	private String mensajePresentar = "";
	@Override
	public void start(Stage stage) {
		try {
			System.out.println(Encriptado.Desencriptar("qaQoTEEmsFpjskSjo2u4mQ=="));
			String fxmlInicio = "";
			String applicationPath = System.getProperty("user.dir");
			rutaArchivo = applicationPath + "/configuracion.txt";
			crearArchivo();
			leerArchivo();
			
			if(mensajePresentar.equals("SI"))
				fxmlInicio = "/licencia/FrmVencimiento.fxml";
			else
				fxmlInicio = "/principal/FrmInicioSesion.fxml";
			
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
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void crearArchivo() throws IOException {
		File archivo = new File(rutaArchivo);
		BufferedWriter bw;
		if(!archivo.exists()) {
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write("Fecha:10/11/2019");
			bw.newLine();
			bw.write("Expiro:NO");
			bw.newLine();
			bw.write("Presente mensaje:SI");
			bw.close();
		}
	}
	private void leerArchivo() {
		File fichero = new File(rutaArchivo);
		Scanner s = null;
		try {
			// Leemos el contenido del fichero
			System.out.println("... Leemos el contenido del fichero ...");
			s = new Scanner(fichero);

			// Leemos linea a linea el fichero
			while (s.hasNextLine()) {
				String linea = s.nextLine(); 	// Guardamos la linea en un String
				String[] parts = linea.split(":");
				
				if(parts[0].equals("Presente mensaje"))
					mensajePresentar = parts[1];
				System.out.println(mensajePresentar);      // Imprimimos la linea
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
