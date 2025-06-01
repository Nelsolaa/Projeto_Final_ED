// Nenhuma nova importação necessária para esta abordagem manual de remoção de acentos
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Define o pacote se você for usar um. Ex: package seu.pacote.aqui;

public class MotorIndiceRemissivo {

    private String arquivoPalavrasChave;
    private String arquivoTexto;
    private String arquivoSaida;

    public MotorIndiceRemissivo(String arquivoPalavrasChave, String arquivoTexto, String arquivoSaida) {
        this.arquivoPalavrasChave = arquivoPalavrasChave;
        this.arquivoTexto = arquivoTexto;
        this.arquivoSaida = arquivoSaida;
    }

    public void executar() {
        try {
            System.out.println("Lendo palavras-chave de: " + this.arquivoPalavrasChave);
            ListaCustomizadaString palavrasChave = lerPalavrasChaveDeArquivo(this.arquivoPalavrasChave);
            if (palavrasChave.estaVazia()) {
                System.out.println("Nenhuma palavra-chave encontrada. A saída estará vazia.");
            }

            System.out.println("Criando Tabela Hash...");
            TabelaHash tabelaDePalavras = new TabelaHash(26); // Capacidade para 'a'-'z'

            System.out.println("Processando arquivo de texto: " + this.arquivoTexto);
            processarArquivoTexto(this.arquivoTexto, tabelaDePalavras);

            System.out.println("Gerando índice remissivo para: " + this.arquivoSaida);
            gerarSaidaIndice(this.arquivoSaida, palavrasChave, tabelaDePalavras);

            System.out.println("Índice remissivo gerado com sucesso em '" + this.arquivoSaida + "'!");

        } catch (IOException e) {
            System.err.println("ERRO DE E/S: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("ERRO DE ÍNDICE: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRO INESPERADO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ListaCustomizadaString lerPalavrasChaveDeArquivo(String caminhoArquivo) throws IOException {
        ListaCustomizadaString palavrasChave = new ListaCustomizadaString();
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            System.out.println("Lendo palavras-chave do arquivo: " + caminhoArquivo);
            while ((linha = leitor.readLine()) != null) {
                // Limpa a palavra-chave aqui também, para consistência
                String palavraLida = limparPalavra(linha.trim());
                if (!palavraLida.isEmpty()) {
                    palavrasChave.adicionar(palavraLida);
                    System.out.println("  Palavra-chave adicionada (após limpar): " + palavraLida);
                }
            }
        }
        return palavrasChave;
    }

    private String limparPalavra(String tokenBruto) {
        if (tokenBruto == null || tokenBruto.isEmpty()) {
            return "";
        }
        String limpa = tokenBruto.toLowerCase(); // 1. Converter para minúsculas PRIMEIRO

        // 2. Substituição manual de acentos e caracteres especiais comuns em português
        limpa = limpa.replace('á', 'a');
        limpa = limpa.replace('à', 'a');
        limpa = limpa.replace('ã', 'a');
        limpa = limpa.replace('â', 'a');
        limpa = limpa.replace('ä', 'a'); // Para consistência, caso apareça

        limpa = limpa.replace('é', 'e');
        limpa = limpa.replace('è', 'e');
        limpa = limpa.replace('ê', 'e');
        limpa = limpa.replace('ë', 'e');

        limpa = limpa.replace('í', 'i');
        limpa = limpa.replace('ì', 'i');
        limpa = limpa.replace('î', 'i');
        limpa = limpa.replace('ï', 'i');

        limpa = limpa.replace('ó', 'o');
        limpa = limpa.replace('ò', 'o');
        limpa = limpa.replace('õ', 'o');
        limpa = limpa.replace('ô', 'o');
        limpa = limpa.replace('ö', 'o');

        limpa = limpa.replace('ú', 'u');
        limpa = limpa.replace('ù', 'u');
        limpa = limpa.replace('û', 'u');
        limpa = limpa.replace('ü', 'u');

        limpa = limpa.replace('ç', 'c');

        // 3. Remover pontuações comuns no final da palavra
        // Esta lógica pode ser melhorada para lidar com múltiplas pontuações ou pontuações no início.
        // Por ora, mantém a remoção simples no final.
        if (!limpa.isEmpty() && (limpa.endsWith(",") || limpa.endsWith(".") || limpa.endsWith(";") ||
                limpa.endsWith(":") || limpa.endsWith("!") || limpa.endsWith("?"))) {
            limpa = limpa.substring(0, limpa.length() - 1);
        }
        // Repetir para caso de dupla pontuação, ex: "palavra!." (simplificado)
        if (!limpa.isEmpty() && (limpa.endsWith(",") || limpa.endsWith(".") || limpa.endsWith(";") ||
                limpa.endsWith(":") || limpa.endsWith("!") || limpa.endsWith("?"))) {
            limpa = limpa.substring(0, limpa.length() - 1);
        }


        // 4. Opcional: Remover quaisquer outros caracteres que não sejam letras ou números
        //    Se a tokenização inicial com split("[^a-zA-Z0-9\\-]+") for boa,
        //    esta etapa pode não ser necessária ou pode ser afinada.
        //    Exemplo para manter apenas letras e números (e hífen se desejado):
        //    StringBuilder sb = new StringBuilder();
        //    for (char c : limpa.toCharArray()) {
        //        if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '-') {
        //            sb.append(c);
        //        }
        //    }
        //    limpa = sb.toString();

        return limpa;
    }

    private void processarArquivoTexto(String caminhoArquivo, TabelaHash tabelaHash) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int numeroLinha = 1;
            System.out.println("Processando texto do arquivo: " + caminhoArquivo);
            while ((linha = leitor.readLine()) != null) {
                // A tokenização aqui é crucial.
                // O split("[^a-zA-Z0-9\\-]+") vai separar por qualquer coisa que NÃO seja letra, número ou hífen.
                // Letras acentuadas serão separadores se não forem tratadas ANTES do split.
                // Para a abordagem manual, a limpeza (incluindo remoção de acentos) ocorre APÓS o split.
                String[] tokensBrutos = linha.split("[^a-zA-Z0-9\\-\u00C0-\u00FF]+"); // Regex ajustado para tentar manter palavras com acentos como tokens

                for (String tokenBruto : tokensBrutos) {
                    String palavra = limparPalavra(tokenBruto); // Limpeza ocorre aqui
                    if (!palavra.isEmpty()) {
                        // System.out.println("  Processando palavra: '" + tokenBruto + "' -> '" + palavra + "' na linha " + numeroLinha);
                        tabelaHash.inserir(palavra, numeroLinha);
                    }
                }
                numeroLinha++;
            }
        }
    }

