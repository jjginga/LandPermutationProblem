package com.jjginga;

import com.jjginga.result.SearchResult;
import com.jjginga.searchtechnique.AbstractSearchTechnique;
import com.jjginga.searchtechnique.BFS;
import com.jjginga.searchtechnique.DFS;
import com.jjginga.searchtechnique.IterativeDDFS;
import com.jjginga.state.IState;

import java.util.concurrent.*;

/**
 * Manages the execution of search tasks using different search techniques.
 * This class initializes and executes searches with BFS, DFS, and IDDFS algorithms,
 * handling the scheduling and result processing in a concurrent environment.
 */
public class SearchTaskManager {


    /**
     * Executes search operations for a given state and objective using BFS, DFS, and IDDFS techniques.
     * Each search is performed sequentially with a timeout constraint.
     *
     * @param state The initial state for the search.
     * @param objective The objective to be achieved by the search, typically a target number of borders.
     */
    public static void executeSearch(IState state, int objective) {
        AbstractSearchTechnique bfs = new BFS(state, objective);
        executeSearch(bfs);
        AbstractSearchTechnique dfs = new DFS(state, objective);
        executeSearch(dfs);
        AbstractSearchTechnique iddfs = new IterativeDDFS(state, objective);
        executeSearch(iddfs);
    }

    /**
     * Executes a single search operation using the provided search technique.
     * Manages the execution in a separate thread with a fixed timeout, handling search completion and timeouts.
     *
     * @param searchTechnique The search technique to be executed.
     */
    private static void executeSearch(AbstractSearchTechnique searchTechnique) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<SearchResult> future = executor.submit(searchTechnique);

        try {
            //wait for the search to complete or timeout
            SearchResult result = future.get(60, TimeUnit.SECONDS);
            System.out.println(searchTechnique);
            System.out.println(result);
        } catch (TimeoutException e) {
            //handle search timeout atempting to retrive the last known result
            future.cancel(true);
            searchTechnique.awaitCompletion();
            SearchResult currentResult = searchTechnique.getNotFoundResult();
            System.err.println(searchTechnique);
            System.err.printf("Search timed out: %s%n", currentResult);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

}
