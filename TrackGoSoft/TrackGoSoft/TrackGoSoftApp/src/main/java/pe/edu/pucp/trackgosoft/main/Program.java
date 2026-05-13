package pe.edu.pucp.trackgosoft.main;

import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.bo.empresa.EmpresaBO;
import pe.edu.pucp.trackgosoft.bo.empresa.EmpresaBOImpl;
import pe.edu.pucp.trackgosoft.bo.transporte.TransporteBO;
import pe.edu.pucp.trackgosoft.bo.transporte.TransporteBOImpl;
import pe.edu.pucp.trackgosoft.bo.direccion.DireccionBO;
import pe.edu.pucp.trackgosoft.bo.direccion.DireccionBOImpl;
import pe.edu.pucp.trackgosoft.bo.usuario.EmpleadoBO;
import pe.edu.pucp.trackgosoft.bo.usuario.EmpleadoBOImpl;
import pe.edu.pucp.trackgosoft.bo.usuario.AdministradorBO;
import pe.edu.pucp.trackgosoft.bo.usuario.AdministradorBOImpl;
import pe.edu.pucp.trackgosoft.bo.pedido.PedidoBO;
import pe.edu.pucp.trackgosoft.bo.pedido.PedidoBOImpl;
import pe.edu.pucp.trackgosoft.bo.usuario.GestorAutenticacion;
import pe.edu.pucp.trackgosoft.bo.usuario.GestorAutenticacionImpl;
import pe.edu.pucp.trackgosoft.bo.pedido.GestorDePedidos;
import pe.edu.pucp.trackgosoft.bo.tracking.RegistroDeTracking;
import pe.edu.pucp.trackgosoft.bo.reportes.GestorDeReportes;

import pe.edu.pucp.trackgosoft.modelo.empresa.Empresa;
import pe.edu.pucp.trackgosoft.modelo.transporte.Transporte;
import pe.edu.pucp.trackgosoft.modelo.direccion.Direccion;
import pe.edu.pucp.trackgosoft.modelo.pedido.EstadoPedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;
import pe.edu.pucp.trackgosoft.modelo.usuario.Cargo;
import pe.edu.pucp.trackgosoft.modelo.usuario.Empleado;
import pe.edu.pucp.trackgosoft.modelo.usuario.Administrador;
import pe.edu.pucp.trackgosoft.modelo.usuario.Turno;

import java.util.Date;
import java.util.List;

/**
 * Clase principal de pruebas de integración que demuestra
 * el funcionamiento de todos los CRUDs a través de la Capa de Negocio (BOs).
 */
public class Program {
    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("   INICIANDO PRUEBAS DE TODOS LOS CRUD (N-CAPAS)");
        System.out.println("==================================================\n");

        // Instancias de la capa de negocio
        EmpresaBO empresaBO = new EmpresaBOImpl();
        TransporteBO transporteBO = new TransporteBOImpl(); // Instanciado directo para acceder a métodos con String
        DireccionBO direccionBO = new DireccionBOImpl();
        EmpleadoBO empleadoBO = new EmpleadoBOImpl();
        AdministradorBO administradorBO = new AdministradorBOImpl();
        PedidoBO pedidoBO = new PedidoBOImpl(); // Modificado a interfaz para mejor abstracción

        // Variables dinámicas para evitar colisiones
        String suffix = String.valueOf(System.currentTimeMillis()).substring(8);
        String ruc = "111111" + suffix;
        String placa1 = "T-01" + suffix;
        String placa2 = "T-02" + suffix;
        String placa3 = "T-03" + suffix;
        
        int idDireccion = -1;
        // CORRECCIÓN: Los IDs de pedido ahora son enteros, acordes a la nueva BD
        int idPedido = -1;
        int idPedido2 = -1;
        int idEmpTemp = -1;
        int idEmpleado = -1;
        int idAdmin = -1;
        Empresa empresaBD = null;

