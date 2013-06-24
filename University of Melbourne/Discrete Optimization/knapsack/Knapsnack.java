/**
 * Created with IntelliJ IDEA.
 * User: brzezinsky
 * Date: 6/25/13
 * Time: 12:26 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.util.BitSet;
import java.util.StringTokenizer;

public class Knapsnack extends AbstractSolver {

    private Node root;
    private static long startTime;

    public Knapsnack(int []costs, int []weights, int K) {
        super(costs, weights, K);
        int totalCost = 0;
        for (int i = 0; i < this.items.length; ++i) {
            totalCost += costs[i];
        }
        this.root = new Node(0, K, totalCost, 0);
        this.root.taken = new BitSet(items.length);
        this.startTime = System.currentTimeMillis();
    }

    static class Node {
        int value;
        int room;
        int estimate;
        int at;
        BitSet taken;
        static int best = 0;
        static BitSet bestTaken = null;

        Node(int value, int room, int estimate, int at) {
            this.value = value;
            this.room = room;
            this.estimate = estimate;
            this.at = at;
        }

        public void branchAndBound(Item[] items) throws Exception{
            if (this.room < 0) return;
            if (System.currentTimeMillis() - Knapsnack.startTime >= 1000 * 30) throw new Exception();
            if (this.estimate <= this.best && this.at < items.length) return;
            this.best = Math.max(best, this.value);
            if (at == items.length) {
                if (this.value == this.best) {
                    this.bestTaken = (BitSet) this.taken.clone();
                }
            } else {
                Node left = new Node(this.value + items[at].cost, this.room - items[at].weight, this.estimate, at + 1);
                left.taken = (BitSet) this.taken.clone();
                left.taken.set(at);

                Node right = new Node(this.value, this.room, this.estimate - items[at].cost, at + 1);
                right.taken = (BitSet) this.taken.clone();

                left.branchAndBound(items);
                right.branchAndBound(items);
            }
        }
    }

    @Override
    public void run() {
				try {
        	this.root.branchAndBound(this.items);
				} catch(Exception e) {}
    }

    public int getResult() {
        return this.root.best;
    }

    public void printResult(PrintWriter output) throws Exception {
        output.println(root.best + " 1");
        for (int i = 0; i < this.items.length; ++i) {
            if (root.bestTaken.get(i)) output.print("1 ");
            else output.print("0 ");
        }
        output.println();
    }
}
