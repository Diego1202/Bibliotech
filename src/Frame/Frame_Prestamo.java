/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import proyecto_biblioteca.PrestamosBeans;

/**
 *
 * @author ceden
 */
public class Frame_Prestamo extends javax.swing.JFrame {

    PrestamosBeans prestamo;
    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    int height = pantalla.height;
    int width = pantalla.width;

    /**
     * Creates new form Frame_Prestamo
     */
    public Frame_Prestamo() {
        try {
            // Establecer el tema "Nimbus" como el aspecto visual
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            // Manejar cualquier excepción que ocurra al establecer el tema
            ex.printStackTrace();
        }
        getContentPane().setBackground(new java.awt.Color(237, 208, 198));
        initComponents();
        prestamo = new PrestamosBeans();
        Existe_Cliente.setBackground(new java.awt.Color(237, 208, 198));
        setSize(width / 2, height / 3);
        setLocationRelativeTo(null);
        redimencionarBoton();

        DefaultTableModel modelo = new DefaultTableModel();
        JTPrestamo.setModel(modelo);
        ResultSet result = prestamo.Consultar_Persona_Cedula_Prestamo(Input_Cedula_Cliente.getText());
        try {
            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                modelo.addColumn(result.getMetaData().getColumnLabel(i).toUpperCase());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Frame_Prestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        agregarIdPrestamoMouseListener();
    }
    
    private void agregarIdPrestamoMouseListener() {
        JTPrestamo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = JTPrestamo.getSelectedRow();
                Object valorSeleccionado = JTPrestamo.getValueAt(filaSeleccionada, 0);
                // Utiliza el valor seleccionado según tus necesidades
                Frame_Detalles detalle = new Frame_Detalles(Integer.parseInt(valorSeleccionado.toString()));
                detalle.setVisible(true);
                cierre();
                System.out.println("Se hace click");
            }
        });
    }
    
    private void cierre(){
        this.setVisible(false);
    }

    private void redimencionarBoton() {
        // Cargar la imagen del ícono
        ImageIcon icono = new ImageIcon(getClass().getResource("/resources/search_icono.png"));

        // Obtener la imagen del ícono y redimensionarla
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon con la imagen redimensionada
        ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);

        // Establecer el nuevo ícono en el botón
        Buscar_Cliente.setIcon(iconoRedimensionado);
    }

    private void Existe() {
        Existe_Cliente.setBackground(new java.awt.Color(237, 208, 198));
        Existe_Cliente.setVisible(true);
        Existe_Cliente.revalidate();

        mostrar();
    }

    private void No_Existe() {
        Existe_Cliente.setVisible(false);
        Existe_Cliente.revalidate();
    }

    public void mostrar() {
        try {
            ResultSet result;
            DefaultTableModel modelo = new DefaultTableModel();
            JTPrestamo.setModel(modelo);
            result = prestamo.Consultar_Persona_Cedula_Prestamo(Input_Cedula_Cliente.getText());
            ResultSetMetaData rsmd;
            rsmd = (ResultSetMetaData) result.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                modelo.addColumn(rsmd.getColumnLabel(i).toUpperCase());
            }
            while (result.next()) {
                Object[] columna = new Object[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columna[i] = result.getObject(i + 1);
                }
                modelo.addRow(columna);
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/Icono_Logo.png"));

        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Agregar_Devolucion = new javax.swing.JButton();
        Retornar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Input_Cedula_Cliente = new javax.swing.JTextField();
        Buscar_Cliente = new javax.swing.JButton();
        Existe_Cliente = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTPrestamo = new javax.swing.JTable();
        Agregar_Prestamo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prestamos");
        setIconImage(getIconImage());

        Agregar_Devolucion.setBackground(new java.awt.Color(122, 141, 155));
        Agregar_Devolucion.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar_Devolucion.setForeground(new java.awt.Color(0, 33, 74));
        Agregar_Devolucion.setText("Devolucion Libro");
        Agregar_Devolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Agregar_DevolucionActionPerformed(evt);
            }
        });

        Retornar.setBackground(new java.awt.Color(122, 141, 155));
        Retornar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Retornar.setForeground(new java.awt.Color(0, 33, 74));
        Retornar.setText("Regresar");
        Retornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetornarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setText("Cedula:");

        Input_Cedula_Cliente.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        Input_Cedula_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Input_Cedula_ClienteActionPerformed(evt);
            }
        });

        Buscar_Cliente.setBackground(new java.awt.Color(122, 141, 155));
        Buscar_Cliente.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Buscar_Cliente.setForeground(new java.awt.Color(0, 33, 74));
        Buscar_Cliente.setText("Buscar");
        Buscar_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar_ClienteActionPerformed(evt);
            }
        });

        JTPrestamo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(JTPrestamo);

        javax.swing.GroupLayout Existe_ClienteLayout = new javax.swing.GroupLayout(Existe_Cliente);
        Existe_Cliente.setLayout(Existe_ClienteLayout);
        Existe_ClienteLayout.setHorizontalGroup(
            Existe_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Existe_ClienteLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        Existe_ClienteLayout.setVerticalGroup(
            Existe_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Existe_ClienteLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        Agregar_Prestamo.setBackground(new java.awt.Color(122, 141, 155));
        Agregar_Prestamo.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar_Prestamo.setForeground(new java.awt.Color(0, 33, 74));
        Agregar_Prestamo.setText("Agregar Prestamo");
        Agregar_Prestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Agregar_PrestamoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(Input_Cedula_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Buscar_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(Existe_Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Agregar_Devolucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Retornar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar_Prestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Input_Cedula_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(Agregar_Prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(Agregar_Devolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(Retornar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Existe_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetornarActionPerformed
        Principal principal = new Principal();
        principal.setVisible(true);
        prestamo.cerrar_Prestamo();
        this.setVisible(false);
    }//GEN-LAST:event_RetornarActionPerformed

    private void Agregar_DevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Agregar_DevolucionActionPerformed
        Frame_Devolucion devolucion = new Frame_Devolucion();
        devolucion.setVisible(true);
        prestamo.cerrar_Prestamo();
        this.setVisible(false);
    }//GEN-LAST:event_Agregar_DevolucionActionPerformed

    private void Buscar_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar_ClienteActionPerformed

        ResultSet result = prestamo.Consultar_Persona_Cedula_Prestamo(Input_Cedula_Cliente.getText());
        try {
            result.next();
            result.getString(2);
            Existe();

        } catch (SQLException ex) {
            No_Existe();
        }
    }//GEN-LAST:event_Buscar_ClienteActionPerformed

    private void Agregar_PrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Agregar_PrestamoActionPerformed
        Frame_AgregarPrestamo aggPrestamo = new Frame_AgregarPrestamo();
        aggPrestamo.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Agregar_PrestamoActionPerformed

    private void Input_Cedula_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Input_Cedula_ClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Input_Cedula_ClienteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar_Devolucion;
    private javax.swing.JButton Agregar_Prestamo;
    private javax.swing.JButton Buscar_Cliente;
    private javax.swing.JPanel Existe_Cliente;
    private javax.swing.JTextField Input_Cedula_Cliente;
    private javax.swing.JTable JTPrestamo;
    private javax.swing.JButton Retornar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
