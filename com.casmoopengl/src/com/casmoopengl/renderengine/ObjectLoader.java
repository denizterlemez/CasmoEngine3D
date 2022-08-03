package com.casmoopengl.renderengine;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */
public class ObjectLoader {
	private List<Integer> vaos = new ArrayList<>();
	private List<Integer> vbos = new ArrayList<>();
	private List<Integer> textures = new ArrayList<>();
	
	
	public Model loadobjmodel(String filename) {
		List<String> lines = Utils.readalllines(filename);
		List<Vector3f> vertices = new ArrayList<>();
		List<Vector3f> nominals = new ArrayList<>();
		List<Vector2f> textures = new ArrayList<>();
		List<Vector3i> faces = new ArrayList<>();
		
		for(String line : lines) {
			String[] tokens =line.split("\\s+");
			switch(tokens[0]) {
			   case "v":
				   
				   Vector3f verticesvec = new Vector3f(
						   Float.parseFloat(tokens[1]),
						   Float.parseFloat(tokens[2]),
						   Float.parseFloat(tokens[3])
						   );
				   
				   vertices.add(verticesvec);
				   break;
			   case "vt":
				   Vector2f texturevec = new Vector2f(
						   Float.parseFloat(tokens[1]),
						   Float.parseFloat(tokens[2])
						   );
				   
				   
				   
				   break;
			   case "vn":
				   Vector3f normalvec = new Vector3f(
						   Float.parseFloat(tokens[1]),
						   Float.parseFloat(tokens[2]),
						   Float.parseFloat(tokens[3])
						   );
				   
				   nominals.add(normalvec);
				   break;
			   case "f":
				   processface(tokens[1] , faces);
				   processface(tokens[2] , faces);
				   processface(tokens[3] , faces);
				   break;
			   default:
				   break;
				
				
			
			}
		}
		List<Integer> indices = new ArrayList<>();
		float[] verticesarr = new float[vertices.size() * 3];
		int i = 0;
		for(Vector3f pos : vertices) {
			verticesarr[i * 3] = pos.x;
			verticesarr[i * 3 + 1] = pos.y;
			verticesarr[i * 3 + 2] = pos.z;
			i++;
		}
		
		float[] textcoordarr = new float[vertices.size() *2];
		float[] normalarr = new float[vertices.size() * 3];
		
		for(Vector3i face : faces) {
			processvertex(face.x , face.y, face.z , textures ,nominals,indices,textcoordarr,normalarr);
			
		}
		
		int[] indicesarr = indices.stream().mapToInt((Integer v) -> v).toArray();
		
		return loadModel(verticesarr,textcoordarr,indicesarr);
		
		
	}
	
	private static void processvertex(int pos , int texcoord , int normal , List<Vector2f> textcoordlist,
			List<Vector3f> normallist , List<Integer> indiceslist, float[] textcoordarr , float[] normalarr) {
		
		indiceslist.add(pos);
		
		if(texcoord >= 0) {
			Vector2f textcoordvec= textcoordlist.get(texcoord);
			textcoordarr[pos*2] = textcoordvec.x;
			textcoordarr[ pos * 2 + 1] = 1 - textcoordvec.y;
		}
		
		if(normal >= 0) {
			Vector3f normalvec = normallist.get(normal);
			normalarr[pos * 3] = normalvec.x;
			normalarr[pos * 3 + 1] = normalvec.y;
			normalarr[pos * 3 +  2] = normalvec.z;
		}
		
		
	}
	
	
	private static void processface(String token, List<Vector3i> faces) {
		String[] linetoken = token.split("/");
		int length = linetoken.length;
		int pos = -1, coords= -1, normal = -1;
		pos = Integer.parseInt(linetoken[0]) -1;
		if(length > 1) {
			String textcoord = linetoken[1];
			coords = textcoord.length() > 0 ? Integer.parseInt(textcoord) -1: -1;
			if(length > 2)
				normal = Integer.parseInt(linetoken[2]) -1;
 		}
		
		Vector3i facesvec =new Vector3i(pos ,  coords, normal);
		faces.add(facesvec);
		
		
		
	}
	
	
	public Model loadModel(float[] vertices , float[] texturecoords , int[] indices) {
		int id = createVAO();
		storeindicesbuffer(indices);
		storeDataInAttribList(0,3,vertices);
		storeDataInAttribList(1,2, texturecoords);
		unbind();
		return new Model(id, vertices.length /3);
		
	}
	
	public int loadtexture(String filename) throws Exception{
		int width,height;
		ByteBuffer buffer;
		try(MemoryStack stack = MemoryStack.stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer a = stack.mallocInt(1);
			buffer = STBImage.stbi_load(filename, w, h,a , 4);
			if(buffer ==null)
				throw new Exception("resim  yüklenemedi"+STBImage.stbi_failure_reason());
			
			
			width = w.get();
			height= h.get();
		}
		
		
		int id = GL11.glGenTextures();
		textures.add(id);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		STBImage.stbi_image_free(buffer);
		return id;
		
	}
	
	private int createVAO() {
		int id = GL30.glGenVertexArrays();
		vaos.add(id);
		GL30.glBindVertexArray(id);
		return id;
		
	}
	
	private void storeindicesbuffer(int[] indices) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer buffer = Utils.storeIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	
	
	private void storeDataInAttribList(int attribNo , int vertexcount , float[] data) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = Utils.storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribNo , vertexcount , GL11.GL_FLOAT , false , 0,0 );
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbind() {
		GL30.glBindVertexArray(0);
		
	}
	
	public void cleanup() {
		for( int vao : vaos)
			GL30.glDeleteVertexArrays(vao); 
		for(int vbo :vbos)
			GL30.glDeleteBuffers(vbo);
		for(int texture :textures)
			GL30.glDeleteBuffers(texture);
		
	}
	
	
	

}
