package com.pablov.jumpingjack.utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pablov.jumpingjack.Juego;
import com.pablov.jumpingjack.entidades.Enemigo;
import com.pablov.jumpingjack.entidades.Jack;
import com.pablov.jumpingjack.entidades.ObjetoInteractivo;
import com.pablov.jumpingjack.entidades.Puerta;
import com.pablov.jumpingjack.escenas.Hud;

/*Esta clase define un ConntactListener personalizado.
 * Un ContactListener es la clase que es llamada cuando dos fijaciones colisionan en Box2D.
 * beginContact se llama cuando las dos fijaciones comienzan a conectar, endContact se llama
 * cuando se termina el contacto, preSolve es llamado cuando ha empezado la colisión y permite
 *hacer cambios directos en el momento, y postSolve se llama cuando se termina la colisión, para
 *dar los resultados de lo que ha pasado debido a la colisión.*/

public class DetectorContactoMundo implements ContactListener {
    @Override
    public void beginContact(Contact contacto) {
        Fixture fijacionA = contacto.getFixtureA();
        Fixture fijacionB = contacto.getFixtureB();

        int defColision = fijacionA.getFilterData().categoryBits | fijacionB.getFilterData().categoryBits;

        if (fijacionA.getUserData() == "cabeza" || fijacionB.getUserData() == "cabeza") {
            Fixture cabeza = fijacionA.getUserData() == "cabeza" ? fijacionA : fijacionB;
            Fixture objeto = cabeza == fijacionA ? fijacionB : fijacionA;

            // Si el objeto no es nulo y extiende de ObjetoInteractivo
            if (objeto.getUserData() != null && ObjetoInteractivo.class.isAssignableFrom(objeto.getUserData().getClass())) {
                ((ObjetoInteractivo) objeto.getUserData()).golpeCabeza();
            }
        }

        if (fijacionA.getUserData() == "pies" || fijacionB.getUserData() == "pies") {
            Fixture pies = fijacionA.getUserData() == "pies" ? fijacionA : fijacionB;
            Fixture objeto = pies == fijacionA ? fijacionB : fijacionA;

            // Si el objeto no es nulo y extiende de ObjetoInteractivo
            if (objeto.getUserData() != null && ObjetoInteractivo.class.isAssignableFrom(objeto.getUserData().getClass())) {
                ((ObjetoInteractivo) objeto.getUserData()).tocarPies();
            }

            if (objeto.getUserData() != null && Puerta.class.isAssignableFrom(objeto.getUserData().getClass())) {
                ((ObjetoInteractivo) objeto.getUserData()).tocarPies();
            }
        }

        switch (defColision) {
            case Juego.BIT_CABEZA_ENEMIGO | Juego.BIT_JACK:
                if (fijacionA.getFilterData().categoryBits == Juego.BIT_CABEZA_ENEMIGO)
                    ((Enemigo) fijacionA.getUserData()).golpeadoEnCabeza();
                else
                    ((Enemigo) fijacionB.getUserData()).golpeadoEnCabeza();
                break;
            case Juego.BIT_ENEMIGO | Juego.BIT_BARRERA:
                if (fijacionA.getFilterData().categoryBits == Juego.BIT_ENEMIGO)
                    ((Enemigo) fijacionA.getUserData()).invertirVelocidad(true, false);
                else
                    ((Enemigo) fijacionB.getUserData()).invertirVelocidad(true, false);
            case Juego.BIT_JACK | Juego.BIT_ENEMIGO:
                if (fijacionA.getUserData() != null && fijacionB.getUserData() != null) {
                    if (fijacionA.getFilterData().categoryBits == Juego.BIT_JACK)
                        ((Jack) fijacionA.getUserData()).golpeado();
                    else
                        ((Jack) fijacionB.getUserData()).golpeado();
                }
                break;
            case Juego.BIT_ENEMIGO:
                if (fijacionA.getUserData() != null)
                    ((Enemigo) fijacionA.getUserData()).invertirVelocidad(true, false);
                ((Enemigo) fijacionB.getUserData()).invertirVelocidad(true, false);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
