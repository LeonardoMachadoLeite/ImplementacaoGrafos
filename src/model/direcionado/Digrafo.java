package model.direcionado;


import exceptions.OrdencaoTopologicaException;
import exceptions.VerticeJaExisteNoGrafoException;
import exceptions.VerticeNaoEncontradoNoGrafo;

import java.util.*;

public class Digrafo {

    //Atributos
    private TreeMap<String, VerticeDirecionado> conjuntoVertices;
    private TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>> arvoreDivergencia;
    private TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>> arvoreConvergencia;

    //Construtor
    public Digrafo() {
        this.arvoreConvergencia = new TreeMap<>();
        this.arvoreDivergencia = new TreeMap<>();
        this.conjuntoVertices = new TreeMap<>();
    }
    public Digrafo(Digrafo digrafo) {
        this.conjuntoVertices = (TreeMap<String, VerticeDirecionado>) digrafo.conjuntoVertices.clone();
        this.arvoreDivergencia = (TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>>) digrafo.arvoreDivergencia.clone();
        this.arvoreConvergencia = (TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>>) digrafo.arvoreConvergencia.clone();
    }

    //Getters e Setters
    public TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>> getArvoreDivergencia() {
        return arvoreDivergencia;
    }

    public void setArvoreDivergencia(TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>> arvoreDivergencia) {
        this.arvoreDivergencia = arvoreDivergencia;
    }

    public TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>> getArvoreConvergencia() {
        return arvoreConvergencia;
    }

    public void setArvoreConvergencia(TreeMap<VerticeDirecionado, LinkedList<ArestaDirecionada>> arvoreConvergencia) {
        this.arvoreConvergencia = arvoreConvergencia;
    }

    //Metodos de insercao
    public Digrafo addV(String nome) throws VerticeJaExisteNoGrafoException{
        if (!this.contemVertice(nome)) {
            VerticeDirecionado novoVertice = new VerticeDirecionado(nome);

            this.conjuntoVertices.put(nome,novoVertice);
            this.arvoreDivergencia.put(novoVertice, new LinkedList<>());
            this.arvoreConvergencia.put(novoVertice, new LinkedList<>());

            return this;
        } else{

            throw new VerticeJaExisteNoGrafoException(nome);

        }
    }

    public Digrafo addA(String inicio, String fim) throws VerticeNaoEncontradoNoGrafo {
        return this.addA(inicio, fim,1);
    }

    public Digrafo addA(String inicio, String fim, int peso) throws VerticeNaoEncontradoNoGrafo{

        if (!this.contemVertice(inicio)) {
            throw new VerticeNaoEncontradoNoGrafo(inicio);
        }
        if (!this.contemVertice(fim)) {
            throw new VerticeNaoEncontradoNoGrafo(fim);
        }

        VerticeDirecionado vInicial = this.conjuntoVertices.get(inicio);
        VerticeDirecionado vFinal = this.conjuntoVertices.get(fim);
        ArestaDirecionada novaArestaDirecionada = new ArestaDirecionada(vInicial,vFinal,peso);

        this.arvoreDivergencia.get(vInicial).addLast(novaArestaDirecionada);
        this.arvoreConvergencia.get(vFinal).addLast(novaArestaDirecionada);

        return this;
    }

    //Contem Vertice
    public boolean contemVertice(String nomeVertice) {
        return this.conjuntoVertices.containsKey(nomeVertice);
    }

    //Graus de um vertice
    public int grau(String nomeVertice) throws VerticeNaoEncontradoNoGrafo{
        return this.grauDeSaida(nomeVertice);
    }

    public int grauDeSaida(String nomeVertice) throws VerticeNaoEncontradoNoGrafo{
        if (!this.contemVertice(nomeVertice)) {
            throw new VerticeNaoEncontradoNoGrafo(nomeVertice);
        }
        return this.arvoreDivergencia.get(this.conjuntoVertices.get(nomeVertice)).size();
    }

    public int grauDeEntrada(String nomeVertice) throws VerticeNaoEncontradoNoGrafo{
        if (!this.contemVertice(nomeVertice)) {
            throw new VerticeNaoEncontradoNoGrafo(nomeVertice);
        }
        return this.arvoreConvergencia.get(this.conjuntoVertices.get(nomeVertice)).size();
    }

    //Metodos basicos
    public void removerVertice(String nomeVertice) {
        VerticeDirecionado vertice = conjuntoVertices.get(nomeVertice);

        arvoreConvergencia.remove(vertice);
        arvoreDivergencia.remove(vertice);
        conjuntoVertices.remove(nomeVertice);
    }

    public LinkedList<VerticeDirecionado> getDivergentes(VerticeDirecionado v) {
        LinkedList<VerticeDirecionado> listaDivergentes = new LinkedList<>();
        for (ArestaDirecionada a : this.arvoreDivergencia.get(v)) {
            listaDivergentes.add(v);
        }
        return listaDivergentes;
    }

    public LinkedList<VerticeDirecionado> getConvergentes(VerticeDirecionado v) {
        LinkedList<VerticeDirecionado> listaConvergentes = new LinkedList<>();
        for (ArestaDirecionada a : this.arvoreConvergencia.get(v)) {
            listaConvergentes.add(v);
        }
        return listaConvergentes;
    }

    private VerticeDirecionado getVerticeGrau0() {

        for (VerticeDirecionado i : arvoreConvergencia.keySet()) {

            if (arvoreConvergencia.get(i).size() == 0) {
                return i;
            }

        }

        return null;
    }

    //Ordenação Topológica
    public LinkedList<VerticeDirecionado> ordenacaoTopologica() throws OrdencaoTopologicaException {
        LinkedList<VerticeDirecionado> ordem = new LinkedList<>();

        Digrafo copia = new Digrafo(this);
        VerticeDirecionado grau0 = copia.getVerticeGrau0();

        while (grau0 != null) {

            copia.removerVertice(grau0.getNome());
            ordem.addLast(grau0);
            grau0 = copia.getVerticeGrau0();

        }

        if (copia.conjuntoVertices.size() != 0) {
            throw new OrdencaoTopologicaException("Nao eh possivel ordenar este grafo, pois ele apresenta ciclos.");
        }

        return ordem;
    }

    public LinkedList<VerticeDirecionado> buscaEmProfundidade() {
        LinkedList<VerticeDirecionado> posOrdem = new LinkedList<>();

        TreeSet<VerticeDirecionado> verticesAindaNaoPercorridos = new TreeSet<>(this.conjuntoVertices.values());
        VerticeDirecionado verticeAtual = verticesAindaNaoPercorridos.pollFirst();
        Stack<VerticeDirecionado> pilha = new Stack<>(), pilhaPosOrdem = new Stack<>();
        pilha.push(verticeAtual);
        posOrdem.addLast(verticeAtual);

        while (verticesAindaNaoPercorridos.size() > 0) {

            for (VerticeDirecionado v : getDivergentes(verticeAtual)) {
                if (!verticesAindaNaoPercorridos.contains(v)) {
                    pilha.push(v);
                    pilhaPosOrdem.push(v);
                }
            }

            if (!pilha.empty()) {
                verticeAtual = pilha.pop();
                verticesAindaNaoPercorridos.remove(verticeAtual);
            } else {
                verticeAtual = verticesAindaNaoPercorridos.pollFirst();
            }

        }

        return posOrdem;
    }

    //Componentes Fortemente Conexos
    public LinkedList<LinkedList<VerticeDirecionado>> componentesFortementeConexos() {


        return null;
    }
}
