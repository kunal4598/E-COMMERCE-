package com.example.majorproject;

import java.sql.ResultSet;

public class Login {
    public Customer customerLogin(String userName, String password){
        String loginQuery = "SELECT * FROM customer WHERE email = '" + userName + "' AND password = '" + password + "'";
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(loginQuery);

        try {
            if(rs.next()){
                return  new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("Mobile"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        Customer customer = login.customerLogin("kshadan03@gmail.com", "abc123");
        System.out.println("welcome " + customer.getName());
    }
}
