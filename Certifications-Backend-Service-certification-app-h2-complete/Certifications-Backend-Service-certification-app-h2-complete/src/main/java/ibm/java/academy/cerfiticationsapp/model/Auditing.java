package ibm.java.academy.cerfiticationsapp.model;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class Auditing<T> {
    
    @JsonIgnore
    @CreatedBy
    private T createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @LastModifiedBy
    private T lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @Version
    @JsonIgnore
    private Integer version;
}
