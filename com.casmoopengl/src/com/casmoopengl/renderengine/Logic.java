package com.casmoopengl.renderengine;
/*
 * CASMO ENGİNE ALPHA TESTİNG
 * GPL LİCENSE 3.0
 * Contact: caspersscasper0@gmail.com
 * 
 */
public interface Logic {
	
	 void init() throws Exception;
	 void input();
	 void update();
	 void render();
	 void cleanup();
	void update(float interval, Mouse mouse);

}
