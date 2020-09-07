package util;


import javafx.stage.Stage;
import modelo.DetalleFactura;
import modelo.Empleado;
import modelo.Servicio;
import modelo.Usuario;

public class Context {
	private final static Context instance = new Context();


	private Stage stage;
	private Stage stageModal;
	private Stage stageModalSecundario;
	private Stage stagePrincipal;
	
	private Empleado empleado;
	private Usuario usuario;
	private Servicio servicio;
	private DetalleFactura detalleFactura;
	
	public static Context getInstance() {
		return instance;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Stage getStageModal() {
		return stageModal;
	}
	public void setStageModal(Stage stageModal) {
		this.stageModal = stageModal;
	}
	public Stage getStagePrincipal() {
		return stagePrincipal;
	}
	public void setStagePrincipal(Stage stagePrincipal) {
		this.stagePrincipal = stagePrincipal;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public DetalleFactura getDetalleFactura() {
		return detalleFactura;
	}
	public void setDetalleFactura(DetalleFactura detalleFactura) {
		this.detalleFactura = detalleFactura;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Stage getStageModalSecundario() {
		return stageModalSecundario;
	}
	public void setStageModalSecundario(Stage stageModalSecundario) {
		this.stageModalSecundario = stageModalSecundario;
	}
	
}