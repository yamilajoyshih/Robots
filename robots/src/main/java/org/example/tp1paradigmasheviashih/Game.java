package org.example.tp1paradigmasheviashih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private int nivelActual;
    private final Grid grid;
    private Jugador jugador;
    private final List<Robot> robots;
    boolean estaEnJuego;

    public Game(int gridCols, int gridRows){
        this.grid= new Grid(gridCols, gridRows);
        this.jugador = new Jugador(gridCols/2, gridRows/2);
        this.nivelActual = 0;
        this.robots = new ArrayList<>();
        this.estaEnJuego = true;
        iniciarNivel();
    }

    public void reiniciarJuego(){
        this.estaEnJuego = true;
        this.grid.apagarCeldas();
        this.jugador = new Jugador(grid.getCols()/2, grid.getRows()/2);
        this.nivelActual = 0;
        iniciarNivel();
    }

    public void actualizarEstadoJuego(){
        controlarColisionesRobots();
        isGameOver();
        if(this.estaEnJuego && this.robots.isEmpty()){
            iniciarNivel();
        }
    }

    public void iniciarNivel(/*int numRobots*/){
        this.nivelActual++;
        if(nivelActual > 1){
            this.jugador.aumentarTeleportSafely();
        }
        this.robots.clear();
        this.grid.apagarCeldas();
        Posicion posicionJugador = jugador.getPosition();
        int numRobots = 4 + nivelActual;
        for (int i= 0; i< numRobots;i++){
            Posicion posicionRandom = grid.getRandomPosition();
            while (posicionRandom.equals(posicionJugador)){
                posicionRandom = grid.getRandomPosition();
            }
            Robot robot;
            if((i+1)%5 == 0){
                robot = new RobotDoble(posicionRandom.getCol(), posicionRandom.getRow());
            } else {
                robot = new RobotSimple(posicionRandom.getCol(), posicionRandom.getRow());
            }
            robots.add(robot);
        }
    }

    public void isGameOver() {
        Posicion posicionJugador = this.jugador.getPosition();
        for (Robot robot : this.robots) {
            if (robot.getPosicion().getCol() == posicionJugador.getCol() && robot.getPosicion().getRow() == posicionJugador.getRow()) {
                this.estaEnJuego = false;
                return;
            }
        }
        if(grid.estaIncendiada(posicionJugador)){
            this.estaEnJuego = false;
        }
    }


    public void moverJugador(int dx, int dy) {
        if (this.estaEnJuego) {
            jugador.moverJugador(dx, dy, grid);
            moveRobots();
            actualizarEstadoJuego();
        }
    }


    // ROBOTS

    public void moveRobots() {
        if (this.estaEnJuego) {
            if (!robots.isEmpty()) {
                List<Robot> robotsAEliminar = new ArrayList<>();

                for (Robot robot : robots) {
                    robot.moveTowardsPlayer(jugador, grid);
                    if (grid.estaIncendiada(robot.getPosicion())) {
                        robotsAEliminar.add(robot);
                    }
                }
                robots.removeAll(robotsAEliminar);
            }
            actualizarEstadoJuego();
        }


    }

    public void controlarColisionesRobots() {
        Map<Posicion, List<Robot>> robotPosicionDicc = new HashMap<>();

        for (Robot robot: robots){
            Posicion posicionRobot = robot.getPosicion();
            if (!robotPosicionDicc.containsKey(posicionRobot)){
                robotPosicionDicc.put(posicionRobot, new ArrayList<>());
            }
            robotPosicionDicc.get(posicionRobot).add(robot);
        }

        for (List<Robot> robotEnCelda : robotPosicionDicc.values()){
            if (robotEnCelda.size() > 1){
                for (Robot robot : robotEnCelda){
                    robots.remove(robot);
                }
                Posicion posicionColision = robotEnCelda.getFirst().getPosicion();
                grid.agregarCeldaIncendiada(posicionColision);
            }
        }
    }

    public void teleportRandomly(){
        if(this.estaEnJuego){
            jugador.teleportRandom(grid);
            actualizarEstadoJuego();
         }
    }

    public void teleportSafe(int row, int col){
        if(this.estaEnJuego) {
            if(jugador.cantidadTeleportSafely() > 0){
                jugador.teleportSafely(row, col, grid);
            }
            actualizarEstadoJuego();
        }
    }

    public int CantidadDeTelepSeguras(){
        return jugador.cantidadTeleportSafely();
    }

    public int getNivel() {
        return this.nivelActual;
    }

    public List<Robot> getRobots(){
        return this.robots;
    }

    public Posicion getPosicionJugador(){
        return this.jugador.getPosition();
    }

    public ArrayList<Posicion> getCeldasIncendiadas(){
        return grid.getCeldasIncendiadas();
    }

    public boolean isEstaEnJuego(){
        return this.estaEnJuego;
    }

    public int cantidadColumnas() {
        return this.grid.getCols();
    }

    public int cantidadFilas() {
        return this.grid.getRows();
    }

}

