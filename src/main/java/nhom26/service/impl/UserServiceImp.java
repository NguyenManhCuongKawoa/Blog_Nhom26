package nhom26.service.impl;

import nhom26.model.Provider;
import nhom26.model.User;
import nhom26.repository.RoleRepository;
import nhom26.repository.UserRepository;
import nhom26.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_ROLE = "ROLE_USER";

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        // Encode plaintext password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setProvider(Provider.LOCAL);
        // Set Role to ROLE_USER
        user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
        return userRepository.saveAndFlush(user);
    }

	@Override 
	public User update(User user) {
		Optional<User> uExisted = findByUsername(user.getUsername());
		uExisted.get().setEmail(user.getEmail());
		uExisted.get().setName(user.getName());
		uExisted.get().setLastName(user.getLastName());

		return userRepository.saveAndFlush(uExisted.get());
	}
}
