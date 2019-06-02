package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.escenas.Hud;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Bloque extends ObjetoInteractivo {
    public Bloque(Pantalla pantalla, Rectangle bordes) {
        super(pantalla, bordes);
        fijacion.setUserData(this);
        setFiltroCategoria(Juego.BIT_BLOQUE);
    }

    @Override
    public void golpeCabeza() {
        Gdx.app.log("Bloque", "Colision");
        setFiltroCategoria(Juego.BIT_DESTRUIDO);
        getCelda().setTile(null);
        Hud.anadirPuntos(100);
    }
}
