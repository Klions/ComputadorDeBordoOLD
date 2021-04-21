/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import org.json.JSONObject;
import police.InicializadorMain;

/**
 *
 * @author John
 */
public class ConfigUsuario {
    
    public static JSONObject getUsuarioPorID(int user_id){
        JSONObject usuario_get = new JSONObject();
        for(int i = 0; i < InicializadorMain.usuariosDBarray.length(); i++){
            JSONObject o = InicializadorMain.usuariosDBarray.getJSONObject(i);
            int pass = o.getInt("id_usuario");
            if(user_id == pass){
                usuario_get = o;
            }
        }
        return usuario_get;
    }
    
    public static JSONObject cb_userPorID(int user_id){
        for(int i = 0; i < InicializadorMain.cb_users.length(); i++){
            JSONObject obj = InicializadorMain.cb_users.getJSONObject(i);
            if(user_id == obj.getInt("user_id"))return obj;
        }
        return null;
    }
    
    public static boolean EhPolicial(int user_id){
        if(cb_userPorID(user_id) != null){
            for(int i = 0; i < InicializadorMain.hierarquiaDBarray.length(); i++){
                JSONObject ohier = InicializadorMain.hierarquiaDBarray.getJSONObject(i);
                if(user_id==ohier.getInt("id_usuario")){
                    int pter = ohier.getInt("cargo");
                    if(pter>0 && pter<99){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean JaFoiPolicial(int user_id){
        for(int i = 0; i < InicializadorMain.hierarquiaDBarray.length(); i++){
            JSONObject ohier = InicializadorMain.hierarquiaDBarray.getJSONObject(i);
            if(user_id==ohier.getInt("id_usuario")){
                return true;
            }
        }
        return false;
    }
    
    public static int getPatentePolicial(int user_id){
        int patente_policial = 0;
        if(cb_userPorID(user_id) != null){
            for(int i = 0; i < InicializadorMain.hierarquiaDBarray.length(); i++){
                JSONObject ohier = InicializadorMain.hierarquiaDBarray.getJSONObject(i);
                if(user_id==ohier.getInt("id_usuario")){
                    patente_policial = ohier.getInt("cargo");
                }
            }
        }
        return patente_policial;
    }
}
