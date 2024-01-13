package com.winewizard.winewizard.model;

import jakarta.persistence.*;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "feedback")
public class Feedback {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        String name;

        String email;

        String message;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        String type;

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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
}
