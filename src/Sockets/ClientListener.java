package Sockets;

/**
 * Created by Musafir on 4/4/2017.
 */
public interface ClientListener {

    void onLoginResult(boolean canLogIn);
    void onLoginError();
}
