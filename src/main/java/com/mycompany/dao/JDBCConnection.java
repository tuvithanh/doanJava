/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

/**
 *
 * @author VITHANH
 */
public class JDBCConnection {
    public static void main(String[] args) {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLSerVerDriver");
            String url = "jdbc:sqlserver://DESKTOP-8CNFBDC:1433"
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
