import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;
public class AssetManagement {
    List<Asset> assetList = new ArrayList<>();
    List<Asset> retainedAssets = new ArrayList<>();
    List<Integer> retainedList;
    HashMap<User.userType, List<Integer>> assetForUsers = new HashMap<>();

    //Adding new asset
    public void addAsset(int id, String name, Asset.assetType type, int count, Scanner sc){
        System.out.println("***** ADD NEW ASSET *****");
        if(type == Asset.assetType.HARDWARE || type == Asset.assetType.SOFTWARE){
            System.out.println("Adding Asset..........");
            for(Asset asset : assetList){
                if(asset.getId() == id){
                    System.out.println("Asset Already Available !!!\tUse Update Option to update Asset Information.");
                    System.out.println();
                    return;
                }
            }
            int ch;
            for(User.userType userType : User.userType.values()){
                System.out.print("Is "+name+" For "+userType+" (1 Yes / 0 No) : ");
                ch = sc.nextInt();
                switch(ch){
                    case 0 -> {}
                    case 1 -> {
                        retainedList = assetForUsers.get(userType);
                        if(retainedList != null & !retainedList.contains(id)){
                            retainedList.add(id);
                        }
                        assetForUsers.put(userType, new ArrayList<>(retainedList));
                    }
                    default -> {
                        System.out.println("Invalid Option !!!");
                        return;
                    }
                }
            }    
            Asset asset = new Asset(id, name, type, count);
            assetList.add(asset);
            Asset retainedAsset = new Asset(id, name, type, 0);
            retainedAssets.add(retainedAsset);
            System.out.println("Asset Added Successfully !!!");
            System.out.println();
            displayAssets(assetList, "ASSET LIST");
        }else{
            System.out.println();
            System.out.println("Invalid Asset Type !!!");
        }
        //System.out.println("Asset For Users : "+assetForUsers);
    }

    public void addAssetForUsers(User.userType type, int id){
        List<Integer> newList = assetForUsers.get(type);
        if(!newList.contains(id)){
            newList.add(id);
            assetForUsers.put(type, newList);
        }
    }

    //Display all Assets
    public void displayAssets(List<Asset> assetList, String stock){
        System.out.println("***** "+stock+" *****");
        for(Asset asset: assetList){
            System.out.print(asset.toString(assetForUsers));
        }
        System.out.println();
    }

    //Remove an asset from Stock
    public void removeAsset(int id){
        System.out.println("***** REMOVE ASSET *****");
        Asset asset = getAsset(id);
        if(asset != null){
            assetList.remove(asset);
            retainedAssets.remove(asset);
            for(User.userType userType : User.userType.values()){
                assetForUsers.get(userType).remove(Integer.valueOf(id));
            }
            System.out.println("Asset with Id = "+id+" Removed Successfully !!!");
            System.out.println();
        }else{
            System.out.println("Asset not Found !!!");
            System.out.println();
        }
        
    }

    //Update Asset information (Name change, increase / decrease count)
    public void updateAssetInformation(int id, String name, String type, int count, Scanner sc){
        System.out.println("***** UPDATE ASSET INFORMATION *****");
        Asset asset = getAsset(id);
        type = type.toUpperCase();
        if(type.equals(Asset.assetType.HARDWARE.toString()) || type.equals(Asset.assetType.SOFTWARE.toString())){
            if(asset != null){
                asset.setName(name);
                asset.setType(Asset.assetType.valueOf(type));
                asset.setCount(count);
                System.out.println("Asset Information Updated Successfully !!!");
                System.out.println();
            }else{
                System.out.println("Asset not found !!!");
                System.out.println();
            }
        }else{
            System.out.println();
            System.out.println("Invalid Asset Type !!!");
            System.out.println();
        }
    }

    //Retain an asset from the User
    public void retainAsset(int userId, int id, UserManagement userManagement, String reason){
        System.out.println();
        System.out.println("***** RETAIN ASSET *****");
        System.out.println();
        User user = userManagement.getUser(userId);
        Asset asset = null;
        for(Asset allocatedAsset : user.getAllocatedAssets()){
            if(allocatedAsset.getId() == id){
                asset = allocatedAsset;
                break;
            }
        }
        if(asset != null){
            user.getAllocatedAssets().remove(asset);
            updateRetainedAssets(asset);
            System.out.println("Asset Retained Successfully !!!");
            userManagement.updateUserSummary(userId, "Retain Asset    ", asset.getName(), reason);
            userManagement.userList();
        }else{
            System.out.println("Asset or User Not Found !!!");
            System.out.println();
        }
        displayAssets(retainedAssets, "RETAINED ASSET LIST");
    }

    public void retainAllAssets(User user, UserManagement userManagement, String reason){
        System.out.println();
        System.out.println("***** RETAIN ALL ASSETS *****");
        System.out.println();
        for(Asset asset : user.getAllocatedAssets()){
            updateRetainedAssets(asset);
            userManagement.updateUserSummary(user.getId(), "Retain Asset", asset.getName(),reason);
        }
        user.getAllocatedAssets().clear();
    }

    //Adding the Asset to retained Asset List
    public void updateRetainedAssets(Asset asset){
        //if(!retainedAssets.isEmpty()){
            for(Asset retainedAsset : retainedAssets){
                if(retainedAsset.getId() == asset.getId()){
                    retainedAsset.setCount(retainedAsset.getCount() + 1);
                    System.out.println();
                    return;
                }
            }
            // asset.setCount(1);
            // retainedAssets.add(asset);
            // System.out.println("Asset Retained Successfully !!!");
            // System.out.println();
            // displayAssets(retainedAssets, "RETAINED ASSET LIST");
        // }else{
        //     asset.setCount(1);
        //     retainedAssets.add(asset);
        //     System.out.println("Asset Retained Successfully !!!");
        //     System.out.println();
        //     displayAssets(retainedAssets, "RETAINED ASSET LIST");
        // }
    }

    public void updateAssetAvailability(Scanner sc, User.userType type, UserManagement userManagement){
        displayAssets(assetList, "ASSET LIST");
        
        for(Asset asset : assetList){
            int ch;
            System.out.print("Is "+asset.getName()+" For "+type+"(1 Yes / 0 No) : ");
            ch = sc.nextInt();
            switch(ch){
                case 0 -> {
                    assetForUsers.get(type).remove(Integer.valueOf(asset.getId()));
                }
                case 1 -> {
                    assetForUsers.get(type).add(asset.getId());
                }
                default -> {
                    System.out.println("Invalid Option !!!");
                    return;
                }
            }
        }
        System.out.println("Availability of Assets Updated Successfully !!!");
        List<Asset> allocatedAsset;
        for(User user : userManagement.userList){
            allocatedAsset = new ArrayList<>(user.getAllocatedAssets());
            if(user.getUserType() == type){
                for(Asset asset : allocatedAsset){
                    if(!assetForUsers.get(type).contains(asset.getId())){
                        retainAsset(user.getId(), asset.getId(), userManagement, "Updating Availability");
                    }
                }
            }
        }

    }

    //Find an asset
    public Asset getAsset(int id){
        for(Asset asset : assetList){
            if(asset.getId() == id){
                return asset;
            }
        }
        return null;
    }

}
