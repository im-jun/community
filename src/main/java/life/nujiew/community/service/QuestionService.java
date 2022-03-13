package life.nujiew.community.service;

import life.nujiew.community.dto.PaginationDTO;
import life.nujiew.community.dto.QuestionDTO;
import life.nujiew.community.mapper.QuestionMapper;
import life.nujiew.community.mapper.UserMapper;
import life.nujiew.community.model.Question;
import life.nujiew.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    // 查询帖子
    public PaginationDTO list(Integer page, Integer size) {
        // 封装了questionDTOList和分页属性的DTO
        PaginationDTO paginationDTO = new PaginationDTO();
        // 查询帖子总数
        Integer totalCount = questionMapper.count();
        // 设置分页条
        paginationDTO.setPagination(totalCount, page, size);

        // 最小就是第一页
        if (page < 1) {
            page = 1;
        }
        // 最大就是最后一页
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        /*
        * 分页功能
        * 此处是计算偏移量，sql的limit需要使用，[limit offset,size]
        * 传进来两个参数，page接收用户点击的页码，从1开始，size代表一页显示的贴子数
        * 例如当前用户点击第2页，那么offset就是5
        * */
        Integer offset = size * (page - 1);

        // 分页查询出所有帖子
        List<Question> questions = questionMapper.list(offset, size);
        // 封装了帖子属性和user的DTO
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        // 循环所有帖子
        for (Question question : questions) {
            // 通过帖子的Creator关联到user的id，查询出对应的user
            User user = userMapper.findById(question.getCreator());
            // 创建DTO
            QuestionDTO questionDTO = new QuestionDTO();
            // 快速将一个对象的所有属性拷贝到另一个目标对象上
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            // 将设置好的DTO放入DTO集合中
            questionDTOList.add(questionDTO);
        }

        // 为PaginationDTO赋值
        paginationDTO.setQuestions(questionDTOList);

        // 返回DTO
        return paginationDTO;
    }
}
