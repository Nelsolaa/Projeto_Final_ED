public class App {

    public static void main(String[] args) {
        String arquivoPalavrasChave = "C:\\Users\\nelso\\OneDrive\\Área de Trabalho\\javafx\\Trabalho_Final_Estrutura\\src\\palavrasChaves.txt";
        String arquivoTexto = "C:\\Users\\nelso\\OneDrive\\Área de Trabalho\\javafx\\Trabalho_Final_Estrutura\\src\\textoInicial.txt";
        String arquivoSaida = "indice_remissivo_saida.txt";


        MotorIndiceRemissivo motor = new MotorIndiceRemissivo(arquivoPalavrasChave, arquivoTexto, arquivoSaida);
        motor.executar();
    }
}