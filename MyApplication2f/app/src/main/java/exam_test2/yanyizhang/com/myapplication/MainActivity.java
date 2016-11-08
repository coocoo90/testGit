package exam_test2.yanyizhang.com.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        File path= Environment.getExternalStorageDirectory();
//
//        File f=new File(path,"lala.txt");
//
//        try {
//        FileOutputStream fos=new FileOutputStream(f);
//
//        for (int i = 0; i <10 ; i++) {
//
//                fos.write("lala\n".getBytes());
//
//        }
//        fos.flush();
//        fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        File path= Environment.getExternalStorageDirectory();

        File f=new File(path,"lala.txt");

        try {
            Scanner scanner=new Scanner(f);

            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
