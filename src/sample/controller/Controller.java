package sample.controller;

import fr.opensagres.xdocreport.core.XDocReportException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import sample.Main;
import sample.model.DataContainer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private TextArea loggerTextArea;

    private File tableFile = new File("testTable.xlsx");
    private File templateFile = new File("wordWorker.docx");
    private DataContainer dataContainer = new DataContainer();

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
        loggerTextArea.appendText("->File choose: " + templateFile.getAbsolutePath() + "\n");
    }

    @FXML
    private void formDecision () throws IOException, XDocReportException {
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
        ExcelParser.parse(tableFile, dataContainer);
        WordWorker.parseWord(dataContainer, templateFile);
    }


    @FXML
    private void openTemplate() {
        Desktop desktop =  null;
        if (Desktop.isDesktopSupported()){
            desktop = Desktop.getDesktop();
        }

        try {
            desktop.open(new File(String.valueOf(templateFile)));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    private void initialize () {
        loggerTextArea.setEditable(false);
    }

    @FXML
    private void openTable(){
        Desktop desktop =  null;
        if (Desktop.isDesktopSupported()){
            desktop = Desktop.getDesktop();
        }

        try {
            desktop.open(new File(String.valueOf(tableFile)));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
