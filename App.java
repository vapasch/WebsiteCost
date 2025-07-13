import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    public static void main(String[] args) {
        launch(args); 
}
    
@Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("KostologhshScene.fxml"));
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); //css

        primaryStage.setTitle("Κοστολόγιο");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}