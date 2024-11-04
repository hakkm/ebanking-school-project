package com.ebanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

enum Type {
    VIRIN,
    VIREST,
    VIRTMULTA,
    VIRCHAC,
}

public class Transaction {
    private final Type type;
    private final LocalDateTime timestamp;
    private final String reference;
    private final List<Compte> comptes;

    public Transaction(List<Compte> comptes) {
        this.timestamp = LocalDateTime.now();
        this.reference = generateTransactionReference();
        this.comptes = comptes;
        this.type = getTransactionType(comptes);
        // add transaction to each account
        for (Compte compte : comptes) {
            compte.addTransaction(this);
        }
    }

    private Type getTransactionType(List<Compte> comptes) {
        // VIRIN: 2 client, meme banque
        // VIREST: 2 client, banque differente
        // VIRTMULTA: plusieurs comptes, meme banque
        // VIRCHAC: Plusieurs clients, chaque client un compte

        if (comptes.size() == 2) {
            if (comptes.getFirst().getBanque() == comptes.getLast().getBanque()) {
                return Type.VIRIN;
            } else {
                return Type.VIREST;
            }
        } else if (comptes.size() > 2) {
            for (Compte compte : comptes) {
                if (compte.getBanque().equals(comptes.getFirst().getBanque())) {
                    return Type.VIRTMULTA;
                } else {
                    return Type.VIRCHAC;
                }
            }
        }
        return null;
    }

    private String generateTransactionReference() {
        return "TXN-" + UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                ", timestamp=" + timestamp +
                ", reference='" + reference + '\'' +
                ", comptes=" + comptes.stream().map(compte -> compte.getNumCompte()).collect(Collectors.toList()) +
                '}';
    }

    public String getReference() {
        return reference;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this); // Converts this object to JSON
        } catch (JsonProcessingException e) {
            System.out.println("An error occurred while converting this object to JSON");
            return null;
        }
    }
}
