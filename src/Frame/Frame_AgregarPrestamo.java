/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import com.mysql.jdbc.ResultSet;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import proyecto_biblioteca.PrestamosBeans;

/**
 *
 * @author ceden
 */
public class Frame_AgregarPrestamo extends javax.swing.JFrame {

    PrestamosBeans prestamo;
    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    int height = pantalla.height;
    int width = pantalla.width;

    /**
     * Creates new form Frame_AgregarPrestamo
     */
    public Frame_AgregarPrestamo() {
        try {
            // Establecer el tema "Nimbus" como el aspecto visual
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            // Manejar cualquier excepción que ocurra al establecer el tema
        }
        getContentPane().setBackground(new java.awt.Color(237, 208, 198));
        initComponents();
        prestamo = new PrestamosBeans();
        setResizable(false);
        int h = pantalla.height + 50;
        setSize(width / 3, h / 3);
        setLocationRelativeTo(null);
        ID_Prestamo.setFocusable(false);
        ID_Prestamo.setText("" + prestamo.incremento());
        redimencionarBoton();
        Panel_Libro.setVisible(false);
        Panel_Persona.setVisible(false);
        fecha();
        agregarPersonaMouseListener();
        agregarLibroMouseListener();
    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("resources/Icono_Logo.png"));

        return retValue;
    }
    
    private void fecha() {
        Fecha_Prestamo.setEditable(false);

        // Obtén la fecha actual
        Date fechaActual = new Date();

        // Formatea la fecha en el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = dateFormat.format(fechaActual);

        // Establece la fecha formateada en el JTextField
        Fecha_Prestamo.setText(fechaFormateada);
    }

    private void mostrar_Libro() {
        try {
            ResultSet result;
            DefaultTableModel modelo = new DefaultTableModel();
            JTLibro.setModel(modelo);
            result = prestamo.Consultar_Libro_Nombre(Buscar_Libro.getText());
            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                modelo.addColumn(result.getMetaData().getColumnLabel(i).toUpperCase());
            }
            while (result.next()) {
                Object[] columna = new Object[result.getMetaData().getColumnCount()];
                for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
                    columna[i] = result.getObject(i + 1);
                }
                modelo.addRow(columna);
            }

            Panel_Libro.setVisible(true);

        } catch (SQLException e) {
            //JTLibro.setVisible(false);
            //JTLibro.revalidate();
        }
    }

    private void mostrar_Persona() {
        try {

            ResultSet result;
            DefaultTableModel modelo = new DefaultTableModel();
            JTPersona.setModel(modelo);
            result = prestamo.Consultar_Persona_Cedula(Buscar_Cliente.getText());
            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                modelo.addColumn(result.getMetaData().getColumnLabel(i).toUpperCase());
            }
            while (result.next()) {
                Object[] columna = new Object[result.getMetaData().getColumnCount()];
                for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
                    columna[i] = result.getObject(i + 1);
                }
                modelo.addRow(columna);
            }

            Panel_Persona.setVisible(true);

        } catch (SQLException e) {
            //JTLibro.setVisible(false);
            //JTLibro.revalidate();
        }
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
        Search_Libro.setIcon(iconoRedimensionado);
        Search_Cliente.setIcon(iconoRedimensionado);
    }

    private void agregarLibroMouseListener() {
        JTLibro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = JTLibro.getSelectedRow();
                Object valorSeleccionado = JTLibro.getValueAt(filaSeleccionada, 0);
                // Utiliza el valor seleccionado según tus necesidades
                prestamo.setId_Libro(Integer.parseInt(valorSeleccionado.toString()));
            }
        });
    }

    private void agregarPersonaMouseListener() {

        JTPersona.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = JTPersona.getSelectedRow();
                Object valorSeleccionado = JTPersona.getValueAt(filaSeleccionada, 0);
                // Utiliza el valor seleccionado según tus necesidades
                prestamo.setId_Persona(Integer.parseInt(valorSeleccionado.toString()));
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ID_Prestamo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Agregar = new javax.swing.JButton();
        Cancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Jlab = new javax.swing.JLabel();
        Buscar_Persona = new javax.swing.JLabel();
        Buscar_Libro = new javax.swing.JTextField();
        Search_Libro = new javax.swing.JButton();
        Buscar_Cliente = new javax.swing.JTextField();
        Search_Cliente = new javax.swing.JButton();
        Panel_Libro = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTLibro = new javax.swing.JTable();
        Panel_Persona = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JTPersona = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        Fecha_Prestamo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agregar Prestamo");
        setIconImage(getIconImage());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 33, 74));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Agregar Prestamo");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Agregar.setBackground(new java.awt.Color(122, 141, 155));
        Agregar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Agregar.setForeground(new java.awt.Color(0, 33, 74));
        Agregar.setText("Agregar Prestamo");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Cancelar.setBackground(new java.awt.Color(122, 141, 155));
        Cancelar.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Cancelar.setForeground(new java.awt.Color(0, 33, 74));
        Cancelar.setText("Cancelar");
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 33, 74));
        jLabel2.setText("ID_Prestamo:");

        Jlab.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Jlab.setForeground(new java.awt.Color(0, 33, 74));
        Jlab.setText("Libro:");

        Buscar_Persona.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Buscar_Persona.setForeground(new java.awt.Color(0, 33, 74));
        Buscar_Persona.setText("Cédula Cliente:");

        Search_Libro.setBackground(new java.awt.Color(122, 141, 155));
        Search_Libro.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Search_Libro.setForeground(new java.awt.Color(0, 33, 74));
        Search_Libro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search_LibroActionPerformed(evt);
            }
        });

        Search_Cliente.setBackground(new java.awt.Color(122, 141, 155));
        Search_Cliente.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        Search_Cliente.setForeground(new java.awt.Color(0, 33, 74));
        Search_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search_ClienteActionPerformed(evt);
            }
        });

        Panel_Libro.setBackground(new java.awt.Color(237, 208, 198));

        JTLibro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(JTLibro);

        javax.swing.GroupLayout Panel_LibroLayout = new javax.swing.GroupLayout(Panel_Libro);
        Panel_Libro.setLayout(Panel_LibroLayout);
        Panel_LibroLayout.setHorizontalGroup(
            Panel_LibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_LibroLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        Panel_LibroLayout.setVerticalGroup(
            Panel_LibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_LibroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        Panel_Persona.setBackground(new java.awt.Color(237, 208, 198));

        JTPersona.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        JTPersona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTPersonaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(JTPersona);

        javax.swing.GroupLayout Panel_PersonaLayout = new javax.swing.GroupLayout(Panel_Persona);
        Panel_Persona.setLayout(Panel_PersonaLayout);
        Panel_PersonaLayout.setHorizontalGroup(
            Panel_PersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PersonaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        Panel_PersonaLayout.setVerticalGroup(
            Panel_PersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_PersonaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setText("Fecha:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(65, 65, 65))
            .addComponent(Panel_Libro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Panel_Persona, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(Jlab, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ID_Prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Buscar_Libro, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Search_Libro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Buscar_Persona, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Buscar_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Search_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Fecha_Prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ID_Prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Search_Libro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Jlab)
                        .addComponent(Buscar_Libro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(Panel_Libro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Buscar_Persona)
                        .addComponent(Buscar_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Search_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(Panel_Persona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Fecha_Prestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Agregar)
                    .addComponent(Cancelar))
                .addGap(17, 17, 17))
        );

        Buscar_Libro.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
        Frame_Prestamo prest = new Frame_Prestamo();
        prest.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_CancelarActionPerformed

    private void Search_LibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_LibroActionPerformed
        ResultSet result = prestamo.Consultar_Libro_Nombre(Buscar_Libro.getText());
        try {
            result.next();
            result.getString(1);
            int h = height - 80;
            setSize(width / 3, h / 2);
            mostrar_Libro();
            setLocationRelativeTo(null);

        } catch (SQLException ex) {
            Object[] options = {"Aceptar"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "No hay registros",
                    "Mensaje",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == 0) {
                System.out.println("Se hizo clic en el botón Aceptar");
            }
        }


    }//GEN-LAST:event_Search_LibroActionPerformed

    private void Search_PersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_PersonaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Search_PersonaActionPerformed

    private void Search_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_ClienteActionPerformed
        ResultSet result = prestamo.Consultar_Persona_Cedula(Buscar_Cliente.getText());
        try {
            result.next();
            result.getString(1);
            mostrar_Persona();
            height = pantalla.height + 40;

            setSize(width / 3, height / 2);

            setLocationRelativeTo(null);

        } catch (SQLException ex) {
            Object[] options = {"Agregar Cliente", "Cancelar"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "No hay registros",
                    "Mensaje",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == 0) {
                Frame_Persona persona = new Frame_Persona();
                persona.setVisible(true);
                this.setVisible(false);
                prestamo.cerrar_Prestamo();
                
            } else if (choice == 1){
                System.out.println("Se canceló");
            }
        }

    }//GEN-LAST:event_Search_ClienteActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        prestamo.setId_Prestamo(Integer.parseInt(ID_Prestamo.getText()));
        prestamo.setFecha_Prestamo(java.sql.Date.valueOf(Fecha_Prestamo.getText()));
        prestamo.Insertar_Prestamo();
        Frame_Prestamo pers = new Frame_Prestamo();
        pers.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_AgregarActionPerformed

    private void JTPersonaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTPersonaMouseClicked
        JTPersona.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = JTPersona.getSelectedRow();
                Object valorSeleccionado = JTPersona.getValueAt(filaSeleccionada, 0);
                // Utiliza el valor seleccionado según tus necesidades
                prestamo.setId_Persona(Integer.parseInt(valorSeleccionado.toString()));
            }
        });
    }//GEN-LAST:event_JTPersonaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JTextField Buscar_Cliente;
    private javax.swing.JTextField Buscar_Libro;
    private javax.swing.JLabel Buscar_Persona;
    private javax.swing.JButton Cancelar;
    private javax.swing.JTextField Fecha_Prestamo;
    private javax.swing.JTextField ID_Prestamo;
    private javax.swing.JTable JTLibro;
    private javax.swing.JTable JTPersona;
    private javax.swing.JLabel Jlab;
    private javax.swing.JPanel Panel_Libro;
    private javax.swing.JPanel Panel_Persona;
    private javax.swing.JButton Search_Cliente;
    private javax.swing.JButton Search_Libro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
