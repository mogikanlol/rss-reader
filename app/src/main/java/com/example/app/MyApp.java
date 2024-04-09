package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        VBox vBox = new VBox(10);

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);

        VBox container = new VBox();
        HBox buttonContainer = new HBox();
        Button addRssFeedButton = new Button("Add New Rss Feed");
        addRssFeedButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Add new rss link");
            dialog.showAndWait().ifPresent(System.out::println);
        });

        buttonContainer.getChildren().addAll(addRssFeedButton);

        container.getChildren().addAll(buttonContainer, scrollPane);

        BorderPane borderPane = new BorderPane();
        VBox left = new VBox(10);
        Label l1 = new Label("comrakoff");

        Label l2 = new Label("uniqlo");
        {
            l1.setMinHeight(25);
            l2.setMinHeight(25);
            l1.setMaxWidth(Double.MAX_VALUE);
            l2.setMaxWidth(Double.MAX_VALUE);
            l1.setAlignment(Pos.CENTER);
            l2.setAlignment(Pos.CENTER);
            l1.setBackground(Background.fill(Paint.valueOf("#ffffff")));
            l2.setBackground(Background.fill(Paint.valueOf("#ffffff")));
            l1.setOnMouseEntered(event -> {
                l1.setBackground(Background.fill(Paint.valueOf("#e2cddb")));
            });
            l1.setOnMouseExited(event -> {
                l1.setBackground(Background.fill(Paint.valueOf("#ffffff")));
            });
            l2.setOnMouseEntered(event -> {
                l2.setBackground(Background.fill(Paint.valueOf("#e2cddb")));
            });
            l2.setOnMouseExited(event -> {
                l2.setBackground(Background.fill(Paint.valueOf("#ffffff")));
            });
            l1.setStyle("-fx-border-color: black;");
            l2.setStyle("-fx-border-color: black;");
        }


//        left.setStyle("-fx-border-color: black;");
        List<Label> addedRss = List.of(
                l1,
                l2
        );
        left.setFillWidth(true);
        left.setMinWidth(200);
//        scrollPane.setStyle("-fx-border-color: black;");
        left.getChildren().addAll(
                addedRss
        );
        borderPane.setTop(buttonContainer);
        borderPane.setLeft(left);
        borderPane.setCenter(scrollPane);

        BorderPane.setMargin(buttonContainer, new Insets(10));
        BorderPane.setMargin(left, new Insets(10));
        BorderPane.setMargin(scrollPane, new Insets(10));
//        scrollPane.setFitToWidth(true);
//        scrollPane.setMaxWidth(Double.MAX_VALUE);
//        scrollPane.setStyle("-fx-border-color: cyan;");


        Scene scene = new Scene(borderPane, 800, 800);
        stage.setScene(scene);

        vBox.getChildren().addAll(
                      createComrakoff(stage)
        );
        List<Label> comrakoff = createComrakoff(stage);
        l1.setOnMouseClicked(event -> {
            vBox.getChildren().setAll(comrakoff);
        });
        List<Label> uniqlo = createUniqlo(stage);
        l2.setOnMouseClicked(event -> {
            vBox.getChildren().setAll(uniqlo);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    List<Label> createComrakoff(Stage stage) throws Exception {
        RssParser parser1 = new RssParser("https://www.youtube.com/feeds/videos.xml?channel_id=UChr1x_GdwoNkduX5956VyGA", "comrakof");
//        RssParser parser1 = new RssParser("https://netflixtechblog.com/feed", "netflix");
//        RssParser parser1 = new RssParser("https://www.uniqlo.com/jp/ja/contents/corp/press-release/index.xml", "netflix");
//        RssParser parser1 = new RssParser("https://blog.golodnyj.ru/feeds/posts/default?alt=rss", "netflix");
        List<FeedMessage> feedMessages = parser1.readRss();
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
                    Text descriptionText = new Text(m.description());;
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

                    descriptionText.setWrappingWidth(800);

                    content.getChildren().addAll(title, titleText, link, separator, description, descriptionText, backButton);

                    Scene scene = new Scene(content, 800, 800);

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

    List<Label> createUniqlo(Stage stage) throws Exception {
//        RssParser parser1 = new RssParser("https://www.youtube.com/feeds/videos.xml?channel_id=UChr1x_GdwoNkduX5956VyGA", "comrakof");
//        RssParser parser1 = new RssParser("https://netflixtechblog.com/feed", "netflix");
        RssParser parser1 = new RssParser("https://www.uniqlo.com/jp/ja/contents/corp/press-release/index.xml", "netflix");
//        RssParser parser1 = new RssParser("https://blog.golodnyj.ru/feeds/posts/default?alt=rss", "netflix");
        List<FeedMessage> feedMessages = parser1.readRss();
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
                    Text descriptionText = new Text(m.description());;
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

                    descriptionText.setWrappingWidth(800);

//                    content.getChildren().addAll(title, titleText, link, separator, description, descriptionText, backButton);
                    ScrollPane scrollPane = new ScrollPane(new VBox() {{getChildren().addAll(backButton, title, titleText, link, separator, description, descriptionText);}});
                    content.getChildren().addAll(scrollPane);

                    Scene scene = new Scene(content, 800, 800);

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
}
