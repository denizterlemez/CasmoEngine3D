package com.casmoopengl.test;
import com.casmoopengl.renderengine.*;
import org.lwjgl.Version;
public class Launcher {
	private static Display window;
	/*
	 * CASMO ENGİNE ALPHA TESTİNG
	 * GPL LİCENSE 3.0
	 * Contact: caspersscasper0@gmail.com
	 * 
	 */

	private static TestRoom game;
	public static void main (String [] args) {
		System.out.println(Version.getVersion()+ "LWJGL SURUMU");
		
		
		window = new Display(Consts.baslik, 800 , 600, true);
		
		game = new TestRoom();
		EngineM engine = new EngineM();
		
		try {
			engine.start();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		window.init(0);
		while(!window.windowclose()) {
			window.update();
		}
		
		
		window.cleanup();
		
	}

	
	
	public static TestRoom getGame() {
		return game;
	}



	public static Display getWindow() {
		return window;
	
	}


	

}
