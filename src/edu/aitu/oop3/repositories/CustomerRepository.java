package edu.aitu.oop3.repositories;
import edu.aitu.oop3.entities.Customer;

public interface CustomerRepository {
    Customer findByEmail(String email);
}

