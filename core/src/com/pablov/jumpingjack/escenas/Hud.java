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

    public int tiempoMundo;
    private float cuentaTiempo;

    private static int vidas;
    private static int puntos;
    private static int nivel;

    private static Label labelValorPuntos;
    private static Label labelValorVidas;
    private Label labelValorNivel;
    private Label labelValorTiempo;

    public Hud(SpriteBatch batch) {
        tiempoMundo = 180;
        cuentaTiempo = 0;
        vidas = 3;
        puntos = 0;
        nivel = 1;

        Viewport puerto = new FitViewport(Juego.ANCHO_V, Juego.ALTO_V, new OrthographicCamera());
        escenario = new Stage(puerto, batch);

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);

        Label labelPuntos = new Label("PUNTOS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label labelVidas = new Label("JACK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label labelNivel = new Label("NIVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label labelTiempo = new Label("TIEMPO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelValorPuntos = new Label(String.format("%06d", puntos), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelValorVidas = new Label("x" + String.format("%01d", vidas), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelValorNivel = new Label((String.format("%01d", nivel)), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
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

    public static int getVidas() {
        return vidas;
    }

    public static void setVidas(int vidas) {
        Hud.vidas = vidas;
    }

    public static int getPuntos() {
        return puntos;
    }

    public static void setPuntos(int puntos) {
        Hud.puntos = puntos;
    }

    public static int getNivel() {
        return nivel;
    }

    public static void setNivel(int nivel) {
        Hud.nivel = nivel;
    }

    public void actualizar(float delta) {
        cuentaTiempo += delta;
        if(cuentaTiempo >= 1) {
            tiempoMundo--;
            labelValorTiempo.setText(String.format("%03d", tiempoMundo));
            cuentaTiempo = 0;
        }
        labelValorNivel.setText(String.format("%01d", nivel));
        labelValorVidas.setText("x" + String.format("%01d", vidas));
        labelValorPuntos.setText(String.format("%06d", puntos));
    }

    public static void anadirPuntos(int valor) {
        puntos += valor;
        labelValorPuntos.setText(String.format("%06d", puntos));
    }

    public static void anadirVidas(int valor) {
        vidas += valor;
        labelValorVidas.setText("x" + String.format("%01d", vidas));
    }

    @Override
    public void dispose() {
        escenario.dispose();
    }
}
