module text.processor {
    requires javafx.controls;
    requires javafx.fxml;

    opens text.processor to javafx.fxml;
    exports text.processor;
}
