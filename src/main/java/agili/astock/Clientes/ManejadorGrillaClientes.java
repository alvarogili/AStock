/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Clientes;

import BD.Empresas.EmpresaCliente;
import BD.Personas.PersonaCliente;
import agili.astock.BD.AccesosBD;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que Administra los datos de la grilla de clientes
 * @author Alvaro Gili
 */
public class ManejadorGrillaClientes {

    /**
     * Lista completa de clientes
     */
    private static Vector<Object> clientes = new Vector<Object>();
    /**
     * Cantidad de registros
     */
    private int countPersonas = 0;
    private int countEmpresas = 0;

    /**
     * Constructor
     */
    public ManejadorGrillaClientes() {
    }

    /**
     * Actualiza la grilla de clientes solo por cambios
     * @param clientesjTable Tabla
     * @param personaCheckBox JCheckBox "Mostrar Personas"
     * @param empresaCheckBox JCheckBox "Mostrar Empresas"
     */
    public void updateClientsGridByChanges(JTable clientesjTable,
            JCheckBox personaCheckBox,
            JCheckBox empresaCheckBox,
            JFrame parent) {
        boolean actPersonas = false;
        boolean actEmpresas = false;

        AccesosBD abd = AccesosBD.getInstance();
        int temp = abd.getCount(BD.Personas.PersonaCliente.class);
        if (temp != countPersonas) {
            countPersonas = temp;
            actPersonas = true;
        }

        temp = abd.getCount(BD.Empresas.EmpresaCliente.class);
        if (temp != countEmpresas) {
            countEmpresas = temp;
            actEmpresas = true;
        }

        if (actEmpresas || actPersonas) {
            updateClientsGrid(clientesjTable, personaCheckBox, empresaCheckBox, parent);
        }
    }

    /**
     * Actualiza la grilla de clientes
     * @param clientesjTable Tabla
     * @param personaCheckBox JCheckBox "Mostrar Personas"
     * @param empresaCheckBox JCheckBox "Mostrar Empresas"
     */
    public void updateClientsGrid(JTable clientesjTable,
            JCheckBox personaCheckBox,
            JCheckBox empresaCheckBox,
            JFrame parent) {

        AccesosBD abd = AccesosBD.getInstance();
        DefaultTableModel clientesTableModel = null;
        //limpio las grillas

        clientesTableModel = (DefaultTableModel) clientesjTable.getModel();
        // limpio la tabla
        while (clientesTableModel.getRowCount() > 0) {
            clientesTableModel.removeRow(0);
        }

        //por si tiene algo, limpio el vector
        clientes.clear();


        if (personaCheckBox.isSelected()) {
            //Personas
            Object lista[] = abd.getObjetos(BD.Personas.PersonaCliente.class, null);

            for (int i = 0; i < lista.length; i++) {
                PersonaCliente cliente = (PersonaCliente) lista[i];
                clientes.add(cliente);
                //agrego a la grilla
                addPersonaCliente(cliente, clientesTableModel);
            }
        }
        if (empresaCheckBox.isSelected()) {
            //Empresas
            Object lista[] = abd.getObjetos(BD.Empresas.EmpresaCliente.class, null);

            for (int i = 0; i < lista.length; i++) {
                EmpresaCliente cliente = (EmpresaCliente) lista[i];
                clientes.add(cliente);
                //agrego a la grilla
                addEmpresaCliente(cliente, clientesTableModel);
            }
        }

        if (clientesjTable.getRowCount() > 0) {
            clientesjTable.setRowSelectionInterval(0, 0);
        }

        /////////////////////////////////////////////////////////        
    }

