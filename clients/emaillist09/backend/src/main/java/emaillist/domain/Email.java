package emaillist.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Email {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
}