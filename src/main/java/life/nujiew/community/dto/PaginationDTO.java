package life.nujiew.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页属性
 * 编写分页逻辑
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious; // 上一页
    private boolean showFirstPage; // 第一页
    private boolean showNext; // 下一页
    private boolean showEndPage; // 最后一页
    private Integer page; // 当前页
    private List<Integer> pages = new ArrayList<>(); // 存储分页条要展示的页码
    private Integer totalPage; // 总页数

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;

        // 设置分页条要展示的页码
        pages.add(page); // 先把当前页加进去
        for (int i = 1; i <= 3; i++) { // 主要是展示当前页左三页和右三页
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 当前页为1时，不展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        // 当前页为最后一页时，不展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        // 是否展示第一页
        if (pages.contains(1)) {
            // 如果显示的分页条中包含第一页，则不展示，否则展示
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // 是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
