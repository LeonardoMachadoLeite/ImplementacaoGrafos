package model.nao_direcionado;

import estruturas_de_dados.ArvoreDeBusca;
import estruturas_de_dados.FilaEncadeada;
import estruturas_de_dados.FilaVaziaException;
import estruturas_de_dados.PilhaEncadeada;
import exceptions.FimDoCaminhoNaoConectaArestaException;
import exceptions.VerticeNaoEncontradoNoGrafo;

import java.util.*;

public class Grafo {

    protected HashMap<String,Vertice> conjuntoVertices;
    protected LinkedList<Aresta> conjuntoArestas;

    public Grafo() {
        this.conjuntoArestas = new LinkedList<>();
        this.conjuntoVertices = new HashMap<>();
    }

    //Getters e Setters
    public HashMap<String,Vertice> getConjuntoVertices() {
        return conjuntoVertices;
    }

    public void setConjuntoVertices(HashMap<String,Vertice> conjuntoVertices) {
        this.conjuntoVertices = conjuntoVertices;
    }

    public LinkedList<Aresta> getConjuntoArestas() {
        return conjuntoArestas;
    }

    public void setConjuntoArestas(LinkedList<Aresta> conjuntoArestas) {
        this.conjuntoArestas = conjuntoArestas;
    }

    public LinkedList<Vertice> getAdjacentes(Vertice v) {
        LinkedList<Vertice> adjacentes = new LinkedList<>();

        for (Vertice i : this.conjuntoVertices.values()) {
            if (!i.equals(v)) {
                if (v.isAdjacente(i)) {
                    adjacentes.addLast(i);
                }
            }
        }

        return adjacentes;
    }

    public Vertice getVertice(String identificadorVertice) {
        if (!this.conjuntoVertices.containsKey(identificadorVertice)) {
            throw new VerticeNaoEncontradoNoGrafo(identificadorVertice);
        }
        return this.conjuntoVertices.get(identificadorVertice);
    }


    //Metodos de insercao
    public boolean addVertice(Vertice v) {
        if (this.conjuntoVertices.containsKey(v.getNome())) {
            return false;
        }
        this.conjuntoVertices.put(v.getNome(),v);
        return true;
    }

    public boolean addAresta(Aresta novaAresta) {
        if (this.conjuntoVertices.containsKey(novaAresta.getV1().getNome()) && this.conjuntoVertices.containsKey(novaAresta.getV2().getNome())) {
            this.conjuntoVertices.get(novaAresta.getV1().getNome()).addAresta(novaAresta);
            this.conjuntoVertices.get(novaAresta.getV2().getNome()).addAresta(novaAresta);
            this.conjuntoArestas.addLast(novaAresta);
            return true;
        }
        return false;
    }

    public Grafo addV(String nomeVertice) {
        this.addVertice(new Vertice(nomeVertice));
        return this;
    }

    public Grafo addA(String nomeV1, String nomeV2, int peso) {
        Vertice V1 = getVertice(nomeV1), V2 = getVertice(nomeV2);
        Aresta novaAresta = new Aresta(V1,V2,peso);
        V1.addAresta(novaAresta);
        V2.addAresta(novaAresta);
        this.conjuntoArestas.add(novaAresta);
        return this;
    }

    public Grafo addA(String nomeV1, String nomeV2) {
        return this.addA(nomeV1, nomeV2, 1);
    }

    //Metodos Basicos
    public boolean ehRegular() {
        int k = conjuntoVertices.values().iterator().next().grau();
        for (Vertice i : conjuntoVertices.values()) {
            if (i.grau() != k) {
                return false;
            }
        }
        return true;
    }

    public boolean ehCompleto() {
        int n = conjuntoVertices.size(); //Numero de vertices
        if ( conjuntoArestas.size() < ((n*(n-3)/2) + n) ) {
            return false;
        }
        for (Vertice i : conjuntoVertices.values()) {
            if (getAdjacentes(i).size() != conjuntoVertices.size()) {
                return false;
            }
        }
        return true;
    }

