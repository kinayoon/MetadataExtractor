package kr.kina.test;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/** Junit을 대신해서 Test쓰레드로 사용하였음
 *  Created by Yoon on 2016-11-08.
 */
public class DefaultTest {

    public static void main(String[] args) {


        //재생시간 표시할 때
        String duration = "227985.046875";

        DateFormat format = new SimpleDateFormat("mm:ss");
  //      String a= format.format(duration);
 //      System.out.println(a);

        double num = Double.parseDouble(duration) + 0.0;
        System.out.println(num);
       /* System.out.println((int)((Math.random()*90000000) + 10000000));
        System.out.println((int)((Math.random()*90000000) + 10000000));
        System.out.println((int)((Math.random()*90000000) + 10000000));
        System.out.println((int)((Math.random()*90000000) + 10000000));
        System.out.println((int)((Math.random()*90000000) + 10000000));
        System.out.println((int)((Math.random()*90000000) + 10000000));
        System.out.println((int)((Math.random()*90000000) + 10000000));
        System.out.println((int)((Math.random()*90000000) + 10000000));*/

    }
}
