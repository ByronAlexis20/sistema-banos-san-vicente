package controlador;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import modelo.Factura;
import modelo.FacturaDAO;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import util.Context;
import util.ControllerHelper;
import util.PrintReport;

public class RecRecaudacionGeneradaC {
	Tooltip toolTip;
	@FXML private TableView<Factura> tvDatos;
	@FXML private Button btnImprimir;
	@FXML private Button btnAnular;
	FacturaDAO facturaDAO = new FacturaDAO();
	SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
	ControllerHelper helper = new ControllerHelper();
	DecimalFormat df = new DecimalFormat("0.00");
	
	public void initialize() {
		try {
			toolTip = new Tooltip("Reimprimir factura");
			btnImprimir.setStyle("-fx-graphic: url('/imprimir.png');-fx-cursor: hand;");
			btnImprimir.setTooltip(toolTip);
			
			toolTip = new Tooltip("Anular factura");
			btnAnular.setStyle("-fx-graphic: url('/eliminar.png');-fx-cursor: hand;");
			btnAnular.setTooltip(toolTip);
			
			llenarDatos();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	private void llenarDatos() {
		try {
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			ObservableList<Factura> datos = FXCollections.observableArrayList();
			List<Factura> listadoFacturas = facturaDAO.getListaFacturaUsuario();
			datos.addAll(listadoFacturas);
			//llenar los datos en la tabla
			TableColumn<Factura, String> controlColum = new TableColumn<>("No. Control");
			controlColum.setMinWidth(10);
			controlColum.setPrefWidth(90);
			controlColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Factura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getNumFactura()));
				}
			});
			TableColumn<Factura, String> fechaColum = new TableColumn<>("Fecha");
			fechaColum.setMinWidth(10);
			fechaColum.setPrefWidth(100);
			fechaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Factura, String> param) {
					return new SimpleObjectProperty<String>(formateador.format(param.getValue().getFechaEmision()));
				}
			});
			TableColumn<Factura, String> clienteColum = new TableColumn<>("Cliente");
			clienteColum.setMinWidth(10);
			clienteColum.setPrefWidth(200);
			clienteColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Factura, String> param) {
					String cliente = param.getValue().getCliente().getNombres() + " " + param.getValue().getCliente().getApellidos();
					return new SimpleObjectProperty<String>(cliente);
				}
			});
			TableColumn<Factura, String> totalColum = new TableColumn<>("Total");
			totalColum.setMinWidth(10);
			totalColum.setPrefWidth(100);
			totalColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Factura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(df.format(param.getValue().getTotalPagar()).replace(",", ".")));
				}
			});
			tvDatos.getColumns().addAll(controlColum, fechaColum,clienteColum,totalColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirFactura() {
		try {
			
	
			
			if(tvDatos.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar una factura", Context.getInstance().getStage());
				return;
			}
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("NO_CONTROL", tvDatos.getSelectionModel().getSelectedItem().getNumFactura());
			param.put("USUARIO", Context.getInstance().getUsuario().getEmpleado().getNombres() + " " + Context.getInstance().getUsuario().getEmpleado().getApellidos());
			param.put("IVA", "IVA " + tvDatos.getSelectionModel().getSelectedItem().getIvaPorcentaje() + "% $");
			pr.crearReporte("/recursos/reportes/rptFactura.jasper", facturaDAO, param);
			//pr.crearReporte("/recursos/reportes/rptPrueba.jasper", facturaDAO, null);
			pr.imprimirFactura("Factura");
			//pr.imprimirReporte();   
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void anularFactura() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar una factura", Context.getInstance().getStage());
				return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Seguro desea anular la factura",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				Factura facturaAnular = tvDatos.getSelectionModel().getSelectedItem();
				facturaAnular.setEstado("E");
				facturaDAO.getEntityManager().getTransaction().begin();
				facturaDAO.getEntityManager().merge(facturaAnular);
				facturaDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("La factura ha sido anulada", Context.getInstance().getStage());
				llenarDatos();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
