package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Jack extends Sprite {
    public enum State {CAER, SALTAR, PARAR, CORRER}

    public State estadoActual;
    public State estadoAnterior;
    public World mundo;
    public Body cuerpo;
    private TextureRegion jackParado;
    private Animation<TextureRegion> jackCorrer;
    private Animation<TextureRegion> jackSaltar;
    private Animation<TextureRegion> jackCaer;
    private float tiempoEstado;
    private boolean correrDerecha;

    public Jack(World mundo, Pantalla pantalla) {
        super(pantalla.getAtlas().findRegion("jack"));
        this.mundo = mundo;
        estadoActual = State.PARAR;
        estadoAnterior = State.PARAR;
        tiempoEstado = 0;
        correrDerecha = true;

        Array<TextureRegion> fotogramas = new Array<TextureRegion>();
        fotogramas.add(new TextureRegion(getTexture(), 426, 6, 66, 92));
        fotogramas.add(new TextureRegion(getTexture(), 216, 5, 68, 93));
        fotogramas.add(new TextureRegion(getTexture(), 73, 2, 70, 96));
        jackCorrer = new Animation(0.1f, fotogramas);
        fotogramas.clear();
        fotogramas.add(new TextureRegion(getTexture(), 357, 5, 67, 93));
        jackSaltar  = new Animation(0.1f, fotogramas);
        fotogramas.clear();
        fotogramas.add(new TextureRegion(getTexture(), 426, 6, 66, 92));
        jackCaer = new Animation(0.1f, fotogramas);
        fotogramas.clear();
        definirJack();
        jackParado = new TextureRegion(getTexture(), 630, 6, 66, 92);
        setBounds(0, 0, 66 / Juego.PPM, 92 / Juego.PPM);
        setRegion(jackParado);
    }

    public void actualizar(float delta) {
        setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
        setRegion(getFotograma(delta));
    }

    public TextureRegion getFotograma(float delta) {
        estadoActual = getEstado();
        TextureRegion region;
        switch(estadoActual){
            case SALTAR:
                region = jackSaltar.getKeyFrame(tiempoEstado);
                break;
            case CORRER:
                region = jackCorrer.getKeyFrame(tiempoEstado, true);
                break;
            case CAER:
                region = jackCorrer.getKeyFrame(tiempoEstado);
                break;
            case PARAR:
            default:
                region = jackParado;
                break;
        }
        if((cuerpo.getLinearVelocity().x < 0 || !correrDerecha) && !region.isFlipX()) {
            region.flip(true, false);
            correrDerecha = false;
        }
        else if((cuerpo.getLinearVelocity().x > 0 || correrDerecha) && region.isFlipX()) {
            region.flip(true, false);
            correrDerecha = true;
        }

        //Es el estado actual igual al estado anterior?
        //Si es verdadero, el tiempo del estado es igual al mismo sumado con el delta
        //Si es falso, el tiempo del estado es igual a 0
        tiempoEstado = estadoActual == estadoAnterior ? tiempoEstado + delta : 0;
        estadoAnterior = estadoActual;
        return region;
    }

    public State getEstado() {
        if (cuerpo.getLinearVelocity().y > 0 || (cuerpo.getLinearVelocity().y < 0 && estadoAnterior == State.SALTAR))
            return State.SALTAR;
        else if (cuerpo.getLinearVelocity().y < 0)
            return State.CAER;
        else if (cuerpo.getLinearVelocity().x != 0)
            return State.CORRER;
        else
            return State.PARAR;
    }

    public void definirJack() {
        BodyDef defCuerpo = new BodyDef();
        defCuerpo.position.set(70 / Juego.PPM, 770 / Juego.PPM);
        defCuerpo.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(defCuerpo);
        FixtureDef fijacion = new FixtureDef();
        CircleShape circulo1 = new CircleShape();
        CircleShape circulo2 = new CircleShape();
        circulo1.setRadius(33 / Juego.PPM);
        circulo1.setPosition(new Vector2(0, -13 / Juego.PPM));
        fijacion.shape = circulo1;
        cuerpo.createFixture(fijacion);
        circulo2.setRadius(33 / Juego.PPM);
        circulo2.setPosition(new Vector2(0, 13 / Juego.PPM));
        fijacion.shape = circulo2;
        cuerpo.createFixture(fijacion);
    }
}
