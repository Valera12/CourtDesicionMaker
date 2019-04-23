package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import sample.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private Button fileButton;

    @FXML
    private Button templateButton;

    @FXML
    private Button formButton;

    @FXML
    private TextFlow loggerTextFlow;

    @FXML
    private TextArea loggerTextArea;

    private File tableFile;
    private File templateFile;

    @FXML
    private void chooseTableFile () {
        tableFile = new FileChooser().showOpenDialog(Main.mainWindow);
        if (tableFile == null) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("CHOOSE FILE PLEASE!");
          alert.setTitle("NOT FILE");
          alert.show();
          loggerTextArea.appendText("file error\n");
          return;
        }
        loggerTextArea.appendText("->File choose: " + tableFile.getAbsolutePath() + "\n");
    }

    @FXML
    private void chooseTemplateFile () {
        templateFile = new FileChooser().showOpenDialog(Main.mainWindow);
        if(templateFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("CHOOSE FILE PLEASE!");
            alert.setTitle("NOT FILE");
            alert.show();
            loggerTextArea.appendText("file error\n");
            return;
        }
        loggerTextArea.appendText("->File choose: " + tableFile.getAbsolutePath() + "\n");
    }

    @FXML
    private void formDecision () {
        if (tableFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("CHOOSE TABLE FILE PLEASE!");
            alert.setTitle("NOT FILE");
            alert.show();
            loggerTextArea.appendText("->table file error\n");
            return;
        } else if (templateFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("CHOOSE TEMPLATE FILE PLEASE!");
            alert.setTitle("NOT FILE");
            alert.show();
            loggerTextArea.appendText("->template file error\n");
            return;
        }
        List<String> choices = new ArrayList<>();
        final String ALL_IN_ONE_FILE = "all in one file";
        final String ALL_IN_DIFFERENT_FILES = "all in different files";
        choices.add(ALL_IN_ONE_FILE);
        choices.add(ALL_IN_DIFFERENT_FILES);
        Dialog dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.showAndWait();
        switch (dialog.getResult().toString()) {
            case ALL_IN_ONE_FILE:
                loggerTextArea.appendText("->Choose mode : all in one file\n");
                break;
            case ALL_IN_DIFFERENT_FILES:
                loggerTextArea.appendText("->Choose mode : all in different files\n");
                break;
        }
    }

    @FXML
    private void initialize () {
        loggerTextArea.setEditable(false);
    }
}
