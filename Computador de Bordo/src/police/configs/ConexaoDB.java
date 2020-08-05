/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.json.JSONObject;
import police.Corporacao;

/**
 *
 * @author John
 */
public class ConexaoDB {
    public void ConectarMysql(){
        
    }
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    private final String host =  "axirouxe.com"; //"axirouxe.com"; //mysql-mariadb15-bra-104.zap-hosting.com
    private final String banco = "rainbow_cb";//"axiroux1_cbgta";
    private final String user =  "user_cb";//"cborigin";
    private final String pass =  "V5n7fg@3";//"4_iDca63";
    
    private String host_server =  host; //"158.69.22.55";
    private String banco_server = banco;//"vrp";
    private String user_server =  user;//"ferrazgado";
    private String pass_server =  pass;//"gadoferraz";
    
    private String Servidor_Config = "";
    
    Preferences prefs = Preferences.userNodeForPackage(Example.class);
    
    public boolean SetarBancoServidor(String s_host, String s_banco, String s_user, String s_senha){
        prefs.put(host_server, s_host);
        prefs.put(banco_server, s_banco);
        prefs.put(user_server, s_user);
        prefs.put(pass_server, s_senha);
        prefs.put(Servidor_Config, "true");
        //Servidor_Config = true;
        System.out.println("host_server: "+host_server+" / banco_server: "+banco_server+" / user_server: "+user_server+" / pass_server: "+pass_server);
        return true;
    }
    
    public boolean AttDB(){
        host_server = prefs.get(host_server, "padrao");
        banco_server = prefs.get(banco_server, "padrao");
        user_server = prefs.get(user_server, "padrao");
        pass_server = prefs.get(pass_server, "padrao");
        Servidor_Config = prefs.get(Servidor_Config, "padrao");
        System.out.println("host_server: "+host_server+" / banco_server: "+banco_server+" / user_server: "+user_server+" / pass_server: "+pass_server);
        return true;
    }

