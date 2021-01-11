package com.mentormate.hackathon.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The Project entity class.
 *
 * @author Polina Usheva
 */
@Data
@Entity
@AllArgsConstructor
@Table(name = "projects")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

    @Column
    @NotNull(message = "Project name must be valid.")
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<>();
}
