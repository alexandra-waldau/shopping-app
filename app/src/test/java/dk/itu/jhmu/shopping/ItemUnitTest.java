package dk.itu.jhmu.shopping;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

//Bunch of very basic tests for a simple class. Perhaps not necessary to write these tests but
//handy if I ever change anything.
public class ItemUnitTest {

        Item item;

        @BeforeClass
        public void setup() {

            String what = "Lemon";
            String where = "REMA 1000";
            item = new Item(what,where);
        }

        //Testing getters. Not strictly necessary but good practice.
        @Test
        public void shouldReturnAName() {
            assertEquals(item.getWhat(),"Lemon");
        }

        @Test
        public void shouldReturnALocation() {
            assertEquals(item.getWhere(),"REMA 1000");
        }

        //Testing setters. Not strictly necessary but good practice.
        @Test
        public void shouldReturnNewName() {
            item.setWhat("Orange");
            assertEquals(item.getWhat(),"Orange");
        }

        @Test
        public void shouldReturnNewLocation() {
            item.setWhere("Fotex");
            assertEquals(item.getWhere(),"Fotex");
        }

        //Testing toString() Method.
        @Test
        public void shouldReturnANiceString(){
            assertEquals(item.toString(),"Orange in: Fotex");
        }
    }