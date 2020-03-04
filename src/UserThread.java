import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class UserThread extends Thread{
    File file;
    FileInfo info;
    public UserThread(){}
    String id;
    int current_disk = -1;
    boolean requestNewDisk;
    boolean printing;
    PrintJobThread currentPrint;

    public UserThread(String id){
        file  = new File(id);
        this.id = id;
        requestNewDisk = false;
        printing = false;
    }

    @Override
    public void run(){
        try{
            FileReader Freader = new FileReader("inputs/"+file);
            BufferedReader Breader = new BufferedReader(Freader);
            String key="", line ="";

            int file_length = 0, track = 0;

            while((line = Breader.readLine())!= null) {
                StringBuffer lineBuffer = new StringBuffer().append(line);
                if(line.contains(".save")){
                    key = line.substring(6);
                    requestNewDisk = true;
                    current_disk = Main.disk_resource_manager.request();
                    info = new FileInfo();
                    info.clear();
                    info.set_disk_number(current_disk);
                    track = Main.disk_manager.disk[current_disk].get_current_track();

                    info.set_start_sector(track);

                }else if(line.contains(".print")){
                    key = line.substring(7);
                    currentPrint = new PrintJobThread(key);
                    printing = true;
                    currentPrint.start();
                    currentPrint.join();

                }else if(line.contains(".end")){
                    info.set_file_length(file_length);
                    Main.dict_manage.enter(key,info);//key,info
                    file_length = 0;
                    key = "";
                    Main.disk_resource_manager.release(current_disk);
                }else{
                    track = Main.disk_manager.disk[current_disk].get_current_track();
                    Main.disk_manager.disk[current_disk].write(track,lineBuffer);
                    file_length++;
                }
            }
            Freader.close();
        }catch(Exception e){
            System.out.println("the file doesn't exist. the error prints " + e.toString());
        }
    }
}
