package controlador;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import modelo.EmpresaDAO;
import modelo.Usuario;
import modelo.UsuarioDAO;
import util.Constantes;
import util.Context;
import util.PrintReport;

public class RepRecaudacionesC {
	EmpresaDAO empresaDAO = new EmpresaDAO();
	SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
	String administrador = "";
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	public void initialize() {
		try {
			List<Usuario> listaUsuario = usuarioDAO.getUsuariosPorRol(Constantes.ID_ADMINISTRADOR);
			if(listaUsuario.size() > 0) {
				administrador = listaUsuario.get(0).getEmpleado().getNombres() + " " + listaUsuario.get(0).getEmpleado().getApellidos();
			}
			rbDiarioAnulado.setSelected(true);rbDiarioCategoria.setSelected(true);rbDiarioCliente.setSelected(true);rbDiarioImpuesto.setSelected(true);
			rbDiarioNumerico.setSelected(true);rbDiarioServicio.setSelected(true);rbDiarioUsuario.setSelected(true);btnImprimirRangoImpuesto.setDisable(true);
			btnVisualizarRangoAnulado.setDisable(true);btnVisualizarRangoCategoria.setDisable(true);btnVisualizarRangoCliente.setDisable(true);
			btnVisualizarRangoNumerico.setDisable(true);btnVisualizarRangoServicio.setDisable(true);btnVisualizarRangoUsuario.setDisable(true);
			btnImprimirRangoImpuesto.setStyle("-fx-cursor: hand;");btnVisualizarDiarioAnulado.setStyle("-fx-cursor: hand;");
			btnVisualizarDiarioCategora.setStyle("-fx-cursor: hand;");btnVisualizarDiarioCliente.setStyle("-fx-cursor: hand;");
			btnVisualizarDiarioImpuesto.setStyle("-fx-cursor: hand;");btnVisualizarDiarioNumerico.setStyle("-fx-cursor: hand;");
			btnVisualizarDiarioServicio.setStyle("-fx-cursor: hand;");btnVisualizarDiarioUsuario.setStyle("-fx-cursor: hand;");
			btnVisualizarRangoAnulado.setStyle("-fx-cursor: hand;");btnVisualizarRangoCategoria.setStyle("-fx-cursor: hand;");
			btnVisualizarRangoCliente.setStyle("-fx-cursor: hand;");btnVisualizarRangoNumerico.setStyle("-fx-cursor: hand;");
			btnVisualizarRangoServicio.setStyle("-fx-cursor: hand;");btnVisualizarRangoUsuario.setStyle("-fx-cursor: hand;");
			
			dtpDiarioServicio.setStyle("-fx-cursor: hand;");dtpInicioServicio.setStyle("-fx-cursor: hand;");dtpFinServicio.setStyle("-fx-cursor: hand;");
			dtpDiarioCategoria.setStyle("-fx-cursor: hand;");dtpInicioRangoCategoria.setStyle("-fx-cursor: hand;");dtpFinRangoCategoria.setStyle("-fx-cursor: hand;");
			dtpDiarioUsuario.setStyle("-fx-cursor: hand;");dtpInicioRangoUsuario.setStyle("-fx-cursor: hand;");dtpFinRangoUsuario.setStyle("-fx-cursor: hand;");
			dtpDiarioNumerico.setStyle("-fx-cursor: hand;");dtpInicioRangoNumerico.setStyle("-fx-cursor: hand;");dtpFinRangoNumerico.setStyle("-fx-cursor: hand;");
			dtpDiarioCliente.setStyle("-fx-cursor: hand;");dtpInicioRangoCliente.setStyle("-fx-cursor: hand;");dtpFinRangoCliente.setStyle("-fx-cursor: hand;");
			dtpDiarioAnulado.setStyle("-fx-cursor: hand;");dtpInicioRangoAnulado.setStyle("-fx-cursor: hand;");dtpFinRangoAnulado.setStyle("-fx-cursor: hand;");
			dtpDiarioImpuesto.setStyle("-fx-cursor: hand;");dtpInicioRangoImpuesto.setStyle("-fx-cursor: hand;");dtpFinRangoImpuesto.setStyle("-fx-cursor: hand;");
			
			
			dtpDiarioServicio.setValue(LocalDate.now());dtpInicioServicio.setValue(LocalDate.now());dtpFinServicio.setValue(LocalDate.now());
			dtpDiarioCategoria.setValue(LocalDate.now());dtpInicioRangoCategoria.setValue(LocalDate.now());dtpFinRangoCategoria.setValue(LocalDate.now());
			dtpDiarioUsuario.setValue(LocalDate.now());dtpInicioRangoUsuario.setValue(LocalDate.now());dtpFinRangoUsuario.setValue(LocalDate.now());
			dtpDiarioNumerico.setValue(LocalDate.now());dtpInicioRangoNumerico.setValue(LocalDate.now());dtpFinRangoNumerico.setValue(LocalDate.now());
			dtpDiarioCliente.setValue(LocalDate.now());dtpInicioRangoCliente.setValue(LocalDate.now());dtpFinRangoCliente.setValue(LocalDate.now());
			dtpDiarioAnulado.setValue(LocalDate.now());dtpInicioRangoAnulado.setValue(LocalDate.now());dtpFinRangoAnulado.setValue(LocalDate.now());
			dtpDiarioImpuesto.setValue(LocalDate.now());dtpInicioRangoImpuesto.setValue(LocalDate.now());dtpFinRangoImpuesto.setValue(LocalDate.now());
			
			
			rbDiarioServicio.setStyle("-fx-cursor: hand;");rbRangoServicio.setStyle("-fx-cursor: hand;");
			rbDiarioCategoria.setStyle("-fx-cursor: hand;");rbRangoCategoria.setStyle("-fx-cursor: hand;");
			rbDiarioUsuario.setStyle("-fx-cursor: hand;");rbRangoUsuario.setStyle("-fx-cursor: hand;");
			rbDiarioNumerico.setStyle("-fx-cursor: hand;");rbRangoNumerico.setStyle("-fx-cursor: hand;");
			rbDiarioCliente.setStyle("-fx-cursor: hand;");rbRangoCliente.setStyle("-fx-cursor: hand;");
			rbDiarioAnulado.setStyle("-fx-cursor: hand;");rbRangoAnulado.setStyle("-fx-cursor: hand;");
			rbDiarioImpuesto.setStyle("-fx-cursor: hand;");rbRangoImpuesto.setStyle("-fx-cursor: hand;");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	//por servicio *******************************************************************************************************************************
	@FXML private RadioButton rbRangoServicio;
	@FXML private DatePicker dtpDiarioServicio;
	@FXML private Button btnVisualizarRangoServicio;
	@FXML private Button btnVisualizarDiarioServicio;
	@FXML private DatePicker dtpFinServicio;
	@FXML private RadioButton rbDiarioServicio;
	@FXML private DatePicker dtpInicioServicio;
	public void seleccionDiarioServicio() {
		try {
			rbRangoServicio.setSelected(false);
			rbDiarioServicio.setSelected(true);
			btnVisualizarDiarioServicio.setDisable(false);
			btnVisualizarRangoServicio.setDisable(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionRangoServicio() {
		try {
			rbRangoServicio.setSelected(true);
			rbDiarioServicio.setSelected(false);
			btnVisualizarDiarioServicio.setDisable(true);
			btnVisualizarRangoServicio.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirDiarioServicio() {
		try {
			Date fechaDiario = Date.from(dtpDiarioServicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE VENTA DE SERVICIOS DEL " + formateador.format(fechaDiario));
			param.put("FECHA_INICIO", fechaDiario);
			param.put("FECHA_FIN", fechaDiario);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionServicio.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionServicioUsuario.jasper", empresaDAO, param);
			}
			
			pr.showReport("REPORTE DE RECAUDACIONES DIARIAS");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirRangoServicio() {
		try {
			Date fechaInicio = Date.from(dtpInicioServicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(dtpFinServicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE VENTA DE SERVICIOS DESDE " + formateador.format(fechaInicio) + " HASTA " + formateador.format(fechaFin));
			param.put("FECHA_INICIO", fechaInicio);
			param.put("FECHA_FIN", fechaFin);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionServicio.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionServicioUsuario.jasper", empresaDAO, param);
			}
			pr.showReport("REPORTE DE RECAUDACIONES EN RANGO DE FECHA");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	//por servicio y categorizacion*******************************************************************************************************************************
	@FXML private DatePicker dtpDiarioCategoria;
	@FXML private RadioButton rbDiarioCategoria;
	@FXML private Button btnVisualizarRangoCategoria;
	@FXML private DatePicker dtpInicioRangoCategoria;
	@FXML private DatePicker dtpFinRangoCategoria;
	@FXML private RadioButton rbRangoCategoria;
	@FXML private Button btnVisualizarDiarioCategora;
	public void seleccionDiarioCategoria() {
		try {
			rbDiarioCategoria.setSelected(true);
			rbRangoCategoria.setSelected(false);
			btnVisualizarDiarioCategora.setDisable(false);
			btnVisualizarRangoCategoria.setDisable(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionRangoCategoria() {
		try {
			rbDiarioCategoria.setSelected(false);
			rbRangoCategoria.setSelected(true);
			btnVisualizarDiarioCategora.setDisable(true);
			btnVisualizarRangoCategoria.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirDiarioCategoria() {
		try {
			Date fechaDiario = Date.from(dtpDiarioCategoria.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE VENTA DE SERVICIOS Y CATEGORÍA DEL " + formateador.format(fechaDiario));
			param.put("FECHA_INICIO", fechaDiario);
			param.put("FECHA_FIN", fechaDiario);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionCategoria.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionCategoriaUsuario.jasper", empresaDAO, param);
			}
			
			pr.showReport("REPORTE DE RECAUDACIONES DIARIAS");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirRangoCategoria() {
		try {
			Date fechaInicio = Date.from(dtpInicioRangoCategoria.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(dtpFinRangoCategoria.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE VENTA DE SERVICIOS Y CATEGORÍA DESDE " + formateador.format(fechaInicio) + " HASTA " + formateador.format(fechaFin));
			param.put("FECHA_INICIO", fechaInicio);
			param.put("FECHA_FIN", fechaFin);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionCategoria.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionCategoriaUsuario.jasper", empresaDAO, param);
			}
			pr.showReport("REPORTE DE RECAUDACIONES EN RANGO DE FECHA");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	//por usuarios generales*******************************************************************************************************************************
	@FXML private RadioButton rbRangoUsuario;
	@FXML private DatePicker dtpDiarioUsuario;
	@FXML private RadioButton rbDiarioUsuario;
	@FXML private DatePicker dtpFinRangoUsuario;
	@FXML private Button btnVisualizarDiarioUsuario;
	@FXML private DatePicker dtpInicioRangoUsuario;
	@FXML private Button btnVisualizarRangoUsuario;
	public void seleccionDiarioUsuario() {
		try {
			rbDiarioUsuario.setSelected(true);
			rbRangoUsuario.setSelected(false);
			btnVisualizarDiarioUsuario.setDisable(false);
			btnVisualizarRangoUsuario.setDisable(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionRangoUsuario() {
		try {
			rbDiarioUsuario.setSelected(false);
			rbRangoUsuario.setSelected(true);
			btnVisualizarDiarioUsuario.setDisable(true);
			btnVisualizarRangoUsuario.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirDiarioUsuario() {
		try {
			Date fechaDiario = Date.from(dtpDiarioUsuario.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE RECAUDACIONES DE USUARIO ORGANIZADO POR NÚMERO DE CONTROL DEL " + formateador.format(fechaDiario));
			param.put("FECHA_INICIO", fechaDiario);
			param.put("FECHA_FIN", fechaDiario);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionUsuario.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionUsuarioUsuario.jasper", empresaDAO, param);
			}
			
			pr.showReport("REPORTE DE RECAUDACIONES DIARIAS");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirRangoUsuario() {
		try {
			Date fechaInicio = Date.from(dtpInicioRangoUsuario.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(dtpFinRangoUsuario.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE RECAUDACIONES DE USUARIO ORGANIZADO POR NÚMERO DE CONTROL DESDE " + formateador.format(fechaInicio) + " HASTA " + formateador.format(fechaFin));
			param.put("FECHA_INICIO", fechaInicio);
			param.put("FECHA_FIN", fechaFin);
			param.put("ADMINISTRADOR", administrador);
			param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionUsuario.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionUsuarioUsuario.jasper", empresaDAO, param);
			}
			pr.showReport("REPORTE DE RECAUDACIONES EN RANGO DE FECHA");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	//Numerico por servicio y categorizacion*******************************************************************************************************************************
	@FXML private RadioButton rbDiarioNumerico;
	@FXML private DatePicker dtpFinRangoNumerico;
	@FXML private DatePicker dtpDiarioNumerico;
	@FXML private RadioButton rbRangoNumerico;
	@FXML private Button btnVisualizarRangoNumerico;
	@FXML private Button btnVisualizarDiarioNumerico;
	@FXML private DatePicker dtpInicioRangoNumerico;
	public void seleccionDiarioNumerico() {
		try {
			rbDiarioNumerico.setSelected(true);
			rbRangoNumerico.setSelected(false);
			btnVisualizarDiarioNumerico.setDisable(false);
			btnVisualizarRangoNumerico.setDisable(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionRangoNumerico() {
		try {
			rbDiarioNumerico.setSelected(false);
			rbRangoNumerico.setSelected(true);
			btnVisualizarDiarioNumerico.setDisable(true);
			btnVisualizarRangoNumerico.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirDiarioNumerico() {
		try {
			Date fechaDiario = Date.from(dtpDiarioNumerico.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE SERVICIOS PRESTADOS ORGANIZADOS POR CATEGORÍA DEL " + formateador.format(fechaDiario));
			param.put("FECHA_INICIO", fechaDiario);
			param.put("FECHA_FIN", fechaDiario);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionNumerico.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionNumericoUsuario.jasper", empresaDAO, param);
			}
			
			pr.showReport("REPORTE DE RECAUDACIONES DIARIAS");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirRangoNumerico() {
		try {
			Date fechaInicio = Date.from(dtpInicioRangoNumerico.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(dtpFinRangoNumerico.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE SERVICIOS PRESTADOS ORGANIZADOS POR CATEGORÍA DESDE " + formateador.format(fechaInicio) + " HASTA " + formateador.format(fechaFin));
			param.put("FECHA_INICIO", fechaInicio);
			param.put("FECHA_FIN", fechaFin);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionNumerico.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionNumericoUsuario.jasper", empresaDAO, param);
			}
			pr.showReport("REPORTE DE RECAUDACIONES EN RANGO DE FECHA");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	//por cliente*******************************************************************************************************************************
	@FXML private DatePicker dtpInicioRangoCliente;
	@FXML private DatePicker dtpFinRangoCliente;
	@FXML private DatePicker dtpDiarioCliente;
	@FXML private Button btnVisualizarDiarioCliente;
	@FXML private Button btnVisualizarRangoCliente;
	@FXML private RadioButton rbDiarioCliente;
	@FXML private RadioButton rbRangoCliente;
	public void seleccionDiarioCliente() {
		try {
			rbDiarioCliente.setSelected(true);
			rbRangoCliente.setSelected(false);
			btnVisualizarDiarioCliente.setDisable(false);
			btnVisualizarRangoCliente.setDisable(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionRangoCliente() {
		try {
			rbDiarioCliente.setSelected(false);
			rbRangoCliente.setSelected(true);
			btnVisualizarDiarioCliente.setDisable(true);
			btnVisualizarRangoCliente.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirDiarioCliente() {
		try {
			Date fechaDiario = Date.from(dtpDiarioCliente.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE RECAUDACIÓN POR CLIENTE DEL " + formateador.format(fechaDiario));
			param.put("FECHA_INICIO", fechaDiario);
			param.put("FECHA_FIN", fechaDiario);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionCliente.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionClienteUsuario.jasper", empresaDAO, param);
			}
			
			pr.showReport("REPORTE DE RECAUDACIONES DIARIAS");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirRangoCliente() {
		try {
			Date fechaInicio = Date.from(dtpInicioRangoCliente.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(dtpFinRangoCliente.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE RECAUDACIÓN POR CLIENTE DESDE " + formateador.format(fechaInicio) + " HASTA " + formateador.format(fechaFin));
			param.put("FECHA_INICIO", fechaInicio);
			param.put("FECHA_FIN", fechaFin);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionCliente.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionClienteUsuario.jasper", empresaDAO, param);
			}
			
			pr.showReport("REPORTE DE RECAUDACIONES EN RANGO DE FECHA");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	//Documentos anulados*******************************************************************************************************************************
	@FXML private Button btnVisualizarDiarioAnulado;
	@FXML private DatePicker dtpFinRangoAnulado;
	@FXML private Button btnVisualizarRangoAnulado;
	@FXML private DatePicker dtpInicioRangoAnulado;
	@FXML private DatePicker dtpDiarioAnulado;
	@FXML private RadioButton rbDiarioAnulado;
	@FXML private RadioButton rbRangoAnulado;
	public void seleccionDiarioAnulado() {
		try {
			rbDiarioAnulado.setSelected(true);
			rbRangoAnulado.setSelected(false);
			btnVisualizarDiarioAnulado.setDisable(false);
			btnVisualizarRangoAnulado.setDisable(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionRangoAnulado() {
		try {
			rbDiarioAnulado.setSelected(false);
			rbRangoAnulado.setSelected(true);
			btnVisualizarDiarioAnulado.setDisable(true);
			btnVisualizarRangoAnulado.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirDiarioAnulado() {
		try {
			Date fechaDiario = Date.from(dtpDiarioAnulado.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE DOCUMENTOS ANULADOS DEL " + formateador.format(fechaDiario));
			param.put("FECHA_INICIO", fechaDiario);
			param.put("FECHA_FIN", fechaDiario);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionAnulado.jasper", empresaDAO, param);
			else {
				param.put("USUARIO", Context.getInstance().getUsuario().getEmpleado().getApellidos() + " " + Context.getInstance().getUsuario().getEmpleado().getNombres());
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionAnuladoUsuario.jasper", empresaDAO, param);
			}
			pr.showReport("REPORTE DE RECAUDACIONES DIARIAS");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirRangoAnulado() {
		try {
			Date fechaInicio = Date.from(dtpInicioRangoAnulado.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(dtpFinRangoAnulado.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE DOCUMENTOS ANULADOS DESDE " + formateador.format(fechaInicio) + " HASTA " + formateador.format(fechaFin));
			param.put("FECHA_INICIO", fechaInicio);
			param.put("FECHA_FIN", fechaFin);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionAnulado.jasper", empresaDAO, param);
			else {
				param.put("USUARIO", Context.getInstance().getUsuario().getEmpleado().getApellidos() + " " + Context.getInstance().getUsuario().getEmpleado().getNombres());
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionAnuladoUsuario.jasper", empresaDAO, param);
			}
			
			pr.showReport("REPORTE DE RECAUDACIONES EN RANGO DE FECHA");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	//Impuesto recaudado*******************************************************************************************************************************
	@FXML private DatePicker dtpFinRangoImpuesto;
	@FXML private DatePicker dtpDiarioImpuesto;
	@FXML private DatePicker dtpInicioRangoImpuesto;
	@FXML private Button btnImprimirRangoImpuesto;
	@FXML private RadioButton rbDiarioImpuesto;
	@FXML private RadioButton rbRangoImpuesto;
	@FXML private Button btnVisualizarDiarioImpuesto;
	public void seleccionDiarioImpuesto() {
		try {
			rbDiarioImpuesto.setSelected(true);
			rbRangoImpuesto.setSelected(false);
			btnImprimirRangoImpuesto.setDisable(true);
			btnVisualizarDiarioImpuesto.setDisable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void seleccionRangoImpuesto() {
		try {
			rbDiarioImpuesto.setSelected(false);
			rbRangoImpuesto.setSelected(true);
			btnImprimirRangoImpuesto.setDisable(false);
			btnVisualizarDiarioImpuesto.setDisable(true);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirDiarioImpuesto() {
		try {
			Date fechaDiario = Date.from(dtpDiarioImpuesto.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE IMPUESTOS RECAUDADOS DEL " + formateador.format(fechaDiario));
			param.put("FECHA_INICIO", fechaDiario);
			param.put("FECHA_FIN", fechaDiario);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionImpuesto.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionImpuestoUsuario.jasper", empresaDAO, param);
			}
			pr.showReport("REPORTE DE RECAUDACIONES DIARIAS");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void imprimirRangoImpuesto() {
		try {
			Date fechaInicio = Date.from(dtpInicioRangoImpuesto.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(dtpFinRangoImpuesto.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "REPORTE DE IMPUESTOS RECAUDADOS DESDE " + formateador.format(fechaInicio) + " HASTA " + formateador.format(fechaFin));
			param.put("FECHA_INICIO", fechaInicio);
			param.put("FECHA_FIN", fechaFin);
			param.put("ADMINISTRADOR", administrador);
			if(Context.getInstance().getUsuario().getRol().getIdRol() == Constantes.ID_ADMINISTRADOR)
				pr.crearReporte("/recursos/reportes/rptRecaudacionImpuesto.jasper", empresaDAO, param);
			else {
				param.put("ID_USUARIO", Context.getInstance().getUsuario().getIdUsuario());
				pr.crearReporte("/recursos/reportes/rptRecaudacionImpuestoUsuario.jasper", empresaDAO, param);
			}
			pr.showReport("REPORTE DE RECAUDACIONES EN RANGO DE FECHA");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
