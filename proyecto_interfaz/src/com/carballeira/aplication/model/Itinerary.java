package com.carballeira.aplication.model;

import java.time.LocalDate;

public class Itinerary {
	// Variables
    private final String name;
    private final String startLocation;
    private final String endLocation;
    private final LocalDate tripDate;
    // Getters y Setters
    public String getName() {
		return name;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public LocalDate getTripDate() {
		return tripDate;
	}
	// Constructor
	public Itinerary(String itineraryName, String startLocation, String endLocation, LocalDate tripDate) {
        this.name = itineraryName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.tripDate = tripDate;  
    }
	// Validar
    public Boolean checkDatosVacios() {
    	if(name == null || startLocation == null || endLocation == null || tripDate == null) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

	
  
}
