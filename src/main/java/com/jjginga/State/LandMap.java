package com.jjginga.State;

import java.util.*;

public class LandMap implements IState {
    private int[][] landMap;
    private int borders;
    private int depth;

    public LandMap(int[][] landMap) {
        this.landMap = landMap;
        evaluate();
        depth = 0;
    }

    public void evaluate() {
        int borders = 0;

        for (int[] row : landMap)
            for (int col = 0; col < row.length - 1; col++)
                if (row[col] != row[col + 1])
                    borders++;

        for(int row = 0; row < landMap.length - 1; row++)
            for (int col = 0; col < landMap[row].length; col++)
                if (landMap[row][col] != landMap[row + 1][col])
                    borders++;

        this.borders = borders;
    }

    public int getObjectiveValue() {
        return borders;
    }
    public List<IState> generateSucessors() {
        List<IState> successors = new LinkedList<>();
        Set<IState> visited = new HashSet<>();

        //swap horizontally
        for(int row = 0; row < landMap.length; row++){
            for(int col = 0; col < landMap[row].length-1; col++){
                if(landMap[row][col] != landMap[row][col+1]){
                    //clone and swap land
                    LandMap newLandMap = swap(row, col, row, col + 1);

                    evaluateAndAddSuccessor(visited, newLandMap, successors);
                }
            }
        }

        //swap vertically
        for(int row = 0; row < landMap.length-1; row++){
            for(int col = 0; col < landMap[row].length; col++){
                if(landMap[row][col]!= landMap[row+1][col]){
                    //clone and swap land
                    LandMap newLandMap = swap(row, col, row + 1, col);

                    evaluateAndAddSuccessor(visited, newLandMap, successors);
                }
            }
        }

        //swap diagonally

        return successors;

    }

    private LandMap swap(int originRow, int originCol, int destinationRow, int destinationCol) {
        LandMap newLandMap = clone();
        newLandMap.landMap[originRow][originCol] = newLandMap.landMap[destinationRow][destinationCol];
        newLandMap.landMap[destinationRow][destinationCol] = landMap[originRow][originCol];
        return newLandMap;
    }

    private void evaluateAndAddSuccessor(Set<IState> visited, LandMap newLandMap, List<IState> successors) {
        //check if already visited
        if(visited.contains(newLandMap))
            return;

        //count borders
        newLandMap.evaluate();

        if(newLandMap.getObjectiveValue() <= this.getObjectiveValue()){
            newLandMap.setDepth(this.getDepth() + 1);
            successors.add(newLandMap);
            visited.add(newLandMap);
        }
    }

    public LandMap clone() {
        return new LandMap(Arrays.stream(this.landMap)
                     .map(int[]::clone)
                     .toArray(int[][]::new));
    }

    public int[][] getLandMap() {
        return landMap;
    }

    public void setLandMap(int[][] landMap) {
        this.landMap = landMap;
    }

    public int getBorders() {
        return borders;
    }

    public void setBorders(int borders) {
        this.borders = borders;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
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
