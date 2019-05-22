package com.ak.be.engine.service.user.impl

import com.ak.be.engine.auth.UserDetailsDefault
import com.ak.be.engine.db.entity.AkUserEntity
import com.ak.be.engine.db.repository.AuthorityRepository
import com.ak.be.engine.db.repository.UserRepository
import com.ak.be.engine.service.model.Restaurant
import com.ak.be.engine.service.model.User
import com.ak.be.engine.service.model.fromEntity
import com.ak.be.engine.service.user.UserService
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceDefault(val userRepository: UserRepository,
                         val passwordEncoder: PasswordEncoder,
                         val authorityRepository: AuthorityRepository) : UserService {

    @Transactional
    override fun createUser(email: String, password: String, firstName: String, lastName: String): User {
        val passwordHash = passwordEncoder.encode(password)
        val defaultAuthorityOpt = authorityRepository.findById(1)
        val defaultAuthority = if (defaultAuthorityOpt.isPresent) defaultAuthorityOpt.get() else throw IllegalArgumentException("defaultAuthority not found")

        val akUserEntity = AkUserEntity()
        akUserEntity.email = email
        akUserEntity.passwordHash = passwordHash
        akUserEntity.authorities = Arrays.asList(defaultAuthority)
        akUserEntity.firstName = firstName
        akUserEntity.lastName = lastName
        val saved = userRepository.save(akUserEntity)
        return User.fromEntity(saved)
    }

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

    override fun getRestaurantsByUserId(userId: Int): List<Restaurant> {
        val userById = userRepository.findById(userId)
        val akUserEntity = if (userById.isPresent) userById.get() else throw IllegalArgumentException("user not found")
        return akUserEntity.restaurants.map { Restaurant.fromEntity(it) }
    }

}

