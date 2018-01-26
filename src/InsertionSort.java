import java.io.*;
import java.util.Scanner;

// Исключение для 3 аргумента командной строки (integer или string)
class NotExistsTypeOfContent extends Exception {
}
// Исключение для 4 аргумента командной строки (asc или desc)
class NotExistsOrderSorting extends Exception {
}

public class InsertionSort {

    // Метод сравнения двух строк лексикографически без библиотечных функций
    private static int compareTwoStrings (String strA, String strB){
        int lenStrA;
        int lenStrB;

            if (strA == null) {
                lenStrA = 0;
            } else {
                lenStrA = strA.length();
            }

            if (strB == null) {
                lenStrB = 0;
            } else {
                lenStrB = strB.length();
            }

            for (int i = 0; i < lenStrA && i < lenStrB; i++) {
                if ((int) strA.charAt(i) == (int) strB.charAt(i)) {
                    continue;
                } else {
                    return (int) strA.charAt(i) - (int) strB.charAt(i);
                }
            }
            if (lenStrA < lenStrB) {
                return (int) strB.charAt(lenStrA);

            } else if (lenStrA > lenStrB) {
                return (int) strA.charAt(lenStrB);
            } else {
                return 0;
            }
    }

    public static void main(String[] args) {
        Integer maxCountElements = 100; // Максимальное количество строк во входном файле
        int arrInt[] = new int[maxCountElements];
        String arrString[] = new String[maxCountElements];
        boolean isInteger = false;
        boolean isAsc = false;
        String strOfFile = null;
        try {
            // Входные аргументы
            // args[0] - Название входящего файла
            // args[1] - Название исходящего файла
            // args[2] - Тип содержимого (integer или string)
            // args[3] - Порядок сортировки (asc или desc)

            System.out.println("Программа сортировки содержимого файла.\n" +
                    "Инструкция как запускать программу:\n" +
                    "Запускать из командной строки следующим образом: java InsertionSort <имя_входного_файла> <имя_выходного_файла> <тип_содержимого> <порядок_сортировки>.\n" +
                    "<имя_входного_файла> - необходимо положить файл в папку InsertionSort\\bin и указать название файла.\n" +
                    "<имя_выходного_файла> - файл с указанным названием файла создается в папке InsertionSort\\bin.\n" +
                    "<тип_содержимого> - может принимать значение integer для целых чисел, и string для строк.\n" +
                    "<порядок_сортировки> - может принимать значение asc для сортировки по возрастанию и desc для сортировки по убыванию.\n" +
                    "Пример запуска \"java InsertionSort input.txt output.txt integer asc\".\n");

            Scanner scanner = new Scanner(args[0]);
            try {
                if (args[0].length() != 0) {
                    File file = new File(args[0]);
                    scanner = new Scanner(file,"cp1251");
                }
                if (args[2].length() != 0) {
                    if (args[2].equals("integer")) {
                        isInteger = true;
                    } else if (args[2].equals("string")) {
                        isInteger = false;
                    } else {
                        throw new NotExistsTypeOfContent();
                    }
                }
                if (args[3].length() != 0) {
                    if (args[3].equals("asc")) {
                        isAsc = true;
                    } else if (args[3].equals("desc")) {
                        isAsc = false;
                    } else {
                        throw new NotExistsOrderSorting();
                    }
                }

                int iCount = 0;
                while (scanner.hasNextLine()) {
                    strOfFile = scanner.nextLine();
                    if (isInteger) {
                        arrInt[iCount] = Integer.parseInt(strOfFile);
                        iCount++;
                    } else {
                        arrString[iCount] = strOfFile;
                        iCount++;
                    }
                }
                if (isInteger) {
                    // Алгоритм сортировки вставками для integer
                    int index, tempInt;
                    for (int i = 0; i < arrInt.length; i++) {
                        tempInt = arrInt[i];
                        if (isAsc) // Сортировка по возрастанию
                        {
                            for (index = i - 1; index >= 0 && arrInt[index] > tempInt; index--) {
                                arrInt[index + 1] = arrInt[index];
                            }
                            arrInt[index + 1] = tempInt;
                        } else // Сортировка по убыванию
                            {
                            for (index = i - 1; index >= 0 && arrInt[index] < tempInt; index--) {
                                arrInt[index + 1] = arrInt[index];
                            }
                            arrInt[index + 1] = tempInt;
                        }
                    }
                } else {

                    if (isAsc) // Сортировка по возрастанию
                    {
                        String tempString;
                        for (int next = 1; next < arrString.length; next++) {
                            tempString = arrString[next];
                            int moveItem = next;

                            while (moveItem > 0 && compareTwoStrings(arrString[moveItem - 1], tempString) > 0) {
                                arrString[moveItem] = arrString[moveItem - 1];
                                moveItem--;
                                if (moveItem == 0) break;
                            }
                            arrString[moveItem] = tempString;
                        }
                    } else // сортировка по убыванию
                        {
                        String tempString;
                        for (int next = 1; next < arrString.length; next++) {
                            tempString = arrString[next];

                            int moveItem = next;

                            while (moveItem > 0 && compareTwoStrings(arrString[moveItem - 1], tempString) < 0) {
                                arrString[moveItem] = arrString[moveItem - 1];
                                moveItem--;
                                if (moveItem == 0) break;
                            }
                            arrString[moveItem] = tempString;
                        }
                    }

                }
                // Запись в файл
                try (PrintWriter writer = new PrintWriter(args[1])) {
                    if (isInteger) {
                        for (int i = 0; i < arrInt.length; i++) {
                            writer.println(arrInt[i]);
                        }
                    } else
                    {
                        for (int i = 0; i < arrString.length; i++) {
                            writer.println(arrString[i]);
                        }
                    }
                    System.out.println("Результат выполнения программы записан в файл "+ args[1]);
                } catch (IOException e) {
                    System.out.println("Не могу сохранить файл");
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("Программа завершила работу. Файл " + args[0] + " не найден");
            } catch (NotExistsTypeOfContent e) {
                System.out.println("Программа завершила работу. 3 аргумент должен быть integer или string");
            } catch (NotExistsOrderSorting e) {
                System.out.println("Программа завершила работу. 4 аргумент должен быть asc или desc");
            } catch (NumberFormatException e) {
                System.out.println("Программа завершила работу. В файле " + args[0] + " встретилась строка вместо числа");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Программа завершила работу. Укажите строку из 4 аргументов, разделенных пробелом");
        }
    }
}
