package main.java.com.energyeet.astar.util;

import javafx.scene.shape.Rectangle;

/**
 * Cell which is represented on the {@link main.java.com.energyeet.astar.ui.GridEditor}
 */
public class GridCell extends Rectangle {
    private GridCellState state; // state of the cell
    private int fCost;
    private int hCost;

    private int x;
    private int y;

    private boolean solution = false;
    private GridCell previousCell;

    /**
     * Constructor
     * @param height {@link double} height of cell
     * @param width {@link double} width of cell
     */
    public GridCell(double height, double width, int x, int y) {
        this.state = GridCellState.NONE;
        this.setHeight(height);
        this.setWidth(width);
        this.fCost = 0;
        this.hCost = 0;
        this.x = x;
        this.y = y;
        this.setClass();
    }

    /**
     * Gets the current state of the cell
     * @return {@link GridCellState} state of the cell
     */
    public GridCellState getState() {
        return state;
    }

    /**
     * Sets the state of the cell
     * @param state {@link GridCellState} state of the cell
     */
    public void setState(GridCellState state) {
        this.state = state;
        this.setClass();
    }

    /**
     * Gets the x-position from the grid
     * @return {@link int} x-position
     */
    public int getXPosition() {
        return this.x;
    }

    /**
     * Gets the <-position from the grid
     * @return {@link int} y-position
     */
    public int getYPosition() {
        return this.y;
    }

    /**
     * Gets the f-cost for the cell
     * @return {@link int} fCost
     */
    public int getFCost() {
        return this.fCost;
    }

    public int getHCost() {return this.hCost; }

    /**
     * Sets the h-cost for the cell
     * @param hCost {@link int} new h-cost
     */
    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    /**
     * Sets the f-cost for the cell
     * @param fCost {@link int} new f-cost
     */
    public void setFCost(int fCost) {
        this.fCost = fCost;
    }

    /**
     * Gets the previous cell
     * @return {@link int} previous cell
     */
    public GridCell getPreviousCell() {
        return this.previousCell;
    }

    /**
     * Sets the previous cell of the cell
     * @param previousCell {@link GridCell} previous cell
     */
    public void setPreviousCell(GridCell previousCell) {
        this.previousCell = previousCell;
    }

    /**
     * Sets if the cell is a part of the solution
     * @param solution {@link boolean} is solution
     */
    public void setSolution(boolean solution) {
        this.solution = solution;
    }

    /**
     * Sets the right CSS-classes with the dependency on the {@link GridCellState}
     */
    private void setClass() {
        if (!this.getStyleClass().isEmpty()) {
            this.getStyleClass().remove(this.getStyleClass().get(0));
        }
        if (this.state == GridCellState.START) {
            this.getStyleClass().add("start-point");
        } else if (this.state == GridCellState.END) {
            this.getStyleClass().add("end-point");
        } else if (this.state == GridCellState.WALL) {
            this.getStyleClass().add("wall");
        } else {
            this.getStyleClass().add("none");
        }
    }

    /**
     * Gets if the gricell is part of the solution
     * @return {@link boolean}
     */
    public boolean getIsSolution() {
        return this.solution;
    }

    /**
     * Sets the value of solution
     * @param isSolution {@link boolean}
     */
    public void setIsSolution(boolean isSolution) {
        this.solution = isSolution;
    }
}
