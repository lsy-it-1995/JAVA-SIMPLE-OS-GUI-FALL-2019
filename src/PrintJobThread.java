import java.awt.print.PrinterJob;
import java.io.BufferedWriter;

public class PrintJobThread extends Thread{


    String fileName;
    int cur_printer;
    public PrintJobThread(String fileName){
        this.fileName = fileName;
        cur_printer = -1;
    }

    @Override
    public void run(){
        int available_Printer = Main.printer_resource_manager.request();
        cur_printer = available_Printer;
        FileInfo info = Main.dict_manage.lookup(fileName);
        for(int i = 0; i < info.file_length; i++){
            StringBuffer line = Main.disk_manager.disk[info.disk_number].read(info.start_sector+i);
            Main.printer_manager.printers[available_Printer].print(line);
        }
        Main.printer_resource_manager.release(available_Printer);
    }
}
