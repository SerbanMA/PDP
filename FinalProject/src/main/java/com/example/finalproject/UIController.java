package com.example.finalproject;

import com.example.finalproject.domain.MyImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;

public class UIController {
    @FXML
    private ImageView initialImage;
    @FXML
    private ImageView resultImage;
    @FXML
    private Slider slider;
    @FXML
    private Label sliderValue;
    @FXML
    private RadioButton blur;
    @FXML
    private RadioButton gray;
    @FXML
    private Label message;
    @FXML
    protected void initialize() throws FileNotFoundException {
        ToggleGroup group = new ToggleGroup();
        blur.setToggleGroup(group);
        blur.setSelected(true);
        gray.setToggleGroup(group);
        slider.setBlockIncrement(1.00);
        slider.setMax(100);
        slider.setMin(0);
        slider.setValue(10);
        sliderValue.setText("Blur size value: 10");
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                slider.setValue(Math.round(new_val.doubleValue()));
                sliderValue.setText("Blur size value: " + String.valueOf(Math.round(new_val.doubleValue())));
            }
        });
    }

    public void setInitialImage(Image initialImage) {
        this.initialImage.setImage(initialImage);
        resultImage.setImage(null);
    }

    public void setResultImage(String imagePath) {
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());

        resultImage.setImage(image);
    }

    @FXML
    protected void onApplyChangesClick() {
        if (initialImage.getImage() != null) {
            if (blur.isSelected()) {

                int blurSize = (int) slider.getValue();

                String imageURL = initialImage.getImage().getUrl().split("file:/")[1];
                String[] folders = initialImage.getImage().getUrl().split("/");
                String imageFileName = folders[folders.length - 1].replace(".jpg", "")
                        .replace(".png", "")
                        .replace(".jpeg", "");
                String outputURL = "_data/output/" + imageFileName + "-blur-" + blurSize + ".jpg";

                MyImage image = new MyImage(imageURL, blurSize);
                image.exportBlurImageToFile(outputURL);

                setResultImage(outputURL);
                message.setText("");
            } else if (gray.isSelected()) {
                String imageURL = initialImage.getImage().getUrl().split("file:/")[1];
                String[] folders = initialImage.getImage().getUrl().split("/");
                String imageFileName = folders[folders.length - 1].replace(".jpg", "")
                        .replace(".png", "")
                        .replace(".jpeg", "");
                String outputURL = "_data/output/" + imageFileName + "-gray.jpg";

                MyImage image = new MyImage(imageURL);
                image.exportGrayImageToFile(outputURL);

                setResultImage(outputURL);
                message.setText("");
            }
        }
    }

    @FXML
    protected void onApplyChangesPress() {
        if (initialImage.getImage() != null) {
            message.setText("Image is processing!");
        }
        else {
            message.setText("You need to upload an image!");
        }
    }

    @FXML
    protected void onUploadClick(Event event) {
        try {
            FileChooser fileChooser = new FileChooser();
            Node node = (Node) event.getSource();

            fileChooser.setInitialDirectory(new File("_data/input"));
            File selectedFile = fileChooser.showOpenDialog(node.getScene().getWindow());

            Image im1 = new Image(selectedFile.toURI().toString());
            setInitialImage(im1);
            message.setText("");
        } catch (Exception e) {
            System.out.println("The window was closed before choosing a photo");
        }
    }
}