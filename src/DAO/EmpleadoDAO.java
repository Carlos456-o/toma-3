/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.DetalleVenta;
import Modelo.Empleado;
import Util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiantes
 */
public class EmpleadoDAO {

    public void crearEmpleado(Empleado empleado) throws SQLException {
        String sql = """
            INSERT INTO Empleados (
            primer_nombre,
            segundo_nombre,
            primer_apellido,
            segundo_apellido,celular,
            cargo,
            fecha_contratacion
            ) VALUES (?, ?, ?, ?, ?, ?, ?)\"""";

        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, empleado.getPrimerNombre());
            stmt.setString(2, empleado.getSegundoNombre());
            stmt.setString(3, empleado.getPrimerApellido());
            stmt.setString(4, empleado.getSegundoApellido());
            stmt.setString(5, empleado.getCelular());
            stmt.setString(6, empleado.getCargo());
            stmt.setDate(7, new java.sql.Date(empleado.getFechaContratacion().getTime()));
            stmt.executeUpdate();
        }
    }
// Método Main

    public static void main(String[] args) {
        try {
            DetalleVentaDAO dao = new DetalleVentaDAO();

            // Actualizar un detalle de venta
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdDetalleVenta(1); // ID existente
            detalle.setIdVenta(1);
            detalle.setIdProducto(3);
            detalle.setCantidad(2);
            detalle.setPrecioUnitario(200.0f);
            dao.actualizarDetalleVenta(detalle);
            System.out.println("Detalle de venta actualizado.");

            // Eliminar un detalle de venta
            dao.eliminarDetalleVenta(2); // ID a eliminar
            System.out.println("Detalle de venta eliminado.");

            // Leer y mostrar todos los detalles de venta para verificar
            List<DetalleVenta> detalles = dao.leerTodosDetallesVenta();
            System.out.println("Lista de detalles de venta:");
            for (DetalleVenta det : detalles) {
                System.out.println("ID: " + det.getIdDetalleVenta()
                        + ", Venta ID: " + det.getIdVenta()
                        + ", Producto ID: " + det.getIdProducto()
                        + ", Cantidad: " + det.getCantidad()
                        + ", Precio Unitario: " + det.getPrecioUnitario());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public List<Empleado> leerTodosEmpleados() throws SQLException {
        String sql = "SELECT * FROM Empleados";
        List<Empleado> empleados = new ArrayList<>();

        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setPrimerNombre(rs.getString("primer_nombre"));
                empleado.setSegundoNombre(rs.getString("segundo_nombre"));
                empleado.setPrimerApellido(rs.getString("primer_apellido"));
                empleado.setSegundoApellido(rs.getString("segundo_apellido"));
                empleado.setCelular(rs.getString("celular"));
                empleado.setCargo(rs.getString("cargo"));
                empleado.setFechaContratacion(rs.getDate("fecha_contratacion"));
                empleados.add(empleado);
            }
        }
        return empleados;
    }
    // DetalleVentaDAO
// Métodos para Actualizar y Eliminar
// Método para actualizar un detalle de venta

    public void actualizarDetalleVenta(DetalleVenta detalle) throws SQLException {
        String sql = "UPDATE Detalles_Ventas SET id_venta = ?, id_producto = ?, cantidad = ?, precio_unitario = ? WHERE id_detalle_venta = ?";

        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdVenta());
            stmt.setInt(2, detalle.getIdProducto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setFloat(4, detalle.getPrecioUnitario());
            stmt.setInt(5, detalle.getIdDetalleVenta());
            stmt.executeUpdate();
        }
    }

// Método para eliminar un detalle de venta
    public void eliminarDetalleVenta(int idDetalleVenta) throws SQLException {
        String sql = "DELETE FROM Detalles_Ventas WHERE id_detalle_venta = ?";

        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, idDetalleVenta);
            stmt.executeUpdate();
        }
    }

}
