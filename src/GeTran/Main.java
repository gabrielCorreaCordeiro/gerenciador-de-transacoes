
package GeTran;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static int numeroItens;
    private static int numeroTransacoes;
    private static int numeroAcessos;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Criando transacoes e gravando no banco...");
        Produtor produtor;
        produtor = new Produtor(getNumeroItens(), getNumeroTransacoes(), getNumeroAcessos());
        produtor.start();
        System.out.println("Pressione Enter para encerrar a producao!");

        if (scanner.hasNextLine()) {
            System.out.println("Producao encerrada");
            produtor.setFlag(false);
        }

    }

    public static int getNumeroItens() {
        return numeroItens = new Random().nextInt(3) + 2;
    }

    public void setNumeroItens(int numeroItens) {
        this.numeroItens = numeroItens;
    }

    public static int getNumeroTransacoes() {
        return numeroTransacoes = new Random().nextInt(5) + 1;
    }

    public void setNumeroTransacoes(int numeroTransacoes) {
        this.numeroTransacoes = numeroTransacoes;
    }

    public static int getNumeroAcessos() {
        return numeroAcessos = new Random().nextInt(10) + 2;
    }

    public void setNumeroAcessos(int numeroAcessos) {
        this.numeroAcessos = numeroAcessos;
    }

}
