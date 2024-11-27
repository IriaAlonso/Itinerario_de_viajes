package com.carballeira.aplication.controller;

import java.util.HashMap;
import java.util.Map;

import com.carballeira.aplication.model.Itinerary;
import com.carballeira.aplication.view.MapaInteractivo;

import javafx.scene.control.Alert;

public class Controller {
	private Itinerary model;
	private MapaInteractivo vista;
	private static final String advertencia = "file:/D:/advertencia.png";

	public MapaInteractivo getVista() {
		return vista;
	}

	public void setVista(MapaInteractivo vista) {
		this.vista = vista;
	}

	private Map<String, double[]> coordenadas = new HashMap<>();

	public Itinerary getModel() {
		return model;
	}

	public void setModel(Itinerary model) {
		this.model = model;
	}

	public Controller(Itinerary model, MapaInteractivo vista) {
		this.model = model;
		this.vista = vista;
	}

	public Controller() {
		// Coordenadas de las provincias
		coordenadas.put("Andalucía", new double[] { 37.3891, -5.9845 });
		coordenadas.put("Aragón", new double[] { 41.6032, -0.1276 });
		coordenadas.put("Asturias", new double[] { 43.3667, -5.85 });
		coordenadas.put("Islas Baleares", new double[] { 39.6953, 3.0176 });
		coordenadas.put("Canarias", new double[] { 28.2916, -16.6291 });
		coordenadas.put("Cantabria", new double[] { 43.1820, -3.9876 });
		coordenadas.put("Castilla y León", new double[] { 41.5922, -4.7534 });
		coordenadas.put("Castilla-La Mancha", new double[] { 39.8762, -3.6548 });
		coordenadas.put("Cataluña", new double[] { 41.9029, 2.1270 });
		coordenadas.put("Extremadura", new double[] { 39.4764, -6.3433 });
		coordenadas.put("Galicia", new double[] { 42.6021, -8.6442 });
		coordenadas.put("Madrid", new double[] { 40.4168, -3.7038 });
		coordenadas.put("Murcia", new double[] { 37.9922, -1.1307 });
		coordenadas.put("Navarra", new double[] { 42.6950, -1.6767 });
		coordenadas.put("La Rioja", new double[] { 42.2544, -2.3810 });
		coordenadas.put("País Vasco", new double[] { 43.0622, -2.6117 });
		coordenadas.put("Comunidad Valenciana", new double[] { 39.4699, -0.3763 });
		coordenadas.put("Ceuta", new double[] { 35.8890, -5.3160 });
		coordenadas.put("Melilla", new double[] { 35.2951, -2.9380 });
	}

	// Guardar el itinerario
	public void saveItinerary() {
		if (model.checkDatosVacios()) {
			this.vista.addItinerary(model);
			new AlertasController().showAlert("Itinerario guardado exitosamente");
			
		}else {
			new AlertasController().showAlert("Campos incompletos");
		}
	}
	

	// Obtener coordenadas de una ubicación
	public double[] getCoordinates(String location) {
		return coordenadas.get(location);
	}
	


}
