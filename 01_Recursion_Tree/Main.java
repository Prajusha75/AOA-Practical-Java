import java.util.Scanner;

public class Main {
    // Demonstrates T(n) = 2T(n/2) + n using a recursion tree.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n (power of 2 recommended): ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Enter a positive value.");
            return;
        }

        int level = 0, nodes = 1, size = n;
        long totalWork = 0;

        System.out.println("\nLevel\tNodes\tSubproblem Size\tWork at Level");
        while (true) {
            long work = (long) nodes * size;
            System.out.println(level + "\t" + nodes + "\t" + size + "\t\t" + work);
            totalWork += work;

            if (size == 1) break;
            nodes *= 2;
            size /= 2;
            level++;
        }

        System.out.println("\nTotal work = " + totalWork);
        System.out.println("Height of tree = " + level);
        System.out.println("For T(n) = 2T(n/2) + n, time complexity = O(n log n).");
        System.out.println("Recursion stack space = O(log n).");
    }
}
