package com.ironhack.demobakingapp.model.Users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ThirdParty {

    /** PARAMS **/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String hashKey;

    /** CONSTRUCTORS **/

    public ThirdParty() {
    }

    public ThirdParty(Long id, String name, String hashKey) {
        this.id = id;
        this.name = name;
        this.hashKey = hashKey;
    }

    /** GETTERS & SETTERS **/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
}
