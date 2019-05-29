package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Bloque extends ObjetoInteractivo {
    public Bloque(World mundo, TiledMap mapa, Rectangle bordes) {
        super(mundo, mapa, bordes);
    }
}
