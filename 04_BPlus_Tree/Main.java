
import java.util.*;

class BPlusNode {
    boolean leaf;
    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<BPlusNode> children = new ArrayList<>();
    BPlusNode next;

    BPlusNode(boolean leaf) {
        this.leaf = leaf;
    }
}

class BPlusTree {
    BPlusNode root = new BPlusNode(true);
    int order;

    BPlusTree(int order) {
        this.order = order;
    }

    class Split {
        int key;
        BPlusNode right;

        Split(int key, BPlusNode right) {
            this.key = key;
            this.right = right;
        }
    }

    boolean search(int x) {
        BPlusNode p = root;

        while (!p.leaf) {
            int i = 0;

            while (i < p.keys.size() && x >= p.keys.get(i))
                i++;

            p = p.children.get(i);
        }

        return Collections.binarySearch(p.keys, x) >= 0;
    }

    void insert(int x) {
        if (search(x)) {
            System.out.println("Key already exists");
            return;
        }

        Split s = insertRecursive(root, x);

        if (s != null) {
            BPlusNode newRoot = new BPlusNode(false);

            newRoot.keys.add(s.key);
            newRoot.children.add(root);
            newRoot.children.add(s.right);

            root = newRoot;
        }

        System.out.println("Inserted");
    }

    Split insertRecursive(BPlusNode p, int x) {

        if (p.leaf) {
            int pos = Collections.binarySearch(p.keys, x);
            p.keys.add(-pos - 1, x);

            if (p.keys.size() < order)
                return null;

            int mid = p.keys.size() / 2;

            BPlusNode right = new BPlusNode(true);

            right.keys.addAll(p.keys.subList(mid, p.keys.size()));
            p.keys.subList(mid, p.keys.size()).clear();

            right.next = p.next;
            p.next = right;

            return new Split(right.keys.get(0), right);
        }

        int i = 0;

        while (i < p.keys.size() && x >= p.keys.get(i))
            i++;

        Split s = insertRecursive(p.children.get(i), x);

        if (s == null)
            return null;

        p.keys.add(i, s.key);
        p.children.add(i + 1, s.right);

        if (p.children.size() <= order)
            return null;

        int mid = p.keys.size() / 2;
        int promoted = p.keys.get(mid);

        BPlusNode right = new BPlusNode(false);

        right.keys.addAll(p.keys.subList(mid + 1, p.keys.size()));
        right.children.addAll(
            p.children.subList(mid + 1, p.children.size())
        );

        p.keys.subList(mid, p.keys.size()).clear();
        p.children.subList(mid + 1, p.children.size()).clear();

        return new Split(promoted, right);
    }

    BPlusNode leftmostLeaf() {
        BPlusNode p = root;

        while (!p.leaf)
            p = p.children.get(0);

        return p;
    }

    void delete(int x) {
        if (!search(x)) {
            System.out.println("Key not found");
            return;
        }

        ArrayList<Integer> allKeys = new ArrayList<>();

        BPlusNode p = leftmostLeaf();

        while (p != null) {
            allKeys.addAll(p.keys);
            p = p.next;
        }

        allKeys.remove(Integer.valueOf(x));

        root = new BPlusNode(true);

        for (int value : allKeys) {
            Split s = insertRecursive(root, value);

            if (s != null) {
                BPlusNode newRoot = new BPlusNode(false);

                newRoot.keys.add(s.key);
                newRoot.children.add(root);
                newRoot.children.add(s.right);

                root = newRoot;
            }
        }

        System.out.println("Deleted");
    }

    void display() {
        ArrayList<Integer> allKeys = new ArrayList<>();

        BPlusNode p = leftmostLeaf();

        while (p != null) {
            allKeys.addAll(p.keys);
            p = p.next;
        }

        System.out.println(allKeys);
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter order of B+ Tree (>= 3): ");
        int order = sc.nextInt();

        if (order < 3) {
            System.out.println("Invalid order");
            return;
        }

        BPlusTree tree = new BPlusTree(order);

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
