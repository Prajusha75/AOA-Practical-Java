import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int binarySearch(int[] a, int key) {
        int low = 0, high = a.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] == key) return mid;
            if (a[mid] < key) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();
        if (n <= 0) return;

        int[] a = new int[n];
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();

        Arrays.sort(a);
        System.out.println("Sorted array: " + Arrays.toString(a));
        System.out.print("Enter element to search: ");
        int key = sc.nextInt();

        int index = binarySearch(a, key);
        if (index == -1) System.out.println("Element not found.");
        else System.out.println("Element found at sorted index " + index + ".");

        System.out.println("Binary search time complexity: O(log n).");
        System.out.println("Auxiliary space complexity: O(1).");
    }
}
