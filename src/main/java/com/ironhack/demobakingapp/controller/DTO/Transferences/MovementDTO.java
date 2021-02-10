package com.ironhack.demobakingapp.controller.DTO.Transferences;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MovementDTO {

    /** PARAMS **/

    private Long senderAccountId;
    @NotNull(message = "You need to provide a name for the receiver")
    @NotEmpty
    private String receiverName;
    private String concept;
    @NotNull(message = "You need to provide a destination account ID")
    @NotEmpty
    private Long receiverAccountId;
    @NotNull(message = "The amount you need to transfer cannot be null")
    @NotEmpty
    private BigDecimal amount;

    /** CONSTRUCTORS **/

    /** Between Account Holders **/
    public MovementDTO(Long senderAccountId, String receiverName, Long receiverAccountId, BigDecimal amount) {
        this.senderAccountId = senderAccountId;
        this.receiverName = receiverName;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }
    /** With Third Parties **/
    public MovementDTO(String receiverName, String concept, Long receiverAccountId, BigDecimal amount) {
        this.receiverName = receiverName;
        this.concept = concept;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }

    /** GETTERS & SETTERS **/

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
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

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
