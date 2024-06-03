package wizards;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Verifica se o caminho do arquivo de teste foi fornecido
        String inputFile;
        if (args.length != 1) {
            System.err.println("Uso: java TACGenerator <caminho_para_arquivo_de_teste>");
            System.exit(1);
        }
        inputFile = args[0];

        TACGenerator tac = new TACGenerator();
        TACOptimizer optimizer = new TACOptimizer();
        try {
            //printTokens(inputFile);
            printFile(new FileInputStream(inputFile));
            tac.generateTACFromSource(new FileInputStream(inputFile));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Código Intermédio (Three Address Code):");
        
        tac.getFunctionsTAC().forEach((fn, localTAC) -> {
            localTAC.forEach(code -> System.out.println(code));
        });
        tac.getGlobalTAC().forEach(code -> System.out.println(code));

        System.out.println("\nCódigo Intermédio Otimizado:");
        List<String> optimizedTAC = optimizer.optimizeTAC(tac.getGlobalTAC());
        optimizedTAC.forEach(System.out::println);
        for (var localTAC: tac.getFunctionsTAC().values()) {
            optimizedTAC = optimizer.optimizeTAC(localTAC);
            optimizedTAC.forEach(System.out::println);
        }
    }

    public static void printFile(InputStream inputFile) throws IOException {
        System.out.println("=== SOURCE CODE ===");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("=== END SOURCE CODE ===\n");
    }
}
