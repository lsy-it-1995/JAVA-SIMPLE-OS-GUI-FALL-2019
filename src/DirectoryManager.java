import java.util.Hashtable;

public class DirectoryManager {
    Hashtable<String, FileInfo> T = new Hashtable<String, FileInfo>();

    public DirectoryManager(){}

    void enter(String key, FileInfo file){
        T.put(key,file);
    }

    FileInfo lookup(String key){
        return T.get(key);
    }

    @Override
    public String toString() {
        String output= "";
        for(String str: T.keySet()){
            output+= str+"    " +   T.get(str)+ "\n";
        }
        return output;
    }
}
