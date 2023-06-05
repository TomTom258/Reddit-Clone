package com.example.redditclone.users.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Set;

@Entity(name = "role")
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Column(name = "users", nullable = true)
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "role_privileges", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "privileges_id")})
    @Column(name = "privileges", nullable = true)
    private Collection<Privilege> privileges;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Role setUsers(Set<User> users) {
        this.users = users;
        return this;
    }

    public Role setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
        return this;
    }
}
