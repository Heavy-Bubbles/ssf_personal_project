package tan.chelsea.ssf_personal_project.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// remember to implement serializable for redis
public class Contact implements Serializable{

    // @NotNull - constrained CharSequence, Collection, Map, or Array is valid as long as it's not null, but it can be empty.
    // @NotEmpty - a constrained CharSequence, Collection, Map, or Array is valid as long as it's not null, and its size/length is greater than zero.
    // @NotBlank - a constrained String is valid as long as it's not null, and the trimmed length is greater than zero.

    // validation for blank and size
    @NotBlank(message =  "Name cannot be empty!")
    @Size(min= 3, max = 20, message = "Name should be between 3 to 20 characters!")
    private String name;

    // validation for blank and email
    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Invalid Email!")
    private String email;

    // validation for blank and phone number
    @NotBlank(message = "Phone number cannot be empty!")
    @Pattern(regexp = "^[0-9]{8}$", message = "Invalid phone number!")
    private String phoneNumber;

    // validation for past date, blank, and format
    @Past(message = "Date of Birth should not be from the future!")
    @NotNull(message = "Date of Birth is mandatory")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    // validation for min and max age
    @Min(value = 10, message = "Must be at least 10 years old!")
    @Max(value = 100, message = "Must be not more than 100 years old!")
    private int age;

    @NotBlank(message = "Donation amount cannot be blank!")
    @Min(value = 1, message = "You have to donate at least $1!")
    private String donation;

    private String id;

    //method to generate ID
    private String generateID(){
        Random random = new Random();
        char[] alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < 8){
            sb.append(alphaNumeric[random.nextInt(alphaNumeric.length)]);
        }
        return sb.toString();
    }

    

    public Contact(String name, String phoneNumber, String email, LocalDate dateOfBirth, String donation, String id) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.donation = donation;
        this.id = id;
    }

    public Contact() {
        this.id = generateID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;

        // calculate age here as you are setting the age when you submit the form
        int calculatedAge = 0;
        if (dateOfBirth != null){
            calculatedAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        this.age = calculatedAge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDonation() {
        return donation;
    }

    public void setDonation(String donation) {
        this.donation = donation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
                + dateOfBirth + ", age=" + age + ", donation=" + donation + ", id=" + id + "]";
    }

    
    
}
