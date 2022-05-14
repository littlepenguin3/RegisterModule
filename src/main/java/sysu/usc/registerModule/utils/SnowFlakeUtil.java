package sysu.usc.registerModule.utils;

/**
 * <p>
 *      雪花算法工具类
 * </p>
 * @author littlepenguin
 * @since 2022/05/14 6:28
 */
public class SnowFlakeUtil {

    /**
     * 起始的时间戳 2022-05-01 00:00:00:000
     */
    private final static long START_STAMP = 1651334400000L;


    /**
     * 序列号占用位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用位数
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 业务标识占用位数
     */
    private final static long SERVICE_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_SERVICE_NUM = ~(-1L << SERVICE_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long SERVICE_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = SERVICE_LEFT + SERVICE_BIT;

    private final long serviceId;
    private final long machineId;
    private long sequence = 0L;
    private long lastStamp = -1L;

    /**
     * 初始化雪花算法工具类
     * @param serviceId 服务号，在ServiceConstants中
     * @param machineId 机器号，需要对Redis注册
     */
    public SnowFlakeUtil(long serviceId, long machineId) {
        if (serviceId > MAX_SERVICE_NUM || serviceId < 0) {
            throw new IllegalArgumentException("serviceId can't be greater than MAX_SERVICE_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.serviceId = serviceId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return 下一个ID
     */
    public synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT
                | serviceId << SERVICE_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    /**
     * 从雪花ID中解析出时间戳
     * @return java毫秒时间戳
     */
    public static long parseStampFromId(long snowId){
        long timeStampHolder = (-1L << TIMESTAMP_LEFT+1) >> 1 ;
        return ((snowId & timeStampHolder) >> TIMESTAMP_LEFT) + START_STAMP;
    }
    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }

}
