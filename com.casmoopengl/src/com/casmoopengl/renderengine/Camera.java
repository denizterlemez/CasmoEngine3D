package com.casmoopengl.renderengine;

import org.joml.Vector3f;

/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */

public class Camera {
	
	private Vector3f position,rotation;
	
	public Camera() {
		position = new Vector3f(0,0,0);
		rotation = new Vector3f(0,0,0);
	}
	
	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public void movePos(float x , float y , float z) {
		if(z != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f *z;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) *z;
			
		}
		if(x != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y - 90 )) * -1.0f *z;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) *x;
			
		}
		
		position .y += y;
		
		
	}
	
	
	public void setPos(float x , float y , float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public void setRot(float x , float y , float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}
	
	public void moveRot(float x , float y , float z) {
		this.rotation.x += x;
		this.rotation.y += y;
		this.rotation.z += z;
	}

	public Vector3f getPos() {
		return position;
	}

	public Vector3f getRot() {
		return rotation;
	}
	
	

}
