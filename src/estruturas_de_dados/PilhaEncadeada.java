/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estruturas_de_dados;

import java.util.Iterator;

/**
 *
 * @author Efraim
 */
public class PilhaEncadeada<T> implements Iterable<T> {
    private int size = 0;
    private NoSimples<T> noInicial;

    public void empilhar(T elemento) {
        this.noInicial = new NoSimples(elemento, this.noInicial);
        size++;
    }

    public T desempilhar() {
        if (this.noInicial == null) {
            return null;
        }
        T ans = this.noInicial.getElemento();
        this.noInicial = this.noInicial.getProxNo();
        size--;
        return ans;
    }

    public T topo() {
        return this.noInicial.getElemento();
    }

    public int tamanho() {
        return this.size;
    }

    public boolean contem(T elemento) {
        for (T i : this) {
            if (i.equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    public void limpar() {
        this.noInicial = null;
        this.size = 0;
    }

    public boolean vazia() {
        return this.size == 0;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
    private class NoSimples<T> {
        private T elemento;
        private NoSimples proxNo;
        public NoSimples(T elemento) {
            this.elemento = elemento;
        }
        public NoSimples(T elemento, NoSimples proxNo) {
            this(elemento);
            this.proxNo = proxNo;
        }
        public T getElemento() {
            return this.elemento;
        }
        public NoSimples getProxNo() {
            return this.proxNo;
        }
    }
    private class Iterador implements Iterator<T> {
        NoSimples<T> noAtual = noInicial;
        boolean first = true;
        @Override
        public boolean hasNext() {
            if (first) {
                return noAtual != null;
            }
            return noAtual.getProxNo() != null;
        }
        @Override
        public T next() {
            if (first) {
                first = false;
                return noAtual.getElemento();
            } else {
                noAtual = noAtual.getProxNo();
                return noAtual.getElemento();
            }
        }
        
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        boolean first = true;
        ans.append("[");
        for (T i : this) {
            if (!first) {
                ans.append(", ");
            } else {
                first = false;
            }
            ans.append(i.toString());
        }
        ans.append("]");
        return ans.toString();
    }
}
