package exceptions;

public class VerticeJaExisteNoGrafoException extends RuntimeException{
    public VerticeJaExisteNoGrafoException(String nomeDoVertice) {
        super(String.format("O vertice %s ja foi cadastrado no grafo. Escolha um nome diferente.", nomeDoVertice));
    }
}
