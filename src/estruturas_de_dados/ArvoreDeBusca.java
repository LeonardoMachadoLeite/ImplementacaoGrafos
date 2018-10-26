package estruturas_de_dados;

import model.nao_direcionado.Vertice;

import java.util.LinkedList;

public class ArvoreDeBusca {

    //Atributos
    private No raiz;
    private int size = 0;
    private int peso = 0;

    //Construtor
    public ArvoreDeBusca() {}
    public ArvoreDeBusca(No raiz) {
        this.raiz = raiz;
    }

    //Getters
    public No getRaiz() {
        return raiz;
    }

    public int getSize() {
        return size;
    }

    public int getPeso() {
        return peso;
    }

    //Metodos
    public No adicionarFilho(Vertice vertice, No pai) {
        return pai.adicionarFilho(vertice);
    }

    private class No {

        //Atributos
        private No pai;
        private final Vertice vertice;
        private LinkedList<No> filhos;

        //Construtor
        public No(Vertice vertice, No pai) {
            this.vertice = vertice;
            this.filhos = new LinkedList<>();
        }

        //Getters
        public No getPai() {
            return this.pai;
        }

        public No getFilho(String nomeVertice) {
            for (No i : filhos) {
                
            }
            return null;
        }

        //Metodos
        public boolean ehFolha() {
            return this.filhos.size() == 0;
        }

        public boolean ehRaiz() {
            return this.pai == null;
        }

        public No adicionarFilho(Vertice vertice) {
            No novoNo = new No(vertice, this);
            this.filhos.addLast(novoNo);
            return novoNo;
        }
    }

}
