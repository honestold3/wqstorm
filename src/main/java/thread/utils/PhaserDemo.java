package thread.utils;

import java.util.concurrent.Phaser;


/**
 * Created by wq on 15/3/3.
 */
public class PhaserDemo extends Phaser {

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                return studentsArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishSecondExercise();
            case 3:
                return finishExam();
            default:
                return true;
        }
    }

    private boolean finishExam() {
        System.out.printf("Phaser: All the students have finished the exam.\n");
        System.out.printf("Phaser: Thank you for your time.\n");
        return false;
    }

    private boolean finishSecondExercise() {
        System.out.printf("Phaser: All the students have finished the second exercise.\n");
        System.out.printf("Phaser: It's time for the third one.\n");
        return false;
    }

    private boolean finishFirstExercise() {
        System.out.printf("Phaser: All the students have finished the first exercise.\n");
        System.out.printf("Phaser: It's time for the second one.\n");
        return false;
    }

    /**
     * It writes two log messages to the console and returns the false value to
     * indicate that the phaser continues with its execution.
     *
     * @return
     */
    private boolean studentsArrived() {
        System.out.printf("Phaser: The exam are going to start. The students are ready.\n");
        System.out.printf("Phaser: We have %d students.\n",getRegisteredParties());
        return false;
    }

    public static void main(String[] args) {
        PhaserDemo phaser = new PhaserDemo();
        Student students[] = new Student[5];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(phaser);
            phaser.register();
        }

        Thread threads[] = new Thread[students.length];
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i], "Student " + i);
            threads[i].start();
        }

        //main thread join
//        //System.out.println(threads.length);
        for (int i = 0; i < threads.length; i++) {
            try {
               threads[i].join();//wait finished
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Main: The phaser has finished: %s.\n",phaser.isTerminated());
    }


}

