package gallery.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Gallery {
	private Long id;
	private String image;
	private String comment;
}