package Utils;

import java.util.Locale;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.awt.image.BufferedImage;

public class CLIio {

    private BufferedImage   image;
    
    //--------------------INPUT--------------------
    private String          input;
    private Number          num;
    private String          input_path;
    private Integer         input_method;
    private Float           input_threshold;
    private Integer         input_block_size;
    private Float           input_target_compression;

    //--------------------OUTPUT--------------------
    private String          output_path;
    private double          output_file_size_before;
    private double          output_file_size_after;
    private double          output_compression_percentage;

    //-------------------help(me)-------------------
    private final boolean   INPUT_VALIDATION        = true;
    private final boolean   BONUS_TARGET_KOMPRESI   = false; // [ BONUS ] target kompresi


    public void cliInput() throws IOException {

        Scanner scanner = new Scanner(System.in);

        // Float Handling :]
        Locale userLocale = Locale.getDefault();
        NumberFormat format = NumberFormat.getInstance(userLocale);

        //--------------------ALAMAT ABSOLUT--------------------
        System.out.print("Path gambar: ");
        String input_filename = scanner.nextLine();
        
        while (!fileExist(input_filename)) {                       // file tidak valid
            System.out.println("ERR: File tidak ditemukan");
            System.out.print("Path gambar: ");
            input_filename = scanner.nextLine();
        }
        // read image
        input_path = "test/input/" + input_filename + ".png";
        image = ImageIO.read(new File(input_path));


        //--------------------METODE PERHITUNGAN--------------------
        // 1. Variance 2. MAD 3. MPD 4. Entropy
        while (INPUT_VALIDATION) {
            System.out.print("Metode perhitungan: ");
            input = scanner.nextLine();
            try {
                input_method = Integer.parseInt(input);
                if (input_method < 1 || input_method > 4) {             // metode tidak valid
                    System.out.println("ERR: Metode tidak valid");
                    continue;
                }
                break;
                
            } catch (NumberFormatException e) {                         // tipe data tidak betul
                System.out.println("Input tidak valid!");
                continue;
            }
        }


        //--------------------TRESHOLD--------------------
        while (INPUT_VALIDATION) {
            System.out.print("Threshold: ");
            input = scanner.nextLine();
            try {
                num = format.parse(input);
                input_threshold = num.floatValue();
                if (input_threshold < 0 || input_threshold > 1) {       // threshold tidak valid
                    System.out.println("ERR: Threshold tidak valid");
                    continue;
                }
                break;
                
            } catch (ParseException e) {                         // tipe data tidak betul
                System.out.println("Input tidak valid!");
                continue;
            }
        }


        //--------------------UKURAN BLOK MINIMUM--------------------
        while (INPUT_VALIDATION) {
            System.out.print("Ukuran blok minimum: ");
            input = scanner.nextLine();
            try {
                input_block_size = Integer.parseInt(input);
                if (input_block_size < 1) {                              // ukuran blok tidak valid
                    System.out.println("ERR: Ukuran blok tidak valid");
                    continue;
                }
                break;

            } catch (NumberFormatException e) {                         // tipe data tidak betul
                System.out.println("Input tidak valid!");
                continue;
            }
        }


        //--------------------[BONUS] TARGET KOMPRESI--------------------
        if (BONUS_TARGET_KOMPRESI) 
        {
            while (INPUT_VALIDATION) {
                System.out.print("Target presentase kompresi: ");
                input = scanner.nextLine();
                try {
                    num = format.parse(input);
                    input_target_compression = num.floatValue();
                    if (input_target_compression < 0 || input_target_compression > 1) { // target kompresi tidak valid
                        System.out.println("ERR: Target presentase kompresi tidak valid");
                        continue;
                    }
                    break;
                    
                } catch (ParseException e) {                         // tipe data tidak betul
                    System.out.println("Input tidak valid!");
                    continue;
                }
            }
        }
        

        //--------------------ALAMAT OUTPUT---------------------------
        System.out.print("Path output: ");
        String output_filename = scanner.nextLine();
        output_path = "test/output/" + output_filename + ".png";

        scanner.close();
        return;
    }

    public void cliOutput(long executionTime) {
        // WAKTU EKSEKUSI
        System.out.println("Waktu eksekusi: " + executionTime + " ms");

        // UKURAN GAMBAR SEBELUM
        output_file_size_before = Utils.getFileSizeKb(new File(getInputPath()));
        System.out.println("Ukuran gambar sebelum: " + output_file_size_before);

        // UKURAN GAMBAR SESUDAH
        output_file_size_after = Utils.getFileSizeKb(new File(getOutputPath()));
        System.out.println("Ukuran gambar sesudah: " + output_file_size_after);

        // PRESENTASE KOMPRESI
        output_compression_percentage = 
            (1 - (double) output_file_size_after / output_file_size_before) * 100;
        System.out.println("Presentase kompresi: " + output_compression_percentage);

        // KEDALAMAN POHON


        // BANYAK SIMPUL


        // [ BONUS ] GIF KOMPRESI


        return;
    }

    //----------------- HELPER ------------------
    public boolean fileExist(String filename) {
        return new File("test/input/" + filename + ".png").isFile();
    };


    //----------------- GETTER ------------------
    public BufferedImage getImage() {
        return image;
    }

    public String getInputPath() {
        return input_path;
    }

    public Integer getInputMethod() {
        return input_method;
    }

    public Float getInputThreshold() {
        return input_threshold;
    }

    public Integer getInputBlockSize() {
        return input_block_size;
    }

    public Float getInputTargetCompression() {
        return input_target_compression;
    }

    public String getOutputPath() {
        return output_path;
    }
}
