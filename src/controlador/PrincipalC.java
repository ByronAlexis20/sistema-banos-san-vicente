package controlador;

import java.io.IOException;

import br.com.supremeforever.suprememdiwindow.MDICanvas;
import br.com.supremeforever.suprememdiwindow.MDIWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Context;

public class PrincipalC {

	
	public void initialize() {
		try {
			MDICanvas canvas = new MDICanvas();
			canvas.setPrefSize(5000, 5000);
			Button b = new Button("+");
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane root = FXMLLoader.load(getClass().getResource("/registros/FrmServicios.fxml"));
						MDIWindow window = new MDIWindow("1", null, "titulo", root);
						canvas.addMDIWindow(window);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			VBox box = new VBox(b,canvas);
			AnchorPane.setBottomAnchor(box, 0d);
			AnchorPane.setLeftAnchor(box, 0d);
			AnchorPane.setRightAnchor(box, 0d);
			AnchorPane.setTopAnchor(box, 0d);
			
			AnchorPane pane = new AnchorPane(box);
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			stage.setScene(scene);
			Context.getInstance().getStagePrincipal().close();
			stage.show();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
