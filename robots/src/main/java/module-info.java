module org.example.tp1paradigmasheviashih {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tp1paradigmasheviashih to javafx.fxml;
    exports org.example.tp1paradigmasheviashih;
}