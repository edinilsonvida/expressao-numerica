package br.com.expressao;

import static br.com.expressao.ExpressaoAleatoria.gerarExpressao;
import java.io.IOException;
import java.util.Scanner;

public class ExpressaoNumerica {

    //recebe os dados de saída do método imprimirParentetizacaoOtima()
    private static String parentetizacaoOtima;
    //recebe os valores dos cálculos feitos para o melhor valor
    private static int valorOtimo[][];
    //recebe o valor das posições do melhor valor
    private static int posicaoValorOtimo[][];
    //recebe os valores (operandos e operadores) da expressão numérica
    private static String expressao;
    //extrai os operandos da expressão numérica
    private static int[] operando;
    //extrai os operadores da expressão numérica
    private static char[] operador;

    public ExpressaoNumerica() {
        parentetizacaoOtima = "";
    }

    /*Métodos da classe*/
    /*Método para contar os operandos
     *Parâmetros: n representa a quantidade de elementos*/
    public static void extrairOperando(int n) {
        int k = 0;
        //int contaOperando = 0;
        char c;
        operando = new int[n];
        for (int j = 0; j < expressao.length(); j++) {
            c = expressao.charAt(j);
            if (Character.isDigit(c)) {
                c = (char) Integer.parseInt(String.valueOf(expressao.charAt(j)));
                operando[k] = c;
                //contaOperando++;
                //System.out.printf("%d ---> %dº operando\n", operando[k], contaOperando);
                k++;
            }
        }
    }

    /*Método para contar os operadores
     *Parâmetros: n representa a quantidade de elementos*/
    public static void extrairOperador(int n) {
        int k = 0;
        //int contaOperador = 0;
        operador = new char[n];
        for (int i = 0; i < expressao.length(); i++) {
            if ((expressao.charAt(i) == '+' || expressao.charAt(i) == '*')) {
                operador[k] = expressao.charAt(i);
                //contaOperador++;
                //System.out.printf("%c ---> %dº operador\n", operador[k], contaOperador);
                k++;
            }
        }
    }

    /*Método para calcular o melhor custo
     *Parâmetros: operando representa os operandos da expressão*/
    public static int[][] calculoProgramacaoDinamica(int operando[]) {
        int n = operando.length;
        int[][] retorno = new int[n][n];
        try {
            int i = 0; //linhas
            int j = 0; //colunas
            int k = 0;
            int valorAtual = 0;
            int valorOtimo[][] = new int[n][n]; //i * j
            int posicaoValorOtimo[][] = new int[n][n];
            for (i = 0; i < n; i++) {
                valorOtimo[i][i] = operando[i]; //caso base
                //System.out.println("Valor ótimo: " + valorOtimo[i][i]);
            }
            for (int l = 1; l < n; l++) {
                for (i = 0; i < n - l; i++) {
                    j = i + l;
                    valorOtimo[i][j] = 0; //inicializa a posição da matriz com 0
                    for (k = i; k < j; k++) {
                        if (operador[k] == '+') {
                            valorAtual = valorOtimo[i][k] + valorOtimo[k + 1][j];
                            //System.out.printf("Operador +: %d\n", valorAtual);
                        } else {
                            valorAtual = valorOtimo[i][k] * valorOtimo[k + 1][j];
                            //System.out.printf("Operador *: %d\n", valorAtual);
                        }
                        //define o valor máximo a ser armazenado
                        if (valorAtual > valorOtimo[i][j]) {
                            valorOtimo[i][j] = valorAtual;
                            //System.out.println("Valor ótimo: " + valorOtimo[i][j]);
                            posicaoValorOtimo[i][j] = k + 1;
                            //System.out.println("K: " + posicaoValorOtimo[i][j]);
                        }
                    }
                }
            }
            retorno = posicaoValorOtimo;
        } catch (Exception e) {
            System.out.println("Erro: " + e);
            e.printStackTrace();
        }
        return retorno;
    }

