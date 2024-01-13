package application;

public class menuProductos {
	private String producto;
	private int precio;
	private int id_producto;
	private String proveedor;
	
	public menuProductos(String producto, int precio, int id_producto) {
		super();
		this.producto = producto;
		this.precio = precio;
		this.id_producto= id_producto;
	}
	
	public menuProductos(int id_producto, String producto, String proveedor, int precio) {
		super();
		this.producto = producto;
		this.precio = precio;
		this.id_producto= id_producto;
		this.proveedor = proveedor;
	}
	
	public String getProducto() {
		return producto;
	}
	
	public int getPrecio() {
		return precio;
	}

	public int getId_producto() {
		return id_producto;
	}

	public String getProveedor() {
		return proveedor;
	}
	
	
	
}
