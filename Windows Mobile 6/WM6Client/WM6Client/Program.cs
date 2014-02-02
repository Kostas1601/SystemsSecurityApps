//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148

//Fortonoume paketa pou xriazomaste
using System;
using System.Net.Sockets;
using System.Linq;
using System.Collections.Generic;
using System.Windows.Forms;
using System.IO;
using System.Text;

//Fortonoume to paketo location apo ton fakelo Samples
using Microsoft.WindowsMobile.Samples.Location;


namespace WM6Client                                  //H efarmogi mas
{
    static class Program
    {       
       
        [MTAThread]
        static void Main()                          //H main tou programmatos
        {
            Application.Run(new Form1());           //Dimiourgia Form1
        }

        public static string Gps_collection()      //H Sunartisi Gps_collection
        {
            string location;                       //Metabliti pou periexei plirofories topothesias
            string lat = "1";                        //Arxikopoihsh metablitis pou periexei plirofories Latidute(1 gia na einai diaforo tou 0 prokeimenou na doume ama douleuei to gps)
            string lon = "1";                       //Arxikopoihsh metablitis pou periexei plirofories Longitude(1 gia na einai diaforo tou 0 prokeimenou na doume ama douleuei to gps)

            Gps gps = null;                        //Dilosi metablitis tupou gps

            if (gps == null)
            {
                gps = new Gps();                  //Dimiourgia neou gps
                gps.Open();                       //Anoigma gps
            }
            else
            {
                gps.Close();                     //Eidalos kleinoume to Gps
                gps = null;
            }

            GpsPosition position = gps.GetPosition();     //Pernoume tis plirofories apo to gps

            lat = position.Latitude.ToString();           //Apothikeuoume to Latitude
            lon = position.Longitude.ToString();          //Apothikeuoume to Longitude

            if (gps != null)
                gps.Close();                              //Kleinoume ot gps

            location = lat + "|" + lon;                   //Ftiaxnoume to Sting (sumfona me ta protipo mas)
             
            return location;                              //To epistrefoume
        }



        public static void Dispatcher(string location)                 //H methodos Dispachet
        {
          
            // IP and Port
            string IP = "10.0.2.15";                                  //Metabliti pou periexei to IP
            int port = 4505;                                          //Metabliti pou periexei to Port

            // TCP connection
            TcpClient connection;                                     //Dilosi metablitis tupou TCPclient
            StreamWriter TCPwriter;                                   //Dilosi metablitis tupou StreamWriter

           

            try
            {
                connection = new TcpClient(IP, port);                //Dimiourgia sundesis me tou Client me ton Server
                NetworkStream stream = connection.GetStream();       //Dimiourgia Stream sundesis
           
                TCPwriter = new StreamWriter(stream);                //Dimiourgia StreamWriter sundesis  
                TCPwriter.WriteLine(location);                       //Graphimo tis plirofories sto Stream               

                TCPwriter.Flush();                                   //Kleisimo tou stream
                TCPwriter.Close();

                connection.Close();                                  //kleisomo tis sundesis
                 
            }
            catch (Exception ex)
            {
                MessageBox.Show("ERROR: " + ex.Message);             //Se periptosi lathous emfanise antistoixo minuma 
            }


        }//End of Dispacher

    }//End of programm

}//End of WM6Client