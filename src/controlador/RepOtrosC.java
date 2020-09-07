package controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import modelo.EmpresaDAO;
import modelo.Usuario;
import modelo.UsuarioDAO;
import util.Constantes;
import util.PrintReport;

public class RepOtrosC {
	@FXML private Button btnNominaClientes;
	@FXML private Button btnServiciosDispoibles;
	@FXML private Button btnPreciosServicios;
	@FXML private Button btnNominaEmpleados;
	EmpresaDAO empresaDAO = new EmpresaDAO();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	String administrador = "";
	public void initialize() {
		try {
			btnNominaClientes.setStyle("-fx-cursor: hand;");
			btnServiciosDispoibles.setStyle("-fx-cursor: hand;");
			btnPreciosServicios.setStyle("-fx-cursor: hand;");
			btnNominaEmpleados.setStyle("-fx-cursor: hand;");
			List<Usuario> listaUsuario = usuarioDAO.getUsuariosPorRol(Constantes.ID_ADMINISTRADOR);
			if(listaUsuario.size() > 0) {
				administrador = listaUsuario.get(0).getEmpleado().getNombres() + " " + listaUsuario.get(0).getEmpleado().getApellidos();
			}
		}catch(Exception ex) {
			
		}
	}
	public void preciosServicios() {
		try {
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TITULO_REPORTE", "PRECIO DE LOS SERVICIOS DE ACUERDO A SU CATEGORÍA");
			param.put("ADMINISTRADOR", administrador);
			//pr.crearReporte("/recursos/reportes/rptOtrosPrecio.jasper", empresaDAO, param);
			pr.crearReporte("/recursos/reportes/rptOtrosPrecio.jasper", empresaDAO, param);
			pr.showReport("REPORTE DE PRECIOS");
		}catch(Exception ex) {
			
		}
	}
	public void nominaEmpleados() {
		try {
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ADMINISTRADOR", administrador);
			pr.crearReporte("/recursos/reportes/rptOtrosNominaEmpleados.jasper", empresaDAO, param);
			pr.showReport("NÓMINA DE EMPLEADOS");
		}catch(Exception ex) {
			
		}
	}
	public void nominaClientes() {
		try {
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ADMINISTRADOR", administrador);
			pr.crearReporte("/recursos/reportes/rptOtrosNominaClientes.jasper", empresaDAO, param);
			pr.showReport("NÓMINA DE CLIENTES");
		}catch(Exception ex) {
			
		}
	}

	public void serviciosDisponibles() {
		try {
			PrintReport pr = new PrintReport();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ADMINISTRADOR", administrador);
			pr.crearReporte("/recursos/reportes/rptOtrosListaServicios.jasper", empresaDAO, param);
			pr.showReport("LISTADO DE SERVICIOS");
		}catch(Exception ex) {
			
		}
	}
}
