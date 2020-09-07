package controlador;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import modelo.Cliente;
import modelo.ClienteDAO;
import modelo.DetalleFactura;
import modelo.Empresa;
import modelo.EmpresaDAO;
import modelo.Factura;
import modelo.FacturaDAO;
import util.Context;
import util.ControllerHelper;
import util.PrintReport;

public class RecRecaudacionesC {
	Tooltip toolTip;
	@FXML private TableView<DetalleFactura> tvDatos;
	@FXML private Button btnNuevoCliente;
	@FXML private Button btnNuevo;
	@FXML private TextField txtIva;
	@FXML private TextField txtSubtotal;
	@FXML private Button btnAgregar;
	@FXML private DatePicker dtpFecha;
	@FXML private TextField txtCedula;
	@FXML private TextField txtTotal;
	@FXML private TextField txtNombres;
	@FXML private Label lblIva;
	@FXML private Button btnGrabar;
	@FXML private TextField txtControl;
	@FXML private Button btnQuitar;
	
	ControllerHelper helper = new ControllerHelper();
	Integer iva = 0;
	EmpresaDAO empresaDAO = new EmpresaDAO();
	ClienteDAO clienteDAO = new ClienteDAO();
	FacturaDAO facturaDAO = new FacturaDAO();
	
	//datos de la factura
	Cliente cliente;
	Factura factura;
	
