package com.pazukdev.backend.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DownloadTest {

    public static void main(String[] args) throws IOException {
        final String sheetFileUrl = "https://docs.google.com/spreadsheets/d/1-wjosk677Z3iBk-gXA7WgryffXdpsD0pcPk8ld6U3Uo";
        final String docsFileUrl = "https://docs.google.com/document/d/1_dybfXVuNeziHoR-0Dllop7xluXsX2RjQvP1wTqgC1o";

        final String fileUrl = docsFileUrl;
        final String exportFormat = "txt";
        final URL url = new URL(fileUrl + "/export?format=" + exportFormat);

        System.out.println(url.getPath());

//        System.out.println(Files.readAllLines(url.getPath(), StandardCharsets.UTF_8););



        if (true) {
            return;
        }

        final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }

}
