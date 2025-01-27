import java.util.Scanner;
import java.util.ArrayList;
class Options{
    private static int id, count, userId;
    private static String name, type, userType, reason;
    static boolean executeAdminOption(Scanner sc, AssetManagement assetManagement, UserManagement userManagement, int option){
        switch(option){
            case 1:
            //Display Assets
                System.out.println();
                assetManagement.displayAssets(assetManagement.assetList, "ASSET LIST");
                System.out.println();
                viewAdminOptions();
                //executeOption(sc, assetManagement, userManagement);
                return true;
            case 2:
            //Add new asset
                System.out.println();
                System.out.println("Enter the Asset Details :");
                System.out.print("Enter Id of the Asset : ");
                id = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Name of the Asset : ");
                name = sc.nextLine();
                System.out.print("Enter Type of the Asset (HardWare / SoftWare) : ");
                type = sc.nextLine();
                System.out.print("Enter Count : ");
                type = type.toUpperCase();
                count = sc.nextInt();
                System.out.println();
                try{
                assetManagement.addAsset(id, name, Asset.assetType.valueOf(type), count,sc);
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid Asset Type !!!");
                }
                System.out.println();
                viewAdminOptions();
                //executeOption(sc,assetManagement, userManagement);
                return true;
            case 3:
            //Allocate assets to user
                userManagement.userList();
                assetManagement.displayAssets(assetManagement.assetList, "ASSET LIST");
                System.out.print("Enter User Id : ");
                userId = sc.nextInt();
                System.out.print("Enter Asset Id : ");
                id = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Reason : ");
                reason = sc.nextLine();
                userManagement.allocateAsset(userId, id, assetManagement, reason);
                viewAdminOptions();
                //executeOption(sc,assetManagement, userManagement);
                return true;
            case 4:
            //Update asset information
                System.out.println();
                assetManagement.displayAssets(assetManagement.assetList, "ASSET LIST");
                System.out.print("Enter Id of the Asset : ");
                id = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Name of the Asset : ");
                name = sc.nextLine();
                System.out.print("Enter Type of the Asset (HardWare / SoftWare) : ");
                type = sc.nextLine();
                System.out.print("Enter Count : ");
                count = sc.nextInt();
                assetManagement.updateAssetInformation(id, name, type, count, sc);
                System.out.println();
                viewAdminOptions();
                //executeOption(sc,assetManagement, userManagement);
                return true;
            case 5:
            //Remove an asset
                System.out.println();
                System.out.print("Enter id of the Asset to Remove : ");
                id = sc.nextInt();
                assetManagement.removeAsset(id);
                System.out.println();
                viewAdminOptions();
                //executeOption(sc,assetManagement, userManagement);
                return true;
            case 6:
            //Display the Users
                System.out.println();
                userManagement.userList();
                System.out.println();
                viewAdminOptions();
                //executeOption(sc, assetManagement, userManagement);
                return true;
            case 7:
            //Display the Requirements
                userManagement.requirements(assetManagement);
                System.out.println();
                viewAdminOptions();
                //executeOption(sc, assetManagement, userManagement);
                return true;
            case 8:
            //Add New User
                System.out.println("Enter User Details : ");
                System.out.print("Enter User Id : ");
                userId = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter User Name : ");
                name = sc.nextLine();
                System.out.print("Enter User type(Manager / Employee / Trainee) : ");
                userType = sc.nextLine();
                userType = userType.toUpperCase();
                try{
                userManagement.addUser(userId, name, User.userType.valueOf(userType));
                }catch(IllegalArgumentException e){
                    System.out.println();
                    System.out.println("Invalid User Type !!!");
                }
                System.out.println();
                viewAdminOptions();
                //executeOption(sc,assetManagement, userManagement);
                return true;
            case 9:
            //Retain Assets
                System.out.println();
                userManagement.userList();
                System.out.print("Enter User Id : ");
                userId = sc.nextInt();
                System.out.print("Enter Asset Id : ");
                id = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Valid Reason : ");
                reason = sc.nextLine();
                assetManagement.retainAsset(userId, id, userManagement, reason);
                viewAdminOptions();
                return true;
            case 10:
            //Display Assets
                System.out.println();
                assetManagement.displayAssets(assetManagement.retainedAssets, "RETAINED ASSET LIST");
                System.out.println();
                viewAdminOptions();
                //executeOption(sc, assetManagement, userManagement);
                return true;
            case 11:
            //Remove User
                System.out.println();
                userManagement.userList();
                System.out.print("Enter User Id : ");
                userId = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Reason : ");
                reason = sc.nextLine();
                userManagement.removeUser(userId, assetManagement, userManagement, reason);
                viewAdminOptions();
                return true;
            case 12:
            //Update Asset Availability
                System.out.println();
                System.out.print("Enter Manager OR Employee OR Trainee : ");
                userType = sc.nextLine();
                userType = userType.toUpperCase();
                try{
                    assetManagement.updateAssetAvailability(sc, User.userType.valueOf(userType), userManagement);
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid User Type !!!");
                }
                viewAdminOptions();
                return true;
            case 13:
            //Update User and Roles
                System.out.println();
                System.out.println("***** Update User Info & Roles *****");
                System.out.print("Enter User Id : ");
                userId = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter User Type : ");
                userType = sc.nextLine();
                userType = userType.toUpperCase();
                System.out.print("Enter Reason : ");
                reason = sc.nextLine();
                try{
                    userManagement.updateUserRole(userId, User.userType.valueOf(userType), assetManagement, userManagement, reason);
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid Option !!!");
                }
                viewAdminOptions();
                return true;
            case 14:
                System.out.print("Enter User Id : ");
                userId = sc.nextInt();
                sc.nextLine();
                userManagement.displayUserSummary(userId);
                viewAdminOptions();
                return true;
            case 15:
            //Exit
                System.out.println("Thank You !!!");
                return false;
            default:
                System.out.println("Invalid Option !!!");
                return true;
        }

    }

