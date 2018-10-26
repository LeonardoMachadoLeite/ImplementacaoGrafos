package exceptions;

public class FimDoCaminhoNaoConectaArestaException extends Exception{
    public FimDoCaminhoNaoConectaArestaException() {
        super("O fim do caminho nao tem conexao com a aresta passada.");
    }
}
