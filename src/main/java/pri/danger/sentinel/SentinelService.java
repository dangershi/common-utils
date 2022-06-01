package pri.danger.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pri.danger.DTO.SentinelDTO;

/**
 * Sentinel限流测试service
 *
 * @author: Danger
 * @time: 2022/6/1
 */
@Service
@Slf4j
public class SentinelService {

    @SentinelResource(value = "sayHello")
    public String sayHello(String name) {
        Entry entry = null;
        // 务必保证finally会被执行
        try {
            // 资源名可使用任意有业务语义的字符串
            entry = SphU.entry(name);
            // 被保护的业务逻辑
            return "Hello, " + name;
        } catch (BlockException e) {
            // 资源访问阻止，被限流或被降级
            // 进行相应的处理操作
            log.error("sayHello BlockException");
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return null;
    }

    @SentinelResource(value = "sayBye")
    public String sayBye(String name) {
        return "Bye, " + name;
    }

    /**
     * 通过sentinelDTO中的属性匹配限流规则
     *
     * @author Danger
     * @time: 2022/6/1
     * @param sentinelDTO
     * @return java.lang.String
     */
    public String flowRuleTest(SentinelDTO sentinelDTO) {
        Entry entry = null;
        // 务必保证finally会被执行
        try {
            // 资源名可使用任意有业务语义的字符串
            entry = SphU.entry(sentinelDTO.getName());
            // 被保护的业务逻辑
            printName(sentinelDTO);
            return sentinelDTO.getName();
        } catch (BlockException e) {
            // 资源访问阻止，被限流或被降级
            // 进行相应的处理操作
            return handleBlockExecption(sentinelDTO);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

    }

    /**
     * 触发限流时的业务操作
     *
     * @author Danger
     * @time: 2022/6/1
     * @param sentinelDTO
     * @return java.lang.String
     */
    private String handleBlockExecption(SentinelDTO sentinelDTO) {
        log.error("flowRuleSayHello handleBlockExecption, sentinelDTO={}", sentinelDTO);
        return "handleBlockExecption";
    }

    private void printName(SentinelDTO sentinelDTO) {
        System.out.println(sentinelDTO.getName());
    }

}
