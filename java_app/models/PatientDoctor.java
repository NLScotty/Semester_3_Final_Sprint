package models;

public class PatientDoctor {
    private int patientId;
    private int doctorId;

    public PatientDoctor(int patientId, int doctorId){
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }
    public int getDoctorId() {
        return doctorId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return "Patient ID: "+patientId+", Doctor ID: "+doctorId;
    }
}
