// --- Estrutura de Dados: Nó para ArvoreBinariaBusca ---
class NoABB {
    Palavra dado; // O nó armazena um objeto do tipo Palavra [cite: 33]
    NoABB esquerda;
    NoABB direita;

    public NoABB(Palavra dado) {
        this.dado = dado;
        this.esquerda = null;
        this.direita = null;
    }
}