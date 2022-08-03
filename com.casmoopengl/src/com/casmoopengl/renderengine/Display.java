package com.casmoopengl.renderengine;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
import org.joml.Matrix4f;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */

public class Display {
	
	public static final float FOV = (float) Math.toRadians(60);
	public static final float  Z_NEAR  = 0.01f;
	public static final float  Z_FAR = 1000f;
	
	
	private final String baslık;
	
	private int width, height;
	private long window;
	
	
	private boolean resize, Vsync;
	
	
	
	private final Matrix4f projectionMatrix;



	public Display(String baslık, int width, int height, boolean vsync) {
		
		this.baslık = baslık;
		this.width = width;
		this.height = height;
		Vsync = vsync;
		projectionMatrix = new Matrix4f();
	}
	
	
	
	public void init(int GL_STENCIL_TEST) {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!GLFW.glfwInit())
			throw new IllegalStateException("hata abe");
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE , GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE , GL11.GL_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR , 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR , 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE,GLFW.GLFW_OPENGL_CORE_PROFILE);
		boolean maximised = false;
		
		
		if(width == 0 || height == 0) {
			width = 100;
			height = 100;
			
			GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
			maximised = true;
		}
		
		
		window = GLFW.glfwCreateWindow(width, height, baslık, MemoryUtil.NULL, MemoryUtil.NULL);
		
		if(window == MemoryUtil.NULL) //pc cop oldugundan hata çıkar
			throw new RuntimeException("abi pcni sikebilirmiyim");
		
		
		
		GLFW.glfwSetFramebufferSizeCallback(window , (window,width,height) -> {
			this.width = width;
			this.height = height;
			
			this.setResize(true);
			
		});
		
		GLFW.glfwSetKeyCallback(window, (window  , key ,scancode ,action ,mods) -> {
			
			if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
				GLFW.glfwSetWindowShouldClose(window, true);
			
			
		});
		
		if(maximised)
			GLFW.glfwMaximizeWindow(window);
		
		
		else {
			GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			
			GLFW.glfwSetWindowPos(window, (vidmode.width()  - width)  /2,
					(vidmode.height() - height) / 2);
			
		}
		
		GLFW.glfwMakeContextCurrent(window);
		
		
		if (isVsync())
			GLFW.glfwSwapInterval(1);
		
		
		GLFW.glfwShowWindow(window);
		
		GL.createCapabilities();
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		
		//GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glCullFace(GL11.GL_BACK);
		
		
		
		
		
		
		
	   
		
	}
	
	
	public void update() {
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}
	
	
	public void cleanup() {
		GLFW.glfwDestroyWindow(window);
	}
	
	
	public void setClearColour(float r, float g , float b , float a)
	{
		GL11.glClearColor(r, g, b, a);
	}
	
	public boolean isKeyPressed ( int keycode) {
		return GLFW.glfwGetKey(window , keycode)== GLFW.GLFW_PRESS;
	}
	
	
	public boolean windowclose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	
	
	
	

    public String getTitle() {
    	// TODO Auto-generated method stub
    	return baslık;
    }
    
    public void setTitle(String baslık) {
    	// TODO Auto-generated method stub
    	GLFW.glfwSetWindowTitle(window, baslık);
    }

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}



	public void setWidth(int width) {
		// TODO Auto-generated method stub
		this.width = width;
	}



	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}



	public void setHeight(int height) {
		// TODO Auto-generated method stub
		this.height = height;
	}



	public long getWindow() {
		// TODO Auto-generated method stub
		return window;
	}



	public void setWindow(long window) {
		// TODO Auto-generated method stub
		this.window = window;
	}



	public Matrix4f getProjectionMatrix() {
		// TODO Auto-generated method stub
		return projectionMatrix;
	}



	public boolean isVsync() {
		return Vsync;
	}



	public void setVsync(boolean vsync) {
		// TODO Auto-generated method stub
		Vsync = vsync;
	}



	public boolean isResize() {
		// TODO Auto-generated method stub
		return resize;
	}



	public void setResize(boolean resize) {
		// TODO Auto-generated method stub
		this.resize = resize;
	}
	
	
    public Matrix4f updateProjectionMatrix()
    {
    	float aspectratio = (float) width / height;
    	return  projectionMatrix.setPerspective(FOV,aspectratio, Z_NEAR, Z_FAR);
    	
    }
	
    
    public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width,int height) {
    	float aspectratio = (float) width/height;
    	return matrix.setPerspective(FOV, aspectratio, Z_NEAR, Z_FAR);
    }



	public long getWindowHandle() {
		// TODO Auto-generated method stub
		return window;
	}
	
	

}
