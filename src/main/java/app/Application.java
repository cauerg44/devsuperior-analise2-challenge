package app;

import ents.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre com caminho do arquivo: ");
        String caminhoArquivo = sc.nextLine();
        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            List<Sale> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                int month = Integer.parseInt(fields[0]);
                int year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                int items = Integer.parseInt(fields[3]);
                double total = Double.parseDouble(fields[4]);

                Sale sale = new Sale(month, year, seller, items, total);
                list.add(sale);
            }

            System.out.println("Total de vendas por vendedor:");
            Set<String> sellers = list.stream().map(Sale::getSeller).collect(Collectors.toSet());
            for (String seller : sellers) {
                double total = list.stream()
                        .filter(sale -> sale.getSeller().equals(seller))
                        .mapToDouble(Sale::getTotal)
                        .sum();
                Sale totalSale = new Sale(0, 0, seller, 0, total);
                System.out.println(totalSale);
            }
        }
        catch (IOException e) {
            System.out.println("Erro: " + caminhoArquivo + " (O sistema não pode encontrar o arquivo especificado)");
        }

        sc.close();
    }
}

