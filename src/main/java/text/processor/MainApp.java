// package text.processor;

// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

// public class MainApp extends Application {
//     @Override
//     public void start(Stage primaryStage) throws Exception {
//         Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml")));
//         primaryStage.setTitle("Text Processing System");
//         primaryStage.setScene(scene);
//         primaryStage.show();
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }

package text.processor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final TextProcessor processor = new TextProcessor();
    private final TextArea inputArea = new TextArea();
    private final TextArea resultArea = new TextArea();
    private final TextField regexField = new TextField();
    private final TextField replacementField = new TextField();
    private File currentFile;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Processor");

        // Top panel
        Button loadButton = new Button("Load File");
        Button saveButton = new Button("Save File");
        Button matchButton = new Button("Find Matches");
        Button replaceButton = new Button("Replace");
        Button freqButton = new Button("Word Frequency");
        Button summaryButton = new Button("Summarize");

        HBox topPanel = new HBox(10, loadButton, saveButton, matchButton, replaceButton, freqButton, summaryButton);
        topPanel.setPadding(new Insets(10));

        // Input fields
        regexField.setPromptText("Enter regex");
        replacementField.setPromptText("Replacement text");
        HBox inputPanel = new HBox(10, regexField, replacementField);
        inputPanel.setPadding(new Insets(10));

        // Layout
        inputArea.setPromptText("Input text here or load a file...");
        resultArea.setPromptText("Results...");

        VBox root = new VBox(10, topPanel, inputPanel, new Label("Input:"), inputArea, new Label("Output:"), resultArea);
        root.setPadding(new Insets(10));

        // Button actions
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            currentFile = fileChooser.showOpenDialog(primaryStage);
            if (currentFile != null) {
                try {
                    List<String> lines = processor.readFile(currentFile);
                    inputArea.setText(String.join("\n", lines));
                } catch (IOException ex) {
                    showError("Failed to read file: " + ex.getMessage());
                }
            }
        });

        saveButton.setOnAction(e -> {
            if (currentFile != null) {
                try {
                    List<String> lines = List.of(inputArea.getText().split("\n"));
                    processor.writeFile(currentFile, lines);
                    showInfo("File saved successfully.");
                } catch (IOException ex) {
                    showError("Failed to write file: " + ex.getMessage());
                }
            } else {
                showError("No file loaded.");
            }
        });

        matchButton.setOnAction(e -> {
            String input = inputArea.getText();
            String regex = regexField.getText();
            List<String> matches = processor.findMatches(input, regex);
            resultArea.setText(String.join("\n", matches));
        });

        replaceButton.setOnAction(e -> {
            String input = inputArea.getText();
            String regex = regexField.getText();
            String replacement = replacementField.getText();
            String replaced = processor.replaceMatches(input, regex, replacement);
            resultArea.setText(replaced);
        });

        freqButton.setOnAction(e -> {
            String input = inputArea.getText();
            Map<String, Long> frequency = processor.wordFrequency(input);
            resultArea.setText(frequency.toString());
        });

        summaryButton.setOnAction(e -> {
            String input = inputArea.getText();
            Map<String, Integer> summary = processor.summarizeText(input);
            resultArea.setText(summary.toString());
        });

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
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

