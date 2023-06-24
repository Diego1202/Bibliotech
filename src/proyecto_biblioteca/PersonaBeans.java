/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_biblioteca;

import com.mysql.jdbc.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ceden
 */
public class PersonaBeans {

    private int Id_Persona;
    private String Cedula;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String Telefono;

    AccesoBD bd;

    public PersonaBeans() {
        bd = new AccesoBD("localhost", "root", "12345678", "biblioteca");
        bd.conectarBD();
    }

    public int getId_Persona() {
        return Id_Persona;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public void setId_Persona(int Id_Persona) {
        this.Id_Persona = Id_Persona;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public int incremento() {
        int cont = 0;
        try {
            ResultSet rs;
            rs = (ResultSet) bd.consultaBD("Select max(Id_Persona) from Persona");
            if (rs.next()) {
                cont = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta " + ex.getMessage());
        }
        return cont;
    }

    public void insertar_Persona() {
        try {
            String cadena = "Insert into Persona values ('" + this.getId_Persona() + "','" + this.getCedula() + "','" + this.getNombre() + "','" + this.getApellido() + "','" + this.getDireccion() + "','" + this.getTelefono() + "')";
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la insercion de la Persona " + e.getMessage());
        }
    }

    public ResultSet Consulta_Persona() {
        return (ResultSet) bd.consultaBD("SELECT id_persona as id, cedula, nombre, apellido, direccion, telefono, id_persona as ' ' FROM PERSONA");
    }

    public ResultSet Consultar_Persona_ID() {
        return (ResultSet) bd.consultaBD("SELECT * FROM PERSONA WHERE Id_Persona =" + this.getId_Persona() + "");
    }

    public void Actualizar_Persona() {
        try {
            String cadena = "UPDATE Persona SET CEDULA='" + this.getCedula() + "', NOMBRE='" + this.getNombre() + "', APELLIDO='" + this.getApellido() + "', DIRECCION='" + this.getDireccion() + "', telefono='" + this.getTelefono() + "' WHERE Id_Persona=" + this.getId_Persona();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la actualizacion de la Persona " + e.getMessage());
        }
    }

    public void Eliminar_Persona() {
        try {
            String cadena = "DELETE FROM PERSONA WHERE Id_Persona=" + this.getId_Persona();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la eliminación de la Persona " + e.getMessage());
        }
    }

    public void cerrar_Persona() {
        bd.cerrarBD();
    }

}
