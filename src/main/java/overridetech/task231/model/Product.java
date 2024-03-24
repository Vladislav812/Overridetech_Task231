package overridetech.task231.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
