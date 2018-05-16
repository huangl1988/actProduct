package cn.newtouch.config;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by steven on 2018/4/24.
 */
public class LocalProducer{

   private static Queue<Map<String,String>> mapQueue =new ConcurrentLinkedQueue<>();

   public static boolean send(Map<String,String> map){
       return mapQueue.add(map);
   }



}
