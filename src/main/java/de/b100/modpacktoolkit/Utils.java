package de.b100.modpacktoolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static String loadFile(String path) {
		return loadFile(new File(path));
	}
	
	public static String loadFile(File file) {
		return read(file);
	}
	
	public static String loadFile(InputStream inputStream) {
		return read(inputStream);
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
	
	public static String clipWhitespace(String string) {
		int startIndex = 0;
		int endIndex = string.length();
		for(int i=0; i < string.length(); i++) {
			if(!isWhitespace(string.charAt(i))) {
				startIndex = i;
				break;
			}
		}
		for(int i = string.length() - 1; i >= startIndex; i--) {
			if(!isWhitespace(string.charAt(i))) {
				endIndex = i+1;
				break;
			}
		}
		return string.substring(startIndex, endIndex);
	}
	
	public static boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n';
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getAllObjects(Class<?> clazz, Class type, Object object, boolean throwException){
		List list = new ArrayList<>();
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			if(field.getType() == type) {
				Object obj;
				try {
					obj = field.get(object);
					
					list.add(obj);
				} catch (Exception e) {
					if(throwException)throw new RuntimeException(e);
				}
			}
		}
		
		return list;
	}
}
