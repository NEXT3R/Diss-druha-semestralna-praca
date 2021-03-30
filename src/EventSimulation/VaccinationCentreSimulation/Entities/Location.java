package EventSimulation.VaccinationCentreSimulation.Entities;

public enum Location {
    REGISTRATION_QUEUE,
    REGISTRATION,
    EXAMINATION_QUEUE,
    EXAMINATION,
    VACCINATION_QUEUE,
    VACCINATION,
    WAITING_ROOM;

    public String resolveStringValue(Location l){
        switch (l){
            case REGISTRATION_QUEUE:
                return "Registration queue";
            case EXAMINATION_QUEUE:
                return "Examination queue";
            case VACCINATION_QUEUE:
                return "Vaccination queue";
            case REGISTRATION:
                return "Registration";
            case VACCINATION:
                return "Vaccination";
            case EXAMINATION:
                return "Examination";
            case WAITING_ROOM:
                return "Waiting room";
        }
        return "Unknown";
    }
}