    static boolean executeMainOption(Scanner sc, AssetManagement assetManagement, UserManagement userManagement,int option) {
        switch(option) {
            case 1:
                Options.viewAdminOptions();
                do{
                    System.out.print("Enter Your Option : ");
                    option = sc.nextInt();
                    sc.nextLine();
                }
                while(Options.executeAdminOption(sc, assetManagement, userManagement, option));
                viewMainOptions();
                return true;
            case 2:
                Options.viewUserOptions();
                do{
                    System.out.print("Enter Your Option : ");
                    option = sc.nextInt();
                    sc.nextLine();
                }
                while(Options.executeUserOption(sc, assetManagement, userManagement, option));
                Options.viewMainOptions();
                return true;
            case 3:
                System.out.println("Thank You !!!");
                return false;
            default:
                System.out.println("Invalid Option !!!");
                return true;
        }
    }

    static boolean executeUserOption(Scanner sc, AssetManagement assetManagement, UserManagement userManagement,int option) {
        switch(option) {
            case 1:
                System.out.println("***** View Summary *****");
                System.out.print("Enter User Id : ");
                userId = sc.nextInt();
                userManagement.displayUserSummary(userId);
                Options.viewUserOptions();
                return true;
            case 2:
                System.out.println("***** Raise a Request *****");
                Options.viewUserOptions();
                return true;
            case 3:
                System.out.println("Thank You !!!");
                return false;
            default:
                System.out.println("Invalid Option !!!");
                return true;
        }
    }

    static void viewAdminOptions(){
        System.out.println("*************************");
        System.out.println("***** WELCOME ADMIN *****");
        System.out.println("*************************");
        System.out.println("1.View Assets.");
        System.out.println("2.Add New Asset.");
        System.out.println("3.Allocate Asset.");
        System.out.println("4.Update Asset Inventory.");
        System.out.println("5.Remove an Asset.");
        System.out.println("6.User List.");
        System.out.println("7.See Requirements.");
        System.out.println("8.Add New User.");
        System.out.println("9.Retain Assets.");
        System.out.println("10.Display Retained Assets List.");
        System.out.println("11.Remove User.");
        System.out.println("12.Update Asset Availability.");
        System.out.println("13.Update User info and Roles.");
        System.out.println("14 Display User Summary.");
        System.out.println("15.Logout.");
        System.out.println();
    }

    static void viewUserOptions() {
        System.out.println("************************");
        System.out.println("***** WELCOME USER *****");
        System.out.println("************************");
        System.out.println("1.VIEW SUMMARY.");
        System.out.println("2.RAISE A REQUEST.");
        System.out.println("3.LOGOUT.");
    }

    static void viewMainOptions() {
        System.out.println("***********************************");
        System.out.println("***** ASSET MANAGEMENT SYSTEM *****");
        System.out.println("***********************************");
        System.out.println("1.Admin.");
        System.out.println("2.User.");
        System.out.println("3.Exit.");
    }
    //Sample Data Insertion
    static void addUsers(UserManagement userManagement){
        userManagement.addUser(1, "User 1", User.userType.MANAGER);
        userManagement.addUser(2, "User 2", User.userType.EMPLOYEE);
        userManagement.addUser(3, "User 3", User.userType.TRAINEE);
    }

    static void addAssets(AssetManagement assetManagement, Scanner sc){
        assetManagement.addAsset(1, "Phone", Asset.assetType.HARDWARE, 1, sc);
        assetManagement.addAsset(2, "Laptop", Asset.assetType.HARDWARE, 12, sc);
        assetManagement.addAsset(3, "Charger", Asset.assetType.HARDWARE, 14, sc);
    }

    static void assetForUsers(AssetManagement assetManagement){
        assetManagement.assetForUsers.put(User.userType.MANAGER, new ArrayList<>());
        assetManagement.assetForUsers.put(User.userType.EMPLOYEE, new ArrayList<>());
        assetManagement.assetForUsers.put(User.userType.TRAINEE, new ArrayList<>());
        //System.out.println(assetManagement.assetForUsers);
    }
}
public class AssetManagementSystem{
    private static int option;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AssetManagement assetManagement = new AssetManagement();
        UserManagement userManagement = new UserManagement();
        Options.assetForUsers(assetManagement);
        Options.addUsers(userManagement);
        Options.addAssets(assetManagement, sc);
        Options.viewMainOptions();
        do{
        System.out.print("Enter Your Option : ");
        option = sc.nextInt();
        sc.nextLine();
        }
        while(Options.executeMainOption(sc, assetManagement, userManagement, option));
    }
}