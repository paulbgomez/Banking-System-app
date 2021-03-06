package com.ironhack.demobakingapp.controller.DTO.Transferences;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

public class BalanceDTO {

    /** PARAMS **/

    @NotNull
    private Long accountId;
    @NotNull
    private BigDecimal balanceAmount;
    @NotNull
    private Currency balanceCurrency;

    /** CONSTRUCTORS **/

    public BalanceDTO() {
    }

    public BalanceDTO(@NotNull Long accountId, @NotNull BigDecimal balanceAmount, @NotNull Currency balanceCurrency) {
        setAccountId(accountId);
        setBalanceAmount(balanceAmount);
        setBalanceCurrency(balanceCurrency);
    }

    /** GETTERS & SETTERS **/

    public Long getAccountId() {return accountId;}
    public void setAccountId(Long accountId) {this.accountId = accountId;}
    public BigDecimal getBalanceAmount() {return balanceAmount;}
    public void setBalanceAmount(BigDecimal balanceAmount) {this.balanceAmount = balanceAmount;}
    public Currency getBalanceCurrency() {return balanceCurrency;}
    public void setBalanceCurrency(Currency balanceCurrency) {this.balanceCurrency = balanceCurrency;}
}
