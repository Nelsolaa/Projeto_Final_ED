public class App {

    public static void main(String[] args) {
        String arquivoPalavrasChave = "PalavrasChave.txt";
        String arquivoTexto = "textoInicial.txt";
        String arquivoSaida = "indice_remissivo_saida.txt";
        
        MotorIndiceRemissivo motor = new MotorIndiceRemissivo(arquivoPalavrasChave, arquivoTexto, arquivoSaida);
        motor.executar();
    }
}