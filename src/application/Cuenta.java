package application;

public class Cuenta {
	private int id_cuenta;
	private int id_mesa;
	private String fecha_inicio;
	private String fecha_fin;
	private String Cliente;
	
	public Cuenta(int id_cuenta, int id_mesa,String Cliente, String fecha_inicio, String fecha_fin) {
		super();
		this.id_cuenta = id_cuenta;
		this.id_mesa = id_mesa;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.Cliente = Cliente;
	}
	
	
	public int getId_cuenta() {
		return id_cuenta;
	}
	public int getId_mesa() {
		return id_mesa;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public String getFecha_fin() {
		return fecha_fin;
	}

	public String getCliente() {
		return Cliente;
	}
	
}
