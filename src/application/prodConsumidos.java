package application;

public class prodConsumidos {
	private String det_prod;
	private int cantidad;
	private int precio;
	
	
	public prodConsumidos(String det_prod, int cantidad, int precio) {
		super();
		this.det_prod = det_prod;
		this.cantidad = cantidad;
		this.precio = precio;
	}


	public String getDet_prod() {
		return det_prod;
	}


	public int getCantidad() {
		return cantidad;
	}


	public int getPrecio() {
		return precio;
	}
	
	
	
	
}
