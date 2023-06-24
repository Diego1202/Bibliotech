/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_biblioteca;

import com.mysql.jdbc.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author ceden
 */
public class DevolucionBeans {
    private int Id_Devolucion;
    private int Id_Prestamo;
    private Date Fecha_Devolucion;
    
    AccesoBD bd;

    public DevolucionBeans() {
        bd = new AccesoBD("localhost", "root", "12345678", "biblioteca");
        bd.conectarBD();
    }

    public int getId_Devolucion() {
        return Id_Devolucion;
    }

    public void setId_Devolucion(int Id_Devolucion) {
        this.Id_Devolucion = Id_Devolucion;
    }

    public int getId_Prestamo() {
        return Id_Prestamo;
    }

    public void setId_Prestamo(int Id_Prestamo) {
        this.Id_Prestamo = Id_Prestamo;
    }

    public Date getFecha_Devolucion() {
        return Fecha_Devolucion;
    }

    public void setFecha_Devolucion(Date Fecha_Devolucion) {
        this.Fecha_Devolucion = Fecha_Devolucion;
    }
    
    public int incremento() {
        int cont = 0;
        try {
            ResultSet rs;
            rs = (ResultSet) bd.consultaBD("Select max(Id_Devolucion) from Devolucion");
            if (rs.next()) {
                cont = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta");
        }
        return cont;
    }

    public void Insertar_Prestamo() {
        try {
            String cadena = "insert into Devolucion values ('" + this.getId_Devolucion() + "', '" + this.getId_Prestamo()+ "', '" + this.getFecha_Devolucion() + "')";
            bd.actualizarBD(cadena);
        } catch (Exception e) {
        }
    }

    public ResultSet Consultar_Prestamo_Id() {
        return (ResultSet) bd.consultaBD("SELECT * FROM Devolucion WHERE Id_Devolucion=" + this.getId_Devolucion());
    }

    public ResultSet Consultar_Prestamos() {
        return (ResultSet) bd.consultaBD("""
                                         SELECT p.NOMBRE, p.APELLIDO, l.NOMBRE AS NOMBRE_LIBRO, pr.fecha, CASE WHEN d.ID_DEVOLUCION IS NULL THEN false ELSE d.fecha END AS ESTADO_DEVOLUCION
                                         FROM PERSONA p
                                         JOIN PRESTAMO pr ON p.ID_PERSONA = pr.ID_PERSONA
                                         JOIN LIBRO l ON pr.ID_LIBRO = l.ID_LIBRO
                                         LEFT JOIN DEVOLUCION d ON pr.ID_PRESTAMO = d.ID_PRESTAMOS;""");
    }

    public void Actualizar_Prestamo() {
        try {
            String cadena = "UPDATE Devolucion set Id_Prestamo='" + this.getId_Prestamo()+ "', fecha='" + this.getFecha_Devolucion() + "' WHERE Id_Devolucion="+this.getId_Devolucion();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
        }
    }

    public void Eliminar_Prestamo() {
        try {
            String cadena = "DELETE FROM Devolucion WHERE Id_Devolucion = " + this.getId_Devolucion();
            bd.actualizarBD(cadena);
        } catch (Exception e) {
            System.out.println("No se pudo realizar la eliminación de la Persona " + e.getMessage());
        }
    }
    
    public void Cerrar_Devolucion(){
        bd.cerrarBD();
    }

    @Override
    public String toString() {
        return "DevolucionBeans{" + "Id_Devolucion=" + Id_Devolucion + ", Id_Prestamo=" + Id_Prestamo + ", Fecha_Devolucion=" + this.getFecha_Devolucion() + '}';
    }
    
    
    
}
