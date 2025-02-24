package guestbook.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Guestbook {
	private Long id;
	private String name;
	private String password;
	private String regDate;
	private String contents;
}