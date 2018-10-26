package model.direcionado;

public class ArestaDirecionada {

    private final VerticeDirecionado inicio;
    private final VerticeDirecionado fim;
    private int peso = 1;

    //Construtor
    public ArestaDirecionada(VerticeDirecionado inicio, VerticeDirecionado fim) {
        this.inicio = inicio;
        this.fim = fim;
    }
    public ArestaDirecionada(VerticeDirecionado inicio, VerticeDirecionado fim, int peso) {
        this(inicio, fim);
        this.peso = peso;
    }

    //Getters
    public VerticeDirecionado getInicio() {
        return inicio;
    }

    public VerticeDirecionado getFim() {
        return fim;
    }

    public int getPeso() {
        return peso;
    }

}
