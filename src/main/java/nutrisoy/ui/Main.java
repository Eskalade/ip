package nutrisoy.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Parent root = loader.load();
        MainWindow mainWindow = loader.getController();
        mainWindow.setDuke(new Duke("./data/nutrisoy.txt"));

        stage.setTitle(Ui.BOT_NAME + " - NutriSoy");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/styles/main.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(440);
        stage.setMinHeight(480);
        stage.show();
    }
}
