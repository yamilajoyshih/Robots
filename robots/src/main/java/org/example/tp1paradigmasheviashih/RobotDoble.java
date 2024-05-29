package org.example.tp1paradigmasheviashih;

class RobotDoble extends Robot {

    public RobotDoble(int col, int row) {
        super(col, row);
    }

    @Override
    public void moveTowardsPlayer(Jugador jugador, Grid grid) {
        Posicion posicionJugador = jugador.getPosition();

        int jugadorRow = posicionJugador.getRow();
        int jugadorCol = posicionJugador.getCol();

        int direccionMovimientoX = Integer.compare(jugadorCol, this.posicion.getCol());
        int direccionMovimientoY = Integer.compare(jugadorRow, this.posicion.getRow());
        int dx = 0;
        int dy = 0;
        if(direccionMovimientoX > 0){
            dx = 2;
        } else if(direccionMovimientoX < 0){
            dx = -2;
        }
        if(direccionMovimientoY > 0){
            dy = 2;
        } else if(direccionMovimientoY < 0){
            dy = -2;
        }

        Posicion nuevaPosicion = new Posicion(this.posicion.getCol()+dx, this.posicion.getRow()+dy);
        if (grid.isValid(nuevaPosicion)) {
            this.posicion = nuevaPosicion;
        } else {
            nuevaPosicion = new Posicion(this.posicion.getCol()+(dx/2), this.posicion.getRow()+(dy/2));
            if(grid.isValid(nuevaPosicion)) {
                this.posicion = nuevaPosicion;
            }
        }
    }
}
