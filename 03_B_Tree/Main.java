import java.util.Scanner;

class BTreeNode {
    int[] keys;
    BTreeNode[] child;
    int count;
    boolean leaf;
    int t;

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new int[2 * t - 1];
        child = new BTreeNode[2 * t];
        count = 0;
    }

    void traverse() {
        int i;
        for (i = 0; i < count; i++) {
            if (!leaf) child[i].traverse();
            System.out.print(keys[i] + " ");
        }
        if (!leaf) child[i].traverse();
    }

    void insertNonFull(int key) {
        int i = count - 1;
        if (leaf) {
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            count++;
        } else {
            while (i >= 0 && keys[i] > key) i--;
            i++;
            if (child[i].count == 2 * t - 1) {
                splitChild(i, child[i]);
                if (key > keys[i]) i++;
            }
            child[i].insertNonFull(key);
        }
    }

    void splitChild(int index, BTreeNode full) {
        BTreeNode right = new BTreeNode(t, full.leaf);
        right.count = t - 1;

        for (int j = 0; j < t - 1; j++)
            right.keys[j] = full.keys[j + t];

        if (!full.leaf) {
            for (int j = 0; j < t; j++)
                right.child[j] = full.child[j + t];
        }

        full.count = t - 1;

        for (int j = count; j >= index + 1; j--)
            child[j + 1] = child[j];
        child[index + 1] = right;

        for (int j = count - 1; j >= index; j--)
            keys[j + 1] = keys[j];
        keys[index] = full.keys[t - 1];
        count++;
    }
}

class BTree {
    private BTreeNode root;
    private final int t;

    BTree(int t) { this.t = t; }

    void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.count = 1;
            return;
        }

        if (root.count == 2 * t - 1) {
            BTreeNode newRoot = new BTreeNode(t, false);
            newRoot.child[0] = root;
            newRoot.splitChild(0, root);
            int i = (key > newRoot.keys[0]) ? 1 : 0;
            newRoot.child[i].insertNonFull(key);
            root = newRoot;
        } else {
            root.insertNonFull(key);
        }
    }

    void display() {
        if (root != null) root.traverse();
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter minimum degree t (t >= 2): ");
        int t = sc.nextInt();
        if (t < 2) {
            System.out.println("Minimum degree must be at least 2.");
            return;
        }

        BTree tree = new BTree(t);
        System.out.print("How many keys? ");
        int n = sc.nextInt();
        System.out.println("Enter keys:");
        for (int i = 0; i < n; i++) tree.insert(sc.nextInt());

        System.out.print("B-Tree traversal (sorted): ");
        tree.display();
        System.out.println("Insertion time: O(log n) for fixed degree.");
    }
}
