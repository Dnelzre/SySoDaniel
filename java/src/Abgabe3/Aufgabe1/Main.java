package Abgabe3.Aufgabe1;

public class Main {
    public static void main(String[] args) throws Exception {

        ResourceManager rm = new ResourceManager(2);
        rm.addElf();
        rm.addElf();
        rm.addElf();
        rm.addPacker();
        rm.addDecorator();
        rm.addDecorator();
        rm.addDecorator();

        StatistikReporter rep = new StatistikReporter(rm.getStatistik());
        rep.start();

        Thread.sleep(2000);

        rm.changeBakingDevices(3);

        Thread.sleep(2000);

        rm.addElf();

        rm.addDecorator();
    }
}
