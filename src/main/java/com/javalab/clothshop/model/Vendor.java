package com.javalab.clothshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
public class Vendor extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Singular
    private List<Product> products;
}
