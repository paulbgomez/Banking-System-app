package com.ironhack.demobakingapp.controller.DTO.Transferences;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MovementDTO {

    /** PARAMS **/

    private Long senderAccount;
    @NotNull(message = "You need to provide a name for the receiver")
    private String receiverName;
    private String concept;
    @NotNull(message = "You need to provide a destination account ID")
    private Long receiverAccount;
    @NotNull(message = "The amount you need to transfer cannot be null")
    private BigDecimal amount;

    /** CONSTRUCTORS **/

    public MovementDTO() {
    }

    /** Between Account Holders **/
    public MovementDTO(Long senderAccount, @NotNull(message = "You need to provide a name for the receiver")String receiverName, @NotNull(message = "You need to provide a destination account ID")Long receiverAccount, @NotNull(message = "The amount you need to transfer cannot be null")  BigDecimal amount) {
        setSenderAccount(senderAccount);
        setReceiverName(receiverName);
        setReceiverAccount(receiverAccount);
        setAmount(amount);
    }

    /** With Third Parties **/
//    public MovementDTO(@NotNull(message = "You need to provide a name for the receiver") @NotEmpty String receiverName, String concept, @NotNull(message = "You need to provide a destination account ID") @NotEmpty Long receiverAccount, @NotNull(message = "The amount you need to transfer cannot be null") @NotEmpty BigDecimal amount) {
//        this.receiverName = receiverName;
//        this.concept = concept;
//        this.receiverAccount = receiverAccount;
//        this.amount = amount;
//    }

    /** GETTERS & SETTERS **/

    public Long getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Long senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Long getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Long receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
