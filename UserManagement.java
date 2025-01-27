import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class UserManagement {
    
    List<User> userList = new ArrayList<>();
    HashMap<Integer, List<Asset>> requirementMap = new HashMap<>();
    List<Integer> retainedList;
    HashMap<Integer, List<List<String>>> userSummary = new HashMap<>();
    List<String> summaryList = new ArrayList<>();
    
    //Display all User and the assets allocated to them
    public void userList(){
        System.out.println("***** USER LIST *****");
        System.out.println();
        for(User user: userList){
            System.out.print(user.toString());
            System.out.println();
        }
        System.out.println();
    }

    //Add New User
    public void addUser(int id, String name, User.userType type){
        System.out.println("***** ADD USER *****");
        System.out.println("Adding User.....");
        System.out.println();
        for(User user: userList){
            if(user.getId() == id){
                System.out.println("User Id Already Available !!!");
                System.out.println();
                return;
            }
        }
        if(type == User.userType.MANAGER || type == User.userType.EMPLOYEE || type == User.userType.TRAINEE){
            User user = new User(id, name, type);
            userList.add(user);
            System.out.println("User Added Successfully !!!");
            System.out.println();
            userList();
        }else{
            System.out.println("User Type is Invalid !!!");
            System.out.println();
        }
        userSummary.put(id, new ArrayList<List<String>>());
        //System.out.println("User Summary : "+userSummary);
    }

    //allocate an asset to an User
    public void allocateAsset(int userId, int id, AssetManagement assetManagement, String reason){
        System.out.println("***** ALLOCATE ASSET *****");
        System.out.println();
        User user = getUser(userId);
        Asset asset = assetManagement.getAsset(id);
        if(user != null & asset != null){
            if(assetManagement.assetForUsers.get(user.getUserType()).contains(asset.getId())){
                if(asset.getCount() > 0){
                    if(!user.getAllocatedAssets().contains(asset)){
                        user.getAllocatedAssets().add(asset);
                        asset.setCount(asset.getCount() - 1);
                        System.out.println("Asset Allocated Successfully !!!");
                        updateUserSummary(userId, "Asset Allocation", asset.getName(), reason);
                        System.out.println();
                    }else{
                        System.out.println("Asset Already Allocated !!!");
                        System.out.println();
                    }
                }else{
                    System.out.println("Asset Not Available");
                    System.out.println();
                }
            }else{
                System.out.println(asset.getName()+" is Not Available For "+user.getUserType());
            }
        }else{
            System.out.println("Asset or User Not Found !!!");
            System.out.println();
        }
    }

    //Find the asset needed to be allocated
    public void requirements(AssetManagement assetManagement){
        List<Asset> assetSet;
        List<Asset> aSet;
        for(User user : userList){
            assetSet = new ArrayList<Asset>(assetManagement.assetList);
            assetSet.removeAll(user.getAllocatedAssets());
            aSet = new ArrayList<>(assetSet);
            for(Asset asset : assetSet){
                if(!assetManagement.assetForUsers.get(user.getUserType()).contains(asset.getId())){
                    aSet.remove(asset);
                }
            }
            requirementMap.put(user.getId(), aSet);
        }
        displayRequirements(requirementMap);
    }

    //Display the required assets for each user
    public void displayRequirements(HashMap<Integer, List<Asset>> assetSet){
        System.out.println();
        System.out.println("***** REQUIREMENTS *****");
        System.out.println();
        assetSet.forEach((key , value) -> {
            String s = "User - Id : "+key+"\t Assets : ";
            for(Asset asset : value){
                s = s+asset.getName()+"(id = "+asset.getId()+")\t";
            }
            System.out.println(s);
            System.out.println();
        });
    }

    //Remove User
    public void removeUser(int userId, AssetManagement assetManagement, UserManagement userManagement, String reason){
        User user = getUser(userId);
        if(user != null){
            if(!user.getAllocatedAssets().isEmpty()){
                assetManagement.retainAllAssets(user, userManagement, reason);
                System.out.println("All Assets Retained Successfully !!!");
                System.out.println();
                assetManagement.displayAssets(assetManagement.retainedAssets, "RETAINED ASSETS LIST");
            }else{
                System.out.println("User has No Assets !!!");
                System.out.println();
            }
            userList.remove(user);
            System.out.println("User Removed Successfully !!!");
            updateUserSummary(userId, "Remove User     ", user.getUserType().toString(), reason);
            userManagement.userList();
        }else{
            System.out.println("User Not Available !!!");
            System.out.println();
        }
    }

    //Updating User Roles
    public void updateUserRole(int userId, User.userType type, AssetManagement assetManagement, UserManagement userManagement, String reason){
        System.out.println();
        System.out.println("***** UPDATE ASSET INFO & ROLES *****");
        System.out.println();
        User user = getUser(userId);
        if(user.getUserType() == type){
            System.out.println("Same User Type Entered, No Changes Made !!!");
            System.out.println();
            return;
        }else{
            user.setUserType(type);
            List<Asset> dallocatedAssets = new ArrayList<>(user.getAllocatedAssets());
            for(Asset asset : dallocatedAssets){
                if(!assetManagement.assetForUsers.get(user.getUserType()).contains(asset.getId())){
                    assetManagement.retainAsset(userId, asset.getId(), userManagement, reason);
                }
            }
            System.out.println();
            System.out.println(" User Updated Successfully !!!");
            updateUserSummary(userId, "Role Update     ", "To "+type.toString(), reason);
            System.out.println();
        }
    }
    //Display User Summary
    public void displayUserSummary(int userId){
        if(!userSummary.keySet().contains(userId)){
            System.out.println("Invalid User Id !!!");
            return;
        }
        System.out.println();
        System.out.println("************************");
        System.out.println("***** User Summary *****");
        System.out.println("************************");
        System.out.println();
        System.out.println("*************************************************************");
        System.out.println("Operation\t\t\t"+"Asset or Role\t\t"+"Date and Time\t\t"+"Reason");
        System.out.println("*************************************************************");
        List<List<String>> sList = userSummary.get(userId);
        for(int i=0;i<sList.size();i++){
            for(int j=0;j<sList.get(i).size();j++){
                System.out.print(sList.get(i).get(j)+"\t\t");
            }
            System.out.println();
            System.out.println();
        }
        System.out.println();
    }

    //Update User Summary
    public void updateUserSummary(int userId, String operation, String assetOrRole, String reason){
        summaryList.add(operation);
        summaryList.add(assetOrRole);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        summaryList.add(LocalDate.now() +" "+ LocalTime.now().format(formatter));
        summaryList.add(reason);
        userSummary.get(userId).add(new ArrayList<>(summaryList));
        summaryList.clear();
    }

    //Find a particular user
    public User getUser(int userId){
        for(User user : userList){
            if(user.getId() == userId){
                return user;
            }
        }
        return null;
    }
}
