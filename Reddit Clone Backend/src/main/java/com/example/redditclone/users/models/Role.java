package com.example.redditclone.users.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity(name = "role")
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "ability", nullable = false)
    private String ability;

    @ManyToMany(mappedBy = "roles")
    @Column(name = "users", nullable = true)
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "role_permissions", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permissions_id")})
    @Column(name = "permissions", nullable = true)
    private Set<Permission> permissions;

    public Role() {
    }

    public Role(String ability) {
        this.ability = ability;
    }

    public Long getId() {
        return id;
    }

    public String getAbility() {
        return ability;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Role setAbility(String ability) {
        this.ability = ability;
        return this;
    }

    public Role setUsers(Set<User> users) {
        this.users = users;
        return this;
    }

    public Role setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }
}
