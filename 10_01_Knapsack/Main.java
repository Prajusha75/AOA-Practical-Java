import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        if (n <= 0) return;

        int[] weight = new int[n];
        int[] value = new int[n];
        System.out.println("Enter weight and value of each item:");
        for (int i = 0; i < n; i++) {
            weight[i] = sc.nextInt();
            value[i] = sc.nextInt();
        }

        System.out.print("Enter knapsack capacity: ");
        int capacity = sc.nextInt();
        if (capacity < 0) return;

        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                if (weight[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w],
                        value[i - 1] + dp[i - 1][w - weight[i - 1]]);
                }
            }
        }

        System.out.println("Maximum value = " + dp[n][capacity]);
        System.out.print("Selected item weights: ");
        int w = capacity;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                System.out.print(weight[i - 1] + " ");
                w -= weight[i - 1];
            }
        }
        System.out.println();
        System.out.println("Time complexity: O(n * capacity).");
        System.out.println("Space complexity: O(n * capacity).");
    }
}
