package com.greffmaster.bncloced;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileExplorer extends JFrame {

	private static final long serialVersionUID = 1L;
	public JFrame fframe;
	private MainWindow mainwin;
	private JFileChooser fpicker;
	
	private int mode = 0;
	
	public FileExplorer(MainWindow main) {
		this.mainwin = main;
		
		this.fframe = new JFrame();
		this.fframe.setSize(600,400);
		this.fframe.setTitle("Pick a file");
		this.fframe.setAlwaysOnTop(true);
		
		this.fpicker = new JFileChooser();
		this.fpicker.setBounds(fframe.getBounds());
		this.fpicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String cmd = event.getActionCommand();
				//System.out.println(cmd);
				if(cmd == "CancelSelection")
				{
					fframe.setVisible(false);
					mainwin.setEnabled(true);
					return;
				}
				
				if(cmd == "ApproveSelection")
				{
					File file = fpicker.getSelectedFile();
					//System.out.println("Selected: "+file.getName());
					fframe.setVisible(false);
					mainwin.setEnabled(true);
					if(mode==0) {
						try {
							mainwin.loadFile(file);
						} catch (Exception ex) {}
						return;
					}
					try {
						mainwin.saveFile(file);
					} catch (Exception ex) {}
					return;
				}
				
			}});
		
		this.fframe.add(this.fpicker);
		
		this.fframe.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent)
		    {
		        mainwin.setEnabled(true);
		    }
		});
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		this.fframe.setVisible(visible);
		// Disable main window when choosing a file
		if(this.fframe.isVisible()) this.mainwin.setEnabled(false);
	}
	
	public void setMode(int modeInt)
	{
		this.mode = modeInt;
		if(this.mode == 1)
		{
			this.fframe.setTitle("Save file");
			this.fpicker.showSaveDialog(fframe);
			return;
		}
		this.fframe.setTitle("Load file");
		this.fpicker.showOpenDialog(fframe);
	}

}
