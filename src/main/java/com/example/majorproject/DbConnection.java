package com.example.majorproject;
import java.sql.*;

public class DbConnection {
    private String dburl = "jdbc:mysql://localhost:3306/ecommerces";

    private String userName = "root";

    private String password = "123";

    private Statement getStatement(){
        try {
            Connection connection = DriverManager.getConnection(dburl,userName,password);
            return connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    //excute the query

    public ResultSet getQueryTable(String query){
        try {
            Statement statement = getStatement();
            return statement.executeQuery(query);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateDataBase(String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable("SELECT * FROM customer");

        if(rs != null){
            System.out.println("connection successfull");
        }
        else System.out.println("connection Failed");
    }

}
