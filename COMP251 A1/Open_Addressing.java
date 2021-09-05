// no collaborator

import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }

     }

                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {
         Random generator = new Random();
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
      public int probe(int key, int i) {
          //implement formula
        return ((((this.A*key)%power2(this.w))>>(this.w-this.r))+i)%(power2(this.r));
     }


     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
            int collision;  // track the number of collisions
            for (collision = 0; collision<this.m; collision++){ // when the collision number is smaller than the number of slots
                int atmp_loc = probe(key, collision); // calculate possible slot
                if(this.Table[atmp_loc] == -1 || this.Table[atmp_loc] == -2){ // if slot is empty
                  this.Table[atmp_loc] = key; // insert the key
                  break;
                }
            }
            return collision;
        }

        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }

         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
            int collision; // keep track of collision number
            for (collision = 0; collision<this.m; collision++){ // when collision number smaller than slot number
              int search_loc = probe(key, collision); // get possible slot number
              if(this.Table[search_loc]==key){  // if key is found
                this.Table[search_loc] = -2;  // change the key to -2, to indicate that the slot is empty and used
                break;
              }else if(this.Table[search_loc]==-1){ // if -1 is detected, there should be no more valid keys after this point
                collision++;  // increase the collision by 1, to get the number of slot visited before giving up searching
                break;
              }
            }
            return collision;
        }
}
