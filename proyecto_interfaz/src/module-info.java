module proyecto_interfaz {
	 requires javafx.controls;
	    requires javafx.fxml;
		requires javafx.web;
		requires jxmaps;
		requires javafx.swing;
		requires javafx.graphics;
	    // Exporta el paquete donde está tu clase Main para que JavaFX pueda acceder a ella.
	    exports com.carballeira.aplication.view;
	    opens application to javafx.graphics, javafx.fxml;
	    opens com.carballeira.aplication.model to javafx.graphics, javafx.controls, javafx.base;
	    opens com.carballeira.aplication.view to javafx.graphics, javafx.controls,  javafx.base;
	}
