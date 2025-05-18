package homework.task3;

public class IntThread extends Thread {
    private final int num;

    public IntThread(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println(this.num);
        }
    }
}
