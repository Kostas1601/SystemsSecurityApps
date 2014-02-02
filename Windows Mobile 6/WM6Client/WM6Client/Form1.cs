//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148

using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace WM6Client
{
    public partial class Form1 : Form
    {
        private string location;

        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {           
            location = Program.Gps_collection();
            label1.Text = "Your Current Location is: "+location;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Program.Dispatcher(location);
            label2.Text = "You Loaction has been sent.....";

        }

        private void label1_ParentChanged(object sender, EventArgs e)
        {
           
        }

        private void label2_ParentChanged(object sender, EventArgs e)
        {

        }

       
    }
}