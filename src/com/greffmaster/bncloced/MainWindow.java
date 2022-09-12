package com.greffmaster.bncloced;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public JFrame frame;
	private FileExplorer fexplorer;
	
	private Color gray;
	private Color lgray;
	private Color white;
	private LineBorder border;
	
	private String localizedStrings[] = {"","","","","","","","","","","","",""};
	private short localizedStringsLen[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};

	public MainWindow() {
		this.frame = new JFrame();
		this.gray = new Color(32,32,32);
		this.lgray = new Color(64,64,64);
		this.white = new Color(255,255,255);
		this.border = new LineBorder(gray, 1, false);
		
		this.frame.setSize(400,800);
		this.frame.setResizable(false);
		this.frame.setTitle("Bounce Locale Editor");
		this.frame.setLayout(null);
		this.frame.getContentPane().setBackground(gray);
		
		this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.exit(0);
		    }
		});
		
		JLabel title = new JLabel("Bounce Locale Editor");
		title.setBounds(0, 0, 400, 20);
		title.setHorizontalAlignment(0);
		title.setForeground(white);
		
		JLabel instr_label = new JLabel("Instructions Form");
		instr_label.setBounds(20, 10, 400, 40);
		instr_label.setForeground(white);
		
		JTextArea instr_field = new JTextArea();
		instr_field.setBounds(20, 40, 360, 140);
		instr_field.setLineWrap(true);
		instr_field.setForeground(white);
		instr_field.setBackground(lgray);
		instr_field.setName("Instructions Form");
		
		JScrollPane instr_txt = new JScrollPane(instr_field);
		instr_txt.setBounds(20, 40, 360, 140);
		instr_txt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		instr_txt.setBorder(border);
		
		this.frame.add(title);
		this.frame.add(instr_label);
		this.frame.add(instr_txt);
		
		createTextElement("Back",               20,200,360,20);
		createTextElement("Congrats!",          20,240,360,20);
		createTextElement("Continue",           20,280,360,20);
		createTextElement("Exit",               20,320,360,20);
		createTextElement("Game over",          20,360,360,20);
		createTextElement("Hight score",        20,400,360,20);
		createTextElement("Instructions",       20,440,360,20);
		createTextElement("Level %U",           20,480,360,20);
		createTextElement("Level %U completed!",20,520,360,20);
		createTextElement("New game",           20,560,360,20);
		createTextElement("New high score!",    20,600,360,20);
		createTextElement("OK",                 20,640,360,20);
		
		this.fexplorer = new FileExplorer(this);
		
		JButton openfile = new JButton("Open");
		openfile.setBounds(20, 680, 100, 20);
		openfile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fexplorer.setMode(0);
			}
		});
		
		
		JButton savefile = new JButton("Save");
		savefile.setBounds(280, 680, 100, 20);
		savefile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fexplorer.setMode(1);
			}
		});
		
		this.frame.add(openfile);
		this.frame.add(savefile);
		
		this.frame.setVisible(true);
	}
	
	private void createTextElement(String labelStr,int x,int y,int w,int h)
	{
		JLabel label = new JLabel(labelStr);
		label.setBounds(x, y-30, w, 40);
		label.setForeground(white);
		
		JTextField field = new JTextField();
		field.setBounds(x, y, w, h);
		field.setForeground(white);
		field.setBackground(lgray);
		field.setBorder(border);
		field.setName(labelStr);
		
		this.frame.add(label);
		this.frame.add(field);
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		this.frame.setEnabled(enabled);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		this.frame.setVisible(visible);
	}
	
	public void loadFile(File file) throws IOException
	{
		// Read from locale file and put data into arrays
		FileInputStream stream = new FileInputStream(file);
		stream.skip(0x24);
		for(int count=0;count<13;count++)
		{
			this.localizedStringsLen[count] = FileStreamUtils.readShort(stream, true);
			this.localizedStrings[count] = "";
			for(int i=0; i<this.localizedStringsLen[count]; i++)
			{
				this.localizedStrings[count] += (char) stream.read();
			}
		}
		stream.close();
		// Set text fields values
		JScrollPane scrolk = (JScrollPane) this.frame.getContentPane().getComponent(2);
		JViewport view = scrolk.getViewport();
		((JTextArea)view.getView()).setText(this.localizedStrings[0]);
		
		Component[] components = this.frame.getContentPane().getComponents();
		int iter = 1;
		for(Component comp : components) {
		    if (comp instanceof JTextField) { 
		        ((JTextField)comp).setText(this.localizedStrings[iter]);
		        iter++;
		    }
		}
	}
	
	public void saveFile(File file) throws IOException
	{
		// Get text fields values and put data into arrays
		JScrollPane scrolk = (JScrollPane) this.frame.getContentPane().getComponent(2);
		JViewport view = scrolk.getViewport();
		this.localizedStrings[0] = ((JTextArea)view.getView()).getText();
		this.localizedStringsLen[0] = (short) this.localizedStrings[0].length();

		Component[] components = this.frame.getContentPane().getComponents();
		int iter = 1;
		for(Component comp : components) {
		    if (comp instanceof JTextField) { 
		    	this.localizedStrings[iter] = ((JTextField)comp).getText();
		    	this.localizedStringsLen[iter] = (short) this.localizedStrings[iter].length();
		        iter++;
		    }
		}
		// Write data into file
		FileOutputStream stream = new FileOutputStream(file);
		// 4 bytes of yet unknown purpose
		stream.write(0x00);
		stream.write(0x1C);
		stream.write(0x00);
		stream.write(0x24);
		// String sizes header
		int size = 0;
		for(int i=0;i<12;i++)
		{
			size += this.localizedStringsLen[i];
			FileStreamUtils.writeShort(stream, (short)(36+(2*(i+1))+size), true);
		}
		// Write "Bounce" entry
		FileStreamUtils.writeShort(stream, (short)(6), true);
		FileStreamUtils.writeString(stream, "Bounce", true);
		// Write all entries
		for(int i=0;i<13;i++)
		{
			FileStreamUtils.writeShort(stream, (short)this.localizedStringsLen[i], true);
			FileStreamUtils.writeString(stream, this.localizedStrings[i], true);
		}
		stream.close();
	}

}
