package com.casmoopengl.renderengine;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */
import org.lwjgl.glfw.GLFW;

import org.lwjgl.glfw.GLFWErrorCallback;
import com.casmoopengl.*;
import com.casmoopengl.test.Launcher;

public class EngineM {
	
	
	public static final long NANOSECOND = 1000000000L;
	public static final float 	FRAMERATE = 1000;
	
	private static int fps;
	private static float frametime = 1.0f/ FRAMERATE;
	
	
	
	private boolean isRunning;
	private Display window;
	private Mouse mouse;
	private GLFWErrorCallback errcallback;
	private Logic gamelogic;
	
	private void init() throws Exception {
		GLFW.glfwSetErrorCallback(errcallback = GLFWErrorCallback.createPrint(System.err));
		window = Launcher.getWindow();
		gamelogic = Launcher.getGame();
		mouse= new Mouse();
		window.init(0);
		gamelogic.init();
		mouse.init();
	}
	
	public void start() throws Exception {
		init();
		if(isRunning)
			return;
		run();
	}
	public void run() {
		this.isRunning  =true;
		int frames = 0;
		long frameCounter= 0;
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;
		
		
		while(isRunning) {
			boolean render = false;
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) NANOSECOND;
			frameCounter += passedTime;
			
			
			input();
			
			while(unprocessedTime > frametime) {
				render = true;
				unprocessedTime -= frametime;
				
				if(window.windowclose())
					stop();
				
				
				if(frameCounter >= NANOSECOND) {
					setFps(frames);
					window.setTitle(Consts.baslik + getFps());
					frames = 0;
					frameCounter = 0;
				}
			}
			
			if(render) {
				update();
				render();
				frames++;
			}
			
			
			
		}
		cleanup();
		
	}
	
public void update() {
		float interval = 0;
	// TODO Auto-generated method 
	gamelogic.update(interval , mouse);
		
	}

public void stop() {
	if(!isRunning)
		return;
	isRunning = false;
		
	}

public void input() {
	mouse.input();
	gamelogic.input();
}


public void render() {
	gamelogic.render();
	window.update();
}


private void cleanup() {
	
	window.cleanup();
	errcallback.free();
	GLFW.glfwTerminate();
	
}

public static int getFps() {
	// TODO Auto-generated method stub
	return fps;
}

public static void setFps(int fps) {
	// TODO Auto-generated method stub
	EngineM.fps = fps;
}
}





