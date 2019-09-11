//package UtilityFiles;
//
//import java.util.*;
//public class DFS {
//    // Array for keeping track of the elements, that have been visited by the dfs.
//    private boolean[] marked;
//    // Array for keeping track of the intermediate results.
//    private int[] cache;
//    // Defensive copy of the input matrix.
//    private int[][] matrix;
//    // The value of the maximum increasing sequence.
//    private int max;
//    // Height and width of the input matrix.
//    private int height, width;
//
//    public int longestIncreasingPath(int[][] matrix) {
//        // Initialization.
//        if (matrix.length == 0 || matrix[0].length == 0) return 0;
//        this.height = matrix.length;
//        this.width = matrix[0].length;
//        this.matrix = matrix;
//        // Catch, 2D -> 1D: row * width + col; 1D -> 2D: x = 1D / width, y = 1D % width.
//        this.marked = new boolean[width * height];
//        this.cache = new int[height * width];
//        this.max = Integer.MIN_VALUE;
//
//        for (int i = 0; i < width * height; i++) {
//            // Run depth-first search on every element in the matrix.
//            int len = dfs(i);
//            max = Math.max(max, len);
//        }
//        return max;
//    }
//
//    private int dfs(int i) {
//        // If we've already run dfs on the current vertex, just return the result.
//        if (cache[i] > 0) return cache[i];
//        // Mark the vertex as visited.
//        marked[i] = true;
//        int max = 1;
//        // Recursively visit all neighbors that we haven't seen so far.
//        for (int n : getNeighbors(i)) {
//            if (!marked[n]) {
//                int len = 1 + dfs(n);
//                max = Math.max(max, len);
//            }
//        }
//        // Save the length of the longest increasing sequence for the current element for future use by the dfs().
//        cache[i] = max;
//        // Clean up the dfs() path.
//        marked[i] = false;
//        return max;
//    }
//
//    // Return all neighbors of a particular element in the matrix.
//    private List<Integer> getNeighbors(int i) {
//        List<Integer> neighbors = new ArrayList<>();
//        // Convert 1D to 2D.
//        int x = i / width, y = i % width;
//        for (int j = -1; j <= 1; j++) {
//            if (j == 0) continue;
//            if (isInTheGrid(x + j, y) && matrix[x + j][y] > matrix[x][y]) neighbors.add((x + j) * width + y);
//            if (isInTheGrid(x, y + j) && matrix[x][y + j] > matrix[x][y]) neighbors.add(x * width + y + j);
//        }
//        return neighbors;
//    }
//
//    // Check whether indices are in the matrix.
//    private boolean isInTheGrid(int x, int y) {
//        return (x >= 0 && x < height && y >= 0 && y < width);
//    }
//
//    public static void main(String[] args) {
//        DFS test = new DFS();
//
//
//        int [][] x = new int[][]{
//                {0, 3, 20, 0},
//                {20, 20, 20, 40},
//                {5, 20, 60, 10},
//                {20, 0, 20, 10}
//        };
//
//        int p = test.longestIncreasingPath(x);
//        System.out.println(test.getNeighbors(p));
//
////        int y = test.longestIncreasingPath(x);
////        System.out.println(test.dfs(y));
////        System.out.println(Arrays.deepToString(test.cache));
//    }
//}
