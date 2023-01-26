package com.arcade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Logger;

@SpringBootApplication
public class GameArcadeApplication {

    private final static Logger logger = Logger.getLogger(GameArcadeApplication.class.getName());

    private static int serverPort;
    private static String serverContextPath;
    private static boolean isBrowserStart;
    private static boolean isLocalHostRunning;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GameArcadeApplication.class, args);

        if (isLocalHostRunning) {
            String url = "http://localhost:" + serverPort + serverContextPath;
            logger.info("Application started on: " + url);
            if (isBrowserStart) {
                openBrowser(url);
            }
        }
    }

    private static void openBrowser(String url) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("rundll32 url.dll, FileProtocolHandler " + url);
    }

    @Value("${server.port}")
    public void setServerPort(int port) {
        serverPort = port;
    }

    @Value("${server.servlet.context-path}")
    public void setServerContextPath(String contextPath) {
        serverContextPath = contextPath;
    }

    @Value("${browser.start}")
    public void setIsBrowserStart(boolean browserStart) {
        isBrowserStart = browserStart;
    }

    @Value("${local.host.running}")
    public void setIsLocalHostRunning(boolean localHostRunning) {
        isLocalHostRunning = localHostRunning;
    }
}
