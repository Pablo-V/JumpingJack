package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.pantallas.Pantalla;

public class Muelle extends ObjetoInteractivo {

    protected Pantalla pantalla;

    public Muelle(Pantalla pantalla, Rectangle bordes) {
        super(pantalla, bordes);
        this.pantalla = pantalla;
        fijacion.setUserData(this);
        setFiltroCategoria(Juego.BIT_OBJETO);
    }

    @Override
    public void golpeCabeza() {

    }

    @Override
    public void tocarPies() {
        Gdx.app.log("Muelle", "Colision");
        //Aplicar fuerza contraria a la velocidad de caída para contrarrestar la gravedad,
        //necesaria para poder aplicar un impulso vertical sin reducción de altura.
        pantalla.jugador.cuerpo.applyLinearImpulse(new Vector2(0f, pantalla.jugador.cuerpo.getLinearVelocity().y * -1), pantalla.jugador.cuerpo.getWorldCenter(), true);
        pantalla.jugador.cuerpo.applyLinearImpulse(new Vector2(0f, 18f), pantalla.jugador.cuerpo.getWorldCenter(), true);
    }
}
