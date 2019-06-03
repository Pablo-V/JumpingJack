package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Raton extends Enemigo {

    private float tiempoEstado;
    private Animation<TextureRegion> animCaminar;
    private Array<TextureRegion> fotogramas;

    public Raton(Pantalla pantalla, float x, float y) {
        super(pantalla, x, y);
        fotogramas = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            fotogramas.add(new TextureRegion(pantalla.getAtlas().findRegion("raton/raton_para"), 1, 1, 59, 35));
        animCaminar = new Animation<TextureRegion>(0.3f, fotogramas);
        tiempoEstado = 0;
        setBounds(getX(), getY(), 59 / Juego.PPM, 35 / Juego.PPM);
    }

    public void actualizar(float delta) {
        tiempoEstado += delta;
        setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
        setRegion(animCaminar.getKeyFrame(tiempoEstado, true));
    }

    @Override
    protected void definirEnemigo() {
        BodyDef defCuerpo = new BodyDef();
        defCuerpo.position.set(140 / Juego.PPM, 770 / Juego.PPM);
        defCuerpo.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(defCuerpo);

        FixtureDef defFijacion = new FixtureDef();
        PolygonShape forma = new PolygonShape();
        Rectangle rect = new Rectangle();
        rect.width = 59;
        rect.height = 35;
        forma.setAsBox(rect.getWidth() / 2 / Juego.PPM, rect.getHeight() / 2 / Juego.PPM);
        defFijacion.shape = forma;
        cuerpo.createFixture(defFijacion);
    }
}
