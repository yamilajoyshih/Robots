package org.example.tp1paradigmasheviashih;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EventListener;

public class TecladoEventHandler implements EventHandler<KeyEvent> {

    private final RobotApp robotApp;
    private final Game juego;
    private final GraphicsContext gc;

    public TecladoEventHandler(RobotApp robotApp, Game juego, GraphicsContext gc) {
        this.robotApp = robotApp;
        this.juego = juego;
        this.gc = gc;
    }


    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        int dx = 0;
        int dy = 0;
        boolean jugadorSeMovio = true;
        switch (code) {
            case W:
                dy = -1; // esto porque el eje y crece hacia abajo
                break;
            case S:
                dy = 1;
                break;
            case A:
                dx = -1;
                break;
            case D:
                dx = 1;
                break;
            case Q: // Movimiento diagonal arriba-izquierda
                dx = -1;
                dy = -1;
                break;
            case E: // Movimiento diagonal arriba-derecha
                dx = 1;
                dy = -1;
                break;
            case Z: // Movimiento diagonal abajo-izquierda
                dx = -1;
                dy = 1;
                break;
            case C: // Movimiento diagonal abajo-derecha
                dx = 1;
                dy = 1;
                break;
            case R:
                if(!juego.isEstaEnJuego()){
                    juego.reiniciarJuego();
                }
                jugadorSeMovio = false;
                break;
            default:
                jugadorSeMovio = false;
                break;
        }
        if (jugadorSeMovio) {
            juego.moverJugador(dx, dy);
        }

        robotApp.drawGrid(gc);
    }

    public RobotApp getRobotApp() {
        return robotApp;
    }
}
