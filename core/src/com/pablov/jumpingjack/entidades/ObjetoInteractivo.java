package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.pantallas.Pantalla;

public abstract class ObjetoInteractivo {
    protected World mundo;
    protected TiledMap mapa;
    protected TiledMapTile tile;
    protected Rectangle bordes;
    protected Body cuerpo;
    protected Fixture fijacion;

    public ObjetoInteractivo(Pantalla pantalla, Rectangle bordes) {
        this.mundo = pantalla.getMundo();
        this.mapa = pantalla.getMapa();
        this.bordes = bordes;

        BodyDef defCuerpo = new BodyDef();
        FixtureDef defFijacion = new FixtureDef();
        PolygonShape forma = new PolygonShape();

        defCuerpo.type = BodyDef.BodyType.StaticBody;
        defCuerpo.position.set((bordes.getX() + bordes.getWidth() / 2) / Juego.PPM, (bordes.getY() + bordes.getHeight() / 2) / Juego.PPM);
        cuerpo = mundo.createBody(defCuerpo);
        forma.setAsBox(bordes.getWidth() / 2 / Juego.PPM, bordes.getHeight() / 2 / Juego.PPM);
        defFijacion.shape = forma;
        fijacion = cuerpo.createFixture(defFijacion);
    }

    public abstract void golpeCabeza();

    public abstract void tocarPies();

    public void setFiltroCategoria(short bitFiltro) {
        Filter filtro = new Filter();
        filtro.categoryBits = bitFiltro;
        fijacion.setFilterData(filtro);
    }

    public TiledMapTileLayer.Cell getCelda() {
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("Frente");
        return capa.getCell((int)(cuerpo.getPosition().x), (int)(cuerpo.getPosition().y));
    }
}