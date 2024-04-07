package found_work.controller;

// import org.example.JavaTaigaCode.models.TaskDTO;
// import org.example.JavaTaigaCode.service.FoundWorkService;
import found_work.models.TaskDTO;
import found_work.service.FoundWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@CrossOrigin 
@RequestMapping("/api")
public class FoundWorkController {

  @Autowired
  FoundWorkService foundWorkService;

  @Cacheable(value="foundWork", key = "#milestoneID")
  @GetMapping("/foundWork/{milestoneID}")
  @ResponseBody
  public List<TaskDTO> getFoundWorkByID(@PathVariable("milestoneID") Integer milestoneID, HttpServletRequest request) {
    String token = request.getHeader("token");  
    return foundWorkService.getFoundWork(milestoneID, token);
  }

}
