// --- Estrutura de Dados: ListaCustomizadaString (para palavras-chave) ---
class ListaCustomizadaString {
    NoListaString cabeca;
    int contador;

    public ListaCustomizadaString() {
        this.cabeca = null;
        this.contador = 0;
    }

    public void adicionar(String dado) {
        NoListaString novoNo = new NoListaString(dado);
        if (cabeca == null) {
            cabeca = novoNo;
        } else {
            NoListaString atual = cabeca;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
        contador++;
    }

    public String obter(int indice) {
        if (indice < 0 || indice >= contador) {
            // Tratamento de Exceção: Índice fora dos limites
            System.err.println("Erro: Índice da lista de palavras-chave fora dos limites.");
            throw new IndexOutOfBoundsException("Índice: " + indice + ", Tamanho: " + contador);
        }
        NoListaString atual = cabeca;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.dado;
    }

    public void definir(int indice, String dado) {
        if (indice < 0 || indice >= contador) {
            // Tratamento de Exceção: Índice fora dos limites
            System.err.println("Erro: Índice da lista de palavras-chave fora dos limites ao tentar definir valor.");
            throw new IndexOutOfBoundsException("Índice: " + indice + ", Tamanho: " + contador);
        }
        NoListaString atual = cabeca;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        atual.dado = dado;
    }

    public int tamanho() {
        return contador;
    }

    public boolean estaVazia() {
        return contador == 0;
    }
}

