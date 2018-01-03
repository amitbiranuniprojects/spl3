package bgu.spl181.net.api.bidi;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.*;
import java.util.ArrayList;

public class JsonHandler {
    public static Gson gson = new Gson();
    public static String userPath = "Database\\Clients.json";
    public static String moviePath = "Database\\Movies.json";
    public static BufferedReader buff = null;
    public static ArrayList<LinkedTreeMap> jsonUser = null;
    public static ArrayList<LinkedTreeMap> jsonMovie = null;
    private static LinkedTreeMap<String, ArrayList> usersMap = null;//todo
    private static LinkedTreeMap<String, ArrayList> moviesMap = null;//todo


    public  static LinkedTreeMap getUser(String datum) {
        ReadFromJson();
        for (LinkedTreeMap L : jsonUser){
            if (((String)L.get("username")).compareTo(datum) == 0)
                return L;
        }
        return null;
    }

    public  static boolean addUser(String[] data){
        boolean ans = false;
        ReadFromJson();
        LinkedTreeMap user = new LinkedTreeMap();
        user.put("username",data[0]);
        user.put("type","normal");
        user.put("password",data[1]);
        if (data.length == 3)
            user.put("country",data[2]);
        else
            user.put("country","");
        user.put("movies", new ArrayList<LinkedTreeMap>());
        user.put("balance", "0");

        try {
            buff = new BufferedReader(new FileReader(userPath));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        LinkedTreeMap<String, ArrayList> M =usersMap;//todo
        M.get("users").add(user);//todo

        String newJson = gson.toJson(M);
        ans = true;
        try {
            WriteToJson(userPath, newJson);
        } catch (IOException e) {
            e.printStackTrace();
            ans = false;
        }

        return ans;
    }
    private synchronized static void WriteToJson( String path, String newJason) throws IOException {
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.append(newJason);
        fileWriter.flush();
    }

    private synchronized static void ReadFromJson(){
        try {
            buff = new BufferedReader(new FileReader(userPath));

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        usersMap = ((LinkedTreeMap<String, ArrayList>)gson.fromJson(buff, Object.class));//todo
        jsonUser = usersMap.get("users");//todo
        try {
            buff = new BufferedReader(new FileReader(moviePath));

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        moviesMap = ((LinkedTreeMap<String, ArrayList>)gson.fromJson(buff, Object.class));//todo
        jsonMovie = moviesMap.get("movies");//todo
    }

}
