package main.java.com.energyeet.astar.service;

import javafx.scene.control.Slider;
import main.java.com.energyeet.astar.util.GridCell;
import main.java.com.energyeet.astar.util.GridCellState;
import main.java.com.energyeet.astar.util.threads.SearchThread;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class for handling the threads
 */
public class ThreadService {

    /**
     * Starts the thread to search for the quickest path
     * @param cellArray {@link GridCell}
     * @param slider {@link Slider}
     */
    public static void startSearchThread(GridCell[][] cellArray, Slider slider) {
        // Example of how to show start a new thread with the runnable interface
        SearchThread searchThread = new SearchThread(cellArray);
        Thread thread = new Thread(() -> {
            searchThread.run();
            List<GridCell> path = searchThread.getPath();
            Collections.reverse(path);
            path.forEach((GridCell cell) -> {
                try {
                    Thread.sleep((int)slider.getValue());
                    if (cell.getState() != GridCellState.END) {
                        cell.getStyleClass().remove(cell.getStyleClass().get(0));
                        cell.getStyleClass().add("solution");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread.currentThread().interrupt();
        });
        thread.start();
    }
}
