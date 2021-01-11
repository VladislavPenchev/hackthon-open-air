package com.mentormate.hackathon.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * {@link Role} provides different  access to resources
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@Data
@Entity
@AllArgsConstructor
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    private RoleType name;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthority() {
        return name.name();
    }
}

