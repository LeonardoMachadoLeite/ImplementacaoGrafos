package model.direcionado;


import exceptions.VerticeJaExisteNoGrafoException;
import exceptions.VerticeNaoEncontradoNoGrafo;

import java.util.LinkedList;
import java.util.TreeMap;

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
}
