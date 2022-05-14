package sysu.usc.registerModule.structMapper.abs;

/**
 * <p>
 *      转换为最基本DO BaseObject的structMapper,
 *      拼接FromDTO BmcDTO(内含一个签名字符串)
 * </p>
 * @author littlepenguin
 * @since 2022/05/13 6:07
 */
public interface AbstractStructMapper<T,S> {

    /**
     * 源对象转换为目标对象
     * @param source 源对象
     * @return
     */
    T toTarget(S source);
}
