package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.Juego;

public abstract class ObjetoInteractivo {
    protected World mundo;
    protected TiledMap mapa;
    protected TiledMapTile tile;
    protected Rectangle bordes;
    protected Body cuerpo;

    public ObjetoInteractivo(World mundo, TiledMap mapa, Rectangle bordes) {
        this.mundo = mundo;
        this.mapa = mapa;
        this.bordes = bordes;

        BodyDef defCuerpo = new BodyDef();
        FixtureDef fijacion = new FixtureDef();
        PolygonShape forma = new PolygonShape();

        defCuerpo.type = BodyDef.BodyType.StaticBody;
        defCuerpo.position.set((bordes.getX() + bordes.getWidth() / 2) / Juego.PPM, (bordes.getY() + bordes.getHeight() / 2) / Juego.PPM);
        cuerpo = mundo.createBody(defCuerpo);
        forma.setAsBox(bordes.getWidth() / 2 / Juego.PPM, bordes.getHeight() / 2 / Juego.PPM);
        fijacion.shape = forma;
        cuerpo.createFixture(fijacion);
    }
}
