package main;

import model.direcionado.Digrafo;
import model.direcionado.VerticeDirecionado;
import model.nao_direcionado.Caminho;
import model.nao_direcionado.GerenciadorCaminhos;
import model.nao_direcionado.Grafo;
import model.nao_direcionado.Vertice;

import java.util.LinkedList;


public class Main {

    public static void main(String[] args) throws Exception{
        Grafo grafo1 = grafoBusca();
        Grafo grafo2 = grafoDistMin();
        Digrafo digrafo = digrafoForteConexo();
        
        
        LinkedList<LinkedList<VerticeDirecionado>> listaComponentes = digrafo.componentesFortementeConexos();

        //for para imprimir cada componente
        for (LinkedList<VerticeDirecionado> componente : listaComponentes) {
            System.out.println(componente);
        }
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

    public static Digrafo digrafoForteConexo() {
        Digrafo digrafo = new Digrafo();

        digrafo.addV("1").addV("2").addV("3").addV("4").addV("5").addV("6").addV("7").addV("8").addV("9").addV("10").addV("11");

        digrafo.addA("1","2").addA("2","3").addA("3","1").addA("3","4")
                .addA("4","3").addA("4","5").addA("4","6").addA("6","5")
                .addA("6","7").addA("5","7").addA("7","8").addA("8","6")
                .addA("8","9").addA("9","10").addA("10","11").addA("11","9");

        return digrafo;
    }
}
