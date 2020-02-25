package main.java.com.energyeet.astar.ui;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.java.com.energyeet.astar.AStarConstants;
import main.java.com.energyeet.astar.service.ThreadService;
import main.java.com.energyeet.astar.util.helper.EventHandlerHelper;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main UI Window class
 */
public class Main extends Application implements AStarConstants {

    private GridPane gridPaneRoot;
    private GridPane gridPaneContent;
    private HBox hBoxButtons;

    private MenuBar menuBar;
    private Stage primaryStage;
    private EventHandlerHelper eventHandlerHelper;
    private GridEditor gridEditor;
    private Slider sliderSpeed;

    public static void main(String[] args) {
        for (String s : args) {
            System.out.println(s);
        }
	    launch(args);
    }

    /**
     * Start method of the program
     * @param primaryStage {@link Stage} primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.gridPaneRoot = new GridPane();
        this.gridPaneContent = new GridPane();
        this.hBoxButtons = new HBox();
        this.eventHandlerHelper = new EventHandlerHelper(primaryStage);
        this.gridEditor = new GridEditor(15, 15);
        this.hBoxButtons.setSpacing(10);
        this.gridPaneContent.setVgap(10);

        gridPaneContent.setAlignment(Pos.CENTER);
        GridPane.setVgrow(gridPaneContent, Priority.ALWAYS);
        HBox.setHgrow(gridPaneRoot, Priority.ALWAYS);

        buildToolBar();
        buildButton();
        buildSlider();
        buildComponentsTogether();

        this.buildStage();
    }

    /**
     * Get the primary stage from the ui
     * @return {@link Stage} current stage
     */
    public Stage getPrimaryStage() { return this.primaryStage; }


    /**
     * Builds the slider together
     */
    private void buildSlider() {
        this.sliderSpeed = new Slider(1, 1000, 1000);
        this.sliderSpeed.setRotate(180);
    }

    /**
     * Builds the toolbar together
     */
    private void buildToolBar() {
        this.menuBar = new MenuBar();
        Menu fileMenu = new Menu(MENU_FILE_CAPTION);
        MenuItem saveItem = new MenuItem(MENUITEM_SAVE_CAPTION);
        saveItem.setOnAction(eventHandlerHelper.menuBarHandler(gridEditor));
        MenuItem openItem = new MenuItem(MENUITEM_OPEN_CAPTION);
        openItem.setOnAction(eventHandlerHelper.menuBarHandler(gridEditor));

        fileMenu.getItems().add(saveItem);
        fileMenu.getItems().add(openItem);
        this.menuBar.getMenus().add(fileMenu);

        GridPane.setHgrow(menuBar, Priority.ALWAYS);
    }

    /**
     * Builds the buttons together
     */
    private void buildButton() {
        Button buttonStart = new Button(BUTTON_START_CAPTION);
        buttonStart.setOnAction(event -> {
            ThreadService.startSearchThread(gridEditor.getCellArray(), this.sliderSpeed);
        });
        this.hBoxButtons.getChildren().add(buttonStart);

        Button buttonClear = new Button(BUTTON_CLEAR_CAPTION);
        buttonClear.setOnAction(event -> {
            gridEditor.rebuild();
        });
        this.hBoxButtons.getChildren().add(buttonClear);

        Button buttonClearSolution = new Button(BUTTON_CLEARSOLUTION_CAPTION);
        buttonClearSolution.setOnAction(event -> {
            gridEditor.clearPath();
        });
        this.hBoxButtons.getChildren().add(buttonClearSolution);
    }

    /**
     * Builds the components for the stage
     */
    private void buildStage() {
        Scene rootScene = new Scene(gridPaneRoot, 900, 600);
        rootScene.getStylesheets().add(this.getClass().getClassLoader().getResource("style.css").toString());

        Image image = new Image(Main.class.getResourceAsStream("/logo.png"));
        this.primaryStage.getIcons().add(image);
        this.primaryStage.setScene(rootScene);
        this.primaryStage.setTitle(WINDOW_TITLE);
        this.primaryStage.show();
    }

    /**
     * Builds all the components of UI together
     */
    private void buildComponentsTogether() {
        gridPaneContent.add(menuBar,0,0);

        gridPaneContent.add(gridEditor,0,0);
        gridPaneContent.add(hBoxButtons,0,2);
        gridPaneContent.add(sliderSpeed,0,1);

        this.gridPaneRoot.add(menuBar,0,0);
        this.gridPaneRoot.add(gridPaneContent, 0, 1);
    }
}
