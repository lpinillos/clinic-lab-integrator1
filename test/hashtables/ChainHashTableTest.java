package hashtables;

import Exceptions.NoValueFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChainHashTableTest {

    //Estado del test
    private static ChainHashTable<String, String> chainHashTable;

    //Escenario: Lista Vacia
    public void setupStage(){
        chainHashTable = new ChainHashTable<>();
    }

    //Escenario 2: Dos objetos anadidos
    public void setupStage2(){
        chainHashTable = new ChainHashTable<>();
        chainHashTable.insert("1004120560", "Santiago");
        chainHashTable.insert("1003120561", "Luis");

    }

    @Test
    public void addPatientTest(){
        //Este test tiene la finalidad de revisar si un objeto se inserta correctamente cuando
        //la Hashtable esta vacia
        setupStage();
        chainHashTable.insert("1004120560", "Santiago");
        assertEquals("Santiago", chainHashTable.search("1004120560"));
    }

    @Test
    public void addPatientTest2(){
        //Este test tiene la finalidad de revisar si un objeto se inserta correctamente cuando
        //la Hashtable esta ya tiene elementos en ella
        setupStage2();
        chainHashTable.insert("1002120560", "Daniel");
        assertEquals("Daniel", chainHashTable.search("1002120560"));
    }

    @Test
    public void addPatientTest3(){
        //Este test tiene la finalidad de revisar si al insertar un objeto con la misma llave que otro ya
        //previamente agregado se guarda correctamente
        setupStage2();
        chainHashTable.insert("1004120560", "Daniel");
        assertEquals("Daniel", chainHashTable.search("1004120560"));
    }

    @Test
    public void searchPatient(){
        //Este test tiene la finalidad de revisar que no se pueda encuentrar el objeto de la llave especificada
        //En una Hashtable vacia
        setupStage();
        boolean exceptionThrow = false;
        try {
            chainHashTable.search("1003120561");
        }catch (NoValueFoundException ex){
            ex.printStackTrace();
            exceptionThrow = true;
        }
        assertTrue(exceptionThrow);
    }

    @Test
    public void searchPatient2(){
        //Este test tiene la finalidad de revisar si se encuentra el objeto de la llave especificada
        //En una Hashtable con objetos ya agregados
        setupStage2();
        assertEquals("Luis", chainHashTable.search("1003120561"));
    }

    @Test
    public void searchPatient3(){
        //Este test tiene la finalidad de revisar si se encuentra un objeto que contenga la misma llave
        //Que un objeto ya agregado a la Hashtable
        setupStage2();
        chainHashTable.insert("1004120560", "Daniel");
        assertEquals("Daniel", chainHashTable.search("1004120560"));
    }

    @Test
    public void deletePatient(){
        //Este test tiene la finalidad de revisar que no se pueda eliminar un objeto con la llave especificada
        //En una Hashtable vacia
        setupStage();
        boolean exceptionThrow = false;
        try {
            chainHashTable.delete("1003120561");
        }catch (NoValueFoundException ex){
            ex.printStackTrace();
            exceptionThrow = true;
        }
        assertTrue(exceptionThrow);
    }

    @Test
    public void deletePatient2(){
        //Este test tiene la finalidad de revisar que no se pueda encuentrar un objeto con la llave especificada
        //Una vez este se ha eliminado de la Hashtable
        setupStage2();
        chainHashTable.delete("1004120560");
        boolean exceptionThrow = false;
        try {
            chainHashTable.search("1004120560");
        }catch (NoValueFoundException ex){
            ex.printStackTrace();
            exceptionThrow = true;
        }
        assertTrue(exceptionThrow);
    }

    @Test
    public void deletePatient3(){
        //Este test tiene la finalidad de revisar que no se pueda eliminar un objeto con la llave especificada
        //Si esta no se ha agregado a la Hashtable
        setupStage2();
        boolean exceptionThrow = false;
        try {
            chainHashTable.delete("1003110569");
        }catch (NoValueFoundException ex){
            ex.printStackTrace();
            exceptionThrow = true;
        }
        assertTrue(exceptionThrow);
    }

}