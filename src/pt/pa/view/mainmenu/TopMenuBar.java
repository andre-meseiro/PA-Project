package pt.pa.view.mainmenu;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.commands.graphviewhandler.BackgroundType;

/**
 * This class is used to define the display of the top menu bar.
 *
 * @author André Meseiro 202100225, Pedro Anjos 202100230, Catarina Jesus 202000594 and Daniel Roque 201901608
 */
public class TopMenuBar extends MenuBar {
    private final GraphDisplay graphDisplay;

    /**
     * Creates a TopMenuBar instance.
     *
     * @param graphDisplay GraphDisplay the graph display
     */
    public TopMenuBar(GraphDisplay graphDisplay) {
        this.graphDisplay = graphDisplay;
        drawMenus();
    }

    /**
     * Auxiliary method to draw the menus.
     */
    private void drawMenus() {
        Menu fileMenu = drawFileMenu();
        Menu viewMenu = drawViewMenu();
        getMenus().addAll(fileMenu, viewMenu);
    }

    /**
     * Auxiliary method to draw the file menu.
     *
     * @return Menu the drawn menu
     */
    private Menu drawFileMenu() {
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> ((Stage) this.getScene().getWindow()).close());
        MenuItem changeDataset = drawChangeDatasetMenu();
        MenuItem exportItem = new MenuItem("Export Dataset");
        exportItem.setOnAction(e -> {
            Stage stage = new Stage(StageStyle.DECORATED);
            ExportDatasetPanel exportPromptPane = new ExportDatasetPanel(graphDisplay);
            Scene scene = new Scene(exportPromptPane, 250, 100);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Export Dataset");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.getScene().getWindow());
            stage.showAndWait();
        });
        fileMenu.getItems().addAll(changeDataset, exportItem, exitItem);
        return fileMenu;
    }

    /**
     * Auxiliary method to draw the menu to change the dataset.
     *
     * @return MenuItem the drawn menu
     */
    private MenuItem drawChangeDatasetMenu() {
        MenuItem changeDatasetMenu = new MenuItem("Change Dataset");
        changeDatasetMenu.setOnAction(e -> {
            Scene scene = new Scene(new DatasetLoaderPanel(), 700, 350);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Projeto PA 2223 - Bus Network");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            ((Stage) getScene().getWindow()).close();
        });
        return changeDatasetMenu;
    }

    /**
     * Auxiliary method to draw the view menu.
     *
     * @return Menu the drawn menu
     */
    private Menu drawViewMenu() {
        Menu viewMenu = new Menu("View");
        Menu mapChangeMenu = drawChangeBackgroundMenu();
        viewMenu.getItems().add(mapChangeMenu);
        return viewMenu;
    }

    /**
     * Auxiliary method to draw the menu to change the background.
     *
     * @return Menu the drawn menu
     */
    private Menu drawChangeBackgroundMenu() {
        Menu mapChangeMenu = new Menu("Change Background");
        MenuItem defaultMapItem = new MenuItem("Default");
        defaultMapItem.setOnAction(e -> changeBackground(BackgroundType.DEFAULT));
        MenuItem darkMapItem = new MenuItem("Dark");
        darkMapItem.setOnAction(e -> changeBackground(BackgroundType.DARK));
        MenuItem terrainMapItem = new MenuItem("Terrain");
        terrainMapItem.setOnAction(e -> changeBackground(BackgroundType.TERRAIN));
        MenuItem satelliteMapItem = new MenuItem("Satellite");
        satelliteMapItem.setOnAction(e -> changeBackground(BackgroundType.SATELLITE));
        mapChangeMenu.getItems().addAll(defaultMapItem, darkMapItem, terrainMapItem, satelliteMapItem);
        return mapChangeMenu;
    }

    /**
     * Auxiliary method to change the background.
     *
     * @param backgroundType BackgroundType the background type
     */
    private void changeBackground(BackgroundType backgroundType) {
        graphDisplay.changeBackground(backgroundType);
    }

}

