package com.pablov.jumpingjack.utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.pablov.jumpingjack.escenas.Hud;

import java.util.ArrayList;
import java.util.Base64;

public class DatosJuego {
    public Preferences prefs;

    public DatosJuego() {
        prefs = Gdx.app.getPreferences("jumpingjack_datos");
    }

    public void nuevo() {
        prefs.putInteger("nivel", 1);
        prefs.putInteger("vidas", 3);
        prefs.putInteger("puntos", 0);
        prefs.flush();
    }

    public void guardar() {
        prefs.putInteger("nivel", Hud.getNivel());
        prefs.putInteger("vidas", Hud.getVidas());
        prefs.putInteger("puntos", Hud.getPuntos());
        prefs.flush();
    }
}