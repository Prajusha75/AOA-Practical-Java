import java.util.*;

class BPlusNode {
    boolean leaf;
    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<BPlusNode> children = new ArrayList<>();
    BPlusNode next;

    BPlusNode(boolean leaf) { this.leaf = leaf; }
}

class BPlusTree {
    private BPlusNode root = new BPlusNode(true);
    private final int order; // maximum number of children in an internal node

    BPlusTree(int order) { this.order = order; }

    private static class Split {
        int separator;
        BPlusNode right;
        Split(int separator, BPlusNode right) {
            this.separator = separator;
            this.right = right;
        }
    }

    void insert(int key) {
        Split split = insertRecursive(root, key);
        if (split != null) {
            BPlusNode newRoot = new BPlusNode(false);
            newRoot.keys.add(split.separator);
            newRoot.children.add(root);
            newRoot.children.add(split.right);
            root = newRoot;
        }
    }

    private Split insertRecursive(BPlusNode node, int key) {
        if (node.leaf) {
            int pos = Collections.binarySearch(node.keys, key);
            if (pos >= 0) return null; // ignore duplicate keys
            node.keys.add(-pos - 1, key);

            if (node.keys.size() < order) return null;

            int mid = node.keys.size() / 2;
            BPlusNode right = new BPlusNode(true);
            right.keys.addAll(node.keys.subList(mid, node.keys.size()));
            node.keys.subList(mid, node.keys.size()).clear();
            right.next = node.next;
            node.next = right;
            return new Split(right.keys.get(0), right);
        }

        int index = 0;
        while (index < node.keys.size() && key >= node.keys.get(index)) index++;
        Split childSplit = insertRecursive(node.children.get(index), key);
        if (childSplit == null) return null;

        node.keys.add(index, childSplit.separator);
        node.children.add(index + 1, childSplit.right);

        if (node.children.size() <= order) return null;

        int mid = node.keys.size() / 2;
        int promoted = node.keys.get(mid);
        BPlusNode right = new BPlusNode(false);

        right.keys.addAll(node.keys.subList(mid + 1, node.keys.size()));
        right.children.addAll(node.children.subList(mid + 1, node.children.size()));

        node.keys.subList(mid, node.keys.size()).clear();
        node.children.subList(mid + 1, node.children.size()).clear();
        return new Split(promoted, right);
    }

    void displayLeaves() {
        BPlusNode current = root;
        while (!current.leaf) current = current.children.get(0);

        System.out.print("Leaf level (linked order): ");
        while (current != null) {
            System.out.print(current.keys + " ");
            current = current.next;
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter order (maximum children, >= 3): ");
        int order = sc.nextInt();
        if (order < 3) {
            System.out.println("Order must be at least 3.");
            return;
        }

        BPlusTree tree = new BPlusTree(order);
        System.out.print("How many keys? ");
        int n = sc.nextInt();
        System.out.println("Enter keys:");
        for (int i = 0; i < n; i++) tree.insert(sc.nextInt());

        tree.displayLeaves();
        System.out.println("All actual data keys are stored in linked leaf nodes.");
    }
}
