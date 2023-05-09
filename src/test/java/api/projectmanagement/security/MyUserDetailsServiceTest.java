package api.projectmanagement.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class MyUserDetailsServiceTest {
    MyUserDetailsService myUserDetailsService;
    BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new BCryptPasswordEncoder(12);
        myUserDetailsService = new MyUserDetailsService(encoder);
    }

    @Test
    public void testLoadUserByUsername(){
        String rawPassword = "admin";
        String encodedPassword = encoder.encode(rawPassword);
        User user = new User("admin", encodedPassword, "ROLE_ADMIN");
        UserPrincipal userPrincipal = new UserPrincipal(user);
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("admin");
        SimpleGrantedAuthority roleFromUserDetails = (SimpleGrantedAuthority) userDetails.getAuthorities().toArray()[0];
        SimpleGrantedAuthority roleFromUserPrincipal = (SimpleGrantedAuthority) userPrincipal.getAuthorities().toArray()[0];

        assertThat(userDetails.getUsername()).isSameAs(userPrincipal.getUsername());
        assertThat(roleFromUserDetails.getAuthority()).isSameAs(roleFromUserPrincipal.getAuthority());
        assertThat(encoder.matches(rawPassword, userPrincipal.getPassword())).isTrue();
        assertThat(encoder.matches(rawPassword, userDetails.getPassword())).isTrue();
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
    }

}
