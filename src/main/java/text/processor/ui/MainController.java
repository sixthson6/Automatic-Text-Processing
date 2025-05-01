package text.processor.ui;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import text.processor.DataManager;
import text.processor.TextProcessor;
import text.processor.service.FileIO;
import text.processor.service.Regex;
import text.processor.service.TextEntry;

public class MainController {

    private final TextProcessor processor = new TextProcessor();
    private final FileIO fileIO = new FileIO();
    private final Regex regexService = new Regex();
    private final DataManager dataManager = new DataManager();
    private final Logger logger = Logger.getLogger(MainController.class.getName());

    private final TextArea inputArea = new TextArea();
    private final TextArea resultArea = new TextArea();
    private final TextField regexField = new TextField();
    private final TextField replacementField = new TextField();
    private final ListView<TextEntry> entryList = new ListView<>();
    private final TableView<String> matchTable = new TableView<>();
    private File currentFile;

    public VBox getRoot() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Button loadButton = new Button("Load File");
        Button saveButton = new Button("Save File");
        Button matchButton = new Button("Find Matches");
        Button replaceButton = new Button("Replace");
        Button freqButton = new Button("Word Frequency");
        Button summaryButton = new Button("Summarize");
        Button addEntryButton = new Button("Add Entry");
        Button deleteEntryButton = new Button("Delete Entry");

        HBox topPanel = new HBox(10, loadButton, saveButton, matchButton, replaceButton, freqButton, summaryButton, addEntryButton, deleteEntryButton);
        HBox inputPanel = new HBox(10, regexField, replacementField);

        regexField.setPromptText("Enter regex");
        replacementField.setPromptText("Replacement text");

        inputArea.setPromptText("Input text here or load a file...");
        resultArea.setPromptText("Results...");

        setupMatchTable();

        VBox entryPanel = new VBox(new Label("Text Entries:"), entryList);
        entryPanel.setSpacing(5);
        entryPanel.setPrefWidth(300);
        entryPanel.setPrefHeight(150);

        root.getChildren().addAll(topPanel, inputPanel, entryPanel,
                new Label("Input:"), inputArea,
                new Label("Matches:"), matchTable,
                new Label("Output:"), resultArea);

        loadButton.setOnAction(e -> loadFile());
        saveButton.setOnAction(e -> saveFile());
        matchButton.setOnAction(e -> findMatches());
        replaceButton.setOnAction(e -> replaceText());
        freqButton.setOnAction(e -> showFrequency());
        summaryButton.setOnAction(e -> summarize());
        addEntryButton.setOnAction(e -> addEntry());
        deleteEntryButton.setOnAction(e -> deleteEntry());

        return root;
    }

    private void setupMatchTable() {
        TableColumn<String, String> matchColumn = new TableColumn<>("Match");
        matchColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        matchColumn.setMinWidth(500);
        matchTable.getColumns().add(matchColumn);
        matchTable.setPlaceholder(new Label("No matches found"));
        matchTable.setPrefHeight(200);
    }

    private void loadFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            currentFile = fileChooser.showOpenDialog(null);
            if (currentFile != null) {
                List<String> lines = fileIO.readFile(currentFile);
                inputArea.setText(String.join("\n", lines));
                logger.log(Level.INFO, "File loaded: {0}", currentFile.getName());
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error reading file", ex);
            showError("Failed to read file: " + ex.getMessage());
        }
    }

    private void saveFile() {
        try {
            if (currentFile != null) {
                List<String> lines = List.of(inputArea.getText().split("\n"));
                fileIO.writeFile(currentFile, lines);
                logger.info("File saved successfully");
                showInfo("File saved successfully.");
            } else {
                showError("No file loaded.");
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error writing file", ex);
            showError("Failed to write file: " + ex.getMessage());
        }
    }

    private void findMatches() {
        try {
            String input = inputArea.getText();
            String regex = regexField.getText();
            List<String> matches = regexService.findMatches(input, regex);
            matchTable.setItems(FXCollections.observableArrayList(matches));
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
            logger.info("Text replaced successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Replacement failed", e);
            showError("Error during replace: " + e.getMessage());
        }
    }

    private void showFrequency() {
        try {
            String input = inputArea.getText();
            Map<String, Long> frequency = processor.wordFrequency(input);
            resultArea.setText(frequency.toString());
            logger.info("Word frequency calculated");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Frequency error", e);
            showError("Error during frequency analysis: " + e.getMessage());
        }
    }

    private void summarize() {
        try {
            String input = inputArea.getText();
            Map<String, Integer> summary = processor.summarizeText(input);
            resultArea.setText(summary.toString());
            logger.info("Text summarized");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Summarize error", e);
            showError("Error during summarization: " + e.getMessage());
        }
    }

    private void addEntry() {
        String content = inputArea.getText();
        if (!content.isBlank()) {
            TextEntry newEntry = new TextEntry(content);
            dataManager.addEntry(newEntry);
            entryList.setItems(FXCollections.observableArrayList(dataManager.getAllEntries()));
            logger.info("New entry added.");
        } else {
            showError("Input area is empty. Cannot add entry.");
        }
    }

    private void deleteEntry() {
        TextEntry selected = entryList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dataManager.deleteEntry(selected.getId());
            entryList.setItems(FXCollections.observableArrayList(dataManager.getAllEntries()));
            logger.info("Entry removed.");
        } else {
            showError("No entry selected for deletion.");
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