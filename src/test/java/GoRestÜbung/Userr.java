package GoRestÜbung;

public class Userr {

    private int id;
    private String name;
    private String eMail;
    private String gender;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Userr{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eMail='" + eMail + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
