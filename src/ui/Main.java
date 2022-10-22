package ui;

import com.google.gson.Gson;
import hashtables.ChainHashTable;
import heap.Heap;

import model.*;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static ChainHashTable<String, Patient> patients;
    private static Heap<String> queuePriorityG;
    private static Heap<String> queueG;
    private static Heap<String> queuePriorityH;
    private static Heap<String> queueH;
    private static ArrayList<Patient> inLab;
    private static Patient[] code;
    private static int count = 0;
    private static int unit = 0;

    public static void main(String[] args) {

        patients = new ChainHashTable<>();
        queuePriorityG = new Heap<>();
        queueG = new Heap<>();
        queuePriorityH = new Heap<>();
        queueH = new Heap<>();
        inLab = new ArrayList<>();
        code = new Patient[1];

        int exit = 0;
        JOptionPane.showMessageDialog(null, "Starting program");

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
            String input = JOptionPane.showInputDialog(null,
                    """
                            |=========================================|
                            |1. Enter patient
                            |2. Attend hematology patient
                            |3. Attend general queue patient
                            |4. Patients at the lab
                            |5. Attention order
                            |6. Undo entry or exit of pacients
                            |7. End program
                            |=========================================|""");

            if (input.isEmpty()) {
                continue;
            }

            int option = Integer.parseInt(input);

            switch (option) {
                case 1:

                    insertPatient();
                    break;
                case 2:

                    attendH();
                    break;
                case 3:

                    attendG();
                    break;
                case 4:

                    patientsInLab();
                    break;
                case 5:

                    checkOrder();
                    break;
                case 6:

                    undo();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Thanks for using the program!\n");
                    exit++;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Please enter a valid option!");
            }
        }
    }


    public static void insertPatient() {

        String id = JOptionPane.showInputDialog("Enter pacient id");

        while (id.isEmpty()) {
            id = JOptionPane.showInputDialog("Enter pacient id");
        }

        if (patients.search(id) != null && patients.search(id).getId().equals(id)) {

            int importance = 0;

            if (patients.search(id).getImportance().equals(situationImportance.NONE)) {
                importance = 1;
            } else if (patients.search(id).getImportance().equals(situationImportance.LOW)) {
                importance = 2;
            } else if (patients.search(id).getImportance().equals(situationImportance.MODERATE)) {
                importance = 3;
            } else if (patients.search(id).getImportance().equals(situationImportance.INTERMEDIATE)) {
                importance = 4;
            } else if (patients.search(id).getImportance().equals(situationImportance.SERIOUS)) {
                importance = 5;
            } else if (patients.search(id).getImportance().equals(situationImportance.GRAVE)) {
                importance = 6;
            } else if (patients.search(id).getImportance().equals(situationImportance.CRITICAL)) {
                importance = 7;
            }

            JOptionPane.showMessageDialog(null, "Name: " + patients.search(id).getName() + " " + patients.search(id).getLastName() + "\n" +
                    "Age: " + patients.search(id).getAge() + "\n" +
                    "Id: " + patients.search(id).getId() + "\n" +
                    "Gender: " + patients.search(id).getGender() + "\n" +
                    "Pregnant: " + patients.search(id).isPregnant() + "\n" +
                    "Disease: " + patients.search(id).getEnfermedad() + "\n" +
                    "Importance: " + patients.search(id).getImportance() + "\n" +
                    "Priority: " + patients.search(id).getPriority());

            inLab.add(new Patient(patients.search(id).getId(),
                    patients.search(id).getName(),
                    patients.search(id).getLastName(),
                    patients.search(id).getGender(),
                    patients.search(id).getAge(),
                    patients.search(id).isPregnant(),
                    patients.search(id).getEnfermedad(),
                    importance,
                    patients.search(id).getPriority()));
        } else {

            JOptionPane.showMessageDialog(null, "The patient is not entered yet");

            String name = JOptionPane.showInputDialog("Enter the name of the patient");

            String lastName = JOptionPane.showInputDialog("Enter the last name of the patient");

            String[] genderOption = {"Masculine", "Femenine"};

            String gender = (String) JOptionPane.showInputDialog(null, "Enter the patient's gender",
                    "Choose gender", JOptionPane.QUESTION_MESSAGE,
                    null, genderOption,
                    genderOption[1]);

            int age = Integer.parseInt(JOptionPane.showInputDialog("Enter the age of the patient"));

            String[]pregnancyOption = {"true", "false"};

            String pregnancyChoose = (String) JOptionPane.showInputDialog(null,"Is the patient pregnant?",
                    "Choose pregnancy", JOptionPane.QUESTION_MESSAGE,
                    null,
                    pregnancyOption,
                    pregnancyOption[1]);

            boolean pregnancy = Boolean.parseBoolean(pregnancyChoose);

            String[] optionsToChoose = {"Cancer", "Asthma", "Leukemia", "Bronchitis", "Infection", "Parasites", "None of the listed", "None"};

            String ill = (String) JOptionPane.showInputDialog(null, "Enter the disease of the patient",
                    "Choose disease", JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionsToChoose,
                    optionsToChoose[3]);

            int importance = Integer.parseInt(JOptionPane.showInputDialog("Enter the severity of the patient's condition" +
                    "\nEnter 1 -> NONE, 2 -> LOW, 3 -> MODERATE, 4 -> INTERMEDIATE, 5 -> SERIOUS, 6 -> GRAVE, 7 -> CRITICAL"));

            patients.insert(id, new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, 0));

            count = 1;

            calculatePriority(id);

            inLab.add(new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, patients.search(id).getPriority()));
            code[0] = new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, patients.search(id).getPriority());

            JOptionPane.showMessageDialog(null, "Patient added successfully!");

        }

        int unity = Integer.parseInt(JOptionPane.showInputDialog("Enter the unit to which the patient goes" +
                "\nEnter 1 -> Hematology, 2 -> General propose"));

        if (unity == 1) {
            unit = 1;
            if (patients.search(id).getPriority() > 0) { //map
                queuePriorityH.HeapInsert(id, patients.search(id).getPriority());
            } else {
                queueH.HeapInsert(id, 1);
            }
        } else if (unity == 2) {
            unit = 2;
            if (patients.search(id).getPriority() > 0) { //map
                queuePriorityG.HeapInsert(id, patients.search(id).getPriority());
            } else {
                queueG.HeapInsert(id, 1);
            }
        }

    }

    public static void calculatePriority(String id) {

        if (patients.search(id).getAge() >= 60) {
            patients.search(id).setPriority(patients.search(id).sumPriority(1));
        }

        if (patients.search(id).isPregnant()) {
            patients.search(id).setPriority(patients.search(id).sumPriority(2));
        }

        if (patients.search(id).getEnfermedad().equals("Cancer") || patients.search(id).getEnfermedad().equals("Leukemia") ||
                patients.search(id).getEnfermedad().equals("Asthma") || patients.search(id).getEnfermedad().equals("Bronchitis") ||
                patients.search(id).getEnfermedad().equals("Infection") || patients.search(id).getEnfermedad().equals("Parasites")) {
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

    public static void attendH() {

        if (queueG.length() <= 0 && queuePriorityG.length() <= 0) {
            JOptionPane.showMessageDialog(null, "There is no patient entered yet!");
        } else {
            int queues = Integer.parseInt(JOptionPane.showInputDialog("Which queue do you want to attend?" + "\n" +
                    "Enter 1 -> priority queue, 2 -> General queue"));

            if (queues == 1) {
                if (queuePriorityG.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "There is no patient entered yet!");
                } else {
                    JOptionPane.showMessageDialog(null, "Patient checkIn: " + "\n" +
                            "ID: " + patients.search(queuePriorityH.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queuePriorityH.heapMaximum()).getName() + " " +
                            patients.search(queuePriorityH.heapMaximum()).getLastName());

                    JOptionPane.showMessageDialog(null, "The patient is now in query");

                    JOptionPane.showMessageDialog(null, "Patient checkout:" + "\n" +
                            "ID: " + patients.search(queuePriorityH.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queuePriorityH.heapMaximum()).getName() + " " +
                            patients.search(queuePriorityH.heapMaximum()).getLastName());

                    for (int i = 0; i < inLab.size(); i++) {
                        if (inLab.get(i).getId().equals(patients.search(queuePriorityH.heapMaximum()).getId())) {
                            inLab.remove(i);
                            break;

                        }
                    }
                    code[0] = patients.search(queuePriorityH.heapMaximum());
                    queuePriorityH.heapExtracMax();
                    count = 2;
                }
            } else if (queues == 2) {
                if (queueG.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "There is no patient entered yet!");
                } else {
                    JOptionPane.showMessageDialog(null, "Patient checkIn:" + "\n" +
                            "ID: " + patients.search(queueH.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queueH.heapMaximum()).getName() + " " +
                            patients.search(queueH.heapMaximum()).getLastName());

                    JOptionPane.showMessageDialog(null, "The patient is now in query");

                    JOptionPane.showMessageDialog(null, "Patient checkout:" + "\n" +
                            "ID: " + patients.search(queueH.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queueH.heapMaximum()).getName() + " " +
                            patients.search(queueH.heapMaximum()).getLastName());

                    for (int i = 0; i < inLab.size(); i++) {
                        if (inLab.get(i).getId().equals(patients.search(queueH.heapMaximum()).getId())) {
                            inLab.remove(i);
                            break;
                        }
                    }

                    code[0] = patients.search(queueH.heapMaximum());
                    queueH.heapExtracMax();
                    count = 2;
                }
            }

            unit = 1;
        }

    }

    public static void attendG() {

        if (queueG.length() <= 0 && queuePriorityG.length() <= 0) {
            JOptionPane.showMessageDialog(null, "There is no patient entered yet!");
        } else {
            int queues2 = Integer.parseInt(JOptionPane.showInputDialog("Which queue do you want to attend?" + "\n" +
                    "Enter 1 -> Priority queue, 2 -> General queue"));
            if (queues2 == 1) {
                if (queuePriorityG.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "There is no patient entered yet!");
                } else {
                    JOptionPane.showMessageDialog(null, "Patient checkIn " + "\n" +
                            "ID: " + patients.search(queuePriorityG.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queuePriorityG.heapMaximum()).getName() + " " +
                            patients.search(queuePriorityG.heapMaximum()).getLastName());

                    JOptionPane.showMessageDialog(null, "The patient is now in query");

                    JOptionPane.showMessageDialog(null, "Patient checkout:" + "\n" +
                            "ID: " + patients.search(queuePriorityG.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queuePriorityG.heapMaximum()).getName() + " " +
                            patients.search(queuePriorityG.heapMaximum()).getLastName());

                    for (int i = 0; i < inLab.size(); i++) {
                        if (inLab.get(i).getId().equals(patients.search(queuePriorityG.heapMaximum()).getId())) {
                            inLab.remove(i);
                            break;

                        }
                    }
                    code[0] = patients.search(queuePriorityG.heapMaximum());
                    queuePriorityG.heapExtracMax();
                    count = 2;
                }
            } else if (queues2 == 2) {
                if (queueG.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "There is no patient entered yet!");
                } else {
                    JOptionPane.showMessageDialog(null, "Patient checkIn:" + "\n" +
                            "ID: " + patients.search(queueG.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queueG.heapMaximum()).getName() + " " +
                            patients.search(queueG.heapMaximum()).getLastName());

                    JOptionPane.showMessageDialog(null, "The patient is now in query");

                    JOptionPane.showMessageDialog(null, "Patient checkout:" + "\n" +
                            "ID: " + patients.search(queueG.heapMaximum()).getId() + "\n" +
                            "Name: " + patients.search(queueG.heapMaximum()).getName() + " " +
                            patients.search(queueG.heapMaximum()).getLastName());

                    for (int i = 0; i < inLab.size(); i++) {
                        if (inLab.get(i).getId().equals(patients.search(queueG.heapMaximum()).getId())) {
                            inLab.remove(i);
                            break;
                        }
                    }

                    code[0] = patients.search(queueG.heapMaximum());
                    queueG.heapExtracMax();
                    count = 2;
                }
            }

            unit = 2;
        }

    }

    public static void patientsInLab() {

        if (inLab.size() > 0) {
            for (int i = 0; i < inLab.size(); i++) {
                JOptionPane.showMessageDialog(null, "ID: " + inLab.get(i).getId() + "\n" +
                        "Name: " + inLab.get(i).getName() + " " +
                        inLab.get(i).getLastName() + "\n" +
                        "Gender: " + inLab.get(i).getGender() + "\n" +
                        "Age: " + inLab.get(i).getAge() + "\n" +
                        "Pregnancy: " + inLab.get(i).isPregnant() + "\n" +
                        "Disease: " + inLab.get(i).getEnfermedad() + "\n" +
                        "Importance: " + inLab.get(i).getImportance() + "\n" +
                        "Priority: " + inLab.get(i).getPriority());
            }
        } else {
            JOptionPane.showMessageDialog(null, "There are no patients entered yet!");
        }

    }

    public static void checkOrder() {

        if (inLab.size() <= 0) {
            JOptionPane.showMessageDialog(null, "There are no patient entered yet!");
        } else {
            int unit = Integer.parseInt(JOptionPane.showInputDialog("Which unit do you want to check?" + "\n" +
                    "Enter 1 -> Hematology, 2 -> General attention"));
            if (unit == 1) {
                if (queuePriorityH.length() <= 0 && queueH.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "There are no pacients entered yet");
                } else {
                    int uni = Integer.parseInt(JOptionPane.showInputDialog(null, "Which unit do you want to check?" +
                            "\nEnter 1 -> Priority o 2 -> Normal"));
                    if (uni == 1) {
                        if (queuePriorityH.length() <= 0) {
                            JOptionPane.showMessageDialog(null, "There are no patients entered yet!");
                        } else {
                            String priority = queuePriorityH.print();
                            JOptionPane.showMessageDialog(null, priority);
                        }
                    } else if (uni == 2) {
                        if (queueH.length() <= 0) {
                            JOptionPane.showMessageDialog(null, "There are no patients entered yet!");
                        } else {
                            String normalQueue = queueH.print();
                            JOptionPane.showMessageDialog(null, normalQueue);
                        }
                    }
                }
            } else if (unit == 2) {
                if (queuePriorityG.length() <= 0 && queueG.length() <= 0) {
                    JOptionPane.showMessageDialog(null, "There are no patients entered yet!");
                } else {
                    int uni = Integer.parseInt(JOptionPane.showInputDialog(null, "Which unit do you want to check?" +
                            "\nEnter 1 -> Priority o 2 -> Normal"));
                    if (uni == 1) {
                        if (queuePriorityG.length() <= 0) {
                            JOptionPane.showMessageDialog(null, "There are no patient entered yet!");
                        } else {
                            String priority = queuePriorityG.print();
                            JOptionPane.showMessageDialog(null, priority);
                        }
                    } else if (uni == 2) {
                        if (queueG.length() <= 0) {
                            JOptionPane.showMessageDialog(null, "There are no patient entered yet!");
                        } else {
                            String normalQueue = queueG.print();
                            JOptionPane.showMessageDialog(null, normalQueue);
                        }
                    }
                }
            }
        }

    }

    public static void undo() {

        if (count == 1) {
            patients.delete(code[0].getId());
            inLab.remove(0);
            if (unit == 1) {
                if (code[0].getPriority() > 0) {
                    queuePriorityH.deletePriority(code[0].getId());
                } else {
                    queueH.deletePriority(code[0].getId());
                }
            } else if (unit == 2) {
                if (code[0].getPriority() > 0) {
                    queuePriorityG.deletePriority(code[0].getId());
                } else {
                    queueG.deletePriority(code[0].getId());
                }
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
            if (unit == 1) {
                if (code[0].getPriority() > 0) {
                    queuePriorityH.HeapInsert(code[0].getId(), code[0].getPriority());
                } else {
                    queueH.HeapInsert(code[0].getId(), 1);
                }
            } else if (unit == 2) {
                if (code[0].getPriority() > 0) {
                    queuePriorityG.HeapInsert(code[0].getId(), code[0].getPriority());
                } else {
                    queueG.HeapInsert(code[0].getId(), 1);
                }
            }
        } else if (count == 0) {
            JOptionPane.showMessageDialog(null, "There are no patient entered yet!");
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