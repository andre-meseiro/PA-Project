import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.pa.view.mainmenu.DatasetLoaderPanel;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new DatasetLoaderPanel(), 700, 350);
        primaryStage.setTitle("Projeto PA 2223 - Bus Network");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}