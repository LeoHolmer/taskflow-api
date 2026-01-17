package com.taskflow.api.model;

import com.taskflow.api.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;

/**
 * Entidad User representa a los usuarios del sistema.
 * 
 * Implementa:
 * - Soft delete vía @Where(clause = "deleted_at IS NULL")
 * - Roles basados en enumeración (USER, ADMIN)
 * - Relación 1-N con Task (un usuario puede tener múltiples tareas)
 * 
 * @author Leonardo Holmer
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "deleted_at IS NULL")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    /**
     * Email único - índice implícito por UNIQUE constraint
     * Validado en DTO con @Email annotation
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Password siempre encriptado con BCrypt.
     * NUNCA retornar en responses.
     */
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    // Relación lazy (default para OneToMany) - se carga solo si se accede explícitamente
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
