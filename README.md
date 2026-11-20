# AOA Practical Programs - Java

This repository contains Java implementations of all 11 experiments in the AOA practical list.

## Experiments
1. Recurrence Relations using Recursion Tree (`T(n) = 2T(n/2) + n`)
2. Recurrence Relations using Master Method (`T(n) = aT(n/b) + n^k`)
3. B-Tree
4. B+ Tree
5. Binary Search
6. Merge Sort
7. Quick Sort
8. Fractional Knapsack using Greedy Algorithm
9. Job Sequencing with Deadlines using Greedy Algorithm
10. 0/1 Knapsack using Dynamic Programming
11. Longest Common Subsequence (LCS) using Dynamic Programming

## Requirements
- JDK installed (Java 17 or later recommended)
- Visual Studio Code
- VS Code Extension Pack for Java (Microsoft)

Check Java installation in the VS Code terminal:

```powershell
java -version
javac -version
```

## Run an experiment in VS Code (Windows PowerShell)

1. Extract this ZIP file.
2. Open VS Code.
3. Choose **File > Open Folder** and open `AOA-Practical-Java`.
4. Open the experiment folder you want.
5. Open `Main.java`.
6. Open the terminal using **Terminal > New Terminal**.
7. Compile and run from the repository root. Example for Merge Sort:

```powershell
javac .\06_Merge_Sort\Main.java
java -cp .\06_Merge_Sort Main
```

Use the corresponding folder for another experiment. For example, Binary Search:

```powershell
javac .\05_Binary_Search\Main.java
java -cp .\05_Binary_Search Main
```

Each experiment is in a separate folder, so each can use the class name `Main`.

## GitHub repository setup

Open PowerShell in the `AOA-Practical-Java` root folder:

```powershell
git init
git add .
git commit -m "Add all 11 AOA practical programs in Java"
git branch -M main
```

Create a new empty repository on GitHub named `AOA-Practical-Java`, then connect it (replace the URL with your own GitHub username):

```powershell
git remote add origin https://github.com/YOUR-USERNAME/AOA-Practical-Java.git
git push -u origin main
```

## Notes
- Binary Search sorts the input array first, then searches it.
- The Recursion Tree example is specifically for `T(n) = 2T(n/2) + n`.
- The Master Method program compares `log_b(a)` with `k`; Case 3 assumes the regularity condition.
- B-Tree uses minimum degree `t >= 2`.
- B+ Tree uses `order` as the maximum number of children in an internal node. Duplicate keys are ignored.
- Job Sequencing assumes each job takes one unit of time and uses a single-character job ID.
