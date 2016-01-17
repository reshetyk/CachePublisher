package cachepublisher;

import com.jcraft.jsch.*;

import java.io.File;
import java.util.List;

/**
 * Created by Reshetuyk on 15.01.2016.
 */
public class SftpTransfer implements TransferMethod<String> {

    private String host;
    private String user;
    private int port;
    private String keyPath;
    private String target;

    public SftpTransfer(String host, String user, int port, String keyPath, String target) {
        this.host = host;
        this.user = user;
        this.port = port;
        this.keyPath = keyPath;
        this.target = target;
    }

    @Override
    public void doTransfer(File source) {
        JSch jsch = new JSch();
        try {

//            String knownHostsFilename = "/home/username/.ssh/known_hosts";
//            jsch.setKnownHosts(knownHostsFilename);
//            jsch.addIdentity();

            Session session = jsch.getSession(user, host);
            session.setPort(port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftpChannel = (ChannelSftp) channel;

            sftpChannel.put(source.getAbsolutePath(), target);

            sftpChannel.exit();
            session.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void doTransfer(List<File> source) {

    }

    @Override
    public void setTarget(String target) {
        this.target = target;
    }
}
