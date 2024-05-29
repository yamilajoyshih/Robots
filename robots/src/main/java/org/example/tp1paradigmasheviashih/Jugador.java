package org.example.tp1paradigmasheviashih;

public class Jugador {
    private Posicion posicion;
    private int teleportSafelyRestantes;

    public Jugador(int col, int row){
        this.posicion = new Posicion(col, row);
        this.teleportSafelyRestantes = 3;
    }

    public void moverJugador(int dx, int dy, Grid grid){
        Posicion nuevaPosicion = new Posicion(this.posicion.getCol() + dx,this.posicion.getRow() + dy);
        if (grid.isValid(nuevaPosicion)){
            this.posicion = nuevaPosicion;
        }
    }

    public Posicion getPosition(){
        return this.posicion;
    }

    public void teleportRandom(Grid grid){
        this.posicion = grid.getRandomPosition();
    }

    public int cantidadTeleportSafely(){
        return teleportSafelyRestantes;
    }

    public void aumentarTeleportSafely(){
        teleportSafelyRestantes++;
    }

    public void teleportSafely(int row, int col, Grid grid) {
        if (teleportSafelyRestantes > 0) {
            Posicion nuevaPosicion = new Posicion(col, row);
            if (grid.isValid(nuevaPosicion)) {
                this.posicion = nuevaPosicion;
                teleportSafelyRestantes--;
            }
        }
    }
}

