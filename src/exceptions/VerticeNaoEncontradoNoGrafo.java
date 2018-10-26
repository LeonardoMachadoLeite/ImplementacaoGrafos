package exceptions;

public class VerticeNaoEncontradoNoGrafo extends RuntimeException{
    public VerticeNaoEncontradoNoGrafo(String nomeDoVertice) {
        super(String.format("O vertice %s nao foi encontrado no grafo.", nomeDoVertice));
    }
}
