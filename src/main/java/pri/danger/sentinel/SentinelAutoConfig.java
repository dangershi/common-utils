package pri.danger.sentinel;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.spring.annotation.ApolloJSONValue;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Sentienl限流配置
 *
 * @author: Danger
 * @time: 2022/6/1
 */
@Configuration
@EnableApolloConfig
@Slf4j
public class SentinelAutoConfig {

    private static Map<String, Object> flowQpsRuleMap;

    @ApolloJSONValue("${sentinel.flowrule:{\"sayHello\":{\"resource\":\"sayHello\",\"count\":1,\"grade\":1,\"limitApp\":\"default\"},\"sayBye\":{\"resource\":\"sayBye\",\"count\":20,\"grade\":1,\"limitApp\":\"default\"},\"testA\":{\"resource\":\"testA\",\"count\":10,\"grade\":1,\"limitApp\":\"default\"}}}")
    public void setFlowQpsRuleMap(Map<String, Object> data) {
        flowQpsRuleMap = data;
        log.info("update apollo flowQpsRuleMap:{}", flowQpsRuleMap);
        this.loadFlowQpsRules(flowQpsRuleMap);
    }

    private void loadFlowQpsRules(Map<String, Object> flowQpsRuleMap) {
        List<FlowRule> flowRules = new ArrayList<>();
        flowQpsRuleMap.forEach((k, v) -> {
            FlowRule flowRule = JSON.parseObject(JSON.toJSONString(v), FlowRule.class);
            flowRules.add(flowRule);
        });
        FlowRuleManager.loadRules(flowRules);
        log.info("loadFlowQpsRules success, flowRules={}", FlowRuleManager.getRules());
    }

}
