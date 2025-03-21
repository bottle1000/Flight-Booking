package flight_booking.demo.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PageQuery {

	private int page = 0;
	private int size = 20;
	private Sort.Direction direction = Sort.Direction.DESC;
	private List<String> sort = new ArrayList<>();

	public PageQuery() {
	}

	public PageQuery(int page, int size, Sort.Direction direction, List<String> sort) {
		this.page = page;
		this.size = size;
		this.direction = direction;
		this.sort = sort != null ? sort : new ArrayList<>();
	}

	public PageRequest toPageable() {
		if (sort.isEmpty()) {
			return PageRequest.of(page, size);
		} else {
			return PageRequest.of(page, size, direction, sort.toArray(new String[0]));
		}
	}
}
