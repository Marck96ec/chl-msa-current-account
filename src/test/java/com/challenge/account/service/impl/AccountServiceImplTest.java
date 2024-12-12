package com.challenge.account.service.impl;

import com.challenge.account.domain.db.Account;
import com.challenge.account.domain.customer.Person;
import com.challenge.account.repository.AccountRepository;
import com.challenge.account.repository.MovementRepository;
import com.challenge.account.server.models.CustomerPerson;
import com.challenge.account.server.models.CustomerPersonResponse;
import com.challenge.account.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {


    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_createsCustomerSuccessfully() {
        CustomerPerson customerPerson = new CustomerPerson();
        Person person = new Person();
        Account account = new Account();
        CustomerPersonResponse response = new CustomerPersonResponse();

        when(customerMapper.toPersonCreate(any(CustomerPerson.class))).thenReturn(person);
        when(movementRepository.save(any(Person.class))).thenReturn(Mono.just(person));
        when(customerMapper.toCustomerCreate(any(Person.class), any(CustomerPerson.class))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));
        when(customerMapper.toCustomerPersonResponse(any(Account.class), any(Person.class))).thenReturn(response);

        Mono<CustomerPersonResponse> result = accountServiceImpl.createCustomer(customerPerson);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(response, res);
                })
                .verifyComplete();
    }

    @Test
    void createCustomer_handlesPersonRepositorySaveError() {
        CustomerPerson customerPerson = new CustomerPerson();
        Person person = new Person();

        when(customerMapper.toPersonCreate(any(CustomerPerson.class))).thenReturn(person);
        when(movementRepository.save(any(Person.class))).thenReturn(Mono.error(new RuntimeException("Save error")));

        Mono<CustomerPersonResponse> result = accountServiceImpl.createCustomer(customerPerson);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void createCustomer_handlesCustomerRepositorySaveError() {
        CustomerPerson customerPerson = new CustomerPerson();
        Person person = new Person();
        Account account = new Account();

        when(customerMapper.toPersonCreate(any(CustomerPerson.class))).thenReturn(person);
        when(movementRepository.save(any(Person.class))).thenReturn(Mono.just(person));
        when(customerMapper.toCustomerCreate(any(Person.class), any(CustomerPerson.class))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.error(new RuntimeException("Save error")));

        Mono<CustomerPersonResponse> result = accountServiceImpl.createCustomer(customerPerson);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void updateCustomer_updatesCustomerSuccessfully() {
        Integer id = 1;
        CustomerPerson customerPerson = new CustomerPerson();
        Person person = new Person();
        person.setPerson_id(1L);
        Person updatedPerson = new Person();
        updatedPerson.setPerson_id(1L);
        Account account = new Account();
        Account updatedAccount = new Account();
        CustomerPersonResponse response = new CustomerPersonResponse();

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(customerMapper.toPersonCreate(any(CustomerPerson.class))).thenReturn(updatedPerson);
        when(movementRepository.save(any(Person.class))).thenReturn(Mono.just(updatedPerson));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.just(account));
        when(customerMapper.toCustomerUpdate(any(Person.class), any(CustomerPerson.class), any(Account.class))).thenReturn(updatedAccount);
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(updatedAccount));
        when(customerMapper.toCustomerPersonResponse(any(Account.class), any(Person.class))).thenReturn(response);

        Mono<CustomerPersonResponse> result = accountServiceImpl.updateCustomer(id, customerPerson);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(response, res);
                })
                .verifyComplete();
    }


    @Test
    void updateCustomer_handlesPersonRepositorySaveError() {
        Integer id = 1;
        CustomerPerson customerPerson = new CustomerPerson();
        Person person = new Person();
        person.setPerson_id(1L);
        Person updatedPerson = new Person();
        updatedPerson.setPerson_id(1L);

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(customerMapper.toPersonCreate(any(CustomerPerson.class))).thenReturn(updatedPerson);
        when(movementRepository.save(any(Person.class))).thenReturn(Mono.error(new RuntimeException("Save error")));

        Mono<CustomerPersonResponse> result = accountServiceImpl.updateCustomer(id, customerPerson);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void updateCustomer_handlesCustomerRepositorySaveError() {
        Integer id = 1;
        CustomerPerson customerPerson = new CustomerPerson();
        Person person = new Person();
        person.setPerson_id(1L);
        Person updatedPerson = new Person();
        updatedPerson.setPerson_id(1L);
        Account account = new Account();
        Account updatedAccount = new Account();

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(customerMapper.toPersonCreate(any(CustomerPerson.class))).thenReturn(updatedPerson);
        when(movementRepository.save(any(Person.class))).thenReturn(Mono.just(updatedPerson));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.just(account));
        when(customerMapper.toCustomerUpdate(any(Person.class), any(CustomerPerson.class), any(Account.class))).thenReturn(updatedAccount);
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.error(new RuntimeException("Save error")));

        Mono<CustomerPersonResponse> result = accountServiceImpl.updateCustomer(id, customerPerson);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void getAllCustomers_returnsAllCustomersSuccessfully() {
        Person person1 = new Person();
        person1.setPerson_id(1L);
        Person person2 = new Person();
        person2.setPerson_id(2L);
        Account account1 = new Account();
        Account account2 = new Account();
        CustomerPersonResponse response1 = new CustomerPersonResponse();
        CustomerPersonResponse response2 = new CustomerPersonResponse();

        when(movementRepository.findAll()).thenReturn(Flux.just(person1, person2));
        when(accountRepository.findByPersonId(1L)).thenReturn(Mono.just(account1));
        when(accountRepository.findByPersonId(2L)).thenReturn(Mono.just(account2));
        when(customerMapper.toCustomerPersonResponse(account1, person1)).thenReturn(response1);
        when(customerMapper.toCustomerPersonResponse(account2, person2)).thenReturn(response2);

        Flux<CustomerPersonResponse> result = accountServiceImpl.getAllCustomers();

        StepVerifier.create(result)
                .expectNext(response1)
                .expectNext(response2)
                .verifyComplete();
    }

    @Test
    void getAllCustomers_handlesEmptyPersonRepository() {
        when(movementRepository.findAll()).thenReturn(Flux.empty());

        Flux<CustomerPersonResponse> result = accountServiceImpl.getAllCustomers();

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getAllCustomers_handlesCustomerNotFound() {
        Person person = new Person();
        person.setPerson_id(1L);

        when(movementRepository.findAll()).thenReturn(Flux.just(person));
        when(accountRepository.findByPersonId(1L)).thenReturn(Mono.empty());

        Flux<CustomerPersonResponse> result = accountServiceImpl.getAllCustomers();

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getCustomerById_returnsCustomerSuccessfully() {
        Integer id = 1;
        Person person = new Person();
        person.setPerson_id(1L);
        Account account = new Account();
        CustomerPersonResponse response = new CustomerPersonResponse();

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.just(account));
        when(customerMapper.toCustomerPersonResponse(any(Account.class), any(Person.class))).thenReturn(response);

        Mono<CustomerPersonResponse> result = accountServiceImpl.getCustomerById(id);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertNotNull(res);
                    assertEquals(response, res);
                })
                .verifyComplete();
    }

    @Test
    void getCustomerById_handlesPersonNotFound() {
        Integer id = 1;

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.empty());

        Mono<CustomerPersonResponse> result = accountServiceImpl.getCustomerById(id);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void getCustomerById_handlesCustomerNotFound() {
        Integer id = 1;
        Person person = new Person();
        person.setPerson_id(1L);

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.empty());

        Mono<CustomerPersonResponse> result = accountServiceImpl.getCustomerById(id);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteCustomer_deletesCustomerSuccessfully() {
        Integer id = 1;
        Person person = new Person();
        person.setPerson_id(1L);
        Account account = new Account();

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.just(account));
        when(accountRepository.delete(any(Account.class))).thenReturn(Mono.empty());
        when(movementRepository.delete(any(Person.class))).thenReturn(Mono.empty());

        Mono<Void> result = accountServiceImpl.deleteCustomer(id);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteCustomer_handlesPersonNotFound() {
        Integer id = 1;

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.empty());

        Mono<Void> result = accountServiceImpl.deleteCustomer(id);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteCustomer_handlesCustomerNotFound() {
        Integer id = 1;
        Person person = new Person();
        person.setPerson_id(1L);

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.empty());

        Mono<Void> result = accountServiceImpl.deleteCustomer(id);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteCustomer_handlesCustomerRepositoryDeleteError() {
        Integer id = 1;
        Person person = new Person();
        person.setPerson_id(1L);
        Account account = new Account();

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.just(account));
        when(accountRepository.delete(any(Account.class))).thenReturn(Mono.error(new RuntimeException("Delete error")));

        Mono<Void> result = accountServiceImpl.deleteCustomer(id);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void deleteCustomer_handlesPersonRepositoryDeleteError() {
        Integer id = 1;
        Person person = new Person();
        person.setPerson_id(1L);
        Account account = new Account();

        when(movementRepository.findById(any(Long.class))).thenReturn(Mono.just(person));
        when(accountRepository.findByPersonId(any(Long.class))).thenReturn(Mono.just(account));
        when(accountRepository.delete(any(Account.class))).thenReturn(Mono.empty());
        when(movementRepository.delete(any(Person.class))).thenReturn(Mono.error(new RuntimeException("Delete error")));

        Mono<Void> result = accountServiceImpl.deleteCustomer(id);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

}