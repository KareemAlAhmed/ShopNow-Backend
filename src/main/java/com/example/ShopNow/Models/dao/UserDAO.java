package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserDAO {
    public ResponseEntity<?> save(User user);
    public List<User> getAllUser();
    public List<UserResponseDTO> getUsersSecured();
    public void updateCartWish( User data);
    public User getUserById(int id);
    public User getUserByField(String userField, Object data);
    public User updateUser(User user);
    public void updateUserAuthorities(String oldData,String field,String newData);

    class UserResponseDTO {
        private int id;
        private String username;
        private String email;
        private String phone;
        private float reviewRates;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public float getReviewRates() {
            return reviewRates;
        }

        public void setReviewRates(float reviewRates) {
            this.reviewRates = reviewRates;
        }

        // Static factory method
        public static UserResponseDTO fromEntity(User user) {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setPhone(user.getPhone());
            dto.setReviewRates(user.getReviewRates());
            dto.setEmail(user.getEmail());
            return dto;
        }
    }
}