    /*Método para calcular o melhor custo, porém com chamadas recursivas
     *Parâmetros: i é o índice inicial
     *            j é o índice final*/
    public static int calculoRecursivo(int i, int j) {
        int retorno = 0;
        if (i == j) {
            retorno = operando[i]; //caso base
            //System.out.printf("Operando: %d\n", operando[i]);
        } else {
            //valorOtimo[i][j] = Integer.MAX_VALUE;
            int valorAtual;
            //valorOtimo[i][j] = 0; //inicializa a posição da matriz com 0
            for (int k = i; k < j; k++) {
                if (operador[k] == '+') {
                    valorAtual = calculoRecursivo(i, k) + calculoRecursivo(k + 1, j);
                } else {
                    valorAtual = calculoRecursivo(i, k) * calculoRecursivo(k + 1, j);
                }
                //define o valor máximo a ser armazenado
                if (valorAtual > valorOtimo[i][j]) {
                    valorOtimo[i][j] = valorAtual;
                    //System.out.println("Valor ótimo: " + valorOtimo[i][j]);
                    posicaoValorOtimo[i][j] = k + 1;
                    //System.out.println("K: " + posicaoValorOtimo[i][j]);
                }
                retorno = valorOtimo[i][j];
            }
        }
        //System.out.println("Valor ótimo|Retorno: " + retorno);
        return retorno;
    }

    public static void imprimirOperando() {
        int linha;
        int n = operando.length;
        for (linha = 0; linha < n; linha++) {
            System.out.println("Operando: " + linha + " = " + operando[linha]);
        }
    }

    public static void imprimirOperador() {
        int linha;
        int n = operador.length;
        for (linha = 0; linha < n; linha++) {
            System.out.println("Operador: " + linha + " = " + operador[linha]);
        }
    }

    /*Método para alocar os parênteses de uma forma ótima
     *Parâmetros: i é o índice inical
     *            j é o índice final*/
    public String imprimirParentetizacaoOtima(int i, int j) {
        if (i == j) {
            expressao += operando[i] + " ";
        } else {
            expressao += " ( ";
            imprimirParentetizacaoOtima(i, posicaoValorOtimo[i][j] - 1);
            expressao += operador[i];
            imprimirParentetizacaoOtima(posicaoValorOtimo[i][j], j);
            expressao += " ) ";
        }
        return expressao;
    }

    private void exibirSolucaoOtima() {
        System.out.println("A melhor alocação de parênteses é: " + imprimirParentetizacaoOtima(0, operando.length - 1));
    }

    private void calcularUsandoPD() {
        ExpressaoNumerica.posicaoValorOtimo = calculoProgramacaoDinamica(operando);
    }

    public static void main(String[] args) throws IOException {
        //gera a expressão de acordo com o nível
        Scanner leitura = new Scanner(System.in);
        System.out.printf("Informe o nível da expressão numérica: ");
        int nivel = leitura.nextInt();
        expressao = gerarExpressao(nivel);
        System.out.printf("Expressão numérica gerada: \"%s\"\n\n", expressao);

        //gera a expressão manualmente
        /*
         Scanner ler = new Scanner(System.in);
         System.out.printf("\nInforme uma expressão numérica (operadores + e/ou *):\n");
         expressao = ler.nextLine();
         System.out.printf("Expressão numérica: \"%s\"\n", expressao);
         */
         
        ExpressaoNumerica.extrairOperando(nivel + 1);
        ExpressaoNumerica.extrairOperador(nivel);

        ExpressaoNumerica teste = new ExpressaoNumerica();
        //ExpressaoNumerica.imprimirOperando();
        //ExpressaoNumerica.imprimirOperador();

        //cálculo utilizando programação dinâmica
        System.out.printf("\n+====================================================+\n");
        System.out.printf("Resultados do cálculo utilizando programação dinâmica\n");
        long tempoInicioPD = System.nanoTime();
        teste.calcularUsandoPD();
        //teste.exibirSolucaoOtima();
        long tempoTerminoPD = System.nanoTime();
        System.out.printf("\nTempo de execução em nanosegundos: " + Long.toString(tempoTerminoPD - tempoInicioPD) + "\n");

        //cálculo utilizando recursão
        System.out.printf("\n+====================================================+\n");
        System.out.printf("Resultados do cálculo utilizando recursão\n");
        long tempoInicioR = System.nanoTime();
        valorOtimo = new int[operando.length][operando.length]; //i * j
        posicaoValorOtimo = new int[operando.length][operando.length];
        calculoRecursivo(0, nivel);
        //teste.exibirSolucaoOtima();
        long tempoTerminoR = System.nanoTime();
        System.out.printf("\nTempo de execução em nanosegundos: " + Long.toString(tempoTerminoR - tempoInicioR) + "\n\n");
    }
}
