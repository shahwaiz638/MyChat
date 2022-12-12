package com.shahwaiz.chat_app;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemoryData {
    public static void saveMobile(String data, Context context)
    {

        try {
            FileOutputStream fileOutputStream=context.openFileOutput("Mobile1.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMobile(Context context)
    {
        String data="";
        try {
            FileInputStream fileInputStream=context.openFileInput("Mobile1.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line= bufferedReader.readLine())!=null)
            {
               stringBuilder.append(line);
            }
            data=stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveLastMsg(String data, Context context,String chatId)
    {

        try {
            FileOutputStream fileOutputStream=context.openFileOutput(chatId+".txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void saveName(String data, Context context)
    {

        try {
            FileOutputStream fileOutputStream=context.openFileOutput("Name.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getName(Context context)
    {
        String data="";
        try {
            FileInputStream fileInputStream=context.openFileInput("Name.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line= bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }
            data=stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void saveEmail(String data, Context context)
    {

        try {
            FileOutputStream fileOutputStream=context.openFileOutput("Email.txt",Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getEmail(Context context)
    {
        String data="";
        try {
            FileInputStream fileInputStream=context.openFileInput("Email.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line= bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }
            data=stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getLastMsg(String chatId,Context context)
    {
        String data="";
        try {
            FileInputStream fileInputStream=context.openFileInput(chatId+".txt");
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line= bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }
            data=stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
