package com.example.my_coffee_list.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bean")
@Data
public class Bean {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  //auto_increment
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;
}