    public boolean ehConexo(Vertice v) {
        ArrayList<Vertice> verticesVisitados = new ArrayList<>();
        PilhaEncadeada<Vertice> P = new PilhaEncadeada<>();
        verticesVisitados.add(v);
        P.empilhar(v);

        while (!P.vazia()) {

            Iterator<Vertice> adjacentes = getAdjacentes(P.desempilhar()).iterator();
            while (adjacentes.hasNext()) {
                Vertice w = adjacentes.next();
                if (!verticesVisitados.contains(w)) {
                    verticesVisitados.add(w);
                    P.empilhar(w);
                }
            }

        }

        if (verticesVisitados.size() != this.conjuntoVertices.size()) {
            return false;
        } else {
            return true;
        }
    }

    //Ordena os vertices em distancia do vertice inicial. Assim, os mais próximos estarão no início.
    public LinkedList<Vertice> ordenarVertices(Vertice v) {
        FilaEncadeada<Vertice> F = new FilaEncadeada<>();
        LinkedList<Vertice> verticesOrdenados = new LinkedList<>();
        verticesOrdenados.addFirst(v);
        F.enfileirar(v);

        try {
            while (true) {
                Vertice u = F.desenfileirar();


                Iterator<Vertice> adjacentes = getAdjacentes(u).iterator();

                while (adjacentes.hasNext()) {
                    Vertice w = adjacentes.next();
                    if (!verticesOrdenados.contains(w)) {
                        verticesOrdenados.addFirst(w);
                        F.enfileirar(w);
                    }
                }

            }
        } catch (FilaVaziaException e) {}
        return verticesOrdenados;
    }

    public ArvoreDeBusca buscaEmProfundidade(Vertice v) {
        ArvoreDeBusca arvoreDeBusca = new ArvoreDeBusca();
        PilhaEncadeada<Vertice> P = new PilhaEncadeada<>();
        arvoreDeBusca.adicionarFilho(v,null);
        P.empilhar(v);

        while (!P.vazia()) {
            Vertice topo = P.desempilhar();
            Iterator<Vertice> adjacentes = getAdjacentes(topo).iterator();
            while (adjacentes.hasNext()) {
                Vertice w = adjacentes.next();
                if (!arvoreDeBusca.contains(w)) {
                    arvoreDeBusca.adicionarFilho(w, arvoreDeBusca.getNo(topo));
                    P.empilhar(w);
                }
            }

        }

        return arvoreDeBusca;
    }

    //Metodos de Arvore Minima(Falta substituir tree map por uma arvore personalizada)
    public TreeMap<String,Vertice> algoritmoDePrim(Vertice v) {
        TreeMap<String,Vertice> arvoreMinima = new TreeMap<>();
        HashMap<String,Vertice> componente = new HashMap<>();
        PriorityQueue<Aresta> arestasAdjacentes = new PriorityQueue<>(new Comparator<Aresta>() {
            @Override
            public int compare(Aresta o1, Aresta o2) {
                return o2.getPeso() - o1.getPeso();
            }
        });

        addVerticeAoComponentePrim(componente,arestasAdjacentes,v);
        arvoreMinima.put(v.getNome(), v);

        while (componente.size() < this.conjuntoVertices.size()) {

            Aresta arestaDeMenorPeso = arestasAdjacentes.poll();

            Vertice V1 = arestaDeMenorPeso.getV1();
            Vertice V2 = arestaDeMenorPeso.getV2();

            if (!componente.containsKey(V1.getNome())) {
                addVerticeAoComponentePrim(componente,arestasAdjacentes,V1);
                arvoreMinima.put(V1.getNome(), V1);
            } else if (!componente.containsKey(V2.getNome())) {
                addVerticeAoComponentePrim(componente,arestasAdjacentes,V2);
                arvoreMinima.put(V2.getNome(), V2);
            }


            if (arestasAdjacentes.isEmpty()) {
                break;
            }
        }

        return arvoreMinima;
    }

    private void addVerticeAoComponentePrim(HashMap<String,Vertice> componente,
                                            PriorityQueue<Aresta> arestasAdjacentes, Vertice v) {

        componente.put(v.getNome(), v);
        Iterator<Aresta> itr = v.getListaAresta().iterator();

        while (itr.hasNext()) {
            Aresta arestaAdjacente = itr.next();
            if (!(componente.containsKey(arestaAdjacente.getV1().getNome()) &&
                    componente.containsKey(arestaAdjacente.getV2().getNome()))) {
                arestasAdjacentes.add(arestaAdjacente);
            }
        }
    }

