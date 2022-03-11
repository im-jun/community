package life.nujiew.community.service;

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
    public List<QuestionDTO> list() {
        // 查询出所有帖子
        List<Question> questions = questionMapper.list();
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

        // 返回DTO集合
        return questionDTOList;
    }
}
