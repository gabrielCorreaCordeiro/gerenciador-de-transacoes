package Beans;

import Beans.Dado;
import java.util.LinkedList;

public class ListaDados {

    private LinkedList<Dado> Listdados;

    public ListaDados(int numeroItens) {
        Listdados = new LinkedList<>();
        gerarDados(numeroItens);
    }

    private void gerarDados(int numeroItens) {
        while (numeroItens > 0) {
            String nomeDado = "ID" + numeroItens;
            Listdados.add(new Dado(nomeDado));
            numeroItens--;
        }
    }

    public LinkedList<Dado> getDados() {
        return Listdados;
    }

}
