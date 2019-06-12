package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.escenas.Hud;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Puerta extends ObjetoInteractivo {

    protected Pantalla pantalla;
    private boolean tocada;

    public Puerta(Pantalla pantalla, Rectangle bordes) {
        super(pantalla, bordes);
        this.pantalla = pantalla;
        fijacion.setUserData(this);
        setFiltroCategoria(Juego.BIT_OBJETO);
        defFijacion.filter.categoryBits = Juego.BIT_OBJETO;
    }

    @Override
    public void golpeCabeza() {

    }

    @Override
    public void tocarPies() {
        if (!tocada) {
            Gdx.app.log("Puerta", "Colision");
            Hud.setNivel(Hud.getNivel() + 1);
            pantalla.datos.guardar();
            setFiltroCategoria(Juego.BIT_DESTRUIDO);
            tocada = true;
        }
        pantalla = new Pantalla(pantalla.juego);
        pantalla.juego.setScreen(pantalla);
    }
}
