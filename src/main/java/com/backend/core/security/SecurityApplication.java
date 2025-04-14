package com.backend.core.security;

import com.backend.core.security.persistence.entity.PermissionEntity;
import com.backend.core.security.persistence.entity.RoleEntity;
import com.backend.core.security.persistence.entity.RoleEnum;
import com.backend.core.security.persistence.entity.UserEntity;
import com.backend.core.security.persistence.reporitory.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args -> {
			PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
			PermissionEntity readPermission = PermissionEntity.builder().name("READ").build();
			PermissionEntity updatePermission = PermissionEntity.builder().name("UPDATE").build();
			PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE").build();
			PermissionEntity refactorPermission = PermissionEntity.builder().name("REFACTOR").build();

			RoleEntity roleAdmin = RoleEntity.builder().roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission,readPermission,updatePermission,deletePermission))
					.build();
			RoleEntity roleUser = RoleEntity.builder().roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission,readPermission))
					.build();
			RoleEntity roleInvited = RoleEntity.builder().roleEnum(RoleEnum.INVITED)
					.permissionList(Set.of(readPermission))
					.build();
			RoleEntity roleDeveloper = RoleEntity.builder().roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission,readPermission,updatePermission,deletePermission, refactorPermission))
					.build();

			UserEntity userJohn = UserEntity.builder()
					.username("john")
					.password("$2a$10$W/0UyaL5fkZ0ohLnm.NH5.ELlMMWMkk.UNA/MdeKM3tLUD.TBD5OS")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userEdward = UserEntity.builder()
					.username("edward")
					.password("$2a$10$W/0UyaL5fkZ0ohLnm.NH5.ELlMMWMkk.UNA/MdeKM3tLUD.TBD5OS")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
					.build();
			UserEntity userSandra = UserEntity.builder()
					.username("sandra")
					.password("$2a$10$W/0UyaL5fkZ0ohLnm.NH5.ELlMMWMkk.UNA/MdeKM3tLUD.TBD5OS")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleInvited))
					.build();
			UserEntity userJulia = UserEntity.builder()
					.username("julia")
					.password("$2a$10$W/0UyaL5fkZ0ohLnm.NH5.ELlMMWMkk.UNA/MdeKM3tLUD.TBD5OS")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();

			userRepository.saveAll(List.of(userJohn,userEdward,userSandra,userJulia));
		};
	}

//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("1234"));
//	}

}
