package javaHomeWork.mathOfMatrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class MathMatrixOfLongTest {

    @Test
    void getEmptyMatrix() {
        MathMatrixOfLong nulls = MathMatrixOfLong.getEmptyMatrix(100,100);
        for (int i=0; i < 10000; i++) {
            Assertions.assertNull(nulls.get(i / 100, i % 100));
        }
    }

    @Test
    void zeros() {
        MathMatrixOfLong zeros = MathMatrixOfLong.zeros(100,100);
        for (int i=0; i < 10000; i++) {
            Assertions.assertEquals(0,zeros.get(i/100,i%100));
        }
    }

    @Test
    void generateBy() {
        MathMatrixOfLong a = MathMatrixOfLong.generateBy(3,3, ((row, column) -> new Random().nextLong() % 10));
        MathMatrixOfLong b = MathMatrixOfLong.generateBy(3,3, ((row, column) -> new Random().nextLong() % 10));
        System.out.println();
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.oneThreadMultiplication(b));

    }
    @Test
    void parallelMultiplication() {
        MathMatrixOfLong a = MathMatrixOfLong.generateBy(700,500, ((row, column) -> new Random().nextLong()));
        MathMatrixOfLong b = MathMatrixOfLong.generateBy(500,700, ((row, column) -> new Random().nextLong()));
        long start = System.currentTimeMillis();
        String first = a.oneThreadMultiplication(b).toString();
        long end = System.currentTimeMillis();
        System.out.println("Один поток:" + (end - start));
        for(int i = 1; i < 10; i++ ) {
            long start2 = System.currentTimeMillis();
            String second = a.parallelMultiplication(b, i).toString();
            long end2 = System.currentTimeMillis();
            System.out.println("Многопоточка#" + i +":" + (end2 - start2));
            Assertions.assertEquals(first, second);
        }
    }
    @Test
    void writeToFile() {
        MathMatrixOfLong a = MathMatrixOfLong.generateBy(700,500, ((row, column) -> new Random().nextLong()));
        a.writeToFile("a.txt");
        long start = System.currentTimeMillis();
        MathMatrixOfLong b = MathMatrixOfLong.readFromFile("a.txt");
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        Assertions.assertEquals(a,b);
    }

    @Test
    void subMatrix() {
        MathMatrixOfLong a = MathMatrixOfLong.generateBy(16, 16, ((row, column) -> new Random().nextLong() % 10));
        System.out.println(a);
        long start = System.currentTimeMillis();
        for (int i = 0; i < a.height / 4; i++) {//rows
            for (int j = 0; j < a.width / 4; j++) {//columns
                Matrix<Long> b = a.subMatrix(i * 4, j * 4, (i + 1) * 4, (j + 1) * 4);
                System.out.println(b);
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
    @Test
    void recursiveMultiplyTest() {
        MathMatrixOfLong a = MathMatrixOfLong.generateBy(1024, 1024, ((row, column) -> new Random().nextLong() % 10));
        MathMatrixOfLong b = MathMatrixOfLong.generateBy(1024, 1024, ((row, column) -> new Random().nextLong() % 10));
        long start1 = System.currentTimeMillis();
        MathMatrixOfLong r1 = a.parallelMultiplication(b,4);
        System.out.println("4 threads:" + (System.currentTimeMillis() - start1));
        long start2 = System.currentTimeMillis();
        MathMatrixOfLong r2 = a.strassenAlgorithm(b);
        System.out.println("recursive:" + (System.currentTimeMillis() - start2));
        System.out.println(r1.width+" "+r1.height + " " + r2.width + " "+ r2.height);
        Assertions.assertEquals(r1, r2);

    }

}