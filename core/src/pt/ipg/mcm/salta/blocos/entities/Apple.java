package pt.ipg.mcm.salta.blocos.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pt.ipg.mcm.salta.blocos.main.Game;

public class Apple extends B2DSprite {

  private final TextureRegion reg;

  public Apple() {
		
		Texture tex = Game.res.getTexture("quadro");
		reg = new TextureRegion(tex, 80, 0, 16, 18);

		width = reg.getRegionWidth();
		height = reg.getRegionHeight();
		
	}


  @Override
  protected TextureRegion getTextureRegion() {
    return reg;
  }


}
