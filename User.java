import java.util.ArrayList;
import java.util.List;

public class User {
    public static enum userType{
        MANAGER, EMPLOYEE, TRAINEE
    }
    private final int id;
    private final String name;
    private List<Asset> allocatedAssets;
    private userType type;

    public User(int id, String name, userType type){
        this.id = id;
        this.name = name;
        this.allocatedAssets = new ArrayList<>();
        this.type = type;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public List<Asset> getAllocatedAssets(){
        return allocatedAssets;
    }
    public userType getUserType(){
        return type;
    }

    public void setUserType(userType type){
        this.type = type;
    }
    


    @Override
    public String toString(){
        String s = "\nId : "+id+"\t Name : "+name+"\t User Type : "+type+"\t Assets Allocated : ";
        if(allocatedAssets.size() > 0){
            for(Asset asset : allocatedAssets){
                if(asset != null){
                    s = s+asset.getName()+"(id = "+asset.getId()+")\t";
                }
            }
        }else{
            s = s+"No Assets";
        }
        return s;
    }
}
