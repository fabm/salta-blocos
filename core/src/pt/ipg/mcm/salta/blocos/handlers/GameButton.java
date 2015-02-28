package pt.ipg.mcm.salta.blocos.handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import pt.ipg.mcm.salta.blocos.main.Game;

/**
 * Simple image button.
 */
public class GameButton {

  // center at x, y
  private float x;
  private float y;
  private float width;
  private float height;

  private TextureRegion reg;

  Vector3 vec;
  private OrthographicCamera cam;

  private boolean clicked;

  private String text;
  private TextureRegion[] font;
  Logger logger = new Logger(GameButton.class.getSimpleName(), Logger.DEBUG);

  public GameButton(TextureRegion reg, float x, float y, OrthographicCamera cam) {

    this.reg = reg;
    this.x = x;
    this.y = y;
    this.cam = cam;

    width = reg.getRegionWidth();
    height = reg.getRegionHeight();
    vec = new Vector3();

    Texture tex = Game.res.getTexture("quadro");
    font = new TextureRegion[11];
    for (int i = 0; i < 6; i++) {
      font[i] = new TextureRegion(tex, 32 + i * 9, 16, 9, 9);
    }
    for (int i = 0; i < 5; i++) {
      font[i + 6] = new TextureRegion(tex, 32 + i * 9, 25, 9, 9);
    }

  }

  public boolean isClicked() {
    return clicked;
  }

  public void setText(String s) {
    text = s;
  }

  public void update() {

    vec.set(BBInput.x, BBInput.y, 0);
    cam.unproject(vec);

    if (BBInput.isPressed() &&
        vec.x > x - width / 2 && vec.x < x + width / 2 &&
        vec.y > y - height / 2 && vec.y < y + height / 2) {
      clicked = true;
    } else {
      clicked = false;
    }

  }

  public void render(SpriteBatch sb) {
    BitmapFont bmf = new BitmapFont();
    bmf.setColor(Color.BLUE);
    sb.begin();

    sb.draw(reg, x - width / 2, y - height / 2);

    if (text != null) {
      bmf.draw(sb,text,x-4,y+6);
    }

    sb.end();

  }


  public void render(ShapeRenderer sr,SpriteBatch sb) {

    if (text != null) {
      render(sb);
    }

    sr.begin(ShapeRenderer.ShapeType.Line);

    sr.circle(x - width / 2+16, y - height / 2+16, 16);

    sr.end();


  }
}
