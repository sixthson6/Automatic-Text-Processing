module text.processor {
    requires javafx.controls;
    requires javafx.fxml;
    // requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;
 
    opens text.processor to javafx.fxml;
    exports text.processor;
    exports text.processor.ui;
    exports text.processor.service;
}
