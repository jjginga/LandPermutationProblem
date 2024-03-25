package com.jjginga.State;

import java.util.*;

public class LandMap implements IState {
    private final int[][] landMap;
    private int borders;
    private int depth;

    public LandMap(int[][] landMap) {
        this.landMap = landMap;
        evaluate();
        depth = 0;
    }

    public LandMap(int[][] landMap, int depth) {
        this.landMap = landMap;
        evaluate();
        this.depth = depth;
    }

    public void evaluate() {
        this.borders = countBorders();
    }

    private int countBorders() {
        int borders = 0;
        for(int row = 0; row < landMap.length; row++)
            for (int col = 0; col < landMap[row].length; col++) {
                if (col<landMap[row].length - 1 && landMap[row][col] != landMap[row][col + 1])
                    borders++;
                if (row < landMap.length -1 && landMap[row][col] != landMap[row + 1][col])
                    borders++;
            }
        return borders;
    }

    public int getObjectiveValue() {
        return borders;
    }

    public List<IState> generateSucessors() {
        List<IState> successors = new LinkedList<>();

        for(int row = 0; row < landMap.length; row++){
            for(int col = 0; col < landMap[row].length; col++){
                //we evaluate the swap with the neighbour to the right
                if(col < landMap[row].length - 1 && landMap[row][col] != landMap[row][col+1]){
                    //we try the swap to evaluate if it is valid
                    if(trySwap(row, col, row, col + 1)){
                        //if is valid we clone and add to successors
                        successors.add(new LandMap(cloneMap(), this.getDepth() + 1));
                        //we revert the swap to original state
                        revertSwap(row, col, row, col + 1);
                    }
                }
                if(row < landMap.length - 1 && landMap[row][col] != landMap[row + 1][col]){
                    //we try the swap to evaluate if it is valid
                    if(trySwap(row, col, row + 1, col)){
                        //if is valid we clone and add to successors
                        successors.add(new LandMap(cloneMap(), this.getDepth() + 1));
                        //we revert the swap to original state
                        revertSwap(row, col, row + 1, col);
                    }
                }
            }
        }

        return successors;
    }

    private boolean trySwap(int row1, int col1, int row2, int col2){
        //we do the swap
        int tmp = landMap[row1][col1];
        landMap[row1][col1] = landMap[row2][col2];
        landMap[row2][col2] = tmp;

        //we count the borders after the swap
        int newBorders = countBorders();

        if(newBorders>this.borders){
            revertSwap(row1, col1, row2, col2);
            return false;
        }

        return true;
    }

    private void revertSwap(int row1, int col1, int row2, int col2) {
        //we simply revert the swap
        int tmp = landMap[row1][col1];
        landMap[row1][col1] = landMap[row2][col2];
        landMap[row2][col2] = tmp;
    }

    public int[][] cloneMap() {
        return Arrays.stream(this.landMap)
                     .map(int[]::clone)
                     .toArray(int[][]::new);
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LandMap)) return false;
        LandMap landMap = (LandMap) o;
        return Arrays.deepEquals(this.landMap, landMap.landMap);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(landMap);
    }

}