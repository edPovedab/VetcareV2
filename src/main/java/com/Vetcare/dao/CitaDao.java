
package com.Vetcare.dao;

/**
 *
 * @author Eduardo
 */


import com.Vetcare.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaDao extends JpaRepository<Cita, Integer> {
    
}
