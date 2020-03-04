import javax.swing.*;

public class Main {
    public static int User_number = 0, Printer_number = 0, Disk_number = 0;
    public static int index = 0;
    public static String[] UserName;
    public static UserThread[] Users;
//    public static Printer[] printers;
    public static DiskManager disk_manager;

    public static PrinterManager printer_manager;
    public static ResourceManager disk_resource_manager;
    public static ResourceManager printer_resource_manager;
    public static DirectoryManager dict_manage;
    public static int speed = 0;
    public static void main(String[] args){
        if(args.length>0){
            try{
                speed= 0;
                User_number = Integer.parseInt(args[index++].substring(1));

                Users = new UserThread[User_number];

                Printer_number = Integer.parseInt(args[args.length-2].substring(1));
                Disk_number = Integer.parseInt(args[args.length-3].substring(1));

                disk_manager = new DiskManager(Disk_number);
                disk_resource_manager = new ResourceManager(Disk_number);
                printer_resource_manager = new ResourceManager(Printer_number);
                printer_manager = new PrinterManager(Printer_number);
                dict_manage = new DirectoryManager();
                UserName = new String[User_number];
                String gui = args[args.length-1];
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if(gui.equals("-ng")){
                            speed = 2;
                            JFrame frame = new JFrame("GUI");
                            JPanel info = new GUI(User_number,Printer_number, Disk_number);
                            frame.add(info);
                            frame.setSize(800,900);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.setVisible(true);
                        }
                    }
                });
                for(int i = 0 ;i <User_number;i++,index++){
                    UserName[i] = args[index];
                    Users[i] = new UserThread(UserName[i]);
                    Users[i].start();
                }

                for(int i = 0; i < User_number; i++){
                    Users[i].join();
                }


            }catch(Exception e){
                System.out.println("there is an invalid input");
            }
        }
    }
}

