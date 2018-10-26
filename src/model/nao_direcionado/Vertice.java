package model.nao_direcionado;


import java.util.LinkedList;
import java.util.Objects;

public class Vertice {

    private final String nome;
    private LinkedList<Aresta> listaAresta;

    public Vertice(String nome) {
        this.nome = nome;
        this.listaAresta = new LinkedList<>();
    }

    public String getNome() {
        return nome;
    }

    public LinkedList<Aresta> getListaAresta() {
        return listaAresta;
    }

    public void setListaAresta(LinkedList<Aresta> listaAresta) {
        this.listaAresta = listaAresta;
    }

    public int grau() {
        return this.listaAresta.size();
    }

    public boolean isAdjacente(Vertice v) {
        for (Aresta i : listaAresta) {
            if (i.contains(v)) {
                return true;
            }
        }
        return false;
    }

    public void addAresta(Aresta aresta) {
        this.listaAresta.addLast(aresta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Objects.equals(nome, vertice.nome);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
