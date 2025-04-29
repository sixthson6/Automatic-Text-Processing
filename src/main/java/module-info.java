module text.processor {
    requires javafx.controls;
    requires javafx.fxml;
    // requires javafx.controls;
    requires javafx.graphics;

    opens text.processor to javafx.fxml;
    exports text.processor;
}
