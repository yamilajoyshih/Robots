package org.example.tp1paradigmasheviashih;

import java.util.Objects;

public class Posicion {
    private final int col;
    private final int row;

    public Posicion(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicion posicion = (Posicion) o;
        return col == posicion.col && row == posicion.row;
    }
}
