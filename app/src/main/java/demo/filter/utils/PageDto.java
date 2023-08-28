package demo.filter.utils;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageDto<T> {
    public int totalPages;
    public long totalElements;
    public List<T> content;

    public PageDto(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
    }

    public PageDto() {
    }

    public <R> PageDto<R> forEach(Function<? super T, ? extends R> mapper) {
        PageDto<R> result = new PageDto<>();
        result.totalPages = this.totalPages;
        result.totalElements = this.totalElements;
        result.content = this.content.stream().map(mapper).collect(Collectors.toList());
        return result;
    }
}
