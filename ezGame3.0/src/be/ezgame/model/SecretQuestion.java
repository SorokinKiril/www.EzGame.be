package be.ezgame.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the secret_question database table.
 * 
 */
@Entity
@Table(name="secret_question")

public class SecretQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SECRET_QUESTION_ID")
	private Integer secretQuestionId;

	private String question;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="secretQuestion")
	private List<User> users;

	public SecretQuestion() {
	}

	public Integer getSecretQuestionId() {
		return this.secretQuestionId;
	}

	public void setSecretQuestionId(Integer secretQuestionId) {
		this.secretQuestionId = secretQuestionId;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setSecretQuestion(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setSecretQuestion(null);

		return user;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && secretQuestionId != null)
				? secretQuestionId.equals(((SecretQuestion) other).secretQuestionId)
				: (other == this);
	}

	@Override
	public int hashCode() {
		return (secretQuestionId != null) ? (getClass().hashCode() + secretQuestionId.hashCode()) : super.hashCode();
	}

}