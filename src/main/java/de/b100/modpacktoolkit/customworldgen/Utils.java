package de.b100.modpacktoolkit.customworldgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Utils {
	
	public static String loadFile(File file) {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			
			String str = "";
			boolean first = true;
			while(true) {
				String line = br.readLine();
				
				if(line != null) {
					if(!first)str += '\n';
					
					str += line;
				}else {
					break;
				}
				
				first = false;
			}
			
			br.close();
			
			return str;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getFileExtension(File file) {
		return getFileExtension(file.getAbsolutePath());
	}
	
	public static String getFileExtension(String path) {
		String ext = "";
		char[] ca = path.toCharArray();
		boolean a = false;
		
		for(int i=0; i < ca.length; i++) {
			ext += ca[i];
			
			if(ca[i] == '/' || ca[i] == '\\') {
				a = false;
			}
			if(ca[i] == '.') {
				a = true;
				ext = "";
			}
			
		}
		
		return a ? ext : null;
	}
	
	public static String getFilenameWithoutExtension(File file) {
		return getFilenameWithoutExtension(file.getName());
	}
	
	public static String getFilenameWithoutExtension(String filename) {
		String ext = getFileExtension(filename);
		return filename.substring(0, filename.length() - ext.length() - 1);
	}
	
}
