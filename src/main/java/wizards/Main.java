package wizards;

import montpy.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Exemplo de uso
        TACGenerator tacGenerator = new TACGenerator();

        // Lendo o código-fonte MontPy a partir de um arquivo
        String sourceFilePath = "src/main/resources/exemplo.mpy"; // Substitua pelo caminho real do arquivo MontPy
        try {
            InputStream sourceCode = new FileInputStream(sourceFilePath);
            tacGenerator.generateTACFromSource(sourceCode);
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + sourceFilePath);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Exibir o TAC gerado
        List<String> globalTAC = tacGenerator.getGlobalTAC();
        Map<String, List<String>> functionsTAC = tacGenerator.getFunctionsTAC();

        System.out.println("TAC Global:");
        for (String line : globalTAC) {
            System.out.println(line);
        }

        System.out.println("\nTAC de Funções:");
        for (Map.Entry<String, List<String>> entry : functionsTAC.entrySet()) {
            System.out.println("Função " + entry.getKey() + ":");
            for (String line : entry.getValue()) {
                System.out.println(line);
            }
        }

        // Converter TAC para Assembly
        TACToAssemblyConverter converter = new TACToAssemblyConverter();
        List<String> assemblyCode = converter.convert(functionsTAC, globalTAC);

        // Exibir o código Assembly gerado
        System.out.println("\nCódigo Assembly:");
        for (String line : assemblyCode) {
            System.out.println(line);
        }
    }
}
