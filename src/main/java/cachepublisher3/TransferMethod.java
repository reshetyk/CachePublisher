package cachepublisher3;

import java.io.File;
import java.util.List;

/**
 * Created by Reshetuyk on 15.01.2016.
 */
public interface TransferMethod {
    void doTransfer(File source);

    void doTransfer(List<File> source);
}
