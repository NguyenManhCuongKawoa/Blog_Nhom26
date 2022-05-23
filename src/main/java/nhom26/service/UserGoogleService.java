package nhom26.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nhom26.model.Provider;
import nhom26.model.User;
import nhom26.repository.RoleRepository;
import nhom26.repository.UserRepository;

@Service
public class UserGoogleService {
	
	@Autowired
	private UserRepository userRepository;
    @Autowired
	private RoleRepository roleRepository;
    
    private static final String USER_ROLE = "ROLE_USER";

	public void processOAuthPostLogin(String username, String name) {
        Optional<User> existUser = userRepository.findByUsername(username);
         
        if (!existUser.isPresent()) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(username);
            newUser.setName(name);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setActive(1);        
            newUser.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
             
            userRepository.save(newUser);        
        }
         
    }

}
