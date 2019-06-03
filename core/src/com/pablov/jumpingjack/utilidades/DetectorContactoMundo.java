package com.pablov.jumpingjack.utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pablov.jumpingjack.entidades.ObjetoInteractivo;

/*Esta clase define un ConntactListener personalizado.
* Un ContactListener es la clase que es llamada cuando dos fijaciones colisionan en Box2D.
* beginContact se llama cuando las dos fijaciones comienzan a conectar, endContact se llama
* cuando se termina el contacto, preSolve es llamado cuando ha empezado la colisión y permite
 *hacer cambios directos en el momento, y postSolve se llama cuando se termina la colisión, para
 *dar los resultados de lo que ha pasado debido a la colisión.*/

public class DetectorContactoMundo implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fijacionA = contact.getFixtureA();
        Fixture fijacionB = contact.getFixtureB();

        if(fijacionA.getUserData() == "cabeza" || fijacionB.getUserData() == "cabeza") {
            Fixture cabeza = fijacionA.getUserData() == "cabeza" ? fijacionA : fijacionB;
            Fixture objeto = cabeza == fijacionA ? fijacionB : fijacionA;

            // Si el objeto no es nulo y extiende de ObjetoInteractivo
            if(objeto.getUserData() != null && ObjetoInteractivo.class.isAssignableFrom(objeto.getUserData().getClass())) {
                ((ObjetoInteractivo) objeto.getUserData()).golpeCabeza();
            }
        }

        if(fijacionA.getUserData() == "pies" || fijacionB.getUserData() == "pies") {
            Fixture pies = fijacionA.getUserData() == "pies" ? fijacionA : fijacionB;
            Fixture objeto = pies == fijacionA ? fijacionB : fijacionA;

            // Si el objeto no es nulo y extiende de ObjetoInteractivo
            if(objeto.getUserData() != null && ObjetoInteractivo.class.isAssignableFrom(objeto.getUserData().getClass())) {
                ((ObjetoInteractivo) objeto.getUserData()).tocarPies();
            }
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
