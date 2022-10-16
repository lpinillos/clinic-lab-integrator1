package ui;

import com.google.gson.Gson;
import hashtables.ChainHashTable;
import heap.Heap;

import java.io.*;
import java.util.*;


public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static ChainHashTable<String, Patient> patients;
    private static Heap<String> queuePriority;
    private static Heap<String> queue;
    private static ArrayList<Patient> inLab;
    private static int countEnteredPacient = 0;

    public static void main(String[] args) {

        patients = new ChainHashTable<>();
        queuePriority = new Heap<>();
        queue = new Heap<>();
        inLab = new ArrayList<>();

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
            System.out.println("""
                    ===========================
                    1. Ingresar paciente\s
                    2. Atender cola de prioridad\s
                    3. Atender cola\s
                    4. Personas en el laboratorio\s
                    5. Orden de atencion\s
                    6. Deshacer salida o entrada de paciente\s
                    7. Acabar programa\s
                    ===========================""");
            int option = sc.nextInt();

            switch (option) {
                case 1:

                    IngresoPaciente();
                    countEnteredPacient++;
                    break;

                case 2:
                    if (countEnteredPacient == 0) {
                        System.out.println("No se han ingresado pacientes");
                    } else {
                        System.out.println("Pasar el paciente:");
                        System.out.println(patients.search(queuePriority.heapMaximum()).getId() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getName() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getLastName());

                        System.out.println("Salida del paciente:");
                        System.out.println(patients.search(queuePriority.heapMaximum()).getId() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getName() + "\n" +
                                patients.search(queuePriority.heapMaximum()).getLastName());

                        for (int i = 0; i < inLab.size(); i++) {
                            if (inLab.get(i).getId().equals(patients.search(queue.heapMaximum()).getId())) {
                                inLab.remove(i);
                                break;

                            }
                        }
                        queuePriority.heapExtracMax();
                    }
                    break;
                case 3:


                    break;
                case 4:


                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:
                    System.out.println("Gracias por usar el programa\n");
                    exit++;
            }
        }
    }

    public static void IngresoPaciente() {

        System.out.println("Ingrese la cedula del paciente");
        String id = sc.next();


        if (patients.search(id).getId().equals(id)) {
            System.out.println(patients.search(id).getId() + "\n" +
                    patients.search(id).getName() + "\n" +
                    patients.search(id).getLastName() + "\n" +
                    patients.search(id).getGender() + "\n" +
                    patients.search(id).getAge() + "\n" +
                    patients.search(id).isPregnant() + "\n" +
                    patients.search(id).getEnfermedad() + "\n" +
                    patients.search(id).getImportance() + "\n" +
                    patients.search(id).getPriority());

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
        } else {
            System.out.println("El paciente no ha sido agregado");

            System.out.println("Ingrese el nombre del paciente");
            String name = sc.next();

            System.out.println("Ingrese el apellido del paciente");
            String lastName = sc.next();

            System.out.println("Ingrese el genero del paciente");
            String gender = sc.next();

            System.out.println("Ingrese la edad del paciente");
            int age = sc.nextInt();

            System.out.println("El paciente se encuentra embarazado?");
            boolean pregnancy = sc.nextBoolean();

            System.out.println("Ingrese la enfermedad que tenga el paciente");
            System.out.println("Dejelo en blanco si no posee ninguna");
            String ill = sc.next();

            System.out.println("Ingrese la gravedad de la situacion actual del paciente");
            System.out.println("Digite 1 -> NONE, 2 -> LOW, 3 -> MODERATE, 4 -> INTERMEDIATE, 5 -> SERIOUS, 6 -> GRAVE, 7 -> CRITICAL");
            int importance = sc.nextInt();

            patients.insert(id, new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, 0));
            inLab.add(new Patient(id, name, lastName, gender, age, pregnancy, ill, importance, 0));


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