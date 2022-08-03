package com.casmoopengl.renderengine;

import org.joml.Vector3f;

/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */
public class Entitys {
	
	private Model model;
	private Vector3f pos,rotation;
	private float scale;
	
	public Entitys(Model model , Vector3f pos,Vector3f rotation ,float scale) {
		this.model = model;
		
		this.pos = pos;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	
	public void incpos(float x,float y ,float z) {
		this.pos.x += x;
		
		this.pos.y += y;
		this.pos.z += z;
	}
	
	public void setpos(float x,float y ,float z) {
		this.pos.x += x;
		
		this.pos.y += y;
		this.pos.z += z;
	}
	
	
	
	public void incrot(float x,float y ,float z) {
		this.rotation.x += x;
		
		this.rotation.y += y;
		this.rotation.z += z;
	}
	
	public void setrot(float x,float y ,float z) {
		this.rotation.x += x;
		
		this.rotation.y += y;
		this.rotation.z += z;
	}


	public Model getModel() {
		return model;
	}


	public Vector3f getPos() {
		return pos;
	}


	public Vector3f getRotation() {
		return rotation;
	}


	public float getScale() {
		return scale;
	}
	
	

}
