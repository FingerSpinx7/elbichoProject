package application;


import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import model.ConnectionUtil;

public class ViewMesasController extends Form implements Initializable {
	
	@FXML
    private Button consultBtn;
	
	@FXML
	private Button addProdBtn;

	@FXML
	private Button addProvBtn;
	
	@FXML
    private Button actualizarBtn;
	
	@FXML
    private Button mesa1Id;

    @FXML
    private Button mesa2Id;

    @FXML
    private Button mesa3Id;
    
    @FXML
    private Button mesa4Id;
    
    @FXML
    private Button mesa5Id;
    
    @FXML
    private Button mesa6Id;
    
    @FXML
    private Button mesa7Id;
    
    @FXML
    private Button mesa8Id;
    
    @FXML
    private Button mesa9Id;
    
    @FXML
    private Button mesa10Id;
    
    @FXML
    private Button mesa11Id;
    
    @FXML
    private Button mesa12Id;
    
    @FXML
    private ImageView img1;

    @FXML
    private ImageView img10;

    @FXML
    private ImageView img11;

    @FXML
    private ImageView img12;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ImageView img6;

    @FXML
    private ImageView img7;

    @FXML
    private ImageView img8;

    @FXML
    private ImageView img9;

    Image image1 = new Image(getClass().getResourceAsStream("PoolTable.png"));
    Image image2 = new Image(getClass().getResourceAsStream("png-transparent-billiard-table-pool-billiards-fashion-billiards-game-angle-fashion-girl.png"));
    
    
    

