package com.casmoopengl.renderengine;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */
public class Model {
	
	
	private int id;
	
	private int vertexcount;
	private Texture texture;
	
	

	public Model(int id , int vertexcount) {
		this.id = id;
		this.vertexcount = vertexcount;
	}
	
	public Model(int id, int vertexcount, Texture texture) {
		
		this.id = id;
		this.vertexcount = vertexcount;
		this.texture = texture;
	}
	
	public Model(Model model,  Texture texture) {
		this.id = model.getId();
		this.vertexcount = model.getvertexcount();
		
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public int getId() {
		return id;
	}
	
	public int getvertexcount() {
		return vertexcount;
	}
	
	

}
