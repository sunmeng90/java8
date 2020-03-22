package org.meng.java.thread.httpserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.meng.java.thread.pool.ThreadPool;

import java.io.Closeable;

import org.meng.java.thread.pool.DefaultThreadPool;

public class SimpleHttpServer {
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>();
    static String basePath = ".";
    static ServerSocket serverSocket;
    static int port = 8080;

    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        if (basePath != null && Files.exists(Paths.get(basePath)) && Files.isDirectory(Paths.get(basePath))) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void start() throws Exception {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            SimpleHttpServer.threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();

    }

    static class HttpRequestHandler implements Runnable {
        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("got new request ");
            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                System.out.println("got new request " + header);
                String filePath = basePath + header.split(" ")[1];
                System.out.println("resolve file path " + filePath + " -> " + Paths.get(filePath));
                out = new PrintWriter(socket.getOutputStream());
                if (filePath.endsWith(".jpg") || filePath.endsWith(".ico") || filePath.endsWith(".png")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        bos.write(i);
                    }
                    byte[] arr = bos.toByteArray();
                    System.out.println("Send response length: " + arr.length);
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + arr.length);
                    out.println("");
                    out.flush(); // this is required, otherwise headers may not write to http
                    socket.getOutputStream().write(arr, 0, arr.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/html; charset=UFT-8");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.println(line);
                    }
                }
                out.flush();
            } catch (Exception e) {
                System.out.println("Server error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                close(br, in, reader, out, socket);
            }
        }

        private static void close(Closeable... closeables) {
            for (Closeable closeable : closeables) {
                try {
                    if (closeable != null) {
                        closeable.close();
                    }
                } catch (Exception e) {
                    System.out.println("Can't close closeable: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        SimpleHttpServer.start();
    }
}