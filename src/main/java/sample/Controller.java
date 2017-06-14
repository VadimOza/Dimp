package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class Controller {

    @FXML
    private Button train;
    @FXML
    private Button loadData;
    @FXML
    private Button loadTest;
    @FXML
    private Button verify;

    @FXML
    private TextArea log;
    private WekaService wekaService;

    //    private List<String> toTrain = new LinkedList<>();
    private File fileTrain;
    private File fileTest;

    public Controller() {
        this.wekaService = new WekaService();
    }

    @FXML
    private void initialize() {
        loadData.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(loadData.getScene().getWindow()));
            file.ifPresent(file1 -> this.fileTrain = file1);
        });
        loadTest.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(loadTest.getScene().getWindow()));
            file.ifPresent(file1 -> this.fileTest = file1);
        });

        train.setOnAction(event -> {
            try {
                Optional<String> res = wekaService.train(this.fileTrain, this.fileTest);
                res.ifPresent(s -> {
                    log.setText(log.getText() + "Sound classify as " + s + "\n");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
