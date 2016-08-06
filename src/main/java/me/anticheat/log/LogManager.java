package me.anticheat.log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.anticheat.Main;
import org.bukkit.Bukkit;

public class LogManager {

    public static File file;
    public static void createDirections() {
        File mainfile = new File("plugins/VetoxCheat");
        if(!mainfile.exists()) mainfile.mkdir();

        File mainfile2 = new File("plugins/VetoxCheat/logs");
        if(!mainfile2.exists()) mainfile2.mkdir();

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {


            public void run() {
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                Calendar c = df.getCalendar();
                c.setTimeInMillis(System.currentTimeMillis());


                String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                File f = new File("plugins/VetoxCheat/logs/" + day + ":" + month + ".log");
                if(!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                file = f;

            }
        },20);




    }

    public static BufferedWriter bw;

    public static void saveLogs(Main main) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {

            public void run() {
                //Initialisierungen:
                FileWriter fw;
                try {
                    fw = new FileWriter(file.getAbsoluteFile(), true);
                    bw = new BufferedWriter(fw);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //Forschleife:
                for(Log log : Log.logs) {
                    try {

                        bw.write("[" + log.getHour() + ":" + log.getMinute() + "] " + log.getType().getName() + ":" + log.getSender() + ":" + log.getMessage());
                        bw.newLine();



                    } catch (IOException e) { System.err.println("Ein fehler ist aufgetreten: saveLogs()");
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Schließen:
                Log.oldlogs = Log.logs;
                Log.logs = new ArrayList();
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }, 20*4, 20*60*4);

    }

    public static void saveLogsOnce(Main main) {

        //Initialisierungen:
        FileWriter fw;
        try {
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //Forschleife:
        for(Log log : Log.logs) {
            try {

                bw.write("[" + log.getHour() + ":" + log.getMinute() + "] " + log.getType().getName() + ":" + log.getSender() + ":" + log.getMessage());
                bw.newLine();



            } catch (IOException e) { System.err.println("Ein fehler ist aufgetreten: saveLogs()");
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Schließen:
        Log.oldlogs = Log.logs;
        Log.logs = new ArrayList();
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}