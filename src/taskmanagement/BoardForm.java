/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author alex
 */
public class BoardForm {
    
    public static Text display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Text sceneTitle = new Text("Create new board");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        window.setTitle(sceneTitle.getText());
        window.setWidth(300);
        window.setHeight(175);
        window.initStyle(StageStyle.UTILITY);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label boardTitle = new Label("Board Title:");
        grid.add(boardTitle, 0, 1);

        TextField boardTextField = new TextField();
        grid.add(boardTextField, 1, 1);

        Button btn = new Button("Create Board");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 2);

        Text boardTitleString = new Text();
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boardTitleString.setText(boardTextField.getText());
                window.close();
            }
        });

        window.setScene(new Scene(grid, 300, 175));
        window.showAndWait();
        return boardTitleString;
    }
}
