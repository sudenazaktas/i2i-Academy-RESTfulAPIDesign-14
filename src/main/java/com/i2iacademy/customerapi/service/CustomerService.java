package com.i2iacademy.customerapi.service;

import com.i2iacademy.customerapi.dto.CustomerRequestDTO;
import com.i2iacademy.customerapi.dto.CustomerResponseDTO;
import com.i2iacademy.customerapi.entity.Customer;
import com.i2iacademy.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Contains the business logic for Customer operations.
// Acts as the bridge between the Controller (DTOs) and the Repository (Entities).
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Constructor injection (preferred over field injection for testability)
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Creates a new customer from the incoming request DTO
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        Customer customer = new Customer(
                requestDTO.getFirstName(),
                requestDTO.getLastName(),
                requestDTO.getEmail(),
                requestDTO.getPhoneNumber()
        );

        Customer savedCustomer = customerRepository.save(customer);
        return mapToResponseDTO(savedCustomer);
    }

    // Retrieves a single customer by id, throws an exception if not found
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return mapToResponseDTO(customer);
    }

    // Retrieves all customers
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Updates an existing customer's fields
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setFirstName(requestDTO.getFirstName());
        customer.setLastName(requestDTO.getLastName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPhoneNumber(requestDTO.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(customer);
        return mapToResponseDTO(updatedCustomer);
    }

    // Deletes a customer by id
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    // Helper method to convert an Entity into a Response DTO
    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}