package javaHomeWork.main;

import javaHomeWork.mathOfMatrix.MathMatrixOfLong;

import java.util.Random;
import java.util.concurrent.Callable;


public class Main {
    /**
     * Аргументы командной строки: <тип решения> <путь к файлу первой матрицы> <Путь к файлу второй матрицы> <Путь к результату>
     * <Тип решения>:
     * parallel <p> - многопоточное решение (p>=1)
     * oneThread - обычное решение, не создающее новых процессов.
     * recursive - умножение с применением алгоритма Штрассена
     * Генерация случайной матрицы: -randomGenerate <путь к файлу> <высота> <ширина>
     */
    public static void main(String[] args) {
        try {
            switch (args[0]) {
                case "parallel":
                    MathMatrixOfLong first = MathMatrixOfLong.readFromFile(args[2]);
                    MathMatrixOfLong second = MathMatrixOfLong.readFromFile(args[3]);
                    MathMatrixOfLong result = printTimeOfExecute(() -> first.parallelMultiplication(second, Integer.parseInt(args[1])));
                    result.writeToFile(args[4]);
                    break;
                case "-random":
                    MathMatrixOfLong
                            .generateBy(Integer.parseInt(args[2]), Integer.parseInt(args[3]), ((r, c) -> new Random().nextLong()))
                            .writeToFile(args[1]);
                    break;
                case "oneThread":
                    MathMatrixOfLong first2 = MathMatrixOfLong.readFromFile(args[1]);
                    MathMatrixOfLong second2 = MathMatrixOfLong.readFromFile(args[2]);
                    MathMatrixOfLong result2 = printTimeOfExecute(() -> first2.oneThreadMultiplication(second2));
                    result2.writeToFile(args[3]);
                    break;
                case "recursive":
                    MathMatrixOfLong first3 = MathMatrixOfLong.readFromFile(args[1]);
                    MathMatrixOfLong second3 = MathMatrixOfLong.readFromFile(args[2]);
                    MathMatrixOfLong result3 = printTimeOfExecute(() -> first3.strassenAlgorithm(second3));
                    assert result3 != null;
                    result3.writeToFile(args[3]);
                    break;
                default:
                    throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Неверные аргументы командной строки!");
        }
    }

    private static MathMatrixOfLong printTimeOfExecute(Callable<MathMatrixOfLong> task) {
        try {
            long start = System.currentTimeMillis();
            MathMatrixOfLong result = task.call();
            System.out.println("Время выполнения: " + (System.currentTimeMillis() - start) + "мс.");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

}
