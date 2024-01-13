
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class Form implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
	protected abstract boolean getResultado();
	
	protected boolean muestraFormularioModal(Form controlador, String fxml, Window propietario, String titulo) throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxml));
		loader.setController(controlador);
		Stage form2 = new Stage();
		form2.setTitle(titulo);
		form2.setScene(new Scene(loader.load()));
		form2.initModality(Modality.WINDOW_MODAL);
		form2.initOwner(propietario);
		form2.showAndWait();
		
		return controlador.getResultado();
	}
}
