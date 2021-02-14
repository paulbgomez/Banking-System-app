package com.ironhack.demobakingapp.controller.DTO.Users;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ironhack.demobakingapp.classes.Address;
import com.ironhack.demobakingapp.controller.DTO.Users.UserDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class AccountHolderDTO extends UserDTO {

    /** PARAMS **/
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @NotNull(message = "Primary Address can't be null.")
    private AddressDTO primaryAddressDTO;

    private AddressDTO mailingAddressDTO;

    /** CONSTRUCTORS **/
    public AccountHolderDTO() {
    }

    public AccountHolderDTO( @NotNull String name,  @NotNull String username,  @NotNull String password) {
        super(name, username, password);
    }

    public AccountHolderDTO( @NotNull String name,  @NotNull String username,  @NotNull String password, LocalDate birthDate, @NotNull(message = "Primary Address can't be null.") AddressDTO primaryAddressDTO) {
        super(name, username, password);
        setBirthDate(birthDate);
        setPrimaryAddressDTO(primaryAddressDTO);
    }

    public AccountHolderDTO( @NotNull String name,  @NotNull String username,  @NotNull String password, LocalDate birthDate, @NotNull(message = "Primary Address can't be null.") AddressDTO primaryAddressDTO, AddressDTO mailingAddressDTO) {
        super(name, username, password);
        setBirthDate(birthDate);
        setPrimaryAddressDTO(primaryAddressDTO);
        setMailingAddressDTO(mailingAddressDTO);
    }

    /** GETTERS & SETTERS **/
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public AddressDTO getPrimaryAddressDTO() {
        return primaryAddressDTO;
    }

    public void setPrimaryAddressDTO(AddressDTO primaryAddressDTO) {
        this.primaryAddressDTO = primaryAddressDTO;
    }

    public AddressDTO getMailingAddressDTO() {
        return mailingAddressDTO;
    }

    public void setMailingAddressDTO(AddressDTO mailingAddressDTO) {
        this.mailingAddressDTO = mailingAddressDTO;
    }
}
