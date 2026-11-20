
import java.util.*;

class BTreeNode {
    int[] keys;
    BTreeNode[] child;
    int n, t;
    boolean leaf;

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new int[2 * t - 1];
        child = new BTreeNode[2 * t];
        n = 0;
    }

    boolean search(int x) {
        int i = 0;
        while (i < n && x > keys[i]) i++;

        if (i < n && keys[i] == x) return true;
        return !leaf && child[i].search(x);
    }

    void traverse(List<Integer> list) {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf) child[i].traverse(list);
            list.add(keys[i]);
        }
        if (!leaf) child[i].traverse(list);
    }

    void insertNonFull(int x) {
        int i = n - 1;

        if (leaf) {
            while (i >= 0 && keys[i] > x) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = x;
            n++;
        } else {
            while (i >= 0 && keys[i] > x) i--;
            i++;

            if (child[i].n == 2 * t - 1) {
                splitChild(i, child[i]);
                if (x > keys[i]) i++;
            }
            child[i].insertNonFull(x);
        }
    }

    void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++)
            z.keys[j] = y.keys[j + t];

        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.child[j] = y.child[j + t];
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--)
            child[j + 1] = child[j];

        child[i + 1] = z;

        for (int j = n - 1; j >= i; j--)
            keys[j + 1] = keys[j];

        keys[i] = y.keys[t - 1];
        n++;
    }
}

class BTree {
    BTreeNode root;
    int t;

    BTree(int t) {
        this.t = t;
    }

    boolean search(int x) {
        return root != null && root.search(x);
    }

    void insert(int x) {
        if (search(x)) {
            System.out.println("Key already exists");
            return;
        }

        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = x;
            root.n = 1;
        } else if (root.n == 2 * t - 1) {
            BTreeNode s = new BTreeNode(t, false);
            s.child[0] = root;
            s.splitChild(0, root);

            int i = x > s.keys[0] ? 1 : 0;
            s.child[i].insertNonFull(x);
            root = s;
        } else {
            root.insertNonFull(x);
        }

        System.out.println("Inserted");
    }

    void insertWithoutMessage(int x) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = x;
            root.n = 1;
        } else if (root.n == 2 * t - 1) {
            BTreeNode s = new BTreeNode(t, false);
            s.child[0] = root;
            s.splitChild(0, root);

            int i = x > s.keys[0] ? 1 : 0;
            s.child[i].insertNonFull(x);
            root = s;
        } else {
            root.insertNonFull(x);
        }
    }

    void delete(int x) {
        if (!search(x)) {
            System.out.println("Key not found");
            return;
        }

        List<Integer> list = new ArrayList<>();
        root.traverse(list);

        list.remove(Integer.valueOf(x));
        root = null;

        for (int value : list)
            insertWithoutMessage(value);

        System.out.println("Deleted");
    }

    void display() {
        List<Integer> list = new ArrayList<>();

        if (root != null)
            root.traverse(list);

        System.out.println(list);
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter minimum degree (t >= 2): ");
        int t = sc.nextInt();

        if (t < 2) {
            System.out.println("Invalid degree");
            return;
        }

        BTree tree = new BTree(t);

        while (true) {
            System.out.println("\n1.Insert  2.Search  3.Delete  4.Display  5.Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter key: ");
                    tree.insert(sc.nextInt());
                    break;

                case 2:
                    System.out.print("Enter key: ");
                    System.out.println(
                        tree.search(sc.nextInt()) ? "Found" : "Not Found"
                    );
                    break;

                case 3:
                    System.out.print("Enter key: ");
                    tree.delete(sc.nextInt());
                    break;

                case 4:
                    tree.display();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
