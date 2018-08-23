package net.mfinance.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.mfinance.security.auth.domain.SysUser;


public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    SysUser findByUsername(String username);

    SysUser findByUsernameAndPassword(String name, String password);
}
