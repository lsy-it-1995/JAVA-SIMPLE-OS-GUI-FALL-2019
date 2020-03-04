import java.io.IOException;

public class ResourceManager {
    boolean isFree[];
    public ResourceManager(int number_of_item){
        isFree = new boolean[number_of_item];
        for(int i = 0; i < isFree.length; i++){
            isFree[i] = true;
        }
    }

    synchronized int request(){
        while(true){
            for(int i =0 ; i < isFree.length; i++){
                if(isFree[i]){
                    isFree[i] = false;
                    return i;
                }
                try{
                    this.wait();
                }catch(InterruptedException e){
                    System.out.println("I can't wait!");
                }
            }
        }
    }
    synchronized void release(int index){
        isFree[index] = true;
        this.notify();
    }
    public String availableArray(){
        String ava = "";
        for(int i = 0; i < isFree.length; i++){
            if(isFree[i]==true){
                ava += i + " ";
            }
        }
        return ava;
    }
}