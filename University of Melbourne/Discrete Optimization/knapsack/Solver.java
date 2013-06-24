/**
 * Created with IntelliJ IDEA.
 * User: brzezinsky
 * Date: 6/24/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.util.BitSet;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solver extends Thread {
    public Solver(String inputFileName, String outputFileName) {
        try {
            if (inputFileName != null) {
                this.input = new BufferedReader(new FileReader(inputFileName));
            } else {
                this.input = new BufferedReader(new InputStreamReader(System.in));
            }
            if (outputFileName != null) {
                this.output = new PrintWriter(outputFileName);
            } else {
                this.output = new PrintWriter(System.out);
            }
            this.setPriority(Thread.MAX_PRIORITY);
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(666);
        }
    }

    private void solve() throws Throwable {
        int n = nextInt();
        int K = nextInt();
        int []cost = new int[n];
        int []weight = new int[n];
        for (int i = 0; i < n; ++i) {
            cost[i] = nextInt();
            weight[i] = nextInt();
        }


        final int SOLVER_COUNT = 3;

        ExecutorService executor = Executors.newFixedThreadPool(4);
        AbstractSolver []solvers = new AbstractSolver[SOLVER_COUNT];


        solvers[0] = new Knapsnack(cost, weight, K);
        solvers[1] = new GreedySolver(cost, weight, K);
        solvers[2] = new DPSolver(cost, weight, K);

        Future []res = new Future[SOLVER_COUNT];
        for (int i = 0; i < SOLVER_COUNT; ++i) {
            res[i] = executor.submit(new Thread(null, solvers[i], "", 1024 * 1024 * 1024));
        }
        for (int i = 0; i < SOLVER_COUNT; ++i) {
            try {
								System.gc();
                res[i].get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        int at = 0;
        for (int i = 0; i < SOLVER_COUNT; ++i) {
            if (solvers[i].getResult() > solvers[at].getResult()) at = i;
        }
        solvers[at].printResult(output);
        executor.shutdown();

    }

    public void run() {
        try {
            solve();
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            System.err.println(e.toString());
            e.printStackTrace();
            System.exit(666);
        } finally {
            output.close();
        }
    }

    public static void main(String... args) {
        String fileName = null;

        // get the temp file name
        for (String arg : args) {
            if (arg.startsWith("-file=")) {
                fileName = arg.substring(6);
            }
        }
        if (fileName == null)
            new Solver(null, null).start();
        else new Solver(fileName, null).start();
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    private double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    private long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    private String next() throws IOException {
        while (tokens == null || !tokens.hasMoreTokens()) {
            tokens = new StringTokenizer(input.readLine());
        }
        return tokens.nextToken();
    }

    private StringTokenizer tokens;
    private BufferedReader input;
    private PrintWriter output;
}
