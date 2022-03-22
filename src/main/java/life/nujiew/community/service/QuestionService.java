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

    /**
     * 分页查询所有帖子
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer page, Integer size) {
        // 封装了questionDTOList和分页属性的DTO
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        // 查询帖子总数
        Integer totalCount = questionMapper.count();
        /*
         * 显示的总页数
         * 例如现在有12条帖子，totalCount = 12
         * 一页显示5条帖子，size = 5
         * 那么，totalPage = 12 / 5 + 1
         * */
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        // 设置分页条
        paginationDTO.setPagination(totalPage, page);

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
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

    /**
     * 我的帖子（查询某用户的帖子）
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        // 封装了questionDTOList和分页属性的DTO
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        // 查询帖子总数
        Integer totalCount = questionMapper.countByUserId(userId);
        /*
         * 显示的总页数
         * 例如现在有12条帖子，totalCount = 12
         * 一页显示5条帖子，size = 5
         * 那么，totalPage = 12 / 5 + 1
         * */
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        // 设置分页条
        paginationDTO.setPagination(totalPage, page);

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

        // 分页查询出对应用户的所有帖子
        List<Question> questions = questionMapper.listByUserId(userId,offset, size);
        // 封装了帖子属性和user的DTO
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        // 循环所有帖子
        for (Question question : questions) {
            // 通过帖子的Creator关联到user的id，查询出对应的user
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

    /**
     * 通过帖子id查出对应的帖子
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        // 快速将一个对象的所有属性拷贝到另一个目标对象上
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }


    /**
     * 更新帖子，判断是更新还是新建
     * @param question
     */
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 新建帖子
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            // 更新帖子
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
