/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author John
 */
public class ExportarOuImportar {
    public static String textAreaDialog(Object obj, String title, String text, boolean CampoAtivado, String ValorPrimario) {
        if(title == null) {
            title = "Janela";
        }
        if(ValorPrimario == null) {
            ValorPrimario = "Confirmar";
        }
        JTextArea textArea = new JTextArea(text);
        textArea.setColumns(30);
        textArea.setRows(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEnabled(CampoAtivado);
        textArea.setSize(textArea.getPreferredSize().width, textArea.getPreferredSize().height);
        
        Object[] options = { ValorPrimario, "Cancelar" };
        int ret = JOptionPane.showOptionDialog((Component) obj, new JScrollPane(textArea), title, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        //JOptionPane.showConfirmDialog((Component) obj, new JScrollPane(textArea), title, JOptionPane.OK_OPTION);
        if (ret == 0) {
            return textArea.getText();
        }
        return null;
    }
}
