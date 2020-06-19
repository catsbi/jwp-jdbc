package next.controller;

import core.annotation.web.Controller;
import core.annotation.web.RequestMapping;
import core.annotation.web.RequestMethod;
import core.annotation.web.RequestParam;
import core.db.DataBase;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dto.UserCreatedDto;
import next.dto.UserUpdatedDto;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView creatUser(UserCreatedDto userCreatedDto, HttpServletResponse response) {
        DataBase.addUser(userCreatedDto.toUser());

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.addHeader("location", "/api/users?userId=" + userCreatedDto.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView getUser(@RequestParam(value = "userId") String userId, HttpServletResponse response) {
        User user = DataBase.findUserById(userId);

        response.setStatus(HttpServletResponse.SC_OK);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView updateUser(@RequestParam(value = "userId") String userId,
                                   UserUpdatedDto userUpdatedDto,
                                   HttpServletResponse response) {
        User user = DataBase.findUserById(userId);
        user.update(userUpdatedDto.toUser());

        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ModelAndView(new JsonView());
    }
}
