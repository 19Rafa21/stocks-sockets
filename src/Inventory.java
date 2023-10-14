import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Inventory {

    private Hashtable<String, Integer> inventory;
    
    public Inventory() throws IOException {

         // Verifica se o arquivo existe
        File file = new File("inventario.txt");
        if (file.exists()) {
            // Prossegue o programa normalmente
        } else {
            // Cria o arquivo
            file.createNewFile();

            // Escreve os itens no arquivo txt
            FileWriter writer = new FileWriter(file);
            
            writer.write("Maca,5\nPera,5\nBanana,10");

            writer.close();
        }

        this.inventory = new Hashtable<>();
    }

    public int getQuantity(String name) {
        return this.inventory.get(name);
    }

    /*public synchronized String changeQuantity(String key, int newValue) {
        if (inventory.containsKey(key)) {
            inventory.replace(key, (inventory.get(key) + newValue));
            return "New quantity : " + inventory.get(key) + " " + key + "s";
        } else {
            return "STOCK_ERROR: Quantidade invalida";
        }
    }*/

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            result.append(entry.getKey());
            result.append(" : ");
            result.append(entry.getValue());
            result.append("\n ");
        }

        if (result.length() > 1) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }

    /* public Inventory fromString(String string) {
        Inventory newInventory = new Inventory();

        StringBuilder msg = new StringBuilder(string);
        String[] lines = msg.toString().split("\n");

        for (String line : lines) {
            String[] parts = line.split(" : ");
            newInventory.inventory.put(parts[0], Integer.parseInt(parts[1]));
        }

        return newInventory;
    } */

    
    public void changeQuantity(String itemName, int newQuantity) throws IOException {

        // ObtÃ©m a quantidade atual do item
        int oldQuantity = inventory.get(itemName);

        // Atualiza a quantidade do item
        int newTotalQuantity = oldQuantity + newQuantity;

        // Atualiza o inventÃ¡rio
        inventory.replace(itemName, newTotalQuantity);

        // Escreve o inventÃ¡rio atualizado no arquivo
        writeInventory(inventory);
    }

    public void writeInventory(Hashtable<String, Integer> inventory) throws IOException {

        // Apaga o arquivo antigo
        File file = new File("inventario.txt");
        file.delete();

        // Cria um objeto FileWriter
        FileWriter writer = new FileWriter("inventario.txt");

        // Percorre o inventÃ¡rio
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            // Escreve o item no arquivo
            writer.write(entry.getKey() + "," + entry.getValue() + "\n");
        }

        // Fecha o arquivo
        writer.close();
    }

    public Hashtable<String, Integer> readInventory() throws IOException {

        // Cria uma lista de itens
        //this.inventory = new Hashtable<>();
    
        File file = new File("inventario.txt");
    
        // Abre o arquivo para leitura
        FileReader reader = new FileReader(file);
    
        // Lê os dados do inventário do arquivo
        BufferedReader in = new BufferedReader(reader);
    
        // Lê cada linha do arquivo
        String line;
        while ((line = in.readLine()) != null) {
            // Separa a linha em duas colunas
            String[] columns = line.split(",");
    
            // Adiciona o item ao inventário
            if (columns.length == 1) {
                columns = new String[]{line, "1"};
            }
    
            inventory.put(columns[0], Integer.parseInt(columns[1]));
        }
    
        // Fecha o arquivo
        reader.close();
    
        return inventory;
    }
}

