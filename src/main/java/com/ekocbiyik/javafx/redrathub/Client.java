package com.ekocbiyik.javafx.redrathub;/*
 * Simple Java code to send commands to the RedRatHubCmd application via a socket.
 *
 * Chris Dodge - RedRat Ltd.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Client {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    /*
     * Opens the socket to RedRatHubCmd.
     */
    public void openSocket(String host, int port) throws UnknownHostException, IOException {
        if (socket != null && socket.isConnected()) return;

        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    /*
     * Closes the RedRatHubCmd socket.
     */
    public void closeSocket() throws IOException {
        socket.close();
    }

    /*
     * Sends a message to the readData() method. Use when returned data from RedRatHub is not needed.
     */
    public void sendMessage(String message) throws Exception {
        String res = readData(message);
        if (!res.trim().equals("OK")) {
            throw new Exception("Error sending message: " + res);
        }
    }

    /*
     * Reads data back from RedRatHub. Use when returned data is needed to be output.
     */
    public String readData(String message) throws IOException {
        if (socket == null || !socket.isConnected()) {
            System.out.println("\tSocket has not been opened. Call 'openSocket()' first.");
            return null;
        }

        // Send message
        out.write((message + "\n").getBytes("UTF-8"));

        // Check response. This is either a single line, e.g. "OK\n", or a multi-line response with
        // '{' and '}' start/end delimiters.
        String received = "";
        byte[] inBuf = new byte[256];
        while (true) {
            // Read data...
            int inLength = in.read(inBuf);
            //byte[] thisMSg = new byte[inLength];
            String msg = new String(Arrays.copyOfRange(inBuf, 0, inLength), "UTF-8");
            received += msg;
            if (checkEom(received)) return received;
        }
    }

    /*
     * Checks for the end of a message
     */
    public boolean checkEom(String message) {
        // Multi-line message
        if (message.trim().endsWith("}")) {
            return message.startsWith("{");
        }

        // Single line message
        return message.endsWith("\n");
        //return true;
    }
}
