package com.carballeira.aplication.view;


import java.time.LocalDate;


import com.carballeira.aplication.controller.AlertasController;
import com.carballeira.aplication.controller.Controller;
import com.carballeira.aplication.model.Itinerary;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class MapaInteractivo extends Application {
	// Variables
	private final String titulo = "Planificador de Viajes";
	private final String lbl_nombre = "Nombre del itinerario";
	private final String lbl_salida = "Lugar de salida";
	private final String lbl_destino = "Lugar de destino";
	private final String lbl_fecha = "Fecha del viaje";
	private final String lbl_itinerario = "Lista de itinerarios";
	private final String btn_guardar = "Guardar itinerario";
	private final String btn_mapa = "Mostrar mapa";
	
	// Controlador
    private Controller controller = new Controller();
    // Tabla
    private TableView<Itinerary> itineraryTableView;
    private ObservableList<Itinerary> itineraries = FXCollections.observableArrayList();
    
    // Usar la variable de instancia ImageView y asegurarse de que esté inicializada
    @FXML
    private ImageView imageView; 


    //rutas imagenes 
    private static final String iconoAvion = "file:/D:/avion.png";
    private static final String imagenGato = "file:/D:/gato.png";
    private static final String imagenNoGato = "file:/D:/nogato.png";


    // Obtener la lista de itinerarios
    public ObservableList<Itinerary> getItineraries() {
        return itineraries;
    }
    // Restablece la lista
    public void setItineraries(ObservableList<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }


    // Metodo principal
    @Override
    public void start(Stage primaryStage) {
        configurePrimaryStage(primaryStage);
        BorderPane root = createMainLayout();
        Scene scene = configureScene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        startItineraryUpdateThread();
    }
    // Ventana principal
    private void configurePrimaryStage(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(iconoAvion));
        primaryStage.setTitle(titulo);
    }
    // Diseño
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setTop(createTopContainer());
        root.setCenter(new VBox());
        root.setBottom(createBottomContainer());
        return root;
    }
    // Contenedor
    private VBox createTopContainer() {
        VBox topContainer = new VBox(10);
        topContainer.setPadding(new Insets(15));


        Label itineraryNameLabel = new Label(lbl_nombre);
        TextField itineraryNameField = new TextField();


        Label startLabel = new Label(lbl_salida);
        ComboBox<String> startComboBox = createLocationComboBox();


        Label endLabel = new Label(lbl_destino);
        ComboBox<String> endComboBox = createLocationComboBox();


        Label tripDateLabel = new Label(lbl_fecha);
        DatePicker tripDatePicker = new DatePicker();


        Button saveButton = createSaveButton(itineraryNameField, startComboBox, endComboBox, tripDatePicker);
        Button mapButton = createMapButton(startComboBox, endComboBox);


        // Solo inicializar imageView si es necesario en este contenedor.
        if (imageView == null) {
            imageView = createImageView(); // Inicializar solo si es null
        }


        topContainer.getChildren().addAll(
            itineraryNameLabel, itineraryNameField,
            startLabel, startComboBox,
            endLabel, endComboBox,
            tripDateLabel, tripDatePicker,
            saveButton, mapButton,
            imageView
        );


        return topContainer;
    }
    // Tabla
    private VBox createBottomContainer() {
        VBox bottomContainer = new VBox(10);
        bottomContainer.setPadding(new Insets(15));


        Label itineraryListLabel = new Label(lbl_itinerario);
        itineraryTableView = createItineraryTableView();


        bottomContainer.getChildren().addAll(itineraryListLabel, itineraryTableView);
        return bottomContainer;
    }
    // Desplegable de los lugares
    private ComboBox<String> createLocationComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
            "Andalucía", "Aragón", "Asturias", "Islas Baleares", "Canarias", "Cantabria",
            "Castilla y León", "Castilla-La Mancha", "Cataluña", "Extremadura", "Galicia",
            "Madrid", "Murcia", "Navarra", "La Rioja", "País Vasco", "Comunidad Valenciana",
            "Ceuta", "Melilla"
        );
        return comboBox;
    }
    // Boton para guardar el itinerario
    private Button createSaveButton(TextField itineraryNameField, ComboBox<String> startComboBox,
            ComboBox<String> endComboBox, DatePicker tripDatePicker) {
        Button saveButton = new Button(btn_guardar);
        saveButton.setOnAction(e -> {
            // Obtener los datos de la vista
            String itineraryName = itineraryNameField.getText();  // Obtener el nombre del itinerario
            String startLocation = startComboBox.getValue();      // Obtener el lugar de inicio
            String endLocation = endComboBox.getValue();          // Obtener el lugar de fin
            LocalDate tripDate = tripDatePicker.getValue();       // Obtener la fecha del viaje (LocalDate directamente)
            
            // Crear el objeto Itinerary con los datos obtenidos
            Itinerary itinerary = new Itinerary(itineraryName, startLocation, endLocation, tripDate);
            controller.setModel(itinerary);
            controller.setVista(this);
            // Llamar al método del controlador para guardar el itinerario
            controller.saveItinerary(); // Aquí pasamos el itinerario al controlador para guardarlo
            // Limpiar los datos
            clearFields(itineraryNameField, startComboBox, endComboBox, tripDatePicker);
        });
        return saveButton;
    }


    public void addItinerary(Itinerary itinerary) {
        itineraries.add(itinerary);
    }
    
    // Limpiar los campos
    private void clearFields(TextField itineraryNameField, ComboBox<String> startComboBox,
		ComboBox<String> endComboBox, DatePicker tripDatePicker) {
		// Limpiar el campo de texto
		itineraryNameField.clear();
		
		// Limpiar las selecciones de los ComboBox
		startComboBox.getSelectionModel().clearSelection();
		endComboBox.getSelectionModel().clearSelection();
		
		// Limpiar la selección de la fecha
		tripDatePicker.setValue(null);
	}
    
    
    // Boton de mostrar mapa
    private Button createMapButton(ComboBox<String> startComboBox, ComboBox<String> endComboBox) {
        Button mapButton = new Button(btn_mapa);
        mapButton.setOnAction(e -> {
            String startLocation = startComboBox.getValue();
            String endLocation = endComboBox.getValue();
            if (startLocation != null && endLocation != null) {
                abrirMapaInteractivo(startLocation, endLocation);
            } else {
            	new AlertasController().showAlert("Por favor, seleccione ambos lugares (origen y destino)");
            }
        });
        return mapButton;
    }


    private ImageView createImageView() {
        Image image = new Image(imagenNoGato);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }
    
    // Crear la tabla de itinerarios
    private TableView<Itinerary> createItineraryTableView() {
        TableView<Itinerary> tableView = new TableView<>();
        tableView.setPrefHeight(250);


        TableColumn<Itinerary, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(192);


        TableColumn<Itinerary, String> startColumn = new TableColumn<>("Origen");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startLocation"));
        startColumn.setPrefWidth(192);
        
        TableColumn<Itinerary, String> endColumn = new TableColumn<>("Destino");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endLocation"));
        endColumn.setPrefWidth(191);


        TableColumn<Itinerary, String> dateColumn = new TableColumn<>("Fecha");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("tripDate"));
        dateColumn.setPrefWidth(191);
        
        // Agregar columnas a la tabla
        tableView.getColumns().addAll(nameColumn, startColumn, endColumn, dateColumn);
        tableView.setItems(getItineraries());
        return tableView;
    }
    
    // Escena
    private Scene configureScene(BorderPane root) {
        Scene scene = new Scene(root, 800, 730);
        scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
        return scene;
    }
    
    // Abrir el mapa interactivo
    private void abrirMapaInteractivo(String startLocation, String endLocation) {
        Stage mapStage = new Stage();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();


        double[] startCoords = controller.getCoordinates(startLocation);
        double[] endCoords = controller.getCoordinates(endLocation);


        if (startCoords != null && endCoords != null) {
            String rutaArchivoHtml = getClass().getResource("/mapa.html").toExternalForm();
            webEngine.load(rutaArchivoHtml);


            webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    String script = "var startLatLng = [" + startCoords[0] + ", " + startCoords[1] + "];" +
                                    "var endLatLng = [" + endCoords[0] + ", " + endCoords[1] + "];" +
                                    "initializeMap(startLatLng, endLatLng);";
                    webEngine.executeScript(script);
                }
            });
        } else {
        	new AlertasController().showAlert("No se pudieron obtener las coordenadas.");
        }


        StackPane root = new StackPane();
        root.getChildren().add(webView);
        Scene scene = new Scene(root, 800, 800);
        mapStage.setScene(scene);
        mapStage.getIcons().add(new Image(iconoAvion));
        mapStage.show();
    }
    
    // Hilo
    private void startItineraryUpdateThread() {
        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    Platform.runLater(() -> {
                        itineraryTableView.setItems(getItineraries());
                        if (imageView != null) {
                            imageView.setImage(new Image(imagenGato));  // Usar imagenGato
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                    Platform.runLater(() -> imageView.setImage(new Image(imagenNoGato)));  // Usar imagenNoGato
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }	
}


