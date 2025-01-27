import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Asset {

    private final int id;
    private String name;
    private assetType type;
    private int count;

    public static enum assetType{
        HARDWARE, SOFTWARE
    }

    public Asset(int id, String name, assetType type, int count){
        this.id = id;
        this.name = name;
        this.type = type;
        this.count = count;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setType(assetType type){
        this.type = type;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public assetType getType(){
        return type;
    }
    public int getCount(){
        return count;
    }

    Set<User.userType> keys;
    public String toString(HashMap<User.userType, List<Integer>> assetForUsers){
        System.out.println();
        keys = assetForUsers.keySet();
        String s = "\t Available for : ";
        for(User.userType userType : keys){
            if(assetForUsers.get(userType).contains(id)){
                s = s+userType+", ";
            }
        }
        s=s.substring(0,s.length()-2);
        return "Asset Id : "+id+"\tAsset Name : "+name+"\t Asset Type : "+type+"\t Available Count : "+count+s+"\n";
    }
}
