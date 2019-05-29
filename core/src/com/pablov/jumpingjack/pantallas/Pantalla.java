package com.pablov.jumpingjack.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.ColisionMapa;
import com.pablov.jumpingjack.escenas.Hud;
import com.pablov.jumpingjack.personajes.Jack;

public class Pantalla implements Screen {
    private Juego juego;
    private OrthographicCamera camara;
    private Viewport puerto;
    private Hud hud;
    private Jack jugador;
    FPSLogger fpsLogger;

    //Variables mapa .tmx
    private TmxMapLoader cargadorMapa;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;

    //Variables Box2D
    private World mundo;
    private Box2DDebugRenderer b2dr;

    public Pantalla(Juego juego) {
        this.juego = juego;
        camara = new OrthographicCamera();
        puerto = new FitViewport(Juego.ANCHO_V / Juego.PPM, Juego.ALTO_V / Juego.PPM, camara);
        hud = new Hud(juego.batch);

        cargadorMapa = new TmxMapLoader();
        mapa = cargadorMapa.load("Mapa1.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa, 1 / Juego.PPM);

        camara.position.set(puerto.getWorldWidth() / 2 / Juego.PPM, 700 / Juego.PPM, 0);

        mundo = new World(new Vector2(0, -16), true);
        jugador = new Jack(mundo);
        b2dr = new Box2DDebugRenderer();

        BodyDef defCuerpo = new BodyDef();
        PolygonShape forma = new PolygonShape();
        FixtureDef fijacion = new FixtureDef();
        Body cuerpo;

        ColisionMapa.crearFormas(mapa, 70, mundo);

        /*Obtener objetos rectangulares de capa Terreno
        for(MapObject objeto : mapa.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();

            defCuerpo.type = BodyDef.BodyType.StaticBody;
            defCuerpo.position.set((rect.getX() + rect.getWidth() / 2) / Juego.PPM, (rect.getY() + rect.getHeight() / 2) / Juego.PPM);

            cuerpo = mundo.createBody(defCuerpo);

            forma.setAsBox(rect.getWidth() / 2 / Juego.PPM, rect.getHeight() / 2 / Juego.PPM);
            fijacion.shape = forma;
            cuerpo.createFixture(fijacion);
        }*/

        for(MapObject objeto : mapa.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();

            defCuerpo.type = BodyDef.BodyType.StaticBody;
            defCuerpo.position.set((rect.getX() + rect.getWidth() / 2) / Juego.PPM, (rect.getY() + rect.getHeight() / 2) / Juego.PPM);

            cuerpo = mundo.createBody(defCuerpo);

            forma.setAsBox(rect.getWidth() / 2 / Juego.PPM, rect.getHeight() / 2 / Juego.PPM);
            fijacion.shape = forma;
            cuerpo.createFixture(fijacion);
        }

        /*//Obtener objetos poligonales de capa Terreno
        for(MapObject objeto : mapa.getLayers().get(1).getObjects().getByType(PolygonMapObject.class)) {
            PolygonShape pol = new PolygonShape();
            float[] vertices = ((PolygonMapObject) objeto).getPolygon().getTransformedVertices();

            float[] verticesMundo = new float[vertices.length];

            for (int i = 0; i < vertices.length; ++i) {
                //System.out.println(vertices[i]);
                verticesMundo[i] = vertices[i] / 2; //70 son los pixeles por baldosa (tile)
            }

            pol.set(verticesMundo);
            defCuerpo.type = BodyDef.BodyType.StaticBody;
            cuerpo = mundo.createBody(defCuerpo);
            fijacion.shape = pol;
            cuerpo.createFixture(fijacion);
        }*/

        //camara.viewportWidth = 700;
        //camara.viewportHeight = 350;
        //puerto.apply();
        fpsLogger = new FPSLogger();
    }

    @Override
    public void show() {

    }

    public void gestionarEntrada(float delta) {
        /*if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camara.position.x -= 500 * delta / Juego.PPM;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camara.position.x += 500 * delta / Juego.PPM;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            camara.position.y += 500 * delta / Juego.PPM;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camara.position.y -= 500 * delta / Juego.PPM;*/
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            jugador.cuerpo.applyLinearImpulse(new Vector2(0, 11f), jugador.cuerpo.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.D) && jugador.cuerpo.getLinearVelocity().x <= 5)
            jugador.cuerpo.applyLinearImpulse(new Vector2(0.5f, 0), jugador.cuerpo.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.A) && jugador.cuerpo.getLinearVelocity().x >= -5)
            jugador.cuerpo.applyLinearImpulse(new Vector2(-0.5f, 0), jugador.cuerpo.getWorldCenter(), true);
    }

    public void actualizar(float delta) {
        gestionarEntrada(delta);

        mundo.step(1/60f, 8, 3);

        camara.position.x = jugador.cuerpo.getPosition().x;
        camara.position.y = jugador.cuerpo.getPosition().y;

        renderer.setView(camara);
        camara.update();
    }

    @Override
    public void render(float delta) {
        actualizar(delta);

        fpsLogger.log();

        Gdx.gl.glClearColor(66/255f, 176/255f, 244/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(mundo, camara.combined);

        juego.batch.setProjectionMatrix(hud.escenario.getCamera().combined);
        hud.escenario.draw();
    }

    @Override
    public void resize(int width, int height) {
        puerto.update(width, height);
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
        mundo.dispose();
        renderer.dispose();
    }
}
