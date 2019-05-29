package com.pablov.jumpingjack.escenas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pablov.jumpingjack.Juego;

public class Hud implements Disposable {
    public Stage escenario;
    private Viewport puerto;

    private Integer tiempoMundo;
    private float cuentaTiempo;
    private Integer vidas;

    Label labelTiempo;
    Label labelVidas;
    Label labelReloj;
    Label labelNivel;
    Label labelMundo;
    Label labelJack;

    public Hud(SpriteBatch batch) {
        tiempoMundo = 300;
        cuentaTiempo = 0;
        vidas = 3;

        puerto = new FitViewport(Juego.ANCHO_V, Juego.ALTO_V, new OrthographicCamera());
        escenario = new Stage(puerto, batch);

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);

        labelReloj = new Label(String.format("%03d", tiempoMundo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelVidas = new Label("x" + String.format("%01d", vidas), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelTiempo = new Label("TIEMPO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelNivel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelMundo = new Label("NIVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelJack = new Label("JACK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        tabla.add(labelJack).expandX().padTop(5);
        tabla.add(labelMundo).expandX().padTop(5);
        tabla.add(labelTiempo).expandX().padTop(5);
        tabla.row();
        tabla.add(labelVidas).expandX();
        tabla.add(labelNivel).expandX();
        tabla.add(labelReloj).expandX();

        escenario.addActor(tabla);
    }

    @Override
    public void dispose() {
        escenario.dispose();
    }
}
