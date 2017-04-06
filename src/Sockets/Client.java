package Sockets;

import Domain.LoginData;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Cosmin on 4/4/2017.
 */
public class Client {

    private static final Gson GSON = new Gson();
    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

    private final ClientListener clientListener;

    private void init() {
        try {
            clientSocket = new Socket(Server.IP, Server.PORT);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(ClientListener clientListener) {
        this.clientListener = clientListener;
    }

    public void tryToLogin(final String loginCode) {
        init();
        Thread loginThread = new Thread(new Runnable() {
            public void run() {
                sendAndReceive(loginCode);
            }
        });
        loginThread.start();
    }


    private void sendAndReceive(String loginCode) {
        try {
            LoginData loginData = new LoginData();
            loginData.loginCode = loginCode;
            String json = GSON.toJson(loginData);
            outToServer.writeBytes(json + '\n');
            String jsonFromServer = inFromServer.readLine();
            loginData = GSON.fromJson(jsonFromServer, LoginData.class);
            clientListener.onLoginResult(loginData.canLogIn);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            clientListener.onLoginError();
        }
    }

    public static void main(String argv[]) throws Exception {
        Client client = new Client(new ClientListener() {
            public void onLoginResult(boolean canLogIn) {
                System.out.println("Can Log In?? " + canLogIn);
            }

            public void onLoginError() {
                System.out.println("Login Error checheraut");
            }
        });
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String login = scanner.nextLine();
            client.tryToLogin(login);
        }
    }
}
