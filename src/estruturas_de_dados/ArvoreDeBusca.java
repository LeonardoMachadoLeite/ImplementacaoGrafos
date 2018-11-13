package estruturas_de_dados;

import model.nao_direcionado.Vertice;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

public class ArvoreDeBusca implements Iterable<Vertice>{

    //Atributos
    private No raiz;
    private int size = 0;
    private int peso = 0;
    private TreeMap<Vertice, No> NoDosVertices;

    //Construtor
    public ArvoreDeBusca() {
        this.NoDosVertices = new TreeMap<>();
    }
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
        if (this.raiz == null) {
            this.raiz = new No(vertice,null, new LinkedList<>());
            this.NoDosVertices.put(vertice, this.raiz);
            return this.raiz;
        }
        No novoNo = pai.adicionarFilho(vertice);
        this.NoDosVertices.put(vertice, novoNo);
        return novoNo;
    }

    public No getNo(Vertice v) {
        return this.NoDosVertices.get(v);
    }

    public boolean contains(Vertice v) {
        return this.NoDosVertices.containsKey(v);
    }

    public LinkedList<Vertice> articulacoes() {
        LinkedList<Vertice> articulacoes = new LinkedList<>();

        for (Vertice v : this) {

            if (v.equals(raiz.vertice)) {
                if (this.raiz.filhos.size() > 1) {
                    articulacoes.addLast(raiz.vertice);
                }
                continue;
            } else {

                No noAtual = this.NoDosVertices.get(v);

                if (noAtual.filhos.size() == 0) {
                    continue;
                } else {
                    //No Interno
                    for (Vertice ancestral : noAtual.ancestrais) {
                        for (No raizDaSubarvore : noAtual.filhos) {
                            IteradorProfundidade itr = new IteradorProfundidade(raizDaSubarvore);
                            while (itr.hasNext()) {
                                Vertice verticeDescendente = itr.next();
                                if (verticeDescendente.isAdjacente(ancestral)) {

                                }
                            }
                        }
                    }
                }
            }
        }

        return articulacoes;
    }



/*
    private boolean testarSubarvore(LinkedList<Vertice> articulacoes, LinkedList<Vertice> ancestrais, No raizDaSubarvore) {
        ArvoreDeBusca subarvore = new ArvoreDeBusca(raizDaSubarvore);
        LinkedList<Vertice> ancestraisDaSubarvore = (LinkedList<Vertice>) ancestrais.clone();
        ancestraisDaSubarvore.addLast(raizDaSubarvore.vertice);

        if (raizDaSubarvore.filhos.size() != 0) {
            boolean articulacao = true;
            for (No no : raizDaSubarvore.filhos) {
                if (testarSubarvore(articulacoes,ancestraisDaSubarvore,no)) {
                    articulacao = false;
                    break;
                }
            }
            if (articulacao) {
                articulacoes.addLast(raizDaSubarvore.vertice);

            }
        }

        for (Vertice a : ancestrais) {
            if (a.isAdjacente(raizDaSubarvore.vertice)) {
                return true;
            }
        }
        return false;


    }
*/
    @Override
    public String toString() {
        return this.raiz.toString();
    }

    @Override
    public Iterator<Vertice> iterator() {
        return new IteradorProfundidade(this.raiz);
    }

    private class IteradorProfundidade implements Iterator<Vertice>{

        No proxNo;
        PilhaEncadeada<No> P;

        private IteradorProfundidade(No noInicial) {
            this.P = new PilhaEncadeada<>();
            this.P.empilhar(noInicial);
        }

        @Override
        public boolean hasNext() {
            return !P.vazia();
        }

        @Override
        public Vertice next() {
            proxNo = P.desempilhar();
            for (No i : proxNo.filhos) {
                P.empilhar(i);
            }
            return proxNo.vertice;
        }
    }

    private class No {

        //Atributos
        private No pai;
        private final Vertice vertice;
        private LinkedList<No> filhos;
        private LinkedList<Vertice> ancestrais;

        //Construtor
        public No(Vertice vertice, No pai, LinkedList<Vertice> ancestrais) {
            this.vertice = vertice;
            this.pai = pai;
            this.filhos = new LinkedList<>();
            this.ancestrais = ancestrais;
        }

        //Getters
        public No getPai() {
            return this.pai;
        }

        public No getFilho(String nomeVertice) {
            for (No i : filhos) {
                if (i.vertice.toString().equals(nomeVertice)) {
                    return i;
                }
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
            No novoNo;
            if (this.pai == null) {
                novoNo = new No(vertice, this, new LinkedList<>());
            } else {
                LinkedList<Vertice> ancestrais = (LinkedList<Vertice>) this.ancestrais.clone();
                ancestrais.addLast(this.pai.vertice);
                novoNo = new No(vertice, this, ancestrais);
            }
            this.filhos.addLast(novoNo);
            return novoNo;
        }

        @Override
        public String toString() {
            if (this.ehFolha()) {
                return this.vertice.toString();
            } else {
                return String.format("%s{%s}",this.vertice.toString(),this.filhos);
            }
        }
    }

}
