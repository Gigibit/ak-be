package com.ak.be.engine.service.user.impl

import com.ak.be.engine.auth.UserDetailsDefault
import com.ak.be.engine.db.repository.UserRepository
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.model.fromEntity
import com.ak.be.engine.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceDefault(@Autowired val userRepository: UserRepository) : UserService {

    @Transactional
    override fun createUserDetails(username: String?): UserDetails {
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