//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148

//To paketo sto opoio anikei
package com.blackberry.client;


//kanei import ta paketa pou xreiazonte
import java.lang.String;
import net.rim.device.api.system.Application;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.TextField;



//Dimiourgeia class othonis
public class LocationScreen extends MainScreen implements FieldChangeListener{
	
	private ButtonField bgps;                     //Dimiourgei ena koumpi to koumpi "get location"
	private ButtonField sbgps;                    //Dimiourgei ena koumpi to koumpi "send location"
	private TextField tlocation;                  //Dimiourgei to pedio keimenou pou anagrafei tin topothesia
	private TextField  tinfo;                     //Dimiourgei to pedio keimenou pou perixei tis plirofories
	private String location;                      //H metabliti pou periexei tin topothesia
	private String info;                          //H etabliti pou periexei tis pliroforis gia ton xristi
	private String IP = "192.168.56.1";           //H metabliti pou periexei to IP tou Server
	private String Port = "4505";                 //H metabliti pou periexei to Port tou server
	
	
	public LocationScreen() {
		super();
		//Arxikopoihsh metabliton minumaton
    	location="Not Available Yet";
    	info="Not Available Yet";
   
       //Orizei ton titlo tis efarmogis    
        setTitle("BlackBerry Client");
        
        bgps = new ButtonField("Get Gps Location", ButtonField.CONSUME_CLICK| ButtonField.NEVER_DIRTY);  //Dimiourgei ena neo koumpi
        bgps.setChangeListener(this);                                                                    //Prosthetei kai tous listenerstou koumpiou
        add(bgps);                                                                                       //Kai telika to prosthetei stin arxiki othoni
        
       tlocation = new TextField();         //Dimiourgei ena pedio keimenou
       tlocation.setText(location);         //Gemizei to pedio me ta periexomena tis metablitis locaiton
       add(tlocation);                      //kai telika to prosthetei stin arxiki othoni
        
        
        
        sbgps = new ButtonField("Send Gps Location", ButtonField.CONSUME_CLICK| ButtonField.NEVER_DIRTY);  //Dimiourgei ena neo koumpi
        sbgps.setChangeListener(this);                                                                     //Prosthetei kai tous listenerstou koumpiou
        add(sbgps);                                                                                        //Kai telika to prosthetei stin arxiki othoni
         
        tinfo = new TextField();             //Dimiourgei ena pedio keimenou
        tinfo.setText(info);                 //Gemizei to pedio me ta periexomena tis metablitis locaiton
        add(tinfo);                          //kai telika to prosthetei stin arxiki othoni
        
		
		
	}

	public void fieldChanged(Field field, int context) {
		
		if(field == bgps)                                                             //Ean patithei to koumpi bgps
        {    		
			new Thread() {                                                            //Dimiourgoume kainourio Thread
				public void run() {                         
					LocationHandler lh = new LocationHandler();                       //Dimiourgoume neo antikeimeno tupou LocationHandler
					final String msg =lh.getCurrentLatitude()       
							+ "|" + lh.getCurrentLongitude();                         //Pernoume tis aparetites plirofories topothesias
					Application.getApplication().invokeLater(new Runnable() {
						public void run() {                                            
							location = msg;                                           //Antigrafoume tis plirofories topothesias
							tlocation.setText("Your Current Location is :"+ msg);     //Emfanizoume minuma sto xristi me tis plirofories topothesias
						}
					});
				}
			}.start();			
        }
        else if(field == sbgps)                                          //Ean patithei to koumpi sbgps
        {         	 
            info = MyApp.Dispatcher(location,IP,Port);                   //Kaloume ton Dispatcher (me orisma tis suntetagmenes)
            tinfo.setText(info);	                                     //Emfanizeoume tis plirofories stin othoni tou kinitou
        }
	}//End of filedChanged
	
	
}