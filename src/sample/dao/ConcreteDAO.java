package sample.dao;

import sample.Metropolitan;
import sample.Station;
import  sample.Line;

import java.sql.*;

public class ConcreteDAO implements DAO{
    static final String DB_URL = "jdbc:postgresql://localhost:5432/metropolitan";
    static final String USER = "postgres";
    static final String PASS = "password";


    Connection conn = null;
    Statement stmt = null;
    public ConcreteDAO() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }

    @Override
    public Metropolitan getAll(){
        Metropolitan metropolitan = new Metropolitan();
        try{
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT *  FROM line";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int lineId = rs.getInt("line_id");
                String color = rs.getString("color");
                metropolitan.addLine(lineId, color);

            }
            sql = "SELECT *  FROM station";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                int lineId = rs.getInt("line_id");
                int stationId = rs.getInt("station_id");
                String name = rs.getString("name");

                metropolitan.getLine(lineId).addStation(new Station(stationId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metropolitan;
    }

    @Override
    public void updateLine(int lineId, int newId, String newColor) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = " update line set newId=" + newId + ", color=\"" + newColor + "\" where lineId=" + lineId + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateStation(int lineId, int stationId, int newId, String newName){
        try {
            stmt = conn.createStatement();
            String sql;
            sql = " update station set newID=" + newId + ", new name=\"" + newName +
                    "\" where station id=" + stationId + " and line id=" + lineId +";";
            System.out.println(sql);
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addLine(int lineId, String color){
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "insert into line values (" + lineId + ", \"" + color + "\");";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public  void addStation(int lineId, int stationId, String name) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "insert into station values (" + lineId + ", " + stationId + ",\"" + name + "\");";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteLine(int lineId) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from line where ID = " + lineId + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStation(int lineId, int stationId) {
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from station where stationId = " + stationId + " and lineId=" + lineId + ";";
            int status = stmt.executeUpdate(sql);
            if(status < 0){
                System.out.println("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
