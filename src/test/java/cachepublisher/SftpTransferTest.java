package cachepublisher;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.file.nativefs.NativeFileSystemFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PublickeyAuthenticator;
import org.apache.sshd.server.UserAuth;
import org.apache.sshd.server.auth.UserAuthPublicKey;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SftpTransferTest {

    public static SshServer createAndStartSshServer(int port) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setHost("localhost");
        sshd.setPort(port);
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("c:/temp/pair"));

        List<NamedFactory<UserAuth>> userAuthFactories = new ArrayList<>();
//        userAuthFactories.add(new UserAuthNone.Factory());
        userAuthFactories.add(new UserAuthPublicKey.Factory());
        sshd.setUserAuthFactories(userAuthFactories);

        sshd.setCommandFactory(new ScpCommandFactory());

        List<NamedFactory<Command>> namedFactoryList = new ArrayList<>();
        namedFactoryList.add(new SftpSubsystem.Factory());
        sshd.setSubsystemFactories(namedFactoryList);
        sshd.start();
        return sshd;
    }

    private SshServer createSshServer( int port, String homeDir, String hostKeyPath ) {
        System.setProperty("user.dir",homeDir);
        SshServer server = SshServer.setUpDefaultServer();
        server.setHost("localhost");
        server.setPort(port);
//        server.setFileSystemFactory(new VirtualFileSystemFactory(homeDir));
        server.setFileSystemFactory(new NativeFileSystemFactory());

                server.setSubsystemFactories(Collections.<NamedFactory<Command>>singletonList(new SftpSubsystem.Factory()));
        server.setCommandFactory(new ScpCommandFactory());
        server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(hostKeyPath));
        server.setUserAuthFactories(Collections.<NamedFactory<UserAuth>>singletonList(new UserAuthPublicKey.Factory()));
        server.setPublickeyAuthenticator(new PublickeyAuthenticator() {
            public boolean authenticate(String username, PublicKey key, ServerSession session) {
                return true;
            }
        });//        server.setPasswordAuthenticator( new );
        return server;
    }

    @Test
    public void testDoTransfer() throws Exception {
        SshServer sshServer = createSshServer(28999, "C:/temp/home", "C:/temp/pair/hostkey.ser");
        sshServer.start();
//        createAndStartSshServer(28999);
        SftpTransfer sftpTransfer = new SftpTransfer("localhost", "user", 28999, "", "ok");
        sftpTransfer.doTransfer(new File("C:/temp/home/to send/2.txt"));
        sshServer.stop();
    }
}