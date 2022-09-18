package com.greffmaster.bncloced;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private FileExplorer fexplorer;
	private Color gray;
	private Color lgray;
	private Color white;
	private LineBorder border;
	// Strings and their lengths
	private String localizedStrings[] = {"","","","","","","","","","","","",""};
	private short localizedStringsLen[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};

	public MainWindow(String title) {
		super(title);
		this.gray = new Color(32,32,32);
		this.lgray = new Color(64,64,64);
		this.white = new Color(255,255,255);
		this.border = new LineBorder(gray, 1, false);
		
		this.setSize(400,800);
		this.setResizable(false);
		this.setLayout(null);
		this.getContentPane().setBackground(gray);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.exit(0);
		    }
		});
		
		JLabel title_label = new JLabel("Bounce Locale Editor");
		title_label.setBounds(0, 0, 400, 20);
		title_label.setHorizontalAlignment(0);
		title_label.setForeground(white);
		
		JLabel instr_label = new JLabel("Instructions Form");
		instr_label.setBounds(20, 10, 400, 40);
		instr_label.setForeground(white);
		
		JTextArea instr_field = new JTextArea();
		instr_field.setBounds(20, 40, 360, 140);
		instr_field.setLineWrap(true);
		instr_field.setForeground(white);
		instr_field.setBackground(lgray);
		instr_field.setName("Instructions Form");
		instr_field.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent arg0) {
				setTitle("Bounce Locale Editor*");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setTitle("Bounce Locale Editor*");
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setTitle("Bounce Locale Editor*");
			}
		});
		
		JScrollPane instr_txt = new JScrollPane(instr_field);
		instr_txt.setBounds(20, 40, 360, 140);
		instr_txt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		instr_txt.setBorder(border);
		
		this.add(title_label);
		this.add(instr_label);
		this.add(instr_txt);
		
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
				fexplorer.openFileExplorer(0);
			}
		});
		
		
		JButton savefile = new JButton("Save");
		savefile.setBounds(280, 680, 100, 20);
		savefile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				fexplorer.openFileExplorer(1);
			}
		});
		
		this.add(openfile);
		this.add(savefile);
		// Show window
		this.setVisible(true);
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
		field.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent arg0) {
				setTitle("Bounce Locale Editor*");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setTitle("Bounce Locale Editor*");
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setTitle("Bounce Locale Editor*");
			}
		});
		
		this.add(label);
		this.add(field);
	}
	
	public void loadFile(File file) throws IOException
	{
		// Read from locale file and put data into arrays
		FileInputStream stream = new FileInputStream(file);
		// Test the first 4 bytes to check if its an actual bounce locale
		short testval1 = FileStreamUtils.readShort(stream, true);
		short testval2 = FileStreamUtils.readShort(stream, true);
		if(testval1 != 28 || testval2 != 36)
		{
			JOptionPane.showMessageDialog(null, "The provided file is not a Bounce locale data!", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// Skip string position header
		stream.skip(0x20);
		// Read data
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
		JScrollPane scrolk = (JScrollPane) this.getContentPane().getComponent(2);
		JViewport view = scrolk.getViewport();
		((JTextArea)view.getView()).setText(this.localizedStrings[0]);
		
		Component[] components = this.getContentPane().getComponents();
		int iter = 1;
		for(Component comp : components) {
		    if (comp instanceof JTextField) { 
		        ((JTextField)comp).setText(this.localizedStrings[iter]);
		        iter++;
		    }
		}
		this.setTitle("Bounce Locale Editor");
	}
	
	public void saveFile(File file) throws IOException
	{
		// Get text fields values and put data into arrays
		JScrollPane scrolk = (JScrollPane) this.getContentPane().getComponent(2);
		JViewport view = scrolk.getViewport();
		this.localizedStrings[0] = ((JTextArea)view.getView()).getText();
		this.localizedStringsLen[0] = (short) this.localizedStrings[0].length();

		Component[] components = this.getContentPane().getComponents();
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
		FileStreamUtils.writeString(stream, "Bounce");
		// Write all entries
		for(int i=0;i<13;i++)
		{
			FileStreamUtils.writeShort(stream, (short)this.localizedStringsLen[i], true);
			FileStreamUtils.writeString(stream, this.localizedStrings[i]);
		}
		stream.close();
		this.setTitle("Bounce Locale Editor");
	}
	
	public void closeFileExplorer()
	{
		fexplorer.setVisible(false);
		this.setEnabled(true);
	}

}
