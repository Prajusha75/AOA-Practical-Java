import java.util.Scanner;

public class Main {
    // Analyses T(n) = aT(n/b) + n^k.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("For T(n) = aT(n/b) + n^k");
        System.out.print("Enter a (a >= 1): ");
        double a = sc.nextDouble();
        System.out.print("Enter b (b > 1): ");
        double b = sc.nextDouble();
        System.out.print("Enter k (power of n): ");
        double k = sc.nextDouble();

        if (a < 1 || b <= 1) {
            System.out.println("Invalid input. Use a >= 1 and b > 1.");
            return;
        }

        double p = Math.log(a) / Math.log(b);
        System.out.printf("%nlog_b(a) = %.4f%n", p);

        double eps = 1e-9;
        if (p > k + eps) {
            System.out.println("Case 1: n^log_b(a) grows faster.");
            System.out.printf("T(n) = Theta(n^%.4f)%n", p);
        } else if (Math.abs(p - k) <= eps) {
            System.out.println("Case 2: Both terms have the same polynomial order.");
            System.out.printf("T(n) = Theta(n^%.4f log n)%n", k);
        } else {
            System.out.println("Case 3: n^k grows faster (regularity condition assumed).");
            System.out.printf("T(n) = Theta(n^%.4f)%n", k);
        }
    }
}
