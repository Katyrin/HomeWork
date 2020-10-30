package ru.geekbrains.chat.client;

import ru.geekbrains.chat.common.Library;
import ru.geekbrains.network.SocketThread;
import ru.geekbrains.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private static final String WINDOW_TITLE = "Chat client";

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("roman");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;
    private SocketThread socketThread;

    private ClientGUI(){
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle(WINDOW_TITLE);
        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUser = new JScrollPane(userList);
        scrollUser.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);
        btnLogin.addActionListener(this);
        // реализация кнопки дисконект
        btnDisconnect.addActionListener(this);

        userList.setSelectionModel(new DefaultListSelectionModel(){
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (index0==index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                }
                super.setSelectionInterval(index0, index1);
            }
            @Override
            public void addSelectionInterval(int index0, int index1) {
                if (index0==index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        // скрыл нижнюю панель
        panelBottom.setVisible(false);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop){
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        }else if (src == btnSend || src == tfMessage){
            sendMessage();
        }else if (src == btnLogin){
            connect();
        }else if (src == btnDisconnect) {
            // реализация кнопки дисконект
            socketThread.close();
        }else {
            showException(Thread.currentThread(), new RuntimeException("Unknown action source: " + src));
        }
    }

    private void connect(){
        try {
            Socket socket = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread("Client", this, socket);
        } catch (IOException e) {
            showException(Thread.currentThread(), e);
        }
    }

    private void sendMessage(){
        String msg = tfMessage.getText();
        String userName = tfLogin.getText();
        if ("".equals(msg))
            return;
        tfMessage.setText(null);
        tfMessage.grabFocus();
//        putLog(String.format("%s: %s", userName, msg));
//        wrtMsgToLogFile(msg, userName);
        if (userList.isSelectionEmpty()){
            socketThread.sendMessage(Library.getClientBcastMsg(msg));
        }else {
            socketThread.sendMessage(Library.getPrivateClientBcastMsg(msg
                    + Library.DELIMITER + userList.getSelectedValue()));
        }
    }

    private void wrtMsgToLogFile(String msg, String userName){
        try(FileWriter out = new FileWriter("log.txt", true)) {
            out.write(userName + ": " + msg + "\n");
            out.flush();
        } catch (IOException e) {
            if (!shownIoErrors){
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
    }

    private void putLog(String msg){
        if ("".equals(msg))
            return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    private void showException(Thread t, Throwable e){
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0){
            msg = "Empty Stacktrace";
        }else {
            msg = String.format("Exception in \"%s\" %s: %s\n\tat %s", t.getName(), e.getClass().getCanonicalName(),
                    e.getMessage(), ste[0]);
        }
        JOptionPane.showMessageDialog(null, msg, "Exception", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showException(t, e);
        System.exit(1);
    }

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Start");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        panelBottom.setVisible(false);
        panelTop.setVisible(true);
        setTitle(WINDOW_TITLE);
        userList.setListData(new String[0]);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        panelBottom.setVisible(true);
        panelTop.setVisible(false);
        String login = tfLogin.getText();
        String password = new String(tfPassword.getPassword());
        thread.sendMessage(Library.getAuthRequest(login, password));
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        handleMessage(msg);
    }

    private void handleMessage(String msg){
        String[] arr = msg.split(Library.DELIMITER);
        String msgType = arr[0];
        switch (msgType){
            case Library.SERVER_BCAST_MSG:
            case Library.PRIVATE_SERVER_BCAST_MSG:
                putLog(String.format("[%s] <%s>: %s", arr[1], arr[2], arr[3]));
                break;
            case Library.MSG_FORMAT_ERROR:
                putLog(msg);
                socketThread.close();
                break;
            case Library.AUTH_ACCEPT:
                setTitle(String.format("%s entered with nickname: %s", WINDOW_TITLE, arr[1]));
                break;
            case Library.USER_LIST:
                String users = msg.substring(Library.USER_LIST.length() + Library.DELIMITER.length());
                String[] usersArr = users.split(Library.DELIMITER);
                Arrays.sort(usersArr);
                userList.setListData(usersArr);
                break;
            case Library.AUTH_DENIED:
                putLog(msg);
                break;
            default:
                throw new RuntimeException("Unknown message type: " + msg);
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        showException(thread, exception);
    }
}
