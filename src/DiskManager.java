public class DiskManager{
    /*
    The DiskManager keeps track of the next free sector on each disk, which is useful for saving files.
    The DiskManager should contain the DirectoryManager for finding file sectors on Disk
     */
//    DirectoryManager directory_manager;
    Disk[] disk;

    public DiskManager(int number_of_disk){
        disk = new Disk[number_of_disk];
        for(int i = 0; i < number_of_disk; i++){
            disk[i] = new Disk();
        }
    }


    @Override
    public String toString() {
        String line = "";
        for(int i = 0; i < disk.length; i++){
            line += "disk number: " + i + " " + disk[i].toString()+"\n";
        }
        return line;
    }

}