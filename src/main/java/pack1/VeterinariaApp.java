package pack1;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VeterinariaApp extends JFrame {
    private JTextField nombreField, tipoField, razaField, dueñoField;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable mascotasTable;
    private DefaultTableModel tableModel;
    private JButton btnLimpiar;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    public VeterinariaApp() {
        super("Administración de Veterinaria");

        nombreField = new JTextField(10);
        tipoField = new JTextField(10);
        razaField = new JTextField(10);
        dueñoField = new JTextField(10);

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Raza");
        tableModel.addColumn("Dueño");

        mascotasTable = new JTable(tableModel);

        // Crear interfaz y configurar eventos
        crearInterfaz();
        configurarEventos();

        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        // Inicializar EntityManager
        entityManagerFactory = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        entityManager = entityManagerFactory.createEntityManager();
    }

    private void crearInterfaz() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fila 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nombreField, gbc);

        // Fila 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(tipoField, gbc);

        // Fila 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Raza:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(razaField, gbc);

        // Fila 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Dueño:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(dueñoField, gbc);

        // Fila 5
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(btnGuardar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(btnActualizar, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        add(btnEliminar, gbc);

        // Fila 6
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        add(new JSeparator(), gbc);

        // Fila 7
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        add(new JLabel("Mascotas:"), gbc);

        // Fila 8
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        add(new JScrollPane(mascotasTable), gbc);
        // Fila 9
        gbc.gridx = 2;
        gbc.gridy = 7;
        add(btnLimpiar, gbc);
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarMascota();
                actualizarTabla();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarMascota();
                actualizarTabla();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarMascota();
                actualizarTabla();
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                mascotasTable.clearSelection(); // Deseleccionar cualquier fila seleccionada
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                entityManager.close();
                entityManagerFactory.close();
            }
        });
        // Agregar ActionListener a la tabla para manejar clics en las celdas
        mascotasTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = mascotasTable.rowAtPoint(evt.getPoint());
                int columna = mascotasTable.columnAtPoint(evt.getPoint());
                if (fila >= 0 && columna >= 0) {
                    llenarCamposDesdeTabla(fila);
                }
            }
        });
    }
    private void llenarCamposDesdeTabla(int fila) {
        nombreField.setText(mascotasTable.getValueAt(fila, 1).toString());
        tipoField.setText(mascotasTable.getValueAt(fila, 2).toString());
        razaField.setText(mascotasTable.getValueAt(fila, 3).toString());
        dueñoField.setText(mascotasTable.getValueAt(fila, 4).toString());
    }

    private void limpiarCampos() {
        nombreField.setText("");
        tipoField.setText("");
        razaField.setText("");
        dueñoField.setText("");
    }
    private void guardarMascota() {
        try {
            if (entityManager.getTransaction().isActive()) {
                if (entityManager.getTransaction().getRollbackOnly()) {
                    entityManager.getTransaction().rollback();
                } else {
                    entityManager.getTransaction().commit();
                }
            }

            entityManager.getTransaction().begin();

            String nombre = nombreField.getText();
            String tipo = tipoField.getText();
            String raza = razaField.getText();
            String nombreDueño = dueñoField.getText();

            Dueño dueño;

            // Buscar el Dueño en la base de datos por el nombre
            TypedQuery<Dueño> query = entityManager.createQuery("SELECT d FROM Dueño d WHERE d.nombre = :nombre", Dueño.class);
            query.setParameter("nombre", nombreDueño);

            try {
                dueño = query.getSingleResult();
            } catch (NoResultException ex) {
                // Si no se encuentra el dueño, crear uno nuevo
                dueño = new Dueño();
                dueño.setNombre(nombreDueño);
                entityManager.persist(dueño);
            }

            // Crear una nueva instancia de Mascota y establecer los valores
            Mascota nuevaMascota = new Mascota();
            nuevaMascota.setNombre(nombre);
            nuevaMascota.setTipo(tipo);
            nuevaMascota.setRaza(raza);
            nuevaMascota.setDueño(dueño);

            // Persistir la nueva mascota en la base de datos
            entityManager.persist(nuevaMascota);

            entityManager.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Mascota guardada correctamente");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la mascota");
            entityManager.getTransaction().rollback();
        }
    }

    private void actualizarMascota() {
        try {
            entityManager.getTransaction().begin();

            // Obtener el ID de la fila seleccionada en la tabla
            int filaSeleccionada = mascotasTable.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una mascota de la tabla para actualizar");
                return;
            }

            // Obtener el ID de la mascota desde la tabla
            Long idMascota = (Long) mascotasTable.getValueAt(filaSeleccionada, 0);

            // Buscar la mascota en la base de datos
            Mascota mascota = entityManager.find(Mascota.class, idMascota);
            if (mascota == null) {
                JOptionPane.showMessageDialog(this, "No se encontró la mascota en la base de datos");
                return;
            }

            // Actualizar los valores de la mascota con los nuevos valores de los campos
            mascota.setNombre(nombreField.getText());
            mascota.setTipo(tipoField.getText());
            mascota.setRaza(razaField.getText());

            String nombreDueño = dueñoField.getText();
            TypedQuery<Dueño> query = entityManager.createQuery("SELECT d FROM Dueño d WHERE d.nombre = :nombre", Dueño.class);
            query.setParameter("nombre", nombreDueño);

            Dueño nuevoDueño = null;
            try {
                nuevoDueño = query.getSingleResult();
            } catch (NoResultException ex) {
                // Si no se encuentra el dueño, crea uno nuevo
                nuevoDueño = new Dueño();
                nuevoDueño.setNombre(nombreDueño);
                entityManager.persist(nuevoDueño);
            }


            // Asignar el nuevo dueño a la mascota
            mascota.setDueño(nuevoDueño);

            entityManager.getTransaction().commit();

            // Actualizar la fila en la tabla con los nuevos datos
            mascotasTable.setValueAt(mascota.getId(), filaSeleccionada, 0);
            mascotasTable.setValueAt(mascota.getNombre(), filaSeleccionada, 1);
            mascotasTable.setValueAt(mascota.getTipo(), filaSeleccionada, 2);
            mascotasTable.setValueAt(mascota.getRaza(), filaSeleccionada, 3);
            mascotasTable.setValueAt(mascota.getDueño(), filaSeleccionada, 4);

            JOptionPane.showMessageDialog(this, "Mascota actualizada correctamente");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la mascota");
            entityManager.getTransaction().rollback();
        }
    }

    private void eliminarMascota() {
        try {
            entityManager.getTransaction().begin();

            // Obtener el ID de la fila seleccionada en la tabla
            int filaSeleccionada = mascotasTable.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una mascota de la tabla para eliminar");
                return;
            }

            // Obtener el ID de la mascota desde la tabla
            Long idMascota = (Long) mascotasTable.getValueAt(filaSeleccionada, 0);

            // Buscar la mascota en la base de datos
            Mascota mascota = entityManager.find(Mascota.class, idMascota);
            if (mascota == null) {
                JOptionPane.showMessageDialog(this, "No se encontró la mascota en la base de datos");
                return;
            }

            // Eliminar la mascota de la base de datos
            entityManager.remove(mascota);

            entityManager.getTransaction().commit();

            // Remover la fila de la tabla
            tableModel.removeRow(filaSeleccionada);

            JOptionPane.showMessageDialog(this, "Mascota eliminada correctamente");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la mascota");
            entityManager.getTransaction().rollback();
        }
    }


    private void actualizarTabla() {
        // Limpiar la tabla antes de actualizarla
        tableModel.setRowCount(0);

        // Realizar una consulta para obtener todas las mascotas
        TypedQuery<Mascota> query = entityManager.createQuery("SELECT m FROM Mascota m", Mascota.class);
        List<Mascota> mascotas = query.getResultList();

        // Llenar la tabla con los resultados de la consulta
        for (Mascota mascota : mascotas) {
            Object[] fila = {mascota.getId(), mascota.getNombre(), mascota.getTipo(), mascota.getRaza(), mascota.getDueño()};
            tableModel.addRow(fila);
        }

        // Repintar la tabla
        mascotasTable.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VeterinariaApp();
            }
        });
    }
}
