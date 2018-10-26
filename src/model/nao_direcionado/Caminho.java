package model.nao_direcionado;

import exceptions.FimDoCaminhoNaoConectaArestaException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Caminho implements Iterable<Vertice>, Comparable<Caminho>, Cloneable{

    private final Vertice inicio;
    private Vertice fim;
    private int peso = 0;
    private LinkedList<Aresta> caminho;

    //Construtor
    public Caminho(Vertice inicio) {
        this.inicio = inicio;
        this.caminho = new LinkedList<>();
        this.fim = inicio;
    }

    //Getters e Setters
    public Vertice getInicio() {
        return inicio;
    }

    public Vertice getFim() {
        return fim;
    }

    public int getPeso() {
        return peso;
    }

    public LinkedList<Aresta> getCaminho() {
        return caminho;
    }

    //Metodos
    public Caminho add(Aresta aresta) throws FimDoCaminhoNaoConectaArestaException{
        if (!aresta.contains(this.fim)) {
            throw new FimDoCaminhoNaoConectaArestaException();
        }
        this.peso += aresta.getPeso();
        this.caminho.add(aresta);
        if (this.fim.equals(aresta.getV1())) {
            this.fim = aresta.getV2();
        } else {
            this.fim = aresta.getV1();
        }
        return this;
    }

    public Caminho clone() {
        Caminho clone = new Caminho(this.inicio);
        clone.caminho = (LinkedList<Aresta>) this.caminho.clone();
        clone.fim = this.fim;
        clone.peso = this.peso;
        return clone;
    }

    //Retorna a quantidade de arestas no caminho.
    public int getComprimento() {
        return caminho.size();
    }

    public boolean ehSimples() {
        Set<Vertice> verticesVisitados = new HashSet<>();
        for (Vertice i : this) {
            if (!verticesVisitados.add(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean ehCiclo() {
        Set<Vertice> verticesVisitados = new HashSet<>();
        for (Vertice i : this) {
            if (i.equals(fim)) {
                return inicio.equals(fim);
            } else {
                if (!verticesVisitados.add(i)) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Caminho(I : " + inicio + ", F : " + fim + ", " + "P : " + peso + ", " + caminho + ")";
    }

    @Override
    public Iterator<Vertice> iterator() {
        return new Iterator<Vertice>() {

            Iterator<Aresta> itrArestas = caminho.iterator();
            Vertice verticeAtual = inicio;
            boolean first = true;

            @Override
            public boolean hasNext() {
                if (first) {
                    return true;
                } else {
                    return itrArestas.hasNext();
                }
            }

            @Override
            public Vertice next() {
                if (first) {
                    first = false;
                    return verticeAtual;
                } else {
                    Aresta arestaAtual = itrArestas.next();
                    if (arestaAtual.getV1().equals(verticeAtual)) {
                        verticeAtual = arestaAtual.getV2();
                    } else {
                        verticeAtual = arestaAtual.getV1();
                    }
                    return verticeAtual;
                }
            }
        };
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Caminho o) {
        return Integer.compare(this.peso, o.peso);
    }
}
