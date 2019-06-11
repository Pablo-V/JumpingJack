package com.pablov.jumpingjack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pablov.jumpingjack.pantallas.MenuPrincipal;

public class Juego extends Game {
    public static final int ANCHO_V = 800;
    public static final int ALTO_V = 600;
    public static final float PPM = 70; //Píxeles por metro

    //Declaración de constantes short para utilizar operadores de bits en las colisiones
    //de objetos interactivos, en concreto, para definir categorías de objetos y máscaras
    //(categorías con las que un objeto puede colisionar). Estos operadores tienen
    //un rendimiento superior ya que las comparaciones van más directas al procesador.
    public static final short BIT_NADA = 0;
    public static final short BIT_SUELO = 1;
    public static final short BIT_JACK = 2;
    public static final short BIT_BLOQUE = 4;
    public static final short BIT_MONEDA = 8;
    public static final short BIT_DESTRUIDO = 16;
    public static final short BIT_OBJETO = 32;
    public static final short BIT_ENEMIGO = 64;
    public static final short BIT_CABEZA_ENEMIGO = 128;
    public static final short BIT_BARRERA = 256;

    public SpriteBatch batch;

    @Override
    public void create() {
        Gdx.graphics.setTitle("Jumping Jack");
        batch = new SpriteBatch();
        setScreen(new MenuPrincipal(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}
