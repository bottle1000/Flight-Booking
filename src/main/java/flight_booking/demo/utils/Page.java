package flight_booking.demo.utils;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Page<T> {

	private List<T> content;
	private int currentPage;
	private int totalPage;
	private int totalCount;

	public Page(List<T> content, int currentPage, int totalPage, int totalCount) {
		this.content = content;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
	}

	public static <T> Page<T> from(org.springframework.data.domain.Page<T> page) {
		return new Page<>(
			page.getContent(),
			page.getNumber(),
			page.getTotalPages(),
			(int)page.getTotalElements()
		);
	}
}