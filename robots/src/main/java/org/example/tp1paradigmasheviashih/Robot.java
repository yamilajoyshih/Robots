package org.example.tp1paradigmasheviashih;

public abstract class Robot {
    protected Posicion posicion;

    public Robot(int col, int row) {
        this.posicion = new Posicion(col, row);
    }

    public abstract void moveTowardsPlayer(Jugador jugador, Grid grid);

    public Posicion getPosicion() {
        return this.posicion;
    }
}

