package com.jjginga;

import com.jjginga.Result.SearchResult;
import com.jjginga.SearchTechnique.AbstractSearchTechnique;
import com.jjginga.SearchTechnique.BFS;
import com.jjginga.SearchTechnique.DFS;
import com.jjginga.SearchTechnique.IterativeDDFS;
import com.jjginga.State.IState;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class SearchTaskManager {


    public static void executeSearch(IState state, int objective) {
        AbstractSearchTechnique bfs = new BFS(state, objective);
        executeSearch(bfs);
        AbstractSearchTechnique dfs = new DFS(state, objective);
        executeSearch(dfs);
        AbstractSearchTechnique iddfs = new IterativeDDFS(state, objective);
        executeSearch(iddfs);
    }

    private static void executeSearch(AbstractSearchTechnique searchTechnique) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<SearchResult> future = executor.submit(searchTechnique);

        try {
            SearchResult result = future.get(60, TimeUnit.SECONDS);
            System.out.println(searchTechnique);
            System.out.println(result);
        } catch (TimeoutException e) {
            future.cancel(true);
            SearchResult currentResult = searchTechnique.getNotFoundResult();
            System.err.println(searchTechnique);
            System.err.println(String.format("Search timed out: %s", currentResult));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

}
