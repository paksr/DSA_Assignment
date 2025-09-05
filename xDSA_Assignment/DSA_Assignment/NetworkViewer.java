import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NetworkViewer extends Application {
    private static UnweightedGraph graph;
    private static Stage primaryStage;
    private static volatile boolean fxStarted = false;

    public static void setGraph(UnweightedGraph g) {
        graph = g;
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        fxStarted = true;
        Platform.setImplicitExit(false); // Prevent JavaFX from exiting when window closes
        if (graph == null) {
            System.out.println("Error: No graph provided in start");
            return;
        }
        openWindow(graph);
    }

    public static void show(UnweightedGraph network) {
        if (!fxStarted) {
            fxStarted = true;
            Platform.startup(() -> {
                Platform.setImplicitExit(false);
                openWindow(network);
            });
        } else {
            Platform.runLater(() -> openWindow(network));
        }
    }

    private static void openWindow(UnweightedGraph network) {
        if (network == null) {
            System.out.println("Error: No graph provided");
            return;
        }
        graph = network;
        if (primaryStage == null) {
            primaryStage = new Stage();
        }
        GraphView view = new GraphView(graph);
        Scene scene = new Scene(view, 600, 400);
        primaryStage.setTitle("Disease Network");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            primaryStage.hide();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}