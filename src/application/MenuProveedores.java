package application;


public class MenuProveedores {
	private int id_proveedor;
	private String nombre;
	private String telefono;
	
	public MenuProveedores(int id_proveedor, String telefono, String nombre) {
		super();
		this.id_proveedor = id_proveedor;
		this.telefono = telefono;
		this.nombre = nombre;
	}
	
	public MenuProveedores(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public int getId_proveedor() {
		return id_proveedor;
	}
	
	@Override
    public String toString() {
        return nombre;
    }
	
	
}
