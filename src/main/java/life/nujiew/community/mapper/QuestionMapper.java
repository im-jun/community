package life.nujiew.community.mapper;

import life.nujiew.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    // 发布帖子
    @Insert("insert into question (title,description,tag,creator,gmt_create,gmt_modified) " +
            "values (#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified})")
    void create(Question question);

    // 获取帖子列表
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    // 查询帖子总数
    @Select("select count(1) from question")
    Integer count();

    // 查找对应用户的帖子，也就是 ‘我的帖子’
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    // 查询某用户帖子总数
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);
}
