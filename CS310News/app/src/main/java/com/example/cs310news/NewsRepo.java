package com.example.cs310news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepo {

    public void getNewsByCategoryId(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(() -> {
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line=reader.readLine()) != null) {
                    buffer.append(line);
                }

                List<NewsObj> newsObjList = new ArrayList<>();
                JSONObject object = new JSONObject(buffer.toString());
                JSONArray array = object.getJSONArray("items");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject current = array.getJSONObject(i);

                    // yyyy-MM-dd'T'hh:mm:ss.sssZ
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sssZ");
                    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                    String date = current.getString("date");

                    try {
                        Date myDate = format1.parse(date);
                        date = format2.format(myDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    NewsObj newsObj = new NewsObj(
                            current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            date,
                            current.getString("image"),
                            current.getString("categoryName"));

                    newsObjList.add(newsObj);
                }

                //Log.i("DEV", newsObjList.toString());

                Message msg = new Message();
                msg.obj = newsObjList;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    public void downloadImage(ExecutorService srv, Handler uiHandler, String path) {
        srv.execute(()->{
            try {

                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public void getNewsById(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(() -> {
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line=reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject object = new JSONObject(buffer.toString());
                JSONArray array = object.getJSONArray("items");

                JSONObject current = array.getJSONObject(0);

                // yyyy-MM-dd'T'hh:mm:ss.sssZ
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sssZ");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                String date = current.getString("date");

                try {
                    Date myDate = format1.parse(date);
                    date = format2.format(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                NewsObj newsObj = new NewsObj(
                        current.getInt("id"),
                        current.getString("title"),
                        current.getString("text"),
                        date,
                        current.getString("image"),
                        current.getString("categoryName"));

                //Log.i("DEV", newsObj.toString());

                Message msg = new Message();
                msg.obj = newsObj;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    public void getCommentsByNewsId(ExecutorService srv, Handler uiHandler, int id) {

        srv.execute(() -> {
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(id));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line=reader.readLine()) != null) {
                    buffer.append(line);
                }

                List<CommentObj> commentObjList = new ArrayList<>();
                JSONObject object = new JSONObject(buffer.toString());
                JSONArray array = object.getJSONArray("items");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject current = array.getJSONObject(i);
                    CommentObj commentObj = new CommentObj(
                            current.getInt("id"),
                            current.getInt("news_id"),
                            current.getString("text"),
                            current.getString("name"));

                    commentObjList.add(commentObj);
                }

                //Log.i("DEV", commentObjList.toString());

                Message msg = new Message();
                msg.obj = commentObjList;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    public void postComment(ExecutorService srv, Handler uiHandler, int newsId, String usrname, String usrcomment) {

        srv.execute(() -> {
            try {

                URL url =  new URL("http://10.3.0.14:8080/newsapp/savecomment");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", usrname);
                jsonObject.put("text", usrcomment);
                jsonObject.put("news_id", newsId);

                //Log.i("DEV", jsonObject.toString());

                OutputStream outputStream = new BufferedOutputStream(conn.getOutputStream());
                outputStream.write(jsonObject.toString().getBytes());
                outputStream.flush();
                outputStream.close();

                //Log.i("DEV", String.valueOf(conn.getResponseCode()));

                Message msg = new Message();
                msg.obj = conn.getResponseCode();
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

}
