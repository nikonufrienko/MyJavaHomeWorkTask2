package javaHomeWork.main;

import javaHomeWork.mathOfMatrix.MathMatrixOfLong;

import java.util.Random;


public class Main {
    /**
     * Аргументы коммандной строки: <тип решения> <путь к файлу первой матрицы> <Путь к файлу второй матрицы> <Путь к результату>
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
                    MathMatrixOfLong.readFromFile(args[2])
                            .parallelMultiplication(MathMatrixOfLong.readFromFile(args[3]), Integer.parseInt(args[1]))
                            .writeToFile(args[4]);
                    break;
                case "-random":
                    MathMatrixOfLong
                            .generateBy(Integer.parseInt(args[2]), Integer.parseInt(args[3]), ((r, c) -> new Random().nextLong()))
                            .writeToFile(args[1]);
                    break;
                case "oneThread":
                    MathMatrixOfLong.readFromFile(args[1]).oneThreadMultiplication(MathMatrixOfLong.readFromFile(args[2])).writeToFile(args[3]);
                    break;
                case "recursive":
                    MathMatrixOfLong.readFromFile(args[1]).strassenAlgorithm(MathMatrixOfLong.readFromFile(args[2])).writeToFile(args[3]);
                    break;
                default: throw new IndexOutOfBoundsException();
            }
        }catch(IndexOutOfBoundsException exception) {
            System.out.println("Неверные аргументы коммандной строки!");
        }
    }
}
