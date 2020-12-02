package ru.geekbrains.chat.server.core;

import ru.geekbrains.chat.common.Library;
import ru.geekbrains.network.ServerSocketThread;
import ru.geekbrains.network.ServerSocketThreadListener;
import ru.geekbrains.network.SocketThread;
import ru.geekbrains.network.SocketThreadListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.*;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private String currentTime = DATE_FORMAT.format(System.currentTimeMillis());
    private ServerSocketThread server = null;
    private Vector<SocketThread> clients = new Vector<>();
    private final ChatServerListener listener;
    private static final Logger logger = Logger.getLogger("");
    private  Handler handler;

    public ChatServer(ChatServerListener listener){
        this.listener = listener;
    }

    public void start(int port){
        if (server != null && server.isAlive()){
            putLog("Server already started");
        }else {
            server = new ServerSocketThread(this,"Chat server", 8189, 2000);
        }
    }

    public void stop(){
        if (server == null || !server.isAlive()){
            putLog("Server is not running");
        }else {
            server.interrupt();
        }
    }

    private void putLog(String msg){
        msg = currentTime + ": " + Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerMessage(msg);

        logger.setLevel(Level.INFO);
        logger.getHandlers()[0].setLevel(Level.INFO);

        try (FileInputStream fio = new FileInputStream("logging.properties")){
            LogManager.getLogManager().readConfiguration(fio);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.getHandlers()[0].setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getLevel() + "\t" + record.getMessage() + "\n";
            }
        });

        try {
            handler = new FileHandler("mylog.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.setLevel(Level.INFO);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

        logger.log(Level.INFO, msg);
    }

    private String getUsers(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            sb.append(client.getNickname()).append(Library.DELIMITER);
        }
        return sb.toString();
    }

    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server thread started");
        SqlClient.connect();
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server thread stopped");
        SqlClient.disconnect();
        clients.forEach(SocketThread::close);
    }

    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server socked created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        //putLog("Server timeout");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client connected");
        String name = "Socket Thread " + socket.getInetAddress() + ": " + socket.getPort();
        new ClientThread(name, this, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    // Socket methods

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket created");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        ClientThread client = (ClientThread) thread;
        clients.remove(thread);
        if (client.isAuthorized() && !client.isReconnecting()){
            String msg = Library.getTypeBroadcast("Server",
                    String.format("%s disconnected", client.getNickname()), currentTime);
            sendToAllAuthorizedClients(msg);
            putLog(formatMsg(msg));
        }
        sendToAllAuthorizedClients(Library.getUserList(getUsers()));
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthorizedMessage(client, msg);
        } else {
            handleNonAuthorizedMessage(client, msg);
        }
    }

    private void handleNonAuthorizedMessage(ClientThread client, String msg) {
        String[] arr = msg.split(Library.DELIMITER);
        if (arr.length != Library.AUTH_REQUEST_LENGTH ||
                !arr[Library.MSG_PREFIX].equals(Library.AUTH_REQUEST)) {
            client.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog("Invalid login attempt: " + login);
            client.authFail();
            return;
        }else {
            ClientThread oldClient = findClientByNickname(nickname);
            client.authAccept(nickname);
            if (oldClient == null){
                String message = Library.getTypeBroadcast("Server",
                        nickname + " connected", currentTime);
                sendToAllAuthorizedClients(message);
                putLog(formatMsg(message));
            }else {
                oldClient.reconnect();
                clients.remove(oldClient);
            }
        }
        sendToAllAuthorizedClients(Library.getUserList(getUsers()));
    }

    private synchronized ClientThread findClientByNickname(String nickname){
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            if (client.getNickname().equals(nickname))
                return client;
        }
        return null;
    }

    private void handleAuthorizedMessage(ClientThread client, String msg) {
        String[] arr = msg.split(Library.DELIMITER);
        String msgType = arr[0];
        switch (msgType){
            case Library.CLIENT_BCAST_MSG:
                sendToAllAuthorizedClients(Library.getTypeBroadcast(client.getNickname(), arr[1], currentTime));
                putLog(formatMsg(Library.getTypeBroadcast(client.getNickname(), "Send message", currentTime)));
                break;
            case Library.PRIVATE_CLIENT_BCAST_MSG:
                sendPrivateMessage(Library.getPrivateServerBcastMsg("P-msg: " + client.getNickname(),
                        arr[1], currentTime), arr[2]);
                break;
            default:
                client.sendMessage(Library.getMsgFormatError(msg));
        }
    }

    private void sendPrivateMessage(String msg, String nickname){
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (client.getNickname().equals(nickname))
                client.sendMessage(msg);
        }
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }

    private String formatMsg(String msg){
        String[] arr = msg.split(Library.DELIMITER);
        return arr[2] + " " + arr[3];
    }
}