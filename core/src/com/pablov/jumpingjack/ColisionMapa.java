package com.pablov.jumpingjack;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class ColisionMapa {

    // Píxeles por tile (baldosa), esto cambiará en función del valor dado en el constructor
    private static float ppt = 0;


    public static Array<Body> crearFormas(Map map, float pixeles, World world) {
        ppt = pixeles;
        MapObjects objetos = map.getLayers().get(2).getObjects();

        Array<Body> cuerpos = new Array<Body>();

        for(MapObject objeto : objetos) {

            if (objeto instanceof TextureMapObject) {
                continue;
            }

            Shape forma;

            if (objeto instanceof RectangleMapObject) {
                forma = getRectangle((RectangleMapObject)objeto);
            }
            else if (objeto instanceof PolygonMapObject) {
                forma = getPolygon((PolygonMapObject)objeto);
            }
            else if (objeto instanceof PolylineMapObject) {
                forma = getPolyline((PolylineMapObject)objeto);
            }
            else if (objeto instanceof CircleMapObject) {
                forma = getCircle((CircleMapObject)objeto);
            }
            else {
                continue;
            }

            BodyDef defCuerpo = new BodyDef();
            defCuerpo.type = BodyDef.BodyType.StaticBody;
            Body cuerpo = world.createBody(defCuerpo);
            cuerpo.createFixture(forma, 1);

            cuerpos.add(cuerpo);

            forma.dispose();
        }
        return cuerpos;
    }

    private static PolygonShape getRectangle(RectangleMapObject objetoRectangulo) {
        Rectangle rect = objetoRectangulo.getRectangle();
        PolygonShape poligono = new PolygonShape();
        Vector2 size = new Vector2((rect.x + rect.width * 0.5f) / ppt,
                (rect.y + rect.height * 0.5f ) / ppt);
        poligono.setAsBox(rect.width * 0.5f / ppt,
                rect.height * 0.5f / ppt,
                size,
                0.0f);
        return poligono;
    }

    private static CircleShape getCircle(CircleMapObject objetoCirculo) {
        Circle circulo = objetoCirculo.getCircle();
        CircleShape formaCircular = new CircleShape();
        formaCircular.setRadius(circulo.radius / ppt);
        formaCircular.setPosition(new Vector2(circulo.x / ppt, circulo.y / ppt));
        return formaCircular;
    }

    private static PolygonShape getPolygon(PolygonMapObject objetoPoligono) {
        PolygonShape poligono = new PolygonShape();
        float[] vertices = objetoPoligono.getPolygon().getTransformedVertices();

        float[] verticesMundo = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            verticesMundo[i] = vertices[i] / ppt;
        }

        poligono.set(verticesMundo);
        return poligono;
    }

    private static ChainShape getPolyline(PolylineMapObject objetoMultilinea) {
        float[] vertices = objetoMultilinea.getPolyline().getTransformedVertices();
        Vector2[] verticesMundo = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            verticesMundo[i] = new Vector2();
            verticesMundo[i].x = vertices[i * 2] / ppt;
            verticesMundo[i].y = vertices[i * 2 + 1] / ppt;
        }

        ChainShape formaCadena = new ChainShape();
        formaCadena.createChain(verticesMundo);
        return formaCadena;
    }
}