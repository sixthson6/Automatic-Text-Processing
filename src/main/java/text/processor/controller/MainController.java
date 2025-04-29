package text.processor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {
    @FXML private TextArea inputArea;
    @FXML private TextField regexField;
    @FXML private TextArea outputArea;

    public void onMatchRegex() {
        // Will be wired later to call TextProcessingService
    }
}

