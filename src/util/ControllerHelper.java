package util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.controlsfx.control.Notifications;

import br.com.supremeforever.suprememdiwindow.MDICanvas;
import br.com.supremeforever.suprememdiwindow.MDIWindow;
import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.LaunchSystemSV;

public class ControllerHelper {
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public void abrirPantallaModal(String uriVista, String titulo,Stage parent){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LaunchSystemSV.class.getResource(uriVista));
			Parent page = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle(titulo);
			stage.getIcons().add(new Image("/icono.png"));
			stage.initOwner(parent);
			stage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(page);
			stage.setScene(scene);
			Context.getInstance().setStageModal(stage);
			stage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace(); //Retorna Connection reset cuando demora mucho
		}
	}
	public void abrirPantallaModalSecundario(String uriVista, String titulo,Stage parent){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LaunchSystemSV.class.getResource(uriVista));
			Parent page = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle(titulo);
			stage.getIcons().add(new Image("/icono.png"));
			stage.initOwner(parent);
			stage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(page);
			stage.setScene(scene);
			Context.getInstance().setStageModalSecundario(stage);
			stage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace(); //Retorna Connection reset cuando demora mucho
		}
	}
	public void abrirPantallaPrincipal() {
		try {
			ImageView imagen = new ImageView();
			Image ima = new Image("/imagen_ventana.png");
			imagen.setImage(ima);
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/icono.png"));
			stage.setMaximized(true);
			MDICanvas canvas = new MDICanvas();
			canvas.setPrefSize(50000, 50000);
			
			//armar el menu
			MenuBar menu = new MenuBar();
			menu.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color; ");
			Menu menuAdministracion = new Menu("Administraci�n");
			MenuItem menuEmpleados = new MenuItem("Empleados");
			abrirModal(menuEmpleados,canvas,"/administracion/FrmListadoEmplados.fxml","Registro de empleados",imagen,"empleados");
			MenuItem menuEmpresa = new MenuItem("Empresa");
			abrirModal(menuEmpresa,canvas,"/administracion/FrmEmpresa.fxml","Datos de empresa",imagen,"empresa");
			MenuItem menuUsuario = new MenuItem("Usuarios");
			abrirModal(menuUsuario,canvas,"/administracion/FrmUsuarios.fxml","Usuarios",imagen,"usuarios");
			
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				menuAdministracion.getItems().addAll(menuEmpleados,menuEmpresa,menuUsuario);
			
			Menu menuRegistro = new Menu("Registros");
			//MenuItem menuCargos = new MenuItem("Cargos");
			//abrirModal(menuCargos,canvas,"/registros/FrmCargos.fxml","Registro de Cargos",imagen,"cargos");
			MenuItem menuCategoria = new MenuItem("Categor�a");
			abrirModal(menuCategoria,canvas,"/registros/FrmCategoria.fxml","Registro de categorias",imagen,"categoria");
			MenuItem menuServicios = new MenuItem("Servicios");
			abrirModal(menuServicios,canvas,"/registros/FrmServicios.fxml","Registro de servicios",imagen,"servicios");
			
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				menuRegistro.getItems().addAll(menuCategoria,menuServicios);
			
			Menu menuRecaudaciones = new Menu("Recaudaciones");
			MenuItem menuRecaudacion = new MenuItem("Generar factura");
			abrirModal(menuRecaudacion,canvas,"/recaudaciones/FrmRecaudaciones.fxml","Recaudaciones",imagen,"recaudaciones");
			MenuItem menuFacturasDiarias = new MenuItem("Facturas generadas");
			abrirModal(menuFacturasDiarias,canvas,"/recaudaciones/FrmRecaudacionGenerada.fxml","Recaudaciones",imagen,"recaudacionesDiarias");
			
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				menuRecaudaciones.getItems().addAll(menuRecaudacion,menuFacturasDiarias);
			else
				menuRecaudaciones.getItems().addAll(menuRecaudacion,menuFacturasDiarias);
			
			Menu menuConfiguraciones = new Menu("Configuraciones");
			MenuItem menuAsignarPrecios = new MenuItem("Asignar precios");
			abrirModal(menuAsignarPrecios,canvas,"/configuraciones/FrmAsignarPrecios.fxml","Recaudaciones",imagen,"recaudaciones");
			
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				menuConfiguraciones.getItems().add(menuAsignarPrecios);
				
			
			Menu menuReporte = new Menu("Reportes");
			MenuItem menuRepRecaudacion = new MenuItem("Recaudaciones");
			abrirModal(menuRepRecaudacion,canvas,"/reportes/FrmRecaudaciones.fxml","Reporte recaudaciones",imagen,"reporteRecaudaciones");
			MenuItem menuRepOtros = new MenuItem("Otros reportes");
			abrirModal(menuRepOtros,canvas,"/reportes/FrmOtros.fxml","Otros Reportes",imagen,"reporteOtros");
			
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				menuReporte.getItems().addAll(menuRepRecaudacion,menuRepOtros);
			else
				menuReporte.getItems().addAll(menuRepRecaudacion);
			
		
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				menu.getMenus().addAll(menuAdministracion,menuRegistro,menuRecaudaciones,menuConfiguraciones,menuReporte);
			else
				menu.getMenus().addAll(menuRecaudaciones,menuReporte);
			//menu de acceso rapido
			VBox box = new VBox();
			HBox mnu = new HBox();
			mnu.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color; ");
			AnchorPane.setLeftAnchor(mnu, 0d);
			AnchorPane.setRightAnchor(mnu, 0d);
			AnchorPane.setTopAnchor(mnu, 0d);
			Button botonRecaudacion = new Button("Recaudaci�n");
			botonRecaudacion.setStyle("-fx-graphic: url('/recaudacion.png');-fx-cursor: hand;-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color; ");
			abrirModalBoton(botonRecaudacion,canvas,"/recaudaciones/FrmRecaudaciones.fxml","Recaudaciones",imagen,"recaudacionesBoton");
			
			Button botonReportes = new Button("Reportes");
			botonReportes.setStyle("-fx-graphic: url('/reportes.png');-fx-cursor: hand;-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color; ");
			abrirModalBoton(botonReportes,canvas,"/reportes/FrmRecaudaciones.fxml","Reportes recaudaciones",imagen,"reportesBoton");
			
			mnu.getChildren().addAll(botonRecaudacion,botonReportes);
			
			box.getChildren().addAll(menu,mnu,canvas);
			
			//box.getChildren().add(menu);
			
			AnchorPane.setBottomAnchor(box, -35.0);
			AnchorPane.setLeftAnchor(box, 0d);
			AnchorPane.setRightAnchor(box, 0d);
			AnchorPane.setTopAnchor(box, 0d);
			
			FXMLLoader root = new FXMLLoader();
			root.setLocation(getClass().getResource("/principal/Principal.fxml"));
			AnchorPane page = (AnchorPane) root.load();
			
			page.getChildren().add(box);
			Scene scene = new Scene(page);
			
			stage.setScene(scene);
			Context.getInstance().getStagePrincipal().close();
			stage.show();
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					Optional<ButtonType> result = mostrarAlertaConfirmacion("Desea salir del sistema?",Context.getInstance().getStage());
					if(result.get() == ButtonType.OK)
						System.exit(0);
					else
						event.consume();
					//System.exit(0);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void abrirModal(MenuItem item,MDICanvas canvas,String fxml,String titulo,ImageView image,String idFxml) {
		try {
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane root = FXMLLoader.load(getClass().getResource(fxml));
						MDIWindow window = new MDIWindow(idFxml,image , titulo, root);
						window.minHeight(500.0);
						
						//window.autosize();
						canvas.addMDIWindow(window);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void abrirModalBoton(Button item,MDICanvas canvas,String fxml,String titulo,ImageView image,String idFxml) {
		try {
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane root = FXMLLoader.load(getClass().getResource(fxml));
						MDIWindow window = new MDIWindow(idFxml,image , titulo, root);
						window.minHeight(500.0);
						
						//window.autosize();
						canvas.addMDIWindow(window);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public Parent ventanaParent(String uriVista){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LaunchSystemSV.class.getResource(uriVista));
			Parent page = (Parent) loader.load();
			return page;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}
	}
	public void mostrarAlertaError(String mensaje,Stage stage)
	{
		Image img = new Image("/icon_error.png", 70, 70,false,false);
		Notifications not = Notifications.create()
				.title("Informaci�n")
				.text(mensaje)
				.graphic(new ImageView(img))
				.hideAfter(Duration.seconds(2))
				.position(Pos.BOTTOM_RIGHT);
		not.darkStyle();
		not.show();
	}

	public void mostrarAlertaInformacion(String mensaje,Stage stage)
	{
		Image img = new Image("/icon_confirm.png", 70, 70,false,false);
		Notifications not = Notifications.create()
				.title("Informaci�n")
				.text(mensaje)
				.graphic(new ImageView(img))
				.hideAfter(Duration.seconds(2))
				.position(Pos.BOTTOM_RIGHT);
		not.darkStyle();
		not.show();
	}
	public void mostrarAlertaAdvertencia(String mensaje,Stage stage)
	{
		Alert dialogoAlert = new Alert(AlertType.WARNING);
		dialogoAlert.setTitle("Advertencia");
		dialogoAlert.setContentText(mensaje);
		dialogoAlert.initOwner(stage);
		dialogoAlert.show();
	}
	public Optional<ButtonType> mostrarAlertaConfirmacion(String mensaje,Stage stage){
		Alert dialogoAlert = new Alert(AlertType.CONFIRMATION);
		dialogoAlert.setTitle("Confirmaci�n");
		dialogoAlert.setHeaderText(null);
		dialogoAlert.initStyle(StageStyle.UTILITY);
		dialogoAlert.setContentText(mensaje);
		dialogoAlert.initOwner(stage);
		return dialogoAlert.showAndWait();
	}
	public void mostrarVentanaContenedor(String uriVista,AnchorPane ap_contenedor){
		try{
			ap_contenedor.getChildren().removeAll();
			FXMLLoader loader = new FXMLLoader(LaunchSystemSV.class.getResource(uriVista));
			AnchorPane page=(AnchorPane) loader.load();

			FadeTransition ft = new FadeTransition(Duration.millis(1000));
			ft.setNode(page);
			ft.setFromValue(0.1);
			ft.setToValue(1);
			ft.setCycleCount(1);
			ft.setAutoReverse(false);
			ft.play();
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			ap_contenedor.getChildren().setAll(page);

		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public String encodeFileToBase64Binary(Image imagen) throws IOException{
		BufferedImage bImage = SwingFXUtils.fromFXImage(imagen, null);
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		ImageIO.write(bImage, "png", s);
		byte[] res  = s.toByteArray();
		s.close(); //especially if you are using a different output stream.
		return Base64.encodeBase64URLSafeString(res);
	}
	public ImageView getImageFromBase64String(String imageDataString) throws IOException {
		byte[] imgByte = Base64.decodeBase64(imageDataString);
		InputStream in = new ByteArrayInputStream(imgByte);
		BufferedImage bf = ImageIO.read(in);
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					pw.setArgb(x, y, bf.getRGB(x, y));
				}
			}
		}
		ImageView imView = new ImageView(wr);
		return imView;
	}
	public boolean validarDeCedula(String cedula) {
		boolean cedulaCorrecta = false;
		try {
			if (cedula.length() == 10) // ConstantesApp.LongitudCedula
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					// Coeficientes de validaci�n c�dula
					// El decimo digito se lo considera d�gito verificador
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9,10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}

					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					}
					else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			System.out.println("Una excepcion ocurrio en el proceso de validadcion");
			cedulaCorrecta = false;
		}

		if (!cedulaCorrecta) {
			System.out.println("La C�dula ingresada es Incorrecta");
		}
		return cedulaCorrecta;
	}
	//para convertir numeros en letras
	public String cantidadConLetra(String s) {
		StringBuilder result = new StringBuilder();
		BigDecimal totalBigDecimal = new BigDecimal(s).setScale(2, BigDecimal.ROUND_DOWN);
		long parteEntera = totalBigDecimal.toBigInteger().longValue();
		int triUnidades      = (int)((parteEntera % 1000));
		int triMiles         = (int)((parteEntera / 1000) % 1000);
		int triMillones      = (int)((parteEntera / 1000000) % 1000);
		int triMilMillones   = (int)((parteEntera / 1000000000) % 1000);

		if (parteEntera == 0) {
			result.append("Cero ");
			return result.toString();
		}

		if (triMilMillones > 0) result.append(triTexto(triMilMillones).toString() + "Mil ");
		if (triMillones > 0)    result.append(triTexto(triMillones).toString());

		if (triMilMillones == 0 && triMillones == 1) result.append("Mill�n ");
		else if (triMilMillones > 0 || triMillones > 0) result.append("Millones ");

		if (triMiles > 0)       result.append(triTexto(triMiles).toString() + "Mil ");
		if (triUnidades > 0)    result.append(triTexto(triUnidades).toString());

		return result.toString();
	}
	private StringBuilder triTexto(int n) {
		StringBuilder result = new StringBuilder();
		int centenas = n / 100;
		int decenas  = (n % 100) / 10;
		int unidades = (n % 10);

		switch (centenas) {
		case 0: break;
		case 1:
			if (decenas == 0 && unidades == 0) {
				result.append("Cien ");
				return result;
			}
			else result.append("Ciento ");
			break;
		case 2: result.append("Doscientos "); break;
		case 3: result.append("Trescientos "); break;
		case 4: result.append("Cuatrocientos "); break;
		case 5: result.append("Quinientos "); break;
		case 6: result.append("Seiscientos "); break;
		case 7: result.append("Setecientos "); break;
		case 8: result.append("Ochocientos "); break;
		case 9: result.append("Novecientos "); break;
		}

		switch (decenas) {
		case 0: break;
		case 1:
			if (unidades == 0) { result.append("Diez "); return result; }
			else if (unidades == 1) { result.append("Once "); return result; }
			else if (unidades == 2) { result.append("Doce "); return result; }
			else if (unidades == 3) { result.append("Trece "); return result; }
			else if (unidades == 4) { result.append("Catorce "); return result; }
			else if (unidades == 5) { result.append("Quince "); return result; }
			else result.append("Dieci");
			break;
		case 2:
			if (unidades == 0) { result.append("Veinte "); return result; }
			else result.append("Veinti");
			break;
		case 3: result.append("Treinta "); break;
		case 4: result.append("Cuarenta "); break;
		case 5: result.append("Cincuenta "); break;
		case 6: result.append("Sesenta "); break;
		case 7: result.append("Setenta "); break;
		case 8: result.append("Ochenta "); break;
		case 9: result.append("Noventa "); break;
		}

		if (decenas > 2 && unidades > 0)
			result.append("y ");

		switch (unidades) {
		case 0: break;
		case 1: result.append("Un "); break;
		case 2: result.append("Dos "); break;
		case 3: result.append("Tres "); break;
		case 4: result.append("Cuatro "); break;
		case 5: result.append("Cinco "); break;
		case 6: result.append("Seis "); break;
		case 7: result.append("Siete "); break;
		case 8: result.append("Ocho "); break;
		case 9: result.append("Nueve "); break;
		}

		return result;
	}
	public static boolean validarEmail(String email) {
		try{
			// Compiles the given regular expression into a pattern.
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			// Match the given input against this pattern
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	//la respuesta es en kilometros
	public static double distanciaCoordenadas(double lat1, double lng1, double lat2, double lng2) {  
		//double radioTierra = 3958.75;//en millas  
		double radioTierra = 6371;//en kil�metros  
		double dLat = Math.toRadians(lat2 - lat1);  
		double dLng = Math.toRadians(lng2 - lng1);  
		double sindLat = Math.sin(dLat / 2);  
		double sindLng = Math.sin(dLng / 2);  
		double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
		* Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
		double distancia = radioTierra * va2;  
		
		double distanciaMP3 = distancia * 1000;
		return distanciaMP3;  
	}  
	
	public static BufferedImage createImageFromBytes(byte[] imageData) {
		ByteArrayInputStream bite = new ByteArrayInputStream(imageData);
		try {
			return ImageIO.read(bite);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

