package text.processor.ui;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import text.processor.TextProcessor;
import text.processor.service.FileIO;
import text.processor.service.Regex;

public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    private final TextProcessor processor = new TextProcessor();
    private final FileIO fileIO = new FileIO();
    private final Regex regexService = new Regex();
    private final TextArea inputArea = new TextArea();
    private final TextArea resultArea = new TextArea();
    private final TextField regexField = new TextField();
    private final TextField replacementField = new TextField();
    private final VBox root = new VBox();
    private File currentFile;

    public MainController() {
        // Top panel
        Button loadButton = new Button("Load File");
        Button saveButton = new Button("Save File");
        Button matchButton = new Button("Find Matches");
        Button replaceButton = new Button("Replace");
        Button highlightButton = new Button("Highlight Matches");
        Button freqButton = new Button("Word Frequency");
        Button summaryButton = new Button("Summarize");

        HBox topPanel = new HBox(10, loadButton, saveButton, matchButton, replaceButton, highlightButton, freqButton, summaryButton);
        topPanel.setPadding(new Insets(10));

        // Input fields
        regexField.setPromptText("Enter regex");
        replacementField.setPromptText("Replacement text");
        HBox inputPanel = new HBox(10, regexField, replacementField);
        inputPanel.setPadding(new Insets(10));

        // Text areas
        inputArea.setPromptText("Input text here or load a file...");
        resultArea.setPromptText("Results...");

        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.getChildren().addAll(topPanel, inputPanel, new Label("Input:"), inputArea, new Label("Output:"), resultArea);

        // Button actions
        loadButton.setOnAction(e -> loadFile());
        saveButton.setOnAction(e -> saveFile());
        matchButton.setOnAction(e -> findMatches());
        replaceButton.setOnAction(e -> replaceText());
        highlightButton.setOnAction(e -> highlightMatches());
        freqButton.setOnAction(e -> showFrequency());
        summaryButton.setOnAction(e -> summarize());
    }

    public VBox getRoot() {
        return root;
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        currentFile = fileChooser.showOpenDialog(null);
        if (currentFile != null) {
            try {
                List<String> lines = fileIO.readFile(currentFile);
                inputArea.setText(String.join("\n", lines));
                logger.log(Level.INFO, "File loaded: {0}", currentFile.getAbsolutePath());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error loading file", ex);
                showError("Failed to read file: " + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try {
                List<String> lines = List.of(inputArea.getText().split("\n"));
                fileIO.writeFile(currentFile, lines);
                logger.log(Level.INFO, "File saved: {0}", currentFile.getAbsolutePath());
                showInfo("File saved successfully.");
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error saving file", ex);
                showError("Failed to write file: " + ex.getMessage());
            }
        } else {
            showError("No file loaded.");
        }
    }

    private void findMatches() {
        try {
            String input = inputArea.getText();
            String regex = regexField.getText();
            List<String> matches = regexService.findMatches(input, regex);
            resultArea.setText(String.join("\n", matches));
            logger.log(Level.INFO, "Regex match performed with pattern: {0}", regex);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error during regex match", e);
            showError("Error during matching: " + e.getMessage());
        }
    }

    private void replaceText() {
        try {
            String input = inputArea.getText();
            String regex = regexField.getText();
            String replacement = replacementField.getText();
            String replaced = regexService.replaceMatches(input, regex, replacement);
            resultArea.setText(replaced);
            logger.log(Level.INFO, "Text replaced using pattern: {0}", regex);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error during text replacement", e);
            showError("Error during replacement: " + e.getMessage());
        }
    }

    private void highlightMatches() {
        try {
            String input = inputArea.getText();
            String regex = regexField.getText();
            String highlighted = regexService.highlightMatches(input, regex);
            resultArea.setText(highlighted);
            logger.log(Level.INFO, "Highlight applied with regex: {0}", regex);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error during highlight", e);
            showError("Error during highlighting: " + e.getMessage());
        }
    }

    private void showFrequency() {
        try {
            String input = inputArea.getText();
            Map<String, Long> frequency = processor.wordFrequency(input);
            resultArea.setText(frequency.toString());
            logger.info("Word frequency analysis complete.");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error during frequency analysis", e);
            showError("Error during word frequency analysis: " + e.getMessage());
        }
    }

    private void summarize() {
        try {
            String input = inputArea.getText();
            Map<String, Integer> summary = processor.summarizeText(input);
            resultArea.setText(summary.toString());
            logger.info("Text summarization complete.");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error during summarization", e);
            showError("Error during summarization: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
}
