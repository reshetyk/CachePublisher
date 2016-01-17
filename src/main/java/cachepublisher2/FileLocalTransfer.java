package cachepublisher2;

import java.io.File;
import java.util.List;

/**
 * Created by Reshetuyk on 15.01.2016.
 */
public class FileLocalTransfer implements TransferMethod<File> {
    @Override
    public void doTransfer(File target, File source) {

    }

    @Override
    public void doTransfer(File target, List<File> source) {

    }

    @Override
    public File getTarget() {
        return null;
    }
}
