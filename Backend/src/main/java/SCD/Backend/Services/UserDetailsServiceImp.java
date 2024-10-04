package SCD.Backend.Services;

import SCD.Backend.Repositories.CourierRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final CourierRepository repository;

    public UserDetailsServiceImp(CourierRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    }
}
