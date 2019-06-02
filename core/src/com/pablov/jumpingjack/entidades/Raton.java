package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Raton extends Enemigo {

    public Raton(Pantalla pantalla, float x, float y) {
        super(pantalla, x, y);
    }

    @Override
    protected void definirEnemigo() {
        BodyDef defCuerpo = new BodyDef();
        defCuerpo.position.set(70 / Juego.PPM, 770 / Juego.PPM);
        defCuerpo.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(defCuerpo);

        FixtureDef defFijacion = new FixtureDef();
        CircleShape circulo1 = new CircleShape();
        CircleShape circulo2 = new CircleShape();

        defFijacion.filter.categoryBits = Juego.BIT_ENEMIGO;
        defFijacion.filter.maskBits = Juego.BIT_SUELO | Juego.BIT_MONEDA
                | Juego.BIT_BLOQUE | Juego.BIT_OBJETO | Juego.BIT_ENEMIGO;
        circulo1.setRadius(20 / Juego.PPM);
        circulo1.setPosition(new Vector2(-10, 0 / Juego.PPM));
        defFijacion.shape = circulo1;
        cuerpo.createFixture(defFijacion);
        circulo2.setRadius(20 / Juego.PPM);
        circulo2.setPosition(new Vector2(10, 0 / Juego.PPM));
        defFijacion.shape = circulo2;
        cuerpo.createFixture(defFijacion);
    }
}
