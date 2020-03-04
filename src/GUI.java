import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GUI extends JPanel {
    JPanel printer_panel, user_panel, disk_panel, speed_control, main, main2,printer_info, disk_info, user_info;
    int user, printer, disk;
    private int icon_size = 50;
    JTextArea[] disk_area, user_area, printer_area;
    Timer timer;
    int[] disk_update;
    JSlider speed_slider;

    public GUI(int user, int printer, int disk){
        printer_panel = new JPanel();
        user_panel = new JPanel();
        disk_panel = new JPanel();
        speed_control = new JPanel();
        printer_info = new JPanel();
        disk_info = new JPanel();
        user_info = new JPanel();
        main2 = new JPanel();
        main = new JPanel();

        this.user = user;
        this.printer = printer;
        this.disk = disk;
        disk_area = new JTextArea[disk];
        user_area = new JTextArea[user];
        printer_area = new JTextArea[printer];

        disk_update = new int[disk];

        setup();
        this.add(main);
        this.repaint();
        this.revalidate();

        System.out.println("disk: " +disk);
        System.out.println("user: " +user);
        System.out.println("printer: " +printer);
    }

    public void setup(){
        run_per_second();
        timer.start();
        main.setLayout(new BorderLayout());

        disk_panel_setup();
        user_panel_setup();
        printer_panel_setup();
        speed_control_panel_setup();
//        buttons();

        JPanel user_control = new JPanel(new BorderLayout());
        user_control.add(user_panel, BorderLayout.NORTH);
        user_control.add(user_info, BorderLayout.SOUTH);
        main.add(user_control, BorderLayout.NORTH);

        JPanel disk_control = new JPanel(new BorderLayout());
        disk_control.add(disk_panel, BorderLayout.NORTH);
        disk_control.add(disk_info, BorderLayout.SOUTH);
        disk_control.repaint();
        disk_control.revalidate();
        main.add(disk_control, BorderLayout.CENTER);


        main.add(main2, BorderLayout.SOUTH);
        main.repaint();
        main.revalidate();

        main2.setLayout(new BorderLayout());
        main2.add(printer_panel, BorderLayout.NORTH);
        main2.add(printer_info, BorderLayout.CENTER);
        main2.add(speed_control, BorderLayout.SOUTH);
        main2.repaint();
        main2.revalidate();
    }
    void run_per_second() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtextUpdate();
            }
        });

    }
    void jtextUpdate(){
        for(int i = 0; i < printer;i++){
            String printerFile = "PRINTER"+(i+1);
            try{
                FileReader fr = new FileReader(printerFile);
                printer_area[i].read(fr, printerFile);
                printer_area[i].update(printer_area[i].getGraphics());
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        printer_info.repaint();
        printer_info.revalidate();

        for (int i = 0; i < disk; i++) {
            if (disk_update[i] != Main.disk_manager.disk[i].tracking) {
                disk_update[i] = Main.disk_manager.disk[i].tracking;
                disk_area[i].setText("");
                disk_area[i].append(Main.disk_manager.disk[i].display());
                disk_area[i].update(disk_area[i].getGraphics());
            }
        }
        for(int i = 0; i < user;i++){
            if(Main.Users[i].requestNewDisk && (Main.Users[i].current_disk+1) != 0){
                Main.Users[i].requestNewDisk = false;
                user_area[i].append("writing to disk " + (Main.Users[i].current_disk+1));
                user_area[i].append("\n");
                user_area[i].update(user_area[i].getGraphics());
            }
            if(Main.Users[i].printing && Main.Users[i].currentPrint.cur_printer!= -1){
                Main.Users[i].printing = false;
                user_area[i].append("assigning to printer " + (Main.Users[i].currentPrint.cur_printer+1));
                user_area[i].append("\n");
            }
        }

    }


    public void disk_panel_setup(){
        ImageIcon[] user_images = new ImageIcon[disk];
        Image[] img = new Image[disk];
        FlowLayout flowLayout = new FlowLayout();
        disk_panel.setLayout(flowLayout);


        for(int i = 0 ; i < disk; i++){
            user_images[i] = new ImageIcon("resource/disk.jpeg");
            img[i] = user_images[i].getImage();
            Image newImg = img[i].getScaledInstance(icon_size,icon_size, Image.SCALE_SMOOTH);
            user_images[i] = new ImageIcon(newImg);
            JLabel label = new JLabel(user_images[i]);
            BorderLayout border = new BorderLayout();
            JPanel temp = new JPanel();
            temp.setLayout(border);
            temp.add(label, BorderLayout.NORTH);

            JTextArea tempArea = new JTextArea();
            tempArea.setEditable(false);
            tempArea.append(Main.disk_manager.disk[i].display());
            tempArea.setRows(Main.disk_manager.disk[i].tracking);
            tempArea.setColumns(10);
            disk_area[i] = tempArea;
            JScrollPane areaScrollPane = new JScrollPane(disk_area[i]);
            areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setPreferredSize(new Dimension(150, 150));
            temp.add(areaScrollPane, BorderLayout.SOUTH);
            disk_update[i] = Main.disk_manager.disk[i].tracking;
            disk_panel.add(temp);
        }
        disk_panel.repaint();
        disk_panel.revalidate();

    }

    public void user_panel_setup(){
        ImageIcon[] user_images = new ImageIcon[user];
        Image[] img = new Image[user];
        FlowLayout flowLayout = new FlowLayout();
        user_panel.setLayout(flowLayout);

        for(int i = 0 ; i < user; i++){
            user_images[i] = new ImageIcon("resource/user.png");
            img[i] = user_images[i].getImage();
            Image newImg = img[i].getScaledInstance(icon_size,icon_size, Image.SCALE_SMOOTH);
            user_images[i] = new ImageIcon(newImg);
            JLabel label = new JLabel(user_images[i]);

            JPanel temp = new JPanel();
            temp.setLayout(new BorderLayout());
            temp.add(label, BorderLayout.NORTH);

            JTextArea tempArea = new JTextArea();

            if(Main.Users[i].requestNewDisk && (Main.Users[i].current_disk+1) != 0){
                Main.Users[i].requestNewDisk = false;
                tempArea.append("writing to disk " + (Main.Users[i].current_disk+1));
                tempArea.append("\n");
            }
            if(Main.Users[i].printing && Main.Users[i].currentPrint.cur_printer!= -1){
                Main.Users[i].printing = false;
                tempArea.append("assigning to printer " + (Main.Users[i].currentPrint.cur_printer+1));
                tempArea.append("\n");
            }


            user_area[i] = tempArea;

            tempArea.setEditable(false);
            JScrollPane areaScrollPane = new JScrollPane(user_area[i]);
            areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setPreferredSize(new Dimension(180, 180));
            temp.add(areaScrollPane, BorderLayout.SOUTH);
            user_panel.add(temp);
        }
    }

    public void printer_panel_setup(){
        ImageIcon[] user_images = new ImageIcon[printer];
        Image[] img = new Image[printer];
        FlowLayout flowLayout = new FlowLayout();
        printer_panel.setLayout(flowLayout);
        for(int i = 0 ; i < printer; i++){
            try{
                user_images[i] = new ImageIcon("resource/printer.png");
                img[i] = user_images[i].getImage();
                Image newImg = img[i].getScaledInstance(icon_size,icon_size, Image.SCALE_SMOOTH);
                user_images[i] = new ImageIcon(newImg);
                JLabel label = new JLabel(user_images[i]);
                JPanel temp = new JPanel();
                BorderLayout border = new BorderLayout();
                temp.setLayout(border);
                temp.add(label, BorderLayout.NORTH);
                JTextArea tempArea = new JTextArea();
                String printerFile = "PRINTER"+(i+1);
                File file = new File(printerFile);
                BufferedReader Breader = new BufferedReader(new FileReader(file));
                String line;
                while((line = Breader.readLine())!= null){
                    tempArea.append(line);
                }

                tempArea.setEditable(false);

                printer_area[i]= tempArea;
                JScrollPane areaScrollPane = new JScrollPane(printer_area[i]);
                areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                areaScrollPane.setPreferredSize(new Dimension(150, 150));
                temp.add(areaScrollPane, BorderLayout.SOUTH);
                printer_panel.add(temp);
                Breader.close();
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public void speed_control_panel_setup(){
        FlowLayout flowLayout = new FlowLayout();
        speed_control.setLayout(flowLayout);
        int FPS_Min = 1, FPS_MAX = 4, FPS_INIT=2;
        speed_slider = new JSlider(JSlider.HORIZONTAL, FPS_Min, FPS_MAX, FPS_INIT);
        speed_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = speed_slider.getValue();
                Main.speed = value;
            }
        });
        speed_control.add(speed_slider);
    }
}
