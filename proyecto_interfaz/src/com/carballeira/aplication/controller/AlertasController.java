package com.carballeira.aplication.controller;

import javafx.scene.control.Alert;

public class AlertasController {
	public void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