    public ResultSet PegarContas() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host_server+"/"+banco_server+"?"
                            + "user="+user_server+"&password="+pass_server);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from vrp_user_identities ORDER BY user_id");
            
            System.out.println("Conectado ao banco de dados do SERVIDOR: "+host_server);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet PegarDiscord() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host_server+"/"+banco_server+"?"
                            + "user="+user_server+"&password="+pass_server);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from vrp_user_ids ORDER BY user_id");
            
            System.out.println("Conectado ao banco de dados do SERVIDOR: "+host_server);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet PegarBlackList() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from cb_blacklist ORDER BY user_id");
            
            System.out.println("Conectado ao banco de dados: "+host);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet readDataBase(String tabelad) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from "+tabelad+" ORDER BY id DESC");
            //writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            
            /*preparedStatement = connect
                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");*/
            
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            
            /*preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);*/

            // Remove again the insert comment
            
            /*preparedStatement = connect
            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");*/
                    
            //preparedStatement.executeUpdate();

            /*resultSet = statement
            .executeQuery("select * from feedback.comments");
            writeMetaData(resultSet);*/
            
           
            System.out.println("Conectado ao servidor: "+host);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet readDataBase2(String tabelad, String where, String where2) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from "+tabelad+" WHERE "+where+" LIKE '"+where2+"' ORDER BY id DESC");
            System.out.println("Conectado ao servidor: "+host);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet getDadosBanco(String tabelad, String orderby) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            if("".equals(orderby)) orderby="id";
            resultSet = statement
                    .executeQuery("select * from "+tabelad+" ORDER BY "+orderby+" ASC");
            System.out.println("Conectado ao servidor: "+host);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet getDadosBanco2(String tabelad, String orderby) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host_server+"/"+banco_server+"?"
                            + "user="+user_server+"&password="+pass_server);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            if("".equals(orderby)) orderby="id";
            resultSet = statement
                    .executeQuery("select * from "+tabelad+" ORDER BY "+orderby+" ASC");
            System.out.println("Conectado ao servidor: "+host);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet getPassLSPD(String tabelad, String where2) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from "+tabelad+" WHERE passaporte='"+where2+"' AND lspd!='1' ORDER BY id DESC");
            System.out.println("Conectado ao servidor: "+host);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public ResultSet GetPersonalizado(String query) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(query);
            System.out.println("Conectado ao servidor: "+host);
            return resultSet;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public boolean getPossuiRegistro(String tabelad, String where, String where2) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from "+tabelad+" WHERE "+where+" LIKE '"+where2+"' ORDER BY id DESC");
            int Contagem=0;
            while (resultSet.next()) {
                Contagem++;
            }
            if(Contagem > 0){
                System.out.println("Conectado ao servidor: "+host);
                return true;
            }else{
               return false; 
            }

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean AtualizarDatabaseDado(String tabelad, JSONObject jsonob) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            
            int cid = Integer.parseInt(jsonob.getString("passaporte"));
            /*String cnome = jsonob.getString("nome");
            String cnome1 = jsonob.getString("sobrenome");*/
            String ccodigo = jsonob.getString("codigo");
            String cdiscord = jsonob.getString("discord");
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            String query1 = "update "+tabelad+" set codigo='"+ccodigo+"', discord='"+cdiscord+"' where id="+cid;
            statement.executeUpdate(query1);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    public boolean LimparFicha(int ID, JSONObject jsonob) {
        try {
            String id_limpou = jsonob.getString("id_limpou");
            String protocolo = jsonob.getString("protocolo");
            String data = System.currentTimeMillis()+"";
            String pago = jsonob.getString("pago");
            String meses = jsonob.getString("meses");
            String vezespreso = jsonob.getString("vezespreso");
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            String query1 = "update cb_prisoes set limpo='"+protocolo+"' where id_usuario="+ID+" AND limpo=''";
            statement.executeUpdate(query1);
            
            String query2 = "INSERT INTO cb_fichas (id_usuario, id_limpou, protocolo, data, pago, meses, vezespreso) VALUES ('"+ID+"', "+id_limpou+", "+protocolo+", '"+data+"', '"+pago+"', '"+meses+"', '"+vezespreso+"')";
            statement.executeUpdate(query2);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean AddUserLSPD(String tabelad, JSONObject jsonob) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            
            int cid = Integer.parseInt(jsonob.getString("passaporte"));
            String cnome = jsonob.getString("nome");
            String discorde = jsonob.getString("discord");
            String codigoc = jsonob.getString("codigo");
            
            int patente = Integer.parseInt(jsonob.getString("patente"));
            
            String nomep = jsonob.getString("nomeP");
            int passp = Integer.parseInt(jsonob.getString("passaporteP"));
            
            String motivon = "Ingressou na corporação.";
            
            int lspd = jsonob.getInt("lspd");
            int newuser = jsonob.getInt("newuser");
            String data = System.currentTimeMillis()+"";
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            //String query1 = "update "+tabelad+" set codigo='"+codigoc+"' where id="+cid+"";
            if(newuser == 0){
                String query1 = "INSERT INTO "+tabelad+" (id, lspd, codigo, discord) VALUES ('"+cid+"', "+lspd+", '"+codigoc+"', '"+discorde+"')";
                statement.executeUpdate(query1);
            }
            
            String query2 = "INSERT INTO cb_hierarquia (id_usuario, cargo, cargo_antigo, motivo, id_promoveu, data) VALUES ('"+cid+"', '"+patente+"', '0', '"+motivon+"', '"+passp+"', '"+data+"')";
            statement.executeUpdate(query2);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean AttDataLogin(int ide) {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            String datar = System.currentTimeMillis()+"";
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            String query1 = "update cb_identities set ultimologin='"+datar+"' where user_id='"+ide+"'";
            statement.executeUpdate(query1);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean SalvarPtt(String tabelad, JSONObject jsonob) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            
            String cid = jsonob.getString("passaporte");
            String cnome = jsonob.getString("nome");
            String npatenten = jsonob.getString("patenten");
            String npatenteo = jsonob.getString("patenteo");
            String nnome = jsonob.getString("nomen");
            String npassaporte = jsonob.getString("passaporten");
            String motivon = jsonob.getString("motivo");
            String data = System.currentTimeMillis()+"";
            int Stars = jsonob.getInt("stars");
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            //String query1 = "update "+tabelad+" set passaporte='"+cid+"', nome='"+cnome+"', codigo='"+ccodigo+"', discord='"+cdiscord+"' where passaporte='"+ide+"'";
            String query1 = "INSERT INTO "+tabelad+" (id_usuario, cargo, cargo_antigo, motivo, id_promoveu, data, stars) VALUES ('"+npassaporte+"', '"+npatenten+"', '"+npatenteo+"', '"+motivon+"', '"+cid+"', '"+data+"', '"+Stars+"')";
            statement.executeUpdate(query1);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean SalvarPrisao(JSONObject jsonob) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            
            String passaporte = jsonob.getString("passaporte");
            //String dados = jsonob.getString("dados");
            
            String motivo = jsonob.getString("motivo");
            String id_prendeu = jsonob.getString("id_prendeu");
            String protocolo = jsonob.getString("protocolo");
            String justificado = jsonob.getString("justificado");
            
            String meses = jsonob.getString("meses");
            String multas = jsonob.getString("multas");
            String tornz = jsonob.getString("tornozeleira");
            String contravencoes = jsonob.getString("contravencoes");
            String data = System.currentTimeMillis()+"";
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            //String query1 = "update "+tabelad+" set passaporte='"+cid+"', nome='"+cnome+"', codigo='"+ccodigo+"', discord='"+cdiscord+"' where passaporte='"+ide+"'";
            String query1 = "INSERT INTO cb_prisoes (id_usuario, data, motivo, id_prendeu, protocolo, meses, multas, tornz, justificado, contravencoes) VALUES ('"+passaporte+"', '"+data+"', '"+motivo+"', '"+id_prendeu+"', '"+protocolo+"', '"+meses+"', '"+multas+"', '"+tornz+"', '"+justificado+"', '"+contravencoes+"')";
            statement.executeUpdate(query1);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean SalvarProcurado(JSONObject jsonob) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            
            String passaporte = jsonob.getString("passaporte");
            //String dados = jsonob.getString("dados");
            
            String motivo = jsonob.getString("motivo");
            String id_prendeu = jsonob.getString("id_prendeu");
            String protocolo = jsonob.getString("protocolo");
            String justificado = jsonob.getString("justificado");
            
            String meses = jsonob.getString("meses");
            String multas = jsonob.getString("multas");
            String tornz = jsonob.getString("tornozeleira");
            
            String data = System.currentTimeMillis()+"";
            
            String nivel_procurado = jsonob.getString("nivel_procurado");
            String obser = jsonob.getString("observacoes");
            
            String contravencoes = jsonob.getString("contravencoes");
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            //String query1 = "update "+tabelad+" set passaporte='"+cid+"', nome='"+cnome+"', codigo='"+ccodigo+"', discord='"+cdiscord+"' where passaporte='"+ide+"'";
            String query1 = "INSERT INTO cb_procurados (id_usuario, data, motivo, procurou_id, protocolo, meses, multas, tornz, justificado, nivel_procurado, observacao, contravencoes) VALUES ('"+passaporte+"', '"+data+"', '"+motivo+"', '"+id_prendeu+"', '"+protocolo+"', '"+meses+"', '"+multas+"', '"+tornz+"', '"+justificado+"', '"+nivel_procurado+"', '"+obser+"', '"+contravencoes+"')";
            statement.executeUpdate(query1);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean setLSPD(int LSPDE, int passaporte) {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);
            statement = connect.createStatement();
            String query1 = "INSERT INTO cb_usuarios (passaporte, lspd) VALUES ('"+passaporte+"', '"+LSPDE+"')";
            statement.executeUpdate(query1);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public ResultSet getUltimaPatente(String tabelad, String where, String where2, String DASC) {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from "+tabelad+" WHERE "+where+" LIKE '"+where2+"' ORDER BY id "+DASC+" LIMIT 1");
            
            return resultSet;
            

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
     public boolean AttProcurados(int user_id, String protocolo) {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+host+"/"+banco+"?"
                            + "user="+user+"&password="+pass);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            //String datar = System.currentTimeMillis()+"";
            // Result set get the result of the SQL query
            /*resultSet = statement
                    .executeQuery("update "+tabelad+" set passaporte="+cid+", nome="+cnome+", codigo="+ccodigo+", discord="+cdiscord+" where passaporte LIKE "+ide);
            */
            String query1 = "update cb_procurados set pagou='"+protocolo+"' where id_usuario='"+user_id+"' AND pagou=0";
            statement.executeUpdate(query1);
            System.out.println("Conectado ao servidor: "+host);
            return true;

        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String versao =     resultSet.getString("versao");
            String build =      resultSet.getString("build");
            Integer need =       resultSet.getInt("need");
            String link =       resultSet.getString("link");
            String mensagem =   resultSet.getString("mensagem");
            
            //Date date = resultSet.getDate("datum");
            
            System.out.println("versao: " + versao);
            System.out.println("build: " + build);
            System.out.println("need: " + need);
            System.out.println("link: " + link);
            System.out.println("mensagem: " + mensagem);
        }
        
    }
    
    public void ConfigCarregar() throws SQLException {
        // ResultSet is initially before the first data set
        //ArrayList<String> Conf = new ArrayList();
        
        ResultSet config = readDataBase("cb_config");
        while (config.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String versao =     config.getString("versao");
            String build =      config.getString("build");
            Integer need =       config.getInt("need");
            String link =       config.getString("link");
            String mensagem =   config.getString("mensagem");
            
            //Date date = resultSet.getDate("datum");
            /*Conf.add(build);
            Conf.add(versao);
            Conf.add(need.toString());
            Conf.add(link);
            Conf.add(mensagem);*/
            
            Config configu = new Config();
            configu.setBuild(build);
            configu.setVersao(versao);
            configu.setNeed(need.toString());
            configu.setLink(link);
            configu.setMensagem(mensagem);
            
            System.out.println("versao: " + versao);
            System.out.println("build: " + build);
            System.out.println("need: " + need);
            System.out.println("link: " + link);
            System.out.println("mensagem: " + mensagem);
        }
        close();
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
