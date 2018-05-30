/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

/**
 *
 * @author domin
 */
public class SessionService {
    //pola klasy - muszą być
    private static SessionService instance;
    private SessionService() {};
   
    //tutaj możesz dać pola usera, np. username, email, ID
    private static String sessionUsername;
    private static int sessionUserID;
    private static int sessionUserRights;
    private static String sessionUserSurname;
    //ta metoda pozwala się odwołać do sessionservice
    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }
   
   
    //metody sessionservice, nie dawać this. do pól
    public static void setUsername(String username) {
        sessionUsername=username;
    }
    public static String getUsername() {
        return sessionUsername;
    }
    public static void setUserSurname(String userSurname) {
        sessionUsername=userSurname;
    }
    public static String getUserSurname() {
        return sessionUserSurname;
    }
    
        public static void setUserID(int userID) {
        sessionUserID=userID;
    }
    public static int getUserID() {
        return sessionUserID;
    }
    
        public static void setUserRights(int userRights) {
        sessionUserRights=userRights;
    }
    public static int getUserRights() {
        return sessionUserRights;
    }
    
    
    
   
    //niszczenie sesji
    public static void resetSession() {
        instance=null;
        sessionUsername=null;
        sessionUserID=0;
        sessionUserRights=0;
        sessionUserSurname=null;
    }
}
 
 

