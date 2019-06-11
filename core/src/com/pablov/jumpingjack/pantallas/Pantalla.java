package com.pablov.jumpingjack.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.entidades.Enemigo;
import com.pablov.jumpingjack.entidades.Jack;
import com.pablov.jumpingjack.escenas.Hud;
import com.pablov.jumpingjack.utilidades.ColisionMapa;
import com.pablov.jumpingjack.utilidades.DatosJuego;
import com.pablov.jumpingjack.utilidades.DetectorContactoMundo;

public class Pantalla implements Screen {
    public Juego juego;
    private TextureAtlas atlas;
    private OrthographicCamera camara;
    private Viewport puerto;
    private Hud hud;
    private boolean tiempoAgotado, pausa;
    public Jack jugador;
    public DatosJuego datos;

    //Variables mapa .tmx
    private TmxMapLoader cargadorMapa;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;

    //Variables Box2D
    private World mundo;
    //private Box2DDebugRenderer b2dr;
    private ColisionMapa colisionMapa;

    public Pantalla(Juego juego) {
        atlas = new TextureAtlas("entidades.pack");
        this.juego = juego;
        camara = new OrthographicCamera();
        puerto = new FitViewport(Juego.ANCHO_V / Juego.PPM, Juego.ALTO_V / Juego.PPM, camara);
        hud = new Hud(juego.batch);
        datos = new DatosJuego();
        Hud.setNivel(datos.prefs.getInteger("nivel"));
        Hud.setVidas(datos.prefs.getInteger("vidas"));
        Hud.setPuntos(datos.prefs.getInteger("puntos"));

        cargadorMapa = new TmxMapLoader();
        mapa = cargadorMapa.load("Mapa1.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa, 1 / Juego.PPM);

        camara.position.set(puerto.getWorldWidth() / 2 / Juego.PPM, 700 / Juego.PPM, 0);

        mundo = new World(new Vector2(0, -17), true);

        //b2dr = new Box2DDebugRenderer();

        colisionMapa = new ColisionMapa(this);

        jugador = new Jack(this);

        mundo.setContactListener(new DetectorContactoMundo());
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void gestionarEntrada(float delta) {
        if (jugador.estadoActual != Jack.State.MORIR) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && (jugador.cuerpo.getLinearVelocity().y > -0.3f && jugador.cuerpo.getLinearVelocity().y < 0.3f))
                jugador.cuerpo.applyLinearImpulse(new Vector2(0, 11f), jugador.cuerpo.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.D) && jugador.cuerpo.getLinearVelocity().x <= 5)
                jugador.cuerpo.applyLinearImpulse(new Vector2(0.5f, 0), jugador.cuerpo.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.A) && jugador.cuerpo.getLinearVelocity().x >= -5)
                jugador.cuerpo.applyLinearImpulse(new Vector2(-0.5f, 0), jugador.cuerpo.getWorldCenter(), true);
        }
    }


    public void actualizar(float delta) {
        gestionarEntrada(delta);

        mundo.step(1 / 60f, 8, 3);

        jugador.actualizar(delta);
        for (Enemigo enemigo : colisionMapa.getRatones())
            enemigo.actualizar(delta);
        hud.actualizar(delta);

        if (jugador.estadoActual != Jack.State.MORIR) {
            camara.position.x = jugador.cuerpo.getPosition().x;
            camara.position.y = jugador.cuerpo.getPosition().y;
        }

        renderer.setView(camara);
        camara.update();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            pausa = !pausa;
        if (!pausa) {
            actualizar(delta);

            Gdx.gl.glClearColor(66 / 255f, 176 / 255f, 244 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            renderer.render();

            //b2dr.render(mundo, camara.combined);

            juego.batch.setProjectionMatrix(camara.combined);
            juego.batch.begin();
            jugador.draw(juego.batch);
            for (Enemigo enemigo : colisionMapa.getRatones()) {
                enemigo.draw(juego.batch);
                if (enemigo.getX() < jugador.getX() + 6.5f)
                    enemigo.cuerpo.setActive(true);
            }
            juego.batch.end();
            juego.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
            hud.escenario.draw();

            if (hud.tiempoMundo == 0 && !tiempoAgotado) {
                jugador.golpeado();
                tiempoAgotado = true;
                hud.tiempoMundo = 0;
            }

            if (gameOver()) {
                datos.guardar();
                juego.setScreen(new PantallaGameOver(juego));
                dispose();
            }

            if (derrotado()) {
                datos.guardar();
                juego.setScreen(new Pantalla(juego));
                dispose();
            }

        }
    }

    public boolean derrotado() {
        if (jugador.estadoActual == Jack.State.MORIR && jugador.getTiempoEstado() > 3 && Hud.getVidas() > 0)
            return true;
        else
            return false;
    }

    public boolean gameOver() {
        if (jugador.estadoActual == Jack.State.MORIR && jugador.getTiempoEstado() > 3 && Hud.getVidas() <= 0)
            return true;
        else
            return false;
    }

    @Override
    public void resize(int width, int height) {
        puerto.update(width, height);
        Gdx.app.log("Estado", "resize");
    }

    public TiledMap getMapa() {
        return mapa;
    }

    public World getMundo() {
        return mundo;
    }

    @Override
    public void pause() {
        Gdx.app.log("Estado", "pause");

    }

    @Override
    public void resume() {
        Gdx.app.log("Estado", "resume");
    }

    @Override
    public void hide() {
        Gdx.app.log("Estado", "hide");
    }

    @Override
    public void dispose() {
        Gdx.app.log("Estado", "dispose");
        //this.dispose();
        mapa.dispose();
        mundo.dispose();
        renderer.dispose();
        //b2dr.dispose();
        hud.dispose();
    }
}