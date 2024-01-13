package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.ViewMesasController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ConnectionUtil;

public class MainController extends Form implements Initializable {

	@FXML
    private PasswordField passTxt;
    @FXML
    private TextField userTxt;
    @FXML
    private Button btnLogin;
    
    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");

    @FXML
    void logButton(ActionEvent event) {
    	Object evt = event.getSource();
   	 if(evt.equals(btnLogin)) {
   		 if(!userTxt.getText().trim().isEmpty() && !passTxt.getText().trim().isEmpty()) {
   			 	String user = userTxt.getText();
   			 	String pass = passTxt.getText();
   			 	System.out.println(user+1);
   			 	System.out.println(pass+1);
   			 	//valida los datos al presionar login 
   	    		ConnectionUtil connectNow = new ConnectionUtil();
   	        	Connection connectDB = connectNow.connectdb("elbicho");
   	        	
   	        	String verifyLogin = "SELECT COUNT(1) FROM datos_usuario WHERE BINARY usuario = '"+user+"' AND BINARY contraseña = '"+pass+"'";
   	        	   	        	
   	        	try {
   	        		Statement statement = connectDB.createStatement();
   	        		Statement statementUser = connectDB.createStatement();
   	        		ResultSet queryResult = statement.executeQuery(verifyLogin);
   	       
   	        		
   	        		while(queryResult.next()) {
   	        			if(queryResult.getInt(1)==1) {
   	        				ViewMesasController controlador = new ViewMesasController();
   	        				Window propietario = ((Node)event.getSource()).getScene().getWindow();     
   	        				muestraFormularioModal(controlador, "/view/ViewMesas.fxml", propietario, "Usuario");
   	        			} else {
   	        				 JOptionPane.showMessageDialog(null, "Usuario y/o contraseña invalida","Error",JOptionPane.WARNING_MESSAGE);

   	        			}
   	        		}
   	        		}	
   	        	catch(Exception e) {e.printStackTrace();}
   	 	 
   		 } 
   	 }
   	
   }

    void eliminarOldCuentas() {
    	int contador =0;
    	Statement st;
        ResultSet rs;
        
        try{
            st = connectDB.createStatement();
            rs = st.executeQuery("SELECT * FROM cuenta WHERE fecha_inicio <= DATE_SUB(CURDATE(), INTERVAL 6 YEAR)");
            Cuenta cuentas;
            while(rs.next()){
            	String id = rs.getString("id_cuenta");
            	
            	contador++;          	
            	executeQuery("DELETE FROM productos_consumidos WHERE id_cuenta = "+id);
            	executeQuery("DELETE FROM cuenta WHERE id_cuenta = "+id);
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
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
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
		eliminarOldCuentas();

    }

}
