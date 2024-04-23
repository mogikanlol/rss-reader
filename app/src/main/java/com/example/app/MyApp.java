package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;

public class MyApp extends Application {

    // MENSH https://www.youtube.com/feeds/videos.xml?channel_id=UC2nWt5s1PYNL5auuZBYRaCg
    // GOBLIN https://feeds.feedburner.com/oper_ru

    private static final String UNIQLO_URL = "https://www.uniqlo.com/jp/ja/contents/corp/press-release/index.xml";
    private static final String COMRAKOFF_URL = "https://www.youtube.com/feeds/videos.xml?channel_id=UChr1x_GdwoNkduX5956VyGA";


    FeedManager feedManager;

    public MyApp() {
        super();
        feedManager = new FeedManager();
    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox vBox = new VBox(10);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);

        VBox left = new VBox(10);
        left.setFillWidth(true);
        left.setMinWidth(200);

        HBox buttonContainer = new HBox();
        Button addRssFeedButton = new Button("Add New Rss Feed");
        addRssFeedButton.setOnAction(event -> {
            Dialog<Pair<String, String>> dialog = createAddNewRssFeedDialog();
            dialog.showAndWait().ifPresent(stringStringPair -> {
                addNewRssFeed(stringStringPair, left, stage, vBox);
            });
        });
        buttonContainer.getChildren().addAll(addRssFeedButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(buttonContainer);
        borderPane.setLeft(left);
        borderPane.setCenter(scrollPane);

        BorderPane.setMargin(buttonContainer, new Insets(10));
        BorderPane.setMargin(left, new Insets(10));
        BorderPane.setMargin(scrollPane, new Insets(10));


        Scene scene = new Scene(borderPane, 800, 800);
        stage.setScene(scene);

        List<FeedEntity> existedFeed = feedManager.getExistedFeed();
        for (FeedEntity feedEntity : existedFeed) {
            Label label = createLeftLabel(feedEntity.name());
            List<Label> centerLabels = createCenterLabels(feedEntity.feedMessages(), stage);

            label.setOnMouseClicked(event -> {
                vBox.getChildren().setAll(centerLabels);
            });
            left.getChildren().add(label);
        }

        stage.show();
    }

    private Label createLeftLabel(String name) {
        Label l1 = new Label(name);
        l1.setMinHeight(25);
        l1.setMaxWidth(Double.MAX_VALUE);
        l1.setAlignment(Pos.CENTER);
        l1.setBackground(Background.fill(Paint.valueOf("#ffffff")));
        l1.setOnMouseEntered(event -> {
            l1.setBackground(Background.fill(Paint.valueOf("#e2cddb")));
        });
        l1.setOnMouseExited(event -> {
            l1.setBackground(Background.fill(Paint.valueOf("#ffffff")));
        });
        l1.setStyle("-fx-border-color: black;");
        return l1;
    }

    private List<Label> createCenterLabels(List<FeedMessage> feedMessages, Stage stage) {
        return feedMessages.stream()
                .map(m -> {
                    Label label = new Label(m.title());
                    label.setMaxWidth(Double.MAX_VALUE);
                    label.setBackground(Background.fill(Paint.valueOf("#ffffff")));
//                            label.prefHeight(300);
                    label.setMinHeight(50);
//                            label.setContentDisplay(ContentDisplay.CENTER);
                    label.setAlignment(Pos.CENTER);
                    label.setOnMouseEntered(event -> {
                        label.setBackground(Background.fill(Paint.valueOf("#e2cddb")));
                    });
                    label.setOnMouseExited(event -> {
                        label.setBackground(Background.fill(Paint.valueOf("#ffffff")));
                    });

                    VBox content = new VBox();
                    Label title = new Label("Title");
                    Text titleText = new Text(m.title());
                    Separator separator = new Separator(Orientation.HORIZONTAL);
                    Label description = new Label("Description");
                    Text descriptionText = new Text(m.description());
                    Hyperlink link = new Hyperlink(m.link());
                    link.setOnAction(e -> {
                        System.out.println("FIRE!");
                        getHostServices().showDocument(link.getText());
                    });
                    Button backButton = new Button("Go Back");
                    Scene currentScene = stage.getScene();

                    backButton.setOnAction(event -> {
                        stage.setScene(currentScene);
                    });


//                    content.getChildren().addAll(title, titleText, link, separator, description, descriptionText, backButton);
                    VBox content1 = new VBox() {{
//                        getChildren().addAll(backButton, title, titleText, link, separator, description, descriptionText);
                        getChildren().addAll(backButton, titleText, link, separator, descriptionText);
                    }};
//                    content1.setStyle("-fx-border-color: black;");
                    ScrollPane scrollPane = new ScrollPane(content1);
//                    scrollPane.setFitToWidth(true);

                    content.getChildren().addAll(scrollPane);

                    Scene scene = new Scene(content, 800, 800);
                    content1.setPadding(new Insets(20));
                    descriptionText.wrappingWidthProperty().bind(scrollPane.widthProperty().subtract(100));

                    label.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            stage.setScene(scene);


                            stage.show();
                        }
                    });
                    label.setStyle("-fx-border-color: black;");
                    return label;
                }).toList();
    }


    private void addNewRssFeed(Pair<String, String> stringStringPair, VBox left, Stage stage, VBox vBox) {
        try {
            FeedEntity entity = feedManager.createNew(stringStringPair.getKey(), stringStringPair.getValue());
            Label label = createLeftLabel(entity.name());
            List<Label> centerLabels = createCenterLabels(entity.feedMessages(), stage);

            label.setOnMouseClicked(event -> {
                vBox.getChildren().setAll(centerLabels);
            });
            left.getChildren().add(label);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Dialog<Pair<String, String>> createAddNewRssFeedDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField feedName = new TextField();
        feedName.setPromptText("Feed Name");
        TextField feedUrl = new TextField();
        feedUrl.setPromptText("Feed Url");

        // https://code.makery.ch/blog/javafx-dialogs-official/
        grid.add(new Label("Feed Name:"), 0, 0);
        grid.add(feedName, 1, 0);
        grid.add(new Label("Feed Url:"), 0, 1);
        grid.add(feedUrl, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton ->
        {
            if (dialogButton.equals(ButtonType.APPLY)) {
                return new Pair<>(feedName.getText(), feedUrl.getText());
            }
            return null;
        });
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CLOSE);
        // https://stackoverflow.com/questions/32048348/javafx-scene-control-dialogr-wont-close-on-pressing-x
        var closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

//            TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Add new rss link");
        return dialog;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
