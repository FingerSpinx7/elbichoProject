package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ConnectionUtil;

public class AddClienteController extends Form implements Initializable{
	String mesa_actual;
	
	
	@FXML
    private Button EmpezarBtn;
	
	@FXML
    private Button regresarBtn;
	
    @FXML
    private TextField txtNombre;
    
    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");

    @FXML
    void event1(ActionEvent event) {
    	Object evt = event.getSource();
    	if(evt.equals(EmpezarBtn)) {
    		if(!txtNombre.getText().trim().isEmpty()) {
    			String clie =  txtNombre.getText();
    			int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas abrir nueva cuenta con el cliente "+clie+"?\n(Esta acción ocupará la mesa)", "MESA LIBRE",  JOptionPane.YES_NO_OPTION);
    			if(opcion == JOptionPane.YES_OPTION) {
    				cerrarVentana(event);
    				getMesaActual();
    				nuevaCuenta(event,mesa_actual,clie);
    			}
    		}
    	}else if(evt.equals(regresarBtn)) {
    		cerrarVentana(event);
    	}
    }
	
    void cerrarVentana(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
    
    public void nuevaCuenta(ActionEvent event,String idMesa,String Cliente) {
    	
    	try {
    		Statement statement = connectDB.createStatement();
    		Statement statementUser = connectDB.createStatement();
    		String idMesaStr=idMesa;
    		
    		
    		//Guardara en esta parte el usuario ingresado
    		String querySelect = "INSERT INTO cuenta (id_cuenta, id_mesa, fecha_inicio, fecha_fin, Cliente) SELECT COALESCE(MAX(id_cuenta), 0) + 1, "+idMesaStr+", NOW(), null, '"+Cliente+"' FROM cuenta";
    		executeQuery(querySelect);
    		
    		ViewDetMesaController controlador = new ViewDetMesaController();
			Window propietario = ((Node)event.getSource()).getScene().getWindow();
			muestraFormularioModal(controlador, "/view/ViewDetMesa.fxml", propietario, "Usuario");

    		}catch(Exception e) {e.printStackTrace();}

    }
    
	
	
	void getMesaActual() {
    	try {
			Statement st = connectDB.createStatement();
			ResultSet rs = st.executeQuery("SELECT mesa_act_col FROM mesa_actual WHERE num = 1");
			if(rs.next()) {
				mesa_actual = rs.getString("mesa_act_col");
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
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
		
    }
}
