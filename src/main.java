/**
 * Created by Максим on 23.02.2017.
 */
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class main extends Application {

    public static void main(String[] args) {
        Matrix mainMatrix = new Matrix(5);

        mainMatrix.ShuffleArray(10);
        mainMatrix.PrintArray();
        System.out.println("_____________");
        mainMatrix.PrintEq();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String query;
            System.out.println("введите запрос: ");
            query = sc.nextLine();
            switch (query) {
                case "help":
                    System.out.println("eq for print equation \n 2C1 + 3C4 \n 2C1 \n -C1 \n [+,-] \n C1 / 5 \n C1 ~ C2 ");
                    continue;
                case "eq":
                    mainMatrix.PrintEq();
                    continue;
                case "exit":
                    return;
            }
            ParseQuery(query, mainMatrix);
            mainMatrix.PrintArray();
        }
        //launch(args);
    }

    public static void ParseQuery(String query,Matrix mainMatrix)
    {

        String[] tokens = query.split("[ ]+"); //4C1 + 2C2 =>   [4C1, +, 2C2]
        //парсим 1 аргумент
        String[] tokensAroundC1 = tokens[0].split("[C,c]+"); // 3C1-> 3, 1;
        int enterLine = Integer.parseInt("" + tokensAroundC1[1]) - 1;
        if (!tokensAroundC1[0].equals("")) { //если у первой строки указан множитель, то умножает эту строку на него
            double mulN=(tokensAroundC1[0].equals("-")) ? -1 : Double.parseDouble("" + tokensAroundC1[0]); //если строит просто "-" => умножаем на -1;
            mainMatrix.matrixArray[enterLine].MulVector(mulN);
        }

        if (tokens.length > 1) //если наша операция имеет > 1 операнда
        {
            int sign = 1;
            switch (tokens[1]) //парсим по знаку операции
            {
               case "-":
                   sign = -1; //добавляем знак операции

                case "+": //дальше все как при положительном
                    double mulNumber;
                    String[] tokensAroundC2 = tokens[2].split("[C,c]+"); // разделяем 2 операнд на части 2C4 => [2, 4]

                    int secondLine = Integer.parseInt("" + tokensAroundC2[1]) - 1; //номер строки
                    if (tokensAroundC2[0].equals("")) { //если не было умножения
                        mulNumber = sign;
                    } else {
                        mulNumber = Double.parseDouble(tokensAroundC2[0]) * sign;
                    }

                    //выполняем операцию
                    mainMatrix.matrixArray[enterLine].SumVectors(mainMatrix.matrixArray[secondLine].AMulVector(mulNumber));
                    break;
                case "/": //если деление, то операция выглядит 2С3 / 5
                    double mulN = Double.parseDouble(tokens[2]); //
                    mainMatrix.matrixArray[enterLine].DivVector(mulN);
                    break;
                case "~": // свапаем 1 строку с другой
                    String[] tokensAroundC2S = tokens[2].split("[C,c]+"); // 3C1-> 3, 1; тут не учитывается умножение строки на число
                    mainMatrix.SwapLines(enterLine,Integer.parseInt(""+tokensAroundC2S[1])-1);
                    break;
            }
        }
    }



    public void start(Stage primaryStage) {

    }
}
