package com.ironhack.demobakingapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ironhack.demobakingapp.classes.Money;
import com.ironhack.demobakingapp.model.Accounts.Account;
import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
public class Movement {

    /** PARAMS **/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_account")
    private Account senderAccount;
    @ManyToOne
    @JoinColumn(name = "receiver_account")
    private Account receiverAccount;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "quantity_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "quantity_currency"))
    })
    private Money quantity;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime transferenceDate;
    private String concept;

    /** CONSTRUCTORS **/

    public Movement() {
    }

    /** Constructor for regular movements between account holders **/
    public Movement(Account senderAccount, Account receiverAccount, Money quantity) {
        setSenderAccount(senderAccount);
        setReceiverAccount(receiverAccount);
        setQuantity(quantity);
        setTransferenceDate(LocalDateTime.now());
    }

    /** Constructor for third party movements  **/
    public Movement(Money quantity, String concept) {
        setQuantity(quantity);
        setConcept(concept);
        setTransferenceDate(LocalDateTime.now());
    }

    /** GETTERS & SETTERS **/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public Money getQuantity() {
        return quantity;
    }

    public void setQuantity(Money quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTransferenceDate() {
        return transferenceDate;
    }

    public void setTransferenceDate(LocalDateTime transferenceDate) {
        this.transferenceDate = transferenceDate;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }
}