    /**
     * Aplica el filtro al nombre de todos los clientes y retona los que cumplan
     * @param filtro Texto al pricipio del nombre
     * @return Lista de clientes válidos
     */
    public void getClientesFiltradosPorNombre(String filtro, JTable clientesjTable) {

        DefaultTableModel clientesTableModel = (DefaultTableModel) clientesjTable.getModel();
        // limpio la tabla
        while (clientesTableModel.getRowCount() > 0) {
            clientesTableModel.removeRow(0);
        }

        filtro = filtro.toUpperCase();

        //cargo todos los que cumplen esa condición
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i) instanceof PersonaCliente) {
                PersonaCliente personaCliente = (PersonaCliente) clientes.get(i);
                String apYNom = personaCliente.appGetApellido() + ", " + personaCliente.appGetNombre();
                if (apYNom.toUpperCase().startsWith(filtro)) {

                    //agrego a la grilla
                    addPersonaCliente(personaCliente, clientesTableModel);
                }
            } else if (clientes.get(i) instanceof EmpresaCliente) {
                EmpresaCliente empresaCliente = (EmpresaCliente) clientes.get(i);
                if (empresaCliente.appGetNombre().toUpperCase().startsWith(filtro)) {
                    //agrego a la grilla
                    addEmpresaCliente(empresaCliente, clientesTableModel);
                }
            }
        }
        if (clientesjTable.getRowCount() > 0) {
            //lo posiciono en la columna 1
            clientesjTable.setRowSelectionInterval(0, 0);
        }
    }

    /**
     * Aplica el filtro al nombre de todos los clientes y retona los que cumplan
     * @param filtro Texto al pricipio del nombre
     * @return Lista de clientes válidos
     */
    public void getClientesFiltradosPorDNI(String filtro, JTable clientesjTable) {

        DefaultTableModel clientesTableModel = (DefaultTableModel) clientesjTable.getModel();
        // limpio la tabla
        while (clientesTableModel.getRowCount() > 0) {
            clientesTableModel.removeRow(0);
        }

        filtro = filtro.toUpperCase();

        //cargo todos los que cumplen esa condición
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i) instanceof PersonaCliente) {
                PersonaCliente personaCliente = (PersonaCliente) clientes.get(i);
                String dni = personaCliente.appGetDni();
                if (dni.toUpperCase().startsWith(filtro)) {
                    //agrego a la grilla
                    addPersonaCliente(personaCliente, clientesTableModel);
                }
            } else if (clientes.get(i) instanceof EmpresaCliente) {
                if (filtro.equals("")) {//como borré el filtro agrego las empresas
                    EmpresaCliente empresaCliente = (EmpresaCliente) clientes.get(i);
                    //agrego a la grilla
                    addEmpresaCliente(empresaCliente, clientesTableModel);
                }
            }
        }
        if (clientesjTable.getRowCount() > 0) {
            //lo posiciono en la columna 1
            clientesjTable.setRowSelectionInterval(0, 0);
        }
    }

    /**
     * Aplica el filtro al nombre de todos los clientes y retona los que cumplan
     * @param filtro Texto al pricipio del nombre
     * @return Lista de clientes válidos
     */
    public void getClientesFiltradosPorCUIT(String filtro, JTable clientesjTable) {

        DefaultTableModel clientesTableModel = (DefaultTableModel) clientesjTable.getModel();
        // limpio la tabla
        while (clientesTableModel.getRowCount() > 0) {
            clientesTableModel.removeRow(0);
        }

        filtro = filtro.toUpperCase();
        //cargo todos los que cumplen esa condición
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i) instanceof PersonaCliente && filtro.equals("")) {//como borré el filtro agrego las empresas
                PersonaCliente personaCliente = (PersonaCliente) clientes.get(i);
                String cuit = personaCliente.appGetDni();
                if (cuit.toUpperCase().startsWith(filtro)) {
                    //agrego a la grilla
                    addPersonaCliente(personaCliente, clientesTableModel);
                }
            } else if (clientes.get(i) instanceof EmpresaCliente) {
                EmpresaCliente empresaCliente = (EmpresaCliente) clientes.get(i);
                String cuit = empresaCliente.appGetCuit();
                if (cuit.toUpperCase().startsWith(filtro)) {
                    //agrego a la grilla
                    addEmpresaCliente(empresaCliente, clientesTableModel);
                }
            }
        }
        if (clientesjTable.getRowCount() > 0) {
            //lo posiciono en la columna 1
            clientesjTable.setRowSelectionInterval(0, 0);
        }
    }

    /**
     * Agrega una persona a la grilla de clientes
     * @param personaCliente Objeto persona
     * @param clientesTableModel TableModel
     */
    public void addPersonaCliente(PersonaCliente personaCliente, DefaultTableModel clientesTableModel) {
        String apYNom = personaCliente.appGetApellido() + ", " + personaCliente.appGetNombre();
        clientesTableModel.addRow(new Object[]{apYNom,
                    personaCliente.appGetDni(),
                    personaCliente.appGetDireccion(),
                    personaCliente.appGetCodigoPostal(),
                    personaCliente.appGetLocalidad(),
                    personaCliente.appGetProvincia(),
                    personaCliente.appGetTelefono(),
                    personaCliente.appGetTelefono2(),
                    personaCliente.appGetCorreo(),
                    personaCliente.appGetFax(),
                    personaCliente.appGetCumpleaños(),
                    "Persona"});
    }

    /**
     * Agrega una empresa a la grilla de clientes
     * @param empresaCliente Objeto empresa
     * @param clientesTableModel TableModel
     */
    public void addEmpresaCliente(EmpresaCliente empresaCliente, DefaultTableModel clientesTableModel) {
        clientesTableModel.addRow(new Object[]{empresaCliente.appGetNombre(),
                    empresaCliente.appGetCuit(),
                    empresaCliente.appGetDireccion(),
                    empresaCliente.appGetCodigoPostal(),
                    empresaCliente.appGetLocalidad(),
                    empresaCliente.appGetProvincia(),
                    empresaCliente.appGetTelefono(),
                    empresaCliente.appGetTelefono2(),
                    empresaCliente.appGetCorreo(),
                    empresaCliente.appGetFax(),
                    "---",
                    "Empresa"});
    }
}
