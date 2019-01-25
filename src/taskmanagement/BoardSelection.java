/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagement;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author alex
 */
public class BoardSelection {

    static Board selectedBoard = null;
    
    public static Board display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Text sceneTitle = new Text("Select board");
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

        Label boardTitle = new Label("Select Board:");
        grid.add(boardTitle, 0, 1);

        ArrayList<Board> b = getBoards();
        System.out.println(b);
        ObservableList<Board> boards = FXCollections.observableArrayList(b);

        ComboBox boardSelection = new ComboBox();
        boardSelection.getItems().addAll(boards);
        Callback<ListView<Board>, ListCell<Board>> factory = lv -> new ListCell<Board>() {

            @Override
            protected void updateItem(Board item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTitle());
            }
        
        };
        boardSelection.setCellFactory(factory);

        grid.add(boardSelection, 1, 1);

        Button btn = new Button("Select Board");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 2);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setSelectedBoard((Board)boardSelection.getValue());
                window.close();
            }
        });

        window.setScene(new Scene(grid, 300, 175));
        window.showAndWait();
        return selectedBoard;
    }

    public static void setSelectedBoard(Board board) {
        selectedBoard = new Board(board.getId(), board.getTitle(), board.getCards());
    } 

    public static ArrayList<Board> getBoards() {
        FileHandler f = new FileHandler();
        return f.getBoards();
    }
}
