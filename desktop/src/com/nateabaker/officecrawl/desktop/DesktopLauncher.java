package com.nateabaker.officecrawl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.nateabaker.officecrawl.OfficeCrawl;

public class DesktopLauncher {
	public static void main(String[] arg) {

		// if (Assets.rebuildAtlas) {
		Settings settings = new Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
		settings.debug = false;
		try {
			TexturePacker.process(settings, "assets-raw", "../android/assets",
					"assets");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// config.width = Constants.SCREEN_WIDTH;
		// config.height = Constants.SCREEN_HEIGHT;
		// config.vSyncEnabled = LauncherConstants.VSYNC;
		new LwjglApplication(new OfficeCrawl(), config);
	}
}
