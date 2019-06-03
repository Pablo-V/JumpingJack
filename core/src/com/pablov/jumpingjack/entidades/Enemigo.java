package com.pablov.jumpingjack.entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pablov.jumpingjack.pantallas.Pantalla;

public abstract class Enemigo extends Sprite {
    protected World mundo;
    protected Pantalla pantalla;
    public Body cuerpo;

    public Enemigo(Pantalla pantalla, float x, float y) {
        this.mundo = pantalla.getMundo();
        this.pantalla = pantalla;
        setPosition(x, y);
        definirEnemigo();
    }

    protected abstract void definirEnemigo();
}
