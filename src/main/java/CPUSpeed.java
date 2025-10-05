public class CPUSpeed {
    public final String name;
    public final int mips;
    public final double voltage;

    public CPUSpeed(String name, int Mips, double Voltage) {
        this.name = name;
        this.mips = Mips;
        this.voltage = Voltage;
    }

    public CPUSpeed() {
        this.name = "Default";
        this.mips = 0;
        this.voltage = 0;
    }
}
