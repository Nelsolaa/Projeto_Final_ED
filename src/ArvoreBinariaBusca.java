// --- Estrutura de Dados: ArvoreBinariaBusca (ABB) ---
class ArvoreBinariaBusca {
    NoABB raiz;

    public void inserir(Palavra palavra) {
        raiz = inserirRecursivo(raiz, palavra);
    }

    private NoABB inserirRecursivo(NoABB atual, Palavra palavra) {
        if (atual == null) {
            return new NoABB(palavra);
        }

        int comparacao = palavra.compareTo(atual.dado);

        if (comparacao < 0) {
            atual.esquerda = inserirRecursivo(atual.esquerda, palavra);
        } else if (comparacao > 0) {
            atual.direita = inserirRecursivo(atual.direita, palavra);
        }
        // Se comparacao == 0, a palavra já existe. A lógica de adicionar ocorrência
        // deve ser tratada antes de tentar inserir um objeto Palavra duplicado.
        return atual;
    }

    public Palavra buscar(String valorPalavra) {
        return buscarRecursivo(raiz, valorPalavra);
    }

    private Palavra buscarRecursivo(NoABB atual, String valorPalavra) {
        if (atual == null) {
            return null;
        }
        int comparacao = valorPalavra.compareTo(atual.dado.getTermo());

        if (comparacao == 0) {
            return atual.dado;
        } else if (comparacao < 0) {
            return buscarRecursivo(atual.esquerda, valorPalavra);
        } else {
            return buscarRecursivo(atual.direita, valorPalavra);
        }
    }
}
