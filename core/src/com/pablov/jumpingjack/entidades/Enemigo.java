package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.pantallas.Pantalla;

public abstract class Enemigo extends Sprite {
    protected World mundo;
    protected Pantalla pantalla;
    public Body cuerpo;
    public Vector2 velocidad;
    protected boolean invertirX, invertirY;

    public Enemigo(Pantalla pantalla, float x, float y) {
        this.mundo = pantalla.getMundo();
        this.pantalla = pantalla;
        invertirX = false;
        invertirY = false;
        setPosition(x, y);
        definirEnemigo();
        velocidad = new Vector2(2.5f, -6f);
    }

    protected abstract void definirEnemigo();

    public abstract void golpeadoEnCabeza();

    public void invertirVelocidad(boolean invertirX, boolean invertirY) {
        if (invertirX) {
            velocidad.x = -velocidad.x;
            this.invertirX = invertirX;
        }
        if (invertirY) {
            velocidad.y = -velocidad.y;
            this.invertirY = invertirY;
        }
    }
}
