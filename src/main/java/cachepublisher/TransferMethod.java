package cachepublisher;

import java.io.File;
import java.util.List;

/**
 * Created by Reshetuyk on 15.01.2016.
 */
public interface TransferMethod<T> {
    void doTransfer(File source);

    void doTransfer(List<File> source);

    void setTarget(T target);
}
