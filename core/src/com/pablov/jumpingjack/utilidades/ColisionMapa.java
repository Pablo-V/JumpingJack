package com.pablov.jumpingjack.utilidades;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.entidades.Bloque;
import com.pablov.jumpingjack.entidades.Moneda;

public class ColisionMapa {
    public ColisionMapa(World mundo, TiledMap mapa) {
        BodyDef defCuerpo = new BodyDef();
        PolygonShape forma = new PolygonShape();
        FixtureDef fijacion = new FixtureDef();
        Body cuerpo;

        //Obtener objetos rectangulares de capa Terreno
        for (MapObject objeto : mapa.getLayers().get("Terreno").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Moneda(mundo, mapa, rect);
        }

        //Obtener objetos moneda de capas Sorpesas y Monedas
        for (MapObject objeto : mapa.getLayers().get("Sorpresas").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Moneda(mundo, mapa, rect);
        }
        for (MapObject objeto : mapa.getLayers().get("Monedas").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Moneda(mundo, mapa, rect);
        }

        //Obtener objetos bloque de capa Bloques
        for (MapObject objeto : mapa.getLayers().get("Bloques").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Bloque(mundo, mapa, rect);
        }

        //Obtener objetos poligonales de capa Terreno
        for (MapObject objeto : mapa.getLayers().get("Terreno").getObjects().getByType(PolygonMapObject.class)) {
            PolygonMapObject polMapObject = (PolygonMapObject) objeto;
            float[] vertices = polMapObject.getPolygon().getTransformedVertices();

            float[] verticesMundo = new float[vertices.length];

            for (int i = 0; i < vertices.length; ++i)
                verticesMundo[i] = vertices[i] / Juego.PPM;

            forma.set(verticesMundo);
            defCuerpo = new BodyDef();
            defCuerpo.type = BodyDef.BodyType.StaticBody;
            cuerpo = mundo.createBody(defCuerpo);
            cuerpo.createFixture(forma, 1);
        }
    }
}