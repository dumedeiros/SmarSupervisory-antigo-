/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import br.ufrn.lii.brcollector.callback.AbstractRealtimeProcessCallback;
import br.ufrn.lii.commonsdomain.process.ProcessData;
import br.ufrn.lii.commonsdomain.process.TagItem;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author SmarDidactic
 */
public class MyRealtimeProcessCallback extends AbstractRealtimeProcessCallback {
    public static int n = 0;
    
    public MyRealtimeProcessCallback() throws RemoteException {
    }

    public void onDataChange(HashMap<TagItem, ProcessData> hm) throws RemoteException {
        n++;
        Set<TagItem> keySet = hm.keySet();
        JOptionPane.showConfirmDialog(null, n);
        System.out.println("Recebeu alguma coisa");

        for (TagItem tagItem : keySet) {
            System.out.println("Variavel " + tagItem.getIdStr() + " = " + hm.get(tagItem).getValue().getStringValue());
        }
    }
}
