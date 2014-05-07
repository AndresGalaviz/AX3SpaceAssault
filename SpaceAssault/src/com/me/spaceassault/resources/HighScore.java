package com.me.spaceassault.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class HighScore {
	public static int getHighScore() {
		int hs = 0;
		Preferences prefs = Gdx.app.getPreferences("myPreferences");
		if(prefs.contains("highscoreax3")) {
			hs = prefs.getInteger("highscoreax3");
		}
		prefs.flush();
		return hs;
	}
	public static void setHighScore(int hs) {
		Preferences prefs = Gdx.app.getPreferences("myPreferences");
		prefs.putInteger("highscoreax3", hs);
		prefs.flush();
	}
	
}
