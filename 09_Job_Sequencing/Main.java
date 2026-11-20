import java.util.*;

class Job {
    char id;
    int deadline, profit;
    Job(char id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of jobs: ");
        int n = sc.nextInt();
        if (n <= 0) return;

        Job[] jobs = new Job[n];
        int maxDeadline = 0;
        System.out.println("Enter job ID (one character), deadline and profit:");
        for (int i = 0; i < n; i++) {
            char id = sc.next().charAt(0);
            int deadline = sc.nextInt();
            int profit = sc.nextInt();
            jobs[i] = new Job(id, deadline, profit);
            maxDeadline = Math.max(maxDeadline, deadline);
        }

        Arrays.sort(jobs, (a, b) -> Integer.compare(b.profit, a.profit));
        char[] slots = new char[maxDeadline + 1];
        Arrays.fill(slots, '-');
        int totalProfit = 0, jobsDone = 0;

        for (Job job : jobs) {
            for (int time = Math.min(maxDeadline, job.deadline); time >= 1; time--) {
                if (slots[time] == '-') {
                    slots[time] = job.id;
                    totalProfit += job.profit;
                    jobsDone++;
                    break;
                }
            }
        }

        System.out.print("Job schedule: ");
        for (int time = 1; time <= maxDeadline; time++)
            if (slots[time] != '-') System.out.print(slots[time] + " ");

        System.out.println("\nNumber of jobs completed = " + jobsDone);
        System.out.println("Maximum profit = " + totalProfit);
        System.out.println("Time complexity: O(n log n + nD), D = maximum deadline.");
        System.out.println("Space complexity: O(D).");
    }
}
