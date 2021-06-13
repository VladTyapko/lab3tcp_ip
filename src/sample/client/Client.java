package sample.client;

import sample.Metropolitan;

import java.io.*;
import java.net.Socket;


import static sample.OperationsCodes.*;


public class Client {
    private Socket socket = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    ObjectInputStream  objectInputStream;
    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new DataInputStream(socket.getInputStream( ));
        out = new DataOutputStream(socket.getOutputStream());
        InputStream inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
    }
    public Metropolitan getAll() throws IOException, ClassNotFoundException {
        out.writeInt(GET_ALL);
        return(Metropolitan) objectInputStream.readObject();
    }
    public void updateLine(int lineId, int newId, String newColor) throws IOException {
        out.writeInt(UPDATE_LINE);
        out.writeInt(lineId);
        out.writeInt(newId);
        out.writeUTF(newColor);
    }
    public void updateStation(int lineId, int stationId, int newId, String newName) throws IOException {
        out.writeInt(UPDATE_STATION);
        out.writeInt(lineId);
        out.writeInt(stationId);
        out.writeInt(newId);
        out.writeUTF(newName);
    }
    public void addLine(int lineId, String color) throws IOException {
        out.writeInt(ADD_LINE);
        out.writeInt(lineId);
        out.writeUTF(color);
    }

    public void addStation(int lineId, int stationId, String name) throws IOException {
        out.writeInt(ADD_STATION);
        out.writeInt(lineId);
        out.writeInt(stationId);
        out.writeUTF(name);
    }
    public void deleteLine(int lineId) throws IOException {
        out.writeInt(DELETE_LINE);
        out.writeInt(lineId);
    }
    public void deleteStation(int lineId, int stationId) throws IOException {
        out.writeInt(DELETE_STATION);
        out.writeInt(lineId);
        out.writeInt(stationId);
    }

}
