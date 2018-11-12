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
            this.raiz = new No(vertice,null);
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

        if (this.raiz.filhos.size() > 1) {
            articulacoes.add(this.raiz.vertice);
        }

        for (No no : this.raiz.filhos) {
            testarSubarvore(articulacoes, new LinkedList<>(), no);
        }

        return articulacoes;
    }

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

    public LinkedList<Vertice> buscaEmProfundidade() {
        return null;
    }

    @Override
    public Iterator<Vertice> iterator() {
        return new Iterator<Vertice>() {



            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Vertice next() {
                return null;
            }
        };
    }

    private class No {

        //Atributos
        private No pai;
        private final Vertice vertice;
        private LinkedList<No> filhos;

        //Construtor
        public No(Vertice vertice, No pai) {
            this.vertice = vertice;
            this.pai = pai;
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
