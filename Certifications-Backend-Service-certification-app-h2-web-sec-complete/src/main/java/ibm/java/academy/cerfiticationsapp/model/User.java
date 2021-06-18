package ibm.java.academy.cerfiticationsapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	Long id;

    String name;

	String surname;

	String email;

    String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    //@JsonManagedReference
    @JsonIgnoreProperties("user")
    List<Voucher> vouchers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
     joinColumns = @JoinColumn(name = "user_id"),
     inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
