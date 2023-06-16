package com.androidnangcao.demorss;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TinTucXML {
    String TAG = "zzzzzzzzz";
    String txtContent ; // dùng để ghi tạm nội dung của thẻ html đọc được
    List<TinTuc> tinTucList = new ArrayList<>();
    TinTuc objTinTuc;

    public List<TinTuc> getList(InputStream inputStream){
        try{
            XmlPullParserFactory factory =  XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput( inputStream , null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = parser.getName(); // lấy tên thẻ
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if(tagName.equalsIgnoreCase("item")){
                            objTinTuc = new TinTuc();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        txtContent = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(tagName.equalsIgnoreCase("title")){
                            objTinTuc.setTitle( txtContent );
                        }else if( tagName.equalsIgnoreCase("description")){
                            objTinTuc.setDescription( txtContent );
                        }else if( tagName.equalsIgnoreCase("pubDate")){
                            objTinTuc.setPubDate( txtContent );
                        }else if( tagName.equalsIgnoreCase("link")){
                            objTinTuc.setLink( txtContent );
                        }else if( tagName.equalsIgnoreCase("item")){
                            tinTucList.add(objTinTuc);
                        }
                        break;
                    default:
                        Log.d(TAG, "getList: Event khác " + eventType + ", tag: " + tagName);
                        break;
                }

                // chuyển tiếp while
                eventType = parser.next();
            }

            inputStream.close(); // đóng luồng đọc dữ liệu

        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tinTucList;
    }
}



