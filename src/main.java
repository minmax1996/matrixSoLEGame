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
        if (args[0].isEmpty()){
            System.out.print("тут будет UI");
        }
        else{
            if(args[0].equals("--console")){
                try {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("1: ввести матрицу");
                    System.out.println("2: играть с рандомной");

                    int fit = sc.nextInt();
                    switch (fit) {
                        case 1: //ввод
                            System.out.println("введите размер матрицы:");
                            int N = sc.nextInt();
                            double[][] A = new double[N][N];
                            double[] b = new double[N];
                            ParseInputMatrix(A, b, N);
                            SOLEMatrix readMatrix = new SOLEMatrix(N, A, b);

                            PlayMatrix(readMatrix);
                            return;


                        case 2: //рандом
                            System.out.println("введите размер матрицы:");
                            int n = sc.nextInt();
                            SOLEMatrix mainMatrix = new SOLEMatrix(n);
                            mainMatrix.ShuffleArray();

                            PlayMatrix(mainMatrix);
                            return;
                        case 3: //testdev
                            System.out.println("введите размер матрицы:");
                            int _n = sc.nextInt();
                            double[][] A1 = new double[_n][_n];
                            double[] b1 = new double[_n];
                            ParseInputMatrix(A1, _n);
                            Matrix mm=new Matrix(_n, A1);
                            System.out.print(mm.Determinant());
                            return;

                        default:
                            System.out.print("lame");
                            break;
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.print("ArrayIndexOutOfBoundsException");
                }

            }
        }

        //launch(args);
    }

    private static void ParseInputMatrix(double[][]A,double[]b,int N){
        for (int i=0; i < N; ++i) {
            System.out.println("введите "+(i+1)+" строку через пробел");
            Scanner sc2 = new Scanner(System.in);
            String rLine =sc2.nextLine();
            String [] readLine=rLine.split("[ ]+");
            for (int j=0; j < N; ++j){
                A[i][j]=Integer.parseInt(readLine[j]);
            }
            System.out.println("введите значение строки:");
            b[i]=sc2.nextDouble();
        }
    }

    private static void ParseInputMatrix(double[][]A,int N){
        for (int i=0; i < N; ++i) {
            System.out.println("введите "+(i+1)+" строку через пробел");
            Scanner sc2 = new Scanner(System.in);
            String rLine =sc2.nextLine();
            String [] readLine=rLine.split("[ ]+");
            for (int j=0; j < N; ++j){
                A[i][j]=Integer.parseInt(readLine[j]);
            }
        }
    }

    private static void ParseQuery(String query,SOLEMatrix mainMatrix)
    {

        String[] tokens = query.split("[ ]+"); //4C1 + 2C2 =>   [4C1, +, 2C2]
        //парсим 1 аргумент
        String[] tokensAroundC1 = tokens[0].split("[C,c]+"); // 3C1-> 3, 1;
        int enterLine = Integer.parseInt("" + tokensAroundC1[1]) - 1;
        if (!tokensAroundC1[0].equals("")) { //если у первой строки указан множитель, то умножает эту строку на него
            double mulN=(tokensAroundC1[0].equals("-")) ? -1 : Double.parseDouble("" + tokensAroundC1[0]); //если строит просто "-" => умножаем на -1;
            mainMatrix.MulVector(enterLine,mulN);
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
//                    mainMatrix.matrixArray[enterLine].SumVectors(mainMatrix.matrixArray[secondLine].AMulVector(mulNumber));
                    mainMatrix.SumVectors(enterLine,secondLine,mulNumber);
                    break;

                case "/": //если деление, то операция выглядит 2С3 / 5
                    double mulN = Double.parseDouble(tokens[2]); //
//                    mainMatrix.matrixArray[enterLine].DivVector(mulN);
                    mainMatrix.DivVector(enterLine,mulN);
                    break;
                case "~": // свапаем 1 строку с другой
                    String[] tokensAroundC2S = tokens[2].split("[C,c]+"); // 3C1-> 3, 1; тут не учитывается умножение строки на число
                    mainMatrix.SwapLines(enterLine,Integer.parseInt(""+tokensAroundC2S[1])-1);
                    break;
            }
        }
    }

    private static void PlayMatrix(SOLEMatrix mainMatrix){
        Scanner sc = new Scanner(System.in);
        mainMatrix.PrintArray();
        System.out.println("_____________");
        mainMatrix.PrintEq();
        while (!mainMatrix.isSolved()) {
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

        System.out.println("congratulation");
        mainMatrix.PrintEq();
    }


    public void start(Stage primaryStage) {

    }
}
