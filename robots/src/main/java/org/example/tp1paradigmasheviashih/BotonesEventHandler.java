package org.example.tp1paradigmasheviashih;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;


public class BotonesEventHandler {
    private final Game juego;
    private final RobotApp robotApp;
    private final GraphicsContext gc;
    static final int tamanoCeldas = 20;



    public BotonesEventHandler(RobotApp robotApp, Game juego, GraphicsContext gc){
        this.robotApp = robotApp;
        this.juego = juego;
        this.gc = gc;
    }

    public EventHandler<ActionEvent> TeleportRandomly(){
        return event -> {
            juego.teleportRandomly();
            robotApp.drawGrid(gc);
        };
    }

    public EventHandler<ActionEvent> TeleportSafe(Canvas canvas, ToggleButton teleportSafely, MouseEventHandler mouseEventHandler){
        return event -> {
            if (teleportSafely.isSelected() && juego.CantidadDeTelepSeguras() > 0) {
                canvas.setOnMouseClicked(mouseEvent -> {
                    double startX = (gc.getCanvas().getWidth() - (juego.cantidadColumnas() * tamanoCeldas)) / 2;
                    double startY = (gc.getCanvas().getHeight() - (juego.cantidadFilas() * tamanoCeldas)) / 2;

                    double mouseX = mouseEvent.getX() - startX;
                    double mouseY = mouseEvent.getY() - startY;
                    int rowSeleccionado = (int) (mouseY / tamanoCeldas);
                    int colSeleccionado = (int) (mouseX / tamanoCeldas);

                    juego.teleportSafe(rowSeleccionado, colSeleccionado);
                    robotApp.drawGrid(gc);
                });
            } else {
                canvas.setOnMouseClicked(mouseEventHandler);
            }
        };
    }

    public EventHandler<ActionEvent> WaitForRobots(){
        return event->{
            juego.moveRobots() ;
            robotApp.drawGrid(gc);
        };
    }
}
