package com.pablov.jumpingjack.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.utilidades.DatosJuego;

public class MenuPrincipal implements Screen {
    private Viewport puerto;
    private Stage escenario;
    private Game juego;
    private Label botonNueva, botonCargar, botonSalir;
    private DatosJuego datos;

    public MenuPrincipal(Game game) {
        this.juego = game;
        datos = new DatosJuego();
        puerto = new FitViewport(Juego.ANCHO_V, Juego.ALTO_V, new OrthographicCamera());
        escenario = new Stage(puerto, ((Juego) game).batch);

        Label.LabelStyle fuente = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table tabla = new Table();
        tabla.setFillParent(true);

        Label labelGameOver = new Label("JUMPING JACK", fuente);
        botonNueva = new Label("----------------------\n|  Nueva partida  |\n----------------------", fuente);
        botonNueva.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                datos.nuevo();
                juego.setScreen(new Pantalla((Juego) juego));
                dispose();
            }
        });
        botonCargar = new Label("----------------------\n|  Cargar partida  |\n----------------------", fuente);
        botonCargar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new Pantalla((Juego) juego));
                dispose();
            }
        });
        botonSalir = new Label("----------------------\n|         Salir         |\n----------------------", fuente);
        botonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });
        Gdx.input.setInputProcessor(escenario);
        tabla.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("fondo_menu.png"))));
        tabla.add(labelGameOver).expandX();
        tabla.row();
        tabla.add(botonNueva).expandX().padTop(10f);
        tabla.row();
        tabla.add(botonCargar).expandX().padTop(10f);
        tabla.row();
        tabla.add(botonSalir).expandX().padTop(10f);
        escenario.addActor(tabla);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        escenario.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        escenario.draw();
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
        escenario.dispose();
    }
}
