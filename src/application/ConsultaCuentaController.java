package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.ConnectionUtil;

public class ConsultaCuentaController extends Form implements Initializable {
	
	@FXML
	private Label lblFecha;
	
	@FXML
    private Button BuscarBtn;

    @FXML
    private Button ConsultarBtn;
    
    @FXML
    private TableColumn<Cuenta, String> colCliente;

    @FXML
    private TableColumn<Cuenta, Integer> colCuenta;

    @FXML
    private TableColumn<Cuenta, String> colFechaFin;

    @FXML
    private TableColumn<Cuenta, String> colFechaIn;

    @FXML
    private TableColumn<Cuenta, Integer> colMesa;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Cuenta> tblCuenta;
    
    @FXML
    private Button regresarBtn;
    
    @FXML
    private Button TodasBtn;


    @FXML
    private TextField txtNoMesa;
    
    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");
    
    @FXML
    void event1(ActionEvent event) {
    	Object evt = event.getSource();
    		if(evt.equals(datePicker)) {
    			String fecha = getDate();   			
    			String[] partesFecha = fecha.split("-"); 			
    			String fechaSinDia = partesFecha[0]+"-"+partesFecha[1]+"-";
    			
    			int diaSig = getNextDay();
    			
    			if(!txtNoMesa.getText().trim().isEmpty()) {
    				if(esNumerico()) {
            			int NoMesa = Integer.parseInt(txtNoMesa.getText());
            				if(esPositivo(NoMesa)) {
            					showCuentas("SELECT * FROM cuenta WHERE fecha_inicio >= '"+fecha+" 00:00:00' AND fecha_inicio < '"+fechaSinDia+diaSig+" 00:00:00' and fecha_fin is not null and id_mesa = "+NoMesa);
            				}else {alerta("Ingrese un valor apropiado para elegir cuenta");}
    				}else {
    					String cadena = txtNoMesa.getText();
        				showCuentas("SELECT * FROM cuenta WHERE fecha_inicio >= '"+fecha+" 00:00:00' AND fecha_inicio < '"+fechaSinDia+diaSig+" 00:00:00' and fecha_fin is not null and Cliente = '"+cadena+"'");
        				}
    			}else {
    				showCuentas("SELECT * FROM cuenta WHERE fecha_inicio >= '"+fecha+" 00:00:00' AND fecha_inicio < '"+fechaSinDia+diaSig+" 00:00:00' and fecha_fin is not null");
    			}
    			
    			
    			
    		}else if(evt.equals(BuscarBtn)) {
    			String fecha=null; 
    			String fechaSinDia=null;
    			int diaSig=0;
    			
    			if(!(getDate() == null)) {
    			fecha = getDate();
    			String[] partesFecha = fecha.split("-"); 			
    			fechaSinDia = partesFecha[0]+"-"+partesFecha[1]+"-";			
    			diaSig = getNextDay();
    			}
    			
    			
    			if(!txtNoMesa.getText().trim().isEmpty()) {
    				if(fecha!=null) {
	    				if(esNumerico()) {
	            			int NoMesa = Integer.parseInt(txtNoMesa.getText());
	            				if(esPositivo(NoMesa)) {
	            					showCuentas("SELECT * FROM cuenta WHERE fecha_inicio >= '"+fecha+" 00:00:00' AND fecha_inicio < '"+fechaSinDia+diaSig+" 00:00:00' and fecha_fin is not null and id_mesa = "+NoMesa);
	            				}else {alerta("Ingrese un valor apropiado para elegir cuenta");}
	    				}else {
	    					String cadena = txtNoMesa.getText();
	        				showCuentas("SELECT * FROM cuenta WHERE fecha_inicio >= '"+fecha+" 00:00:00' AND fecha_inicio < '"+fechaSinDia+diaSig+" 00:00:00' and fecha_fin is not null and Cliente = '"+cadena+"'");
	        				}
    				}else {
    					String cadena = txtNoMesa.getText();
        				showCuentas("SELECT * FROM cuenta WHERE fecha_fin is not null and Cliente = '"+cadena+"'");
    					
    				}
	    		}else {
	    			showCuentas("SELECT * FROM cuenta WHERE fecha_inicio >= '"+fecha+" 00:00:00' AND fecha_inicio < '"+fechaSinDia+diaSig+" 00:00:00' and fecha_fin is not null");
	    		}
    			
    			
    		}else if(evt.equals(ConsultarBtn)) {
    			System.out.println("Consultar presionado");
    			int cuentaAct = SelectCuentaRow();
    			if (cuentaAct!=0) {
    			executeQuery("UPDATE `cuenta_selected` SET `id_cuenta`="+cuentaAct+" WHERE num =1");
    			abrirVentanaDetCuenta(event);
    			}
    			
    			
    		}else if(evt.equals(regresarBtn)) {
    			cerrarVentana(event);
    			
    		}else if(evt.equals(TodasBtn)) {
    			int noMesa = 0;
    			if(!txtNoMesa.getText().trim().isEmpty()&&esNumerico()) {
    				noMesa = Integer.parseInt(txtNoMesa.getText());
    			}
    			String noMesaStr = txtNoMesa.getText();
    			if(!txtNoMesa.getText().trim().isEmpty()) {
    				if(esNumerico()) {
    					if(esPositivo(noMesa)) {
    		    			showCuentas("SELECT * FROM cuenta WHERE fecha_fin is NOT null and id_cuenta = " +noMesa);
        				}else {alerta("Ingrese un valor apropiado");}
    				}else {
		    			showCuentas("SELECT * FROM cuenta WHERE fecha_fin is NOT null and Cliente = '" +noMesaStr+"'");

    				}
    				
    			}else {showCuentas("SELECT * FROM cuenta WHERE fecha_fin is NOT null");}
    			
    		}
    }
	
    
    void abrirVentanaDetCuenta(ActionEvent event) {
        try {
    		ViewDetCuentaController controlador = new ViewDetCuentaController();
    		Window propietario = ((Node)event.getSource()).getScene().getWindow();
    		muestraFormularioModal(controlador, "/view/ViewDetCuenta.fxml", propietario, "Cuenta");
    	} catch (IOException e) {
    		e.printStackTrace();
    		}
    	}
    
