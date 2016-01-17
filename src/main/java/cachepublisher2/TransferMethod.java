package cachepublisher2;

import java.io.File;
import java.util.List;

/**
 * Created by Reshetuyk on 15.01.2016.
 */
public interface TransferMethod<T> {
    void doTransfer(T target, File source);

    void doTransfer(T target, List<File> source);

    T getTarget();
}
