import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class PromiseFileReader {

    public Promise<String[]> readLines(String fileName){

        Promise<String[]> linesPromise = new Promise<>();

        new Thread(() -> {
            try {
                LineNumberReader lr = new LineNumberReader(new FileReader(fileName));
                lr.skip(Integer.MAX_VALUE);

                int length = lr.getLineNumber() + 1;
                lr.close();

                BufferedReader br = new BufferedReader(new FileReader(fileName));

                String[] lines = new String[length];

                for(int i = 0; i < length; i++) {
                    lines[i] = br.readLine();
                }

                br.close();
                linesPromise.resolve(lines, true);
            } catch(IOException ex) {
                System.out.println(ex);
                String[] lines = {"fail"};
                linesPromise.resolve(lines, false);
            }
        }).start();

        return linesPromise;
    }
}
