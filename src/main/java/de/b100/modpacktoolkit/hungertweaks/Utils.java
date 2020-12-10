package de.b100.modpacktoolkit.hungertweaks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
	
	public static String loadFile(String path) {
		return loadFile(new File(path));
	}
	
	public static String loadFile(File file) {
		return read(file);
	}
	
	private static String read(Object object) {
		BufferedReader br = null;
		
		try {
			if(object instanceof File) {
				File file = (File) object;
				br = new BufferedReader(new FileReader(file));
			}
			if(object instanceof InputStream) {
				InputStream inputStream = (InputStream) object;
				br = new BufferedReader(new InputStreamReader(inputStream));
			}
			
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
	
	public static void saveFile(String path, String string) {
		saveFile(new File(path), string);
	}
	
	public static void saveFile(File file, String string) {
		try {
			createFile(file);
			
			FileWriter fw = new FileWriter(file);
			
			fw.write(string);
			
			fw.close();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void createFile(File file) {
		File parent = file.getAbsoluteFile().getParentFile();
		if(!parent.exists()) {
			parent.mkdirs();
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
			}catch (Exception e) {
				throw new RuntimeException();
			}
		}
	}

}
