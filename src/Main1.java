public class Main1 {
    public static void main(String[] args) {
        final flag flag = new flag();
        final AddPrint addPrint = new AddPrint();

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                addPrint.print(flag);
            }
        });
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                addPrint.print(flag);
            }
        });
        A.start();
        B.start();
    }
}

class AddPrint {

    private static Object o = new Object();//锁

    public void print(flag f) {
        char [] a1 = "12345678".toCharArray();
        char [] a2 = "abcdefgh".toCharArray();
        synchronized (o) {
            for (int i = 1; i < 9; i++) {
                if (f.isShouldRun()) {
                    //shouldRun = false;
                    f.setShouldRun(false);
                    o.notify();
                    if (i < 8) {
                        try {
                            System.out.print(a1[i]);
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    f.setShouldRun(true);
                    o.notify();
                    if (i < 8) {
                        try {
                            System.out.print(a2[i]);
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


        }


    }


}


class flag {

    volatile boolean shouldRun = true;//标记

    public boolean isShouldRun() {
        return shouldRun;
    }

    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }


}
