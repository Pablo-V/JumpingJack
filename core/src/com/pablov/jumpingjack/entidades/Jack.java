package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.Juego;

public class Jack extends Sprite {
    public World mundo;
    public Body cuerpo;

    public Jack(World mundo) {
        this.mundo = mundo;
        definirJack();
    }

    public void definirJack() {
        BodyDef defCuerpo = new BodyDef();
        defCuerpo.position.set(70 / Juego.PPM, 770 / Juego.PPM);
        defCuerpo.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(defCuerpo);

        FixtureDef fijacion = new FixtureDef();
        CircleShape forma = new CircleShape();
        forma.setRadius(28 / Juego.PPM);

        fijacion.shape = forma;
        cuerpo.createFixture(fijacion);
    }
}
