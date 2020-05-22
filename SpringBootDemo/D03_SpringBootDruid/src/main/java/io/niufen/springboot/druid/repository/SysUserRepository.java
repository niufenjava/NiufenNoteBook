package io.niufen.springboot.druid.repository;


import io.niufen.springboot.druid.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author niufen
 */
public interface SysUserRepository extends JpaRepository<SysUserEntity, Long> {

    /**
     * @param username
     */
    void findByUsernameLikeAndPasswordLikeAndPhoneNotLike(String username, String password, String phone);
}

