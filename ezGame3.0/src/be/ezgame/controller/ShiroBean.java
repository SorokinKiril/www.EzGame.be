package be.ezgame.controller;

import javax.persistence.EntityManager;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

import be.ezgame.emf.EMF;
import be.ezgame.model.User;
import be.ezgame.service.UserService;

public class ShiroBean extends AuthorizingRealm {

	private EntityManager em;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {

		em = EMF.getEM();
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String login = upToken.getUsername();
		String password = new String(upToken.getPassword());
		User user;
		UserService userService = new UserService(em);

		user = userService.findUserByLogin(login);

		em.close();
		if (user == null) {
			throw new AuthenticationException("Utilisateur ou mot de passe erroné.");
		} else {
			byte[] salt = user.getUserPasswordSalt().getBytes();
			String hashedPasswordBase64 = new Sha256Hash(password, user.getUserPasswordSalt(), 1024).toBase64();
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserId(), user.getUserPassword(),
					new SimpleByteSource(salt), getName());
			if (user.getUserPassword().equals(hashedPasswordBase64)) {
				return info;
			} else {
				return null;
			}
		}
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token.getClass().isAssignableFrom(UsernamePasswordToken.class);
	}
}
