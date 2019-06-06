package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.escenas.Hud;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Raton extends Enemigo {

    private float tiempoEstado;
    private Animation<TextureRegion> animCaminar;
    private Array<TextureRegion> fotogramas;
    private boolean marcadoParaDestruir, destruido, invertidoX, invertidoY;

    public Raton(Pantalla pantalla, float x, float y) {
        super(pantalla, x, y);
        fotogramas = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            TextureRegion region = new TextureRegion(pantalla.getAtlas().findRegion("raton/raton_para"), 1 + 61 * i, 1, 59, 35);
            region.flip(true, false);
            fotogramas.add(region);
        }
        animCaminar = new Animation<TextureRegion>(0.233f, fotogramas);
        tiempoEstado = 0;
        setBounds(getX(), getY(), 59 / Juego.PPM, 35 / Juego.PPM);
        marcadoParaDestruir = false;
        destruido = false;
    }

    public void actualizar(float delta) {
        tiempoEstado += delta;
        TextureRegion regionMuerto = new TextureRegion(pantalla.getAtlas().findRegion("raton/raton_muere"), 1, 1, 59, 35);
        //Inversión inicial para que el ratón mire a la derecha en principio
        regionMuerto.flip(true, false);
        if (invertirX) {
            invertirX = false;
            invertidoX = !invertidoX;
            for (int i = 0; i < 2; i++)
                fotogramas.get(i).flip(true, false);
        }
        if (marcadoParaDestruir && !destruido) {
            mundo.destroyBody(cuerpo);
            destruido = true;
            regionMuerto.flip(invertidoX, true);
            setRegion(regionMuerto);
            tiempoEstado = 0;
            Hud.anadirPuntos(500);
        } else if (!destruido) {
            cuerpo.setLinearVelocity(velocidad);
            setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
            setRegion(animCaminar.getKeyFrame(tiempoEstado, true));
        }
    }

    @Override
    protected void definirEnemigo() {
        BodyDef defCuerpo = new BodyDef();
        defCuerpo.position.set(getX(), getY());
        defCuerpo.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(defCuerpo);

        FixtureDef defFijacion = new FixtureDef();

        CircleShape circulo1 = new CircleShape();
        CircleShape circulo2 = new CircleShape();

        circulo1.setRadius(20 / Juego.PPM);
        circulo1.setPosition(new Vector2(-10.5f / Juego.PPM, 3.5f / Juego.PPM));
        defFijacion.shape = circulo1;
        defFijacion.filter.categoryBits = Juego.BIT_ENEMIGO;
        cuerpo.createFixture(defFijacion).setUserData(this);
        circulo2.setRadius(20 / Juego.PPM);
        circulo2.setPosition(new Vector2(10.5f / Juego.PPM, 3.5f / Juego.PPM));
        defFijacion.shape = circulo2;
        defFijacion.filter.categoryBits = Juego.BIT_ENEMIGO;
        cuerpo.createFixture(defFijacion).setUserData(this);

        PolygonShape cabeza = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-21, 35).scl(1 / Juego.PPM);
        vertices[1] = new Vector2(21, 35).scl(1 / Juego.PPM);
        vertices[2] = new Vector2(0, 20).scl(1 / Juego.PPM);
        vertices[3] = new Vector2(0, 20).scl(1 / Juego.PPM);
        cabeza.set(vertices);

        defFijacion.shape = cabeza;
        //Restitución: provoca una fuerza de rebote en la dirección contraria a la colisión,
        //0.5f indica que al saltar encima del ratón, Jack subirá un 50% de la fuerza de caída
        defFijacion.restitution = 0.5f;
        defFijacion.filter.categoryBits = Juego.BIT_CABEZA_ENEMIGO;
        cuerpo.createFixture(defFijacion).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        //Deja de dibujar el ratón pasado un segundo tras ser derrotado
        if (!destruido || tiempoEstado < 1)
            super.draw(batch);
    }

    @Override
    //Aquí no se puede eliminar la textura del enemigo ni su cuerpo, debido a que este método es
    //llamado por DetectorContactoMundo y posteriormente también por Pantalla.mundo.step(), por lo
    //que si el enemigo esta en medio de una colisión, la simulación de físicas fallaría.
    //Por tanto, se utilizará un boolean para marcar que el enemigo está pendiente de eliminación.
    public void golpeadoEnCabeza() {
        marcadoParaDestruir = true;
    }
}
