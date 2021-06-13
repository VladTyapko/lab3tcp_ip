package sample.server;

import sample.Metropolitan;
import sample.dao.ConcreteDAO;
import sample.dao.DAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    ObjectOutputStream objectOutputStream;
    OutputStream outputStream;
    DAO dao;
    public Server() {
    }
        public void start (int port) throws IOException, SQLException, ClassNotFoundException {
            dao = new ConcreteDAO();
            server = new ServerSocket(port);
            socket = server.accept();
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            System.out.println("running...");
            while (true) {
                int code = in.readInt();
                switch(code){
                    case 0:{
                        getAll();
                        break;
                    }
                    case 1:{
                        updateLine();
                        break;
                    }
                    case 2:{
                        updateStation();
                        break;
                    }
                    case 3:{
                        addLine();
                        break;
                    }
                    case 4:{
                        addStation();
                        break;
                    }
                    case 5:{
                        deleteLine();
                        break;
                    }
                    case 6:{
                        deleteStation();
                        break;
                    }
                }
            }
        }
        public void getAll() throws IOException {
            Metropolitan metropolitan = dao.getAll();
            objectOutputStream.writeObject(metropolitan);
        }
        public void updateLine() throws IOException {
            int lineId = in.readInt();
            int newId = in.readInt();
            String newColor = in.readUTF();
            dao.updateLine(lineId, newId, newColor);
        }
        public void updateStation() throws IOException {
            int lineId = in.readInt();
            int stationId = in.readInt();
            int newId = in.readInt();
            String newName = in.readUTF();
            dao.updateStation(lineId, stationId, newId, newName);

        }
        public void addLine() throws IOException {
            int lineId = in.readInt();
            String color = in.readUTF();
            dao.addLine(lineId, color);
        }
        public void addStation() throws IOException {
            int lineId = in.readInt();
            int stationId = in.readInt();
            String name = in.readUTF();
            dao.addStation(lineId, stationId, name);
        }
        public void deleteLine() throws IOException {
            int lineId = in.readInt();
            dao.deleteLine(lineId);
        }

        public void deleteStation() throws IOException {
            int lineId = in.readInt();
            int stationId = in.readInt();
            dao.deleteStation(lineId, stationId);
        }
        public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
            Server server = new Server();
            server.start(3636);
        }

    }