	public void initialize() {
		try {
			toolTip = new Tooltip("Grabar factura");
			btnGrabar.setStyle("-fx-graphic: url('/grabar.png');-fx-cursor: hand;");
			btnGrabar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Nuevo cliente");
			btnNuevoCliente.setStyle("-fx-graphic: url('/agg_usuario.png');-fx-cursor: hand;");
			btnNuevoCliente.setTooltip(toolTip);
			
			toolTip = new Tooltip("Agregar servicio");
			btnAgregar.setStyle("-fx-graphic: url('/agregar.png');-fx-cursor: hand;");
			btnAgregar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Quitar servicio");
			btnQuitar.setStyle("-fx-graphic: url('/quitar.png');-fx-cursor: hand;");
			btnQuitar.setTooltip(toolTip);
			
			toolTip = new Tooltip("Nueva factura");
			btnNuevo.setStyle("-fx-graphic: url('/nuevo.png');-fx-cursor: hand;");
			btnNuevo.setTooltip(toolTip);
			
			List<Empresa> listaEmpresa = empresaDAO.getEmpresa();
			if(listaEmpresa.size() > 0) {
				if(listaEmpresa.get(0).getIva() != null) {
					lblIva.setText("Iva " + listaEmpresa.get(0).getIva() + "%:");
					iva = listaEmpresa.get(0).getIva();
				}else {
					lblIva.setText("Iva 12%:");
					iva = 12;
				}
			}else {
				lblIva.setText("Iva 12%:");
				iva = 12;
			}
			txtNombres.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					String cadena = txtNombres.getText().toUpperCase();
					txtNombres.setText(cadena);
				}
			});
			txtIva.setEditable(false);
			txtControl.setEditable(false);
			txtSubtotal.setEditable(false);
			txtTotal.setEditable(false);
			
			txtCedula.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				@Override 
				public void handle(KeyEvent ke) { 
					if (ke.getCode().equals(KeyCode.ENTER)) { 
						buscarClienteCedula(txtCedula.getText());
					} 
				} 
			}); 
			//auto completa
			Integer i = 0;
			List<Cliente> lista = clienteDAO.getClientes();
			String[] palabras = new String[lista.size() + 1];
			
			for(Cliente c : lista) {
				palabras[i] = c.getCedulaRuc() + ";" + c.getApellidos() + " " + c.getNombres();
				i ++;
			}
			TextFields.bindAutoCompletion(txtNombres,palabras);
			nuevo();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}		
	}
	
	private void buscarClienteCedula(String cedula) {
		try {
			List<Cliente> listaCliente = clienteDAO.getClienteCedula(cedula);
			if(listaCliente.size() > 0) {
				cliente = listaCliente.get(0);
				txtCedula.setText(listaCliente.get(0).getCedulaRuc());
				txtNombres.setText(listaCliente.get(0).getNombres() + " " + listaCliente.get(0).getApellidos());
			}else {
				cliente = null;
				helper.mostrarAlertaAdvertencia("Cliente no existe en la base de datos", Context.getInstance().getStage());
				txtNombres.setText("");
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void llenarDatos() {
		try {
			tvDatos.getColumns().clear();
			tvDatos.getItems().clear();
			ObservableList<DetalleFactura> datos = FXCollections.observableArrayList();
			
			//llenar los datos en la tabla
			TableColumn<DetalleFactura, String> cantidadColum = new TableColumn<>("Cantidad");
			cantidadColum.setMinWidth(10);
			cantidadColum.setPrefWidth(90);
			cantidadColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetalleFactura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<DetalleFactura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCantidad()));
				}
			});
			
			TableColumn<DetalleFactura, String> servicioColum = new TableColumn<>("Servicio");
			servicioColum.setMinWidth(10);
			servicioColum.setPrefWidth(200);
			servicioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetalleFactura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<DetalleFactura, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getServicio().getServicio());
				}
			});
			
			TableColumn<DetalleFactura, String> unitarioColum = new TableColumn<>("P. Unitario");
			unitarioColum.setMinWidth(10);
			unitarioColum.setPrefWidth(90);
			unitarioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetalleFactura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<DetalleFactura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(df.format(param.getValue().getPrecioServicio()).replace(",", ".")));
				}
			});
			TableColumn<DetalleFactura, String> totalColum = new TableColumn<>("P. Total");
			totalColum.setMinWidth(10);
			totalColum.setPrefWidth(90);
			totalColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetalleFactura,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<DetalleFactura, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(df.format(param.getValue().getTotal()).replace(",", ".")));
				}
			});
			tvDatos.getColumns().addAll(cantidadColum, servicioColum,unitarioColum,totalColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void limpiar() {
		llenarDatos();
		cliente = null;
		factura = null;
		txtCedula.setText("");
		txtIva.setText("0.00");
		txtNombres.setText("");
		txtSubtotal.setText("0.00");
		txtTotal.setText("0.00");
		dtpFecha.setValue(LocalDate.now());
		dtpFecha.setDisable(true);
		txtControl.setVisible(false);
	}
	
	public void buscarCliente() {
		try {
			helper.abrirPantallaModal("/recaudaciones/FrmClientes.fxml","Registro de clientes", Context.getInstance().getStage());
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void agregar() {
		try {
			Context.getInstance().setDetalleFactura(null);
			helper.abrirPantallaModal("/recaudaciones/FrmServicios.fxml","Agregar servicios", Context.getInstance().getStage());
			if(Context.getInstance().getDetalleFactura() != null) {
				DetalleFactura detalle = new DetalleFactura();
				detalle = Context.getInstance().getDetalleFactura();
				agregarServicios(detalle);
			}
			Context.getInstance().setDetalleFactura(null);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void agregarServicios(DetalleFactura detalle) {
		try {
			tvDatos.getItems().add(detalle);
			tvDatos.refresh();
			sumarTotales();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	DecimalFormat df = new DecimalFormat("0.00");
	private void sumarTotales() {
		try {
			Double subtotal = 0.0;
			for(DetalleFactura detalle : tvDatos.getItems()) subtotal = subtotal + detalle.getTotal();
			Double ivaPorc =  ((double)iva / 100);
			Double totalIva = subtotal * ivaPorc;
			Double total = subtotal - totalIva;
			
			txtSubtotal.setText(String.valueOf(df.format(total).replace(",", ".")));
			txtIva.setText(String.valueOf(df.format(totalIva).replace(",", ".")));
			txtTotal.setText(String.valueOf(df.format(subtotal).replace(",", ".")));
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void quitar() {
		try {
			if(tvDatos.getSelectionModel().getSelectedItem() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un servicio a quitar", Context.getInstance().getStage());
				return;
			}
			DetalleFactura detalle = new DetalleFactura();
			detalle = tvDatos.getSelectionModel().getSelectedItem();
			tvDatos.getItems().remove(detalle);
			tvDatos.refresh();
			sumarTotales();
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
				
				if (factura != null) {
					//clienteDAO.getEntityManager().merge(factura);
				} else {
					Integer numeroControl = 0;
					numeroControl = numeroControl();
					clienteDAO.getEntityManager().getTransaction().begin();
					factura = new Factura();
					factura.setIdFactura(null);
					factura.setCliente(cliente);
					factura.setEstado("A");
					Date date = Date.from(dtpFecha.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
					factura.setFechaEmision(date);
					factura.setIvaPorcentaje(iva);
					factura.setIva(Double.parseDouble(txtIva.getText()));
					factura.setTotalPagar(Double.parseDouble(txtTotal.getText()));
					factura.setUsuarioId(Context.getInstance().getUsuario().getIdUsuario());
					factura.setNumFactura(numeroControl);
					List<DetalleFactura> detalle = new ArrayList<DetalleFactura>();
					for(DetalleFactura det : tvDatos.getItems()) {
						det.setFactura(factura);
						detalle.add(det);
					}
					factura.setDetalleFacturas(detalle);
					
					clienteDAO.getEntityManager().merge(factura);
					helper.mostrarAlertaInformacion("Datos Grabados con Exito", Context.getInstance().getStage());
					clienteDAO.getEntityManager().getTransaction().commit();
					
					imprimirFactura(numeroControl);
					nuevo();
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
		}
	}
	private void imprimirFactura(Integer numeroControl) {
		try {
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("NO_CONTROL", numeroControl);
			param.put("USUARIO", Context.getInstance().getUsuario().getEmpleado().getNombres() + " " + Context.getInstance().getUsuario().getEmpleado().getApellidos());
			param.put("IVA", "IVA " + iva + "% $");
			pr.crearReporte("/recursos/reportes/rptFactura.jasper", facturaDAO, param);
			pr.imprimirFactura("Factura");
		}catch(Exception ex) {
			
		}
	}
	private boolean validarDatos() {
		try {
			boolean bandera = false;
			if(txtCedula.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe registrar cédula del cliente", Context.getInstance().getStage());
				return false;
			}
			if(cliente == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar un cliente", Context.getInstance().getStage());
				return false;
			}
			if(dtpFecha.getValue() == null) {
				helper.mostrarAlertaAdvertencia("Debe seleccionar fecha de la factura", Context.getInstance().getStage());
				return false;
			}
			if(tvDatos.getItems().size() == 0) {
				helper.mostrarAlertaAdvertencia("No existe detalle de factura", Context.getInstance().getStage());
				return false;
			}			
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
	public void nuevo() {
		try {
			numeroControl();
			limpiar();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private Integer numeroControl() {
		try {
			Integer numero = 0;
			List<Factura> listado = new ArrayList<Factura>();
			listado = facturaDAO.getListaFacturas();
			if(listado.size() > 0) {
				numero = listado.get(0).getNumFactura() + 1;
				//txtControl.setText(String.valueOf(listado.get(0).getNumFactura() + 1));
			}else {
				List<Empresa> listaEmpresa = empresaDAO.getEmpresa();
				if(listaEmpresa.size() > 0) {
					if(listaEmpresa.get(0).getInicioControl() != null)
						numero = listado.get(0).getNumFactura();
						//txtControl.setText(String.valueOf(listaEmpresa.get(0).getInicioControl()));
					else
						numero = 1;
						//txtControl.setText("1");
				}else 
					numero = 1;
					//txtControl.setText("1");
			}
			return numero;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}
}
