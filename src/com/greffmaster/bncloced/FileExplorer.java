package com.greffmaster.bncloced;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileExplorer extends JFrame {

	private static final long serialVersionUID = 1L;
	private MainWindow mainwin;
	private JFileChooser fpicker;
	private int mode = 0;
	
	public FileExplorer(MainWindow main) {
		super();
		this.mainwin = main;
		this.setSize(600,400);
		this.setAlwaysOnTop(true);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent)
		    {
		        mainwin.setEnabled(true);
		    }
		});
		
		this.fpicker = new JFileChooser();
		
		this.fpicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String cmd = event.getActionCommand();
				if(cmd == "CancelSelection")
				{
					mainwin.closeFileExplorer();
					return;
				}
				if(cmd == "ApproveSelection")
				{
					File file = fpicker.getSelectedFile();
					mainwin.closeFileExplorer();
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
			}
		});
		
		this.add(this.fpicker);
	}
	
	public void openFileExplorer(int mode)
	{
		this.mode = mode; // 0 - load, 1 - save
		
		if(this.mode == 1)
		{
			this.fpicker.showSaveDialog(this);
			return;
		}
		
		this.fpicker.showOpenDialog(this);
	}

}
