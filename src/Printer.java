import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
/*
Each printer will write data to a file named PRINTERi where i is the index of this printer (starting at 1).
A printer can only handle one line of text at a time. It will take 2750 milliseconds to print one line;
the thread needs to sleep to simulate this delay before the printing job finishes.
 */
    File file;
    BufferedWriter writer;
    public Printer(int printerID){
        try{
            writer = new BufferedWriter(new FileWriter("PRINTER" + printerID));
        }catch (IOException e){
            System.out.println("file can't create. the error message is " + e.toString());
        }
    }

    public void print(StringBuffer data){
        try{
            try {
                if(Main.speed==1)
                    Thread.sleep(5500);
                else if(Main.speed ==2 || Main.speed == 0){
                    Thread.sleep(2750 );
                }else if(Main.speed ==3){
                    Thread.sleep(1375 );
                }else{
                    Thread.sleep(917 );
                }
                String line = data.toString();
                writer.write(line);
                writer.newLine();
                writer.flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}