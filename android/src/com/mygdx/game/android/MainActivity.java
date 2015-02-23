package com.mygdx.game.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.neet.blockbunny.main.Game;
import com.neet.blockbunny.main.InstanceProvider;

public class MainActivity extends AndroidApplication {
  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

    InstanceProvider.set(new InstanceProvider());
    initialize(new Game(), cfg);
  }
}