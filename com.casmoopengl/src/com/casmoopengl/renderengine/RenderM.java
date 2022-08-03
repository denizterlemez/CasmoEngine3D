package com.casmoopengl.renderengine;
import com.casmoopengl.*;
import com.casmoopengl.test.Launcher;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */

public class RenderM {
	private final Display display;
	
	private ShaderM shader;
	
	
	public RenderM() {
		display = Launcher.getWindow();
		
	}
	
	public void init() throws Exception {
		shader= new ShaderM();
		shader.createvertexshader(Utils.loadres("res/vertex.vs"));
		shader.createfragmentshader(Utils.loadres("res/fragment.fs"));
		shader.link();
		shader.createuniform("textureSampler");
		shader.createuniform("transformationMatrix");
		shader.createuniform("projectionMatrix");
		shader.createuniform("viewMatrix");
	}
	public void render(Entitys entity , Camera camera) {
		clear();
		shader.bind();
		shader.setuniform("textureSampler", 0);
		shader.setuniform("transformationMatrix", Transformation.createtransformationmatrix(entity));
		
		shader.setuniform("projectionMatrix", display.updateProjectionMatrix());
		shader.setuniform("viewMatrix", Transformation.getViewMatrix(camera));
		
		GL30.glBindVertexArray(entity.getModel().getId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getId());
		
		GL11.glDrawElements(GL11.GL_TRIANGLES,entity.getModel().getvertexcount() , GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		shader.unbind();
	}
	
	public void clear()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
	}
	
	
	public void cleanup() {
		shader.cleanup();
	}
	

}
