package wizards;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


// Classe principal do programa
public class Main {
    // Método principal do programa
    public static void main(String[] args) {
        // Verifica se o caminho do arquivo de teste foi fornecido
        String inputFile;
        if (args.length != 1) {
            System.err.println("Uso: java TACGenerator <caminho_para_arquivo_de_teste>"); // Mensagem de erro
            System.exit(1); // Encerra o programa
        }
        inputFile = args[0];

        TACGenerator tac = new TACGenerator(); // Instância da classe TACGenerator
        TACOptimizer optimizer = new TACOptimizer(); // Instância da classe TACOptimizer
        try { // Tratamento de exceções
            //printTokens(inputFile);
            printFile(new FileInputStream(inputFile));  //
            tac.generateTACFromSource(new FileInputStream(inputFile));
        } catch (Exception e) { // Tratamento de exceções
            e.printStackTrace();
            System.exit(1);
        }

        // Imprime o código intermédio gerado
        System.out.println("Código Intermédio (Three Address Code):");

        // Imprime o código intermédio gerado
        tac.getFunctionsTAC().forEach((fn, localTAC) -> {
            localTAC.forEach(code -> System.out.println(code));
        });
        tac.getGlobalTAC().forEach(code -> System.out.println(code));

        // Otimização do código intermédio e impressão do código otimizado
        System.out.println("\nCódigo Intermédio Otimizado:");
        List<String> optimizedTAC = optimizer.optimizeTAC(tac.getGlobalTAC());
        optimizedTAC.forEach(System.out::println);
        for (var localTAC: tac.getFunctionsTAC().values()) {
            optimizedTAC = optimizer.optimizeTAC(localTAC); // Otimização do código intermédio
            optimizedTAC.forEach(System.out::println);
        }
    }

    // Método para imprimir o conteúdo do arquivo de teste
    public static void printFile(InputStream inputFile) throws IOException {
        System.out.println("=== SOURCE CODE ===");
        // Leitura do arquivo de teste
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("=== END SOURCE CODE ===\n");
    }
}
