package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.Juego;

public class Moneda extends ObjetoInteractivo {
    public Moneda(World mundo, TiledMap mapa, Rectangle bordes) {
        super(mundo, mapa, bordes);
    }
}
