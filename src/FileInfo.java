public class FileInfo {
    int disk_number;
    int start_sector;
    int file_length;
    public FileInfo(){};


    public void clear(){
        disk_number = 0;
        start_sector = 0;
        file_length = 0;
    }
    public void set_disk_number(int input) {
        disk_number = input;
    }
    public void set_start_sector(int input){
        start_sector = input;
    }
    public void set_file_length(int input){
        file_length = input;
    }

    @Override
    public String toString() {
        return "disk: "+ disk_number +" sector: " + start_sector + " length: " + file_length;
    }
}
