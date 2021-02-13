/***
杨金凤添加注释
****/
public class Main2 {
    private  static volatile    int a = 0;
    public static void main(String[] args) {
        char [] a1 = "12345678".toCharArray();
        char [] a2 = "abcdefgh".toCharArray();

        Object lock = new Object();//锁

        Thread t1 = new Thread(()->{
            synchronized (lock){
                if(a==0){
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char ch:a1) {
                    System.out.print(ch);
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notifyAll();
            }
        });

        Thread t2 = new Thread(()->{
            synchronized (lock){
                a =1;
                for (char ch:a2) {
                    System.out.print(ch);
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                lock.notifyAll();
            }
        });

        t1.start();
        t2.start();
    }
}
