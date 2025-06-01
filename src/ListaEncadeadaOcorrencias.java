// --- Estrutura de Dados: ListaEncadeadaOcorrencias (para números de linha) ---
class ListaEncadeadaOcorrencias {
    NoListaOcorrencias cabeca;

    // Adiciona um número de linha em ordem crescente, evitando duplicatas.
    public void adicionar(int numeroLinha) {
        NoListaOcorrencias novoNo = new NoListaOcorrencias(numeroLinha);
        if (cabeca == null || cabeca.dado > numeroLinha) {
            novoNo.proximo = cabeca;
            cabeca = novoNo;
        } else if (cabeca.dado == numeroLinha) {
            return; // Duplicata
        } else {
            NoListaOcorrencias atual = cabeca;
            while (atual.proximo != null && atual.proximo.dado < numeroLinha) {
                atual = atual.proximo;
            }
            if (atual.proximo != null && atual.proximo.dado == numeroLinha) {
                return; // Duplicata
            }
            novoNo.proximo = atual.proximo;
            atual.proximo = novoNo;
        }
    }

    public boolean estaVazia() {
        return cabeca == null;
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        NoListaOcorrencias atual = cabeca;
        while (atual != null) {
            sb.append(atual.dado);
            if (atual.proximo != null) {
                sb.append(" ");
            }
            atual = atual.proximo;
        }
        return sb.toString();
    }
}
