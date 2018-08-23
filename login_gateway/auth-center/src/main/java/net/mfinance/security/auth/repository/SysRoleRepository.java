package net.mfinance.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.mfinance.security.auth.domain.SysRole;


public interface SysRoleRepository extends JpaRepository<SysRole, Long> {

}
