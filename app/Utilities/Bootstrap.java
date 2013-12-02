package Utilities;

import controllers.Application;
import controllers.TestRDA;

import javax.swing.JOptionPane;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

//TODO fazer os inputs de malhaAberta changable

@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() {
        TestRDA testRDA = new TestRDA();
        testRDA.startTests();
    }
}
