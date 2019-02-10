package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

interface ServerListener {
    ServerResponse onRequest(ServerRequest r);
}

public class Server {

    private static List<ServerListener> listeners = new ArrayList<ServerListener>();
    public static int activeRequests=0;
    public static ServerSocket ss;
    Thread st=new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                //System.err.println("Begin");
                try {
                    Socket s = ss.accept();
                    activeRequests++;
                    //System.out.println(activeRequests);
                    new Thread(new SocketProcessor(s)).start();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    });
    public Server(int port) throws IOException {
        ss = new ServerSocket(port);
        st.start();
    }
    public void addListener(ServerListener toAdd) {
        listeners.add(toAdd);
    }
    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                ServerRequest req=readRequest();

                for (ServerListener hl : listeners)
                    writeResponse(hl.onRequest(req));
                //ServerResponse r=new ServerResponse();
                //r.content="<html><body><h1>Welcome, guest!</h1><h3>P.S.: Ya_typoi</h3></body></html>";
                //writeResponse(r);
            } catch (Throwable t) {
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                }
            }
            activeRequests--;
            //System.err.println("Okay");
        }

        private void writeResponse(ServerResponse s) throws Throwable {
            if(s==null){
                return;
            }
            /*String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: SmartMehanics\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";*/
            //if(s.begin=="")s.begin="HTTP/1.1 200 OK";
            if(!s.headers.containsKey("Content-Length"))s.headers.put("Content-Length",""+s.content.length());
            if(!s.headers.containsKey("Content-Type"))s.headers.put("Content-Type","text/html");
            if(!s.headers.containsKey("Connection"))s.headers.put("Connection","close");
            String response=s.begin+"\r\n";
            for (String header:s.headers.keySet()) {
                response+=header+": "+s.headers.get(header)+"\r\n";
            }
            String result = response + "\r\n" + s.content;
            os.write(result.getBytes());
            os.flush();
        }

        private ServerRequest readRequest() throws Throwable {
            ServerRequest ans=new ServerRequest();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int l=0;
            while(true) {
                String s = br.readLine();

                if(s == null || s.trim().length() == 0) {
                    break;
                }
                if(l==0){
                    ans.begin=s;
                    //System.out.println(s);
                    String link="";
                    Scanner sc=new Scanner(s);
                    sc.next();
                    link=sc.next();
                    ans.link=link;
                    //System.out.println(link);
                }else{
                    ans.headers.put(s.substring(0,s.indexOf(": ")),s.substring(s.indexOf(": ")+2));
                }
                l++;
            }
            return ans;
        }
    }
}

class ServerRequest{
    String link="";
    String begin="";
    HashMap<String,String> headers=new HashMap<>();
}

class ServerResponse{
    String begin="";
    String content="";
    HashMap<String,String> headers=new HashMap<>();
}