package nutrisoy.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nutrisoy.Duke;

/**
 * JavaFX application that displays the NutriSoy chat interface.
 */
public class Main extends Application {
    /**
     * Loads and displays the main application window.
     *
     * @param stage primary JavaFX stage
     * @throws IOException if the main window layout cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane root = loader.load();
        MainWindow mainWindow = loader.getController();
        mainWindow.setDuke(new Duke("./data/nutrisoy.txt"));

        stage.setTitle("NutriSoy");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
