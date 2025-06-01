// --- Estrutura de Dados: Nó para ListaEncadeadaOcorrencias (armazena números de linha) ---
class NoListaOcorrencias {
    int dado; // Número da linha
    NoListaOcorrencias proximo;

    public NoListaOcorrencias(int dado) {
        this.dado = dado;
        this.proximo = null;
    }
}