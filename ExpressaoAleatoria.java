package br.com.expressao;

import java.util.Random;

public class ExpressaoAleatoria {

    public static int[] operandos = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static String[] operadores = {"+", "*"};

    private static int gerarOperando() {
        Random aleatorio = new Random();
        int n = aleatorio.nextInt(9);
        return operandos[n];
    }

    public static String gerarOperador() {
        Random aleatorio = new Random();
        int n = aleatorio.nextInt(2);
        return operadores[n];
    }

    public static String gerarExpressao(int n) {
        if (n == 1) {
            return gerarOperando() + gerarOperador() + gerarOperando();
        } else {
            return gerarExpressao(n - 1) + gerarOperador() + gerarOperando();
        }
    }
}
