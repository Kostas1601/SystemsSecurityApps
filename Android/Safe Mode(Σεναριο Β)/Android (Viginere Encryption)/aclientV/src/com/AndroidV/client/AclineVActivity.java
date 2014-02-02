//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148

//Dilosi namespace
package com.AndroidV.client;

//Fortosi arxeion sustimatos
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Class startingpoint(arxiki klasi android)
public class AclineVActivity extends Activity {

	// Dilosi metabliton klasis
	String ilocation; // metabliti pou tha periexei tin topothesia
	String ip = "10.0.2.2"; // metabliti pou periexei mesa to Ip sto opoio tha
							// sundethei o client
	int port = 4505; // metabliti pou periexai mesa to port sto opoio tha
						// stelenei ta dedomena
	Button bgps; // koumpi to opoio tha energopoiei ton Gps_collector
	Button sbgs; // koumpi to opoio tha energopoiei ton Dispacher
	TextView tlocation; // Pedio keimenou pou tha grefei tis plirofories
						// topothesias
	TextView info; // Pedio keimenou to opoio tha enimeronei ton
					// xristi(send,error..ktl)
	Location gps; // dilosi antikeimenou tupou location (apo ekei tha paroume
					// tis plirofories)

	// Methodos onCreate(Basiki mathodos android)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); // kalei kataskeutastei(patera)
		setContentView(R.layout.main); // arxikopoiei to UI

		bgps = (Button) findViewById(R.id.bgps); // sundesi tou koumpiou bgps me
													// to arxeio XML
		sbgs = (Button) findViewById(R.id.sbgps); // sundesi tou koumpiou sbgps
													// me to arxeio XML
		tlocation = (TextView) findViewById(R.id.tlocation); // sundesi tou
																// pediou
																// keimenou
																// tlocation me
																// to arxeio XML
		info = (TextView) findViewById(R.id.info); // sundesi tou pediou
													// keimenou info me to
													// arxeio XML

		// Handle bgps button
		bgps.setOnClickListener(new View.OnClickListener() { // Dimiourgoume
																// Listener sto
																// koumpi bgps

			@Override
			public void onClick(View arg0) { // Otan patithei to koumpi

				Gps_collector(); // Kaloume ton Gps_collector
				tlocation.setText("Your Current Location is : " + ilocation); // Kai
																				// emfanizoume
																				// stin
																				// othoni
																				// tou
																				// kinitou
																				// tin
																				// topothesia

			}// telos uperfortosis sunartisis onClick
		});// telos diaxirisi bgps button

