package si.fri.rso.userprofile.models;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
@NamedQueries(value =
        {
                @NamedQuery(name = "Person.getOnID", query = "SELECT p FROM Person p WHERE p.id = :id")

        })
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "Person",fetch = FetchType.EAGER)
    private List<Borrow> borrows;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id= id;
    }
}
