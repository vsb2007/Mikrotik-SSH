import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by VSB on 05.08.2015.
 */

public class sshMikrotik {
    private static final int SSH_PORT = 22;
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int BUFFER_SIZE = 1024;
    public String connectAndExecuteListCommand(String host, String username, String password,String command) {
        String response="no data";
        try {
            Session session = initSession(host, username, password);
            Channel channel = initChannel(command, session);
            InputStream in = channel.getInputStream();
            channel.connect();
            String dataFromChannel = getDataFromChannel(channel, in);
            //System.out.println(dataFromChannel);
            response = dataFromChannel;
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return response;
    }
    private Session initSession(String host, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, SSH_PORT);
        session.setPassword(password);
        UserInfo userInfo = new MyUserInfo();
        session.setUserInfo(userInfo);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(CONNECTION_TIMEOUT);
        return session;
    }
    public class MyUserInfo implements UserInfo {

        private String password;

        public void showMessage(String message) {
            System.out.println(message);
        }

        public boolean promptYesNo(String message) {
            System.out.println(message);
            return true;
        }

        //@Override
        public String getPassphrase() {
            return null;
        }

        //@Override
        public String getPassword() {
            return this.password;
        }

        //@Override
        public boolean promptPassphrase(String arg0) {
            System.out.println(arg0);
            return true;
        }

        //@Override
        public boolean promptPassword(String arg0) {
            System.out.println(arg0);
            this.password = arg0;
            return true;
        }
    }
    private Channel initChannel(String commands, Session session) throws JSchException {
        Channel channel = session.openChannel("exec");
        ChannelExec channelExec = (ChannelExec) channel;
        channelExec.setCommand(commands);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        return channel;
    }
    private String getDataFromChannel(Channel channel, InputStream in)
            throws IOException {
        StringBuilder result = new StringBuilder();
        byte[] tmp = new byte[BUFFER_SIZE];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, BUFFER_SIZE);
                if (i < 0) {
                    break;
                }
                result.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                int exitStatus = channel.getExitStatus();
                System.out.println("exit-status: " + exitStatus);
                break;
            }
            trySleep(1000);
        }
        return result.toString();
    }
    private void trySleep(int sleepTimeInMilliseconds) {
        try {
            Thread.sleep(sleepTimeInMilliseconds);
        } catch (Exception e) {
        }
    }

}
