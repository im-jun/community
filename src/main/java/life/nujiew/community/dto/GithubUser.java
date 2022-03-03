package life.nujiew.community.dto;

import lombok.Data;

/**
 * 封装返回的user信息
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
}
