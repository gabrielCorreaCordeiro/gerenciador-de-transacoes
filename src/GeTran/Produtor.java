package GeTran;

import Beans.Operacao;
import Beans.Schedule;
import GeTran.GerenciadorTransacao;
import GeTran.GerenciadorTransacao;
import DAO.TransacaoDao;
import DAO.TransacaoDao;
import java.util.LinkedList;

public class Produtor extends Thread {

    private Thread t;
    private int numeroItens;
    private int numeroTransacoes;
    private int numeroAcessos;
    private static GerenciadorTransacao gerenciador;
    private boolean flag = true;

    public Produtor(int numeroItens, int numeroTransacoes, int numeroAcessos) {
        this.numeroItens = numeroItens;
        this.numeroTransacoes = numeroTransacoes;
        this.numeroAcessos = numeroAcessos;
    }

    @Override
    public void run() {
        int ultimoIndice = 0;
        LinkedList<Operacao> retorno = new LinkedList<>();
        //System.out.println( "Criando transacoes e gravando no banco..." );
        Schedule schedule;

        try {
            do {
                ultimoIndice = TransacaoDao.pegarUltimoIndice();
                gerenciador = new GerenciadorTransacao(numeroItens, numeroTransacoes,
                        numeroAcessos, ultimoIndice);
                schedule = new Schedule(gerenciador.getListaTransacoes());
                schedule.addRetorno(retorno);
                retorno = new LinkedList<Operacao>();
                retorno = TransacaoDao.gravarTransacoes(schedule, false);
                //System.out.println( "ok" );
                Thread.sleep(3 * 1000);
                //System.out.println( "ok" );
            } while (flag);
            schedule.setScheduleInList(retorno);
            TransacaoDao.gravarTransacoes(schedule, true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setFlag(boolean state) {
        this.flag = state;
    }

    @Override
    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

}
