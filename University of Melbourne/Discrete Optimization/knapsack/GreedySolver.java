import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class GreedySolver extends AbstractSolver {

    private int[] taken;
    int weight = 0;
    int value = 0;

    public GreedySolver(int[] cost, int[] weight, int K) {
        super(cost, weight, K);
        for (int i = 0; i < cost.length; ++i) {
            this.items[i].ID = i;
        }
        this.taken = new int[this.items.length];

    }

    @Override
    public int getResult() {
        return value;
    }

    @Override
    public void printResult(PrintWriter output) throws Exception {
        output.println(value + " 0");
        for (int t : taken) output.print(t + " ");
        System.out.println("");
    }

    @Override
    public void run() {
        Arrays.sort(this.items, new Comparator<Item>() {
            public int compare(Item a, Item b) {
                double here = (double) a.cost / a.weight;
                double there = (double) b.cost / b.weight;
                if (here > there) return -1;
                if (here < there) return 1;
                return 0;
            }
        });

        for (int i = 0; i < items.length; i++) {
            if (weight + items[i].weight <= K) {
                taken[items[i].ID] = 1;
                value += items[i].cost;
                weight += items[i].weight;
            } else {
                taken[items[i].ID] = 0;
            }
        }

    }
}
