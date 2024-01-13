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

import application.prodConsumidos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ConnectionUtil;

public class ViewDetMesaController extends Form implements Initializable{
	 
	String mesa_actual;
	String cuenta_actual;
	int tot_tiempo;
	int tot_productos;
	@FXML
    private TableColumn<prodConsumidos, Integer> colCantidad;
	
	@FXML
    private Button regresarBtn;
	
	@FXML
    private Button elimBtn;
	
	@FXML
	private Button ActBtn;

    @FXML
    private TableColumn<prodConsumidos, String> colDetProd;

    @FXML
    private TableColumn<prodConsumidos, Integer> colPrecio;

    @FXML
    private Label idMesa;
    
    @FXML
    private Label clieLbl;
    
    @FXML
    private Label cuentaLbl;
    
    @FXML
    private Label moneyTiempo;


    @FXML
    private Button registroBtn;

    @FXML
    private TableView<prodConsumidos> tblProductos;

    @FXML
    private Label tipoMesa;

    @FXML
    private Label totalLbl;

    @FXML
    private Label tiempotxt;
    
    @FXML
    private Button añadProdButton;
    
    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");

    @FXML
    void evento(ActionEvent event) {
    	Object evt = event.getSource();
    	if(evt.equals(registroBtn)) {
    		System.out.println("Registro presionado");
    		getCuentaActual();
    		int opcion = JOptionPane.showConfirmDialog(null, "¿Cerrar cuenta? (Esta accion pondra disponible la mesa)", "CERRAR CUENTA",  JOptionPane.YES_NO_OPTION);
			if(opcion == JOptionPane.YES_OPTION) {
				String query = "UPDATE cuenta SET fecha_fin = NOW() WHERE id_cuenta = "+cuenta_actual;
				executeQuery(query);
				alerta("El registro del pago fue exitoso");
				
				try {
					cerrarVentana(event);
					ViewMesasController controlador = new ViewMesasController();
					Window propietario = ((Node)event.getSource()).getScene().getWindow();
					muestraFormularioModal(controlador, "/view/ViewMesas.fxml", propietario, "Usuario");
					} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
    	}else if(evt.equals(añadProdButton)) {
    		System.out.println("Añadir producto presionado");
    		try {
				AgregarProdController controlador = new AgregarProdController();
				Window propietario = ((Node)event.getSource()).getScene().getWindow();
				muestraFormularioModal(controlador, "/view/AgregarProd.fxml", propietario, "Agregar productos");
				} catch (IOException e) {
				e.printStackTrace();
			}
    	}else if(evt.equals(ActBtn)) {
    		initializeTodo();
    	}else if(evt.equals(regresarBtn)) {
    		cerrarVentana(event);
    	
    	}else if(evt.equals(elimBtn)) {
    		String deleteItem=SelectProdRow();

			if(deleteItem!=null) {
			int opcion = JOptionPane.showConfirmDialog(null, "¿Eliminar producto "+deleteItem+"?", "Eliminar producto",  JOptionPane.YES_NO_OPTION);
			if(opcion == JOptionPane.YES_OPTION) {
				executeQuery("DELETE pc FROM productos_consumidos pc JOIN productos p ON pc.id_producto = p.id_producto WHERE p.det_producto = '"+deleteItem+"' AND pc.id_cuenta = "+cuenta_actual);
				showProductos();
			}
		
			}else {alerta("Seleccione un articulo de la tabla para eliminar");}
    	}
    	
    }
    
    //funcion para cerrar ventana
    void cerrarVentana(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
    public String SelectProdRow() {
		String prod =null;
		ObservableList<prodConsumidos> Selector;
		Selector = tblProductos.getSelectionModel().getSelectedItems();
		if(!Selector.isEmpty()) {
			System.out.println(Selector.get(0).getDet_prod());
			prod = Selector.get(0).getDet_prod();
		}
		return prod;
		}
    
    //funcion para almacenar mesa actual en base de datos
    void getMesaActual() {
    	try {
			Statement st = connectDB.createStatement();
			ResultSet rs = st.executeQuery("SELECT mesa_act_col FROM mesa_actual WHERE num = 1");
			if(rs.next()) {
				mesa_actual = rs.getString("mesa_act_col");
				idMesa.setText(mesa_actual);
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
		}
        }
    
    void getCliente() {
    	try {
    		getCuentaActual();
			Statement st = connectDB.createStatement();
			ResultSet rs = st.executeQuery("SELECT Cliente FROM cuenta WHERE id_cuenta = "+cuenta_actual);
			if(rs.next()) {
				String cliente = rs.getString("Cliente");
				clieLbl.setText(cliente);
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
    
    
    //imprime el tiempo transcurrido
    void tiempo_min(){
    	try {
    		
    		getMesaActual();
    		getCuentaActual();
			Statement st = connectDB.createStatement();
			ResultSet rs = st.executeQuery("SELECT TIMESTAMPDIFF(MINUTE,fecha_inicio,NOW()) AS minutos_totales FROM cuenta WHERE id_cuenta = "+cuenta_actual);

	        
			if(rs.next()) {
				String minutos = rs.getString("minutos_totales");
				String minutosInt = rs.getString("minutos_totales");
				     
			    float minInt = Float.parseFloat(minutosInt);
				tiempotxt.setText(minutos);
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
		}
    }
    
    //imprime el tipo de mesa en el label correspondiente
    void tipo_mesa() {
try {
    		
    		getMesaActual();
    		
			Statement st = connectDB.createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM `mesa` WHERE id_mesa = "+mesa_actual);

	        
			if(rs.next()) {
				String tip_mesa = rs.getString("tipo_mesa");
				     
				tipoMesa.setText(tip_mesa);
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
		}
    }
    
            
     void totalTiempo() {
    	 try{
    		getMesaActual();
    		getCuentaActual();
			Statement st = connectDB.createStatement();
			ResultSet rsT = st.executeQuery("SELECT CONCAT(CAST(IFNULL(SUM(precio*cantidad), 0) + IFNULL(TIMESTAMPDIFF(MINUTE, fecha_inicio, NOW()) * (40/60), 0) AS CHAR), '') AS total_costo FROM cuenta c LEFT JOIN productos_consumidos pc ON c.id_cuenta = pc.id_cuenta LEFT JOIN productos p ON pc.id_producto = p.id_producto WHERE c.id_cuenta = "+cuenta_actual+" OR c.id_cuenta IS NULL");
	        
			if(rsT.next()) {
				String numTiempo = rsT.getString("total_costo");      
				totalLbl.setText(numTiempo);
			}
			
			
		}catch (SQLException e) {
         e.printStackTrace();
		}
}
     
     void alerta(String mensaje) {
 		Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Advertencia");
         alert.setHeaderText(null);
         alert.setContentText(mensaje);
         alert.showAndWait();
 	}

    
  //Obtenemos datos del menu de la base de datos
    public ObservableList<prodConsumidos> getMenuList(){
        ObservableList<prodConsumidos> prodList = FXCollections.observableArrayList();
        
        getMesaActual();
        getCuentaActual();
        
        
        String query = "SELECT pc.cantidad, p.det_producto, p.precio*pc.cantidad AS precio FROM productos p JOIN productos_consumidos pc ON p.id_producto = pc.id_producto WHERE pc.id_cuenta = "+cuenta_actual;
        Statement st;
        ResultSet rs;
        
        try{
            st = connectDB.createStatement();
            rs = st.executeQuery(query);
            prodConsumidos prodConsumidos;
            while(rs.next()){
            	prodConsumidos = new prodConsumidos(rs.getString("det_producto"), rs.getInt("cantidad"), rs.getInt("precio"));
                prodList.add(prodConsumidos);
                System.out.println("Productos obtenidos");
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return prodList;
    }
    
  //comando para mostrar datos del menu en la tabla
    public void showProductos(){
        ObservableList<prodConsumidos> list = getMenuList();
        
        colDetProd.setCellValueFactory(new PropertyValueFactory<prodConsumidos, String>("det_prod"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<prodConsumidos, Integer>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<prodConsumidos, Integer>("precio"));
       
        tblProductos.setItems(list);
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
	
	void initializeTodo(){
		showProductos();
		tiempo_min();
		tipo_mesa();
		totalTiempo();
		getCliente();
		setCuenta();
		setMoneyTiempo();
	}
	
	void setCuenta() {
		getCuentaActual();
		String noCuenta = cuenta_actual;
		cuentaLbl.setText("Cuenta "+noCuenta);
	}
	
	private void setMoneyTiempo() {
		try{
    		getMesaActual();
    		getCuentaActual();
			Statement st = connectDB.createStatement();
			ResultSet rsT = st.executeQuery("SELECT CONCAT(CAST(IFNULL(TIMESTAMPDIFF(MINUTE, fecha_inicio, NOW()) * (40/60), 0) AS CHAR), '') AS total_costo FROM cuenta c LEFT JOIN productos_consumidos pc ON c.id_cuenta = pc.id_cuenta LEFT JOIN productos p ON pc.id_producto = p.id_producto WHERE c.id_cuenta = "+cuenta_actual+" OR c.id_cuenta IS NULL");
	        
			if(rsT.next()) {
				String numTiempo = rsT.getString("total_costo");      
				moneyTiempo.setText("$"+numTiempo);
			}	
		}catch (SQLException e) {
         e.printStackTrace();
		}
	}
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
		initializeTodo();		
    }
	
	

}
