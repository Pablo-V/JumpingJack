package com.pablov.jumpingjack.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pablov.jumpingjack.Juego;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Juego.ANCHO_V;
		config.height = Juego.ALTO_V;
		new LwjglApplication(new Juego(), config);
	}
}
