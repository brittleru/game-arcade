package com.arcade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class GameArcadeApplication {

	@Value("${server.port}")
	private int serverPortProp;

	@Value("${server.servlet.context-path}")
	private String serverContextPathProp;

	private static int serverPort;
	private static String serverContextPath;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(GameArcadeApplication.class, args);
//		openBrowser();
	}

	private static void openBrowser() throws IOException {
		String url = "http://localhost:" + String.valueOf(serverPort) + serverContextPath;
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
}
