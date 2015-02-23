package com.neet.blockbunny.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class InstanceProvider {

  private static InstanceProvider instanceProvider;

  private Provider<SpriteBatch> spriteBatchProvider;

  private Provider<ShapeRenderer> shapeRendererProvider;

  public static InstanceProvider get() {
    return instanceProvider;
  }

  public static void set(InstanceProvider instanceProvider) {
    InstanceProvider.instanceProvider = instanceProvider;
  }

  public InstanceProvider() {
    init();
  }

  private void init(){
    spriteBatchProvider = new Provider<SpriteBatch>() {
      @Override
      public SpriteBatch get() {
        return new SpriteBatch();
      }
    };
    shapeRendererProvider = new Provider<ShapeRenderer>() {
      @Override
      public ShapeRenderer get() {
        return new ShapeRenderer();
      }
    };

  }

  public Provider<SpriteBatch> getSpriteBatchProvider() {
    return spriteBatchProvider;
  }

  public Provider<ShapeRenderer> getShapeRendererProvider() {
    return shapeRendererProvider;
  }


}
