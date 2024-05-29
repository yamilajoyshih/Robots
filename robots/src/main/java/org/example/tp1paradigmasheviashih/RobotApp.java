package org.example.tp1paradigmasheviashih;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class RobotApp extends Application {

    private Game juego;
    @FXML
    private Button teleportRandomly;
    @FXML
    private ToggleButton teleportSafely;
    @FXML
    private Label labelTeleportSafely;
    @FXML
    private Button waitForRobots;
    @FXML
    private Label labelNivel;

    @FXML
    private Label usosDisponibles;

    static final int tamanoCeldas = 20;
    private int cantidadColumnas;
    private int cantidadFilas;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        menuIniciarJuego(stage);
        this.cantidadColumnas = juego.cantidadColumnas();
        this.cantidadFilas = juego.cantidadFilas();

        Parent p = loadFXML();

        Canvas canvas = (Canvas) p.lookup("#miCanva");
        canvas.setWidth(cantidadColumnas*tamanoCeldas);
        canvas.setHeight(cantidadFilas*tamanoCeldas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem ReglasDelJuego = new MenuItem("Reglas del Juego");
        MenuItem Controles = new MenuItem("Controles");
        ReglasDelJuego.setOnAction(event ->mostrarReglas());
        Controles.setOnAction(event-> mostrarControles());
        menu.getItems().add(ReglasDelJuego);
        menu.getItems().add(Controles);
        menuBar.getMenus().add(menu);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar,p);

        drawGrid(gc);

        TecladoEventHandler tecladoHandler = new TecladoEventHandler(this,this.juego, gc);
        MouseEventHandler mouseEventHandler = new MouseEventHandler(this, this.juego, gc);

        Scene scene = new Scene(vbox);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Robots");

        scene.setOnKeyPressed(tecladoHandler);
        canvas.setOnMouseClicked(mouseEventHandler);

        BotonesEventHandler botonHandler = new BotonesEventHandler(this,this.juego,gc);
        teleportRandomly.setOnAction(botonHandler.TeleportRandomly());
        teleportSafely.setOnAction(botonHandler.TeleportSafe(canvas, teleportSafely, mouseEventHandler));
        waitForRobots.setOnAction(botonHandler.WaitForRobots());

        stage.show();
    }

    private Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    private void menuIniciarJuego(Stage stage) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Configuración de la Grilla");
        dialog.setHeaderText("Por favor, ingresa la cantidad de filas y columnas:");

        TextField rowsField = new TextField();
        rowsField.setPromptText("Filas (mín: 10, máx: 35)");

        TextField colsField = new TextField();
        colsField.setPromptText("Columnas (mín: 10, máx: 70)");

        dialog.getDialogPane().setContent(new VBox(10, new Label("Filas:"), rowsField, new Label("Columnas:"), colsField));

        ButtonType acceptButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().setAll(acceptButton, cancelButton);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(acceptButton);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (validateInput(rowsField, 10, 35) || validateInput(colsField, 10, 75)) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, ingresa valores numéricos entre 10 y 35 para las filas y entre 10 y 75 para las columnas.");
                alert.showAndWait();
            }
        });

        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == acceptButton) {
                int cols = Integer.parseInt(colsField.getText());
                int rows = Integer.parseInt(rowsField.getText());
                juego = new Game(cols, rows);
            }
        });
    }

    private boolean validateInput(TextField textField, int min, int max) {
        String text = textField.getText();
        if (!text.matches("\\d+")) {
            return true;
        }
        int value = Integer.parseInt(text);
        return value < min || value > max;
    }

    private void mostrarReglas(){
        Alert texto = new Alert(Alert.AlertType.INFORMATION);
        texto.setTitle("Reglas del Juego");
        texto.setHeaderText("Reglas del juego:");
        String reglasTexto =
                "¡Tu misión es escapar de los robots que te persiguen! Pero ten cuidado, ¡hay dos tipos de robots! Algunos se mueven de a una celda a la vez, mientras que otros ¡avanzan de a dos celdas! ¡Cuidado con esos, son peligrosos!\n" +
                        "¿Cómo ganas?\n" +
                        "Para salir victorioso, debes utilizar tu ingenio para hacer que los robots colisionen entre sí o, aún mejor, ¡hacerlos caer en celdas incendiadas! ¡Mucho ojo y pensamiento estratégico!\n" +
                        "\n" +
                        "Habilidades:\n" +
                        "Como un experto en evasión, tienes a tu disposición habilidades increíbles:\n" +
                        "1. Teletransporte Random: ¡Puedes teletransportarte de manera aleatoria! Pero ten cuidado, ¡puedes tener la mala suerte de caer sobre un robot! \n" +
                        "2. Teletransporte Seguro: También puedes teletransportarte de manera segura. Sin embargo, ten en cuenta que tus usos son limitados. ¡Recibes uno adicional cada vez que superas un nivel! ¡Aprovecha tus movimientos con sabiduría!\n "+
                        "Suerte!";
        texto.setContentText(reglasTexto);
        texto.showAndWait();
    }

    private void mostrarControles(){
        Alert texto = new Alert(Alert.AlertType.INFORMATION);
        texto.setTitle("Controles del Juego");
        texto.setHeaderText("Controles:");
        String controles =
                "Puedes usar los siguientes controles:\n" +
                        "\n" +
                        "Mouse: Haz clic en la dirección en la que deseas moverte: arriba, abajo, izquierda, derecha o en diagonal.\n\n" +
                        "Teclado:\n" +
                        "W: Mueve hacia arriba.\n" +
                        "S: Mueve hacia abajo.\n" +
                        "A: Mueve hacia la izquierda.\n" +
                        "D: Mueve hacia la derecha.\n" +
                        "Q: Mueve Diagonal arriba-izquierda.\n" +
                        "E: Mueve Diagonal arriba-derecha.\n" +
                        "Z: Mueve Diagonal abajo-izquierda.\n" +
                        "C: Mueve Diagonal abajo-derecha.\n" +
                        "¡Domina estos movimientos para esquivar hábilmente a los robots!\n\n" +
                        "Si perdes el juego, presiona R para reiniciarlo e intentarlo otra vez!\n\n";
        texto.setContentText(controles);
        texto.showAndWait();
    }

    private void drawImage(GraphicsContext gc, String imageName, double x, double y) {
        Image image = new Image(imageName);
        gc.drawImage(image, x, y);
    }

    public void drawGrid(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        double gridWidth = cantidadColumnas * tamanoCeldas;
        double gridHeight = cantidadFilas * tamanoCeldas;

        double startX = (canvasWidth - gridWidth) / 2;
        double startY = (canvasHeight - gridHeight) / 2;

        labelNivel.setText("Nivel "+ juego.getNivel());
        labelTeleportSafely.setText("Teleport Safely \n  Restantes: "+juego.CantidadDeTelepSeguras());

        boolean isBlue = true;

        for (int y = 0; y < cantidadFilas; y++) {
            if (cantidadColumnas % 2 == 0) {
                isBlue = !isBlue;
            }

            for (int x = 0; x < cantidadColumnas; x++) {
                if (isBlue) {
                    gc.setFill(Color.LIGHTBLUE);
                } else {
                    gc.setFill(Color.ALICEBLUE);
                }

                gc.fillRect(startX + x * tamanoCeldas, startY + y * tamanoCeldas, tamanoCeldas, tamanoCeldas);

                isBlue = !isBlue;
            }
        }

        drawImage(gc, "file:src/main/resources/img/personaje.png", startX + juego.getPosicionJugador().getCol() * tamanoCeldas, startY + juego.getPosicionJugador().getRow() * tamanoCeldas);

        List<Robot> robots = juego.getRobots();
        for (Robot robot : robots) {
            if (robot instanceof RobotSimple) {
                drawImage(gc, "file:src/main./resources/img/robotSimple.png", startX + robot.getPosicion().getCol() * tamanoCeldas, startY + robot.getPosicion().getRow() * tamanoCeldas);
            } else if (robot instanceof RobotDoble) {
                drawImage(gc, "file:src/main./resources/img/robotDoble.png", startX + robot.getPosicion().getCol() * tamanoCeldas, startY + robot.getPosicion().getRow() * tamanoCeldas);
            }
        }
        for (Posicion celdaIncendiada : juego.getCeldasIncendiadas()){ // ver tema de celdas en Game y Grid
            drawImage(gc, "file:src/main./resources/img/explosion.png", startX + celdaIncendiada.getCol() * tamanoCeldas, startY + celdaIncendiada.getRow() * tamanoCeldas);
        }

        if(!juego.isEstaEnJuego()){
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            gc.setFill(Color.RED);

            String mensaje = "¡Juego perdido!\nPresione R para Reiniciar";
            Text text = new Text(mensaje);
            text.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            double mensajeWidth = text.getLayoutBounds().getWidth();
            double mensajeHeight = text.getLayoutBounds().getHeight();
            double mensajeX = (canvasWidth - mensajeWidth) / 2;
            double mensajeY = (canvasHeight - mensajeHeight) / 2;

            gc.fillText(mensaje, mensajeX, mensajeY);
        }
    }
}
