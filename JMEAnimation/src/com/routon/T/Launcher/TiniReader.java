package com.routon.T.Launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import android.util.Log;

public class TiniReader {
	private Map<String, Properties> sections;
	private String fileName;
	private String secion;
	private Properties properties;

	
	public TiniReader(String FileName) {
		this.fileName = FileName;
		sections = new HashMap<String, Properties>();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			read(reader);
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void read(BufferedReader reader) throws IOException {	
		String line;
		while ((line = reader.readLine()) != null) {
			parseLine(line);
		}
	}
	
	private void parseLine(String line) {
		line = line.trim();
		if (line.matches("\\[.*\\]") == true) {
			secion = line.replaceFirst("\\[(.*)\\]", "$1");
			properties = new Properties();
			sections.put(secion, properties);
		} else {
			 if(properties != null && !line.startsWith(";") && !line.startsWith("#") && !line.isEmpty()){           	
	                int i = line.indexOf('=');
		            	if(i > 0 && i <= line.length() - 1)
		                {
			                String name = line.substring(0, i).trim();
			                String value = null;
			                if(i < line.length() - 1)
			                {
			                	value = line.substring(i+1).trim();
			                }
			                else
			                {
			                	value = "\r";
			                }
			                properties.setProperty(name, value);  
		                } 
	            }  
	        }  
	}

	public String getValue(String section, String name)
	{
		Properties p = sections.get(section);

		if (p == null) {
			return null;
		}

		return p.getProperty(name);

	}
	
	public void SetValue(String section, String name, String value)
	{
		Properties p = sections.get(section);
		if(p == null)
		{
			Properties newpro = new Properties();
			newpro.setProperty(name, value);
			sections.put(section, newpro);
		}
		else
		{
			p.setProperty(name, value);
		}
	}
	
	//modified by wukai 2012.01.21
	//private  boolean write() {
	public boolean write() {
		PrintWriter writer = null;
		try {
			File f = new File(this.fileName);
			if (!f.exists()) {
				// create file.
				f.createNewFile();
			}
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f)));
			StringBuilder builder = new StringBuilder();
			Iterator<String> iterator = sections.keySet().iterator();
			while (iterator.hasNext()) {
				String sectionName = iterator.next();
				Properties p = (Properties)sections.get(sectionName);
				builder.append("\n").append("\n").append("[").append(sectionName).append("]");
				builder.append(getPropertiesString(p));
				Log.i("iniReader", builder.toString());
			}
			writer.write(builder.toString().trim());
			writer.flush();
			writer.close();
			return true;
		} catch (Exception e) { 
			e.printStackTrace();
			Log.e("EncryptBox", "INIReader: write error(" + this.fileName + ").");
		} 
		if (writer != null) {
			writer.close();
		}
		return false;
	}
	
	private String getPropertiesString(Properties p) {
		   StringBuilder builder = new StringBuilder();
		   Iterator iterator = p.keySet().iterator();
		   while (iterator.hasNext()) {
		     String key = iterator.next().toString();
		     builder.append("\n").append(key).append("=").append(p.get(key));
		   }
		   return builder.toString();
		}
	
	 public Properties getProperties(String section){
		         Properties p = sections.get(section);
		         if(p != null){
		               return p;
		          }
		         return null;
     }

}
