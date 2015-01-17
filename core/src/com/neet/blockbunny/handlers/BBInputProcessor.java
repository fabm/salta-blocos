package com.neet.blockbunny.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.omg.CORBA.TRANSACTION_UNAVAILABLE;

public class BBInputProcessor extends InputAdapter {

  public boolean mouseMoved(int x, int y) {
    BBInput.x = x;
    BBInput.y = y;
    return true;
  }

  public boolean touchDragged(int x, int y, int pointer) {
    BBInput.x = x;
    BBInput.y = y;
    BBInput.down = true;
    return true;
  }

  public boolean touchDown(int x, int y, int pointer, int button) {
    BBInput.x = x;
    BBInput.y = y;
    BBInput.down = true;
    return true;
  }

  public boolean touchUp(int x, int y, int pointer, int button) {
    BBInput.x = x;
    BBInput.y = y;
    BBInput.down = false;
    return true;
  }

  public boolean keyDown(int k) {
    Gdx.app.debug("currentKey:","key:"+k);

    if(k==Keys.LEFT){
      BBInput.setKey(BBInput.LEFT_BUTTON, true);
    }
    if(k==Keys.DOWN){
      BBInput.setKey(BBInput.DOWN_BUTTON, true);
    }
    if(k==Keys.RIGHT){
      BBInput.setKey(BBInput.RIGHT_BUTTON, true);
    }
    if(k==Keys.UP){
      BBInput.setKey(BBInput.UP_BUTTON, true);
    }
    if (k == Keys.Z || k == Keys.UP) {
      BBInput.setKey(BBInput.BUTTON1, true);
    }
    if (k == Keys.X || k == Keys.DOWN) {
      BBInput.setKey(BBInput.BUTTON2, true);
    }
    if (k == Keys.SPACE) {
      BBInput.setKey(BBInput.ENTER_MENU_BUTTON, true);
    }
    return true;
  }

  public boolean keyUp(int k) {
    if(k==Keys.LEFT){
        BBInput.setKey(BBInput.LEFT_BUTTON, false);
    }
    if(k==Keys.DOWN){
        BBInput.setKey(BBInput.DOWN_BUTTON, false);
    }
    if(k==Keys.RIGHT){
        BBInput.setKey(BBInput.RIGHT_BUTTON, false);
    }
    if(k==Keys.UP){
        BBInput.setKey(BBInput.UP_BUTTON, false);
    }
    if (k == Keys.Z || k == Keys.UP) {
      BBInput.setKey(BBInput.BUTTON1, false);
    }
    if (k == Keys.X || k == Keys.DOWN) {
      BBInput.setKey(BBInput.BUTTON2, false);
    }
    if (k == Keys.SPACE) {
      BBInput.setKey(BBInput.ENTER_MENU_BUTTON, false);
    }
    return true;
  }

}
