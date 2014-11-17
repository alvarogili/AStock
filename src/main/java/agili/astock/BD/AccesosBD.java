/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.BD;

import BD.Empresas.Empresa;
import BD.Empresas.EmpresaCliente;
import BD.Personas.Persona;
import BD.Personas.PersonaCliente;
import agili.astock.BD.Agenda.Evento;
import agili.astock.Herramientas.DatosDeConfiguracion;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Clase que realiza todos los accesos a la base de datos Clase de tipo
 * singleton
 *
 * @author Alvaro
 */
public class AccesosBD {

    private static PersistenceManager persistenceManager;
    private static Connection con;
    private DatosDeConfiguracion ddc = DatosDeConfiguracion.getInstance();
    private final Object semaforo = new Object();
    private static AccesosBD accesosBD;

    /**
     * This enum lists possible database engines.
     */
    public enum DBEngine {

        MySQL,
        PostgreSQL,
        SQLite,
        HSQLDB,
        SQLServer
    }
    private DBEngine engine = null;
    private String DBName = "astock";
    private String urlDBEngine = null;
    private String usrDB = null;
    private String pwdDB = null;
    private String unitID;
    //Persistent unit name
    private static final String PU_NAME = "astock";
    //The main function of the Session is to offer persistence operations (create, 
    //read and delete) for instances of StoreRequest.
    private static SessionFactory SESSION_FACTORY;
    /**
     * Hibernate configurations
     */
    //Database connection settings
    private static final String DRIVER = "hibernate.connection.driver_class";
    private static final String URL = "hibernate.connection.url";
    private static final String USERNAME = "hibernate.connection.username";
    private static final String PASSWORD = "hibernate.connection.password";
    //http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/session-configuration.html
    //Drop and re-create the database schema on startup
    private static final String HBM2DDL = "hibernate.hbm2ddl.auto";
    //Echo all executed SQL to stdout
    //Write all SQL statements to console. This is an alternative to setting the 
    //log category org.hibernate.SQL to debug. 
    private static final String SHOW_SQL = "hibernate.show_sql";
    //SQL dialect
    private static final String DIALECT = "hibernate.dialect";
    //JDBC connection pool (use the built-in)
    private static final String CONNECTION_POOL_SIZE = "hibernate.connection.pool_size";
    //Enable Hibernate's automatic session context management
    //Supply a custom strategy for the scoping of the "current" Session. See Section 2.5, “Contextual sessions” for more information about the built-in strategies.
    //e.g. jta | thread | managed | custom.Class 
    private static final String CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";
    //Disable the second-level cache
    //The classname of a custom org.hibernate.connection.ConnectionProvider which provides 
    //JDBC connections to Hibernate. 
    private static final String PROVIDER_CLASS = "hibernate.cache.provider_class";
    /**
     * Map that matches a DBEngine value with the same value but in string.
     */
    public static final Map<String, DBEngine> STRING_TO_DBENGINE;

    static {
        Map<String, DBEngine> STRING_TO_DBENGINETemp =
                new HashMap<String, DBEngine>();

        STRING_TO_DBENGINETemp.put("MySQL", DBEngine.MySQL);
        STRING_TO_DBENGINETemp.put("PostgreSQL", DBEngine.PostgreSQL);
        STRING_TO_DBENGINETemp.put("SQLite", DBEngine.SQLite);
        STRING_TO_DBENGINETemp.put("HSQLDB", DBEngine.HSQLDB);
        STRING_TO_DBENGINETemp.put("SQLServer", DBEngine.SQLServer);
        STRING_TO_DBENGINE = Collections.unmodifiableMap(STRING_TO_DBENGINETemp);
    }

    public static AccesosBD getInstance() {
        if (accesosBD == null) {
            accesosBD = new AccesosBD();
        }
        return accesosBD;
    }

    private AccesosBD() {
    }

