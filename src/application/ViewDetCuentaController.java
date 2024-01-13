package application;

import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ConnectionUtil;

public class ViewDetCuentaController extends Form implements Initializable{
	@FXML
    private Label clieLbl;

    @FXML
    private TableColumn<prodConsumidos, Integer> colCantidad;

    @FXML
    private TableColumn<prodConsumidos, String> colDetProd;

    @FXML
    private TableColumn<prodConsumidos, Integer> colPrecio;

    @FXML
    private Label cuentaLbl;

    @FXML
    private Label idMesa;

    @FXML
    private Button regresarBtn;

    @FXML
    private Button generarPdfBtn;

    
    @FXML
    private TableView<prodConsumidos> tblProductos;
    
    @FXML
    private Label moneyTiempo;

    @FXML
    private Label tiempotxt;

    @FXML
    private Label tipoMesa;

    @FXML
    private Label totalLbl;

    ConnectionUtil connectNow = new ConnectionUtil();
	Connection connectDB = connectNow.connectdb("elbicho");
    
    @FXML
    void evento(ActionEvent event) {
    	Object evt = event.getSource();
    	if(evt.equals(regresarBtn)) {
    		cerrarVentana(event);
    	}else if(evt.equals(generarPdfBtn)) {
    		pdfCreator();
    	}
    }
	
	
    private int getCuentaActual() {
		int cuentaSelected=0;
    	try {
			Statement st = connectDB.createStatement();
			ResultSet rs = st.executeQuery("SELECT id_cuenta FROM cuenta_selected WHERE num = 1");
			if(rs.next()) {
				cuentaSelected = rs.getInt("id_cuenta");
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
		}
    	return cuentaSelected;
      }
    
    void tiempo_min(){
    	try {
    		
    		int cuenta = getCuentaActual();
			Statement st = connectDB.createStatement();
			ResultSet rs = st.executeQuery("SELECT TIMESTAMPDIFF(MINUTE,fecha_inicio,fecha_fin) AS minutos_totales FROM cuenta WHERE id_cuenta = "+cuenta);        
			if(rs.next()) {
				String minutos = rs.getString("minutos_totales");
				tiempotxt.setText(minutos);
			}
		}catch (SQLException e) {
            e.printStackTrace();
		}
    }
    
    
    void tipo_mesa() {
    	try {    			
    		
    		int cuenta = getCuentaActual();
    		Statement st = connectDB.createStatement();	
    		ResultSet rs = st.executeQuery("SELECT mesa.tipo_mesa FROM cuenta JOIN mesa ON cuenta.id_mesa = mesa.id_mesa WHERE cuenta.id_cuenta = "+cuenta);
    			if(rs.next()) {
    				String tip_mesa = rs.getString("tipo_mesa");
    					     
    				tipoMesa.setText(tip_mesa);
    			}		
    		}catch (SQLException e) {
    	         e.printStackTrace();
    		}
    	}
    
    void setCuentaL() {
    	int cuenta = getCuentaActual();
    	String cuentaLbl = "Cuenta "+cuenta;
    	this.cuentaLbl.setText(cuentaLbl);
    	}
    
    void id_mesa() {
    	try {    				
    		int cuenta = getCuentaActual();
    		Statement st = connectDB.createStatement();	
    		ResultSet rs = st.executeQuery("SELECT id_mesa FROM cuenta WHERE id_cuenta = "+cuenta);
    			if(rs.next()) {
    				String tip_mesa = rs.getString("id_mesa");				     
    				idMesa.setText(tip_mesa);
    			}		
    		}catch (SQLException e) {
    	         e.printStackTrace();
    		}
    	}
    
    void setCliente() {
    	try {    				
    		int cuenta = getCuentaActual();
    		Statement st = connectDB.createStatement();	
    		ResultSet rs = st.executeQuery("SELECT Cliente FROM cuenta WHERE id_cuenta = "+cuenta);
    			if(rs.next()) {
    				String clie = rs.getString("Cliente");				     
    				clieLbl.setText(clie);
    			}		
    		}catch (SQLException e) {
    	         e.printStackTrace();
    		}
    	
    }
    
    void totalTiempo() {
   	 try{
   		 	int cuenta = getCuentaActual();
			Statement st = connectDB.createStatement();
			ResultSet rsT = st.executeQuery("SELECT CONCAT(CAST(IFNULL(SUM(precio*cantidad), 0) + IFNULL(TIMESTAMPDIFF(MINUTE, fecha_inicio, fecha_fin) * (40/60), 0) AS CHAR), '') AS total_costo FROM cuenta c LEFT JOIN productos_consumidos pc ON c.id_cuenta = pc.id_cuenta LEFT JOIN productos p ON pc.id_producto = p.id_producto WHERE c.id_cuenta = "+cuenta+" OR c.id_cuenta IS NULL");
	        
			if(rsT.next()) {
				String numTiempo = rsT.getString("total_costo");      
				totalLbl.setText(numTiempo);
			}
		}catch (SQLException e) {
        e.printStackTrace();
		}
    }
    
  //Obtenemos datos del menu de la base de datos
    public ObservableList<prodConsumidos> getMenuList(){
        ObservableList<prodConsumidos> prodList = FXCollections.observableArrayList();
        
        int cuenta = getCuentaActual();
        
        
        String query = "SELECT pc.cantidad, p.det_producto, p.precio*pc.cantidad AS precio FROM productos p JOIN productos_consumidos pc ON p.id_producto = pc.id_producto WHERE pc.id_cuenta = "+cuenta;
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
    

    void cerrarVentana(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void setMoneyTiempo() {
		try{
    		int cuenta =getCuentaActual();
			Statement st = connectDB.createStatement();
			ResultSet rsT = st.executeQuery("SELECT CONCAT(CAST(IFNULL(TIMESTAMPDIFF(MINUTE, fecha_inicio, fecha_fin) * (40/60), 0) AS CHAR), '') AS total_costo FROM cuenta c LEFT JOIN productos_consumidos pc ON c.id_cuenta = pc.id_cuenta LEFT JOIN productos p ON pc.id_producto = p.id_producto WHERE c.id_cuenta = "+cuenta+" OR c.id_cuenta IS NULL");
	        
			if(rsT.next()) {
				String numTiempo = rsT.getString("total_costo");      
				moneyTiempo.setText("$"+numTiempo);
			}	
		}catch (SQLException e) {
         e.printStackTrace();
		}
	}
	
	void pdfCreator() {
		int id = getCuentaActual();
		Document documento = new Document();
		
		try {
			String ruta = System.getProperty("user.home");
			PdfWriter.getInstance(documento, new FileOutputStream(ruta+"/Desktop/Cuenta_pdf/Cuenta"+id+".pdf"));
			
			documento.open();
			PdfPTable tabla = new PdfPTable(5);
			tabla.addCell("Cuenta");
			tabla.addCell("Mesa");
			tabla.addCell("Fecha inicio");
			tabla.addCell("Fecha fin");
			tabla.addCell("Cliente");
			
			try {
				int i=0;
				String query = "select * from cuenta where id_cuenta ="+id;
				String queryProd="SELECT pc.cantidad, p.det_producto, p.precio*pc.cantidad AS precio FROM productos p JOIN productos_consumidos pc ON p.id_producto = pc.id_producto WHERE pc.id_cuenta = "+id;
				String queryTotalMin = "SELECT TIMESTAMPDIFF(MINUTE,fecha_inicio,fecha_fin) AS minutos_totales FROM cuenta WHERE id_cuenta =" +id;
				String queryTotal = "SELECT CONCAT(CAST(IFNULL(TIMESTAMPDIFF(MINUTE, fecha_inicio, fecha_fin) * (40/60), 0) AS CHAR), '') AS total_costo FROM cuenta c LEFT JOIN productos_consumidos pc ON c.id_cuenta = pc.id_cuenta LEFT JOIN productos p ON pc.id_producto = p.id_producto WHERE c.id_cuenta = "+id+" OR c.id_cuenta IS NULL";
				String Totalote = "SELECT CONCAT(CAST(IFNULL(SUM(precio*cantidad), 0) + IFNULL(TIMESTAMPDIFF(MINUTE, fecha_inicio, fecha_fin) * (40/60), 0) AS CHAR), '') AS total_costo FROM cuenta c LEFT JOIN productos_consumidos pc ON c.id_cuenta = pc.id_cuenta LEFT JOIN productos p ON pc.id_producto = p.id_producto WHERE c.id_cuenta = "+id+" OR c.id_cuenta IS NULL";
				
				
				Statement st = connectDB.createStatement();
				ResultSet rsT = st.executeQuery(query);
				
				Statement stP = connectDB.createStatement();
				ResultSet rsP = stP.executeQuery(queryProd);
				
				Statement stTotTi = connectDB.createStatement();
				ResultSet rsTotTi = stTotTi.executeQuery(queryTotalMin);
				
				Statement stTot = connectDB.createStatement();
				ResultSet rsTot = stTot.executeQuery(queryTotal);
				
				Statement stTotalote = connectDB.createStatement();
				ResultSet rsTotalote = stTotalote.executeQuery(Totalote);
				
				
				//Detalles cuenta
				if(rsT.next()){
					do {
						tabla.addCell(rsT.getString(1));
						tabla.addCell(rsT.getString(2));
						tabla.addCell(rsT.getString(3));
						tabla.addCell(rsT.getString(4));
						tabla.addCell(rsT.getString(5));
						i++;
						System.out.println(i);
						
					}while(rsT.next());
					System.out.println("hola");
				}
				tabla.addCell("----");
				tabla.addCell("----");
				tabla.addCell("----");
				tabla.addCell("----");
				tabla.addCell("----");
				
				tabla.addCell("Productos");
				tabla.addCell("");
				tabla.addCell("");
				tabla.addCell("");
				tabla.addCell("");
				
				tabla.addCell("Detalle producto");
				tabla.addCell("Cantidad");
				tabla.addCell("Total");
				tabla.addCell("");
				tabla.addCell("");
				
				//Detalles productos
				if(rsP.next()){
					do {
						tabla.addCell(rsP.getString(2));
						tabla.addCell(rsP.getString(1));
						tabla.addCell("$"+rsP.getString(3));
						tabla.addCell("");
						tabla.addCell("");
						i++;
						System.out.println(i);
						
					}while(rsP.next());
					System.out.println("hola");
				}
				
				//Tiempo
				tabla.addCell("Tiempo");
				while(rsTotTi.next()){
	            	tabla.addCell(rsTotTi.getString(1)+" min.");
	            }
				
				while(rsTot.next()){
	            	tabla.addCell("$"+rsTot.getString(1));
	            }
				tabla.addCell("");
				tabla.addCell("");
				
				
				//Total
				tabla.addCell("");
				tabla.addCell("");
				while(rsTotalote.next()){
	            	tabla.addCell("$"+rsTotalote.getString(1));
	            }
				tabla.addCell("");
				tabla.addCell("");

				documento.add(tabla);	
				
			}catch (DocumentException | SQLException e)	{				
			}
			documento.close();
			JOptionPane.showMessageDialog(null, "Reporte creado");
		} catch (DocumentException | HeadlessException | FileNotFoundException e) {
		} 
	}
	
	void iniciar() {
		showProductos();
		totalTiempo();
		id_mesa();
		tiempo_min();
		setCliente();
		tipo_mesa();
		setCuentaL();
		setMoneyTiempo();
	}
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciar();
    }
		
}
