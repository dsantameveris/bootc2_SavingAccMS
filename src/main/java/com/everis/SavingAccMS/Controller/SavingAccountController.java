package com.everis.SavingAccMS.Controller;

import com.everis.SavingAccMS.Service.Impl.SavingAccountServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.everis.SavingAccMS.DTO.SavingAccountDTO;
import com.everis.SavingAccMS.DTO.Movement.MoneyOperationDTO;
import com.everis.SavingAccMS.Model.SavingAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController()
@RequestMapping("/savingacc")
public class SavingAccountController
{
    @Autowired
    private SavingAccountServiceImpl service;

    //Get All Accounts
    @GetMapping("/all")
    public Flux<SavingAccount> getAllAccounts() 
    {
        return service.findAllAccounts();
    }

    //Get All Accounts (DTO)
    @GetMapping("/alldto")
    public Flux<SavingAccountDTO> getAllAccountsDTO() 
    {
        return service.findAllAccountsDTO();
    }

    //Get account by number
    @GetMapping("/number/{number}")
    public Mono<SavingAccount> getAccountByNumber(@PathVariable String number) 
    {
        return service.findByNumber(number);
    }

    //Get account by number (DTO)
    @GetMapping("/numberdto/{number}")
    public Mono<SavingAccountDTO> getAccountByNumberDTO(@PathVariable String number) 
    {
        return service.findByNumberDTO(number);
    }

    //Get accounts by owner Dni
    @GetMapping("/owner/{dni}")
    public Mono<SavingAccount> getAccountByOwnerDni(@PathVariable String dni) 
    {
        return service.findByOwnerDni(dni);
    }

    //Get accounts by owner Dni (DTO)
    @GetMapping("/ownerdto/{dni}")
    public Mono<SavingAccountDTO> getAccountByOwnerDTO(@PathVariable String dni) 
    {
        return service.findByOwnerDni(dni).map(account -> new SavingAccountDTO(account.getNumber(), account.getCurrency()));
    }

    //Create new Account
    @PostMapping
    public Mono<SavingAccount> createAccount(@RequestBody SavingAccount account) {
        return service.addAccount(account);
    }

    //Update account
    @PutMapping("/edit/{number}")
    public Mono<SavingAccount> editAccount(@PathVariable String number, @RequestBody SavingAccount account)
    {
        return service.updateAccount(number, account);
    }

    //Delete Account
    @DeleteMapping("/delete/{number}")
    public Mono<Void> deleteAccount(@PathVariable String number)
    {
        return service.findByNumber(number).flatMap(a -> service.delAccount(a));
    }

    //Deposit amount
    @PutMapping("/deposit")
    public Mono<MoneyOperationDTO> deposit(@RequestBody MoneyOperationDTO deposit)
    {
        return service.deposit(deposit);        
    }
}