    public Caminho algoritmoDijkstra(Vertice verticeInicial, Vertice verticeFinal) {

        GerenciadorCaminhos gerenciadorCaminhos = new GerenciadorCaminhos(verticeInicial);
        PriorityQueue<Caminho> proxsCaminhos = new PriorityQueue<>();
        HashMap<String,Vertice> verticesJaVisitados = new HashMap<>();
        Caminho caminhoAtual = new Caminho(verticeInicial);
        gerenciadorCaminhos.adicionarCaminho(caminhoAtual);
        adicionarRotas(proxsCaminhos, caminhoAtual, gerenciadorCaminhos, verticesJaVisitados);

        while (!verticesJaVisitados.containsKey(verticeFinal.getNome())) {

            caminhoAtual = proxsCaminhos.poll();
            System.out.println("Caminho atual: " + caminhoAtual);
            verticesJaVisitados.put(caminhoAtual.getFim().getNome(), caminhoAtual.getFim());
            adicionarRotas(proxsCaminhos,caminhoAtual,gerenciadorCaminhos,verticesJaVisitados);
            System.out.println("\nVertices ja visitados: " + verticesJaVisitados);

        }

        return gerenciadorCaminhos.getCaminhoMinimo(verticeFinal);
    }

    public GerenciadorCaminhos algoritmoDijkstra(Vertice verticeInicial) {

        GerenciadorCaminhos gerenciadorCaminhos = new GerenciadorCaminhos(verticeInicial);
        PriorityQueue<Caminho> proxsCaminhos = new PriorityQueue<>();
        HashMap<String,Vertice> verticesJaVisitados = new HashMap<>();
        Caminho caminhoAtual = new Caminho(verticeInicial);
        gerenciadorCaminhos.adicionarCaminho(caminhoAtual);
        adicionarRotas(proxsCaminhos, caminhoAtual, gerenciadorCaminhos, verticesJaVisitados);

        Iterator<Vertice> itrVertices = this.conjuntoVertices.values().iterator();
        Vertice verticeJaTemCaminho = itrVertices.next();

        while (true) {

            caminhoAtual = proxsCaminhos.poll();
            System.out.println("Caminho atual: " + caminhoAtual);
            verticesJaVisitados.put(caminhoAtual.getFim().getNome(), caminhoAtual.getFim());
            adicionarRotas(proxsCaminhos,caminhoAtual,gerenciadorCaminhos,verticesJaVisitados);
            System.out.println("\nVertices ja visitados: " + verticesJaVisitados);

            while (verticesJaVisitados.containsKey(verticeJaTemCaminho.getNome())) {
                if (itrVertices.hasNext()) {
                    verticeJaTemCaminho = itrVertices.next();
                } else {
                    return gerenciadorCaminhos;
                }
            }

        }

    }

    private void adicionarRotas(PriorityQueue<Caminho> proxsCaminho, Caminho caminhoAtual, GerenciadorCaminhos gerenciadorCaminhos, HashMap<String,Vertice> verticesJaVisitados) {
        Vertice verticeAtual = caminhoAtual.getFim();
        LinkedList<Aresta> listaAdjacentes = verticeAtual.getListaAresta();

        for (Aresta i : listaAdjacentes) {
            try {

                Caminho novoCaminho = caminhoAtual.clone().add(i);
                proxsCaminho.add(novoCaminho);
                gerenciadorCaminhos.adicionarCaminho(novoCaminho);

            } catch (FimDoCaminhoNaoConectaArestaException e) {
                e.printStackTrace();
            }
        }

    }

    public LinkedList<Vertice> buscaDeArticulacoes() {
        Vertice verticeInicial = this.conjuntoVertices.values().iterator().next();
        ArvoreDeBusca arvoreDeBusca = this.buscaEmProfundidade(verticeInicial);
        System.out.println(arvoreDeBusca);
        return arvoreDeBusca.articulacoes();
    }
}
