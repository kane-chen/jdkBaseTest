package cn.kane;

public class Test {

    public static void main(String[] args) {
        Print p = new Print();
        new numThread(p).start();
        new charThread(p).start();
    }
}

class Print {

    boolean boo = true;

    char ch = 'A';

    int num = 1;

    public synchronized void printNum() {
        if (boo) {
            try {
                this.wait();
            } catch (Exception e) {}
            System.out.println(num++);
        }
        boo = false;
        notify();
    }

    public synchronized void printChar() {
        if (!boo) {
            try {
                this.wait();
            } catch (Exception e) {}
            System.out.println(ch++);
        }
        boo = true;
        notify();
    }
}

class numThread extends Thread {

    Print p = null;

    public numThread(Print p) {
        this.p = p;
    }

    public void run() {
        while (p.num <= 26)
            p.printNum();
    }
}

class charThread extends Thread {

    Print p = null;

    public charThread(Print p) {
        this.p = p;
    }

    public void run() {
        while (p.ch <= 'Z')
            p.printChar();
    }
}
