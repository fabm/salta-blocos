package com.neet.blockbunny.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neet.blockbunny.handlers.BBInput;
import com.neet.blockbunny.handlers.GameButton;
import com.neet.blockbunny.handlers.GameStateManager;
import com.neet.blockbunny.main.Game;

public class LevelSelect extends GameState {

  private TextureRegion reg;

  private GameButton[][] buttons;

  private Point currentLevel = new Point();

  public LevelSelect(GameStateManager gsm) {

    super(gsm);

    reg = new TextureRegion(Game.res.getTexture("bgs"), 0, 0, 320, 240);

    TextureRegion textureRegion = new TextureRegion(Game.res.getTexture("hud"), 0, 0, 32, 32);

    buttons = new GameButton[1][5];
    for (int row = 0; row < buttons.length; row++) {
      for (int col = 0; col < buttons[0].length; col++) {
        buttons[row][col] = new GameButton(textureRegion, 80 + col * 40, 200 - row * 40, cam);
        buttons[row][col].setText(row * buttons[0].length + col + 1 + "");
      }
    }

    cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

  }

  public void handleInput() {
    if (BBInput.isPressed(BBInput.UP_BUTTON)) {
      currentLevel.y--;
    }
    if (BBInput.isPressed(BBInput.LEFT_BUTTON)) {
      currentLevel.x--;
    }
    if (BBInput.isPressed(BBInput.RIGHT_BUTTON)) {
      currentLevel.x++;
    }
    if (BBInput.isPressed(BBInput.DOWN_BUTTON)) {
      currentLevel.y++;
    }
    if(BBInput.isPressed(BBInput.ENTER_MENU_BUTTON)){
      Play.level = currentLevel.y*5+currentLevel.x+1;
       gsm.setState(State.PLAY);
    }
    if (currentLevel.x < 0) {
      currentLevel.x = 4;
    } else if (currentLevel.x > 4) {
      currentLevel.x = 0;
    }
    if (currentLevel.y < 0) {
      currentLevel.y = 4;
    } else if (currentLevel.y > 4) {
      currentLevel.y = 0;
    }

  }

  public void update(float dt) {

    handleInput();

    for (int row = 0; row < buttons.length; row++) {
      for (int col = 0; col < buttons[0].length; col++) {
        buttons[row][col].update(dt);
        if (buttons[row][col].isClicked()) {
          Play.level = row * buttons[0].length + col + 1;
          Game.res.getSound("levelselect").play();
          gsm.setState(State.PLAY);
        }
      }
    }

  }

  public void render() {

    sb.setProjectionMatrix(cam.combined);

    sb.begin();
    sb.draw(reg, 0, 0);
    sb.end();

    for (int row = 0; row < buttons.length; row++) {
      for (int col = 0; col < buttons[0].length; col++) {
        if (row == currentLevel.y && col == currentLevel.x) {
          sb.setColor(Color.RED);
        } else {
          sb.setColor(Color.WHITE);
        }
        buttons[row][col].render(sb);
      }
    }
    sb.setColor(Color.WHITE);

  }

  public void dispose() {
    // everything is in the resource manager com.neet.blockbunny.handlers.Content
  }

  private static class Point {
    private int x = 0, y = x;
  }
}
