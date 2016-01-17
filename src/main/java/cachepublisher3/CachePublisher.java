package cachepublisher3;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Reshetuyk on 15.01.2016.
 */
public class CachePublisher {
    private List<TransferMethod> transferMethods;
    private File cacheLocation;

    public CachePublisher(List<TransferMethod> transferMethods) {
        this.transferMethods = transferMethods;
    }

    public void publishCache () {
        for (TransferMethod transferMethod : transferMethods) {
            transferMethod.doTransfer(Arrays.asList(cacheLocation.listFiles()));
        }
    }
}
