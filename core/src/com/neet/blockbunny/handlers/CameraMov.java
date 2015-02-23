package com.neet.blockbunny.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraMov extends OrthographicCamera {
	
	private float xmin;
	private float xmax;

	public CameraMov() {
		this(0, 0);
	}
	
	public CameraMov(float xmin, float xmax) {
		super();
		setBounds(xmin, xmax);
	}
	
	public void setBounds(float xmin, float xmax) {
		this.xmin = xmin;
		this.xmax = xmax;
	}
	
	public void setPosition(float x, float y) {
		setPosition(x, y, 0);
	}
	
	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
		comporDeFinalDeMapa();
	}
	
	private void comporDeFinalDeMapa() {
		if(position.x < xmin + viewportWidth / 2) {
			position.x = xmin + viewportWidth / 2;
		}
		if(position.x > xmax - viewportWidth / 2) {
			position.x = xmax - viewportWidth / 2;
		}

	}
	
}
