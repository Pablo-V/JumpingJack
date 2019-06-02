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
import com.pablov.jumpingjack.entidades.Sorpresa;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class ColisionMapa {
    public ColisionMapa(Pantalla pantalla) {
        World mundo = pantalla.getMundo();
        TiledMap mapa = pantalla.getMapa();
        BodyDef defCuerpo = new BodyDef();
        PolygonShape forma = new PolygonShape();
        FixtureDef defFijacion = new FixtureDef();
        Body cuerpo;

        //Obtener objetos rectangulares de capa Terreno
        for (MapObject objeto : mapa.getLayers().get("Terreno").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            defCuerpo = new BodyDef();
            defFijacion = new FixtureDef();
            forma = new PolygonShape();

            defCuerpo.type = BodyDef.BodyType.StaticBody;
            defCuerpo.position.set((rect.getX() + rect.getWidth() / 2) / Juego.PPM, (rect.getY() + rect.getHeight() / 2) / Juego.PPM);
            cuerpo = mundo.createBody(defCuerpo);
            forma.setAsBox(rect.getWidth() / 2 / Juego.PPM, rect.getHeight() / 2 / Juego.PPM);
            defFijacion.shape = forma;
            cuerpo.createFixture(defFijacion);
        }

        //Obtener objetos moneda de capas Sorpesas y Monedas
        for (MapObject objeto : mapa.getLayers().get("Sorpresas").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Sorpresa(pantalla, rect);
        }
        for (MapObject objeto : mapa.getLayers().get("Monedas").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Moneda(pantalla, rect);
        }

        //Obtener objetos bloque de capa Bloques
        for (MapObject objeto : mapa.getLayers().get("Bloques").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
            new Bloque(pantalla, rect);
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