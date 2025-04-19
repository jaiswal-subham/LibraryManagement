package com.order_service.project.domain.models;

import com.order_service.project.domain.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import java.util.Set;

public class CreateOrderRequest {
    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    private Set<OrderItem> items;

    @NotBlank(message = "Customer Name is required")
    private String name;

    @NotBlank(message = "Customer email is required")
    @Email
    private String email;

    @NotBlank(message = "Customer phone is required")
    private String phone;

    @NotBlank(message = "AddressLine1 is required")
    String addressLine1;

    @NotBlank(message = "AddressLine2 is required")
    String addressLine2;

    @NotBlank(message = "City is required")
    String city;

    @NotBlank(message = "State is required")
    String state;

    @NotBlank(message = "ZipCode is required")
    String zipCode;

    @NotBlank(message = "Country is required")
    String country;



}
