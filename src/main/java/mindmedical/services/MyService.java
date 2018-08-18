package mindmedical.services;

import mindmedical.entities.User;
import com.naturalprogrammer.spring.lemon.LemonService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.util.LemonUtils;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MyService extends LemonService<User, Long> {

    @Override
    public User newUser() {
        return new User();
    }

    @Override
    protected Long toId(String id) {
        return Long.valueOf(id);
    }

    // For extracting name when a user does Google or Facebook signup
    @Override
    public void fillAdditionalFields(String registrationId, User user, Map<String, Object> attributes) {

        String nameKey;

        switch (registrationId) {

            case "facebook":
                nameKey = StandardClaimNames.NAME;
                break;

            case "google":
                nameKey = StandardClaimNames.NAME;
                break;

            default:
                throw new UnsupportedOperationException("Fetching name from " + registrationId + " login not supported");
        }

        user.setName((String) attributes.get(nameKey));
    }

    // For updating name when updating profile,
    @Override
    protected void updateUserFields(User user, User updatedUser, UserDto currentUser) {

        super.updateUserFields(user, updatedUser, currentUser);

        user.setName(updatedUser.getName());

        LemonUtils.afterCommit(() -> {
            if (currentUser.getId().equals(user.getId())) {
                currentUser.setTag(user.toTag());
            }
        });
    }

    @Override
    protected User createAdminUser() {

        User user = super.createAdminUser();
        user.setName("Administrator");
        return user;
    }
}