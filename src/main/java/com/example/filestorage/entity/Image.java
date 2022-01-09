package com.example.filestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_file_name")
    @EqualsAndHashCode.Exclude
    private String originalFilename;

    @Column(name = "file_name")
    @EqualsAndHashCode.Exclude
    private String filename;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @EqualsAndHashCode.Exclude
    private User owner;

    public Image(Long id) {
        this.id = id;
    }
}
