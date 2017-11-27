package com.example.user.verynicejsonapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    TextView textField;

    String jString = "{" +
            "       \"colors\":" + "[" +
            "{" +
            " \"color\" :   \"black\"," +
            " \"category\" : \"hue\"," +
            " \"type\" : \"primary\"," +
            " \"code\" : " + "{" +
            " \"rgba\" : [255,255,255,1]," +
            " \"hex\" : \"#000\"" +
            "}" +
            "}," +
            "{" +
            " \"color\" : \"white´\"," +
            " \"category\" : \"value\"," +
            " \"code\" : " + "{" +
            " \"rgba\" : [0,0,0,1]," +
            " \"hex\" : \"#FFF\"" +
            "}" +
            "}," +
            "{" +
            " \"color\" :   \"red\"," +
            " \"category\" : \"hue\"," +
            " \"type\" : \"primary\"," +
            " \"code\" : " + "{" +
            " \"rgba\" : [255,0,0,1]," +
            " \"hex\" : \"#FF0\"" +
            "}" +
            "}," +
            "{" +
            " \"color\" :   \"blue\"," +
            " \"category\" : \"hue\"," +
            " \"type\" : \"primary\"," +
            " \"code\" : " + "{" +
            " \"rgba\" : [0,0,255,1]," +
            " \"hex\" : \"#00F\"" +
            "}" +
            "}," +
            "{" +
            " \"color\" :   \"yellow\"," +
            " \"category\" : \"hue\"," +
            " \"type\" : \"primary\"," +
            " \"code\" : " + "{" +
            " \"rgba\" : [255,255,0,1]," +
            " \"hex\" : \"#FF0\"" +
            "}" +
            "}," +
            "{" +
            " \"color\" :   \"green\"," +
            " \"category\" : \"hue\"," +
            " \"type\" : \"secondary\"," +
            " \"code\" : " + "{" +
            " \"rgba\" : [0,255,0,1]," +
            " \"hex\" : \"#0F0\"" +
            "}" +
            "}" +
            "]" +
            "}";
    String jsonString;
    JSONObject colorsObject;
    JSONArray colorsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = (TextView) findViewById(R.id.textShow);


        try {
            FileOutputStream outputStream = openFileOutput("jString.json", Context.MODE_PRIVATE);
            outputStream.write(jString.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void load(){
        try {
            FileInputStream inputStream = openFileInput("jString.json");
            String content = readFullyAsString(inputStream, "UTF-8");
            colorsObject = (JSONObject) new JSONTokener(content).nextValue();
            colorsArray = colorsObject.getJSONArray("colors");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String readFullyAsString(InputStream inputStream, String encoding)
            throws IOException {
        return readFully(inputStream).toString(encoding);
    }

    public byte[] readFullyAsBytes(InputStream inputStream)
            throws IOException {
        return readFully(inputStream).toByteArray();
    }

    private ByteArrayOutputStream readFully(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }






    public void Count(View view) throws JSONException {
        int counter = 0;
        load();

        for(int i =0; i<colorsArray.length(); i++) {
            JSONObject color = colorsArray.getJSONObject(i);
            JSONObject code = color.getJSONObject("code");
            String rgba = code.getString("rgba");
            rgba = rgba.substring(1, rgba.length() - 1);
            String[] colorNumbers = rgba.split(",");
            String green = colorNumbers[1];
            if(green.equals("255"))
                counter++;
        }

        textField.setText(counter + "");




    }

    public void List(View view) throws JSONException {
        String imABeautifulStringIfYouReadMe = "The colors you are looking for are: ";
        load();
        for(int i =0; i<colorsArray.length(); i++) {
            JSONObject color = colorsArray.getJSONObject(i);
            JSONObject code = color.getJSONObject("code");
            String rgba = code.getString("rgba");
            rgba = rgba.substring(1, rgba.length() - 1);
            String[] colorNumbers = rgba.split(",");
            String green = colorNumbers[1];
            if(green.equals("255"))
                imABeautifulStringIfYouReadMe = imABeautifulStringIfYouReadMe + color.getString("color") + ", ";
        }
        imABeautifulStringIfYouReadMe = imABeautifulStringIfYouReadMe.substring(0,imABeautifulStringIfYouReadMe.length()-2) + ".";
        textField.setText(imABeautifulStringIfYouReadMe);

    }




    public void modifyProf(View view){
        load();
        try {

            JSONObject orange = new JSONObject();
            orange.put("color", "orange");
            orange.put("category", "hue");

            JSONObject code = new JSONObject();
            code.put("rgba", "[255,165,1]");
            code.put("hex", "#FA0");

            orange.put("code", code);

            colorsArray.put(orange);






            textField.setText(colorsArray.getJSONObject(colorsArray.length()-1).toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }













}