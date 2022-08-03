package com.casmoopengl.test;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.casmoopengl.renderengine.*;
public class TestRoom  implements Logic{
	
	/*
	 * CASMO ENGİNE ALPHA TESTİNG
	 * GPL LİCENSE 3.0
	 * Contact: caspersscasper0@gmail.com
	 * 
	 */
	private static final float CAMERA_MOVE_SPEED = 0.05f;
	
	private int direction = 0;
	private float renk = 0.0f;
	
	private final RenderM renderer;
	private final ObjectLoader loader;
	private final Display display;
	
	private Entitys entity;
	private Camera camera;
	
	Vector3f cameraInc;
	
	
	public TestRoom() {
		renderer = new RenderM();
		display = Launcher.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
		cameraInc = new Vector3f(0,0,0);
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		renderer.init();
		
		
		
		Model model = loader.loadobjmodel("res/USSP.obj");
		
		model.setTexture(new Texture(loader.loadtexture("res/311.png")));
		
		entity = new Entitys(model , new Vector3f(0,0,-5), new Vector3f(0,0,0),1);
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
		cameraInc.set(0,0,0);
		// MOVEMENT
		
		if(display.isKeyPressed(GLFW.GLFW_KEY_W))
			cameraInc.z = -1;
		if(display.isKeyPressed(GLFW.GLFW_KEY_S))
			cameraInc.z = 1;
		if(display.isKeyPressed(GLFW.GLFW_KEY_A))
			cameraInc.x = 1;
		if(display.isKeyPressed(GLFW.GLFW_KEY_D))
			cameraInc.x = -1;
		if(display.isKeyPressed(GLFW.GLFW_KEY_Z))
			cameraInc.y = -1;
		if(display.isKeyPressed(GLFW.GLFW_KEY_X))
			cameraInc.y = 1;
		
		
		//if(display.isKeyPressed(GLFW.GLFW_KEY_UP))
			//direction = 1;
		//else if (display.isKeyPressed(GLFW.GLFW_KEY_DOWN))
		    //direction = -1;
		//else
			//direction = 0;
		
		
		
	}

	@Override
	public void update( float interval , Mouse mouse) {
		// TODO Auto-generated method stub
		/*renk += direction * 0.01f;
		
		if(renk > 1)
			renk = 1.0f;
		
		else if(renk <= 0)
			renk = 0.0f;
		
		
		if(entity.getPos().x < -1.5f)
			entity.getPos().x = 1.5f;

		if(entity.getPos().y < -1.5f)
			entity.getPos().y = 1.5f;
		
		entity.getPos().x -= 0.01f;
		entity.getPos().y -= 0.01f;*/
		
		camera.movePos(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);
		entity.incrot(0.0f, 0.05f, 0.0f);
		
		if(mouse.isRighprs()) {
			Vector2f rotvec = mouse.getDispvec();
			
			camera.moveRot(rotvec.x * Consts.MOUSE_SENS, rotvec.y * Consts.MOUSE_SENS, 0);
			
		}
		entity.incrot(0.0f, 0.05f, 0.0f);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		if(display.isResize()) {
			GL11.glViewport(0, 0, display.getWidth(), display.getHeight());
			display.setResize(true);
		}
		display.setClearColour(0.0f, 0.0f, 0.0f, 0.0f);
		
		renderer.render(entity, camera);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		renderer.cleanup();
		loader.cleanup();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
