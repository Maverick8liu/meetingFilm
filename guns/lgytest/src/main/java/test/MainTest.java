package test;




import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class MainTest {

        int count = 0;

        @Test
        public void tset1(){
            int i = (100)>>>1;
            System.out.println(i);
           /* System.out.println(i>>2);

            System.out.println(i<<2);

            System.out.println(i>>>2);*/

        }

       // @Test
       public void testSearch(){
                Random random = new Random();
                Integer[] ary = new Integer[1000];
                int key = 456;
                //ary[ary.length-1] = key;
                for(int i = 0;i<= ary.length - 1;i++){
                        ary[i] = random.nextInt(999);
                }


               // System.out.println(Arrays.toString(ary));
               long sort1 =  System.currentTimeMillis();
                Arrays.sort(ary);
                long sort2 =  System.currentTimeMillis();
               // System.out.println(Arrays.toString(ary));

                long start = System.currentTimeMillis();

                search(ary,0,ary.length-1,key);
                long end1 = System.currentTimeMillis();
                Arrays.binarySearch(ary,key);



                long end2 = System.currentTimeMillis();



            System.out.println("sortTime: "+(sort2-sort1)+" search time:"+(end1 - start)+" binarySearch"+(end2-end1));

        }

        private int search(Integer[] ary,int start,int end, int key){
            int middle = (end - start)/2+start;
            //System.out.println(count++);
            if(key == ary[middle]){
                return ary[middle];
            }else if(key > ary[middle]){
                return search(ary,middle+1,end,key);
            }else if(key < ary[middle]){
                return search(ary,start,middle-1,key);
            }else{
                return -1;
            }
        }

}