    private void ordenarListaStringBolha(ListaCustomizadaString lista) {
        if (lista == null || lista.tamanho() < 2) return;
        int n = lista.tamanho();
        boolean trocou;
        for (int i = 0; i < n - 1; i++) {
            trocou = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.obter(j).compareTo(lista.obter(j + 1)) > 0) {
                    String temp = lista.obter(j);
                    lista.definir(j, lista.obter(j + 1));
                    lista.definir(j + 1, temp);
                    trocou = true;
                }
            }
            if (!trocou) break;
        }
    }

    private void gerarSaidaIndice(String caminhoArquivoSaida, ListaCustomizadaString palavrasChave, TabelaHash tabelaHash) throws IOException {
        ordenarListaStringBolha(palavrasChave);
        System.out.println("Gerando arquivo de saída: " + caminhoArquivoSaida);
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoArquivoSaida))) {
            boolean algumaPalavraEncontrada = false;
            for (int i = 0; i < palavrasChave.tamanho(); i++) {
                String palavraChaveStr = palavrasChave.obter(i); // Já está limpa e normalizada
                // System.out.println("  Buscando palavra-chave normalizada: " + palavraChaveStr);
                Palavra palavraEncontrada = tabelaHash.obter(palavraChaveStr); // Busca pela forma normalizada

                if (palavraEncontrada != null && !palavraEncontrada.getOcorrencias().estaVazia()) {
                    escritor.write(palavraEncontrada.getTermo() + " " + palavraEncontrada.getOcorrencias().toString());
                    escritor.newLine();
                    algumaPalavraEncontrada = true;
                    // System.out.println("    Encontrada: " + palavraEncontrada.getTermo() + " " + palavraEncontrada.getOcorrencias().toString());
                } else {
                    // System.out.println("    Não encontrada ou sem ocorrências: " + palavraChaveStr);
                }
            }
            if (!algumaPalavraEncontrada && palavrasChave.tamanho() > 0) {
                System.out.println("AVISO: Nenhuma das palavras-chave fornecidas foi encontrada no texto ou não possuíam ocorrências válidas para o índice.");
            }
        }
    }
}