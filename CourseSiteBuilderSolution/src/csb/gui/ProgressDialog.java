package csb.gui;

import static csb.gui.CSB_GUI.PRIMARY_STYLE_SHEET;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author McKillaGorilla
 */
public class ProgressDialog extends Stage {
    VBox vBox;
    Label progressLabel;
    HBox progressToolbar;
    ProgressBar bar;
    ProgressIndicator pie;
    Font progressFont = Font.font("SanSerif", FontWeight.NORMAL, 24);
    
    public ProgressDialog(Stage owner, String initLabelText) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        // INIT THE CONTROLS, FIRST THE STAGE'S PARENT CONTAINER
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        
        // THEN THE LABEL
        progressLabel = new Label(initLabelText);
        progressLabel.setFont(progressFont);
        
        // AND THEN THE PROGRESS TOOLBAR AND ITS COMPONENTS
        progressToolbar = new HBox();
        progressToolbar.setAlignment(Pos.CENTER);
        bar = new ProgressBar();
        bar.getStyleClass().add("progress_bar");
        progressToolbar.getChildren().add(bar);
        pie = new ProgressIndicator();
        pie.getStyleClass().add("progress_pie");
        progressToolbar.getChildren().add(pie);
        
        // NOW ARRANGE EVERYTHING IN THE VBOX
        vBox.getChildren().add(progressLabel);
        vBox.getChildren().add(progressToolbar);
        
        // AND PUT ALL THE CONTROLS IN THIS STAGE'S SCENE
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        setScene(scene);
        
        // SIZE THE WINDOW
        setWidth(400);
        setHeight(200);
    }
    
    public void setProgress(double percentage, String labelMessage) {
        bar.setProgress(percentage);
        pie.setProgress(percentage);
        progressLabel.setText(labelMessage);
    }
}
