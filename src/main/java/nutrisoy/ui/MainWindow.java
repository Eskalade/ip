package nutrisoy.ui;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    @FXML
    private Text statusText;

    private Duke duke;
    private Image userImage;
    private Image soyaImage;

    /**
     * Initializes the chat window after its FXML controls have been loaded.
     */
    @FXML
    public void initialize() {
        userImage = loadImage("/images/DaUser.png");
        soyaImage = loadImage("/images/DaDuke.png");
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the backend instance used to process user commands.
     *
     * @param duke application backend
     */
    public void setDuke(Duke duke) {
        assert duke != null : "Application backend must not be null";
        this.duke = duke;
        dialogContainer.getChildren().add(DialogBox.getNutriSoyDialog(
                "Hey, I'm " + Ui.BOT_NAME + ". Drop me a task and I'll keep your chaos organised.", soyaImage));
        userInput.requestFocus();
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
        GuiResponse response = duke.getGuiResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                response.isError()
                        ? DialogBox.getErrorDialog(response.getMessage(), soyaImage)
                        : DialogBox.getNutriSoyDialog(response.getMessage(), soyaImage)
        );
        userInput.clear();
        statusText.setText(response.isError() ? "That command needs a makeover" : "Ready to serve");
        statusText.getStyleClass().setAll("status-text", response.isError() ? "error-status" : "ready-status");

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
        assert resourcePath != null : "Image resource path must not be null";
        InputStream imageStream = MainWindow.class.getResourceAsStream(resourcePath);
        if (imageStream == null) {
            throw new IllegalStateException("Missing image resource: " + resourcePath);
        }
        return new Image(imageStream);
    }
}