		// Handle Send GPS button
		sbgs.setOnClickListener(new View.OnClickListener() {// Dimiourgoume
															// Listener sto
															// koumpi sbgps

			@Override
			public void onClick(View arg0) { // Otan patithei to koumpi

				String fLocation = formatLocation(ilocation);
				String cipher = Encryption_Module(fLocation); // Kryptografoume
																// ta dedomena
																// tis
																// topothesias

				Dispacher(cipher); // Kaloume ton Dispacher
				info.setText(ilocation); // Kai emfanizoume stin othoni tou
											// kinitou tis antistoixes
											// plirofories

			}// telos uperfortosis sunartisis onClick
		});// telos diaxirisi sbgps button

	}// End of onCreate

	//H methodos auth fernei ta dedomena GPS sthn morfh pou theloume
	// Dhladh (14 xarakthres, me to proshmo) gia na kryptografhthoun swsta
	public String formatLocation(String locat) {

		String lat = null, lon = null;
		StringTokenizer st = new StringTokenizer(locat, "|");
		// xwrizei to ilocation ipsilis akriveias se latitude kai longitude
		while (st.hasMoreTokens()) {
			lat = st.nextToken();
			lon = st.nextToken();
		}
		boolean negative = false;
		
		//Ean yparxei proshmo - tote to kwvoume prwsorina
		if (lat.substring(0, 1).compareTo("-") == 0) {
			negative = true;
			lat = lat.substring(1); // xwris to -
		}
		
		//Pairnoume ta psifia prin to "," kai analoga me to ean einai latitude ( 2 psifia prin to ",")
		// h Longitude (3 psifia prin to ",") kai opou xreiazetai prosthetoume mhdenika etsi wste na exoume to swsto format
		st = new StringTokenizer(lat, ",");
		String token = st.nextToken();

		if (token.length() == 1) {
			lat = "0" + lat;
		}
		if (negative) {
			lat = "-" + lat;
		} else {
			lat = "+" + lat;
		}
		
		
		negative = false;
		
		//Akrivws to idio gia to  longitude
		
		//Ean yparxei proshmo - tote to kwvoume prwsorina
		if (lon.substring(0, 1).compareTo("-") == 0) {
			negative = true;
			lon = lon.substring(1); // xwris to -
		}
		
		//Pairnoume ta psifia prin to "," kai analoga me to ean einai latitude ( 2 psifia prin to ",")
		// h Longitude (3 psifia prin to ",") kai opou xreiazetai prosthetoume mhdenika etsi wste na exoume to swsto format
		st = new StringTokenizer(lon, ",");
		token = st.nextToken();

		if (token.length() == 1) {
			lon = "00" + lon;
		}
		if (token.length() == 2) {
			lon = "0" + lon;
		}
		if (negative) {
			lon = "-" + lon;
		} else {
			lon = "+" + lon;
		}

		lat = lat.substring(0, 6); // tous prwtous 6 xarakthres
		lon = lon.substring(0, 7); // tous prwtous 7 xarakthres (giati exei 3
									// psifia prin to komma)
									
		//Ftiaxnoume to teliko String pou exei thn swsth telikh morfh 
		String locationz = lat + "|" + lon;
		//To epistrefoume
		return locationz;
	}

	// Afti h sunartisi sulegei ta dedomena apo to GPS
	public void Gps_collector() {

		// GPS
		final LocationManager gps = (LocationManager) getSystemService(Context.LOCATION_SERVICE);// Dimiourgia
																									// Diaxiristi
																									// Gps
		final LocationListener gpsl = new LocationListener() { // Dimiourgia Gps
																// Listener

			@Override
			public void onLocationChanged(Location location) { // Afti h sunarti
																// kaleite opote
																// allazoun oi
																// suntetagmenes
																// tis
																// topothesias
				ilocation = location.getLatitude() + "|"
						+ location.getLongitude(); // Apothikeuoume tin
													// topothesiastin metabliti
													// ilocation
			}

			// Oi upoloipes sunertiseis prepei na ulopoihthoun anagkastika
			// (prepei na tis kanoume override)
			// Omos kamia apo tis parakato den mas endiaferei opote afinoume to
			// "soma tous keno"
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

		};// end of gps listening

		// Zitame ananeosi tou simatos tou gps
		gps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsl);

	}// End of Gps_collector

	// Afti h sunartisi stelenei ta dedomena sto Server
	public void Dispacher(String ciph) {

		Socket echoSocket = null; // Dimiourgia Sockets
		PrintWriter out = null; // Dimiourgia Reumatos

		try {// Prospathise

			// O emulator tis google (meta apo arketo phaximo) katalabame pos
			// "akouei" mono stin Ip 10.0.2.2
			// stin pragmatikotita stin thesi autis mporoume na baloume opoia
			// alli IP theloume emeis
			// omos gia tous skopous tis ergasia kai na bebaiothoume oti to
			// project douleuei exoume
			// xrisimopoihsei tin sugkekrimeni IP
			echoSocket = new Socket(ip, port); // Dimiourgia Socket kai sundesi
												// sto IP=10.0.2.2 kai sto port
												// 4505(ekei akouei o Server
												// mas)
			out = new PrintWriter(echoSocket.getOutputStream(), true);
		} catch (UnknownHostException e) { // Se periptosi lathous (kai
											// sugkekrimena
											// UnknownHostException)
			info.setText("Don't know about host...."); // Emfanise analogo
														// minuma stin othoni
			e.printStackTrace(); // Emfanise stin consola tin stoiba
		} catch (IOException e) { // Se periptosi lathous (kai sugkekrimena
									// IOException)
			info.setText("Couldn't get I/O for " + "the connection"); // Emfanise
																		// analogo
																		// minuma
																		// stin
																		// othoni
			e.printStackTrace(); // Emfanise stin consola tin stoiba
		}

		out.println(ciph); // Graphe sto reuma tin topothesia
		out.close(); // Kleise to reuma

		try { // Prospathise
			echoSocket.close(); // na kleiseis kai tin sundesi metaxu
								// sercer-client
		} catch (IOException e) { // Se periptosi lathous (kai sugkekrimena
									// IOException)
			info.setText("Error....Could Not close Socket"); // Emfanise analogo
																// minuma stin
																// othoni
			e.printStackTrace(); // Emfanise stin consola tin stoiba
		}

	}// End of Dispacher

	/** <ENCRYPTION MODULE>-------------------------------------------------------------------------------**/
	//H methodos pou Kryptografei ta dedomena GPS Xrhsimopoiontas thn kryptografhsh Viginere
	public String Encryption_Module(String plainText) {
	//to n einai o arithmos twn xarakthrwn tou minimatos pou theloume na kryptografisoume
		final int n = plainText.length();

			//O pinakas symvolwn
		char[] Symbols = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'+', '-', '|', '.' };

	// Metatrepoume to plaintext se pinaka simvolwn gia na ta kryptografisoume
		char[] M = plainText.toCharArray();
		
	//Edw apothikeuetai to kleidi analoga me to megethos ths symvoloseiras pou theloume na apokryptografhsoume
		char[] K = new char[n];
		
	//Pinakas int pou tha periexei to teliko cipthertext
		int[] C = new int[n];
	
	//Edw apotikeuoume to teliko value apo to opoio aporkyptografoume to symvolo
		int[] mValue = new int[n];
	//Edw apothikeuoume ta values toy kathe symvolou tou kleidiou gia thn syggekrimmenh apokryptografhsh
		int[] kValue = new int[n];
		
	//To kleidi 
		char[] key = { '0', '1', '2', '3', '4', '5', '6', '7', '+', '-' };

	// Gemizoume ton K me to kleidi gia thn syggekrimmenh apokryptografhsh 14 xarakthrwn
		int p = 0;
		for (int i = 0; i < n; i++) {
			K[i] = key[p];
			p++;
			if (p == 10) {
				p = 0;
			}
		}
		
	//gemizoume ton pinaka me ta values tou mynimatos xrhsimopoiontas alphabet coding
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (M[i] == Symbols[j]) {
					mValue[i] = j;
				}
			}
		}
		
	//Gemizoume ton kValue me tis times pou antistoixoun se kathe symvolo tou kleidiou
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (K[i] == Symbols[j]) {
					kValue[i] = j;
				}
			}
		}

	//Dhmiourgoume to cipher symfwna me thn methodo kryptografhshs Viginere (timi cipher sthn thesi i) = ((value tou symvolou sto i tou plaintext) + (value tou kleidiou stin thesh i)) mod n
		for (int i = 0; i < n; i++) {
			C[i] = (mValue[i] + kValue[i]) % n;
		}

		// Metatroph array int se string
		StringBuffer buf = new StringBuffer();
		buf.append(C[0]);
		for (int i = 1; i < C.length; buf.append("_").append(C[i++]));
		
		//Metatroph tou pinaka xarakthrwn me to cipher se string
		String cipher = buf.toString();

		//Epistrefoume to ciphertext 
		return cipher;
	}
	/** </ENCRYPTION MODULE>-------------------------------------------------------------------------------**/


}// End of AclineVActivity
