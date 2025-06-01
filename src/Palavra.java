// --- Estrutura de Dados: Classe Palavra ---
class Palavra implements Comparable<Palavra> {
    String termo; // A palavra em si [cite: 30]
    ListaEncadeadaOcorrencias ocorrencias; // Lista de linhas onde a palavra ocorre [cite: 30]

    public Palavra(String termo) {
        this.termo = termo;
        this.ocorrencias = new ListaEncadeadaOcorrencias();
    }

    public void adicionarOcorrencia(int numeroLinha) {
        this.ocorrencias.adicionar(numeroLinha);
    }

    public String getTermo() {
        return termo;
    }

    public ListaEncadeadaOcorrencias getOcorrencias() {
        return ocorrencias;
    }

    @Override
    public int compareTo(Palavra outra) {
        return this.termo.compareTo(outra.termo);
    }

    @Override
    public String toString() {
        return termo + " " + ocorrencias.toString();
    }
}