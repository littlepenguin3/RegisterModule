package sysu.usc.registerModule.constants;

/**
 * <p>
 *      Redis前缀及过期时间
 *      前缀格式 “register：业务：学号：次数”
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 9:05
 */
public class RedisConstants {
    public static final String REGISTER_SCHOOL_CARD_KEY = "register:schoolCard:";
    public static final Long LOGIN_CODE_TTL = 2L;

    public static final String COUNTER_NAME = "counter";
}
