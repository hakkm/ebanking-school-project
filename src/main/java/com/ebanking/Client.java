package com.ebanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private static Integer counter = 0;

    private int numClient;
    private String nom;
    private String prenom;
    private String adresse;
    private String phone;
    private String email;
    private List<Compte> comptes;

    public Client(String nom, String prenom, String adresse, String phone, String email, List<Compte> comptes) {
        this.numClient = counter;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.phone = phone;
        this.email = email;
        this.comptes = new ArrayList<>();
        counter++;
    }

    public List<Compte> getComptes() {
        return new ArrayList<>(comptes);
    }

    public int getNumClient() {
        return numClient;
    }

    public void addCompte(Compte compte) {
        this.comptes.add(compte);
    }

    @Override
    public String toString() {
        return "Client{" +
                "numClient='" + numClient + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
//                ", comptes=" + comptes +
                '}';
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this); // Converts this object to JSON
        } catch (JsonProcessingException e) {
            System.out.println("An error occurred while converting this object to JSON.");
            return null;
        }
    }
}
