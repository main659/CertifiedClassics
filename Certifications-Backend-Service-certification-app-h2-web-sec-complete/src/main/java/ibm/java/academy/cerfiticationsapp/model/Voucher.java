package ibm.java.academy.cerfiticationsapp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private State state;
	
    @Column(length = 100, nullable = false)
	private String voucherCode;
	
	private Date validUntil;

    enum State {
        ACTIVE, PROPOSED, NEW
    }
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "certification_id", insertable = true, updatable = false, nullable = false)
    private Certification certification;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = true, updatable = true, nullable = true)
    @JsonIgnoreProperties("vouchers")
    //@JsonBackReference
    private User user;

    @Override
    public String toString() {
        return "Voucher [certification name=" + certification.getName() + ", id=" + id + ", state=" + state + ", user name and surname=" + (user != null ? user.getName() + " " + user.getSurname(): null) 
                + ", validUntil=" + validUntil + ", voucherCode=" + voucherCode + "]";
    }
}
