package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.escenas.Hud;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Moneda extends ObjetoInteractivo {
    private static TiledMapTileSet tileSet;
    //IMPORTANTE: LOS ID DE TILES SEGUN LIBGDX EMPIEZAN EN 1, NO EN 0 COMO HACE TILED!!!
    private final int MONEDA_VACIA = 109;
    public Moneda(Pantalla pantalla, Rectangle bordes) {
        super(pantalla, bordes);
        tileSet = mapa.getTileSets().getTileSet("TileSet");
        fijacion.setUserData(this);
        setFiltroCategoria(Juego.BIT_MONEDA);
    }

    @Override
    public void golpeCabeza() {
        Gdx.app.log("Moneda", "Colision");
        if (getCelda().getTile().getId() != MONEDA_VACIA) {
            getCelda().setTile(tileSet.getTile(MONEDA_VACIA));
            Hud.anadirPuntos(150);
        }
    }

    @Override
    public void tocarPies() {

    }
}
