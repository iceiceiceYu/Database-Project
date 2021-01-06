package edu.fudan.database.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DailyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
