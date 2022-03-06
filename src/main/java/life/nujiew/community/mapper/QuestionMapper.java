package life.nujiew.community.mapper;

import life.nujiew.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    // 发布帖子
    @Insert("insert into question (title,description,tag,creator,gmt_create,gmt_modified) " +
            "values (#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified})")
    void create(Question question);
}
