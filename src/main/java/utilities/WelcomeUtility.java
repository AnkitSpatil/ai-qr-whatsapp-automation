package utilities;

public class WelcomeUtility {

   //access modifier keywords retrunType methodName(parameters){}
    public static void welcomeUser(){
        System.out.println("Hello, Welcome to QR Code Generator!");
    }
    public static void welcomeUser(String name){
        System.out.println("Hello " + name + ", Welcome to QR Code Generator!");
    }

    public static String printMesssage(String...message){
        return "Message: " + message;
    }
}
