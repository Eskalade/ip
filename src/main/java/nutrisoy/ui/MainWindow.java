package nutrisoy.ui;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import nutrisoy.Duke;

/**
 * Controls the main chat window and connects it to the NutriSoy backend.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;
    private Image userImage;
    private Image dukeImage;

    /**
     * Initializes the chat window after its FXML controls have been loaded.
     */
    @FXML
    public void initialize() {
        userImage = loadImage("/images/DaUser.png");
        dukeImage = loadImage("/images/DaDuke.png");
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the backend instance used to process user commands.
     *
     * @param duke application backend
     */
    public void setDuke(Duke duke) {
        this.duke = duke;
    }

    /**
     * Sends the entered command to the backend and adds both chat bubbles.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNutriSoyDialog(response, dukeImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }

    /**
     * Loads an image stored in the application's resources.
     *
     * @param resourcePath absolute classpath path to the image
     * @return loaded image
     */
    private Image loadImage(String resourcePath) {
        InputStream imageStream = MainWindow.class.getResourceAsStream(resourcePath);
        if (imageStream == null) {
            throw new IllegalStateException("Missing image resource: " + resourcePath);
        }
        return new Image(imageStream);
    }
}
