package ui;

import com.google.gson.Gson;
import hashtables.ChainHashTable;
import heap.Heap;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static ChainHashTable<String, Patient> patients;
    private static Heap<String> queuePriority;
    private static Heap<String> queue;
    private static ArrayList<Patient> inLab;
    private static Patient[] code;
    private static int count = 0;
    private static int countEnteredPacient = 0;

    public static void main(String[] args) {

        patients = new ChainHashTable<>();
        queuePriority = new Heap<>();
        queue = new Heap<>();
        inLab = new ArrayList<>();
        code = new Patient[1];

        int exit = 0;
        System.out.println("Inicializando programa");

        /*
        try {
            loadMemory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */

        File archivo = null;
        archivo = new File("DatosPacientes.csv");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(archivo);

            BufferedReader sc = new BufferedReader(
                    new InputStreamReader(fis)
            );
            String line = sc.readLine();

            while ((line = sc.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split("\\|");
                patients.insert(parts[0], new Patient(
                        parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Boolean.parseBoolean(parts[5]), parts[6], Integer.parseInt(parts[7]), Integer.parseInt(parts[8])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (exit == 0) {
            int option = Integer.parseInt(JOptionPane.showInputDialog(null,
                    """
                            |=========================================|
                            |1. Ingresar paciente                                                        |
                            |2. Verificar ingreso de paciente                                    |
                            |3. Atender cola de prioridad                                          |
                            |4. Atender cola                                                                |
                            |5. Personas en el laboratorio                                        |
                            |6. Orden de atencion                                                      |
                            |7. Deshacer salida o entrada de paciente                   |
                            |8. Acabar programa                                                        |
                            |=========================================|"""));

            switch (option) {
                case 1:

                    IngresoPaciente();
                    countEnteredPacient++;
                    break;

                case 2:

                    verficarIngreso();
                    break;

                case 3:
                    queue.print();
                    if (countEnteredPacient == 0) {
                        JOptionPane.showMessageDialog(null,"No se han ingresado pacientes" );
                    } else {
                        JOptionPane.showMessageDialog(null,"Pasar al paciente: " + "\n" +
                                patients.search(queuePriority.heapMaximum()).getId() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getName() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getLastName());

                        JOptionPane.showMessageDialog(null,"Salida del paciente:" + "\n" +
                                patients.search(queuePriority.heapMaximum()).getId() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getName() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getLastName());

                        for (int i = 0; i < inLab.size(); i++) {
                            if (inLab.get(i).getId().equals(patients.search(queue.heapMaximum()).getId())) {
                                inLab.remove(i);
                                break;

                            }
                        }
                        code[0] = patients.search(queue.heapMaximum());
                        queuePriority.heapExtracMax();
                        count = 2;
                    }
                    break;
                case 4:
                    if (countEnteredPacient == 0) {
                        JOptionPane.showMessageDialog(null, "No se ha ingresado ningun paciente!");
                    } else {
                        JOptionPane.showMessageDialog(null,"Pasar el paciente:" + "\n" +
                                patients.search(queue.heapMaximum()).getId() + "\n" +
                                patients.search(queue.heapMaximum()).getName() + "\n" +
                                patients.search(queue.heapMaximum()).getLastName());

                        JOptionPane.showMessageDialog(null,"Salida del paciente:" + "\n" +
                                patients.search(queue.heapMaximum()).getId() + "\n" +
                                patients.search(queue.heapMaximum()).getName() + "\n" +
                                patients.search(queue.heapMaximum()).getLastName());

                        for (int i = 0; i < inLab.size(); i++) {
                            if (inLab.get(i).getId().equals(patients.search(queue.heapMaximum()).getId())) {
                                inLab.remove(i);
                                break;
                            }
                        }

                        code[0] = patients.search(queue.heapMaximum());
                        queue.heapExtracMax();
                        count = 2;
                    }

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:


                    break;
                case 8:
                    JOptionPane.showMessageDialog(null,"Gracias por usar el programa!\n");
                    exit++;
            }
        }
    }

    public static void verficarIngreso() {
        String id = JOptionPane.showInputDialog("Ingrese cedula del paciente");
        String disease = "";
/*
        if (patients.search(id).getEnfermedad() == null) {
            disease = "ninguna";
        }

 */
        if (patients.search(id).getId().equals(id)) {
            JOptionPane.showMessageDialog(null, "Nombre: " + patients.search(id).getName()
                    + " " + patients.search(id).getLastName() + "\n" + "Edad: " + patients.search(id).getAge() + "\n" + "Id: " + patients.search(id).getId() + "\n" +
                    "Genero: " + patients.search(id).getGender() + "\n" + "Embarazo: " + patients.search(id).isPregnant() + "\nEnfermedad: " + patients.search(id).getEnfermedad() +
                    "\n" + "Prioridad?: " + patients.search(id).getPriority() + "\n" + "Importancia: " + patients.search(id).getImportance());
        }

        inLab.add(new Patient(patients.search(id).getId(),
                patients.search(id).getName(),
                patients.search(id).getLastName(),
                patients.search(id).getGender(),
                patients.search(id).getAge(),
                patients.search(id).isPregnant(),
                patients.search(id).getEnfermedad(),
                patients.search(id).getImportance().ordinal() + 1,
                patients.search(id).getPriority()));

        patients.insert(id, new Patient(id,
                patients.search(id).getName(),
                patients.search(id).getLastName(),
                patients.search(id).getGender(),
                patients.search(id).getAge(),
                patients.search(id).isPregnant(),
                patients.search(id).getEnfermedad(),
                patients.search(id).getPriority(),
                patients.search(id).getImportance().ordinal() + 1));

    }

    public static void IngresoPaciente() {

        String id = JOptionPane.showInputDialog("Ingrese la cedula del paciente");

        String name = JOptionPane.showInputDialog("Ingrese el nombre del paciente");

        String lastName = JOptionPane.showInputDialog("Ingrese el apellido del paciente");

        String gender = JOptionPane.showInputDialog("Ingrese el genero del paciente");

        int age = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del paciente"));

        boolean pregnancy = Boolean.parseBoolean(JOptionPane.showInputDialog("El paciente se encuentra embarazado?"));

        String ill = JOptionPane.showInputDialog("Ingrese la enfermedad que tenga el paciente" +
                "\ncoloque *ninguna* si el paciente no tiene enfermedad alguna ");

        int importance = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la gravedad de la situacion actual del paciente" +
                "\nDigite 1 -> NONE, 2 -> LOW, 3 -> MODERATE, 4 -> INTERMEDIATE, 5 -> SERIOUS, 6 -> GRAVE, 7 -> CRITICAL"));

        patients.insert(id, new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, 0));
        inLab.add(new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, 0));
        code[0] = new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, 0);

        count = 1;

        JOptionPane.showMessageDialog(null, "Paciente agregado con exito!");

        if (patients.search(id).getPriority() > 0) { //map
            queuePriority.HeapInsert(id, patients.search(id).getPriority());
        } else {
            queue.HeapInsert(id, 1);
        }
    }

    public static void calculatePriority(String id) {

        if (patients.search(id).getAge() >= 60) {
            patients.search(id).setPriority(patients.search(id).sumPriority(1));
        }

        if (patients.search(id).isPregnant()) {
            patients.search(id).setPriority(patients.search(id).sumPriority(2));
        }

        if (patients.search(id).getEnfermedad().equals("Cancer")) {
            patients.search(id).setPriority(patients.search(id).sumPriority(2));
        }

        if (patients.search(id).getImportance().equals(situationImportance.NONE)) {
            patients.search(id).setPriority(patients.search(id).sumPriority(0));
        } else if (patients.search(id).getImportance().equals(situationImportance.LOW)) {
            patients.search(id).setPriority(patients.search(id).sumPriority(1));
        } else if (patients.search(id).getImportance().equals(situationImportance.MODERATE)) {
            patients.search(id).setPriority(patients.search(id).sumPriority(2));
        } else if (patients.search(id).getImportance().equals(situationImportance.INTERMEDIATE)) {
            patients.search(id).setPriority(patients.search(id).sumPriority(3));
        } else if (patients.search(id).getImportance().equals(situationImportance.SERIOUS)) {
            patients.search(id).setPriority(patients.search(id).sumPriority(4));
        } else if (patients.search(id).getImportance().equals(situationImportance.GRAVE)) {
            patients.search(id).setPriority(patients.search(id).sumPriority(5));
        } else if (patients.search(id).getImportance().equals(situationImportance.CRITICAL)) {
            patients.search(id).setPriority(patients.search(id).sumPriority(6));
        }

    }

    public static void loadMemory() throws IOException {
        FileInputStream fis = new FileInputStream("program.json");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(fis)
        );

        String json = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            json += line;
        }

        Gson gson = new Gson();
        Patient[] data = gson.fromJson(json, Patient[].class);
        for (Patient datum : data) {
            patients.insert(datum.getId(), datum);
        }
    }
}