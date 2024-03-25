package com.jjginga;

import com.jjginga.state.IState;
import com.jjginga.state.LandMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines and solves the land permutation problem with varying initial states and objectives.
 * Utilizes the SearchTaskManager to execute searches for each problem instance and display the results.
 */
public class LandProblem {

    /**
     * Solves the land permutation problem for predefined instances.
     * Each instance has a specific initial configuration and a target number of borders to achieve.
     */
    public void solve(){
        Map<IState, Integer> landMapObjectives = new HashMap<>();

        landMapObjectives.put(new LandMap(new int[][]{{1,2,3},{1,2,2},{3,3,1}}), 6);
        landMapObjectives.put(new LandMap(new int[][]{{1,2,2,2},{1,2,1,1}}), 4);
        landMapObjectives.put(new LandMap(new int[][]{{1,2,2,2},{1,3,3,3},{1,2,1,1},{1,1,3,2}}), 10);
        landMapObjectives.put(new LandMap(new int[][]{{1,1,2,1,1},{2,2,1,2,1},{1,1,2,1,2},{2,1,1,2,1}}), 10);
        landMapObjectives.put(new LandMap(new int[][]{{1,2,2,2,2,1,2,2,2,2},{1,3,3,3,4,1,3,3,3,4},{1,2,1,4,3,1,2,1,4,3},{1,4,4,4,3,1,4,4,4,3}}), 30);
        landMapObjectives.put(new LandMap(new int[][]{{1,1,2,1,1,1,1,2,1,1},{2,2,1,2,1,2,2,1,2,1},{1,1,2,1,2,1,1,2,1,2},{2,1,1,2,1,2,1,1,2,1},{1,1,2,1,1,1,1,2,1,1},{2,2,1,2,1,2,2,1,2,1},{1,1,2,1,2,1,1,2,1,2},{2,1,1,2,1,2,1,1,2,1}}), 41);
        landMapObjectives.put(new LandMap(new int[][]{{1,1,2,8,8,1,4,3,1,4},{2,2,1,8,3,8,4,3,2,1},{1,1,8,8,3,1,6,2,1,4},{2,1,1,3,1,2,1,1,4,4},{1,7,7,3,1,1,5,6,4,4},{2,2,1,3,1,2,2,1,6,6},{1,7,2,7,5,5,5,5,1,6},{2,7,7,7,1,5,5,1,6,6}}), 70);

        int i = 0;
        for(Map.Entry<IState, Integer> entry : landMapObjectives.entrySet()){
            System.out.printf("Instance %d%n", ++i);
            SearchTaskManager.executeSearch(entry.getKey(), entry.getValue());
        }
    }
}
