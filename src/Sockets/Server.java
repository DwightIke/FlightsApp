package Sockets;

import Database.Database;
import Domain.LoginData;
import Repository.UserRepository;
import Services.UserService;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.sql.Connection;

public class Server {

    public static final String IP = "localhost";
    public static final int PORT = 6789;

    private static Gson GSON = new Gson();

    public static void main(String argv[]) throws Exception {
        Database database = new Database();
        Connection connection = database.getConnection();
        UserRepository userRepo = new UserRepository(connection);
        UserService userService = new UserService(userRepo);
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            String message = inFromClient.readLine();
            System.out.println("Got text: " + message);
            LoginData loginData = GSON.fromJson(message, LoginData.class);
            loginData.canLogIn = userService.canLogIn(loginData.loginCode);
            String outMessage = GSON.toJson(loginData);
            outToClient.writeBytes(outMessage);
            connectionSocket.close();
        }
    }
}