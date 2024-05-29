package org.example.tp1paradigmasheviashih;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private final int rows;
    private final int cols;
    private final ArrayList<Posicion> celdaIncendiadas;

    public Grid (int cols , int rows){
        this.cols = cols;
        this.rows = rows;
        this.celdaIncendiadas = new ArrayList<>();
    }

    public boolean isValid(Posicion nuevaPosicion){
        int row = nuevaPosicion.getRow();
        int col = nuevaPosicion.getCol();
        return row>=0 && row < rows && col>=0 && col<cols;
    }

    public Posicion getRandomPosition(){
        Random rand = new Random();
        int col = rand.nextInt(cols);
        int row = rand.nextInt(rows);
        return new Posicion(col,row);
    }

    public void agregarCeldaIncendiada(Posicion posicion){
        celdaIncendiadas.add(posicion);
    }

    public boolean estaIncendiada(Posicion posicion){
        return celdaIncendiadas.contains(posicion);
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public ArrayList<Posicion> getCeldasIncendiadas() {
        return this.celdaIncendiadas;
    }

    public void apagarCeldas() {
        this.celdaIncendiadas.clear();
    }
}
