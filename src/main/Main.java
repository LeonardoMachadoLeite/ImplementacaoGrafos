package main;

import model.nao_direcionado.Caminho;
import model.nao_direcionado.GerenciadorCaminhos;
import model.nao_direcionado.Grafo;


public class Main {

    public static void main(String[] args) throws Exception{
        Grafo grafoTeste = grafoBusca();

        grafoTeste.getAdjacentes(grafoTeste.getVertice("V1"));
        boolean ehRegular = grafoTeste.ehRegular();
        boolean ehCompleto = grafoTeste.ehCompleto();
        boolean ehConexo = grafoTeste.ehConexo(grafoTeste.getVertice("V1"));

        Caminho menorCaminho = grafoTeste.algoritmoDijkstra(grafoTeste.getVertice("V1"), grafoTeste.getVertice("V7"));
        GerenciadorCaminhos gc = grafoTeste.algoritmoDijkstra(grafoTeste.getVertice("V1"));
    }

    public static Grafo grafoBusca() {
        Grafo grafoTeste = new Grafo();

        grafoTeste.addV("V1").addV("V2").addV("V3").addV("V4").addV("V5").addV("V6").addV("V7").addV("V8");

        grafoTeste.addA("V1","V2").addA("V1","V3").addA("V2","V4").addA("V2","V5").addA("V3","V4").addA("V4","V6")
                .addA("V6","V7").addA("V7","V8").addA("V6","V8").addA("V4","V5");

        return grafoTeste;
    }

    public static Grafo grafoDistMin() {
        Grafo grafo = new Grafo();

        grafo.addV("t").addV("u").addV("v").addV("x").addV("y");

        grafo.addA("t","x",5).addA("t","u",10).addA("x","y",2)
                .addA("x","u",3).addA("u","v",1).addA("x","v",9)
                .addA("y","v",6);

        return grafo;
    }

    public static Grafo grafoDistMin2() {
        Grafo grafo = new Grafo();

        grafo.addV("A").addV("B").addV("C").addV("D").addV("E").addV("F").addV("G").addV("H");

        grafo.addA("D","H",14).addA("D","E",12).addA("D","A",2)
                .addA("A","H",10).addA("A","B",3).addA("D","B",8)
                .addA("C","G",9).addA("C","B",5).addA("C","E",1)
                .addA("C","F",7).addA("B","H",6).addA("B","G",6)
                .addA("B","E",4).addA("G","E",15).addA("G","H",3)
                .addA("H","F",9).addA("A","C",5);

        return grafo;
    }

}
