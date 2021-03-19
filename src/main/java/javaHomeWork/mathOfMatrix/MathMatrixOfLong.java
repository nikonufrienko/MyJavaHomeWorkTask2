package javaHomeWork.mathOfMatrix;

import javaHomeWork.exceptions.IllegalFilePathException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MathMatrixOfLong extends Matrix<Long> {

    public static MathMatrixOfLong getEmptyMatrix(int height, int width) {
        return new MathMatrixOfLong(height, width, new ArrayList<>(Arrays.asList(new Long[height * width])));
    }

    public static MathMatrixOfLong zeros(int height, int width) {
        List<Long> resultStream = Stream.generate(() -> 0L).limit((long) height * width).collect(Collectors.toList());
        return new MathMatrixOfLong(height, width, resultStream);
    }

    public static MathMatrixOfLong generateBy(int height, int width, Generator generator) {
        List<Long> resultList = new LinkedList<>();
        for (int i = 0; i < height * width; i++) {
            resultList.add(generator.generate(i / width, i % width));
        }
        return new MathMatrixOfLong(height, width, new ArrayList<>(resultList));
    }

    private Long vectorScalarMultiplication(ArrayList<Long> first, ArrayList<Long> second) {
        long result = 0;
        for (int i = 0; i < first.size(); i++)
            result += first.get(i) * second.get(i);
        return result;
    }

    public MathMatrixOfLong(List<List<Long>> listOfLists) {
        super(listOfLists);
    }

    public MathMatrixOfLong(int height, int width, List<Long> matrixList) {
        super(height, width, matrixList);
    }

    public MathMatrixOfLong oneThreadMultiplication(MathMatrixOfLong other) {
        if (this.width != other.height)
            throw new ArithmeticException("Длина строк первой матр. не совпадает с длиной столбцов второй.");
        MathMatrixOfLong result = getEmptyMatrix(this.height, other.width);
        for (int r = 0; r < result.height; r++) {
            for (int c = 0; c < result.width; c++) {
                result.put(r, c, vectorScalarMultiplication(this.getRowVector(r), other.getColumnVector(c)));
            }
        }
        return result;
    }

    public void writeToFile(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(height + " " + width + "\n");
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++)
                    writer.write(this.get(r, c).toString() + " ");
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new IllegalFilePathException("Неправильный путь!!");
        }
    }

    public MathMatrixOfLong plus(Matrix<Long> other) {
        if (this.height != other.height || other.width != this.width)
            throw new IllegalArgumentException("Матрицы должны иметь одиннаковый размер!!");
        MathMatrixOfLong result = getEmptyMatrix(this.height, this.width);
        for (int r = 0; r < result.height; r++)
            for (int c = 0; c < result.width; c++)
                result.put(r, c, this.get(r, c) + other.get(r, c));
        return result;
    }

    public MathMatrixOfLong minus(Matrix<Long> other) {
        if (this.height != other.height || other.width != this.width)
            throw new IllegalArgumentException("Матрицы должны иметь одиннаковый размер!!");
        MathMatrixOfLong result = getEmptyMatrix(this.height, this.width);
        for (int r = 0; r < result.height; r++)
            for (int c = 0; c < result.width; c++)
                result.put(r, c, this.get(r, c) - other.get(r, c));
        return result;
    }

    public MathMatrixOfLong(Matrix<Long> matrix) {
        super(matrix.value);
    }

    public static MathMatrixOfLong readFromFile(String path) {
        try (FileReader reader = new FileReader(path)) {
            BufferedReader advancedReader = new BufferedReader(reader);
            String descriptionLine = advancedReader.readLine();
            int heightOfNewMatrix = Integer.parseInt(descriptionLine.split(" ")[0]);
            int widthOfNewMatrix = Integer.parseInt(descriptionLine.split(" ")[1]);
            List<Long> output = new LinkedList<>();
            for (int r = 0; r < heightOfNewMatrix; r++) {
                String[] rowArray = advancedReader.readLine().split(" ");
                if (rowArray.length != widthOfNewMatrix) throw new IllegalArgumentException("Ошибка в файле!");
                for (String number : rowArray) {
                    output.add(Long.valueOf(number));
                }

            }
            return new MathMatrixOfLong(heightOfNewMatrix, widthOfNewMatrix, new ArrayList<>(output));
        } catch (IOException e) {
            throw new IllegalFilePathException("Неправильный путь!!");
        }
    }


    public MathMatrixOfLong parallelMultiplication(MathMatrixOfLong other, int threadsNum) {
        if (this.width != other.height)
            throw new ArithmeticException("Длина строк первой матр. не совпадает с длиной столбцов второй.");
        if (threadsNum < 1)
            throw new IllegalArgumentException("Количество потоков должно быть не менее одного.");
        MathMatrixOfLong result = getEmptyMatrix(this.height, other.width);
        List<Thread> threadList = new LinkedList<>();
        for (int i = 0; i < threadsNum; i++) {
            int cellsPerThread = (result.width * result.height) / threadsNum;
            int start = cellsPerThread * i;
            int end;
            if (i == threadsNum - 1) end = cellsPerThread * (i + 1) + ((result.width * result.height) % threadsNum);
            else end = cellsPerThread * (i + 1);
            threadList.add(new Thread(() -> {
                for (int ind = start; ind < end; ind++) {
                    int row = ind / result.width;
                    int column = ind % result.width;
                    result.put(row, column, vectorScalarMultiplication(this.getRowVector(row), other.getColumnVector(column)));
                }
            }));
        }
        threadList.forEach(Thread::start);
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        return result;
    }

    public MathMatrixOfLong strassenAlgorithm(MathMatrixOfLong other) {
        if (this.width != other.height)
            throw new ArithmeticException("Длина строк первой матр. не совпадает с длиной столбцов второй.");
        int targetSize = Math.max(Math.max(this.width, this.height), Math.max(other.height, other.width));
        int newSize = 1;
        while (newSize < targetSize) newSize *= 2;
        MathMatrixOfLong first =
                new MathMatrixOfLong(this.horizontalConcatenate(zeros(this.height, newSize - this.width))
                        .verticalConcatenate(zeros(newSize - this.height, newSize)));
        MathMatrixOfLong second =
                new MathMatrixOfLong(other.horizontalConcatenate(zeros(other.height, newSize - other.width))
                        .verticalConcatenate(zeros(newSize - other.height, newSize)));
        return new MathMatrixOfLong(first.recursiveMultiplication(second).subMatrix(0, 0, this.height, other.width));
    }

    private MathMatrixOfLong recursiveMultiplication(MathMatrixOfLong other) {
        int size = height;
        if (size < 128)
            return oneThreadMultiplication(other);
        MathMatrixOfLong a11 = new MathMatrixOfLong(this.subMatrix(0, 0, size / 2, size / 2));
        MathMatrixOfLong a12 = new MathMatrixOfLong(this.subMatrix(0, size / 2, size / 2, size));
        MathMatrixOfLong a21 = new MathMatrixOfLong(this.subMatrix(size / 2, 0, size, size / 2));
        MathMatrixOfLong a22 = new MathMatrixOfLong(this.subMatrix(size / 2, size / 2, size, size));

        MathMatrixOfLong b11 = new MathMatrixOfLong(other.subMatrix(0, 0, size / 2, size / 2));
        MathMatrixOfLong b12 = new MathMatrixOfLong(other.subMatrix(0, size / 2, size / 2, size));
        MathMatrixOfLong b21 = new MathMatrixOfLong(other.subMatrix(size / 2, 0, size, size / 2));
        MathMatrixOfLong b22 = new MathMatrixOfLong(other.subMatrix(size / 2, size / 2, size, size));

        MathMatrixOfLong p1 = a11.plus(a22).recursiveMultiplication(b11.plus(b22));
        MathMatrixOfLong p2 = a21.plus(a22).recursiveMultiplication(b11);
        MathMatrixOfLong p3 = a11.recursiveMultiplication(b12.minus(b22));
        MathMatrixOfLong p4 = a22.recursiveMultiplication(b21.minus(b11));
        MathMatrixOfLong p5 = a11.plus(a12).recursiveMultiplication(b22);
        MathMatrixOfLong p6 = a21.minus(a11).recursiveMultiplication(b11.plus(b12));
        MathMatrixOfLong p7 = a12.minus(a22).recursiveMultiplication(b21.plus(b22));

        MathMatrixOfLong c11 = p1.plus(p4).minus(p5).plus(p7);
        MathMatrixOfLong c12 = p3.plus(p5);
        MathMatrixOfLong c21 = p2.plus(p4);
        MathMatrixOfLong c22 = p1.minus(p2).plus(p3).plus(p6);

        return new MathMatrixOfLong(c11.horizontalConcatenate(c12).verticalConcatenate(c21.horizontalConcatenate(c22)));
    }
}
