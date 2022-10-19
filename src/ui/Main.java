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

    public static void main(String[] args) {

        patients = new ChainHashTable<>();
        queuePriority = new Heap<>();
        queue = new Heap<>();
        inLab = new ArrayList<>();
        code = new Patient[1];

        int exit = 0;
        JOptionPane.showMessageDialog(null, "Inicializando programa");

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
                            |1. Ingresar paciente                                                        |                                    |
                            |2. Atender cola de prioridad                                          |
                            |3. Atender cola                                                                |
                            |4. Personas en el laboratorio                                        |
                            |5. Orden de atención                                                      |
                            |6. Deshacer salida o entrada de paciente                   |
                            |7. Acabar programa                                                        |
                            |=========================================|"""));

            if (String.valueOf(option).equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter an option!");
            }

            switch (option) {
                case 1:

                    IngresoPaciente();
                    break;

                case 2:
                    if (queuePriority.length() <= 0) {
                        JOptionPane.showMessageDialog(null, "No se han ingresado pacientes");
                    } else {
                        JOptionPane.showMessageDialog(null, "Pasar al paciente: " + "\n" +
                                patients.search(queuePriority.heapMaximum()).getId() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getName() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getLastName());

                        JOptionPane.showMessageDialog(null, "Salida del paciente:" + "\n" +
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
                case 3:
                    if (queue.length() <= 0) {
                        JOptionPane.showMessageDialog(null, "No se ha ingresado ningun paciente!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Pasar el paciente:" + "\n" +
                                patients.search(queue.heapMaximum()).getId() + "\n" +
                                patients.search(queue.heapMaximum()).getName() + "\n" +
                                patients.search(queue.heapMaximum()).getLastName());

                        JOptionPane.showMessageDialog(null, "Salida del paciente:" + "\n" +
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
                case 4:

                    if (inLab.size() > 0) {
                        for (int i = 0; i < inLab.size(); i++) {
                            System.out.println(inLab.get(i).getId() + "|" +
                                    inLab.get(i).getName() + "|" +
                                    inLab.get(i).getLastName() + "|" +
                                    inLab.get(i).getGender() + "|" +
                                    inLab.get(i).getAge() + "|" +
                                    inLab.get(i).isPregnant() + "|" +
                                    inLab.get(i).getEnfermedad() + "|" +
                                    inLab.get(i).getImportance() + "|" +
                                    inLab.get(i).getPriority());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No han ingresado pacientes");
                    }

                    break;
                case 5:
                    //REVISAR EL PRINT
                    JOptionPane.showMessageDialog(null, queue.print());
                    if (queuePriority.length() <= 0 || queue.length() <= 0) {
                        JOptionPane.showMessageDialog(null, "There are no pacients entered yet");
                    } else {
                        int uni = Integer.parseInt(JOptionPane.showInputDialog(null, "Que unidad desea consultar?" +
                                "\nIngrese 1 -> Prioridad o 2 -> Normal"));
                        if (uni == 1) {
                            String priority = queuePriority.print();
                            JOptionPane.showMessageDialog(null, priority);
                        } else if (uni == 2) {
                            queue.print();
                        }
                    }

                    break;
                case 6:
                    //DESHACER
                    if (count == 1) {
                        patients.delete(code[0].getId());
                        inLab.remove(0);
                        if (code[0].getPriority() > 0) {

                        } else {

                        }
                    } else if (count == 2) {
                        patients.insert(code[0].getId(), new Patient(code[0].getId(),
                                code[0].getName(),
                                code[0].getLastName(),
                                code[0].getGender(),
                                code[0].getAge(),
                                code[0].isPregnant(),
                                code[0].getEnfermedad(),
                                code[0].getImportance().ordinal() + 1,
                                code[0].getPriority()));
                        inLab.add(code[0]);
                        if (code[0].getPriority() > 0) {
                            queuePriority.HeapInsert(code[0].getId(), code[0].getPriority());
                        } else {
                            queue.HeapInsert(code[0].getId(), 1);
                        }
                    } else if (count == 0) {
                        JOptionPane.showMessageDialog(null, "No ha ingresado ni salido ningun paciente");
                    }

                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Gracias por usar el programa!\n");
                    exit++;

                default:
                    JOptionPane.showMessageDialog(null, "Please enter a valid option!");
            }
        }
    }


    public static void IngresoPaciente() {

        String id = JOptionPane.showInputDialog("Ingrese la cedula del paciente");

        if (patients.search(id) != null && patients.search(id).getId().equals(id)) {
            JOptionPane.showMessageDialog(null, "Nombre: " + patients.search(id).getName() + " " + patients.search(id).getLastName() + "\n" +
                    "Edad: " + patients.search(id).getAge() + "\n" +
                    "Id: " + patients.search(id).getId() + "\n" +
                    "Genero: " + patients.search(id).getGender() + "\n" +
                    "Embarazo: " + patients.search(id).isPregnant() + "\n" +
                    "Enfermedad: " + patients.search(id).getEnfermedad() + "\n" +
                    "Prioridad?: " + patients.search(id).getPriority() + "\n" +
                    "Importancia: " + patients.search(id).getImportance());

            inLab.add(new Patient(patients.search(id).getId(),
                    patients.search(id).getName(),
                    patients.search(id).getLastName(),
                    patients.search(id).getGender(),
                    patients.search(id).getAge(),
                    patients.search(id).isPregnant(),
                    patients.search(id).getEnfermedad(),
                    patients.search(id).getImportance().ordinal() + 1,
                    patients.search(id).getPriority()));
        } else {

            JOptionPane.showMessageDialog(null, "El paciente no esta ingresado!");

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

            calculatePriority(id);
            JOptionPane.showMessageDialog(null, "Paciente agregado con exito!");

        }

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