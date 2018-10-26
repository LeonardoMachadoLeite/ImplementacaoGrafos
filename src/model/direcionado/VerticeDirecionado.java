package model.direcionado;

public class VerticeDirecionado {

    //Atributos
    private final String nome;

    //Construtor
    public VerticeDirecionado(String nome) {
        this.nome = nome;
    }

    //Getters
    public String getNome() {
        return nome;
    }

    //Equals e Hashcode
    @Override
    public int hashCode() {
        return this.nome.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VerticeDirecionado) {
            if (((VerticeDirecionado) o).nome.equals(this.nome)) {
                return true;
            }
        }
        return false;
    }
}
