package DAO;

public enum Acesso {
    READ("R"),
    WRITE("W"),
    BEGIN("B"),
    END("E"),
    COMMIT("C"),
    ABORT("A");
    String texto;

    private Acesso(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}
