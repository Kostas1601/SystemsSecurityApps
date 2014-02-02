//Development Team Kostas Xirogiannopoulos  AM:3090148, Sofoklis Floratos  AM:3090198
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using System.Device.Location;
using System.Threading;
using System.Net.Sockets;
using System.Text;


namespace GPSProject
{
    public partial class MainPage : PhoneApplicationPage
    {
        //Antikeimeno watcher pou xeirizetai ta events
        GeoCoordinateWatcher watcher;
        bool trackingOn = false;
        bool sent = false;

        //O kwdikas einai parametropoihsimos ws pros to IP kai to Port number tou server, ws IP prepei na mpei to public IP tou server,
        // h efarmogh den katalavainei "localhost","127.0.0.1" h "10.0.2.2" ean o server trexei sto local mixanhma
        const int ECHO_PORT = 4505;
        const String IP = "192.168.1.107";

        public MainPage()
        {
            InitializeComponent();
            //Dhmiourgei enan watcher thetontas to epipedo akriveias (twn syntetagmenwn pou tha lavoume apo to GPS)
            watcher = new GeoCoordinateWatcher(GeoPositionAccuracy.High); //xrhsimopoieitai ypsili akriveia           
            // Dhmiourgoume ta even handlers
            watcher.StatusChanged += new
            EventHandler<GeoPositionStatusChangedEventArgs>(watcher_StatusChanged);

            // Ksekinaei to to Location service to opoio trexei sto backgroud kaleitai to event watcher_StatusChanged otan oloklhrwthei h ektelesh tou service
            new Thread(startLocServInBackground).Start();
            statusTextBlock.Text = "Starting Location Service...";


        }
        /*------------------------------------------------------------------------------------------------------------------------------*/

        //Ksekinaei o location service
        void startLocServInBackground()
        {
            watcher.TryStart(true, TimeSpan.FromMilliseconds(10000));
        }
        //kaleitai otan allazei to status prokeimenou na allaksei ta periexomena tou statusTextBlock
        void watcher_StatusChanged(object sender, GeoPositionStatusChangedEventArgs e)
        {
            switch (e.Status)
            {
                case GeoPositionStatus.Disabled:
                    // Ean to Location Service einai apenergopoihmeno h ean den yposthrizetai                   
                    if (watcher.Permission == GeoPositionPermission.Denied)
                    {                        
                        statusTextBlock.Text = "You have disabled Location Service.";
                    }
                    else
                    {
                        statusTextBlock.Text = "Location Service is not functioning on this device.";
                    }
                    break;
                case GeoPositionStatus.Initializing:
                    statusTextBlock.Text = "Location Service is retrieving data...";
                    // alliws ksekinaei to location service
                    break;
                case GeoPositionStatus.NoData:
                    // Ean den mporei na lavei ta dedomena topothesias apo to GPS
                    statusTextBlock.Text = "Location data is not available.";
                    break;
                case GeoPositionStatus.Ready:
                    // Alliws, ean to Locatino Service leitourgei kanonika
                    statusTextBlock.Text = "Location data is available.";
                    break;
            }
        }

        //Gia thn antlhsh twn syntetagmenvn topothesias kaleitai auth h methodos
        // GPS Collector
        void watcher_PositionChanged(object sender, GeoPositionChangedEventArgs<GeoCoordinate> e) 
        {

            // Enhmerwnei ta textblocks ths othonhs me ta dedomena GPS
            latitudeTextBlock.Text = e.Position.Location.Latitude.ToString("0.0000000000000");
            longitudeTextBlock.Text = e.Position.Location.Longitude.ToString("0.0000000000000");
            if (!sent)
            {
                //Dookimazei na steilei ta dedomena ston server,ean h apostolh einai epityxhs
                if (GPSend(latitudeTextBlock.Text, longitudeTextBlock.Text))
                {
                    //Enhmerwnetai analogws to statusTextBlock
                    statusTextBlock.Text = "GPS Data sent!";
                    //Kai emfanizetai notification sthn othonh
                    Dispatcher.BeginInvoke(() => MessageBox.Show("GPS data has been sent! :)"));
                    sent = true;
                }

            }
            //Apenergopoieitai o watcher 
            watcher.Stop();
        }
        //To event handler tou kompiou Send GPS Data
        private void startStop_Click(object sender, RoutedEventArgs e)
        {
            //Enhmerwnotnai ta antistoixa textblocks kai emfanizotnai notifications analoga me to periexomeno tou statusBlock
            if (startStop.Content.ToString() == "Send GPS Data")
            {
                if (statusTextBlock.Text == "Sending GPS Data...")
                {
                    if (statusTextBlock.Text.CompareTo("Location Service is retrieving data...") == 0)
                    {
                        Dispatcher.BeginInvoke(() => MessageBox.Show("Data cannot be sent, please check you firewall"));
                        watcher.Stop();
                        return;
                    }

                }
                else if (statusTextBlock.Text == "GPS Data sent!" || statusTextBlock.Text == "Location Service is retrieving data...")
                {
                    Dispatcher.BeginInvoke(() => MessageBox.Show("Your Location has already been sent!"));
                    watcher.Stop();
                    return;
                }
                //Dhmiourgeitai ena neo event handler kai ksekinaei to thread gia to location service
                watcher.PositionChanged += new
            EventHandler<GeoPositionChangedEventArgs<GeoCoordinate>>(watcher_PositionChanged);
                statusTextBlock.Text = "Sending GPS Data...";
                watcher.Stop();
                new Thread(startLocServInBackground).Start();

            }
        }

        /**SOCKETS----------------------------------------------------------------------------------------SOCKETS**/
        //H methodos pou kalei methodous ths klash SocketClient mesw twn opoivn ylopoiei thn syndesh kai thn apostolh twn GPS dedomenwn
        //Dispatcher
        public bool GPSend(String lat, String longt)
        {
            
            // Dhmiourgei ena neo antikeimeno typou SocketClient
            SocketClient client = new SocketClient();

            // Ftiaxnei to mynima sthn morfh pou to theloume
            String message = lat + "|" + longt;
            string succ = "Success";

            // Kanei apopeira syndeshs me ton server           
            string result = client.Connect(IP, ECHO_PORT);

            //Ean h syndesh den einai epityxhs
            if (result.CompareTo(succ) != 0)
            {
                //Emfanizetai notification me antistoixo mynima
                Dispatcher.BeginInvoke(() => MessageBox.Show(result + "Please check your firewall"));
                return false;
            }

            // Alliws  kanoume apopeira na steiloume to mynima ston server kalwntast thn methodo Send() sto antikeimeno typou SocketClient;              
            result = client.Send(message);

            // Kleinoume thn syndesh 
            client.Close();
            return true;
                       
        }
        
    }
}
