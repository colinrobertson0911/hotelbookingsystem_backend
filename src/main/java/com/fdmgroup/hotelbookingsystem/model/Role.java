package com.fdmgroup.hotelbookingsystem.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
	@SequenceGenerator(name = "role_gen", sequenceName = "ROLE_SEQ", allocationSize = 1)
	private long roleId;
	
	@Column
	private String roleName;

	@Override
	public String getAuthority() {
		return roleName;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;
		Role role = (Role) o;
		return getRoleId() == role.getRoleId() &&
				getRoleName().equals(role.getRoleName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoleId(), getRoleName());
	}

	@Override
	public String toString() {
		return "Role{" +
				"roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				'}';
	}
}
