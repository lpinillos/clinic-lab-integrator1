package heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {

    private static Heap<String> heap;

    //Escenario: Lista vacía
    public void setupStage1() {
        heap = new Heap<>();

    }
    public void setupStage2() {
        heap = new Heap<>();
        heap.HeapInsert("PTest1", 1);
        heap.HeapInsert("PTest2", 4);
        heap.HeapInsert("PTest3", 6);
    }

    @Test
    public void insertionOfANewNode1() {
        setupStage1();
        //Este test tiene la finalidad de verificar que se ingresan los
        //pacientes a la priority queue
        heap.HeapInsert("Persona", 2);
        heap.HeapInsert("Paciente", 12);
        assertEquals("Paciente Persona ", heap.print());
    }

    @Test
    public void insertionOfANewNode2() {
        setupStage1();
        //Este test tiene la finalidad de verificar que, a pesar de que se
        //tiene una misma llave, los pacientes se siguen ingresando
        heap.HeapInsert("Persona", 12);
        heap.HeapInsert("Paciente", 12);
        assertEquals("Persona Paciente ", heap.print());
    }

    @Test
    public void insertionOfANewNode3() {
        setupStage2();
        //Este test tiene la finalidad de verificar que, los paciente se siguen ingresando
        //a pesar de que ya hay algunos en la lista y, se organiza correctamente, colocando
        //los pacientes de la mayor llave a la menor
        heap.HeapInsert("Persona", 0);
        assertEquals("PTest3 PTest2 PTest1 Persona ", heap.print());
    }

    @Test
    public void extractionOfMaxValue1() {
        //Este test tiene la finalidad de verificar que se extrae y elimina
        //correctamente el paciente con la mayor llave
        setupStage1();
        heap.HeapInsert("Persona", 12);
        heap.HeapInsert("Paciente", 1);
        //Se extrae el maximo y se elimina
        assertEquals("Persona", heap.heapExtracMax());
        //Como se eliminó persona, ahora el maximo es paciente
        assertEquals("Paciente", heap.heapExtracMax());
    }

    @Test
    public void extractionOfMaxValue2() {
        setupStage1();
        //Este test tiene la finalidad de verificar que cuando los pacientes tienen una
        //Llave igual, se extrae el que primero se ingresó y se elimina
        heap.HeapInsert("Persona", 12);
        heap.HeapInsert("Paciente", 12);
        //Extrae el maximo y lo elimina
        assertEquals("Persona", heap.heapExtracMax());
        //Como se eliminó persona, paciente es el nuevo maximo
        assertEquals("Paciente", heap.heapExtracMax());
    }

    @Test
    public void extractionOfMaxValue3() {
        setupStage2();
        //Este test tiene la finalidad de verificar que, sin importar cuantos pacientes
        //esten ingresados, si se ingresa uno nuevo con una prioridad mayor, me extrae
        //Ese paciente con mayor prioridad y lo elimina
        heap.HeapInsert("Persona", 15);
        heap.HeapInsert("Paciente", 1);
        //Muestra el maximo y lo elimina
        assertEquals("Persona", heap.heapExtracMax());
        //Como se eliminó persona, ahora el maximo es PersonaTest3
        assertEquals("PTest3", heap.heapExtracMax());

    }

    @Test
    public void organitationBuildMaxHeapforTheMaximumToBeInTheRoot1() {
        setupStage1();

        //Este test tiene la finalidad de verificar que si se ingresan los pacientes
        //en orden, se guardan bien en la priority queue
        heap.HeapInsert("Luis", 10);
        heap.HeapInsert("Daniel", 9);
        heap.HeapInsert("Santiago", 8);

        assertEquals("Luis Daniel Santiago ", heap.print());

    }

    @Test
    public void organitationBuildMaxHeapforTheMaximumToBeInTheRoot2() {
        setupStage1();
        //Este test tiene la finalidad de verificar que si se ingresan los pacientes
        //con una llave igual, se organizan de acuerdo al primero que ingresó
        heap.HeapInsert("Luis", 1);
        heap.HeapInsert("Daniel", 1);
        heap.HeapInsert("Santiago", 2);

        assertEquals("Santiago Luis Daniel ", heap.print());

    }

    @Test
    public void organitationBuildMaxHeapforTheMaximumToBeInTheRoot3() {
        setupStage1();
        //Este test tiene la finalidad de verificar que si se ingresan los pacientes
        //con llaves en desorden,se organizan correctamente con una prioridad
        //de mayor a menor
        heap.HeapInsert("Luis", 3);
        heap.HeapInsert("Daniel", 1);
        heap.HeapInsert("Santiago", 2);

        assertEquals("Luis Santiago Daniel ", heap.print());

    }

    @Test
    public void increseKeyTest1() {
        setupStage2();
        //Este metodo tiene la finalidad de verificar que si le cambio la llave
        //a un paciente para que este tenga mayor prioridad, la priority queue
        //se organiza y lo coloca como la raiz, correctamente
        assertEquals("PTest3", heap.heapMaximum());
        heap.heapIncreaseKey("PTest1", 10);
        assertEquals("PTest1", heap.heapMaximum());


    }

    @Test
    public void increseKeyTest2() {
        setupStage2();
        //Este test tiene la finalida de mostrar que, al incrementar una llave
        //La priority queue se reorganiza correctamente, quedando en este caso
        //PTest4 de segundo
        heap.HeapInsert("PTest4", 0);
        heap.heapIncreaseKey("PTest4", 5);
        assertEquals("PTest3 PTest4 PTest1 PTest2 ", heap.print());


    }

    @Test
    public void increseKeyTest3() {
        setupStage2();
        //Este test tiene la finalida de mostrar que, si la nuva llave que se ingresa
        //es menor a la llave que ya tenia el paciente, no se modifica.
        heap.heapIncreaseKey("PTest3", 0);
        assertEquals("PTest3",heap.heapMaximum());


    }

    @Test
    public void maximumTest1() {
        setupStage1();
        //Este test tiene la finalidad de verificar que cuando los pacientes tienen una
        //Llave igual, se extrae el que primero se ingresó
        heap.HeapInsert("Persona", 12);
        heap.HeapInsert("Paciente", 12);
        assertEquals("Persona", heap.heapMaximum());
    }

    @Test
    public void maximumTest2() {
        setupStage2();
        //Este test tiene la finalidad de verificar que, sin importar cuantos pacientes
        //esten ingresados, si se ingresa uno nuevo con una prioridad mayor, me extrae el nuevo ingresado
        heap.HeapInsert("Persona", 15);
        heap.HeapInsert("Paciente", 1);
        assertEquals("Persona", heap.heapExtracMax());
    }

    @Test
    public void maximumTest3() {
        //Este test tiene la finalidad de verificar que se extrae
        //correctamente el paciente con la mayor llave
        setupStage1();
        heap.HeapInsert("Persona", 12);
        heap.HeapInsert("Paciente", 1);
        assertEquals("Persona", heap.heapExtracMax());
    }


}