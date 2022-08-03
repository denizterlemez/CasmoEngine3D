package com.casmoopengl.renderengine;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;


public class ShaderM {
	
	
	private final int pID;
	private int vertexshaderID , fshaderID;
	
	
	private final Map<String,Integer> uniforms;
	
	public ShaderM() throws Exception{
		pID = GL20.glCreateProgram();
		if(pID == 0)
			throw new Exception("shader oluşturulamadı abe");
		
		
		uniforms = new HashMap<>();
	}
	
	public void createuniform(String uniformname) throws Exception{
		int uniformlocation = GL20.glGetUniformLocation(pID, uniformname);
		if(uniformlocation < 0)
			throw new Exception("uniform bulunamadı");
		uniforms.put(uniformname, uniformlocation);
	}
	
	public void setuniform(String uniformname, Matrix4f value) {
		
		try(MemoryStack stack = MemoryStack.stackPush()){
			FloatBuffer fb = stack.mallocFloat(16);
			GL20.glUniformMatrix4fv(uniforms.get(uniformname), false, value.get(fb));
		}
	}
	
	
	public void setuniform(String uniformname, Vector4f value) {
		GL20.glUniform3f(uniforms.get(uniformname), value.x, value.y, value.z);
	}
	
	
	public void setuniform(String uniformname , boolean value) {
		float res = 0;
		if(value)
			res= 1;
		
		GL20.glUniform1f(uniforms.get(uniformname), res);
	}
	
	
	public void setuniform(String uniformname,int value) {
		GL20.glUniform1i(uniforms.get(uniformname), value);
	}
	public void setuniform(String uniformname,float value) {
		GL20.glUniform1f(uniforms.get(uniformname), value);
	}
	
	
	
	
	
	public void createvertexshader(String shadercode) throws Exception{
		vertexshaderID = createShader(shadercode , GL20.GL_VERTEX_SHADER);
	}
	public void createfragmentshader(String shadercode) throws Exception{
		fshaderID = createShader(shadercode , GL20.GL_FRAGMENT_SHADER);
	}
	public int createShader(String shadercode , int shaderType) throws Exception{
		int shaderID = GL20.glCreateShader(shaderType);
		if(shaderID == 0)
			throw new Exception("shader olmadı abe ");
		
		
		GL20.glShaderSource(shaderID, shadercode);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0)
			throw new Exception("olmadı abegggg" +shaderType + GL20.glGetShaderInfoLog(shaderID, 1024));
		
		GL20.glAttachShader(pID, shaderID);
		
		return shaderID;
		
		
	}
	
	public void link() throws Exception {
		GL20.glLinkProgram(pID);
		if(GL20.glGetProgrami(pID, GL20.GL_LINK_STATUS) == 0)
			throw new Exception("shader code hatası " + GL20.glGetProgramInfoLog(pID , 1024) );
		
		
		if(vertexshaderID != 0)
			GL20.glDetachShader(pID, vertexshaderID);
		
		if(fshaderID != 0)
			GL20.glDetachShader(pID, fshaderID);
		
		
		GL20.glValidateProgram(pID);
		if(GL20.glGetProgrami(pID, GL20.GL_VALIDATE_STATUS) == 0)
			throw new Exception("dogrulanmıs shader kodu değil"+ GL20.glGetProgramInfoLog(pID , 1024));
		
		
		
	}
	
	public void bind() {
		GL20.glUseProgram(pID);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void cleanup() {
		unbind();
		if(pID != 0)
			GL20.glDeleteProgram(pID);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
