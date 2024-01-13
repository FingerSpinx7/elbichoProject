package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ConnectionUtil;

public class addProvController extends Form implements Initializable{


    @FXML
    private Button añadirBtn;

    @FXML
    private TableColumn<MenuProveedores, String> colNombre;

    @FXML
    private TableColumn<MenuProveedores, String> colTelefono;

    @FXML
    private TableView<MenuProveedores> tblProv;

    @FXML
    private TextField txtProv;

    @FXML
    private TextField txtTel;

    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");
	
    
    @FXML
    void event1(ActionEvent event) {
    	Object evt = event.getSource();
		if(evt.equals(añadirBtn)) {
			if(!txtProv.getText().trim().isEmpty()&&!txtTel.getText().trim().isEmpty()&&txtTel.getText().length()==10&&esNumerico()&&esPositivo(Double.parseDouble(txtTel.getText()))) {
				System.out.println("Campos llenos");
				int opcion = JOptionPane.showConfirmDialog(null, "¿Agregar proveedor(a) "+txtProv.getText()+" con telefono "+txtTel.getText()+"?", "AGREGAR PRODUCTOS",  JOptionPane.YES_NO_OPTION);
				if(opcion == JOptionPane.YES_OPTION) {
					try{
						String nombre = txtProv.getText();
						String tel = txtTel.getText();
						String query = "INSERT INTO proveedor (id_proveedor, telefono, nombre) SELECT COALESCE(MAX(id_proveedor), 0) + 1, "+tel+", '"+nombre+"' FROM proveedor";
						executeQuery(query);
						showMenu();
						
					} catch (Exception e) {e.printStackTrace();}
					alerta("Proveedor(a) agregado con exito");
				}
				
			}else {alerta("Faltan campos por diligenciar y/o verificar marcacion correcta del telefono");}
		}
    }
	
	
    public ObservableList<MenuProveedores> getMenuList(){
        ObservableList<MenuProveedores> prodList = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM proveedor";
        Statement st;
        ResultSet rs;
        
        try{
            st = connectDB.createStatement();
            rs = st.executeQuery(query);
            MenuProveedores menu;
            while(rs.next()){
            	menu = new MenuProveedores(rs.getInt("id_proveedor"), rs.getString("telefono"), rs.getString("nombre"));
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
        ObservableList<MenuProveedores> list = getMenuList();
        
        colNombre.setCellValueFactory(new PropertyValueFactory<MenuProveedores, String>("nombre"));//viene de la clase
        colTelefono.setCellValueFactory(new PropertyValueFactory<MenuProveedores, String>("telefono"));//viene de la clase
       
        tblProv.setItems(list);
    }
    
	
    void alerta(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
    
    public boolean esNumerico() {
		boolean result= false;
		 try { 
		    Double.parseDouble(txtTel.getText()); 
		    result = true;
		    
		 } catch(NumberFormatException e) { 
		    result = false; 
		 }
		 return result;
		}
    
    public boolean esPositivo(double i){
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
    
	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void initialize(URL url, ResourceBundle rb) {
        // TODO
		showMenu();
    }

}
