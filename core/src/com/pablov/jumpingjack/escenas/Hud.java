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

    private int tiempoMundo;
    private float cuentaTiempo;
    private int vidas;
    private static int puntos;

    Label labelPuntos;
    Label labelVidas;
    Label labelNivel;
    Label labelTiempo;
    static Label labelValorPuntos;
    Label labelValorVidas;
    Label labelValorNivel;
    Label labelValorTiempo;

    public Hud(SpriteBatch batch) {
        tiempoMundo = 300;
        cuentaTiempo = 0;
        vidas = 3;
        puntos = 0;

        puerto = new FitViewport(Juego.ANCHO_V, Juego.ALTO_V, new OrthographicCamera());
        escenario = new Stage(puerto, batch);

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);

        labelPuntos = new Label("PUNTOS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelVidas = new Label("JACK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelNivel = new Label("NIVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelTiempo = new Label("TIEMPO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelValorPuntos = new Label(String.format("%06d", puntos), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelValorVidas = new Label("x" + String.format("%01d", vidas), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelValorNivel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelValorTiempo = new Label(String.format("%03d", tiempoMundo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        tabla.add(labelPuntos).expandX().padTop(5);
        tabla.add(labelVidas).expandX().padTop(5);
        tabla.add(labelNivel).expandX().padTop(5);
        tabla.add(labelTiempo).expandX().padTop(5);
        tabla.row();
        tabla.add(labelValorPuntos).expandX();
        tabla.add(labelValorVidas).expandX();
        tabla.add(labelValorNivel).expandX();
        tabla.add(labelValorTiempo).expandX();

        escenario.addActor(tabla);
    }

    public void actualizar(float delta) {
        cuentaTiempo += delta;
        if(cuentaTiempo >= 1) {
            tiempoMundo--;
            labelValorTiempo.setText(String.format("%03d", tiempoMundo));
            cuentaTiempo = 0;
        }
    }

    public static void anadirPuntos(int valor) {
        puntos += valor;
        labelValorPuntos.setText(String.format("%06d", puntos));
    }

    @Override
    public void dispose() {
        escenario.dispose();
    }
}
