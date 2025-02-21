package flight_booking.demo.domain.user.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import flight_booking.demo.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

	@Id
	private String id;

	@Column(length = 20)
	private String name;

	@NotBlank
	@Email
	@Column(length = 100, unique = true)
	private String email;

	private String profile_url;

	@Column(length = 255)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberShip membership = MemberShip.BASIC;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.CUSTOMER;

	@Builder
	public User(String name, String email, String password, MemberShip membership) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.membership = membership;
	}

	public User(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.membership = user.getMembership();
		this.profile_url = user.getProfile_url();
		this.role = user.getRole();
	}

	public void updateMembership(MemberShip newMembershipType) {
		if (newMembershipType != null && !this.membership.equals(newMembershipType)) {
			this.membership = newMembershipType;
		}
	}

	public void updateRole(Role newRole) {
		if (newRole != null && !this.role.equals(newRole)) {
			this.role = newRole;
		}
	}

	@Override
	public String getUsername() {
		return email;
	}

	//해당 User의 권한을 리턴하는곳!!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("user"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
