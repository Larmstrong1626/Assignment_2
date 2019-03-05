package com.oc.assignment02;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;

public class FileOperations {


    String filename;



    int highscore;





    public FileOperations(Context context,String filename) {

        super();


        highscore=this.readHighScore(context,filename);
        //this.writeHighScore(highscore,context);

    }



    public int readHighScore(Context context,String filename) {
        int highscore=0;
        Scanner scanner;
        try {
            FileInputStream scoreFile = context.openFileInput(filename);
            scanner = new Scanner(scoreFile);

            while (scanner.hasNext()) {
                highscore = scanner.nextInt();

            }
            scanner.close();
        }
        catch (FileNotFoundException e) {

            highscore = 0;
        }
        return highscore;
    }

    public void writeHighScore(int highscore, Context context) {

        try {
            OutputStreamWriter writer;
            BufferedWriter buf;
            PrintWriter printer;
           FileOutputStream outputFile = context.openFileOutput(filename, MODE_PRIVATE);
                writer = new OutputStreamWriter(outputFile);

            buf = new BufferedWriter(writer);
            printer = new PrintWriter(buf);

            printer.println(highscore);

            printer.close();


        } catch (FileNotFoundException e) {

        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