        try {
            // =========================================================================
            // CRUD EMPRESA
            // =========================================================================
            System.out.println("\n=== EMPRESA ===");
            
            // CREATE
            Empresa empresa = new Empresa("EmpresitaFantasma", ruc, "Av. Narnia 12", "Servicios", new Date());
            empresaBO.guardar(empresa, EstadoOperacion.Nuevo);
            System.out.println("Se creó la empresita: " + empresa.getRuc());

            // READ
            System.out.println("\nEmpresa leída:");
            empresaBD = empresaBO.buscarPorRuc(ruc);
            System.out.println(empresaBD);

            // UPDATE
            empresaBD.setNombre("Empresita Fantasmona Cambiada");
            empresaBD.setDireccion("Av. Nueva vida 2000");
            empresaBO.guardar(empresaBD, EstadoOperacion.Modificado);
            System.out.println("\nEmpresa actualizada:");
            System.out.println(empresaBO.buscarPorRuc(ruc));


            // =========================================================================
            // CRUD TRANSPORTE
            // =========================================================================
            System.out.println("\n=== TRANSPORTE ===");
            Transporte t1 = new Transporte(placa1, "Terreneitor", "Fotorama", "4x4 Turbo");
            Transporte t2 = new Transporte(placa2, "Camión Urbano", "Volvo", "FH16");
            Transporte t3 = new Transporte(placa3, "Mini Van", "Toyota", "Hiace");

            // CREATE
            transporteBO.guardar(t1, EstadoOperacion.Nuevo);
            transporteBO.guardar(t2, EstadoOperacion.Nuevo);
            transporteBO.guardar(t3, EstadoOperacion.Nuevo);
            System.out.println("Transportes creados con placas: " + placa1 + ", " + placa2 + ", " + placa3);

            // READ
            System.out.println("\nBuscamos Transporte con placa (" + placa2 + "):");
            Transporte tBD = transporteBO.obtenerPorPlaca(placa2);
            if (tBD != null) {
                System.out.println("Encontrado: " + tBD.getMarca() + " - " + tBD.getModelo());
            }

            // UPDATE
            if (tBD != null) {
                tBD.setMarca("Volvo Actualizado");
                tBD.setModelo("FH16 XL");
                transporteBO.guardar(tBD, EstadoOperacion.Modificado);
                System.out.println("Transporte actualizado: " + placa2);
            }

            // LISTAR TODOS
            System.out.println("\nLista total de transportes:");
            List<Transporte> listaTransportes = transporteBO.listar();
            for (Transporte tr : listaTransportes) {
                System.out.println(" - " + tr.getPlaca() + " | " + tr.getMarca());
            }

            // DELETE
            transporteBO.eliminar(placa1);
            System.out.println("\nSe eliminó el transporte: " + placa1);


            // =========================================================================
            // CRUD PEDIDO
            // =========================================================================
            System.out.println("\n=== PEDIDO ===");
            
            // Pre-requisito: Dirección
            Direccion direccion = new Direccion("Lima", "Lima", "San Miguel", "15087", "Av. La Marina 123", "Frente a la UNI");
            direccionBO.guardar(direccion, EstadoOperacion.Nuevo);
            idDireccion = direccion.getIdDireccion();

            // Necesitamos crear al empleado PRIMERO para que tenga un ID válido
            pe.edu.pucp.trackgosoft.modelo.usuario.Empleado empTemp = new pe.edu.pucp.trackgosoft.modelo.usuario.Empleado("EMP-TEMP", "88888888", "Juan", "Temp", "j@temp.com", "hash", "999", pe.edu.pucp.trackgosoft.modelo.usuario.Cargo.TRANSPORTISTA, "A-1", pe.edu.pucp.trackgosoft.modelo.usuario.Turno.MANHANA);
            empleadoBO.guardar(empTemp, EstadoOperacion.Nuevo);
            idEmpTemp = empTemp.getIdUsuario();
            
            Pedido pedido = new Pedido("Juan Perez", 25.50, EstadoPedido.EN_AGENCIA, idEmpTemp, direccion, empresaBD);
            pedido.agregarDetalle("Teclado Mecanico", 2);

            // CREATE (Transaccional)
            pedidoBO.guardar(pedido, EstadoOperacion.Nuevo);
            idPedido = pedido.getIdPedido(); // Retorna int
            System.out.println("Pedido transaccional creado. ID Interno: " + idPedido + " | Código Visible: " + pedido.getCodigoPedido());

            // READ - CORRECCIÓN: obtener() usa el int
            Pedido pedidoRecuperado = pedidoBO.obtener(idPedido);
            System.out.println("Pedido leído: Destinatario=" + pedidoRecuperado.getDestinatario() + ", Detalles=" + pedidoRecuperado.getDetalleDePedido().size());

            // UPDATE SIN TRANSPORTE
            pedidoRecuperado.setDestinatario("Juan Perez Actualizado");
            pedidoRecuperado.setTarifaEnvio(35.00);
            pedidoRecuperado.setEstado(EstadoPedido.SALIDA_A_RUTA);
            pedidoBO.guardar(pedidoRecuperado, EstadoOperacion.Modificado);
            System.out.println("Pedido modificado exitosamente (sin transporte).");

            // UPDATE CON TRANSPORTE
            Transporte transporteAsignado = transporteBO.obtenerPorPlaca(placa2);
            pedidoRecuperado.asignarInformacionTransporte(idEmpTemp, transporteAsignado);
            pedidoRecuperado.registrarEstado(); // Genera nuevo historial
            pedidoBO.guardar(pedidoRecuperado, EstadoOperacion.Modificado);
            System.out.println("Pedido modificado exitosamente (con transporte asignado y nuevo historial).");

            // READ FINAL
            System.out.println("Pedido final:");
            System.out.println(pedidoBO.obtener(idPedido));


            // =========================================================================
            // CRUD EMPLEADO
            // =========================================================================
            System.out.println("\n=== CRUD EMPLEADO ===");
            
            // Usamos Crypto para generar un hash real para la prueba
            String claveEmpleadoPlana = "passEmpleado123";
            String hashEmpleado = pe.edu.pucp.trackgosoft.db.utils.Crypto.encrypt(claveEmpleadoPlana);
            
            // CREATE
            Empleado empleadoNuevo = new Empleado("EMP-" + suffix, "777" + suffix, "Carlos", "Perez", "carlos" + suffix + "@trackgo.com",
                    hashEmpleado, "999888777", Cargo.TRANSPORTISTA, "A-IIB", Turno.MANHANA);
            empleadoBO.guardar(empleadoNuevo, EstadoOperacion.Nuevo);
            idEmpleado = empleadoNuevo.getIdUsuario();
            System.out.println("Empleado creado con ID: " + idEmpleado);
            System.out.println("Empleado creado con correo: " + empleadoNuevo.getCorreo());

            if (idEmpleado != -1) {
                // READ
                Empleado empBD = empleadoBO.obtener(idEmpleado);
                System.out.println("Empleado leído: " + empBD.getNombres() + " " + empBD.getApellidos());

                // UPDATE
                empBD.setTelefono("999999999");
                empBD.setTurno(Turno.TARDE);
                empleadoBO.guardar(empBD, EstadoOperacion.Modificado);
                System.out.println("Empleado actualizado a Turno TARDE.");
            }


            // =========================================================================
            // CRUD ADMINISTRADOR
            // =========================================================================
            System.out.println("\n=== CRUD ADMINISTRADOR ===");
            
            String claveAdminPlana = "adminSecreto456";
            String hashAdmin = pe.edu.pucp.trackgosoft.db.utils.Crypto.encrypt(claveAdminPlana);
            
            // CREATE
            Administrador adminNuevo = new Administrador("ADM-" + suffix, "888" + suffix, "Laura", "Gomez", "laura" + suffix + "@trackgo.com",
                    hashAdmin, "911111111", "ALTO");
            adminNuevo.setIsManager(true);
            administradorBO.guardar(adminNuevo, EstadoOperacion.Nuevo);
            idAdmin = adminNuevo.getIdUsuario();
            System.out.println("Administrador creado con ID: " + idAdmin);
            System.out.println("Empleado creado con correo: " + adminNuevo.getCorreo());

            if (idAdmin != -1) {
                // READ
                Administrador adminBD = administradorBO.obtener(idAdmin);
                System.out.println("Administrador leído: " + adminBD.getNombres() + " - Nivel: " + adminBD.getNivelDeAcceso());

                // UPDATE
                adminBD.setNivelDeAcceso("TOTAL");
                administradorBO.guardar(adminBD, EstadoOperacion.Modificado);
                System.out.println("Administrador actualizado a Nivel TOTAL.");
            }

            // =========================================================================
            // PRUEBA 5: AUTENTICACIÓN (LOGIN)
            // =========================================================================
            System.out.println("\n=== PRUEBA DE AUTENTICACIÓN (LOGIN) ===");
            GestorAutenticacion authBO = new GestorAutenticacionImpl();

            // 1. Intento Fallido
            System.out.println("1. Intentando login con contraseña incorrecta...");
            try {
                authBO.login("laura" + suffix + "@trackgo.com", "claveEquivocada");
            } catch (RuntimeException e) {
                System.out.println("    Excepción controlada: " + e.getMessage());
            }

            // 2. Intento Exitoso Empleado
            System.out.println("\n2. Intentando login Empleado con credenciales correctas...");
            pe.edu.pucp.trackgosoft.modelo.usuario.Usuario user1 = authBO.login("carlos" + suffix + "@trackgo.com", claveEmpleadoPlana);
            System.out.println("    ¡Login Exitoso! Bienvenido Empleado: " + user1.getNombres() + " " + user1.getApellidos());

            // 3. Intento Exitoso Administrador
            System.out.println("\n3. Intentando login Administrador con credenciales correctas...");
            pe.edu.pucp.trackgosoft.modelo.usuario.Usuario user2 = authBO.login("laura" + suffix + "@trackgo.com", claveAdminPlana);
            System.out.println("    ¡Login Exitoso! Bienvenido Admin: " + user2.getNombres() + " " + user2.getApellidos());

            // =========================================================================
            // PRUEBA 6: TRACKING Y REPORTES (FASE 2)
            // =========================================================================
            System.out.println("\n=== PRUEBA DE TRACKING Y REPORTES (FASE 2) ===");
            
            // CREAR SEGUNDO PEDIDO PARA PRUEBAS DE REPORTE MULTIPLE
            System.out.println("Creando un segundo pedido (EN_AGENCIA) para probar los reportes múltiples...");
            Pedido pedido2 = new Pedido("Maria Lopez", 15.00, EstadoPedido.EN_AGENCIA, idEmpleado, direccion, empresaBD);
            pedido2.agregarDetalle("Mouse Gamer", 1);
            pedidoBO.guardar(pedido2, EstadoOperacion.Nuevo);
            idPedido2 = pedido2.getIdPedido();
            
            // 1. Usamos el nuevo GestorDePedidos (Singleton)
            GestorDePedidos gestorPedidos = GestorDePedidos.getInstance();
            
            // Re-obtenemos el pedido usando el caché
            System.out.println("Cargando pedido desde BD a caché...");
            Pedido pedidoParaTracking = gestorPedidos.obtenerPedido(idPedido);
            
            // 2. Usamos RegistroDeTracking para observar
            RegistroDeTracking tracking = new RegistroDeTracking();
            tracking.observar(pedidoParaTracking);
            
            System.out.println("Estado inicial observado: " + tracking.consultarEstadoActual() + " (" + pedidoParaTracking.getEstado() + ")");
            
            // 3. Cambiamos el estado usando el Gestor
            System.out.println("Actualizando estado del pedido a ENTREGADO mediante el GestorDePedidos...");
            gestorPedidos.actualizarEstado(idPedido, EstadoPedido.ENTREGADO);
            
            // El tracking debe reflejar el cambio
            System.out.println("Nuevo estado observado por Tracking: " + tracking.consultarEstadoActual() + " (" + pedidoParaTracking.getEstado() + ")");
            
            // 4. Generamos los 3 Reportes Finales
            System.out.println("\n--- GENERANDO 3 REPORTES DISTINTOS ---");
            
            System.out.println("\nReporte 1: Pedidos ENTREGADOS...");
            String reporteEntregados = GestorDeReportes.generarReportePedidosPorEstado("ENTREGADO");
            System.out.println(reporteEntregados);

            System.out.println("\nReporte 2: Pedidos EN_AGENCIA (Debería incluir a Maria Lopez)...");
            String reporteAgencia = GestorDeReportes.generarReportePedidosPorEstado("EN_AGENCIA");
            System.out.println(reporteAgencia);

            System.out.println("\nReporte 3: Pedidos por Transportista (Empleado ID: " + idEmpleado + ")...");
            String reporteTransportista = GestorDeReportes.generarReportePedidosPorTransportista(idEmpleado);
            System.out.println(reporteTransportista);
            
            System.out.println("\nReporte 4: Pedidos por Fecha (Hoy)...");
            Date hoy = new Date();
            Date inicio = new Date(hoy.getTime() - (1000 * 60 * 60 * 24));
            Date fin = new Date(hoy.getTime() + (1000 * 60 * 60 * 24));
            String reporteFecha = GestorDeReportes.generarReportePedidosPorFecha(inicio, fin);
            System.out.println(reporteFecha);

        } catch (Exception e) {
            System.err.println("\n[ERROR] Ocurrió un fallo en las pruebas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // =========================================================================
            // LIMPIEZA FINAL 
            // =========================================================================
            System.out.println("\n==================================================");
            System.out.println("   LIMPIANDO DATOS DE PRUEBA...");
            
            try {
                // CORRECCIÓN: Usamos eliminar() pasándole el int
                if (idPedido != -1) {
                    pedidoBO.eliminar(idPedido);
                    System.out.println("    [-] Pedido " + idPedido + " eliminado (y detalles/historial).");
                }
                if (idPedido2 != -1) {
                    pedidoBO.eliminar(idPedido2);
                    System.out.println("    [-] Pedido " + idPedido2 + " eliminado (y detalles/historial).");
                }
                if (idDireccion != -1) {
                    direccionBO.eliminar(idDireccion);
                    System.out.println("    [-] Dirección " + idDireccion + " eliminada.");
                }
                if (empresaBD != null && empresaBD.getId() != -1) {
                    empresaBO.eliminar(empresaBD.getId());
                    System.out.println("    [-] Empresa " + empresaBD.getRuc() + " eliminada.");
                }
                try {
                    transporteBO.eliminar(placa2);
                    transporteBO.eliminar(placa3);
                    System.out.println("    [-] Transportes restantes eliminados.");
                } catch(Exception ignored){}
                
                if (idEmpTemp != -1) {
                    empleadoBO.eliminar(idEmpTemp);
                }
                if (idEmpleado != -1) {
                    empleadoBO.eliminar(idEmpleado);
                    System.out.println("    [-] Empleado " + idEmpleado + " eliminado.");
                }
                if (idAdmin != -1) {
                    administradorBO.eliminar(idAdmin);
                    System.out.println("    [-] Administrador " + idAdmin + " eliminado.");
                }
                
            } catch (Exception e) {
                System.err.println("Error durante la limpieza: " + e.getMessage());
            }

            System.out.println("\n[OK] Conexión a BD gestionada automáticamente por la arquitectura.");
            System.out.println("==================================================");
            System.out.println("   PRUEBAS FINALIZADAS EXITOSAMENTE");
            System.out.println("==================================================");
        }
    }
}
