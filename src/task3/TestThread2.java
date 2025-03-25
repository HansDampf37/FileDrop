package task3;

public class TestThread2 {
    public static void main(String[] args) {
        IntThread t1 = new IntThread(1);
        IntThread t2 = new IntThread(2);
        IntThread t3 = new IntThread(3);
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY + 1);
        t3.setPriority(Thread.MIN_PRIORITY + 2);
        t1.start();
        t2.start();
        t3.start();
    }

}
