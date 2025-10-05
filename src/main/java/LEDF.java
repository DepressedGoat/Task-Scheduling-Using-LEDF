import java.util.*;

public class LEDF {
    private List<CPUSpeed> S;
    private List<Task> T;

    public LEDF() {
        S = new ArrayList<>();
        T = new ArrayList<>();
    }

    public void addCPUSpeed(String Name, int Mips, double Voltage) {
        S.add(new CPUSpeed(Name, Mips, Voltage));
    }

    public void addTasks(String Name, double Arrival, double Deadline, double Length) {
        T.add(new Task(Name, Arrival, Deadline, Length));
    }

    public void schedule() {
        double tc = 0.0;
        double totalEnergy = 0.0;

        S.sort(Comparator.comparingInt(S -> S.mips));

        while (true) {
            List<Task> ready = new ArrayList<>();

            for (Task t : T) {
                if (!t.completed && t.arrival <= tc) {
                    ready.add(t);
                }
            }

            if (ready.isEmpty()) {
                // jump to next arrival
                final double time = tc;
                OptionalDouble nextArr = T.stream()
                        .filter(t -> !t.completed && t.arrival > time)
                        .mapToDouble(t -> t.arrival).min();
                if (nextArr.isEmpty()) break; // all done
                tc = nextArr.getAsDouble();
                continue;
            }

            ready.sort(Comparator.comparingDouble(task -> task.deadline));
            Task active = ready.getFirst();

            CPUSpeed selected = calculateRequiredMips(ready, tc);

            double next = 999999;
            for (Task t : T) {
                if (!t.completed && t.arrival > tc) {
                    next = Math.min(next, t.arrival);
                }
            }

            double finish = tc + active.remaining / selected.mips;
            next = Math.min(finish, next);
            double miDone = (next - tc) * selected.mips;
            active.remaining -= miDone;
            double energy = Math.pow(selected.voltage, 2) * miDone;
            totalEnergy += energy;

            //printResults(tc, next, active, selected);
            System.out.printf("t=%.2f–%.2f: run %s at %d MIPS (V=%.1f)%n", tc, next, active.name, selected.mips, selected.voltage);

            tc = next;

            if (active.remaining <= 1e-6) {
                active.completed = true;
            }
        }
        System.out.printf("%nCalculations completed. The minimum energy required is = %.2f%n", totalEnergy);
    }

    private CPUSpeed calculateRequiredMips(List<Task> ready, double tc) {
        double miLeft = 0;
        double minMips = 0;
        for (Task t : ready) {
                /*
                if (!((t.remaining / S.getFirst().mips) < t.deadline - active.deadline)) {
                    minMips += t.remaining / (t.deadline - tc);
                }*/
            miLeft += t.remaining;

            double neededMips = miLeft / (t.deadline - tc);
            minMips = Math.max(minMips, neededMips);
        }

        CPUSpeed selected = S.getLast();
        for (CPUSpeed s : S) {
            if (s.mips >= minMips) {
                selected = s;
                break;
            }
        }

        return selected;
    }

    private void printResults(double tc, double next, Task active, CPUSpeed selected) {
        double tmp;

        System.out.printf("t=%.2f–%.2f: run %s at %d MIPS (V=%.1f)%n", tc, next, active.name, selected.mips, selected.voltage);
    }
}
