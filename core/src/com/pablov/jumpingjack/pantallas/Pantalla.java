package com.pablov.jumpingjack.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.entidades.Jack;
import com.pablov.jumpingjack.escenas.Hud;
import com.pablov.jumpingjack.utilidades.ColisionMapa;
import com.pablov.jumpingjack.utilidades.DetectorContactoMundo;

public class Pantalla implements Screen {
    private Juego juego;
    private TextureAtlas atlas;
    private OrthographicCamera camara;
    private Viewport puerto;
    private Hud hud;
    public Jack jugador;
    FPSLogger fpsLogger;

    //Variables mapa .tmx
    private TmxMapLoader cargadorMapa;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;

    //Variables Box2D
    private World mundo;
    private Box2DDebugRenderer b2dr;

    public Pantalla(Juego juego) {
        atlas = new TextureAtlas("jack.pack");
        this.juego = juego;
        camara = new OrthographicCamera();
        puerto = new FitViewport(Juego.ANCHO_V / Juego.PPM, Juego.ALTO_V / Juego.PPM, camara);
        hud = new Hud(juego.batch);

        cargadorMapa = new TmxMapLoader();
        mapa = cargadorMapa.load("Mapa1.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa, 1 / Juego.PPM);

        camara.position.set(puerto.getWorldWidth() / 2 / Juego.PPM, 700 / Juego.PPM, 0);

        mundo = new World(new Vector2(0, -17), true);

        b2dr = new Box2DDebugRenderer();

        new ColisionMapa(this);

        jugador = new Jack(this);

        mundo.setContactListener(new DetectorContactoMundo());

        fpsLogger = new FPSLogger();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void gestionarEntrada(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jugador.cuerpo.getLinearVelocity().y == 0)
            jugador.cuerpo.applyLinearImpulse(new Vector2(0, 11f), jugador.cuerpo.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.D) && jugador.cuerpo.getLinearVelocity().x <= 5)
            jugador.cuerpo.applyLinearImpulse(new Vector2(0.5f, 0), jugador.cuerpo.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.A) && jugador.cuerpo.getLinearVelocity().x >= -5)
            jugador.cuerpo.applyLinearImpulse(new Vector2(-0.5f, 0), jugador.cuerpo.getWorldCenter(), true);
    }

    public void actualizar(float delta) {
        gestionarEntrada(delta);

        mundo.step(1 / 60f, 8, 3);

        jugador.actualizar(delta);
        hud.actualizar(delta);

        camara.position.x = jugador.cuerpo.getPosition().x;
        camara.position.y = jugador.cuerpo.getPosition().y;

        renderer.setView(camara);
        camara.update();
    }

    @Override
    public void render(float delta) {
        actualizar(delta);

        fpsLogger.log();

        Gdx.gl.glClearColor(66 / 255f, 176 / 255f, 244 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(mundo, camara.combined);

        juego.batch.setProjectionMatrix(camara.combined);
        juego.batch.begin();
        jugador.draw(juego.batch);
        juego.batch.end();
        juego.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
        hud.escenario.draw();
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
        mapa.dispose();
        mundo.dispose();
        renderer.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}