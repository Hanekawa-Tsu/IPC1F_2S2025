import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Proyecto2 {

    // Usuarios: [max 20][usuario, contrasena, rol]
    public static String[][] usuarios = new String[20][3];
    public static int totalUsuarios = 0;

    // Vendedores
    public static Vendedor[] vendedores = new Vendedor[200];
    public static int totalVendedores = 0;

    // usuario logueado y rol actual
    public static String usuarioLogueado = null;
    public static String rolLogueado = null;

    // Productos
    public static Producto[] productos = new Producto[200]; // arreglo de productos
    public static int totalProductos = 0; // contador de productos

    // Bandera para saber si la ventana admin esta abierta
    public static volatile boolean adminOpen = false;
    public static volatile boolean vendorOpen = false;
    public static volatile boolean clientOpen = false;

    // arreglo de pedidos
    public static Pedido[] pedidos = new Pedido[1000];
    public static int totalPedidos = 0;

    // Declaraciones para clientes, stock e historial de movimientos
    public static Cliente[] clientes = new Cliente[500]; // almacena clientes
    public static int totalClientes = 0; // contador de clientes
    public static int[] stockProductos = new int[200]; // stock paralelo
    public static double[] preciosProductos = new double[200]; // precios de producto
    public static Movimiento[] movimientos = new Movimiento[2000];
    public static int totalMovimientos = 0;

    // Metodo para registrar un usuario nuevo
    public static void registrarUsuario() {
        if (totalUsuarios >= 20) {
            JOptionPane.showMessageDialog(null, "No se pueden registrar mas usuarios.");
            return;
        }
        String usuario = JOptionPane.showInputDialog("Ingrese nombre de usuario:");
        if (usuario == null || usuario.trim().isEmpty()) return;
        String contrasena = JOptionPane.showInputDialog("Ingrese contrasena:");
        if (contrasena == null) return;
        int resp = JOptionPane.showConfirmDialog(null, "¿Este usuario tendra rol de administrador?", "Rol", JOptionPane.YES_NO_OPTION);
        String rol = (resp == JOptionPane.YES_OPTION) ? "admin" : "user";
        usuarios[totalUsuarios][0] = usuario;
        usuarios[totalUsuarios][1] = contrasena;
        usuarios[totalUsuarios][2] = rol;
        totalUsuarios++;
        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente (" + rol + ").");
    }

    // Autenticacion: busca en usuarios, vendedores y clientes
    public static String autenticarYObtenerRol() {
        String usuario = JOptionPane.showInputDialog("Usuario:");
        if (usuario == null) return null;
        String contrasena = JOptionPane.showInputDialog("Contrasena:");
        if (contrasena == null) return null;

        // Buscar en usuarios registrados (amdin/usuario)
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i][0] != null && usuarios[i][0].equals(usuario) && usuarios[i][1].equals(contrasena)) {
                usuarioLogueado = usuario;
                rolLogueado = usuarios[i][2];
                return rolLogueado;
            }
        }

        // Buscar vendedor
        for (int i = 0; i < totalVendedores; i++) {
            Vendedor v = vendedores[i];
            if (v != null && (v.getCodigo().equalsIgnoreCase(usuario) || v.getNombre().equalsIgnoreCase(usuario))
                    && v.getContrasena().equals(contrasena)) {
                usuarioLogueado = v.getCodigo();
                rolLogueado = "vendedor";
                return "vendedor";
            }
        }

        // Buscar cliente
        for (int i = 0; i < totalClientes; i++) {
            Cliente c = clientsafe(i);
            if (c != null && (c.getCodigo().equalsIgnoreCase(usuario) || c.getNombre().equalsIgnoreCase(usuario))
                    && c.getContrasena().equals(contrasena)) {
                usuarioLogueado = c.getCodigo();
                rolLogueado = "cliente";
                return "cliente";
            }
        }

        JOptionPane.showMessageDialog(null, "Usuario o contrasena incorrectos.");
        return null;
    }

    // Acceso seguro a cliente
    private static Cliente clientsafe(int i) {
        if (i < 0 || i >= totalClientes) return null;
        return clientes[i];
    }

    // Buscar indice de vendedor por codigo
    public static int buscarVendedorIndicePorCodigo(String codigo) {
        if (codigo == null) return -1;
        for (int i = 0; i < totalVendedores; i++) {
            if (vendedores[i] != null && vendedores[i].getCodigo().equalsIgnoreCase(codigo.trim())) return i;
        }
        return -1;
    }

    // Buscar indice de producto por codigo
    public static int buscarProductoIndicePorCodigo(String codigo) {
        if (codigo == null) return -1;
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equalsIgnoreCase(codigo.trim())) return i;
        }
        return -1;
    }

    // Crear vendedor interactivo
    public static boolean crearVendedorInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo (ej: VE-001):");
        if (codigo == null || codigo.trim().isEmpty()) return false;
        if (buscarVendedorIndicePorCodigo(codigo) != -1) {
            JOptionPane.showMessageDialog(parent, "Codigo ya existe.");
            return false;
        }
        String nombre = JOptionPane.showInputDialog(parent, "Nombre:");
        if (nombre == null) return false;
        String genero = JOptionPane.showInputDialog(parent, "Genero (M/F):");
        if (genero == null) return false;
        String contrasena = JOptionPane.showInputDialog(parent, "Contrasena:");
        if (contrasena == null) return false;
        if (totalVendedores >= vendedores.length) {
            JOptionPane.showMessageDialog(parent, "Capacidad de vendedores alcanzada.");
            return false;
        }
        vendedores[totalVendedores++] = new Vendedor(codigo.trim(), nombre.trim(), genero.trim(), contrasena.trim(), 0);
        return true;
    }

    // Actualizar vendedor
    public static boolean actualizarVendedorInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo del vendedor a buscar:");
        if (codigo == null) return false;
        int idx = buscarVendedorIndicePorCodigo(codigo);
        if (idx == -1) { JOptionPane.showMessageDialog(parent, "No encontrado."); return false; }
        Vendedor v = vendedores[idx];
        String nombre = JOptionPane.showInputDialog(parent, "Nombre:", v.getNombre());
        if (nombre == null) return false;
        String contrasena = JOptionPane.showInputDialog(parent, "Contrasena:", v.getContrasena());
        if (contrasena == null) return false;
        v.setNombre(nombre.trim());
        v.setContrasena(contrasena.trim());
        return true;
    }

    // Eliminar vendedor
    public static boolean eliminarVendedorInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo del vendedor a eliminar:");
        if (codigo == null) return false;
        int idx = buscarVendedorIndicePorCodigo(codigo);
        if (idx == -1) { JOptionPane.showMessageDialog(parent, "No encontrado."); return false; }
        int conf = JOptionPane.showConfirmDialog(parent, "¿Confirma eliminacion de " + vendedores[idx].getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return false;
        for (int i = idx; i < totalVendedores - 1; i++) vendedores[i] = vendedores[i + 1];
        vendedores[--totalVendedores] = null;
        return true;
    }

    // Productos: crear/actualizar/eliminar/cargar/ver detalle
    public static boolean crearProductoInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo (ej: PR-001):");
        if (codigo == null || codigo.trim().isEmpty()) return false;
        if (buscarProductoIndicePorCodigo(codigo) != -1) {
            JOptionPane.showMessageDialog(parent, "Codigo de producto ya existe.");
            return false;
        }
        String nombre = JOptionPane.showInputDialog(parent, "Nombre del producto:");
        if (nombre == null) return false;
        String[] opciones = {"Tecnologia", "Alimento", "Generales"};
        String categoria = (String) JOptionPane.showInputDialog(parent, "Seleccione categoria:", "Categoria",
                JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (categoria == null) return false;
        String atributo = JOptionPane.showInputDialog(parent, "Atributo (segun categoria):");
        if (atributo == null) return false;
        if (totalProductos >= productos.length) { JOptionPane.showMessageDialog(parent, "Capacidad de productos alcanzada."); return false; }

        // Insertar producto en el índice actual y pedir stock/precio
        int idx = totalProductos;
        productos[idx] = new Producto(codigo.trim(), nombre.trim(), categoria, atributo.trim());

        // stock inicial
        String sStock = JOptionPane.showInputDialog(parent, "Stock inicial (numero):", "0");
        int stock = 0;
        if (sStock != null) {
            try { stock = Integer.parseInt(sStock.trim()); } catch (NumberFormatException ignored) { stock = 0; }
        }
        stockProductos[idx] = Math.max(0, stock);

        // precio inicial
        String sPrecio = JOptionPane.showInputDialog(parent, "Precio unitario (ej: 10.50):", "0");
        double precio = 0.0;
        if (sPrecio != null) {
            try { precio = Double.parseDouble(sPrecio.trim()); } catch (NumberFormatException ignored) { precio = 0.0; }
        }
        preciosProductos[idx] = Math.max(0.0, precio);

        totalProductos++;
        return true;
    }

    // Actualizar producto
    public static boolean actualizarProductoInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo del producto a buscar:");
        if (codigo == null) return false;
        int idx = buscarProductoIndicePorCodigo(codigo);
        if (idx == -1) { JOptionPane.showMessageDialog(parent, "Producto no encontrado."); return false; }
        Producto p = productos[idx];
        String nombre = JOptionPane.showInputDialog(parent, "Nombre:", p.getNombre());
        if (nombre == null) return false;
        String atributo = JOptionPane.showInputDialog(parent, "Atributo unico:", p.getAtributoUnico());
        if (atributo == null) return false;

        // stock
        String sStock = JOptionPane.showInputDialog(parent, "Stock (numero):", String.valueOf(stockProductos[idx]));
        if (sStock != null) {
            try { stockProductos[idx] = Math.max(0, Integer.parseInt(sStock.trim())); } catch (NumberFormatException ignored) {}
        }

        // precio
        String sPrecio = JOptionPane.showInputDialog(parent, "Precio unitario (ej: 10.50):", String.valueOf(preciosProductos[idx]));
        if (sPrecio != null) {
            try { preciosProductos[idx] = Math.max(0.0, Double.parseDouble(sPrecio.trim())); } catch (NumberFormatException ignored) {}
        }

        p.setNombre(nombre.trim());
        p.setAtributoUnico(atributo.trim());
        return true;
    }

    // Eliminar Producto
    public static boolean eliminarProductoInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo del producto a eliminar:");
        if (codigo == null) return false;
        int idx = buscarProductoIndicePorCodigo(codigo);
        if (idx == -1) { JOptionPane.showMessageDialog(parent, "Producto no encontrado."); return false; }
        int conf = JOptionPane.showConfirmDialog(parent, "¿Confirma eliminacion de " + productos[idx].getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return false;
        for (int i = idx; i < totalProductos - 1; i++) productos[i] = productos[i + 1];
        productos[--totalProductos] = null;
        return true;
    }

    // Ver detalle de producto
    public static void verDetalleProducto(Component parent, String codigo) {
        int idx = buscarProductoIndicePorCodigo(codigo);
        if (idx == -1) { JOptionPane.showMessageDialog(parent, "Producto no encontrado."); return; }
        Producto p = productos[idx];
        String mensaje = p.getNombre() + "\nCategoria: " + p.getCategoria() + "\n";
        if ("Tecnologia".equalsIgnoreCase(p.getCategoria())) {
            mensaje += "Meses de garantia: " + p.getAtributoUnico();
        } else if ("Alimento".equalsIgnoreCase(p.getCategoria()) || "Alimentos".equalsIgnoreCase(p.getCategoria())) {
            mensaje += "Fecha de caducidad: " + p.getAtributoUnico();
        } else {
            mensaje += "Material: " + p.getAtributoUnico();
        }
        JOptionPane.showMessageDialog(parent, mensaje, "Detalle del producto", JOptionPane.INFORMATION_MESSAGE);
    }

    // Clientes
    public static boolean crearClienteInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo cliente (ej: CL-001):");
        if (codigo == null || codigo.trim().isEmpty()) return false;
        if (buscarClienteIndicePorCodigo(codigo) != -1) {
            JOptionPane.showMessageDialog(parent, "Codigo de cliente ya existe.");
            return false;
        }
        String nombre = JOptionPane.showInputDialog(parent, "Nombre del cliente:");
        if (nombre == null) return false;
        String genero = JOptionPane.showInputDialog(parent, "Genero (M/F):");
        if (genero == null) return false;
        String cumple = JOptionPane.showInputDialog(parent, "Fecha de cumpleanos (dd/mm/yyyy):");
        if (cumple == null) return false;
        String contrasena = JOptionPane.showInputDialog(parent, "Contrasena:");
        if (contrasena == null) return false;
        if (totalClientes >= clientes.length) { JOptionPane.showMessageDialog(parent, "Capacidad de clientes alcanzada."); return false; }
        clientes[totalClientes++] = new Cliente(codigo.trim(), nombre.trim(), genero.trim(), cumple.trim(), contrasena.trim());
        return true;
    }

    public static int buscarClienteIndicePorCodigo(String codigo) {
        if (codigo == null) return -1;
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i] != null && clientes[i].getCodigo().equalsIgnoreCase(codigo.trim())) return i;
        }
        return -1;
    }

    // Actualizar cliente
    public static boolean actualizarClienteInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo del cliente a buscar:");
        if (codigo == null) return false;
        int idx = buscarClienteIndicePorCodigo(codigo);
        if (idx == -1) { JOptionPane.showMessageDialog(parent, "Cliente no encontrado."); return false; }
        Cliente c = clientsafe(idx);
        String nombre = JOptionPane.showInputDialog(parent, "Nombre:", c.getNombre());
        if (nombre == null) return false;
        String genero = JOptionPane.showInputDialog(parent, "Genero:", c.getGenero());
        if (genero == null) return false;
        String cumple = JOptionPane.showInputDialog(parent, "Cumpleanos:", c.getCumpleanos());
        if (cumple == null) return false;
        String contrasena = JOptionPane.showInputDialog(parent, "Contrasena:", c.getContrasena());
        if (contrasena == null) return false;
        c.setNombre(nombre.trim());
        c.setGenero(genero.trim());
        c.setCumpleanos(cumple.trim());
        c.setContrasena(contrasena.trim());
        return true;
    }

    // Eliminar cliente
    public static boolean eliminarClienteInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo del cliente a eliminar:");
        if (codigo == null) return false;
        int idx = buscarClienteIndicePorCodigo(codigo);
        if (idx == -1) { JOptionPane.showMessageDialog(parent, "Cliente no encontrado."); return false; }
        int conf = JOptionPane.showConfirmDialog(parent, "¿Confirma eliminacion de " + clientes[idx].getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return false;
        for (int i = idx; i < totalClientes - 1; i++) clientes[i] = clientes[i + 1];
        clientes[--totalClientes] = null;
        return true;
    }

    // Stock y movimientos
    public static boolean registrarIngresoStock(String codigoProducto, int cantidad, String usuario) {
        if (codigoProducto == null) return false;
        int idx = buscarProductoIndicePorCodigo(codigoProducto);
        if (idx == -1) return false;
        stockProductos[idx] += cantidad;
        if (totalMovimientos < movimientos.length) {
            String fechaHora = new java.util.Date().toString();
            movimientos[totalMovimientos++] = new Movimiento(fechaHora, codigoProducto, cantidad, usuario);
        }
        return true;
    }

    // Agregar stock
    public static boolean agregarStockInteractivo(Component parent) {
        String codigo = JOptionPane.showInputDialog(parent, "Codigo del producto:");
        if (codigo == null) return false;
        String sCant = JOptionPane.showInputDialog(parent, "Cantidad a agregar (numero):");
        if (sCant == null) return false;
        int cantidad = 0;
        try { cantidad = Integer.parseInt(sCant.trim()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(parent, "Cantidad invalida."); return false; }
        boolean ok = registrarIngresoStock(codigo.trim(), cantidad, usuarioLogueado != null ? usuarioLogueado : "SYSTEM");
        if (!ok) { JOptionPane.showMessageDialog(parent, "Producto no encontrado."); return false; }
        JOptionPane.showMessageDialog(parent, "Stock actualizado.");
        return true;
    }

    // Ver historial de ingresos de un producto
    public static void verHistorialIngresosProducto(Component parent, String codigoProducto) {
        if (codigoProducto == null) { JOptionPane.showMessageDialog(parent, "Codigo invalido."); return; }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalMovimientos; i++) {
            Movimiento m = movimientos[i];
            if (m != null && m.getCodigoProducto().equalsIgnoreCase(codigoProducto.trim())) {
                sb.append(m.getFechaHora()).append(" - ").append(m.getCantidad()).append(" - ").append(m.getUsuario()).append("\n");
            }
        }
        if (sb.length() == 0) JOptionPane.showMessageDialog(parent, "No hay movimientos para el producto: " + codigoProducto);
        else {
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            JScrollPane sp = new JScrollPane(ta);
            sp.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(parent, sp, "Historial de ingresos - " + codigoProducto, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Abrir ventana segun rol
    public static void abrirVentanaPorRol(Component parent, String rol) {
        if ("admin".equalsIgnoreCase(rol)) {
            adminOpen = true;
            SwingUtilities.invokeLater(() -> {
                AdminFrame f = new AdminFrame();
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) { adminOpen = false; }
                });
                f.setVisible(true);
            });
            while (adminOpen) {
                try { Thread.sleep(100); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); break; }
            }
        } else if ("vendedor".equalsIgnoreCase(rol)) {
            vendorOpen = true;
            SwingUtilities.invokeLater(() -> {
                VendorFrame vf = new VendorFrame();
                vf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                vf.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) { vendorOpen = false; }
                });
                vf.setVisible(true);
            });
            while (vendorOpen) {
                try { Thread.sleep(100); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); break; }
            }
        } else if ("cliente".equalsIgnoreCase(rol) || "user".equalsIgnoreCase(rol)) {
            clientOpen = true;
            SwingUtilities.invokeLater(() -> {
                ClientFrame cf = new ClientFrame();
                cf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cf.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) { clientOpen = false; }
                });
                cf.setVisible(true);
            });
            while (clientOpen) {
                try { Thread.sleep(100); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); break; }
            }
        }
    }

    // Clase interna: item carrito
    static class CartItem {
        String codigoProducto;
        String nombre;
        int cantidad;
        int precioUnitario;
        CartItem(String codigoProducto, String nombre, int cantidad, int precioUnitario) {
            this.codigoProducto = codigoProducto;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }
        int total() { return cantidad * precioUnitario; }
    }

    // Clase interna: Pedido
    static class Pedido {
        String codigo;
        String fechaGeneracion;
        String codigoCliente;
        String nombreCliente;
        String descripcionItems;
        int total;
        String estado;
        Pedido(String codigo, String fechaGeneracion, String codigoCliente, String nombreCliente, String descripcionItems, int total, String estado) {
            this.codigo = codigo;
            this.fechaGeneracion = fechaGeneracion;
            this.codigoCliente = codigoCliente;
            this.nombreCliente = nombreCliente;
            this.descripcionItems = descripcionItems;
            this.total = total;
            this.estado = estado;
        }
    }

    // Ventanda Admin
    static class AdminFrame extends JFrame {
        DefaultTableModel modeloVendedores;
        JTable tablaVendedores;
        DefaultTableModel modeloProductos;
        JTable tablaProductos;

        public AdminFrame() {
            super("Modulo Administrador - Vendedores y Productos");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(1000, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            JTabbedPane tabs = new JTabbedPane();

            // Vendedores
            JPanel panelVendedores = new JPanel(new BorderLayout());
            modeloVendedores = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Genero", "Confirmados"}, 0) {
                public boolean isCellEditable(int row, int col) { return false; }
            };
            tablaVendedores = new JTable(modeloVendedores);
            panelVendedores.add(new JScrollPane(tablaVendedores), BorderLayout.CENTER);

            JPanel panelDerechoV = new JPanel(new GridLayout(6,1,5,5));
            JButton btnCrearV = new JButton("Crear");
            JButton btnCargarV = new JButton("Cargar CSV");
            JButton btnActualizarV = new JButton("Actualizar");
            JButton btnEliminarV = new JButton("Eliminar");
            JButton btnTop3V = new JButton("Top 3");
            JButton btnCerrarV = new JButton("Cerrar");
            panelDerechoV.add(btnCrearV);
            panelDerechoV.add(btnCargarV);
            panelDerechoV.add(btnActualizarV);
            panelDerechoV.add(btnEliminarV);
            panelDerechoV.add(btnTop3V);
            panelDerechoV.add(btnCerrarV);
            panelVendedores.add(panelDerechoV, BorderLayout.EAST);

            btnCrearV.addActionListener(e -> { if (crearVendedorInteractivo(this)) { refreshTablaVendedores(); JOptionPane.showMessageDialog(this, "Vendedor creado."); }});
            btnActualizarV.addActionListener(e -> { if (actualizarVendedorInteractivo(this)) { refreshTablaVendedores(); JOptionPane.showMessageDialog(this, "Vendedor actualizado."); }});
            btnEliminarV.addActionListener(e -> { if (eliminarVendedorInteractivo(this)) { refreshTablaVendedores(); JOptionPane.showMessageDialog(this, "Vendedor eliminado."); }});
            btnTop3V.addActionListener(e -> {
                int[] top = topNIndices(3);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < top.length; i++) {
                    if (top[i] == -1) break;
                    Vendedor v = vendedores[top[i]];
                    sb.append((i+1)).append(". ").append(v.getNombre()).append(" (").append(v.getCodigo()).append(") - ").append(v.getConfirmados()).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.length() == 0 ? "No hay vendedores." : sb.toString());
            });
            btnCerrarV.addActionListener(e -> dispose());

            // Productos
            JPanel panelProductos = new JPanel(new BorderLayout());
            modeloProductos = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Categoria", "Atributos", "Stock", "Precio"}, 0) {
                public boolean isCellEditable(int row, int col) { return false; }
            };
            tablaProductos = new JTable(modeloProductos);
            panelProductos.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

            JPanel panelDerechoP = new JPanel(new GridLayout(6,1,5,5));
            JButton btnCrearP = new JButton("Crear");
            JButton btnCargarP = new JButton("Cargar CSV");
            JButton btnActualizarP = new JButton("Actualizar");
            JButton btnEliminarP = new JButton("Eliminar");
            JButton btnVerDetalleP = new JButton("Ver detalle");
            JButton btnCerrarP = new JButton("Cerrar");
            panelDerechoP.add(btnCrearP);
            panelDerechoP.add(btnCargarP);
            panelDerechoP.add(btnActualizarP);
            panelDerechoP.add(btnEliminarP);
            panelDerechoP.add(btnVerDetalleP);
            panelDerechoP.add(btnCerrarP);
            panelProductos.add(panelDerechoP, BorderLayout.EAST);

            btnCrearP.addActionListener(e -> { if (crearProductoInteractivo(this)) { refreshTablaProductos(); JOptionPane.showMessageDialog(this, "Producto creado."); }});
            btnActualizarP.addActionListener(e -> { if (actualizarProductoInteractivo(this)) { refreshTablaProductos(); JOptionPane.showMessageDialog(this, "Producto actualizado."); }});
            btnEliminarP.addActionListener(e -> { if (eliminarProductoInteractivo(this)) { refreshTablaProductos(); JOptionPane.showMessageDialog(this, "Producto eliminado."); }});
            btnVerDetalleP.addActionListener(e -> {
                int fila = tablaProductos.getSelectedRow();
                if (fila >= 0) {
                    String codigo = (String) modeloProductos.getValueAt(fila, 0);
                    verDetalleProducto(this, codigo);
                } else {
                    String codigo = JOptionPane.showInputDialog(this, "Ingrese codigo del producto para ver detalle:");
                    if (codigo != null) verDetalleProducto(this, codigo);
                }
            });
            btnCerrarP.addActionListener(e -> dispose());

            tabs.addTab("Vendedores", panelVendedores);
            tabs.addTab("Productos", panelProductos);
            add(tabs, BorderLayout.CENTER);

            refreshTablaVendedores();
            refreshTablaProductos();
        }

        // Refrescar tablas vendedores
        public void refreshTablaVendedores() {
            modeloVendedores.setRowCount(0);
            for (int i = 0; i < totalVendedores; i++) {
                Vendedor v = vendedores[i];
                if (v == null) continue;
                modeloVendedores.addRow(new Object[]{v.getCodigo(), v.getNombre(), v.getGenero(), v.getConfirmados()});
            }
        }

        // Refrescar tabla productos
        public void refreshTablaProductos() {
            modeloProductos.setRowCount(0);
            for (int i = 0; i < totalProductos; i++) {
                Producto p = productos[i];
                if (p == null) continue;
                modeloProductos.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getCategoria(), p.getAtributoUnico(), stockProductos[i], preciosProductos[i]});
            }
        }
    }

    // Ventana Cliente
    static class ClientFrame extends JFrame {
        DefaultTableModel modeloProductosCliente;
        JTable tablaProductosCliente;
        DefaultTableModel modeloCarrito;
        JTable tablaCarrito;
        java.util.ArrayList<CartItem> carrito = new java.util.ArrayList<CartItem>();

        public ClientFrame() {
            super("Modulo Cliente");
            setSize(900, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            JTabbedPane tabs = new JTabbedPane();

            // Catalogo
            JPanel panelCatalogo = new JPanel(new BorderLayout());
            modeloProductosCliente = new DefaultTableModel(new Object[]{"Codigo","Nombre","Categoria","Stock","Precio"}, 0) {
                public boolean isCellEditable(int row, int col) { return false; }
            };
            tablaProductosCliente = new JTable(modeloProductosCliente);
            panelCatalogo.add(new JScrollPane(tablaProductosCliente), BorderLayout.CENTER);

            JPanel derecha = new JPanel(new GridLayout(3,1,5,5));
            JButton btnAgregarCarrito = new JButton("Agregar al carrito");
            JButton btnVerCarrito = new JButton("Ver carrito");
            JButton btnRefrescar = new JButton("Refrescar");
            derecha.add(btnAgregarCarrito);
            derecha.add(btnVerCarrito);
            derecha.add(btnRefrescar);
            panelCatalogo.add(derecha, BorderLayout.EAST);

            btnRefrescar.addActionListener(e -> refreshProductosCliente());
            btnAgregarCarrito.addActionListener(e -> {
                int fila = tablaProductosCliente.getSelectedRow();
                if (fila < 0) { JOptionPane.showMessageDialog(this, "Seleccione un producto."); return; }
                String codigo = (String) modeloProductosCliente.getValueAt(fila, 0);
                int idx = buscarProductoIndicePorCodigo(codigo);
                if (idx == -1) { JOptionPane.showMessageDialog(this, "Producto no encontrado."); return; }
                String sCant = JOptionPane.showInputDialog(this, "Cantidad a agregar:");
                if (sCant == null) return;
                int cant;
                try { cant = Integer.parseInt(sCant.trim()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Cantidad invalida."); return; }
                if (cant <= 0) { JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0."); return; }
                if (stockProductos[idx] < cant) { JOptionPane.showMessageDialog(this, "Stock insuficiente."); return; }
                int precioUnit = 10;
                carrito.add(new CartItem(codigo, productos[idx].getNombre(), cant, precioUnit));
                JOptionPane.showMessageDialog(this, "Item agregado al carrito.");
            });

            btnVerCarrito.addActionListener(e -> mostrarCarrito());

            // Carrito
            JPanel panelCarrito = new JPanel(new BorderLayout());
            modeloCarrito = new DefaultTableModel(new Object[]{"Codigo","Nombre","Cantidad","Precio unit","Total"},0) {
                public boolean isCellEditable(int row, int col) { return false; }
            };
            tablaCarrito = new JTable(modeloCarrito);
            panelCarrito.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);

            JPanel abajoCarrito = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnEliminarItem = new JButton("Eliminar item");
            JButton btnActualizarCantidad = new JButton("Actualizar cantidad");
            JButton btnRealizarPedido = new JButton("Realizar pedido");
            abajoCarrito.add(btnEliminarItem);
            abajoCarrito.add(btnActualizarCantidad);
            abajoCarrito.add(btnRealizarPedido);
            panelCarrito.add(abajoCarrito, BorderLayout.SOUTH);

            // eliminar item
            btnEliminarItem.addActionListener(e -> {
                int fila = tablaCarrito.getSelectedRow();
                if (fila < 0) { JOptionPane.showMessageDialog(this, "Seleccione item en carrito."); return; }
                carrito.remove(fila);
                mostrarCarrito();
            });

            // actualizar cantidad
            btnActualizarCantidad.addActionListener(e -> {
                int fila = tablaCarrito.getSelectedRow();
                if (fila < 0) { JOptionPane.showMessageDialog(this, "Seleccione item en carrito."); return; }
                String sCant = JOptionPane.showInputDialog(this, "Nueva cantidad:");
                if (sCant == null) return;
                int cant;
                try { cant = Integer.parseInt(sCant.trim()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Cantidad invalida."); return; }
                if (cant <= 0) { JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0."); return; }
                CartItem it = carrito.get(fila);
                int idx = buscarProductoIndicePorCodigo(it.codigoProducto);
                if (idx == -1) { JOptionPane.showMessageDialog(this, "Producto no encontrado."); return; }
                if (stockProductos[idx] < cant) { JOptionPane.showMessageDialog(this, "Stock insuficiente."); return; }
                it.cantidad = cant;
                mostrarCarrito();
            });

            // realizar pedido
            btnRealizarPedido.addActionListener(e -> {
                if (carrito.isEmpty()) { JOptionPane.showMessageDialog(this, "Carrito vacio."); return; }
                for (CartItem it : carrito) {
                    int idx = buscarProductoIndicePorCodigo(it.codigoProducto);
                    if (idx == -1) { JOptionPane.showMessageDialog(this, "Producto no encontrado: " + it.codigoProducto); return; }
                    if (stockProductos[idx] < it.cantidad) { JOptionPane.showMessageDialog(this, "Stock insuficiente para " + it.codigoProducto); return; }
                }
                int total = 0;
                StringBuilder desc = new StringBuilder();
                for (CartItem it : carrito) {
                    int idx = buscarProductoIndicePorCodigo(it.codigoProducto);
                    stockProductos[idx] -= it.cantidad;
                    total += it.total();
                    desc.append(it.codigoProducto).append(" x").append(it.cantidad).append("; ");
                }
                String codigoPedido = "PE-" + String.format("%03d", totalPedidos + 1);
                String fecha = new java.util.Date().toString();
                String codigoCliente = usuarioLogueado != null ? usuarioLogueado : "CL-000";
                String nombreCliente = "Cliente";
                int idxc = buscarClienteIndicePorCodigo(codigoCliente);
                if (idxc != -1) nombreCliente = clientes[idxc].getNombre();
                pedidos[totalPedidos++] = new Pedido(codigoPedido, fecha, codigoCliente, nombreCliente, desc.toString(), total, "PENDIENTE");
                carrito.clear();
                mostrarCarrito();
                refreshProductosCliente();
                JOptionPane.showMessageDialog(this, "Pedido creado y enviado para aprobacion. Codigo: " + codigoPedido);
            });

            tabs.addTab("Productos", panelCatalogo);
            tabs.addTab("Carrito Compra", panelCarrito);
            add(tabs, BorderLayout.CENTER);
            refreshProductosCliente();
        }

        // Actualizar tabla productos cliente
        void refreshProductosCliente() {
            modeloProductosCliente.setRowCount(0);
            for (int i = 0; i < totalProductos; i++) {
                Producto p = productos[i];
                if (p == null) continue;
                modeloProductosCliente.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getCategoria(), stockProductos[i], preciosProductos[i]});
            }
        }

        // Mostrar carrito
        void mostrarCarrito() {
            modeloCarrito.setRowCount(0);
            int suma = 0;
            for (CartItem it : carrito) {
                modeloCarrito.addRow(new Object[]{it.codigoProducto, it.nombre, it.cantidad, it.precioUnitario, it.total()});
                suma += it.total();
            }
            JOptionPane.showMessageDialog(this, new JScrollPane(tablaCarrito), "Carrito - Total: Q" + suma, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ver y confirmar pedidos
    static class VendorFrame extends JFrame {
        DefaultTableModel modeloPedidos;
        JTable tablaPedidos;

        public VendorFrame() {
            super("Modulo Vendedor");
            setSize(800, 450);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            modeloPedidos = new DefaultTableModel(new Object[]{"Codigo","Fecha","Codigo Cliente","Nombre Cliente","Total","Estado"},0) {
                public boolean isCellEditable(int row, int col) { return false; }
            };
            tablaPedidos = new JTable(modeloPedidos);
            add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

            JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnConfirmar = new JButton("Confirmar pedido");
            JButton btnRefrescar = new JButton("Refrescar");
            abajo.add(btnRefrescar);
            abajo.add(btnConfirmar);
            add(abajo, BorderLayout.SOUTH);

            btnRefrescar.addActionListener(e -> refreshPedidos());
            btnConfirmar.addActionListener(e -> {
                int fila = tablaPedidos.getSelectedRow();
                if (fila < 0) { JOptionPane.showMessageDialog(this, "Seleccione un pedido."); return; }
                String codigo = (String) modeloPedidos.getValueAt(fila, 0);
                for (int i = 0; i < totalPedidos; i++) {
                    if (pedidos[i] != null && pedidos[i].codigo.equals(codigo)) {
                        if ("CONFIRMADO".equalsIgnoreCase(pedidos[i].estado)) { JOptionPane.showMessageDialog(this, "Pedido ya confirmado."); return; }
                        pedidos[i].estado = "CONFIRMADO";
                        int idxv = buscarVendedorIndicePorCodigo(usuarioLogueado);
                        if (idxv != -1) {
                            Vendedor vv = vendedores[idxv];
                            vv.setConfirmados(vv.getConfirmados() + 1);
                        }
                        JOptionPane.showMessageDialog(this, "Pedido " + codigo + " confirmado.");
                        refreshPedidos();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Pedido no encontrado.");
            });

            refreshPedidos();
        }

        // Actualizar tabla pedidos
        void refreshPedidos() {
            modeloPedidos.setRowCount(0);
            for (int i = 0; i < totalPedidos; i++) {
                Pedido p = pedidos[i];
                if (p == null) continue;
                modeloPedidos.addRow(new Object[]{p.codigo, p.fechaGeneracion, p.codigoCliente, p.nombreCliente, "Q " + p.total, p.estado});
            }
        }
    }

    // Top N vendedores helper
    public static int[] topNIndices(int N) {
        int[] top = new int[N];
        for (int i = 0; i < N; i++) top[i] = -1;
        for (int k = 0; k < N; k++) {
            int mejor = -1;
            for (int i = 0; i < totalVendedores; i++) {
                if (vendedores[i] == null) continue;
                boolean ya = false;
                for (int t = 0; t < k; t++) if (top[t] == i) { ya = true; break; }
                if (ya) continue;
                if (mejor == -1 || vendedores[i].getConfirmados() > vendedores[mejor].getConfirmados()) {
                    mejor = i;
                }
            }
            top[k] = mejor;
        }
        return top;
    }

    public static void main(String[] args) {
        usuarios[0][0] = "admin";
        usuarios[0][1] = "admin";
        usuarios[0][2] = "admin";
        totalUsuarios = 1;

        String[] opciones = {"Registrar usuario", "Login", "Salir"};
        while (true) {
            int sel = JOptionPane.showOptionDialog(null, "Proyecto2 - Inicio", "Inicio", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            if (sel == -1 || sel == 2) {
                System.exit(0);
            }
            if (sel == 0) {
                registrarUsuario();
            } else if (sel == 1) {
                String rol = autenticarYObtenerRol();
                if (rol != null) {
                    abrirVentanaPorRol(null, rol);
                }
            }
        }
    }
}

// Clase Vendedor 
class Vendedor {
    private String codigo;
    private String nombre;
    private String genero;
    private String contrasena;
    private int confirmados;

    public Vendedor(String codigo, String nombre, String genero, String contrasena, int confirmados) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.contrasena = contrasena;
        this.confirmados = confirmados;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getGenero() { return genero; }
    public String getContrasena() { return contrasena; }
    public int getConfirmados() { return confirmados; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setConfirmados(int confirmados) { this.confirmados = confirmados; }
}

// Clase Producto
class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private String atributoUnico;

    public Producto(String codigo, String nombre, String categoria, String atributoUnico) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.atributoUnico = atributoUnico;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getAtributoUnico() { return atributoUnico; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAtributoUnico(String atributoUnico) { this.atributoUnico = atributoUnico; }
}

// Clase Cliente
class Cliente {
    private String codigo;
    private String nombre;
    private String genero;
    private String cumpleanos;
    private String contrasena;

    public Cliente(String codigo, String nombre, String genero, String cumpleanos, String contrasena) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.cumpleanos = cumpleanos;
        this.contrasena = contrasena;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getGenero() { return genero; }
    public String getCumpleanos() { return cumpleanos; }
    public String getContrasena() { return contrasena; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setCumpleanos(String cumpleanos) { this.cumpleanos = cumpleanos; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}

// Clase Movimiento para historial de stock
class Movimiento {
    private String fechaHora;
    private String codigoProducto;
    private int cantidad;
    private String usuario;

    public Movimiento(String fechaHora, String codigoProducto, int cantidad, String usuario) {
        this.fechaHora = fechaHora;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.usuario = usuario;
    }

    public String getFechaHora() { return fechaHora; }
    public String getCodigoProducto() { return codigoProducto; }
    public int getCantidad() { return cantidad; }
    public String getUsuario() { return usuario; }
}