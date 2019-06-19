import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PlayMusic {


    private File musicToPlay;
    Thread playThread;
    private Thread resumeThread;
    private JButton pause=new JButton();
    private JButton resume=new JButton();

    private BufferedInputStream bufferedInputStream;
    FileInputStream fileInputStream;
    private long totalLength;
    Player player;
    private long pause1;


    public PlayMusic(File file) {

        musicToPlay = file;



        try {

            playThread = new Thread(new Runnable() {


                @Override
                public void run() {


                    try {
                        try {
                            fileInputStream=new FileInputStream(musicToPlay);
                            try {
                                totalLength=fileInputStream.available();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        bufferedInputStream = new BufferedInputStream(fileInputStream);
                    player = new Player(bufferedInputStream);
                    player.play();

                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }

                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pause1 = fileInputStream.available();
                    player.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        resumeThread=new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    fileInputStream=new FileInputStream(musicToPlay);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bufferedInputStream=new BufferedInputStream(fileInputStream);
                try {
                    fileInputStream.skip(totalLength-pause1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    player.play();

                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }

                try {


                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }


            }
        });

        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeThread.start();
            }
        });


    }


}





