// --- Estrutura de Dados: TabelaHash ---
class TabelaHash {
    private ArvoreBinariaBusca[] tabela; // Cada compartimento da Hash armazena uma ABB [cite: 32, 33]
    private int capacidade; // Tamanho da tabela (ex: 26 para 'a'-'z')

    public TabelaHash(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new ArvoreBinariaBusca[capacidade];
        // Inicializa cada posição da tabela explicitamente, se necessário,
        // mas o padrão para objetos é null.
    }

    // Função hash simples baseada na primeira letra da palavra [cite: 31]
    private int funcaoHash(String palavra) {
        if (palavra == null || palavra.isEmpty()) {
            return 0; // Ou lançar erro, ou tratar como caso especial
        }
        char primeiroChar = Character.toLowerCase(palavra.charAt(0));
        if (primeiroChar >= 'a' && primeiroChar <= 'z') {
            return primeiroChar - 'a';
        }
        // Fallback para não alfabéticos (ex: último bucket)
        // Ou pode-se aumentar a tabela e usar aritmética modular.
        return capacidade -1;
    }

    public void inserir(String termoPalavra, int numeroLinha) {
        if (termoPalavra == null || termoPalavra.isEmpty()) {
            return;
        }

        int indice = funcaoHash(termoPalavra);

        if (tabela[indice] == null) {
            tabela[indice] = new ArvoreBinariaBusca();
        }

        Palavra palavraExistente = tabela[indice].buscar(termoPalavra);
        if (palavraExistente != null) {
            palavraExistente.adicionarOcorrencia(numeroLinha); // Adiciona ocorrência à palavra existente [cite: 36]
        } else {
            Palavra novaPalavra = new Palavra(termoPalavra); // Cria novo nó para a palavra [cite: 35]
            novaPalavra.adicionarOcorrencia(numeroLinha); // Adiciona o índice (número da linha) [cite: 35]
            tabela[indice].inserir(novaPalavra);
        }
    }

    public Palavra obter(String termoPalavra) {
        if (termoPalavra == null || termoPalavra.isEmpty()) {
            return null;
        }
        int indice = funcaoHash(termoPalavra);
        if (tabela[indice] == null) {
            return null;
        }
        return tabela[indice].buscar(termoPalavra);
    }
}
