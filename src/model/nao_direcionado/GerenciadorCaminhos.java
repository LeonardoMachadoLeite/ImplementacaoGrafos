package model.nao_direcionado;

import java.util.Collection;
import java.util.HashMap;

//Classe que gerenciará todos os caminhos já conhecidos
public class GerenciadorCaminhos {

    private final Vertice verticeInicial;
    private HashMap<Vertice,Caminho> conjuntoCaminhos;

    public GerenciadorCaminhos(Vertice verticeInicial) {
        this.verticeInicial = verticeInicial;
        this.conjuntoCaminhos = new HashMap<>();
    }

    public Vertice getVerticeInicial() {
        return verticeInicial;
    }

    //Retorna verdadeiro se o novo caminho substituir o já registrado
    public boolean adicionarCaminho(Caminho novoCaminho) {

        //Se o caminho já registrado for o inicial ou se possuir peso maior que o novo caminho
        System.out.println();
        System.out.println("Novo caminho : " + novoCaminho);

        if (conjuntoCaminhos.get(novoCaminho.getFim()) == null || novoCaminho.compareTo(conjuntoCaminhos.get(novoCaminho.getFim())) < 0) {
            System.out.println("Replacing " + conjuntoCaminhos.get(novoCaminho.getFim()) + " for " + novoCaminho);
            conjuntoCaminhos.put(novoCaminho.getFim(), novoCaminho);
            return true;
        }

        return false;
    }

    public Caminho getCaminhoMinimo(Vertice verticeFinal) {
        return conjuntoCaminhos.get(verticeFinal);
    }

    public Collection<Caminho> getTodosCaminhos() {
        return conjuntoCaminhos.values();
    }

}
