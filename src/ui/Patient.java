package ui;

public class Patient {

    private String id;
    private String name;
    private String lastName;
    private String Gender;
    private int Age;
    private boolean Pregnant;
    private String enfermedad;
    private situationImportance importance;
    private int priority;

    public Patient(String id, String name, String lastName, String gender, int age, boolean pregnant, String enfermedad, int importance, int priority) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.Gender = gender;
        this.Age = age;
        this.Pregnant = pregnant;
        this.enfermedad = enfermedad;
        switch (importance){

            case 1:
                this.importance = situationImportance.NONE;
                break;
            case 2:
                this.importance = situationImportance.LOW;
                break;
            case 3:
                this.importance = situationImportance.MODERATE;
                break;
            case 4:
                this.importance = situationImportance.INTERMEDIATE;
                break;
            case 5:
                this.importance = situationImportance.SERIOUS;
                break;
            case 6:
                this.importance = situationImportance.GRAVE;
                break;
            case 7:
                this.importance = situationImportance.CRITICAL;
                break;

        }
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public boolean isPregnant() {
        return Pregnant;
    }

    public void setPregnant(boolean pregnant) {
        Pregnant = pregnant;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public situationImportance getImportance() {
        return importance;
    }

    public void setImportance(situationImportance importance) {
        this.importance = importance;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int sumPriority(int a){
        return priority + a;
    }
}
