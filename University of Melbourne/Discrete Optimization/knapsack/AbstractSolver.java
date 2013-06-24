import java.io.PrintWriter;

public abstract class AbstractSolver implements Runnable {
    int K;
    Item []items;
    public AbstractSolver(int []cost, int []weight, int K) {
        this.K = K;
        this.items = new Item[cost.length];
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i] = new Item(cost[i], weight[i]);
        }
    }

    public abstract int getResult();
    public abstract void printResult(PrintWriter output) throws Exception;
}
