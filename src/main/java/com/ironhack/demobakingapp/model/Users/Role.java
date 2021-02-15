package com.ironhack.demobakingapp.model.Users;

import com.ironhack.demobakingapp.enums.UserRole;
import javax.persistence.*;

@Entity
public class Role {

    /** PARAMS **/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    /** CONSTRUCTORS **/

    public Role() {
    }

    public Role(UserRole userRole, User user) {
        setUserRole(userRole);
        setUser(user);
    }

    public Role(UserRole userRole){
        setUserRole(userRole);
    }

    /** GETTERS & SETTERS **/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
