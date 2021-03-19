# MyJavaHomeWorkTask2
## Аргументы командной строки:
    <тип решения> <путь к файлу первой матрицы> <Путь к файлу второй матрицы> <Путь к результату>
## Возможные типы решения:
    1.  parallel <p> -- многопоточное умножение(p>=1).
    2.  oneThread -- обычное умножение, не создающее новых процессов.
    3.  recursive -- умножение с применением алгоритма Штрассена.
## Генерация случайной матрицы:
    -random <путь к файлу> <высота> <ширина>
## Результаты измерений (1000x1023 * 1023x1012):
### Запуск в однопоточном режиме:
![image](https://user-images.githubusercontent.com/25980970/111812382-2259cc80-88e9-11eb-847b-a3a6a9bb1613.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111812592-546b2e80-88e9-11eb-9a8f-65a768d537f9.png)<br/>
### Запуск в многопоточном режиме:
![image](https://user-images.githubusercontent.com/25980970/111813163-e6733700-88e9-11eb-9c44-8075a44e185e.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111813188-ee32db80-88e9-11eb-8ca6-2a2810b0cdbe.png)<br/>
-
![image](https://user-images.githubusercontent.com/25980970/111813256-0276d880-88ea-11eb-8249-3fa9b9363e43.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111813726-9f397600-88ea-11eb-9ed3-2d0253ba0d3a.png)<br/>
-
![image](https://user-images.githubusercontent.com/25980970/111813840-ca23ca00-88ea-11eb-9a67-e4031a0b5a32.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111813891-da3ba980-88ea-11eb-8933-af974f90859d.png)<br/>
-
![image](https://user-images.githubusercontent.com/25980970/111813998-fe978600-88ea-11eb-8bee-fe41a4047465.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111814125-28e94380-88eb-11eb-8af2-a78dbe8e9184.png)<br/>
-
![image](https://user-images.githubusercontent.com/25980970/111814250-4e764d00-88eb-11eb-8bdb-952463e01126.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111814290-5a620f00-88eb-11eb-95ec-01a075e16a2d.png)<br/>
### Запуск с использованием алгоритма Штрассена в одном потоке:
![image](https://user-images.githubusercontent.com/25980970/111814569-af058a00-88eb-11eb-92eb-487abec3e9f5.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111814629-bf1d6980-88eb-11eb-946c-9efb23c214a9.png)<br/>
## Результаты измерений (500x700 * 700x500):
### Запуск в однопоточном режиме:
![image](https://user-images.githubusercontent.com/25980970/111816736-3d7b0b00-88ee-11eb-85c5-ae1695b0f68a.png)<br/>
![image](https://user-images.githubusercontent.com/25980970/111816847-5f748d80-88ee-11eb-974e-201d6cdad656.png)
--
### Запуск в многопоточном режиме:
![image](https://user-images.githubusercontent.com/25980970/111817059-98146700-88ee-11eb-935c-99ba16ff46b2.png)<br/>
--
![image](https://user-images.githubusercontent.com/25980970/111817217-cb56f600-88ee-11eb-8391-ea186060e4cf.png)</br>
--
![image](https://user-images.githubusercontent.com/25980970/111817498-1e30ad80-88ef-11eb-9db9-b8313e680194.png)</br>
--
![image](https://user-images.githubusercontent.com/25980970/111817595-3bfe1280-88ef-11eb-93b9-4ee17aec9b36.png)</br>
--
![image](https://user-images.githubusercontent.com/25980970/111817831-8d0e0680-88ef-11eb-8187-c2407cad94f2.png)</br>
### Запуск с использованием алгоритма Штрассена в одном потоке:
![image](https://user-images.githubusercontent.com/25980970/111818107-dd856400-88ef-11eb-8fbb-34215c665c1c.png)</br>
--
## Вывод:
1.  Минимальное время работы достигается при использовании 4-х потоков, это обусловлено тем, что у меня 4-x поточный процессор (Pentium G4560).</br>
2.  Результаты работы алгоритма Штрассена показывают, что эффективность непосредственно самого алгоритма является более важным фактором, нежели чем просто эффективное использование ресурсов системы, выполняющей его.</br>
3.  Неэффективность алгоритма Штрассена во втором случае можно объяснить тем, что для его применения необходимо дополнить матрицу нулевыми строками и столбцами до матрицы  2^n x 2^n.</br>
