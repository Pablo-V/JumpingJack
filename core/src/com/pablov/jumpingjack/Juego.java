package com.pablov.jumpingjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Juego extends Game {


	public static final int ANCHO_V = 800;
	public static final int ALTO_V = 600;
	public static final float PPM = 70; //Píxeles por metro

	public SpriteBatch batch;

	@Override
	public void create () {
		Gdx.graphics.setTitle("Jumping Jack");
		batch = new SpriteBatch();
		setScreen(new Pantalla(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
