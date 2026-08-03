package com.ecommerce.service;

import com.ecommerce.dto.request.UpdateUserRequest;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service pour la gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    /**
     * Récupérer un utilisateur par ID
     */
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
        return userMapper.toResponse(user);
    }

    /**
     * Récupérer un utilisateur par email
     */
    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "email", email));
        return userMapper.toResponse(user);
    }

    /**
     * Mettre à jour un utilisateur
     */
    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        userMapper.updateEntity(request, user);
        user = userRepository.save(user);

        log.info("Utilisateur mis à jour: {}", user.getEmail());

        return userMapper.toResponse(user);
    }

    /**
     * Bloquer un utilisateur
     */
    @Transactional
    public void block(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        user.setBlocked(true);
        userRepository.save(user);

        log.info("Utilisateur bloqué: {}", user.getEmail());
    }

    /**
     * Débloquer un utilisateur
     */
    @Transactional
    public void unblock(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        user.setBlocked(false);
        userRepository.save(user);

        log.info("Utilisateur débloqué: {}", user.getEmail());
    }

    /**
     * Supprimer un utilisateur
     */
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));

        userRepository.delete(user);

        log.info("Utilisateur supprimé: {}", user.getEmail());
    }

    /**
     * Rechercher des utilisateurs
     */
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> search(String query, int page, int size) {
        Page<User> users = userRepository.searchUsers(query, PageRequest.of(page, size));
        return PageResponse.of(users.map(userMapper::toResponse));
    }

    /**
     * Récupérer tous les utilisateurs paginés
     */
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAll(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return PageResponse.of(users.map(userMapper::toResponse));
    }

    /**
     * Récupérer les utilisateurs bloqués
     */
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getBlockedUsers(int page, int size) {
        Page<User> users = userRepository.findBlockedUsers(PageRequest.of(page, size));
        return PageResponse.of(users.map(userMapper::toResponse));
    }

    /**
     * Obtenir l'historique des commandes d'un utilisateur
     */
    @Transactional(readOnly = true)
    public List<Object[]> getOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId).stream()
            .map(order -> new Object[]{
                order.getOrderNumber(),
                order.getTotal(),
                order.getStatus(),
                order.getCreatedAt()
            })
            .toList();
    }

    /**
     * Obtenir le montant total dépensé par un utilisateur
     */
    @Transactional(readOnly = true)
    public BigDecimal getTotalSpent(Long userId) {
        return orderRepository.findByUserId(userId).stream()
            .filter(order -> order.getStatus() != com.ecommerce.entity.EOrderStatus.CANCELLED)
            .map(order -> order.getTotal())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Compter les utilisateurs actifs
     */
    @Transactional(readOnly = true)
    public long countActiveUsers() {
        return userRepository.countActiveUsers();
    }
}
