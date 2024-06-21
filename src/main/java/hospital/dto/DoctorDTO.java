package hospital.dto;

public class DoctorDTO {
    private Long id;
    private String avatar;
    private String name;
    private String surname;
    private Long phoneNumber;
    private String email;
    private String profession;
    private String address;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phoneNumber +
                ", email='" + email + '\'' +
                ", role='" + profession + '\'' +
                '}';
    }

}