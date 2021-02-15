package com.ironhack.demobakingapp.controller.DTO.Accounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.demobakingapp.enums.Status;
import java.time.LocalDateTime;

public class AccountDTO {

        /** PARAMS **/

        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        protected LocalDateTime creationTime;
        protected boolean isFrozen;
        protected Status status;


        /** CONSTRUCTORS **/

        public AccountDTO(LocalDateTime creationTime) {
            setFrozen(false);
            setCreationTime(LocalDateTime.now());
            setStatus(Status.ACTIVE);
        }

        /** GETTERS & SETTERS **/

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void blockAccount(){
        if(isFrozen){
            this.setStatus(Status.FROZEN);
        }
    }
}
