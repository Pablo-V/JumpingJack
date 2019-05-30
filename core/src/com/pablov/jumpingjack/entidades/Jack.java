package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Jack extends Sprite {
    public World mundo;
    public Body cuerpo;
    private TextureRegion jackParado;

    public Jack(World mundo, Pantalla pantalla) {
        super(pantalla.getAtlas().findRegion("jack"));
        this.mundo = mundo;
        definirJack();
        jackParado = new TextureRegion(getTexture(), 1, 127, 66, 92);
        setBounds(0, 0, 66 / Juego.PPM, 92 / Juego.PPM);
        setRegion(jackParado);
    }

    public void actualizar(float delta) {
        setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
    }

    public void definirJack() {
        BodyDef defCuerpo = new BodyDef();
        defCuerpo.position.set(70 / Juego.PPM, 770 / Juego.PPM);
        defCuerpo.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(defCuerpo);

        FixtureDef fijacion = new FixtureDef();
        PolygonShape forma = new PolygonShape();
        Rectangle rect = new Rectangle();
        rect.width = 66;
        rect.height = 88;
        forma.setAsBox(rect.getWidth() / 2 / Juego.PPM, rect.getHeight() / 2 / Juego.PPM);
        fijacion.shape = forma;
        cuerpo.createFixture(fijacion);
    }
}
