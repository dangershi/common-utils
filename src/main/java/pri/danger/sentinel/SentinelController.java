package pri.danger.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pri.danger.DTO.SentinelDTO;

/**
 * SentinelController
 *
 * @author: Danger
 * @time: 2022/6/1
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    @Autowired
    private SentinelService service;

    @GetMapping(value = "/sayHello/{name}")
    @ResponseBody
    public String sayHello(@PathVariable String name) {
        return service.sayHello(name);
    }

    @PostMapping(value = "/flowRuleTest")
    @ResponseBody
    public String flowRuleTest(@RequestBody SentinelDTO sentinelDTO) {
        return service.flowRuleTest(sentinelDTO);
    }
}
