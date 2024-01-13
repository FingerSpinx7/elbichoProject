package application;

import java.lang.System.Logger.Level;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainControlls.Main;
import model.ConnectionUtil;

public class addProdStockController extends Form implements Initializable {
	@FXML
    private ComboBox<MenuProveedores> ComboProv;
	@FXML
    private TableColumn<menuProductos, Integer> colPrecio;

    @FXML
    private TableColumn<menuProductos, String> colProd;

    @FXML
    private TableColumn<menuProductos, String> colProveedor;

    @FXML
    private TableView<menuProductos> tblProductos;

    @FXML
    private Button añadirBtn;
    
    @FXML
    private Button regresaBtn;
    
    @FXML
    private Button eliminarBtn;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtProd;
    
    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");
	

    @FXML
    void event1(ActionEvent event) {
    	Object evt = event.getSource();
    		if(evt.equals(añadirBtn)) {
    			System.out.println("Añadir presionado");
    			if(!txtPrecio.getText().trim().isEmpty()&&!txtProd.getText().trim().isEmpty()&&!(ComboProv.getValue()==null)) {
    				if(esNumerico()&&esPositivo(Integer.parseInt(txtPrecio.getText()))&&!(ComboProv.getValue()==null)){
    		    		String precio = txtPrecio.getText();	    		
    		    		String prod = txtProd.getText();
    		    		double precDoub = Double.parseDouble(precio);
    		    		
    		    		MenuProveedores proveedor = ComboProv.getValue();
    		    		String NomProveedor = proveedor.getNombre();
    		   
    		    		System.out.println("Todo bien");	    		
    		    		
    		    		
    		    		
    		    		int opcion = JOptionPane.showConfirmDialog(null, "¿Agregar "+prod+" con precio "+precio+" del proveedor(a) "+NomProveedor, "AGREGAR PRODUCTOS",  JOptionPane.YES_NO_OPTION);
    					if(opcion == JOptionPane.YES_OPTION) {
    						try {
        		    		int id_prov = obtenerIdProveedor(getConnection(), NomProveedor);
        		    		insertarNuevoProducto(getConnection(), prod, precDoub, id_prov);
    			    		alerta("Productos agregados correctamente");
    			    		showProductos();
    						}catch (SQLException e) {
    							e.printStackTrace();
    						}
    			    		
    					}
    				}
    			}else {alerta("Faltan campos por diligenciar");}
    		}else if(evt.equals(eliminarBtn)) {
    			String deleteItem=SelectProdRow();

    			if(deleteItem!=null) {
    			int opcion = JOptionPane.showConfirmDialog(null, "¿Eliminar producto "+deleteItem+"?", "AGREGAR PRODUCTOS",  JOptionPane.YES_NO_OPTION);
				if(opcion == JOptionPane.YES_OPTION) {
					executeQuery("UPDATE productos SET disponibilidad=0 WHERE det_producto = '"+deleteItem+"'");
    				showProductos();
				}
    		
    			}else {alerta("Seleccione un articulo de la tabla para eliminar");}
    		}else if(evt.equals(regresaBtn)) {
    			cerrarVentana(event);
    		}
    }
    
    void cerrarVentana(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
    
    public String SelectProdRow() {
		String prod =null;
		ObservableList<menuProductos> Selector;
		Selector = tblProductos.getSelectionModel().getSelectedItems();
		if(!Selector.isEmpty()) {
			System.out.println(Selector.get(0).getProducto());
			prod = Selector.get(0).getProducto();
		}
		return prod;
		}
    
    void alerta(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
    
    private static int obtenerIdProveedor(Connection connection, String nombreProveedor) throws SQLException {
        String query = "SELECT id_proveedor FROM proveedor WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombreProveedor);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_proveedor");
                }
            }
        }
        throw new SQLException("No se encontró el proveedor con el nombre " + nombreProveedor);
    }
    
    private static void insertarNuevoProducto(Connection connection, String detProducto,
            double precio, int idProveedor) throws SQLException {
        String query = "INSERT INTO productos (id_producto, id_proveedor, det_producto, precio) SELECT COALESCE(MAX(id_producto), 0) + 1, ?, ?, ?  FROM productos";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(2, detProducto);
            preparedStatement.setDouble(3, precio);
            preparedStatement.setInt(1, idProveedor);
            preparedStatement.executeUpdate();
            System.out.println("Producto insertado correctamente.");
        }
    }
   
    public boolean esNumerico() {
		boolean result= false;
		 try { 
		    Integer.parseInt(txtPrecio.getText()); 
		    result = true;
		    
		 } catch(NumberFormatException e) { 
		    result = false; 
		 }
		 return result;
		}
    
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
    
    //Obtenemos Proveedores de la base de datos
    public ObservableList<MenuProveedores> getMenuList(){
        ObservableList<MenuProveedores> prodList = FXCollections.observableArrayList();
        
        String query = "SELECT nombre from proveedor";
        Statement st;
        ResultSet rs;
        
        try{
            st = connectDB.createStatement();
            rs = st.executeQuery(query);
            MenuProveedores proveedores;
            while(rs.next()){
            	proveedores = new MenuProveedores(rs.getString("nombre"));
                prodList.add(proveedores);
                System.out.println("proveedores obtenidos");
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return prodList;
    }
    
    
    //Mostramos los proveedores
    public void showProv(){
        ObservableList<MenuProveedores> list = getMenuList();
      
        ComboProv.setItems(list);
    }
    
    public ObservableList<menuProductos> getProdMenu(){
        ObservableList<menuProductos> prodList = FXCollections.observableArrayList();
    
        String query = "SELECT p.id_producto, p.det_producto, p.precio, pr.nombre as nombre_proveedor FROM productos p JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor WHERE disponibilidad = 1";
        Statement st;
        ResultSet rs;
        
        try{
            st = connectDB.createStatement();
            rs = st.executeQuery(query);
            menuProductos prods;
            while(rs.next()){
            	prods = new menuProductos(rs.getInt("id_producto"), rs.getString("det_producto"), rs.getString("nombre_proveedor"),rs.getInt("precio"));
                prodList.add(prods);
                System.out.println("Productos obtenidos");
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return prodList;
    }
    
  //comando para mostrar datos del menu en la tabla
    public void showProductos(){
        ObservableList<menuProductos> list = getProdMenu();
        
        colProd.setCellValueFactory(new PropertyValueFactory<menuProductos, String>("producto"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<menuProductos, String>("proveedor"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<menuProductos, Integer>("precio"));
        
       
        tblProductos.setItems(list);
    }
    
    
	//conecta base de datos
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
    
    //funcion para executar querys
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
    
	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void initialize(URL url, ResourceBundle rb) {
        // TODO
		showProv();
		showProductos();
    }

}
