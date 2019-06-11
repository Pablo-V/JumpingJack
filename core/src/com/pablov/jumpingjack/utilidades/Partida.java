package com.pablov.jumpingjack.utilidades;

public class Partida {
    private int nivel;
    private int vidas;
    private int puntos;

    public Partida() {

    }

    public Partida(int nivel, int vidas, int puntos) {
        setNivel(nivel);
        setVidas(vidas);
        setPuntos(puntos);
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
