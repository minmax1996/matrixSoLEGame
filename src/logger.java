/**
 * Created by Максим on 23.02.2017.
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class logger {
    String fileName;

    logger(String fn) throws IOException {
        this.fileName=fn;
        File file = new File(fileName);
        file.createNewFile();
        PrintWriter out;

    }
}
