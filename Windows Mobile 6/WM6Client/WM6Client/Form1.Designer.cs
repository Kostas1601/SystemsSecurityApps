//Development Team
//Sofoklis Floratos        AM:3090198
//Kostas Xirogiannopoulos  AM:3090148

namespace WM6Client
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.MainMenu mainMenu1;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

     
        //Arxikopoihsh sustatikon UI
        private void InitializeComponent()
        {
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Font = new System.Drawing.Font("Tahoma", 12F, System.Drawing.FontStyle.Bold);
            this.button1.Location = new System.Drawing.Point(30, 13);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(181, 55);
            this.button1.TabIndex = 0;
            this.button1.Text = "Get Gps Location";
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.BackColor = System.Drawing.SystemColors.GrayText;
            this.button2.Font = new System.Drawing.Font("Tahoma", 11F, System.Drawing.FontStyle.Bold);
            this.button2.Location = new System.Drawing.Point(43, 142);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(156, 36);
            this.button2.TabIndex = 1;
            this.button2.Text = "Send Gps Location";
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(30, 75);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(181, 43);
            this.label1.Text = " ";
            this.label1.ParentChanged += new System.EventHandler(this.label1_ParentChanged);
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(43, 191);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(156, 50);
            this.label2.Text = " ";
            this.label2.ParentChanged += new System.EventHandler(this.label2_ParentChanged);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(240, 268);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Menu = this.mainMenu1;
            this.Name = "Form1";
            this.Text = "Windows Mobile 6 Gps Client";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button button1;//Dimiougia Get location button
        private System.Windows.Forms.Button button2;//Dimiougia Send location button
        private System.Windows.Forms.Label label1;//Dimiourgia Text field pliroforion topothesias
        private System.Windows.Forms.Label label2;//Dimiourgia Text field pliroforion apostolis
    }
}

