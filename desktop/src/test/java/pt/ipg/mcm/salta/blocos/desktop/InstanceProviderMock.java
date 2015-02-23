package pt.ipg.mcm.salta.blocos.desktop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.neet.blockbunny.main.InstanceProvider;
import com.neet.blockbunny.main.Provider;

public class InstanceProviderMock extends InstanceProvider {
  public InstanceProviderMock() {
  }

  @Override
  public Provider<SpriteBatch> getSpriteBatchProvider() {
    return new MockProvider<SpriteBatch>(SpriteBatch.class);
  }

  @Override
  public Provider<ShapeRenderer> getShapeRendererProvider() {
    return new MockProvider<ShapeRenderer>(ShapeRenderer.class);
  }
}
