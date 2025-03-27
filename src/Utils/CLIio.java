package Utils;

import java.util.Scanner;

public class CLIio {
    public void cliInput() {

        Scanner scanner = new Scanner(System.in);

        // ALAMAT ABSOLUT DARI INPUT GAMBAR
        System.out.println("Path gambar: ");
        String input_path = scanner.nextLine();
        // TODO: Validasi path


        // METODE PERHITUNGAN
        System.out.println("Metode perhitungan: ");
        Integer input_method = scanner.nextInt();
        // TODO: validasi metode


        // TRESHOLD
        System.out.println("Threshold: ");
        Float input_threshold = scanner.nextFloat();
        // TODO: validasi threshold tiap metode


        // UKURAN BLOK MINIMUM
        System.out.println("Ukuran blok minimum: ");
        Integer input_block_size = scanner.nextInt();


        // TARGET PRESENTASE KOMPRESI
        System.out.println("Target presentase kompresi: ");
        Float input_target_compression = scanner.nextFloat();


        // ALAMAT OUTPUT
        System.out.println("Path output: ");
        String output_path = scanner.nextLine();



        return;
    }

    public void cliOutput() {
        return;
    }
}
