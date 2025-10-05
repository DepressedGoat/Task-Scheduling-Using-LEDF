public class Main {
    public static void main(String[] args) {
        LEDF l = new LEDF();

        l.addTasks("Task 1", 0, 4, 900);
        l.addTasks("Task 2", 2, 8, 1800);
        l.addTasks("Task 3", 2.5, 8, 450);
        l.addTasks("Task 4", 5, 20, 1000);
        l.addTasks("Task 5", 6, 14, 800);

        l.addCPUSpeed("Speed 1", 200, 1.5);
        l.addCPUSpeed("Speed 2", 300, 2);
        l.addCPUSpeed("Speed 3", 450, 3.5);

        l.schedule();
    }
}
