package exceptions;

public class VerticeNaoTemRelacaoComAresta extends Exception {

    public VerticeNaoTemRelacaoComAresta(String nomeVertice) {
        super(String.format("O vertice %s nao tem relacao com a aresta.",nomeVertice));
    }

}
