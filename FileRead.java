import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class FileRead {
    public File file;
    public FileReader fr;
    public BufferedReader br;
    public FileRead(String fileName) throws IOException{
        file = new File(fileName);
        fr = new FileReader(file);
        br = new BufferedReader(fr);
    }
    public String[] read_put(Graph g) throws IOException{
        String line = "";
        String[] words = null;
        try {
            while ((line = br.readLine()) != null) {
                words = line.split("\t");
                g.addEdge(words[0], words[1], Integer.parseInt(words[2]));
            }
        } catch (Exception e) {
            System.out.println("There is no text.");
        }
        br.close();
        return words;
    }
}
