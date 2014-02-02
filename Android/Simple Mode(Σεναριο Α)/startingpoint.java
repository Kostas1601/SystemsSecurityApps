//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148


//Dilosi namespace
package com.android.Client;

//Fortosi arxeion sustimatos
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.android.Client.R;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Class startingpoint(arxiki klasi android)
public class startingpoint extends Activity {
   
	//Dilosi metabliton klasis
	String ilocation;	           //metabliti pou tha periexei tin topothesia
	String ip="10.0.2.2";          //metabliti pou periexei mesa to Ip sto opoio tha sundethei o client
	int port=4505;                 //metabliti pou periexai mesa to port sto opoio tha stelenei ta dedomena
	Button bgps;                   //koumpi to opoio tha energopoiei ton Gps_collector
	Button sbgs;                   //koumpi to opoio tha energopoiei ton Dispacher
	TextView tlocation;            //Pedio keimenou pou tha grefei tis plirofories topothesias
	TextView info;	               //Pedio keimenou to opoio tha enimeronei ton xristi(send,error..ktl)
	Location gps;                  //dilosi antikeimenou tupou location (apo ekei tha paroume tis plirofories)
	
	
	//Methodos onCreate(Basiki mathodos android)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
		
		super.onCreate(savedInstanceState);                                 //kalei kataskeutastei(patera)
        setContentView(R.layout.main);                                      //arxikopoiei to UI
        
        bgps=(Button) findViewById(R.id.bgps);                              //sundesi tou koumpiou bgps me to arxeio XML
        sbgs=(Button) findViewById(R.id.sbgps);                             //sundesi tou koumpiou sbgps me to arxeio XML
        tlocation = (TextView) findViewById(R.id.tlocation);                //sundesi tou pediou keimenou tlocation me to arxeio XML
        info = (TextView) findViewById(R.id.info);                          //sundesi tou pediou keimenou info me to arxeio XML 
        
        //Handle bgps button
        bgps.setOnClickListener(new View.OnClickListener() {                //Dimiourgoume Listener sto koumpi bgps	
        				
			@Override
			public void onClick(View arg0) {                                //Otan patithei to koumpi 
				
				Gps_collector();                                            //Kaloume ton Gps_collector
				tlocation.setText("Your Current Location is : "+ilocation);	//Kai emfanizoume stin othoni tou kinitou tin topothesia	
				
			}//telos uperfortosis sunartisis onClick
		});//telos diaxirisi bgps button
        
        
        //Handle Send GPS button
        sbgs.setOnClickListener(new View.OnClickListener() {//Dimiourgoume Listener sto koumpi sbgps	
			
			@Override
			public void onClick(View arg0) {	                //Otan patithei to koumpi 		
				Dispacher();                                    //Kaloume ton Dispacher
				info.setText("You Location has been sent....");	//Kai emfanizoume stin othoni tou kinitou tis antistoixes plirofories
				
			}//telos uperfortosis sunartisis onClick
		});//telos diaxirisi sbgps button       
        
        
    }//End of onCreate


   //Afti h sunartisi sulegei ta dedomena apo to GPS
   public void Gps_collector(){  
	   
	   
	   //GPS
       final LocationManager gps=(LocationManager)getSystemService(Context.LOCATION_SERVICE);//Dimiourgia Diaxiristi Gps
       final LocationListener gpsl=new LocationListener(){                                   //Dimiourgia Gps Listener

			@Override
			public void onLocationChanged(Location location) {                               //Afti h sunarti kaleite opote allazoun oi suntetagmenes tis topothesias
				ilocation = location.getLatitude()+"|"+location.getLongitude();		         //Apothikeuoume tin topothesiastin metabliti ilocation			
			}

			
			//Oi upoloipes sunertiseis prepei na ulopoihthoun anagkastika (prepei na tis kanoume override)
			//Omos kamia apo tis parakato den mas endiaferei opote afinoume to "soma tous keno"
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,Bundle extras) {
				// TODO Auto-generated method stub
				
			}		
       	
       	
       };//end of gps listening
	   
       //Zitame ananeosi tou simatos tou gps
       gps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsl);
   
	   
   }//End of Gps_collector
   
   

   //Afti h sunartisi stelenei ta dedomena sto Server
   public void Dispacher(){
	   
	   
	   Socket echoSocket = null;  //Dimiourgia Sockets
	   PrintWriter out = null;	  //Dimiourgia Reumatos	
		
	   
	   try {//Prospathise					
		   
		   //O emulator tis google (meta apo arketo phaximo) katalabame pos "akouei" mono stin Ip 10.0.2.2
		   //stin pragmatikotita stin thesi autis mporoume na baloume opoia alli IP theloume emeis
		   //omos gia tous skopous tis ergasia kai na bebaiothoume oti to project douleuei exoume 
		   //xrisimopoihsei tin sugkekrimeni IP		   
			echoSocket = new Socket(ip, port);                      //Dimiourgia Socket kai sundesi sto IP=10.0.2.2 kai sto port 4505(ekei akouei o Server mas)
			out = new PrintWriter(echoSocket.getOutputStream(), true);			
		} catch (UnknownHostException e) {                                  //Se periptosi lathous (kai sugkekrimena UnknownHostException)
			info.setText("Don't know about host....");	                    //Emfanise analogo minuma stin othoni
			e.printStackTrace();                                            //Emfanise stin consola tin stoiba
		} catch (IOException e) {                                           //Se periptosi lathous (kai sugkekrimena IOException)
			info.setText("Couldn't get I/O for "+"the connection");	        //Emfanise analogo minuma stin othoni
			e.printStackTrace();                                            //Emfanise stin consola tin stoiba
		}
	
		out.println(ilocation);                                             //Graphe sto reuma tin topothesia
		out.close();                                                        //Kleise to reuma
			 
	
		try {                                                               //Prospathise
			echoSocket.close();                                             //na kleiseis kai tin sundesi metaxu sercer-client
		} catch (IOException e) {                                           //Se periptosi lathous (kai sugkekrimena IOException)
			info.setText("Error....Could Not close Socket");		        //Emfanise analogo minuma stin othoni
			e.printStackTrace();                                            //Emfanise stin consola tin stoiba
		}
	   
   }//End of Dispacher

}//End of startingpoint
