package com.github.cquoss.jsch.project.upload;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    // public api methods

    public static void main(final String[] args) {
        new App().run();
    } 
    
    // private helper methods

    private void run() {
        LOGGER.info("run start");
        final ChannelSftp channel;
        final JSch jsch = new JSch();
        final Session session;
        try {
            session = jsch.getSession("foo", "localhost", 2222);
        } catch (final JSchException e) {
            throw new IllegalStateException("Error getting session: " + e.getMessage(), e);
        }
        Properties config = new Properties(); 
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        LOGGER.trace("run [session={}]", session);
        session.setPassword("bar");
        try {
            session.connect();
        } catch (final JSchException e) {
            throw new IllegalStateException("Error connecting session: " + e.getMessage(), e);
        }
        try {
             channel = (ChannelSftp) session.openChannel("sftp");
        } catch (final JSchException e) {
            throw new IllegalStateException("Error opening channel: " + e.getMessage(), e);
        }
        LOGGER.info("run [channel={}]", channel);
        channel.disconnect();
        session.disconnect();
        LOGGER.info("run end");
    }

}
