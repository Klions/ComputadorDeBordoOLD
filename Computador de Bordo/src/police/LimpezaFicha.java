/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;
import police.configs.ConexaoDB;
import police.configs.Config;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class LimpezaFicha extends javax.swing.JFrame {

    /**
     * Creates new form Porte
     */
    InicializadorMain policia = new InicializadorMain();
    Usuario usuarios = new Usuario();
    ConexaoDB conexao = new ConexaoDB();
    JSONArray usuariosDBarray = new JSONArray();
    JSONArray discordDBarray = new JSONArray();
    JSONArray prisoesDBarray = new JSONArray();
    JSONArray blacklistDBarray = new JSONArray();
    JSONObject limparArray = new JSONObject();
    Config config = new Config();
    JDA jda;
    public LimpezaFicha() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(config.img_CBIcone)));
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        
        jPanel1.setBackground(new java.awt.Color(13, 32, 64));
        jPanel3.setBackground(new java.awt.Color(13, 32, 64));
        jPanel4.setBackground(new java.awt.Color(13, 32, 64));
        jPanel5.setBackground(new java.awt.Color(13, 32, 64));
        jPanel6.setBackground(new java.awt.Color(13, 32, 64));
        jPanel7.setBackground(new java.awt.Color(13, 32, 64));
        
        usuariosDBarray = InicializadorMain.usuariosDBarray;
        blacklistDBarray = InicializadorMain.blacklistDBarray;
        discordDBarray = InicializadorMain.discordDBarray;
        prisoesDBarray = InicializadorMain.prisoesDBarray;
        
        /*usuariosDBarray = policia.AttDBUsuarios();
        //prisoesDBarray = usuarios.AttDBPrisoes();
        blacklistDBarray = usuarios.AttBlackList();
        discordDBarray = policia.AttDBDiscord();*/
        jda = policia.getJDA();
        Resetar();
        //Timere();
    }
    
    public void AttDbs(){
        policia.AttDBSTodas();
        
        prisoesDBarray = InicializadorMain.prisoesDBarray;
        usuariosDBarray = InicializadorMain.usuariosDBarray;
        blacklistDBarray = InicializadorMain.blacklistDBarray;
        discordDBarray = InicializadorMain.discordDBarray;
        //prisoesDBarray = usuarios.AttDBPrisoes();
    }
    int TimerAtte=0;
    boolean AtivarTimer=false;
    
    /*public void Timere (){
        int delay = 500;   // tempo de espera antes da 1ª execução da tarefa.
        int interval = 20000;  // intervalo no qual a tarefa será executada.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
          public void run() {
            //System.out.println("Olá World!"); 
            if(AtivarTimer){
                TimerAtte++;
                jLabel6.setText("INICIADO");
                jLabel6.setForeground(new java.awt.Color(51,153,51));
                jLabel5.setForeground(new java.awt.Color(51,153,51));
                iniciar.setEnabled(false);
                pausar.setEnabled(true);
                //jLabel5.setText(Tempo(TimerAtte));
                //attTab.setEnabled(false);
            }else{
                jLabel6.setText("PAUSADO");
                jLabel6.setForeground(new java.awt.Color(204,51,51));
                jLabel5.setForeground(new java.awt.Color(204,51,51));
                iniciar.setEnabled(true);
                pausar.setEnabled(false);
                //attTab.setEnabled(true);
                //attTab.setText("ATUALIZAR AGORA");
            }
            jLabel5.setText(Tempo(TimerAtte));
          }
        }, delay, interval);
    }
    
    public String Tempo(int numero){
        int sec_num = numero; // don't forget the second param
        int hours   = sec_num / 3600;
        int minutes = (sec_num - (hours * 3600)) / 60;
        int seconds = sec_num - (hours * 3600) - (minutes * 60);

        String hora=hours+"";
        String minutos=minutes+"";
        String segundos=seconds+"";
        if (hours   < 10) {hora   = "0"+hours;}
        if (minutes < 10) {minutos = "0"+minutes;}
        if (seconds < 10) {segundos = "0"+seconds;}
        return hora+"h:"+minutos+"m:"+segundos+"s";
    }*/
    public void Resetar(){
        limparArray = new JSONObject();
        passaporteTxt.setText("SEM REGISTRO");
        idadeTxt.setText("SEM REGISTRO");
        nomeTxt.setText("SEM REGISTRO");
        identidadeTxt.setText("SEM REGISTRO");
        telefoneTxt.setText("SEM REGISTRO");
        situacaoTxt.setText("SEM REGISTRO");
        jPanel5.setVisible(false);
        jPanel6.setVisible(false);
        penatotalTxt.setText("FAÇA A PESQUISA");
        ultimoregistroTxt.setText("Pesquise pelo ID");
        tempoesperaTxt.setText("");
        //AttDbs();
        pack();
        ID.setText("");
        ID.requestFocus();
    }
    public void AttInfo(){
        String tpassa = ID.getText();
        if(tpassa.length() <= 0)return;
        boolean contare=false;
        boolean fichasuja=false;
        PrettyTime p = new PrettyTime();
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject o = usuariosDBarray.getJSONObject(i);
            int paser = Integer.parseInt(tpassa);
            if(paser == o.getInt("id_usuario")){
                contare=true;
                int vzespreso = 0;
                int vzesmulta = 0;
                int mesestotal=0;
                String DataPreso="";
                long DataPresoN=0;
                for(int i2 = 0; i2 < prisoesDBarray.length(); i2++){
                    JSONObject ohier = prisoesDBarray.getJSONObject(i2);
                    //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
                    int meseer = ohier.getInt("meses");
                    int multaar = ohier.getInt("multas");
                    
                    //System.out.println("meseer: "+meseer+" // ");
                    if(paser==ohier.getInt("id_usuario")){
                        if("".equals(ohier.getString("limpo"))){
                            if(meseer > 0){
                                situacaoTxt.setText("Baixo índice de criminalidade");
                                vzespreso++;
                                DataPreso= p.format(new Date(ohier.getLong("data")));
                                DataPresoN=ohier.getLong("data");
                                mesestotal+=meseer;
                                
                            }
                            if(multaar > 0 && meseer <= 0){
                                vzesmulta++;
                            }
                        }
                    }
                }
                //Grau de criminalidade
                situacaoTxt.setText("Nenhum índice de criminalidade");
                if(vzespreso > 0) situacaoTxt.setText("Baixo índice de criminalidade");
                if(vzespreso > 3) situacaoTxt.setText("Médio índice de criminalidade");
                if(vzespreso > 7) situacaoTxt.setText("Alto índice de criminalidade");
                if(vzespreso > 10) situacaoTxt.setText("Altíssimo índice de criminalidade");
                                
                String passaporte = o.getString("id_usuario");
                String identidade = o.getString("registration");
                String idade = o.getString("age");
                if("".equals(idade))idade="0";
                idade+=" anos";
                String telefone = o.getString("phone");
                String nomer="Sem";
                String sobrenomer="Registro";
                if(o.getString("nome").length() > 0)nomer=o.getString("nome");
                if(o.getString("sobrenome").length() > 0)sobrenomer=o.getString("sobrenome");
                String nome = nomer+" "+sobrenomer;

                nomeTxt.setText(nome);
                passaporteTxt.setText(passaporte);
                idadeTxt.setText(idade);

                identidadeTxt.setText(identidade);
                telefoneTxt.setText(telefone);
                
                if(vzespreso > 0){
                    fichasuja=true;
                    if(DataPreso == ""){
                        //jPanel7.setVisible(false);
                        jPanel5.setVisible(false);
                        jPanel6.setVisible(false);
                    }else{
                        //jPanel7.setVisible(true);
                        jPanel5.setVisible(true);
                        jPanel6.setVisible(true);
                    }

                    long contaBase = System.currentTimeMillis() - (172800*1000);
                    if(DataPresoN > contaBase){
                        tempoesperaTxt.setText("IMPOSSÍVEL LIMPAR A FICHA (PRESO RECENTEMENTE)");
                        limpar.setEnabled(false);
                    }else{
                        tempoesperaTxt.setText("FICHA COM DISPONIBILIDADE DE LIMPEZA");
                        limpar.setEnabled(true);
                    }
                    ultimoregistroTxt.setText("Ultima vez preso à "+DataPreso);
                    int valorpagar = mesestotal*2;
                    penatotalTxt.setText("VEZES PRESO: "+vzespreso+" / PENAL TOTAL: "+mesestotal+" meses");
                    valorTxt.setText("$ "+String.format("%,d", (valorpagar*1000))+" ("+valorpagar+"k)");
                    limparTxt.setText("LIMPEZA DE "+mesestotal+" MESES");
                    //copiar.setEnabled(true);
                    
                    limparArray = new JSONObject();
                    JSONObject usua = new JSONObject(usuarios.getDados());
                    //prenderDBarray=usuariosDBarray;
                    limparArray.put("id_usuario", passaporte);
                    limparArray.put("id_limpou", usua.getString("id_usuario"));
                    limparArray.put("pago", valorpagar+"");
                    limparArray.put("meses", mesestotal+"");
                    limparArray.put("vezespreso", vzespreso+"");
                }
            }
        }
        if(!contare){
            passaporteTxt.setText("SEM REGISTRO");
            idadeTxt.setText("SEM REGISTRO");
            nomeTxt.setText("SEM REGISTRO");
            identidadeTxt.setText("SEM REGISTRO");
            telefoneTxt.setText("SEM REGISTRO");
            situacaoTxt.setText("SEM REGISTRO");
        }
        if(!fichasuja){
            //jPanel7.setVisible(false);
            jPanel5.setVisible(false);
            jPanel6.setVisible(false);
            penatotalTxt.setText("NUNCA FOI PRESO");
            ultimoregistroTxt.setText("Ficha completamente limpa");
            tempoesperaTxt.setText("NÃO É POSSÍVEL LIMPAR ESTA FICHA");
        }
        pack();
        //AtualizarCrimes();
        ID.requestFocus();
    }
    
    public void MensagemDiscordFicha(JSONObject dados){
        
        int id_usuario = dados.getInt("id_usuario");
        int id_limpou = dados.getInt("id_limpou");
        String protocolo = dados.getString("protocolo");
        
        int pago = dados.getInt("pago");
        int meses = dados.getInt("meses");
        int vezespreso = dados.getInt("vezespreso");
        
        String NomeCidadao="Sem Registro";
        String NomeOficial="Sem Registro";
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject o = usuariosDBarray.getJSONObject(i);
            if(id_usuario == o.getInt("id_usuario")){
                NomeCidadao=o.getString("nome")+" "+o.getString("sobrenome");
            }
            if(id_limpou == o.getInt("id_usuario")){
                NomeOficial=o.getString("nome")+" "+o.getString("sobrenome");
            }
        }
        String Texto = "**"+NomeCidadao+"** teve a ficha limpa.";
        
        int cor = 0x339933;
        
        String canal="673959994747519006"; //relatorio-de-prisao
        TextChannel usuar = jda.getTextChannelById​(Long.parseLong(canal));
        
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Limpeza de ficha - Protocolo: *"+protocolo+"*", null);
        eb.setColor(new Color(cor));
        eb.setDescription(Texto);
        eb.addField("Cidadão", ":construction_worker: "+NomeCidadao+" ("+id_usuario+")", true);
        eb.addField("Oficial", ":police_officer: "+NomeOficial+" ("+id_limpou+")", true);
        eb.addField("Protocolo", ":pencil: "+protocolo, true);
        String PresoVz;
        if(vezespreso == 1){PresoVz=vezespreso+" vez";}else{PresoVz=vezespreso+" vezes";}
        String MesesVz;
        if(meses == 1){MesesVz=meses+" mês";}else{MesesVz=meses+" meses";}
        eb.addField("Valor Pago", ":dollar: $ "+String.format("%,d", (pago*1000)), true);
        eb.addField("Tempo Total", ":hourglass: "+MesesVz, true);
        eb.addField("Vezes Preso", ":file_cabinet: "+PresoVz, true);
        
        eb.addField("Info", "A ficha deste cidadão foi limpa, mas isso não quer dizer que ele não possa ser preso novamente à partir daqui.", false);
        
        //eb.addField("Notificação", "Para desativar as notificações digite", false);
        
        eb.setFooter("Computador de Bordo [ver. "+config.versao+"]", null);

        eb.setThumbnail(config.img_DiscordPolicia);
        Instant instant = Instant.from(ZonedDateTime.now());
        eb.setTimestamp(instant);
        
        usuar.sendMessage(eb.build()).queue();
    }
    
    public void MensagemPrivado(JSONObject dados){
        int id_usuario = dados.getInt("id_usuario");
        int id_limpou = dados.getInt("id_limpou");
        String protocolo = dados.getString("protocolo");
        
        int pago = dados.getInt("pago");
        int meses = dados.getInt("meses");
        int vezespreso = dados.getInt("vezespreso");
        
        String Nome="Sem Registro";
        String Rg="Sem Registro";
        String Telefone="Sem Registro";
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject o = usuariosDBarray.getJSONObject(i);
            if(id_usuario == o.getInt("id_usuario")){
                Nome=o.getString("nome")+" "+o.getString("sobrenome");
                Rg=o.getString("registration");
                Telefone=o.getString("phone");
            }
        }
        int bloqueado=0;
        boolean discorde=false;
        String discordt="";
        for(int i = 0; i < discordDBarray.length(); i++){
            JSONObject o = discordDBarray.getJSONObject(i);
            if(id_usuario==o.getInt("user_id")){
                for(int i2 = 0; i2 < blacklistDBarray.length(); i2++){
                    JSONObject o2 = blacklistDBarray.getJSONObject(i2);
                    if(o2.getInt("user_id") == id_usuario){
                        bloqueado=o2.getInt("bloqueado");
                    }
                }
                discordt = o.getString("identifier");
                discorde=true;
            }
        }
        //User usuar = jda.getUserByTag(DiscordTag);
        if(discorde && bloqueado==0){
            User usuar = jda.getUserById(discordt);
            EmbedBuilder eb = new EmbedBuilder();
            int cor = 0x339933;
            eb.setTitle("Limpeza de ficha - Protocolo: *"+protocolo+"*", null);
            eb.setColor(new Color(cor));
            eb.setDescription(":rotating_light: Ficha limpa na **"+config.NomePolicia+"**! Não cometa mais crimes!!");
            eb.addField("Nome Completo", ":construction_worker: "+Nome+" ("+id_usuario+")", true);
            eb.addField("RG", ":clipboard: "+Rg, true);
            eb.addField("Telefone", ":iphone: "+Telefone, true);
            String PresoVz;
            if(vezespreso == 1){PresoVz=vezespreso+" vez";}else{PresoVz=vezespreso+" vezes";}
            String MesesVz;
            if(meses == 1){MesesVz=meses+" mês";}else{MesesVz=meses+" meses";}
            eb.addField("Valor Pago", ":dollar: $ "+String.format("%,d", (pago*1000)), true);
            eb.addField("Tempo Total", ":hourglass: "+MesesVz, true);
            eb.addField("Vezes Preso", ":file_cabinet: "+PresoVz, true);

            eb.addField("Info", "Sua ficha foi limpa, mas isso não quer dizer que você não possa ser preso novamente.", false);
            
            //eb.addField("Notificações", ":speech_balloon: Você pode desativar o recebimento de nossas notificações digitando '!notificar'.", false);
            eb.addField("Deseja parar de receber notificação?", ":speech_balloon: Mande '!notificar' para alternar as notificações.", false);
            eb.setFooter("Computador de Bordo [ver. "+config.versao+"]", null);
            eb.setThumbnail(config.img_DiscordPolicia);

            usuar.openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage(eb.build()).queue();
            });
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ID = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        nomeTxt = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        passaporteTxt = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        idadeTxt = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        telefoneTxt = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        identidadeTxt = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        situacaoTxt = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        valorTxt = new javax.swing.JLabel();
        limparTxt = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        limpar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        penatotalTxt = new javax.swing.JLabel();
        ultimoregistroTxt = new javax.swing.JLabel();
        tempoesperaTxt = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LIMPAR FICHA");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID:");

        ID.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IDKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ID)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ID, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INFORMAÇÕES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NOME COMPLETO");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        nomeTxt.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        nomeTxt.setForeground(new java.awt.Color(255, 255, 255));
        nomeTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeTxt.setText("Carregando...");
        nomeTxt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("PASSAPORTE");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        passaporteTxt.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        passaporteTxt.setForeground(new java.awt.Color(255, 255, 255));
        passaporteTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passaporteTxt.setText("Carregando...");
        passaporteTxt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("IDADE");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        idadeTxt.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        idadeTxt.setForeground(new java.awt.Color(255, 255, 255));
        idadeTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idadeTxt.setText("Carregando...");
        idadeTxt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4)
                            .addComponent(nomeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(passaporteTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idadeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passaporteTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idadeTxt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("LIMPEZA DE FICHA");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BANCO DE DADOS DA POLÍCIA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("TELEFONE");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        telefoneTxt.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        telefoneTxt.setForeground(new java.awt.Color(255, 255, 255));
        telefoneTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        telefoneTxt.setText("Carregando...");
        telefoneTxt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("IDENTIDADE");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        identidadeTxt.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        identidadeTxt.setForeground(new java.awt.Color(255, 255, 255));
        identidadeTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        identidadeTxt.setText("Carregando...");
        identidadeTxt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("SITUAÇÃO");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        situacaoTxt.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        situacaoTxt.setForeground(new java.awt.Color(255, 255, 255));
        situacaoTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        situacaoTxt.setText("Carregando...");
        situacaoTxt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addComponent(telefoneTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(identidadeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(situacaoTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(telefoneTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identidadeTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(situacaoTxt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VALOR A SER PAGO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("VALOR");

        valorTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        valorTxt.setForeground(new java.awt.Color(0, 153, 0));
        valorTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        valorTxt.setText("$ 999.999.999");

        limparTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        limparTxt.setForeground(new java.awt.Color(255, 255, 255));
        limparTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        limparTxt.setText("LIMPEZA DE ### MESES");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(valorTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(limparTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valorTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(limparTxt)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SALVAR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        limpar.setBackground(new java.awt.Color(51, 153, 51));
        limpar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        limpar.setForeground(new java.awt.Color(255, 255, 255));
        limpar.setText("LIMPAR");
        limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparActionPerformed(evt);
            }
        });

        cancelar.setBackground(new java.awt.Color(204, 51, 51));
        cancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelar.setForeground(new java.awt.Color(255, 255, 255));
        cancelar.setText("CANCELAR");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(limpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CRIMES COMETIDOS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 0, 11), new java.awt.Color(204, 204, 204))); // NOI18N

        penatotalTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        penatotalTxt.setForeground(new java.awt.Color(255, 255, 255));
        penatotalTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        penatotalTxt.setText("VEZES PRESO: ## / PENAL TOTAL: ##");

        ultimoregistroTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ultimoregistroTxt.setForeground(new java.awt.Color(255, 255, 255));
        ultimoregistroTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ultimoregistroTxt.setText("ÚLTIMO REGISTRO À");

        tempoesperaTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tempoesperaTxt.setForeground(new java.awt.Color(255, 51, 51));
        tempoesperaTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tempoesperaTxt.setText("ÚLTIMO REGISTRO À");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(penatotalTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ultimoregistroTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tempoesperaTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(penatotalTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ultimoregistroTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tempoesperaTxt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu2.setText("FECHAR");

        jMenuItem2.setText("VOLTAR PARA O PAINEL");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("EXIBIR");
        jMenu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jMenuItem3.setText("SOBRE");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IDKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AttInfo();
        }
    }//GEN-LAST:event_IDKeyPressed

    private void limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparActionPerformed
        Random rand = new Random(); 
        String Procol = (rand.nextInt(899)+100)+""; 
        Calendar cal = Calendar.getInstance(); 
        cal.getTime(); 
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        //System.out.println(" / "+sdf.format(cal.getTime()));
        Procol=sdf.format(cal.getTime())+Procol;
        
        JSONObject my_obj = new JSONObject();
        int iduser = limparArray.getInt("id_usuario");
        my_obj.put("id_usuario", limparArray.getString("id_usuario"));
        my_obj.put("id_limpou", limparArray.getString("id_limpou"));
        my_obj.put("protocolo", Procol);
        my_obj.put("pago", limparArray.getString("pago"));
        my_obj.put("meses", limparArray.getString("meses"));
        my_obj.put("vezespreso", limparArray.getString("vezespreso"));
        if(conexao.LimparFicha(iduser, my_obj)){
            showMessageDialog(null,
                "ID: "+iduser+" teve a ficha limpa com sucesso com o protocolo: "+Procol+" !",
                "Salvo com sucesso!!",
                JOptionPane.PLAIN_MESSAGE);
            MensagemDiscordFicha(my_obj);
            MensagemPrivado(my_obj);
            AttDbs();
            Resetar();
        }
    }//GEN-LAST:event_limparActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        Resetar();
    }//GEN-LAST:event_cancelarActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new Painel().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        InicializadorMain.sobre.setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LimpezaFicha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LimpezaFicha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LimpezaFicha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LimpezaFicha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LimpezaFicha().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ID;
    private javax.swing.JButton cancelar;
    private javax.swing.JLabel idadeTxt;
    private javax.swing.JLabel identidadeTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JButton limpar;
    private javax.swing.JLabel limparTxt;
    private javax.swing.JLabel nomeTxt;
    private javax.swing.JLabel passaporteTxt;
    private javax.swing.JLabel penatotalTxt;
    private javax.swing.JLabel situacaoTxt;
    private javax.swing.JLabel telefoneTxt;
    private javax.swing.JLabel tempoesperaTxt;
    private javax.swing.JLabel ultimoregistroTxt;
    private javax.swing.JLabel valorTxt;
    // End of variables declaration//GEN-END:variables
}