    public boolean esNumerico() {
		boolean result= false;
		 try { 
		    Integer.parseInt(txtNoMesa.getText()); 
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
			System.out.println(" es menor a 0");
			result = false;
		}else {
			result = true;
			System.out.println("Esto si es positivo");
		}
		return result;
	}
    
    private int getNextDay() {
		LocalDate fechaActual = datePicker.getValue();
        int dia = fechaActual.getDayOfMonth();
        return dia+1;
    }
    
	private String getDate() {
		LocalDate fechaAct = datePicker.getValue();
		
		
		System.out.println(fechaAct.toString());
		System.out.println(formatoFecha(fechaAct));
		String newFecha= formatoFecha(fechaAct);
		return newFecha;
	}
    
    
    
	void setFecha() {
		LocalDate fechaActual = LocalDate.now();

        // Obtener año, mes y día por separado
        int año = fechaActual.getYear();
        int mes = fechaActual.getMonthValue();
        int dia = fechaActual.getDayOfMonth();
		System.out.println(año+"-"+mes+"-"+dia);
		
		datePicker.setValue(fechaActual);
	}
	
	
	
	public String formatoFecha(LocalDate fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = fecha.format(formatter);
        return formattedDate;
	}
	
	void cerrarVentana(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	public ObservableList<Cuenta> getCuentas(String query){
        ObservableList<Cuenta> CuentaList = FXCollections.observableArrayList();
    
        Statement st;
        ResultSet rs;
        
        try{
            st = connectDB.createStatement();
            rs = st.executeQuery(query);
            Cuenta cuentas;
            while(rs.next()){
            	cuentas = new Cuenta(rs.getInt("id_cuenta"), rs.getInt("id_mesa"), rs.getString("Cliente"),rs.getString("fecha_inicio"),rs.getString("fecha_fin"));
            	CuentaList.add(cuentas);
                System.out.println("Productos obtenidos");
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return CuentaList;
    }
    
	public int SelectCuentaRow() {
		int cuenta = 0;
		ObservableList<Cuenta> Selector;
		Selector = tblCuenta.getSelectionModel().getSelectedItems();
		if(Selector.isEmpty()) {
			alerta("Por favor, selecciona un elemento de la tabla.");
			cuenta = 0;
		}else {
		System.out.println(Selector.get(0).getId_cuenta());
		cuenta = Selector.get(0).getId_cuenta();
			}
		return cuenta;
		}
	
	void alerta(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
	
  //comando para mostrar datos del menu en la tabla
    public void showCuentas(String query){
        ObservableList<Cuenta> list = getCuentas(query);
        
        colCuenta.setCellValueFactory(new PropertyValueFactory<Cuenta, Integer>("id_cuenta"));
        colMesa.setCellValueFactory(new PropertyValueFactory<Cuenta, Integer>("id_mesa"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("Cliente"));
        colFechaIn.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("fecha_inicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<Cuenta, String>("fecha_fin"));
       
        
       
        tblCuenta.setItems(list);
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
		setFecha();
		
		String fecha = getDate();   			
		String[] partesFecha = fecha.split("-"); 			
		String fechaSinDia = partesFecha[0]+"-"+partesFecha[1]+"-";
		int diaSig = getNextDay();
		showCuentas("SELECT * FROM cuenta WHERE fecha_inicio >= '"+fecha+" 00:00:00' AND fecha_inicio < '"+fechaSinDia+diaSig+" 00:00:00' and fecha_fin is not null");

		
    }
	
}

