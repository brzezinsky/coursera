import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: brzezinsky
 * Date: 6/25/13
 * Time: 1:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class DPSolver extends AbstractSolver {

    int [][]dp;

    private int result;
    private int []taken;

    public DPSolver(int[] cost, int[] weight, int K) {
        super(cost, weight, K);
        if ((cost.length + 1)  * (K + 1) <= 100000000)
					this.dp = new int[cost.length + 1][K + 1];

				result = 0;
        taken = new int[cost.length];
    }

    @Override
    public int getResult() {
        return result;
    }

    @Override
    public void printResult(PrintWriter output) throws Exception {
        int at = -1;
        int n = items.length;
        for (int i = 0; i <= K; ++i) {
            if (dp[n][i] == result) {
                at = i;
                break;
            }
        }
        for (int i = n - 1; i >= 0; --i) {
            for (int j = 0; j <= K; ++j) {
                if (j == at && dp[i][j] == dp[i + 1][at]) {
                    taken[i] = 0;
                    at = j;
                    break;
                } else if (dp[i][j] + items[i].cost == dp[i + 1][at] && j + items[i].weight == at) {
                    taken[i] = 1;
                    at = j;
                    break;
                }
            }
        }

        output.println(result + " 1");
        for (int t : taken) output.print(t + " ");
        System.out.println("");
    }

    @Override
    public void run() {
        if (K * this.items.length > (int)1e8) return;
        for (int t[]: dp) Arrays.fill(t, Integer.MIN_VALUE);
        dp[0][0] = 0;
        for (int i = 0; i < items.length; ++i) {
            for (int cur = 0; cur  <= K; ++cur) {
                if (dp[i][cur] == Integer.MIN_VALUE) continue;
                dp[i + 1][cur] = Math.max(dp[i + 1][cur], dp[i][cur]);
                int weight = cur + items[i].weight;
                if (weight <= K) {
                    dp[i + 1][weight] = Math.max(dp[i + 1][weight], dp[i][cur] + items[i].cost);
                }
            }
        }
        for (int t : dp[items.length]) result = Math.max(result, t);
    }
}

