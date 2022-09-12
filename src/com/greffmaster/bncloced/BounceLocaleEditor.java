package com.greffmaster.bncloced;

import java.io.File;
import java.io.IOException;

public class BounceLocaleEditor {
	
	public static void main(String[] args) throws IOException {
		MainWindow window = new MainWindow();
		if(args.length > 0) window.loadFile(new File(args[0])); // Read file from argument
	}
}
