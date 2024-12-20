package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "return_time_line",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"return_request_id", "status"})
        }
)
public class ReturnTimeLine extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "return_request_id")
    private ReturnRequest returnRequest;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private ReturnRequestStatusType status;

    @Column(name = "description")
    private String description;

}
