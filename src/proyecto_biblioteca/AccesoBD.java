/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_biblioteca;

import java.sql.*;

/**
 *
 * @author ceden
 */
public class AccesoBD {
    private final String host;
    private final String user;
    private final String password;
    private final String bd;

    //Abre o Cierra conexion
    private Connection conexion;

    public AccesoBD(String host, String user, String password, String bd) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.bd = bd;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getBd() {
        return bd;
    }

    public void conectarBD() {
        try {
            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
            String cadena = "jdbc:mysql://" + this.getHost() + "/" + this.getBd();
            conexion = DriverManager.getConnection(cadena, this.getUser(), this.getPassword());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println("Error de conexion a la base de datos " + e.getMessage());
            System.out.println("Error de conexion mensaje " + e.getMessage());
        }
    }

    public void actualizarBD(String sql) {
        try {
            Statement stm = conexion.createStatement();
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error en la Transaccion...." + e.getMessage());
        }
    }

    public ResultSet consultaBD(String sql) {
        try {
            ResultSet dato;
            Statement stm = conexion.createStatement();
            dato = stm.executeQuery(sql);
            return dato;
        } catch (SQLException e) {
            System.out.println("Error en la Transaccion...." + e.getMessage());
        }
        return null;
    }

    public void cerrarBD() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println("No se pudo cerrar la base de datos.. " + e.toString());
        }
    }

}
