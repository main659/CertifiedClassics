package ibm.java.academy.cerfiticationsapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority{
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;
  private String roleName;
  public Role(){}
  public Role(String roleName){
     this.roleName = roleName;
  }
  public Long getId() {
     return id;
  }
  public void setId(Long id) {
     this.id = id;
  }
  public String getRoleName() {
     return roleName;
  }
  public void setRoleName(String roleName) {
     this.roleName = roleName;
  }
   @Override
   public String getAuthority() {
      return roleName;
   }
}
