package org.example.tp1paradigmasheviashih;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


public class MouseEventHandler implements EventHandler<MouseEvent> {
    private final Game juego;
    private final RobotApp robotApp;
    private final GraphicsContext gc;
    static final int tamanoCeldas = 20;

    public MouseEventHandler(RobotApp robotApp, Game juego, GraphicsContext gc) {
        this.juego = juego;
        this.gc = gc;
        this.robotApp = robotApp;
    }

    public void handle(MouseEvent e) {
        double startX = (gc.getCanvas().getWidth() - (juego.cantidadColumnas() * tamanoCeldas)) / 2;
        double startY = (gc.getCanvas().getHeight() - (juego.cantidadFilas() * tamanoCeldas)) / 2;

        double mouseX = e.getX() - startX;
        double mouseY = e.getY() - startY;
        int ColSeleccionado = (int) (mouseX / tamanoCeldas);
        int RowSeleccionado = (int) (mouseY / tamanoCeldas);
        int dx = 0;
        int dy = 0;

        int jugadorX = juego.getPosicionJugador().getCol();
        int jugadorY = juego.getPosicionJugador().getRow();

        if (ColSeleccionado > jugadorX) {
            dx = 1;
        } else if (ColSeleccionado < jugadorX) {
            dx = -1;
        }
        if (RowSeleccionado > jugadorY) {
            dy = 1;
        } else if (RowSeleccionado < jugadorY) {
            dy = -1;
        }

        if (!(dx == 0 && dy == 0)) {
            juego.moverJugador(dx, dy);
        }

        robotApp.drawGrid(gc);
    }
}


