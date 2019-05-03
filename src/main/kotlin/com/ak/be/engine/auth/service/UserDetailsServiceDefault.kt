package com.ak.be.engine.auth.service

import com.ak.be.engine.db.repository.UserRepository
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.model.fromEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

class UserDetailsServiceDefault(@Autowired val userService: UserService) : UserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the `UserDetails`
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     *
     * @return a fully populated user record (never `null`)
     *
     * @throws #UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */


    override fun loadUserByUsername(username: String?): UserDetails {
        return userService.createUserDetails(username)
    }


}

@Service
class UserService(@Autowired val userRepository: UserRepository) {

    @Transactional
    fun createUserDetails(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("userName is null")
        }

        val userEntity = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("$username not found")
        val user = User.fromEntity(userEntity)

        val userDetails = UserDetailsDefault(user)
        AccountStatusUserDetailsChecker().check(userDetails)

        return userDetails
    }
}