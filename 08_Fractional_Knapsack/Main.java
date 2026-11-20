import java.util.*;

class Item {
    double value, weight;
    Item(double value, double weight) {
        this.value = value;
        this.weight = weight;
    }
    double ratio() { return value / weight; }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        if (n <= 0) return;

        Item[] items = new Item[n];
        System.out.println("Enter value and weight for each item:");
        for (int i = 0; i < n; i++) {
            double value = sc.nextDouble();
            double weight = sc.nextDouble();
            if (weight <= 0) {
                System.out.println("Weight must be positive.");
                return;
            }
            items[i] = new Item(value, weight);
        }

        System.out.print("Enter knapsack capacity: ");
        double capacity = sc.nextDouble();
        if (capacity < 0) return;

        Arrays.sort(items, (x, y) -> Double.compare(y.ratio(), x.ratio()));
        double totalValue = 0;

        System.out.println("\nSelected fractions:");
        for (Item item : items) {
            if (capacity <= 0) break;
            double fraction = Math.min(1.0, capacity / item.weight);
            totalValue += fraction * item.value;
            capacity -= fraction * item.weight;
            System.out.printf("Value %.2f, fraction taken %.2f%n", item.value, fraction);
        }

        System.out.printf("Maximum total value = %.2f%n", totalValue);
        System.out.println("Time complexity: O(n log n) due to sorting.");
        System.out.println("Space complexity: O(n) for the item array.");
    }
}
