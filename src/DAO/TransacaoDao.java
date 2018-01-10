package DAO;

import DAO.MinhaConexao;
import Beans.Schedule;
import Beans.Operacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;

public class TransacaoDao {

    private static MinhaConexao minhaConexao;

    public TransacaoDao() {
        minhaConexao = new MinhaConexao();
        MinhaConexao.getCabecalho();
    }

    public static LinkedList<Operacao> gravarTransacoes(Schedule schedule , boolean ultimo) {
        Operacao operacao = null;

        Connection conn = minhaConexao.getConnection();
        String sql = "INSERT INTO schedule(indiceTransacao, operacao, itemDado, timestampj) VALUES (?, ?, ?, ?)";
        
        
        PreparedStatement stmt = null;
        
        int randomico = (ultimo)? schedule.getScheduleInList().size() :new Random().nextInt(schedule.getScheduleInList().size());
        if (randomico < (schedule.getScheduleInList().size() / 2)) {
            randomico += (schedule.getScheduleInList().size() / 2);
        }
        
        while ((!schedule.getScheduleInList().isEmpty()) && (randomico > 0)) {
            operacao = schedule.getScheduleInList().removeFirst();
            try {
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, operacao.getIndice());
                stmt.setString(2, operacao.getAcesso().toString());
                stmt.setString(3, operacao.getDado().getNome());
                stmt.setString(4, new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
                stmt.executeUpdate();
                randomico--;
            } catch (SQLException e) {
                System.out.println("Erro na insercao da transacao");
                e.printStackTrace();
            }
        }
        try {
            minhaConexao.releaseAll(stmt, conn);
        } catch (SQLException e) {
            System.out.println("Erro ao encerrar conexo");
            e.printStackTrace();
        }
        return schedule.getScheduleInList();
    }

    public static int pegarUltimoIndice() {
        int ultimoIndice = 0;
        minhaConexao = new MinhaConexao();
        Connection conn = minhaConexao.getConnection();
        String sql = "SELECT MAX(indiceTransacao) FROM schedule;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            ultimoIndice = rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("Erro na consulta ao ultimo indice");
            e.printStackTrace();
        }
        return ultimoIndice;
    }

}
