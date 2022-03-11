package life.nujiew.community.dto;

import life.nujiew.community.model.User;
import lombok.Data;

/**
 * 关联question和user
 * creator对应user中的id
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
