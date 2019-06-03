package com.pablov.jumpingjack.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pablov.jumpingjack.Juego;

public class PantallaGameOver implements Screen {
    private Viewport puerto;
    private Stage escenario;
    private Game game;

    public PantallaGameOver(Game game) {
        this.game = game;
        puerto = new FitViewport(Juego.ANCHO_V, Juego.ALTO_V, new OrthographicCamera());
        escenario = new Stage(puerto, ((Juego) game).batch);

        Label.LabelStyle fuente = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table tabla = new Table();
        tabla.center();
        tabla.setFillParent(true);

        Label labelGameOver = new Label("GAME OVER", fuente);
        tabla.add(labelGameOver).expandX();

        escenario.addActor(tabla);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
