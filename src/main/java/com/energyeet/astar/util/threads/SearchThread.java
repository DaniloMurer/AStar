package main.java.com.energyeet.astar.util.threads;

import main.java.com.energyeet.astar.util.GridCell;
import main.java.com.energyeet.astar.util.GridCellState;

import java.util.*;
import java.util.List;

public class SearchThread implements Runnable {
    private static final int DIAGONAL_COST = 20;
    private static final int V_H_COST = 10;

    private GridCell startPointCoordinates;
    private GridCell endPointCoordinates;
    private GridCell[][] cellArray;
    private List<GridCell> path;

    private PriorityQueue<GridCell> openCells;
    private boolean[][] closedCells;

    /**
     * Constructor
     * @param cellArray {@link GridCell} all the cells of the grid
     */
    public SearchThread(GridCell[][] cellArray) {
        this.openCells = new PriorityQueue<>(Comparator.comparingInt(GridCell::getFCost));
        this.closedCells = new boolean[cellArray.length][cellArray[0].length];
        this.cellArray = cellArray;
        this.path = new ArrayList<>();

        //Searching for start- and end-point
        for (int i = 0; i < this.cellArray.length; i++) {
            for (int j = 0; j < this.cellArray[i].length; j++) {
                 if (this.cellArray[i][j].getState() == GridCellState.START) {
                    this.startPointCoordinates = cellArray[i][j];
                } else if (this.cellArray[i][j].getState() == GridCellState.END) {
                    this.endPointCoordinates = cellArray[i][j];
                }
            }
        }

        //Calculates the costs for every cell to step on
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                //cellArray[i][j].setHCost(Math.max(Math.abs(i - endPointCoordinates.getYPosition()), Math.abs(j - endPointCoordinates.getXPosition())));
                cellArray[i][j].setHCost((int)Math.sqrt((i - endPointCoordinates.getYPosition()) * 2 + (int)(j - endPointCoordinates.getXPosition()) * 2));
            }
        }

        startPointCoordinates.setFCost(0);
    }

    @Override
    public void run() {
        this.process();
        this.processResults();
    }

    public List<GridCell> getPath() {
        return this.path;
    }

    /**
     * Updates the cost of the cell
     * @param current {@link GridCell} the cell which is getting processed
     * @param previousInLine {@link GridCell} the cell one before the current line
     * @param cost {@link int} the cost to step on the current cell
     */
     private void updateCostIfNeeded(GridCell current, GridCell previousInLine, int cost) {

        //Checks if there is a previous cell even existing and also if hes already a cell which is a part of the solution-path
        if (previousInLine == null || closedCells[previousInLine.getYPosition()][previousInLine.getXPosition()]) {
            return;
        }

        int tFinalCost = previousInLine.getHCost() + cost;
        boolean isOpen = openCells.contains(previousInLine);

        if (!isOpen || tFinalCost < previousInLine.getFCost()) {
            previousInLine.setFCost(tFinalCost);
            previousInLine.setPreviousCell(current);
            if (!isOpen) {
                openCells.add(previousInLine);
            }
        }
    }

    /**
     * processes every single cell and their costs
     */
    private void process() {
        openCells.add(startPointCoordinates);
        GridCell current;
        GridCell previousInLine;

        while (true) {
            current = openCells.poll(); // Gets the next cell in the list

            //Checks if the current cell is empty or a wall
            if (current != null && current.getState() != GridCellState.WALL) {
                //Sets this cell as a processed cell
                closedCells[current.getYPosition()][current.getXPosition()] = true;
            if (current == null || current.getState() == GridCellState.WALL) {
                continue;
            }

            //Sets this cell as a processed cell
            closedCells[current.getYPosition()][current.getXPosition()] = true;

                //Checks if the current cell is the end-point
                if (current.equals(endPointCoordinates)) {
                    return;
                }

                //Checking if the there is a upper row
                if (current.getYPosition() - 1 >= 0) {
                    previousInLine = cellArray[current.getYPosition() - 1][current.getXPosition()];
                    updateCostIfNeeded(current, previousInLine, current.getFCost() + V_H_COST);

                    //Checking if there is a column on the left
                    if (current.getXPosition() - 1 >= 0) {
                        previousInLine = cellArray[current.getYPosition() - 1][current.getXPosition() - 1];
                        updateCostIfNeeded(current, previousInLine, current.getFCost() + DIAGONAL_COST);
                    }

                    //Checking if there is a column on the right
                    if (current.getXPosition() + 1 < cellArray[0].length) {
                        previousInLine = cellArray[current.getYPosition() - 1][current.getXPosition() + 1];
                        updateCostIfNeeded(current, previousInLine, current.getFCost() + DIAGONAL_COST);
                    }
                }

                //Checking if there is a row on the bottom
                if (current.getXPosition() - 1 >= 0) {
                    previousInLine = cellArray[current.getYPosition()][current.getXPosition() - 1];
                    updateCostIfNeeded(current, previousInLine, current.getFCost() + V_H_COST);
                }

                //Checking if there is a column on the left
                if (current.getXPosition() + 1 < cellArray[0].length) {
                    previousInLine = cellArray[current.getYPosition()][current.getXPosition() + 1];
                    updateCostIfNeeded(current, previousInLine, current.getFCost() + V_H_COST);
                }

                //Checking if there is a column on the right
                if (current.getYPosition() + 1 < cellArray.length) {
                    previousInLine = cellArray[current.getYPosition() + 1][current.getXPosition()];
                    updateCostIfNeeded(current, previousInLine, current.getFCost() + V_H_COST);

                    if (current.getXPosition() - 1 >= 0) {
                        previousInLine = cellArray[current.getYPosition() + 1][current.getXPosition() - 1];
                        updateCostIfNeeded(current, previousInLine, current.getFCost() + DIAGONAL_COST);
                    }

                    if (current.getXPosition() + 1 < cellArray[0].length) {
                        previousInLine = cellArray[current.getYPosition() + 1][current.getXPosition() + 1];
                        updateCostIfNeeded(current, previousInLine, current.getFCost() + DIAGONAL_COST);
                    }
                }
            }
        }
    }

    /**
     * Processes the values of the gridcell and checks if it is a solution-part or not
     */
    private void processResults() {
        if (closedCells[endPointCoordinates.getYPosition()][endPointCoordinates.getXPosition()]) {
            GridCell current = endPointCoordinates;
            cellArray[current.getYPosition()][current.getXPosition()].setSolution(true);

            while (current.getPreviousCell() != null) {
                if (current.getPreviousCell().getState() != GridCellState.WALL) {
                    cellArray[current.getPreviousCell().getYPosition()][current.getPreviousCell().getXPosition()].setSolution(true);
                    path.add(current);
                }
                current = current.getPreviousCell();
            }
        }
    }
}
