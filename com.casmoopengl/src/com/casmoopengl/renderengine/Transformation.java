package com.casmoopengl.renderengine;
import com.casmoopengl.renderengine.*;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */
import com.casmoopengl.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
public class Transformation {
	
	public static Matrix4f createtransformationmatrix(Entitys entity) {
		Matrix4f matrix = new Matrix4f();
		
		matrix.identity().translate(entity.getPos()).rotateX((float) Math.toRadians(entity.getRotation().x)).rotateY((float) Math.toRadians(entity.getRotation().y)).rotateZ((float) Math.toRadians(entity.getRotation().z)).scale(entity.getScale());
		
		
		return matrix;
	}
	
	
	public static Matrix4f getViewMatrix(Camera camera) {
		Vector3f pos = camera.getPos();
		Vector3f rot = camera.getRot();
		
		Matrix4f matrix = new Matrix4f();
		
		matrix.identity();
		
		matrix.rotate((float) Math.toRadians(rot.x) , new Vector3f(1,0,0))
		        .rotate((float) Math.toRadians(rot.y) , new Vector3f(0,1,0))
		        .rotate((float) Math.toRadians(rot.z) , new Vector3f(0,0,1));
		matrix.translate(-pos.x , -pos.y , -pos.z);
		return matrix;
		
		
	}
	
	

}
