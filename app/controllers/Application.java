package controllers;

import play.mvc.*;

import java.util.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void read() {
//        JOptionPane.showMessageDialog(null, "asdfasd");
        int n = MyRealtimeProcessCallback.n;
        Map m = new HashMap(1);
        m.put("m", n);
//        ControllerMessage m = f ? new ControllerMessage("Ok") : null;
        renderJSON(m);
    }
}