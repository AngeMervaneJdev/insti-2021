package io.artcreativity.monpremierprojetange.ui;

public interface AuthCallback {

    void sendMessage(String phoneNumber);
    void verification(String code);

}
