package com.proyectoipc.modelo;

import com.proyectoipc.Entidades.Ensamble;
import com.proyectoipc.conexionSQL.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author elvis_agui
 */
public class ConsulDB {

    private PreparedStatement query = null;
    private ResultSet result = null;
    private Connection conexion = null;
    private int resul;
    
    /**
     * un lista depende si es ascendente o descentdne 
     * @param odenada
     * @param as
     * @return lista de piezas
     */
    public List listar(boolean odenada, boolean as) {
        String consultaAsc = "SELECT * FROM pieza ORDER BY cantidad";
        String consultaDes = "SELECT * FROM pieza ORDER BY cantidad desc";
        String consulta = "SELECT * FROM pieza";
        if (odenada) {
            if (as) {
                consulta = consultaAsc;
            } else {
                consulta = consultaDes;
            }
        }

        List<Pieza> lista = new ArrayList<>();
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            result = query.executeQuery();
            while (result.next()) {
                Pieza pieza = new Pieza();
                pieza.setNombre(result.getString("nombre"));
                pieza.setCantidad(result.getInt("cantidad"));
                pieza.setCosto(result.getDouble("costo"));
                lista.add(pieza);
            }
        } catch (SQLException e) {
            System.out.println("error en listar priductos");
        } finally {
            cierre();
        }

        return lista;
    }
    /**
     * busca la pieza por su nombre 
     * @param nombre
     * @return un Pieza buscada
     */
    public Pieza buscarPieza(String nombre) {
        Pieza econtrada = new Pieza();
        String consulta = "SELECT * FROM pieza WHERE nombre=?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, nombre);
            result = query.executeQuery();
            while (result.next()) {
                econtrada.setNombre(result.getString("nombre"));
                econtrada.setCantidad(result.getInt("cantidad"));
                econtrada.setCosto(result.getDouble("costo"));

            }
        } catch (SQLException ex) {
            System.out.println("error en bucarPieza");
        } finally {
            cierre();
        }

        return econtrada;
    }
    /**
     * lista todas las piezas a agotarse
     * @return 
     */
    public List listaAgotada() {
        String consulta = "SELECT * FROM pieza WHERE cantidad <= 10";
        List<Pieza> lista = new ArrayList<>();
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            result = query.executeQuery();
            while (result.next()) {
                Pieza pieza = new Pieza();
                pieza.setNombre(result.getString("nombre"));
                pieza.setCantidad(result.getInt("cantidad"));
                pieza.setCosto(result.getDouble("costo"));
                pieza.toString();
                lista.add(pieza);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listaAgotados");
        } finally {
            cierre();
        }
        return lista;
    }
    /**
     * lista todas los ensambles sin registrar en sala de ventas
     * @return 
     */
    public List listaParaVenta() {
        List<Ensamble> lista = new ArrayList<>();
        String consulta = "SELECT a.id, a.mueble, e.nombre, a.Fecha FROM ensamble a JOIN usuario e ON (a.ensamblador = e.contraseña) WHERE a.en_sala = 0";

        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            result = query.executeQuery();
            while (result.next()) {
                Ensamble Ensamble = new Ensamble();
                Ensamble.setId(Integer.parseInt(result.getString("id")));
                Ensamble.setMueble(result.getString("mueble"));
                Ensamble.setEnsamblador(result.getString("nombre"));
                Ensamble.setFecha(result.getDate("Fecha"));
                lista.add(Ensamble);
            }
        } catch (SQLException e) {
            System.out.println("error validar");
        } finally {
            cierre();
        }
        return lista;
    }

    public List infoMCreados(boolean ordenado, boolean as) {
        List<Ensamble> lista = new ArrayList<>();
        String consulta = "SELECT a.id, a.mueble, e.nombre, a.en_sala, a.Fecha FROM ensamble a JOIN usuario e ON (a.ensamblador = e.contraseña)";
        String consultaAsc = "SELECT a.id, a.mueble, e.nombre, a.en_sala, a.Fecha FROM ensamble a JOIN usuario e ON (a.ensamblador = e.contraseña) ORDER BY a.Fecha";
        String consultaDes = "SELECT a.id, a.mueble, e.nombre, a.en_sala, a.Fecha FROM ensamble a JOIN usuario e ON (a.ensamblador = e.contraseña) ORDER BY a.Fecha desc";
        if (ordenado) {
            if (as) {
                consulta = consultaAsc;
            } else {
                consulta = consultaDes;
            }
        }

        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            result = query.executeQuery();
            while (result.next()) {
                Ensamble Ensamble = new Ensamble();
                Ensamble.setId(Integer.parseInt(result.getString("id")));
                Ensamble.setMueble(result.getString("mueble"));
                Ensamble.setEnsamblador(result.getString("nombre"));
                Ensamble.setFecha(result.getDate("Fecha"));
                Ensamble.setEnSala(result.getBoolean("en_sala"));
                lista.add(Ensamble);
            }
        } catch (SQLException e) {
            System.out.println("error validar");
        } finally {
            cierre();
        }
        return lista;
    }

    public void enSala(String id) {
        try {
            String consulta = "UPDATE ensamble SET en_sala=1 WHERE id=?";
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, id);
            query.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error en Actualizar pieza");
        } finally {
            cierre();
        }

    }
    /**
     * actualiza las piezas aun no referenciadas
     * @param piezaActualizar
     * @param nombre
     * @return 
     */

    public int Actualizar(Pieza piezaActualizar, String nombre) {
        try {
            String consulta = "UPDATE pieza SET  costo=?, cantidad=? WHERE nombre=?";
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setDouble(1, piezaActualizar.getCosto());
            query.setInt(2, piezaActualizar.getCantidad());
            query.setString(3, nombre);
            query.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("No puedes Actualizar el nombre");
        } finally {
            cierre();
        }

        return resul;
    }

    public void eliminar(String nombre) {
        String consulta = "DELETE FROM pieza WHERE nombre=?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, nombre);
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("un Tipo de mueble necista esta pieza, no la puedes eliminar solo puedes eliminar Piezas que no le podrian servir a los muebles");
        } finally {
            cierre();
        }
    }
    /**
     * recibe una pieza para Insertarla en la base de datos
     * @param piezaCreada
     * @return 
     */
    public int CrearPieza(Pieza piezaCreada) {
        try {
            String consulta = "INSERT INTO pieza(nombre, costo, cantidad) VALUES (?,?,?)";
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, piezaCreada.getNombre());
            query.setDouble(2, piezaCreada.getCosto());
            query.setInt(3, piezaCreada.getCantidad());
            query.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error en pieza Creada");
        } finally {
            cierre();
        }
        return resul;
    }

    private void cierre() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar sql  db");
            }
        }
        if (result != null) {
            try {
                result.close();
            } catch (SQLException ex) {
                System.out.println("error al cerrar resul");
            }
        }
        if (query != null) {
            try {
                query.close();
            } catch (SQLException ex) {
                System.out.println("error al cerrar query");
            }
        }

    }

    public Usuario validar(String nombre, String contra) {
        Usuario us = new Usuario();
        String consulta = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, nombre);
            query.setString(2, contra);
            result = query.executeQuery();
            while (result.next()) {
                us.setNombre(result.getString("nombre"));
                us.setContra(result.getString("contraseña"));
                us.setRol(result.getInt("rol"));
                us.setActivo(result.getBoolean("activo"));

            }
        } catch (SQLException e) {
            System.out.println("error validar");
        } finally {
            cierre();
        }
        return us;
    }

}
