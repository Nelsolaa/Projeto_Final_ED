// --- Estrutura de Dados: Nó para ListaCustomizadaString (para palavras-chave) ---
class NoListaString {
    String dado;
    NoListaString proximo;

    public NoListaString(String dado) {
        this.dado = dado;
        this.proximo = null;
    }
}