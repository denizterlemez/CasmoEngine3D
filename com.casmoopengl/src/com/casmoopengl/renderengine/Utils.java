package com.casmoopengl.renderengine;

import org.lwjgl.system.MemoryUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */
public class Utils {

	public static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static IntBuffer storeIntBuffer(int[] data) {
		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	
	
    public static String loadres(String filename) throws Exception{
    	String result;
    	try(InputStream in = new FileInputStream(filename);
    			Scanner scanner = new Scanner(in , StandardCharsets.UTF_8.name())){
    		result = scanner.useDelimiter("\\A").next();
    	}
    	return result;
    			
    }
	
    public static List<String> readalllines(String filename){
    	List<String> list = new ArrayList<>();
    	try(BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(filename)))){
    		String line;
    		while((line = br.readLine()) != null) {
    			list.add(line);
    			
    		}
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	return list;
    }

	
    
	
}
