package main.java.com.energyeet.astar.util.factory;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.java.com.energyeet.astar.ui.GridEditor;
import main.java.com.energyeet.astar.util.GridCell;
import main.java.com.energyeet.astar.util.GridCellState;

public class GridEditorHandlerFactory {
    private GridEditorHandlerFactory() {
    }

    /**
     * Sets the state of the gridcell
     * @param event {@link MouseEvent}
     * @param clickedCell {@link GridCell}
     */
    public static void setCellState(MouseEvent event, GridCell clickedCell) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {

            if (event.isControlDown()) {
                if (clickedCell.getState() == GridCellState.END) {
                        GridEditor.isEndPointSet = false;
                } else if (clickedCell.getState() == GridCellState.START) {
                        GridEditor.isStartPointSet = false;
                }
                clickedCell.setState(GridCellState.WALL);

            } else if(event.isShiftDown()) {
                if (clickedCell.getState() == GridCellState.END) {
                        GridEditor.isEndPointSet = false;
                } else if (clickedCell.getState() == GridCellState.START) {
                        GridEditor.isStartPointSet = false;
                }
                clickedCell.setState(GridCellState.NONE);

            } else if (!GridEditor.isStartPointSet) {
                if (clickedCell.getState() == GridCellState.END) {
                    GridEditor.isEndPointSet = false;
                }
                clickedCell.setState(GridCellState.START);
                GridEditor.isStartPointSet = true;
            }
        } else if (event.getButton().equals(MouseButton.SECONDARY) && !GridEditor.isEndPointSet) {
            if (clickedCell.getState() == GridCellState.START) {
                GridEditor.isStartPointSet = false;
            }
            clickedCell.setState(GridCellState.END);
            GridEditor.isEndPointSet = true;
        }
    }
}
