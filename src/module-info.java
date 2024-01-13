module Elbicho {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.sql;
	requires itextpdf;
	
	opens view to javafx.fxml, javafx.base;
	exports mainControlls to javafx.graphics;
	exports application to javafx.fxml, itext.pdf;

	opens application to javafx.fxml,javafx.base;
	
	
}

