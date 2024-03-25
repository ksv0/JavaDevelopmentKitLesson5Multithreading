package ksv;

import java.util.concurrent.locks.Lock;

public class Philosopher extends Thread {
    private String name;
    private Lock leftFork;
    private Lock rightFork;
    private int mealsEaten;

    public Philosopher(String name, Lock leftFork, Lock rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.mealsEaten = 0;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            think();
            eat();
        }
    }

    public void think() {
        System.out.println(name + " размышляет.");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void eat() {
        // Захватываем вилки в порядке их номеров, чтобы избежать дедлока
        if (leftFork.hashCode() < rightFork.hashCode()) {
            leftFork.lock();
            System.out.println(name + " взял левую вилку.");
            rightFork.lock();
            System.out.println(name + " взял правую вилку.");
        } else {
            rightFork.lock();
            System.out.println(name + " взял правую вилку.");
            leftFork.lock();
            System.out.println(name + " взял левую вилку.");
        }

        System.out.println(name + " начинает есть.");
        System.out.println(name + " ест.");

        leftFork.unlock();
        rightFork.unlock();
        System.out.println(name + " освободил обе вилки.");

        mealsEaten++;
        System.out.println(name + " закончил есть. Поел " + mealsEaten + " раз.");
    }
}
