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
using System.Windows.Threading;

namespace GPSProject
{
    //Sthn klash SocketClient ylopoieitai thn dynesh TCP me ton server kathws kai oi methodos gia thn apostolh twn dedomenwn topothesias ston server
    public class SocketClient

    {
        // To antikeimeno typoou Socket pou tha xrhsimopoihthei gia thn syndesh
        Socket _socket = null;

        // Antikeimeno pou tha xrhsimopoihthei gia na mas enhmerwsei pote h ascynchronous operation tha oloklhrwthei 
        ManualResetEvent _clientDone = new ManualResetEvent(false);

        //Dhlwnoume enan xrono timeout se miliseconds, gia kathe asychronous klhsh ean den dexthei apanthsh  
        // se auton ton xrono h klhsh akyrwnetai 
        const int TIMEOUT_MILLISECONDS = 5000;

        //Dhlwnoume thn megisto megethos tou data buffer pou tha xrhsimopoihthei me tis asynchronous methodous twn sockets
        const int MAX_BUFFER_SIZE = 2048;


       //H methodos pou ylopoiei thn apopeira syndeshs me ton server
        public string Connect(string hostName, int portNumber)
        {


            string result = string.Empty;

            //Dhmiourgia DnsEndPoint, to IP tou host kai o arithmos tou port perniountai ws parametroi.
            DnsEndPoint hostEntry = new DnsEndPoint(hostName, portNumber);

            //Dhmiourgia enos stream based TCP socket gia thn syndesh 
            _socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            //Dhmiourgia enos antikeimenou SocketAsyncEventArgs pou xrhsimopoieitai sto aithma syndeshs me ton server 
            SocketAsyncEventArgs socketEventArg = new SocketAsyncEventArgs();
            socketEventArg.RemoteEndPoint = hostEntry;

            // Dhmiourgia enos inline handler gia aithma syndeshs me ton server
            socketEventArg.Completed += new EventHandler<SocketAsyncEventArgs>(delegate(object s, SocketAsyncEventArgs e)
            {
                //To apotelesma ths aithshs auths
                result = e.SocketError.ToString();
                


                //Eidopoiei to UI oti h aithsh oloklhrwthike kai to kanei unblock
                _clientDone.Set();
            });

            //Thetei to state tou event se nonsignaled , mplokarontas ta threads
            _clientDone.Reset();

            //Kanei thn asynchronh aithsh syndeshs(Asynchronous connect request) mesw tou socket 
            _socket.ConnectAsync(socketEventArg);

            //Mplokarei to thread tou UI gia to poly 5000 miliseconds
            //Ean den dexthei antapokrisi, mesa ston xrono auto, tote synexizei
            _clientDone.WaitOne(TIMEOUT_MILLISECONDS);

            return result;
        }

        //H methodos pou ylopoiei thn apostolh twn dedomenwn ston server
        public string Send(string data)
        {
            string response = "Operation Timeout";

            //Ksanaxrhsimopoioume to antikeimeno typou Socket pou arxikopoihsame sthn methodo Connect
            if (_socket != null)
            {
                // Dhmiourgia enos antikeimenou SocketAsyncEventArgs 
                SocketAsyncEventArgs socketEventArg = new SocketAsyncEventArgs();

                // Thetoume tis idiothtes gia to antikeimeno ayto
                socketEventArg.RemoteEndPoint = _socket.RemoteEndPoint;
                socketEventArg.UserToken = null;

                // Dhlwsh enos Inline even handler gia to "Completed" event                 
                socketEventArg.Completed += new EventHandler<SocketAsyncEventArgs>(delegate(object s, SocketAsyncEventArgs e)
                {
                    response = e.SocketError.ToString();

                    // Ksemplokarei to UI Thread
                    _clientDone.Set();
                });

                //Prosthetei ta dedomena pou theloume na steiloume mesa se enan buffer
                byte[] payload = Encoding.UTF8.GetBytes(data);
                socketEventArg.SetBuffer(payload, 0, payload.Length);

                // Thetei to event se nonsignaled state, mplokarontas etsi ta threads
                _clientDone.Reset();

                // Kanei ena asygxrono aithma apostolhs( asynchronous Send request) mesw tou socket
                _socket.SendAsync(socketEventArg);

                // Mplokarei to UI thread to poly gia 5000 milisecods 
                // Ean mexri tote den exei lavei apanthsh synexize
                _clientDone.WaitOne(TIMEOUT_MILLISECONDS);
            }
            else
            {
                response = "Socket is not initialized";
            }

            return response;
        }
   
        // Kleinei thn syndesh tou Socket kai apeleutherwnei olous tous sxetikous porous   
        public void Close()
        {
            if (_socket != null)
            {
                _socket.Close();
            }
        }


    }
}
