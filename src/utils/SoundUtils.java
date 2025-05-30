
package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created on 06/05/2024, 17:52:06
 *
 * @author IPT - computer
 * @version 1.0
 */
public class SoundUtils {

    /**
     * Lê um som a partir de um recurso
     *
     * @param resourceName nome do recurso
     * @return objeto que representa o som
     * @throws IOException erros de I/O
     * @throws UnsupportedAudioFileException formato não suportado
     * @throws LineUnavailableException dados corrompidos
     */
    public static Clip loadResourceSound(String resourceName) throws Exception {
        //input stream para o recurso
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        //input stream para ler som
        AudioInputStream ain = AudioSystem.getAudioInputStream(in);
        //obter o objeto para tocar o som
        Clip clip = AudioSystem.getClip();
        //ler o som
        clip.open(ain);
        //retorn o som
        return clip;
    }

    //obtido no Chat GPT 3.5 (06/05/2024
    /**
     * altera o volume do som
     *
     * @param clip som
     * @param volume [0,1]
     */
    public static void setVolume(Clip clip, double volume) {
        // Obtém o controlador de ganho do clipe
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        // Calcula o valor em decibéis para o volume desejado
        float min = gainControl.getMinimum(); // ex: -80.0 dB
        float max = gainControl.getMaximum(); // ex: 6.0 dB
        volume = volume < 0.0001f ? 0.0001f : volume;// evita log(0)
        float ganhoDb = (float) (20.0 * Math.log10(volume)); // escala logaritmica
        float dB = Math.max(min, Math.min(ganhoDb, max)); // garante que está dentro dos limites
        // Define o ganho do clipe
        gainControl.setValue((float) dB);

    }

    public static void playSound(String name){
        try {

            File file = new File( name + ".wav");

            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.start();
        }
        catch (Exception es){
            System.out.println(es);
        }
    }

    public /*String*/ ArrayList<String> Soundtrack_list(/*int ii*/){

        String folder_path_name = "por_definir";
        int i = 0;
        File soundtrack_folder = new File(folder_path_name);
        String music_track_checker = "soundtrack";
        File[] sound_files_list = soundtrack_folder.listFiles();//lista de ficheiros no folder
        ArrayList<String> soundtrack_files_list = new ArrayList<String>();
        //diferenciar ficheiros para usar na soundtrack
        for (File file : sound_files_list) {
            if (file.getName().contains(music_track_checker)) {
                soundtrack_files_list.add("/por_definir/"+file.getName());
            }
        }
        //assert files != null;
       /* while(i<background_img_files_list.size()){
        System.out.println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII "+ background_img_files_list.get(i));i++;}
    */
        //return background_img_files_list.get(ii);
        return soundtrack_files_list;
    }
}
