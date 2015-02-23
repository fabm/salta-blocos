package com.neet.blockbunny.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	private TextureRegion[] frames;
	private float time;
	private float delay;
	private int currentFrame;
  private String nome;

	public Animation() {}

	public Animation(TextureRegion[] frames, float delay) {
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setFrames(TextureRegion[] frames, float delay) {
		this.frames = frames;
		time = 0;
		currentFrame = 0;
		this.delay = delay;
	}
	
	public void update(float dt) {
		if(delay <= 0) return;
		time += dt;
		while(time >= delay) {
			step();
		}
	}
	
	private void step() {
		time -= delay;
		currentFrame++;
    Gdx.app.log("frame","nome: "+getNome()+"frame:"+currentFrame);
		if(currentFrame == frames.length) {
			currentFrame = 0;
		}
	}
	
	public TextureRegion getFrame() {
    return frames[currentFrame];
  }

}
