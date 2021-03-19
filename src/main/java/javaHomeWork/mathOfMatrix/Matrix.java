package javaHomeWork.mathOfMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class Matrix<T> {
    private final List<List<T>> value;
    public int height;
    public int width;


    public ArrayList<T> getColumnVector(int column) {
        ArrayList<T> result = new ArrayList<>();
        value.forEach(element -> result.add(element.get(column)));
        return result;
    }

    public ArrayList<T> getRowVector(int row) {
        return new ArrayList<>(value.get(row));
    }

    public void put(int row, int column, T element) {
        value.get(row).set(column, element);
    }

    public T get(int row, int column) {
        return value.get(row).get(column);
    }

    public Matrix<T> verticalConcatenate(Matrix<T> other) {
        if (this.width != other.width) throw new IllegalArgumentException("Разные размеры матриц по горизонтали!!");
        List<List<T>> resultListOfLists = new ArrayList<>(value);
        resultListOfLists.addAll(other.value);
        return new Matrix<>(resultListOfLists);
    }

    public Matrix<T> horizontalConcatenate(Matrix<T> other) {
        if (this.height != other.height) throw new IllegalArgumentException("Разные размеры матриц по вертикали!!");
        List<List<T>> resultListOfLists = new ArrayList<>(value);
        IntStream.range(0, height).forEach(i -> resultListOfLists.get(i).addAll(other.value.get(i)));
        return new Matrix<>(resultListOfLists);
    }

    public Matrix<T> subMatrix(int r1,int c1,int r2,int c2) {
        List<List<T>> result = new ArrayList<>(value.subList(r1, r2));
        for (int i = 0; i < r2 - r1; i++)
            result.set(i , result.get(i).subList(c1,c2));
        return new Matrix<>(result);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        value.forEach(element -> result.append(element.toString()).append("\n"));
        return result.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        return other.getClass() == this.getClass() && ((Matrix<T>)other).value.equals(value);
    }

    @Override
    public int hashCode(){
        return value.hashCode();
    }

    public Matrix(List<List<T>> listOfLists) {
        value = new ArrayList<>(listOfLists);
        height = value.size();
        width = value.get(0).size();
    }

    public Matrix(int height, int width, List<T> matrixList) {
        this.height = height;
        this.width = width;
        List<List<T>> targetValue = new ArrayList<>();
        for(int r = 0; r < height; r++) {
            List<T> tempList = new ArrayList<>();
            for (int c = 0; c < width; c++)
                tempList.add(matrixList.get(r*width + c));
            targetValue.add(tempList);
        }
        value = targetValue;
    }
    public Matrix(Matrix<T> other) {
        this.value = new ArrayList<>(other.value);
        this.height = other.height;
        this.width = other.width;
    }
}
