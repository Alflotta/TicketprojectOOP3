package edu.aitu.oop3.services;
import edu.aitu.oop3.entities.Customer;
import edu.aitu.oop3.exceptions.AuthenticationException;
import edu.aitu.oop3.repositories.CustomerRepository;

public class AuthService {

    private final CustomerRepository repo;

    public AuthService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer login(String email, String password) {
        Customer customer = repo.findByEmail(email);

        if (customer == null)
            throw new AuthenticationException("User not found");

        if (!customer.getPassword().equals(password))
            throw new AuthenticationException("Wrong password");

        return customer;
    }
}

