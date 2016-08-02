package csb.test;

import static csb.CSB_StartupConstants.CLOSE_BUTTON_LABEL;
import csb.gui.MessageDialog;
import csb.gui.ProgressDialog;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class is used to test our MessageDialog class. Using a separate class
 * like this lets us setup simple tests without all the other stuff in the way.
 *
 * @author Richard McKenna
 */
public class CSB_Test_ProgressDialog extends Application {

    /**
     * This method performs our simple test, where we simply open our message
     * dialog and the close it.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        // INIT THE UI FOR THIS TEST PROGRAM
        Button startTestButton = new Button("Start Progress Test");
        Pane pane = new Pane();
        pane.getChildren().add(startTestButton);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);

        // AND SETUP THE EVENT HANDLER TO TRIGGER THE TEST
        startTestButton.setOnAction(e -> {
            ProgressDialog pd = new ProgressDialog(primaryStage, "Preparing");
            pd.show();
            Task<Void> task = new Task<Void>() {
                double perc = 0;
                double max = 1000;
                double counter = 0;
                String[] messages = {"Stage 1", "Stage 2", "Stage 3", "Stage 4", "Stage 5"};

                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 200; j++) {
                            perc = counter / max;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    int messageIndex = (int) (messages.length * perc);
                                    System.out.println("Perc: " + perc
                                            + "\tmessageIndex: " + messageIndex);
                                    pd.setProgress(perc, messages[messageIndex]);
                                }
                            });
                            Thread.sleep(10);
                            counter++;
                        }
                    }
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        });

        // AND NOW START THIS TEST APP
        primaryStage.show();
    }

    /**
     * This test application starts here, and simply begins the initialization
     * of the JavaFX application.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
