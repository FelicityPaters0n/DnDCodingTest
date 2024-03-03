import java.io.*;
import java.nio.file.*;
import java.util.*;

// Interface for reading data
interface DataReader {
   List<String> readData(String source) throws IOException;
}

// Interface for sorting data
interface DataSorter {
   void sort(List<String> data);
}

// Interface for writing data
interface DataWriter {
   void writeData(String destination, List<String> data) throws IOException;
}

// Implementation of DataReader to read from a file
class FileDataReader implements DataReader {
   public List<String> readData(String filename) throws IOException {
      List<String> lines = new ArrayList<>();
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
         String line;
         while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
         }
      }
      return lines;
   }
}

// Implementation of DataSorter for sorting names
class NameDataSorter implements DataSorter {
   public void sort(List<String> data) {
      Collections.sort(data, (name1, name2) -> {
            String lastName1 = name1.substring(name1.lastIndexOf(" ") + 1);
            String lastName2 = name2.substring(name2.lastIndexOf(" ") + 1);
            return lastName1.compareToIgnoreCase(lastName2) == 0 ? name1.compareToIgnoreCase(name2) : lastName1.compareToIgnoreCase(lastName2);
      });
   }
}

// Implementation of DataWriter to write to a file
class FileDataWriter implements DataWriter {
   public void writeData(String destination, List<String> data) throws IOException {
      Files.write(Paths.get(destination), data);
   }
}


public class nameSorter {
   public static void main(String[] args) {
      if (args.length < 1) {
         System.out.println("Usage: java NameSorter <filename>");
         return;
      }
      String filename = args[0];
      DataReader reader = new FileDataReader();
      DataSorter sorter = new NameDataSorter();
      DataWriter writer = new FileDataWriter();

      try {
         List<String> lines = reader.readData(filename);
         sorter.sort(lines);
         writer.writeData("sorted-names-list.txt", lines);
         System.out.println(lines);
         System.out.println("Sorted names have been written to sorted-names-list.txt");
      } catch (IOException e) {
         System.err.println("An error occurred: " + e.getMessage());
         e.printStackTrace();
      }
   }
}
