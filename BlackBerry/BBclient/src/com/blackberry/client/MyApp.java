//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148

//To paketo sto opoio anhkei
package com.blackberry.client;

//Kanoume Import ta paketa pou xreiazomaste
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import net.rim.device.api.ui.UiApplication;

//To kurio class tis efarmogis
public class MyApp extends UiApplication
{
    //H main methodos tis efarmogis
    public static void main(String[] args)
    {
        MyApp theApp = new MyApp();               //Dimiourgoume ena antikeimeno tupoy MyApp 
        theApp.enterEventDispatcher();            //Prosthetoume ton EventDispatcher
    }
    

 
    //Dilosh klashs MyApp
    public MyApp()
    {           
        pushScreen(new LocationScreen()); //Dimiourgia othonis
    }

    //H sunartisi Dispatcher
    //Tin dilonoume static prokeimenoun na mporesoume na tin kalesoume kai tin Screen
    public static String Dispatcher(String location,String IP,String port){
    	
    	
    	//Dimiourgia String pou tha dothei os orisma prokeimenou na epiteuxthei h sundesi
    	//To deviceside einai mia metabliti pou anaferete ston an to kinito tha xrisimopoihsei tis
    	//upiresies tis BlackBerry h oxi.Otan einai false tis xrisomopoiei
    	//Otan einai true apla dimiourgei mia TCP connection
    	String ConnetionStr = "socket://" + IP + ":" + port + ";"+ "deviceside=true" ;
    	
    	
    	int length = location.length();   //Metabliti pou periexei to mikos ton pliroforion topothesias
    	String info = "";                 //Metabliti pou periexei plirofories sxetika me tin sundesi(oi opoies emfanizonte stin othoni tou kinitou)
  	    
  		try {
  			
  			
  			 StreamConnection conn = (StreamConnection)Connector.open(ConnetionStr);    //Dimiourgia sundesis tou Client-Server
  			 OutputStreamWriter out = new OutputStreamWriter(conn.openOutputStream());	//Dimiourgia Stream sto opoio tha metaferoume ta dedomena
  		    
  			 out.write(location, 0, length);	          //Grafoume sto Stream ta dedomena topothesias
  		     out.close();                                 //Kleinoume to Stream
  		     conn.close();                                //Kleinoume tin sundesi
  		     info = "Your location has been sent...";     //Enimeronoume to xristi tou kinitou oti h apostoli oloklirothike
  		     
  		    } catch (IOException e) {
					info = "Can not set up Connection";   //Enimeronoume to xristi oti upirxe sfalma kati tin diarkeia tis sundesis
					System.err.println("IOExceptionzzz"); //Emfanizoume sto Console to exception
					e.printStackTrace();                  //Emfanizoume sto Console tin stoiba
		    }
  		    
  		   return info;     //Epistrefoume tis plirofories
    	
    }//End of Dispacher
    
}//End of MyApp
