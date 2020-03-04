public class Disk {

    static final int NUM_SECTORS = 1024;
    StringBuffer sectors[] = new StringBuffer[NUM_SECTORS];
    int tracking;
    public Disk(){
        for(int i = 0; i < sectors.length; i ++){
            sectors[i] = new StringBuffer();
        }
        tracking = 0;
    }

    public void write(int sector, StringBuffer data){
        try {
            if(Main.speed==1)
                Thread.sleep(400);
            else if(Main.speed ==2 || Main.speed == 0){
                Thread.sleep(200 );
            }else if(Main.speed ==3){
                Thread.sleep(100 );
            }else{
                Thread.sleep(66 );
            }
            sectors[sector].append(data);
            tracking++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int get_current_track(){
        return tracking;
    }

    public StringBuffer read(int sector){
        try {
            if(Main.speed==1)
                Thread.sleep(400);
            else if(Main.speed ==2 || Main.speed == 0){
                Thread.sleep(200 );
            }else if(Main.speed ==3){
                Thread.sleep(100 );
            }else{
                Thread.sleep(66 );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sectors[sector];
    }


    public String display(){
        String inputs = "";
        for(int i = 0; i < tracking;i++){
            inputs+=sectors[i] + "\n";
        }
        return inputs;
    }

    @Override
    public String toString() {
        String inputs = "";
        for(int i = 0; i < tracking;i++){
            inputs+=sectors[i] + "\n";
        }
        return inputs;
    }
}
