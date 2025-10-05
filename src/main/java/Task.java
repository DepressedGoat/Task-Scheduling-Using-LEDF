public class Task {
    public final String name;
    public final double arrival, deadline, length;
    public double remaining;
    public boolean completed = false;

    public Task(String name, double arrival, double deadline, double length) {
        this.name = name;
        this.arrival = arrival;
        this.deadline = deadline;
        this.length = length;
        this.remaining = length;
    }
}
