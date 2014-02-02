//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148


//To paketo sto opoio briskete
package com.blackberry.client;

//kanei import ta paketa pou xreiazonte
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;
import net.rim.device.api.system.Application;
import net.rim.device.api.ui.component.Status;

//Dilosi class LocationHandler
public class LocationHandler {
	private LocationProvider locationProvider;       //Dimiourgia metablitis pou periexei ton Provider
	private Location currentLocation;                //Dimiourgia metablitis pou periexei tis plirofories topothesias

	public LocationHandler() {                      
		try {
			locationProvider = LocationProvider.getInstance(null);       //Pithmisi parametron Provider 
			currentLocation = locationProvider.getLocation(60);          //Pernei tis plirofores topothesies apo ton Provider
		} catch (final Exception e) {
			Application.getApplication().invokeLater(new Runnable() {    //Se periptosi sfalmatos
				public void run() {
					Status.show(e.getMessage());                         //Emfanise minuma lathous
				}
			});
		}
	}//End of Constructor
	
	
	//H sunartisi getCurrentLongitude
	public double getCurrentLongitude() {
		if (currentLocation != null)
			return currentLocation.getQualifiedCoordinates().getLongitude();   //Epistrefei to Longitude
		return -1;                                                             //Se periptosi lathous epistrefei -1
	}

	
	//H sunartisi getCurrentLatitude()
	public double getCurrentLatitude() {
		if (currentLocation != null) 
			return currentLocation.getQualifiedCoordinates().getLatitude();    //Epistrefei to Latitude
		return -1;                                                             //Se periptosi lathous epistrefei -1
	}
	
	
}//End of LoacationHandler