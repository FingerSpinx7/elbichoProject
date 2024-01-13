package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.menuProductos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ConnectionUtil;

public class AgregarProdController extends Form implements Initializable {
	String mesa_actual;
	String cuenta_actual;

	@FXML
    private TableColumn<menuProductos, Integer> precioCol;

    @FXML
    private TableColumn<menuProductos, String> prodCol;
    
    @FXML
    private TableView<menuProductos> tblMenu;
    
    @FXML
    private Button añadirBtn;
    
    @FXML
    private Button regresarBtn;
    
    @FXML
    private TextField txtCantidad;

    @FXML
    private Label lblmesa;
	
    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");
	
	
	
	@FXML
    void event1(ActionEvent event) {
    	Object evt = event.getSource();
    	if(evt.equals(añadirBtn)) {
    		
    		System.out.println("Añadir presionado");
    		if(!txtCantidad.getText().isEmpty()&&!(tblMenu.getSelectionModel().getSelectedItems().isEmpty())) {
    			if(esNumerico()&&esPositivo(Integer.parseInt(txtCantidad.getText()))){
		    		String precio = SelectPriceRow()+"";	    		
		    		String prod = SelectProdRow();
		    		
		    		String cantidad = txtCantidad.getText();
		    		getCuentaActual();
		    		getMesaActual();
		    		System.out.println("Todo bien");	    		
		    		System.out.println(cantidad+" "+prod +" "+precio+" en cuenta " + cuenta_actual);
		    		
		    		int opcion = JOptionPane.showConfirmDialog(null, "¿Agregar "+cantidad+" "+prod+" a mesa "+mesa_actual+" (cuenta #"+cuenta_actual+")?", "AGREGAR PRODUCTOS",  JOptionPane.YES_NO_OPTION);
					if(opcion == JOptionPane.YES_OPTION) {
						String update = "UPDATE productos_consumidos SET cantidad = cantidad + "+cantidad+" WHERE id_cuenta = "+cuenta_actual+" AND id_producto = (SELECT id_producto FROM productos WHERE det_producto = '"+prod+"' and disponibilidad = 1)";
						String insert = "INSERT INTO productos_consumidos (id_cuenta, id_producto, cantidad) VALUES ( "+cuenta_actual+", (SELECT id_producto FROM productos WHERE det_producto = '"+prod+"' and disponibilidad = 1), "+cantidad+" )";
						String consulta = "SELECT pc.id_cuenta, pc.id_producto, pc.cantidad, p.det_producto FROM productos_consumidos pc JOIN productos p ON pc.id_producto = p.id_producto WHERE pc.id_cuenta = "+cuenta_actual+" AND p.det_producto = '"+prod+"' and disponibilidad = 1";
						Statement st;
				        ResultSet rs;
				        try {
				        	st = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				            rs = st.executeQuery(consulta);
				            boolean res = rs.last();
				            if(res) {				     
				            	executeQuery(update);
				            }else {executeQuery(insert);}

				            
				        }catch (SQLException ex) {
				            ex.printStackTrace();
				        }
				        alerta("Producto agregado con exito (Favor de actualizar cuenta)");
			    		
					}
		
    			}else {alerta("Porfavor ingrese un valor apropiado");}
    		}else {
    			System.out.println("Porfavor seleccione un producto y agregue cantidad");
    			alerta("Porfavor agregue cantidad y seleccione un producto");
    		}
 
    	}else if(evt.equals(regresarBtn)) {
    		System.out.println("Regresar presionado");
    		try {
    			cerrarVentana(event);
    			
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    }
	
	//verifica que sea numerico el valor de cantidad
	public boolean esNumerico() {
		boolean result= false;
		 try { 
		    Integer.parseInt(txtCantidad.getText()); 
		    result = true;
		    
		 } catch(NumberFormatException e) { 
		    result = false; 
		 }
		 return result;
		}
	
	//verifica si el texto en cantidad es positivo
	public boolean esPositivo(int i){
		boolean result = false;
		if (i<=0) {
			System.out.println("es menor a 0");
			result = false;
		}else {
			result = true;
			System.out.println("Esto si es positivo");
		}
		return result;
	}
	
	//pues cierra la ventana jaja saludos
	void cerrarVentana(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	//Obtenemos datos del menu de la base de datos
    public ObservableList<menuProductos> getMenuList(){
        ObservableList<menuProductos> prodList = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM productos where disponibilidad = 1";
        Statement st;
        ResultSet rs;
        
        try{
            st = connectDB.createStatement();
            rs = st.executeQuery(query);
            menuProductos menu;
            while(rs.next()){
            	menu = new menuProductos(rs.getString("det_producto"), rs.getInt("precio"), rs.getInt("id_producto"));
                prodList.add(menu);
                System.out.println("Productos obtenidos");
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return prodList;
    }
    
  //comando para mostrar datos del menu en la tabla
    public void showMenu(){
        ObservableList<menuProductos> list = getMenuList();
        
        precioCol.setCellValueFactory(new PropertyValueFactory<menuProductos, Integer>("precio"));//viene de la clase
        prodCol.setCellValueFactory(new PropertyValueFactory<menuProductos, String>("producto"));//viene de la clase
       
        tblMenu.setItems(list);
    }
	
	
	
	 
	//funcion para almacenar mesa actual en base de datos
	    void getMesaActual() {
	    	try {
				Statement st = connectDB.createStatement();
				ResultSet rs = st.executeQuery("SELECT mesa_act_col FROM mesa_actual WHERE num = 1");
				if(rs.next()) {
					mesa_actual = rs.getString("mesa_act_col");
					lblmesa.setText("Mesa "+mesa_actual);
				}
				
			}catch (SQLException e) {
	            e.printStackTrace();
			}
	        }
	    
	    
	    //almacena cuenta relacionada con la mesa actual 
	    void getCuentaActual(){
	    	try {
				Statement st = connectDB.createStatement();
				ResultSet rs = st.executeQuery("SELECT c.id_cuenta FROM cuenta c JOIN mesa_actual ma ON c.id_mesa = ma.mesa_act_col WHERE ma.num = 1 AND c.fecha_fin IS NULL");
				if(rs.next()) {
					cuenta_actual = rs.getString("id_cuenta");
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    
	    }
	
	//muestra una alerta, tipo sysout     
	void alerta(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
	    
	//identifica el precio de la fila seleccionada
	public int SelectPriceRow() {
	int precio = 0;
	ObservableList<menuProductos> Selector;
	Selector = tblMenu.getSelectionModel().getSelectedItems();
	if(Selector.isEmpty()) {
		alerta("Por favor, selecciona un elemento de la tabla.");
		precio = 0;
	}else {
	System.out.println(Selector.get(0).getPrecio());
	precio = Selector.get(0).getPrecio();
		}
	return precio;
	}
	
	//identifica el producto a adquirir mi loco

	public String SelectProdRow() {
		String prod =null;
		ObservableList<menuProductos> Selector;
		Selector = tblMenu.getSelectionModel().getSelectedItems();
		if(!Selector.isEmpty()) {
			System.out.println(Selector.get(0).getProducto());
			prod = Selector.get(0).getProducto();
		}
		return prod;
		}
		
	
	private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
	
	public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/elbicho", "root","");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
	
	@Override
	protected boolean getResultado() {return false;}
	public void initialize(URL url, ResourceBundle rb) {
        // TODO
		getMesaActual();
		showMenu();
		
    }
}

