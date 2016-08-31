package jo.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author JoostMeulenkamp
 */
public class FX {

    public static void displayMessageDialog(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    static boolean confirmed;

    public static boolean displayConfirmBox(String title, String message) {

        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            confirmed = true;
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            confirmed = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, cancelButton, confirmButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return confirmed;
    }
}