    public void init(DBEngine dBEngine, String urlDB, String userDB, String pwdDB) {
        this.engine = dBEngine;
        this.urlDBEngine = urlDB;
        this.usrDB = userDB;
        this.pwdDB = pwdDB;
        Properties PROPERTIES = new Properties();
        String engine = null;
        if (dBEngine == DBEngine.HSQLDB) {
            engine = "hsqldb";
            PROPERTIES.put(DRIVER, "org.hsqldb.jdbcDriver");
            PROPERTIES.put(DIALECT, "org.hibernate.dialect.HSQLDialect");
        } else if (dBEngine == DBEngine.MySQL) {
            engine = "mysql";
            PROPERTIES.put(DRIVER, "com.mysql.jdbc.Driver");
            PROPERTIES.put(DIALECT, "org.hibernate.dialect.MySQLDialect");
        } else if (dBEngine == DBEngine.PostgreSQL) {
            engine = "postgresql";
            PROPERTIES.put(DRIVER, "org.postgresql.Driver");
            PROPERTIES.put(DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        } else if (dBEngine == DBEngine.SQLServer) {
            engine = "sqlserver";
            PROPERTIES.put(DRIVER, "net.sourceforge.jtds.jdbc.Driver");
            PROPERTIES.put(DIALECT, "org.hibernate.dialect.SQLServerDialect");
        } else if (dBEngine == DBEngine.SQLite) {
            engine = "sqlite";
            PROPERTIES.put(DRIVER, "org.sqlite.JDBC");
            PROPERTIES.put(DIALECT, "cuss.archmanager.storerequests.SQLiteDialect");

        }

        String DB_URL = "jdbc:" + engine + "://" + urlDBEngine;

        if (dBEngine == DBEngine.HSQLDB) {
            DB_URL = "jdbc:" + engine + ":hsql:mem" + ":/" + urlDBEngine;
        }

        if (dBEngine == DBEngine.SQLite) {

            File pathFileDBSQLite = new File(urlDBEngine);

            //System.out.println("urlDBEngineFile: "+ urlDBEngineFile);
            if (pathFileDBSQLite.exists()) {
                //Si existe, me fijo que sea un archivo.
                //Ejemplo /tmp/ArchManager/srmtestdb.db
                if (pathFileDBSQLite.isFile()) {
                    DB_URL = "jdbc:" + engine + ":" + pathFileDBSQLite.toString();
                } else {
                }
            } else {
                //Si no existe, me fijo que el padre (y que exista) sea un directorio para crearlo ahi.
                //Si el padre no existe entonces no lo creo sino se tira una excepcion.
                File parentPathFileDBSQLite = pathFileDBSQLite.getParentFile();
                //si es nulo es pq no es un nombre a un parent directory.
                System.out.println("parentPathFileDBSQLite: " + parentPathFileDBSQLite);
                if (parentPathFileDBSQLite == null) {
                    //throw new ArchManagerException("StoreRequestManager: Path to DB doesn't exist.");
                    //Es pq es un path relativo donde apunta el directorio
                    DB_URL = "jdbc:" + engine + ":" + pathFileDBSQLite.toString();
                } else {
                    if (parentPathFileDBSQLite.exists()) {
                        DB_URL = "jdbc:" + engine + ":" + pathFileDBSQLite.toString();
                    } else {
                    }
                }

            }
        }

        //Database creation
        Connection con = null;
        Statement statement = null;
        try {
            con = DriverManager.getConnection(DB_URL, usrDB, pwdDB);
        } catch (SQLException se) {
        }
        try {
            statement = (Statement) con.createStatement();
            statement.execute("CREATE DATABASE " + DBName);

        } catch (SQLException se) {
            //Si la base de datos existe o no se pudo crear entra por acá.
            //Si no pudo ser creada fallará en "buildSessionFactory()" cuando 
            //el Hibernate intente conectarse.
        } finally {
            try {
                statement.close();
                if (con != null) {
                    if (!con.isClosed()) {
                        con.close();
                    }
                }
            } catch (SQLException ex) {
            }
        }
        ////////
        if (DBName.length() > 0) {
            if (dBEngine != DBEngine.SQLite) {
                DB_URL = DB_URL + "/" + DBName;
            }
        }

        PROPERTIES.put(URL, DB_URL);
        PROPERTIES.put(USERNAME, usrDB);
        PROPERTIES.put(PASSWORD, pwdDB);
        PROPERTIES.put(CONNECTION_POOL_SIZE, "1");
        PROPERTIES.put(CURRENT_SESSION_CONTEXT_CLASS, "thread");
        PROPERTIES.put(PROVIDER_CLASS, "org.hibernate.cache.NoCacheProvider");

        PROPERTIES.put(HBM2DDL, "update");

        PROPERTIES.put(SHOW_SQL, "false");

        Configuration cfg = new AnnotationConfiguration();
        //cfg = cfg.configure(new File("src/main/resources/com/hibernateejemplo/hibernate.cfg.xml"));
        //cfg = cfg.addFile("src/main/resources/cuss/archmanager/storerequests/StoreRequest.hbm.xml");
//        cfg = cfg.addClass(Evento.class);
//        cfg = cfg.addClass(Empresa.class);
//        cfg = cfg.addClass(EmpresaCliente.class);
//        cfg = cfg.addClass(Persona.class);
//        cfg = cfg.addClass(PersonaCliente.class);
        cfg = cfg.setProperties(PROPERTIES);

        ///Creates Sessions. Usually an application has a single SessionFactory. 
        ///Threads servicing client requests obtain Sessions from the factory.
        try {
            SESSION_FACTORY = cfg.buildSessionFactory();
        } catch (HibernateException ex) {
        }
    }

    /**
     * Crea la base de datos si no existe
     *
     * @param properties
     */
//    public void init(HashMap<String, String> properties) {
//        synchronized (semaforo) {
//            try {
//                //si no existe la base de datos la creo
//                con = DriverManager.getConnection("jdbc:mysql://" + ddc.getURLBD(), ddc.getUsuarioBD(), ddc.getContraseñaBD());
//                Statement statement = (Statement) con.createStatement();
//                statement.execute("CREATE DATABASE IF NOT EXISTS astock");
//                statement.close();
//                con.close();
//                /////////////////////////////////////
//                PersistenceManagerFactory conexion = JDOHelper.getPersistenceManagerFactory(properties);
//                persistenceManager = conexion.getPersistenceManager();
//            } catch (SQLException ex) {
//                Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    public int getCount(Class objeto) {
        synchronized (semaforo) {
            Transaction sesion = persistenceManager.currentTransaction();
            if (!sesion.isActive()) {
                sesion.begin();
            }

            // select the number of rows in the table
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + ddc.getURLBD() + "/astock", ddc.getUsuarioBD(), ddc.getContraseñaBD());
                stmt = con.createStatement();

                rs = stmt.executeQuery("select count(*) from " + objeto.getSimpleName());
                // get the number of rows from the result set
                rs.next();
                rowCount = rs.getInt(1);
                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                sesion.commit();
            }
            return rowCount;
        }
    }

    /**
     * Ejecuta una sentencia SQL
     *
     * @param SQLSentence Sentencia a ejecutar
     * @return ResultSet conteniendo el resultado
     */
    public ResultSet executeSQLSentence(String SQLSentence) {
        synchronized (semaforo) {
            Transaction sesion = persistenceManager.currentTransaction();
            if (!sesion.isActive()) {
                sesion.begin();
            }

            // select the number of rows in the table
            Statement stmt = null;
            ResultSet rs = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + ddc.getURLBD() + "/astock", ddc.getUsuarioBD(), ddc.getContraseñaBD());
                stmt = con.createStatement();

                rs = stmt.executeQuery(SQLSentence);

            } catch (SQLException ex) {
                Logger.getLogger(AccesosBD.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                sesion.commit();
            }
            return rs;
        }
    }

    /**
     * Consulta si existe en la base de datos
     *
     * @param objeto Objeto tabla
     * @param verificacion Verificacion a realizar, por ejemplo dni = 29494566
     * @param persistenceManager conexion
     * @return true si existe
     */
    public boolean existeEnBD(Class objeto, String verificacion) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }

                Extent e = persistenceManager.getExtent(objeto, true);
                Query query = persistenceManager.newQuery(e, verificacion);
                Collection results = null;
                try {
                    results = (Collection) query.execute();
                    if (results.size() > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception ex) {
                    return false;
                } finally {
                    sesion.commit();
                }
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Retorna un objeto dada una resticcion
     *
     * @param objeto Tipo de clase
     * @param verificacion String de checkeo
     * @param persistenceManager Conexion
     * @return
     */
    public Object getObjeto(Class objeto, String verificacion) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }
                Extent e = persistenceManager.getExtent(objeto, true);
                Query query = persistenceManager.newQuery(e, verificacion);
                Collection results = null;
                try {
                    results = (Collection) query.execute();
                    Iterator it = results.iterator();
                    if (it.hasNext()) {
                        return it.next();
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    return null;
                } finally {
                    sesion.commit();
                }
            } catch (Exception e) {
                Object result = new Object();
                return result;
            }
        }
    }

    public void actualizarObjeto(Class tipo, Object objAActualizar, String verificacion) {
        synchronized (semaforo) {
            try {
                Object objeto = null;
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }
                //obtengo el objeto a actualizar
                Extent e = persistenceManager.getExtent(tipo, true);
                Query query = persistenceManager.newQuery(e, verificacion);
                Collection results = null;
                try {
                    results = (Collection) query.execute();
                    Iterator it = results.iterator();
                    if (it.hasNext()) {
                        objeto = it.next();
                    }
                } catch (Exception ex) {
                }
                //lo actualizo
                Method[] methodsObjAActualizar = objAActualizar.getClass().getMethods();
                Method[] methodsObjeto = objeto.getClass().getMethods();
                for (int i = 0; i < methodsObjAActualizar.length; i++) {

                    if (methodsObjeto[i].getName().startsWith("appSet")) {//es un metodo set de la clase
                        String nameMethod = methodsObjeto[i].getName();
                        nameMethod = nameMethod.replace("appSet", "appGet");
                        //busco el metodo get que elos metodos de objAActualizar
                        for (int j = 0; j < methodsObjAActualizar.length; j++) {
                            if (methodsObjAActualizar[j].getName().equals(nameMethod)) {
                                /*
                                 * representa el set
                                 */           /*
                                 * representa el get
                                 */
                                methodsObjeto[i].invoke(objeto, methodsObjAActualizar[j].invoke(objAActualizar));
                                j = methodsObjAActualizar.length;
                            }
                        }
                    }
                }
                sesion.commit();

            } catch (Exception e) {
            }
        }
    }

    /**
     * Metodo que retorna todos los elementos en la base de datos de un mismo
     * tipo dado un filto en verificación
     *
     * @param objeto Tipo de elemento
     * @param verificacion Filtro. Si este es null retorna todos los elementos
     * @return arreglo conteniendo los resultados
     */
    public Object[] getObjetos(Class objeto, String verificacion) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }

                Vector<Object> listaResultado = new Vector<Object>();
                Extent extent = persistenceManager.getExtent(objeto, true);

                if (verificacion == null) {
                    Iterator iter = extent.iterator();
                    while (iter.hasNext()) {
                        Object elemento = iter.next();
                        listaResultado.add(elemento);
                    }
                } else {
                    Query query = persistenceManager.newQuery(extent, verificacion);
                    Collection results = null;
                    try {
                        results = (Collection) query.execute();
                        Iterator iter = results.iterator();
                        while (iter.hasNext()) {
                            Object elemento = iter.next();
                            listaResultado.add(elemento);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
                Object arrayResult[] = new Object[listaResultado.size()];
                listaResultado.copyInto(arrayResult);

                return arrayResult;
            } catch (Exception e) {
                Object arrayResult[] = new Object[0];
                return arrayResult;
            }
        }
    }

    /**
     * Metodo que gurada un objeto en la base de datos
     *
     * @param objeto Objeto a guardar
     * @return true si guardo, sino false
     */
    public boolean guardarEnBD(Object objeto) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }
                try {
                    persistenceManager.makePersistent(objeto);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    sesion.commit();
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * Borra el objeto de la base de datos
     *
     * @param object Objeto a borrar
     * @return Retorna true si pudo borrarlo, sino retorna falso
     */
    public boolean borrarObjeto(Object object) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }
                try {
                    persistenceManager.deletePersistent(object);
                } catch (Exception e) {
                    return false;
                } finally {
                    sesion.commit();
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * Metodos asociados a los eventos de la agenda
     */
    /**
     * Metodo que dado un string de verificación y dice si existe un evento que
     * cumple con esa verificación y que su fecha sea igual a la dada
     *
     * @param verificacion String de verificación, si es null busca el que
     * cumpla con esa fecha
     * @param fecha Fecha exacta del evento a buscar
     * @return Objeto que cumple la propiedad
     */
    public boolean existeEvento(String verificacion, Date fecha) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }
                verificacion += " && horaYFecha == :fecha";
                Extent extent = persistenceManager.getExtent(Evento.class, true);
                Query query = persistenceManager.newQuery(extent, verificacion);

                Collection results = null;
                try {
                    results = (Collection) query.execute(fecha);
                    Iterator it = results.iterator();
                    if (it.hasNext()) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception ex) {
                    return false;
                } finally {
                    sesion.commit();
                }
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Metodo que dado un string de verificación retorna un evento que cumple
     * con esa verificación y que su fecha sea igual a la dada
     *
     * @param verificacion String de verificación, si es null busca el que
     * cumpla con esa fecha
     * @param fecha Fecha exacta del evento a buscar
     * @return Objeto que cumple la propiedad
     */
    public Evento getEvento(String verificacion, Date fecha) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }
                verificacion += " && horaYFecha == :fecha";
                Extent extent = persistenceManager.getExtent(Evento.class, true);
                Query query = persistenceManager.newQuery(extent, verificacion);

                Collection results = null;
                try {
                    results = (Collection) query.execute(fecha);
                    Iterator it = results.iterator();
                    if (it.hasNext()) {
                        return (Evento) it.next();
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    return null;
                } finally {
                    sesion.commit();
                }
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Metodo que retorna eventos dentro de un intervalo de tiempo
     *
     * @param desde Fecha inicio
     * @param hasta Fecha final
     * @return Lista de eventos
     */
    public Object[] getIntervaloEventos(Date desde, Date hasta) {
        synchronized (semaforo) {
            try {
                Transaction sesion = persistenceManager.currentTransaction();
                if (!sesion.isActive()) {
                    sesion.begin();
                }

                Vector<Object> listaResultado = new Vector<Object>();

                Query query = persistenceManager.newQuery("select from BD.Agenda.Evento where "
                        + "horaYFecha >= :horaMenor && horaYFecha <= :horaMayor order by horaYFecha ascending");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("horaMenor", desde);
                params.put("horaMayor", hasta);
                Collection results = null;
                try {
                    results = (Collection) query.executeWithMap(params);
                    Iterator iter = results.iterator();
                    while (iter.hasNext()) {
                        Object elemento = iter.next();
                        listaResultado.add(elemento);
                    }
                } catch (Exception ex) {
                    return null;
                } finally {
                    sesion.commit();
                }

                Object arrayResult[] = new Object[listaResultado.size()];
                listaResultado.copyInto(arrayResult);

                return arrayResult;
            } catch (Exception ex) {
                return null;
            }
        }
    }
}
