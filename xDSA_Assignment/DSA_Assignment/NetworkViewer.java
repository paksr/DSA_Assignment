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
        Platform.setImplicitExit(false);
        if (graph != null) {
            openWindow(graph);
        }
    }

    public static void show(UnweightedGraph network) {
        if (!fxStarted) {
            // Initialize JavaFX platform if not already started
            new Thread(() -> Application.launch(NetworkViewer.class)).start();
            
            // Wait for JavaFX to initialize
            while (!fxStarted) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Set the graph and open window
        graph = network;
        Platform.runLater(() -> openWindow(network));
    }

    private static void openWindow(UnweightedGraph network) {
        if (network == null) {
            System.out.println("Error: No graph provided");
            return;
        }
        
        if (primaryStage == null) {
            primaryStage = new Stage();
        }
        
        GraphView view = new GraphView(network);
        Scene scene = new Scene(view, 800, 600);  // Increased size for better visibility
        primaryStage.setTitle("Disease Network Visualization");
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