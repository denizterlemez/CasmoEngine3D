package com.casmoopengl.renderengine;
import org.joml.Vector2d;
import org.joml.Vector2f;

import org.lwjgl.glfw.*;
import org.lwjgl.system.linux.MMAN;

import com.casmoopengl.test.Launcher;

/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */

public class Mouse {
	
	
	private final Vector2d prepos, curpos;
	private Vector2f dispvec;
	private boolean inwin = false , leftprs= false, righprs = false;
	
	
	public Mouse() {
		prepos = new Vector2d(-1,-1);
		curpos = new Vector2d (0,0);
		dispvec = new Vector2f();
		
		
		
		
		
	}
	
	public void init() {
		
		GLFW.glfwSetCursorPosCallback(Launcher.getWindow().getWindowHandle(), (window , xpos,ypos) ->{
			curpos.x = xpos;
			curpos.y = ypos;
		});
		
		GLFW.glfwSetCursorEnterCallback(Launcher.getWindow().getWindowHandle(), (window,entered)   ->{
			 inwin = entered;
			
			
		});
		
		
		GLFW.glfwSetMouseButtonCallback(Launcher.getWindow().getWindowHandle(), (window,button,action,mods)   ->{
			 leftprs = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
			 righprs = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
			 
			 
			
			
		});
		
		
		
		
		
	}
	
	public void input() {
		dispvec.x = 0;
		dispvec.y = 0;
		
		
		
		if(prepos.x > 0 && prepos.y > 0 && inwin) {
			double x = curpos.x - prepos.x;
			double y = curpos.y - prepos.y;
			boolean rotateX = x != 0;
			
			boolean rotateY = y != 0;
			
			
			
			if(rotateX)
				dispvec.y = (float) x;
			
			if(rotateY)
				dispvec.x = (float) y;
			
			
			
			
		}
		
		prepos.x = curpos.x;
		prepos.y = curpos.y;
	}

	public Vector2f getDispvec() {
		return dispvec;
	}

	public void setDispvec(Vector2f dispvec) {
		this.dispvec = dispvec;
	}

	public boolean isLeftprs() {
		return leftprs;
	}

	public void setLeftprs(boolean leftprs) {
		this.leftprs = leftprs;
	}

	public boolean isRighprs() {
		return righprs;
	}

	public void setRighprs(boolean righprs) {
		this.righprs = righprs;
	}
	
	
	
	
	

}