    @FXML
    void eventAct(ActionEvent event) {
    	Object evt = event.getSource();
    	
    	if(evt.equals(mesa1Id)) {
    		System.out.println("1");
    		int id = 1;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
				
    		
    	}else if(evt.equals(mesa2Id)){
    		System.out.println("2");
    		int id = 2;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}

    		
    	}else if(evt.equals(mesa3Id)) {
    		System.out.println("3");
    		int id = 3;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}

    	}else if(evt.equals(mesa4Id)) {
    		System.out.println("4");
    		int id = 4;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa5Id)) {
    		System.out.println("5");
    		int id = 5;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
    			
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa6Id)) {
    		System.out.println("6");
    		int id = 6;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa7Id)) {
    		System.out.println("7");
    		int id = 7;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa8Id)) {
    		System.out.println("8");
    		int id = 8;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa9Id)) {
    		System.out.println("9");
    		int id = 9;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa10Id)) {
    		System.out.println("10");
    		int id = 10;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa11Id)) {
    		System.out.println("11");
    		int id = 11;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);

    		}
    		
    	}else if(evt.equals(mesa12Id)) {
    		System.out.println("12");
    		int id = 12;
    		act_mesa(id);
    		if(statusMesa(id)==1) {
    			System.out.println("Mesa Ocupada");
				abrirVentanaDetMesa(event);

    		}else {
    			System.out.println("Mesa libre");
    			abrirVentanaAddClie(event);
    			
    		}
    	}else if(evt.equals(actualizarBtn)) {
    		initializeMesas();
    		
    	}else if(evt.equals(addProdBtn)) {
    		System.out.println("añadir prod presionado");
    		try {
    		addProdStockController controlador = new addProdStockController();
			Window propietario = ((Node)event.getSource()).getScene().getWindow();
			muestraFormularioModal(controlador, "/view/addProdStock.fxml", propietario, "Añadir producto");
    		}catch(Exception e) {e.printStackTrace();}
    		
    		
    	}else if(evt.equals(addProvBtn)) {
    		System.out.println("Añadir prov presionado");
    		try {
        		addProvController controlador = new addProvController();
    			Window propietario = ((Node)event.getSource()).getScene().getWindow();
    			muestraFormularioModal(controlador, "/view/addProv.fxml", propietario, "Añadir producto");
        		}catch(Exception e) {e.printStackTrace();}
        		
    	}else if(evt.equals(consultBtn)) {
    		System.out.println("Consulta presionado");
    		try {
        		ConsultaCuentaController controlador = new ConsultaCuentaController();
    			Window propietario = ((Node)event.getSource()).getScene().getWindow();
    			muestraFormularioModal(controlador, "/view/ConsultaCuenta.fxml", propietario, "Añadir producto");
        		}catch(Exception e) {e.printStackTrace();}
    	}
    	
    }

    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");
	
	//Actualizara la mesa la cual esta en la interfaz el administrador
    public void act_mesa(int i){
    	String iStr1 = i+"";
		String query = "UPDATE mesa_actual SET mesa_act_col = "+iStr1+" WHERE num = 1";
		executeQuery(query);
    }
    
    
    //Dira a partir de un int si la mesa esta disponible u ocupada
    public int statusMesa(int id) {
		int ret = 0;
    	try {
    		String idStr = id+"";
    		
    		Statement statement = connectDB.createStatement();
    		Statement statementUser = connectDB.createStatement();
    		
    		
    		//Guardara en esta parte el usuario ingresado
    		String querySelect = "SELECT status FROM mesa WHERE id_mesa = "+idStr;
    		ResultSet queryUser = statementUser.executeQuery(querySelect);

    		if (queryUser.next()) {
    		    int num = queryUser.getInt("status");
    		    if(num == 1) {
    		    	ret = 1;
    		    }else {
    		    	ret = 0;
    		    }
    		}		
    	}catch(Exception e) {e.printStackTrace();return 0;}
		
    	return ret;    
    }
    
    
	
    //Cambia el color de los botones dependiendo la ocupacion de la mesa
    void cambiaColor(int id, Button but,ImageView img) {
    	if (statusMesa(id) == 1) {
            but.setStyle("-fx-background-color: #cc0000;");
            // Carga la imagen desde el paquete
            img.setImage(image2);
        } else {
            but.setStyle("-fx-background-color: #40bf40;");
            // Carga la otra imagen desde el paquete
            img.setImage(image1);
        }
    }
    
    //Funcion que permite abrir nueva cuenta si la mesa se encuentra disponible
    public void nuevaCuenta(ActionEvent event,int idMesa) {
    	
    	try {
    		Statement statement = connectDB.createStatement();
    		Statement statementUser = connectDB.createStatement();
    		String idMesaStr=idMesa+"";
    		
    		
    		//Guardara en esta parte el usuario ingresado
    		String querySelect = "INSERT INTO cuenta (id_cuenta, id_mesa, fecha_inicio, fecha_fin) SELECT COALESCE(MAX(id_cuenta), 0) + 1, "+idMesaStr+", NOW(), null FROM cuenta";
    		executeQuery(querySelect);
    		
    		ViewDetMesaController controlador = new ViewDetMesaController();
			Window propietario = ((Node)event.getSource()).getScene().getWindow();
			muestraFormularioModal(controlador, "/view/ViewDetMesa.fxml", propietario, "Usuario");
			cambiaTcolores();

    		}catch(Exception e) {e.printStackTrace();}

    }
    
    
    
    //funcion para ejcutar querys
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
   
    
    //establece conexion con la base de datos
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
	   
    void cambiaTcolores() {
    	cambiaColor(1,mesa1Id,img1);
		cambiaColor(2,mesa2Id,img2);
		cambiaColor(3,mesa3Id,img3);
		cambiaColor(4,mesa4Id,img4);
		cambiaColor(5,mesa5Id,img5);
		cambiaColor(6,mesa6Id,img6);
		cambiaColor(7,mesa7Id,img7);
		cambiaColor(8,mesa8Id,img8);
		cambiaColor(9,mesa9Id,img9);
		cambiaColor(10,mesa10Id,img10);
		cambiaColor(11,mesa11Id,img11);
		cambiaColor(12,mesa12Id,img12);
    }
    
    void abrirVentanaDetMesa(ActionEvent event) {
    try {
		ViewDetMesaController controlador = new ViewDetMesaController();
		Window propietario = ((Node)event.getSource()).getScene().getWindow();
		muestraFormularioModal(controlador, "/view/ViewDetMesa.fxml", propietario, "Usuario");
	} catch (IOException e) {
		e.printStackTrace();
		}
	}
    
    void abrirVentanaAddClie(ActionEvent event) {
        try {
    		AddClienteController controlador = new AddClienteController();
    		Window propietario = ((Node)event.getSource()).getScene().getWindow();
    		muestraFormularioModal(controlador, "/view/AddCliente.fxml", propietario, "Cliente");
    	} catch (IOException e) {
    		e.printStackTrace();
    		}
    	}
    
    void actualizarStatusxFecha() {
    	try {
    		Statement statement = connectDB.createStatement();
    		Statement statementUser = connectDB.createStatement();
    		    		
    		for(int i=1;i<=12;i++) {
    			String iStr=i+"";
    			String querySelect = "UPDATE mesa SET status = CASE WHEN NOT EXISTS (SELECT 1 FROM cuenta WHERE mesa.id_mesa = cuenta.id_mesa) OR NOT EXISTS (SELECT 1 FROM cuenta WHERE mesa.id_mesa = cuenta.id_mesa AND cuenta.fecha_fin IS NULL) THEN 0 ELSE 1 END WHERE id_mesa = "+iStr;
        		executeQuery(querySelect);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    void initializeMesas() {
		actualizarStatusxFecha();
		cambiaTcolores();
	}

    
	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
		actualizarStatusxFecha();
		cambiaTcolores();

    }
	
	
}
