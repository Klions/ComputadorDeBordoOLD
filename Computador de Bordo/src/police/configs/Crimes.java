/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

/**
 *
 * @author John
 */
public class Crimes {
    public String NomeCrime(int valor){
        switch (valor) {
            case 1:
                return "Atropelamento";
            case 2:
                return "Alta Velocidade";
            case 3:
                return "Direção Perigosa/Contramão";
            case 4:
                return "Ultrapassar Sinal Vermelho";
            case 5:
                return "Andar S/ Capacete em Rodovias";
            case 6:
                return "Dirigir sem Habilitação";
            case 7:
                return "Transitar com Veículo Danificado";
            case 8:
                return "Estacionar em Local Proibido";
            case 9:
                return "Dirigir Embriagado";
            case 10:
                return "Abandono de Veículo (Público)";

            case 11:
                return "Posse de Ilegal";    
            case 12:
                return "Posse de Lockpick";    
            case 13:
                return "Posse de Masterpick";    
            case 14:
                return "Posse de Pendrive";    
            case 15:
                return "Posse de Capuz";    
            case 16:
                return "Posse de Drogas";    
            case 17:
                return "Posse de Dinheiro Sujo";    
            case 18:
                return "Posse de Log de Banco";    
            case 19:
                return "Posse de Órgãos";    
            case 20:
                return "Posse de Armas/Munições";   

            case 21:
                return "Uso de Equipamento Balístico";     
            case 22:
                return "Uso de Equipamento Policial";     
            case 23:
                return "Uso de Máscara";     
            case 24:
                return "Porte ilegal (arma leve)";     
            case 25:
                return "Porte ilegal (arma pesada)";    


            case 26:
                return "Assalto à Banco/Joalheria";
            case 27:
                return "Assalto à Loja";
            case 28:
                return "Assalto à Caixa Eletrônico";
            case 29:
                return "Assalto à Caixa Registradora";
            case 30:
                return "Assalto à Carro Forte";

            case 31:
                return "Resistência á Prisão";     
            case 32:
                return "Omissão de Socorro";     
            case 33:
                return "Dano ao Patrimônio";     
            case 34:
                return "Furto";    
            case 35:
                return "Fuga da Abordagem";
            case 36:
                return "Corrida Ilegal";
            case 37:
                return "Tentativa de Roubo";
            case 38:
                return "Difamação";
            case 39:
                return "Obstrução de Justiça";
            case 40:
                return "Roubo";
            case 41:
                return "Multas Pendentes";
            case 42:
                return "Ato Obsceno";


            case 43:
                return "Desacato";
            case 44:
                return "Extorsão";
            case 45:
                return "Falsidade Ideológica";
            case 46:
                return "Calúnia";
            case 47:
                return "Ameaça";
            case 48:
                return "Corrupção";
            case 49:
                return "Poluição Sonora";
            case 50:
                return "Roubo de Veículo";
            case 51:
                return "Lesão Corporal";
            case 52:
                return "Sequestro";


            case 53:
                return "Homicídio Doloso";
            case 54:
                return "Homicídio Culposo";
            case 55:
                return "Tentativa de Homicídio";
            case 56:
                return "Latrocínio";
            case 57:
                return "Tentativa de Homicídio à Autoridade";
            case 58:
                return "Homicídio à Autoridade";
            case 59:
                return "Genocídio";

            default:
                return "";
        }
    }
    
    public int ValorMulta(int valor){
        switch (valor) {
            case 1:
                return 1000;
            case 2:
                return 1000;
            case 3:
                return 1500;
            case 4:
                return 700;
            case 5:
                return 1000;
            case 7:
                return 900;
            case 8:
                return 1200;
            case 9:
                return 5000;
            case 10:
                return 3000;
            case 11:
                return 5000;
            case 12:
                return 2000;
            case 13:
                return 3000;
            case 14:
                return 3000;
            case 15:
                return 1000;
                
            case 17:
                return 2500;
            case 18:
                return 2000;
            case 19:
                return 5000;
            case 20:
                return 50;    
            case 21:
                return 3000;
            case 22:
                return 2000;
            case 23:
                return 5000;
            case 24:
                return 3000;
            case 25:
                return 8000;
            case 26:
                return 4000;
            case 27:
                return 3000;
            case 28:
                return 2000;
            case 29:
                return 1500;
            case 30:
                return 3000;    
            case 31:
                return 3000;
            case 32:
                return 5000;
            case 33:
                return 500;
            case 34:
                return 1500;
            case 35:
                return 10000;
            case 36:
                return 4000;
            case 37:
                return 1500;
                
            case 39:
                return 1500;
            case 40:
                return 3000;    
                
            case 43:
                return 15000;
            case 45:
                return 3000;
                
            case 49:
                return 3000;
            case 50:
                return 3000;    
            case 51:
                return 3000;
            case 52:
                return 5000;
            case 53:
                return 5000;
            case 54:
                return 2500;
            case 55:
                return 2000;
            case 56:
                return 5000;
            case 57:
                return 5000;
            case 58:
                return 10000;
            case 59:
                return 25000;
                
            default:
                return 0;
        }
    }
    
    public int ValorMeses(int valor){
        switch (valor) {
            case 11:
                return 20;
            case 12:
                return 10;
            case 13:
                return 10;
            case 14:
                return 10;
            case 15:
                return 5;
            case 16:
                return 15;   
            case 17:
                return 10;
            case 18:
                return 5;
            case 19:
                return 10;
            case 20:
                return 0;    
            case 21:
                return 10;
            case 22:
                return 5;
            case 23:
                return 0;
            case 24:
                return 15;
            case 25:
                return 30;
            case 26:
                return 40;
            case 27:
                return 30;
            case 28:
                return 20;
            case 29:
                return 15;
            case 30:
                return 30;    
            case 31:
                return 20;
            case 32:
                return 15;
            case 33:
                return 10;
            case 34:
                return 15;
            case 35:
                return 10;
            case 36:
                return 20;
            case 37:
                return 10;
            case 38:
                return 10;
            case 39:
                return 10;
            case 40:
                return 20;
            /*case 41:
                return 10;*/
            case 42:
                return 10;
            case 43:
                return 10;
            case 44:
                return 15;
            case 45:
                return 10;
            case 46:
                return 10;
            case 47:
                return 25;
            case 48:
                return 15;
            case 49:
                return 15;
            case 50:
                return 25;    
            case 51:
                return 15;
            case 52:
                return 10;
            case 53:
                return 70;
            case 54:
                return 50;
            case 55:
                return 45;
            case 56:
                return 90;
            case 57:
                return 70;
            case 58:
                return 100;
            case 59:
                return 150;
                
            default:
                return 0;
        }
    }
}
