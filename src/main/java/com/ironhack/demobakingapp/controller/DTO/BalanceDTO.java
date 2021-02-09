package com.ironhack.demobakingapp.controller.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

public class BalanceDTO {
    @NotNull
    @NotEmpty
    private Long accountId;
    @NotEmpty
    @NotNull
    private BigDecimal balanceAmount;
    @NotEmpty
    private Currency balanceCurrency;

    public BalanceDTO() {
    }

    public BalanceDTO(@NotNull @NotEmpty Long accountId, @NotEmpty @NotNull BigDecimal balanceAmount, @NotEmpty Currency balanceCurrency) {
        setAccountId(accountId);
        setBalanceAmount(balanceAmount);
        setBalanceCurrency(balanceCurrency);
    }

    public Long getAccountId() {return accountId;}
    public void setAccountId(Long accountId) {this.accountId = accountId;}
    public BigDecimal getBalanceAmount() {return balanceAmount;}
    public void setBalanceAmount(BigDecimal balanceAmount) {this.balanceAmount = balanceAmount;}
    public Currency getBalanceCurrency() {return balanceCurrency;}
    public void setBalanceCurrency(Currency balanceCurrency) {this.balanceCurrency = balanceCurrency;}
}
