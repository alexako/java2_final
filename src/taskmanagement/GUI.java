package taskmanagement;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class GUI extends Application {

    Stage window;
    TextField titleInput, descriptionInput, quantityInput;
    GridPane gridPane;
    ObservableList<Card> cards;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Task Management Project - Java 2");

        gridPane = new GridPane();

        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        VBox column1 = new VBox();
        VBox column2 = new VBox();
        VBox column3 = new VBox();
        column1.setSpacing(5);
        column2.setSpacing(5);
        column3.setSpacing(5);
        column1.setPrefWidth(250);
        column2.setPrefWidth(250);
        column3.setPrefWidth(250);

        gridPane.add(column1, 0, 0);
        gridPane.add(column2, 1, 0);
        gridPane.add(column3, 2, 0);

        FileHandler f = new FileHandler();
        f.loadUsers();
        ArrayList<User> users = f.getUsers();

        // Set column labels
        Label toDoLabel = new Label("To Do");
        Label inProgressLabel = new Label("In Progress");
        Label forReviewLabel = new Label("For Review");

        toDoLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");
        inProgressLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");
        forReviewLabel.setStyle("-fx-label-padding: 10px; -fx-wrap-text: true;");

        column1.getChildren().add(toDoLabel);
        column2.getChildren().add(inProgressLabel);
        column3.getChildren().add(forReviewLabel);

        // Card Title input
        titleInput = new TextField();
        titleInput.setPromptText("Title");
        titleInput.setMinWidth(100);

        // Details input
        descriptionInput = new TextField();
        descriptionInput.setPromptText("Description");

        // Add Card Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());

        EventHandler<javafx.scene.input.MouseEvent> eventHandler = 
                    new EventHandler<javafx.scene.input.MouseEvent>() { 
            @Override 
            public void handle(javafx.scene.input.MouseEvent e) { 
                System.out.println("Click!" + e); 
            } 
        };  

        // Drop shadow for cards
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.DARKGRAY);
        ds.setHeight(1);
        ds.setWidth(1);
        ds.setOffsetX(1);
        ds.setOffsetY(1);
        ds.setRadius(5);
        ds.setSpread(0.01);

        // Load cards
        cards = getCards();
        for (int i = 0; i < cards.size(); i++) {
            Button cardContainer = new Button(cards.get(i).getTitle());
            cardContainer.setStyle("-fx-background-color: white; -fx-pref-width: 200px;");
            cardContainer.setAlignment(Pos.BASELINE_LEFT);
            cardContainer.setEffect(ds);
            cardContainer.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
            column1.getChildren().add(cardContainer);
        }
        for (int i = 0; i < cards.size() - 5; i++) {
            Button cardContainer = new Button(cards.get(i).getTitle());
            cardContainer.setStyle("-fx-background-color: white;");
            cardContainer.setAlignment(Pos.BASELINE_LEFT);
            cardContainer.setEffect(ds);
            cardContainer.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
            column2.getChildren().add(cardContainer);
        }
        Button cardContainer = new Button(cards.get(7).getTitle());
        cardContainer.setStyle("-fx-background-color: white;");
        cardContainer.setAlignment(Pos.BASELINE_LEFT);
        cardContainer.setEffect(ds);
        cardContainer.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
        column3.getChildren().add(cardContainer);

        Label connectionStatus = new Label("Not connected ");
        Circle c = new Circle(8, Color.RED);
        Group circle = new Group(c);

        HBox connectionIndicator = new HBox();
        connectionIndicator.getChildren().addAll(connectionStatus, circle);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox header = new HBox();
        header.getChildren().addAll(region, connectionIndicator);

        ComboBox userSelection = new ComboBox();
        userSelection.getItems().addAll(users);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(titleInput, descriptionInput, userSelection, addButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setStyle("-fx-stroke-width: 0");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(header);
        root.setCenter(scrollPane);
        root.setBottom(hBox);
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
        window.show();
    }

    //Add button clicked
    public void addButtonClicked(){
        // Product product = new Product();
        // product.setName(nameInput.getText());
        // product.setPrice(Double.parseDouble(priceInput.getText()));
        // product.setQuantity(Integer.parseInt(quantityInput.getText()));
        // table.getItems().add(product);
        // nameInput.clear();
        // priceInput.clear();
        // quantityInput.clear();

        // private String id;
        // private String title;
        // private String description;
        // private User assignedUser;
        // private int boardId;
        // private int columnIndex;

        Card card = new Card();
        card.setTitle(titleInput.getText());
        card.setDescription(descriptionInput.getText());
        cards.add(card);
        gridPane.add(new Button(card.getTitle()), 0, cards.size());

        titleInput.clear();
        descriptionInput.clear();
    }

    //Get all of the products
    public ObservableList<Card> getCards(){
         ObservableList<Card> cards = FXCollections.observableArrayList();
         cards.add(new Card("1", "Fix UI"));
         cards.add(new Card("2", "Refactor initial load"));
         cards.add(new Card("3", "Add new feature 1"));
         cards.add(new Card("4", "Add new feature 2"));
         cards.add(new Card("5", "Fix UI"));
         cards.add(new Card("6", "Refactor initial load"));
         cards.add(new Card("7", "Add new feature 1"));
         cards.add(new Card("8", "Add new feature 2"));

        return cards;
    }

}