
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;

public class MainClass {

    static boolean playingFlag;
    public static DLL d;
    public File f;
    public static File[] arr;
    public static String[] arr2;
    static FileInputStream FIS;
    static BufferedInputStream BIS;
    public static boolean loop;

    public static Player player;
    public static long pauseLocation;
    public static long songTotalLength;
    public static String fileLocation;

    public static void Stop() {
        if (player != null) {
            player.close();

            pauseLocation = 0;
            songTotalLength = 0;

            //PlayerGUI.Display.setText("");
            playingFlag = false;
        }
    }

    public void Pause() {
        if (player != null) {
            try {
                pauseLocation = FIS.available();
                player.close();
            } catch (IOException ex) {

            }
        }
    }

    public void Loop() {
        if (loop == false) {
            loop = true;
            System.out.println("Looping Active");
        } else {
            loop = false;
            System.out.println("Looping Inactive");
        }
    }

    

    public static void Play(Node root) {

        if (!d.isEmpty()) {
            try {
                String path = root.data.getPath();
                FIS = new FileInputStream(path);
                BIS = new BufferedInputStream(FIS);

                player = new Player(BIS);

                songTotalLength = FIS.available();

                fileLocation = path + "";

            } catch (FileNotFoundException | JavaLayerException ex) {

            } catch (IOException ex) {
            }
            new Thread() {
                @Override
                public void run() {
                    try {
                        PlayerGUI.Display.setText(root.data.getName());
                        PlayerGUI.currentTrack = root;
                        PlayerGUI.updateTracks();

                        PlayerGUI.updateList();
                        player.play();

                        playingFlag = true;
                        if (player.isComplete() && loop) {
                            if (root.next == null) {
                                PlayerGUI.nextTrack = d.root;
                                Play(PlayerGUI.nextTrack);
                            } else {
                                Play(PlayerGUI.nextTrack);
                            }

                        }

//                    if (player.isComplete()) {
//                        Play(fileLocation);
//                    }
//
//                    if (player.isComplete()) {
//                        PlayerGUI.Display.setText("");
//                    }
                    } catch (JavaLayerException ex) {

                    }
                }
            }
                    .start();

        }
    }

    public void Resume() {
        try {
            FIS = new FileInputStream(fileLocation);
            BIS = new BufferedInputStream(FIS);

            player = new Player(BIS);

            FIS.skip(songTotalLength - pauseLocation);

        } catch (FileNotFoundException | JavaLayerException ex) {

        } catch (IOException ex) {
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {

                }
            }
        }.start();

    }

    public void Initialize() {
        d = new DLL();
        playingFlag = false;
        loop = false;
        f = new File("Tracks");
        arr = f.listFiles();
        Merge_Sort(arr, 0, arr.length - 1);
        arr2 = new String[arr.length];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = arr[i].getName();
        }
    }

    public void Merge_Sort(File[] arr, int L, int U) {
        if (L < U) {
            int mid = (L + U) / 2;
            Merge_Sort(arr, L, mid);
            Merge_Sort(arr, mid + 1, U);
            Merge(arr, L, mid, U);
        }
    }

    public void Merge(File[] arr, int L, int mid, int U) {
        File tL[] = new File[mid - L + 1];
        File tR[] = new File[U - mid];
        for (int i = 0; i < tL.length; ++i) {
            tL[i] = arr[L + i];
        }
        for (int j = 0; j < tR.length; ++j) {
            tR[j] = arr[mid + 1 + j];
        }
        int i = 0, j = 0;
        int k = L;
        while (i < tL.length && j < tR.length) {
            if (tL[i].getName().compareTo(tR[j].getName()) < 1) {
                arr[k] = tL[i];
                i++;
            } else {
                arr[k] = tR[j];
                j++;
            }
            k++;
        }
        while (i < tL.length) {
            arr[k] = tL[i];
            i++;
            k++;
        }
        while (j < tR.length) {
            arr[k] = tR[j];
            j++;
            k++;
        }
    }
}
